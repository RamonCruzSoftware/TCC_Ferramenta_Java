package rcs.core.agents;



import jade.core.AID;
import jade.core.Agent;
import jade.core.Service;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.df;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.StaleProxyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.concurrent.Delayed;

import com.mongodb.MongoException;
import com.mongodb.util.MyAsserts.MyAssert;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.financial.wallet.Wallet;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.database.mongoDB.dao.UserInfoDao;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;



public class Manager  extends Agent{

private static final long serialVersionUID = 1L;
private OrdersCreate user;	
private Map<String,ArrayList<Stock>> infoExperts;
private Manager manager;
private UserInfoDao userInfoDao;
private StockDao stockDao;
private String userName;
private InfoConversations info;


protected void setup()
	{
		/*
		 * Create the hashMap with informations : FullName and an list of Stock Names
		 */
		
		manager=this;
		stockDao=new StockDao();
		
		try{
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			addBehaviour(new CyclicBehaviour(manager) 
			{
				@Override
				public void action() 
				{
					ACLMessage message=myAgent.receive();
					if(message!=null)
					{
						try {
						//Create the Experts 
							if(message.getConversationId()==ConversationsID.CREATE_EXPERTS)
							{
								//TODO apagar print
								System.out.println("Create the Experts ");
														
								user=(OrdersCreate)message.getContentObject();
								
								userName=user.getUserIndetifier();
													
								System.out.println("Manager Says: It's user's informations \n Name : "+user.getUserIndetifier()
										+" Profile: "+user.getUserPerfil()+" Value: "+user.getUserValue());
								manager.info=new InfoConversations(user.getUserIndetifier(), user.getUserPerfil());
								
								
								//Contact an hunter 
								//Yellow pages
								addBehaviour(new OneShotBehaviour(manager)
								{
									
									private static final long serialVersionUID = 1L;
									String hunterName;
									@Override
									public void action() {
												
										try
										{
											DFAgentDescription dfd= new DFAgentDescription();
											
											//Service 
											ServiceDescription service= new ServiceDescription();
											service.setType("StockHunter");
											service.setName("Hunter");
											
											dfd.addServices(service);
											
											//request service on yellow pages 
											DFAgentDescription[] result = DFService.search(manager, dfd);
											
											if(result !=null) hunterName=result[0].getName().getLocalName();
											
											ACLMessage hunterMessage=new ACLMessage(ACLMessage.INFORM);
											hunterMessage.addReceiver(new AID(hunterName, AID.ISLOCALNAME));
											hunterMessage.setLanguage("English");
											hunterMessage.setOntology("stocks");
											hunterMessage.setConversationId(ConversationsID.STOCKS_SUGGESTIONS);
											hunterMessage.setContentObject(manager.info);
											
											myAgent.send(hunterMessage);
								
											
										}catch(Exception e)
										{
											e.printStackTrace();
										}
									}
									
								});
								
							}
							if(message.getConversationId()==ConversationsID.STOCKS_SUGGESTIONS)
							{
							InfoConversations inf= (InfoConversations) message.getContentObject();
								
							//TODO apagar print
								System.out.println("Suggetions: "+inf.getStockList().size()+ " Acoes");
								manager.createExperts(inf.getUserProfile(), inf.getUserName(), inf.getStockList());
								
								addBehaviour(new WakerBehaviour(myAgent, 1000) 
								{
									protected void onWake()
									{
										System.out.println("Hora de acordar");
										System.out.println("Enviando mensagem para ..");
										System.out.println(manager.infoExperts);
										for( Entry<String, ArrayList<Stock>>s:manager.infoExperts.entrySet())
										{
											try {
												ACLMessage message=new ACLMessage(ACLMessage.INFORM);
												message.setLanguage("English");
												message.setConversationId(ConversationsID.INIT_WORK);
												message.addReceiver(new AID(s.getKey(),AID.ISLOCALNAME));
												
												message.setContentObject(s.getValue());
												
												System.out.println("{"+s.getKey()+"}");
												myAgent.send(message);
												
										} catch (IOException e)
										{
											
											e.printStackTrace();
										}
										}
									}
								});
								
								
							}
							if(message.getConversationId()==ConversationsID.USER_LOGGED)
							{
								try
								{
									
									for(Entry<String, ArrayList<Stock>>s:manager.infoExperts.entrySet())
									{
										for(Stock stk:s.getValue())
										{
											manager.stockDao.insertStocksSuggestion(stk,manager.userName);
											
										}
									}
								}catch(MongoException e)
								{
									e.printStackTrace();
								}
								
							}
							
	
						} catch (UnreadableException e) 
						{
							
							e.printStackTrace();
						}
					
					
					}else block();
				}
			});
			
			

		
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void takeDown()
	{
		System.out.println(this.getLocalName()+" says: Bye");
		try
		{
		 //Unregister the agent in plataform 
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.deregister(this, dfd);
			
			//kill experts 
			dropExpertAgent();
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
private void dropExpertAgent(String expertName)
{
	PlatformController container=getContainerController();
	try
	{
		if(!(infoExperts.get(expertName).isEmpty()))
		{
			AgentController agentController=container.getAgent(expertName);
			agentController.kill();
			
			//TODO transferir grupo de acoes para outro agente 
			
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
}

private void dropExpertAgent()
{
	PlatformController container=getContainerController();
	try
	{
		for(Entry<String,ArrayList<Stock>>expertAgent:infoExperts.entrySet())
		{
			System.out.println(expertAgent.getKey()+"killed");
			AgentController agentController=container.getAgent(expertAgent.getKey());
			agentController.kill();
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}

private void createExperts(int userProfile,String userIdentifier,ArrayList<Stock> listStocks)
{
	PlatformController container=getContainerController();
	AgentController  agentController;
	infoExperts=new HashMap<String,ArrayList<Stock>>();
	ArrayList<Stock> stockSeleted=new ArrayList<Stock>();
	
	//Create the experts agents
	
	if(userProfile==0) //Corajoso
	{
		if(listStocks.size()>5)
		{
			for(int i=0;i<6;i++)
			{
				stockSeleted.add(listStocks.get(i));
			}
		}else
			if(listStocks.size()<6)
			{
				stockSeleted=listStocks;
			}
		
		
		if(stockSeleted.size()>=2)
		{
			
			ArrayList<Stock> listA=new ArrayList<Stock>();
			ArrayList<Stock> listB=new ArrayList<Stock>();
			
			
			for(int i=0;i<stockSeleted.size()/2;i++)
			{
				listA.add(stockSeleted.get(i));
			}
			
			for(int i=stockSeleted.size()/2;i<stockSeleted.size();i++)
			{
				listB.add(stockSeleted.get(i));
			}
			
			
			infoExperts.put(userIdentifier+"["+1+"]" , listA);
			infoExperts.put(userIdentifier+"["+2+"]" , listB);
			
			
			
		}
	}
		
		if(userProfile==1)
		{
			if(listStocks.size()>15)
			{
				for(int i=0;i<15;i++)
				{
					stockSeleted.add(listStocks.get(i));
				}
			}else
				if(listStocks.size()<16)
				{
					stockSeleted=listStocks;
				}
			
			if(stockSeleted.size()>=5)
			{
				ArrayList<Stock> listA=new ArrayList<Stock>();
				ArrayList<Stock> listB=new ArrayList<Stock>();
				ArrayList<Stock> listC=new ArrayList<Stock>();
				
				double listSize=stockSeleted.size();
				
				for(int i=0;i<(int)(listSize/3);i++)
				{
					listA.add(stockSeleted.get(i));
				}
				for(int i=(int)(listSize/3);i<(int)((listSize/3)*2);i++)
				{
					listB.add(stockSeleted.get(i));
				}
				
				for(int i=(int)((listSize/3)*2);i<(int)listSize;i++)
				{
					listC.add(stockSeleted.get(i));
				}
				
				
				infoExperts.put(userIdentifier+"["+1+"]" , listA);
				infoExperts.put(userIdentifier+"["+2+"]" , listB);
				infoExperts.put(userIdentifier+"["+3+"]" , listC);

				
			}
		}
		if(userProfile==2)//Conservador 
		{
			if(listStocks.size()>20)
			{
				for(int i=0;i<20;i++)
				{
					stockSeleted.add(listStocks.get(i));
				}
			}else
				if(listStocks.size()<21)
				{
					stockSeleted=listStocks;
				}
			
				if(stockSeleted.size()>=15)
				{
					ArrayList<Stock> listA=new ArrayList<Stock>();
					ArrayList<Stock> listB=new ArrayList<Stock>();
					ArrayList<Stock> listC=new ArrayList<Stock>();
					ArrayList<Stock> listD=new ArrayList<Stock>();
					ArrayList<Stock> listE=new ArrayList<Stock>();
					ArrayList<Stock> listF=new ArrayList<Stock>();
					ArrayList<Stock> listG=new ArrayList<Stock>();
					
					double listSize=stockSeleted.size();
					
					for(int i=0;i<(int)(listSize/7);i++)
					{
						listA.add(stockSeleted.get(i));
					}
					for(int i=(int)(listSize/7);i<(int)((listSize/7)*2);i++)
					{
						listB.add(stockSeleted.get(i));
					}
					
					for(int i=(int)((listSize/7)*2);i<(int)((listSize/7)*3);i++)
					{
						listC.add(stockSeleted.get(i));
					}
					
					for(int i=(int)((listSize/7)*3);i<(int)((listSize/7)*4);i++)
					{
						listD.add(stockSeleted.get(i));
					}
					
					for(int i=(int)((listSize/7)*4);i<(int)((listSize/7)*5);i++)
					{
						listE.add(stockSeleted.get(i));
					}
					
					for(int i=(int)((listSize/7)*5);i<(int)((listSize/7)*6);i++)
					{
						listF.add(stockSeleted.get(i));
					}
					for(int i=(int)((listSize/7)*6);i<(int)(listSize);i++)
					{
						listG.add(stockSeleted.get(i));
					}
					
					
					infoExperts.put(userIdentifier+"["+1+"]" , listA);
					infoExperts.put(userIdentifier+"["+2+"]" , listB);
					infoExperts.put(userIdentifier+"["+3+"]" , listC);
					infoExperts.put(userIdentifier+"["+4+"]" , listD);
					infoExperts.put(userIdentifier+"["+5+"]" , listE);
					infoExperts.put(userIdentifier+"["+6+"]" , listF);
					infoExperts.put(userIdentifier+"["+7+"]" , listG);
					
					
				}
		}
		
		
		
		for(Entry<String, ArrayList<Stock>>s:infoExperts.entrySet())
		{
			try
			{
				agentController=container.createNewAgent(s.getKey(), "rcs.core.agents.Expert", null);
				agentController.start();
			}catch (StaleProxyException e) {
				
				e.printStackTrace();
			} catch (ControllerException e) {
				
					e.printStackTrace();
		}
		}
		
}


}
