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

import com.mongodb.util.MyAsserts.MyAssert;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.financial.wallet.Wallet;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.UserInfoDao;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;



public class Manager  extends Agent{

private static final long serialVersionUID = 1L;
private OrdersCreate user;	
private Map<String,ArrayList<Stock>> infoExperts;
private Manager manager;
private UserInfoDao userInfoDao;
private String userName;
private InfoConversations info;

protected void setup()
	{
		/*
		 * Create the hashMap with informations : FullName and an list of Stock Names
		 */
		
		manager=this;
		try{
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			//Conversations 
			//agentsConversations();
			
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
								
								addBehaviour(new WakerBehaviour(myAgent, 3000) 
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

private void requestStocksToHunter(Agent agent,int userProfile,double userValue,String userIdentifier)
{
	manager.info=new InfoConversations(userIdentifier,userProfile);
	
	//TODO apagar print 
	System.out.println("Create the Experts iniciando");
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
private void createExperts(int userProfile,String userIdentifier,ArrayList<Stock> listStocks)
{
	PlatformController container=getContainerController();
	AgentController  agentController;
	infoExperts=new HashMap<String,ArrayList<Stock>>();
	
	//Create the experts agents
	System.out.println("Cria as porras dos experts Perfil da merda do usuario eh "+userProfile);
	if(userProfile==0)
	{
		
		if(listStocks.size()>=2 && listStocks.size()<6)
		{
			System.out.println("Criando Listas");
			ArrayList<Stock> listA=new ArrayList<Stock>();
			ArrayList<Stock> listB=new ArrayList<Stock>();
			
			System.out.println("lista A");
			for(int i=0;i<listStocks.size()/2;i++)
			{
				listA.add(listStocks.get(i));
			}
			System.out.println("Lista B");
			for(int i=listStocks.size()/2;i<listStocks.size();i++)
			{
				listB.add(listStocks.get(i));
			}
			
			System.out.println("Carregando infoExperts");
			infoExperts.put(userIdentifier+"["+1+"]" , listA);
			infoExperts.put(userIdentifier+"["+2+"]" , listB);
			System.out.println("inforExperts "+infoExperts);
			
			
		}
		System.out.println("Criando agentes expertes");
		for(int i=0;i<2;i++)
		{
			
			try {
				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
				agentController.start();
				
					} catch (StaleProxyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ControllerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		}
		
		
		
		
	}
	
	
//	try
//	{
//		switch (userProfile) {
//		case 0://Corajoso
//		{
//			for(int i=0;i<2;i++)
//			{
//				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
//				agentController.start();
//				
//			}
//			if(listStocks.size()>=2 && listStocks.size()<6)
//			{
//				ArrayList<Stock> listA=new ArrayList<Stock>();
//				ArrayList<Stock> listB=new ArrayList<Stock>();
//				
//				for(int i=0;i<listStocks.size()/2;i++)
//				{
//					listA.add(listStocks.get(i));
//				}
//				for(int i=listStocks.size()/2;i<listStocks.size();i++)
//				{
//					listA.add(listStocks.get(i));
//				}
//				
//				infoExperts.put(userIdentifier+"["+1+"]" , listA);
//				infoExperts.put(userIdentifier+"["+2+"]" , listB);
//				
//				
//			}
//		}break;
//		case 1://Moderado
//		{
//			
//			if(listStocks.size()>=5 && listStocks.size()<16)
//			{
//				ArrayList<Stock> listA=new ArrayList<Stock>();
//				ArrayList<Stock> listB=new ArrayList<Stock>();
//				ArrayList<Stock> listC=new ArrayList<Stock>();
//				
//				double listSize=listStocks.size();
//				
//				for(int i=0;i<(int)(listSize/3);i++)
//				{
//					listA.add(listStocks.get(i));
//				}
//				for(int i=(int)(listSize/3);i<(int)((listSize/3)*2);i++)
//				{
//					listB.add(listStocks.get(i));
//				}
//				
//				for(int i=(int)((listSize/3)*2);i<(int)listSize;i++)
//				{
//					listC.add(listStocks.get(i));
//				}
//				
//				
//				infoExperts.put(userIdentifier+"["+1+"]" , listA);
//				infoExperts.put(userIdentifier+"["+2+"]" , listB);
//				infoExperts.put(userIdentifier+"["+3+"]" , listC);
//				
//				for(int i=0;i<3;i++)
//				{
//					agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
//					agentController.start();
//					
//				}
//				
//			}
//			
//		}break;
//		case 2://Conservador
//		{
//			for(int i=0;i<7;i++)
//			{
//				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
//				agentController.start();
//			
//			}
//			
//			if(listStocks.size()>=15 && listStocks.size()<21)
//			{
//				ArrayList<Stock> listA=new ArrayList<Stock>();
//				ArrayList<Stock> listB=new ArrayList<Stock>();
//				ArrayList<Stock> listC=new ArrayList<Stock>();
//				ArrayList<Stock> listD=new ArrayList<Stock>();
//				ArrayList<Stock> listE=new ArrayList<Stock>();
//				ArrayList<Stock> listF=new ArrayList<Stock>();
//				ArrayList<Stock> listG=new ArrayList<Stock>();
//				
//				double listSize=listStocks.size();
//				
//				for(int i=0;i<(int)(listSize/7);i++)
//				{
//					listA.add(listStocks.get(i));
//				}
//				for(int i=(int)(listSize/7);i<(int)((listSize/7)*2);i++)
//				{
//					listB.add(listStocks.get(i));
//				}
//				
//				for(int i=(int)((listSize/3)*2);i<(int)((listSize/3)*3);i++)
//				{
//					listC.add(listStocks.get(i));
//				}
//				
//				for(int i=(int)((listSize/3)*3);i<(int)((listSize/3)*4);i++)
//				{
//					listD.add(listStocks.get(i));
//				}
//				
//				for(int i=(int)((listSize/3)*4);i<(int)((listSize/3)*5);i++)
//				{
//					listE.add(listStocks.get(i));
//				}
//				
//				for(int i=(int)((listSize/3)*5);i<(int)((listSize/3)*6);i++)
//				{
//					listF.add(listStocks.get(i));
//				}
//				for(int i=(int)((listSize/3)*6);i<(listSize);i++)
//				{
//					listG.add(listStocks.get(i));
//				}
//				
//				
//				infoExperts.put(userIdentifier+"["+1+"]" , listA);
//				infoExperts.put(userIdentifier+"["+2+"]" , listB);
//				infoExperts.put(userIdentifier+"["+3+"]" , listC);
//				infoExperts.put(userIdentifier+"["+4+"]" , listD);
//				infoExperts.put(userIdentifier+"["+5+"]" , listE);
//				infoExperts.put(userIdentifier+"["+6+"]" , listF);
//				infoExperts.put(userIdentifier+"["+7+"]" , listG);
//				
//				
//			}
//		}break;
//		default:
//		{
//			for(int i=0;i<1;i++)
//			{
//				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
//				agentController.start();
//				infoExperts.put(userIdentifier+"["+(i+1)+"]" , listStocks);
//			}
//			
//		}
//			break;
//		}
//		
//		
//		
//		
//		
//	}catch(Exception e)
//	{
//		e.printStackTrace();
//	}
	
	
}
private void agentsConversations()
{
	addBehaviour(new CyclicBehaviour(manager)
	{
		
		private static final long serialVersionUID = 1L;

		@Override
		public void action() 
		{
			try {
					ACLMessage message= myAgent.receive();
					if(message!=null )
					{
						//Create the Experts 
						if(message.getConversationId()==ConversationsID.CREATE_EXPERTS)
						{
							//TODO apagar print
							System.out.println("Create the Experts ");
							
							user=(OrdersCreate)message.getContentObject();
							userName=user.getUserIndetifier();
							
							
							System.out.println("Manager Says: It's user's informations \n Name : "+user.getUserIndetifier()
									+" Profile: "+user.getUserPerfil()+" Value: "+user.getUserValue());
							
							manager.requestStocksToHunter(manager, user.getUserPerfil(),user.getUserValue(), "Expert_"+user.getUserIndetifier());
							
						}
						//Reply Ok to create 
						if(message.getConversationId()==ConversationsID.CREATE_MANAGER)
						{
							ACLMessage reply=message.createReply();
							if(message.getContent().equalsIgnoreCase(ConversationsID.CREATE_MANAGER))
							{
								reply.setPerformative(ACLMessage.INFORM);
								reply.setContent("Ok.");
								myAgent.send(reply);
								
							}
						}
						
						if(message.getConversationId()==ConversationsID.USER_LOGGED)
						{
							userInfoDao= new UserInfoDao();
							userInfoDao.userLogged();
							
							System.out.println("Manager says: Ok, initializing conversation with "+userName);
							userConversations(userName);
							
						}
						
						if(message.getConversationId()==ConversationsID.STOCKS_SUGGESTIONS)
						{
								manager.info=(InfoConversations) message.getContentObject();
								System.out.println("Hunter recomendou "+manager.info.getStockList().size()+" Acoes");
								
						    	manager.createExperts(user.getUserPerfil(), user.getUserIndetifier(), manager.info.getStockList());	
						}
						
					}else block();
			
			} catch (UnreadableException e) {
				
				e.printStackTrace();
			}
			
		}
	});
	addBehaviour(new Behaviour(manager)
	{
		
		private static final long serialVersionUID = 1L;
		private boolean stopBehaviour=false;
		private int count=0;
		
		
		@Override
		public void action()
		{
			try {
			
					ACLMessage message=null;
					if(manager.infoExperts.size()>0)
					{
						for(Entry<String, ArrayList<Stock>>e:manager.infoExperts.entrySet())
						{
							
									System.out.println("Manager sending mensage to "+e.getKey());
									message=new ACLMessage(ACLMessage.INFORM);
									message.setConversationId(ConversationsID.CREATE_EXPERTS);
									message.setLanguage("English");
									message.addReceiver(new AID(e.getKey(), AID.ISLOCALNAME));
									message.setContentObject(e.getValue());
									
									myAgent.send(message);
									
							
						}
					}
					
					ACLMessage expertreply=myAgent.receive();
					if(expertreply!=null && expertreply.getConversationId()==ConversationsID.CREATE_EXPERTS)
					{
							count++;
							if(count==manager.infoExperts.size())
							{
								stopBehaviour=true;
							}
					}else block();
			} catch (IOException e1)
			{
				
				e1.printStackTrace();
			}
			
		}

		@Override
		public boolean done() {
			
			return stopBehaviour;
		}
	});
}

private void userConversations(final String userIdentifier)
{
	addBehaviour(new Behaviour() {
		UserInfoDao userInfoDao=new UserInfoDao();
		@Override
		public boolean done() {
			
			if(userInfoDao.isUserUnLogged(userIdentifier))
				return true;
			else
			return false;
			
		}
		
		@Override
		public void action() 
		{
			
			Wallet walletInfo=new Wallet();
			walletInfo.setUserID(userIdentifier);
			walletInfo.setWalletValue(100000.f);
			
			userInfoDao.setNewInformationToUser(walletInfo);
			
			
		}
	});
}


}
