package rcs.core.agents;



import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
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

import rcs.suport.financial.wallet.Stock;
import rcs.suport.statistical.Statistical;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.ManagedStockDao;
import rcs.suport.util.database.mongoDB.dao.ManagedWalletDao;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.database.mongoDB.dao.UserInfoDao;
import rcs.suport.util.database.mongoDB.pojo.ManagedStock;
import rcs.suport.util.database.mongoDB.pojo.ManagedWallet;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;

import com.mongodb.MongoException;



public class Manager  extends Agent{

private static final long serialVersionUID = 1L;
private OrdersCreate user;	
private Map<String,ArrayList<Stock>> infoExperts;
private Map <String,String> strategyExperts;
private Manager manager;
private UserInfoDao userInfoDao;
private StockDao stockDao;
private String userName;
private InfoConversations info;
private WalletManager walletManager;

protected void setup()
{
		/*
		 * Create the hashMap with informations : FullName and an list of Stock Names
		 */
		
		manager=this;
		
		 walletManager= new WalletManager();
		 
		
		
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
											hunterMessage.setConversationId(ConversationsID.STOCKS_HUNTER_SUGGESTIONS);
											hunterMessage.setContentObject(manager.info);
											
											myAgent.send(hunterMessage);
								
											
										}catch(Exception e)
										{
											e.printStackTrace();
										}
									}
									
								});
								
							}
							
							if(message.getConversationId()==ConversationsID.STOCKS_HUNTER_SUGGESTIONS)
							{
								InfoConversations inf= (InfoConversations) message.getContentObject();
								WalletManager m=new WalletManager();
							//TODO apagar print
								System.out.println("Suggetions: "+inf.getStockList().size()+ " Acoes");
								//colocar aqui tratamento de aceite de acoes 
							//	System.out.println("Carreira aprovada? "+m.analyzeStockSuggestions(inf.getStockList(), 2));
								
								//Descomentar isso
								manager.createExperts(inf.getUserProfile(), inf.getUserName(), inf.getStockList());
								
								//Iniciando atividade delegando aos experts tutela de acoes 
								addBehaviour(new WakerBehaviour(myAgent, 100) 
								{
									protected void onWake()
									{
										
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
										//Faz reparticao de dinheiro 
										manager.walletManager= new WalletManager(manager.infoExperts,manager.user.getUserValue());
										
		
									}
								});
								
								//enviando Estrategias para agentes experts 
								addBehaviour(new WakerBehaviour(myAgent, 200) 
								{

									private static final long serialVersionUID = 1L;
									protected void onWake()
									{
										for(Entry<String, String>s:manager.strategyExperts.entrySet())
										{
											try {
												ACLMessage message=new ACLMessage(ACLMessage.INFORM);
												message.setLanguage("English");
												message.setConversationId(s.getValue());
												message.addReceiver(new AID(s.getKey(),AID.ISLOCALNAME));
												
												myAgent.send(message);
												
										} catch (Exception e)
										{
											
											e.printStackTrace();
										}
										}
									}
									
								});
								
								//Envia nome do usuario
								addBehaviour(new WakerBehaviour(manager, 300) 
								{
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;

									protected void onWake()
									{
										for(Entry<String, ArrayList<Stock>>s:manager.infoExperts.entrySet())
										{
											try 
											{
												ACLMessage message=new ACLMessage(ACLMessage.INFORM);
												message.setLanguage("English");
												message.setConversationId(ConversationsID.EXPERT_USER_NAME);
												message.addReceiver(new AID(s.getKey(),AID.ISLOCALNAME));
												
												message.setContentObject(manager.user);
												
											} catch (IOException e) {
												
												e.printStackTrace();
											}
											
											
										}
										
									}
								});
								
								
							}
							if(message.getConversationId()==ConversationsID.EXPERT_ORDER_BUY)
							{
								
								System.out.println(message.getSender().getLocalName()+ " pediu dinheiro para comprar..");
								
								ACLMessage reply=message.createReply();
								String stringValue=""+manager.walletManager.approveOrderBuy(message.getSender().getLocalName());
								reply.setContent(stringValue);
								myAgent.send(reply);
													
								
							}
							if(message.getConversationId()==ConversationsID.EXPERT_ORDER_SELL)
							{
								System.out.println(getLocalName()+": "+ message.getSender().getLocalName()+ " vendeu acoes e lucrou.. .");
								
								double profitValue= Double.parseDouble((String)message.getContent());
								
									System.out.println(profitValue);
								
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
							
	
						} catch(UnreadableException e1) 
						{
							
							e1.printStackTrace();
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
	strategyExperts = new HashMap<String, String>();
	
	ArrayList<Stock> stockSeleted=new ArrayList<Stock>();
	
	//Create the experts agents
	
	if(userProfile==0) //Corajoso
	{
		if(listStocks.size()>8)
		{
			for(int i=0;i<8;i++)
			{
				stockSeleted.add(listStocks.get(i));
			}
		}else
			if(listStocks.size()<9)
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
			
			strategyExperts.put(userIdentifier+"["+1+"]", ConversationsID.EXPERT_STRATEGY_MME_13_21);
			strategyExperts.put(userIdentifier+"["+2+"]", ConversationsID.EXPERT_STRATEGY_DARK_CLOUD_BULLISH_ENGULF);
			
			
		}else
		{ if(stockSeleted.size()==1)
		{
			infoExperts.put(userIdentifier+"["+1+"]" , stockSeleted);
			strategyExperts.put(userIdentifier+"["+1+"]", ConversationsID.EXPERT_STRATEGY_MME_13_21);
		}
			
		}
	}
		
		if(userProfile==1)
		{
			if(listStocks.size()>13)
			{
				for(int i=0;i<13;i++)
				{
					stockSeleted.add(listStocks.get(i));
				}
			}else
				if(listStocks.size()<14)
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
				
				//Mudar estrategia
				strategyExperts.put(userIdentifier+"["+1+"]", ConversationsID.EXPERT_STRATEGY_MME_13_21);
				strategyExperts.put(userIdentifier+"["+2+"]", ConversationsID.EXPERT_STRATEGY_MMS_13_21);
				strategyExperts.put(userIdentifier+"["+3+"]", ConversationsID.EXPERT_STRATEGY_MME_13_21);

				
			}
		}
		if(userProfile==2)//Conservador 
		{
			if(listStocks.size()>30)
			{
				for(int i=0;i<30;i++)
				{
					stockSeleted.add(listStocks.get(i));
				}
			}else
				if(listStocks.size()<30)
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
					
					//Mudar estrategia
					strategyExperts.put(userIdentifier+"["+1+"]", ConversationsID.EXPERT_STRATEGY_MMS_13_21);
					strategyExperts.put(userIdentifier+"["+2+"]", ConversationsID.EXPERT_STRATEGY_MMS_21_34);
					strategyExperts.put(userIdentifier+"["+3+"]", ConversationsID.EXPERT_STRATEGY_MME_21_34);
					
					strategyExperts.put(userIdentifier+"["+4+"]", ConversationsID.EXPERT_STRATEGY_MMS_13_21);		
					strategyExperts.put(userIdentifier+"["+5+"]", ConversationsID.EXPERT_STRATEGY_MMS_21_34);
					strategyExperts.put(userIdentifier+"["+6+"]", ConversationsID.EXPERT_STRATEGY_MME_21_34);
					
					strategyExperts.put(userIdentifier+"["+7+"]", ConversationsID.EXPERT_STRATEGY_MMS_13_21);
				
					//MMS (13/21) MMS(21/34) MME (21/34)
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
//Metodos de gerenciamento da carteira 

private ArrayList<String> approveOrder(ArrayList<String> orders)
{
	
	return null;
}

private class WalletManager
{
	 ManagedStockDao managedStockDao;
	 
	
	 ManagedWallet managedWallet;
	 ManagedWalletDao managedWalletDao;
	 StockDao stockDao;
	 double quota;
	 
	 Map<String,Double> expertsQuota;
	 Map<String,ArrayList<Stock>> infoExperts;
	 
	 public WalletManager()
	 {
		 this.stockDao= new StockDao();
	 }
	 
	 public WalletManager(Map<String,ArrayList<Stock>> infoExperts,double userValue)
	 {
		 
		 this.managedWallet= new ManagedWallet();
		 this.stockDao= new StockDao();
		 this.managedWalletDao= new ManagedWalletDao();
		 this.infoExperts=infoExperts;
		 this.expertsQuota=new HashMap<String, Double>();
		 
		 try
		 {
			 this.managedWallet.setWalletValue(userValue);
			 //Money qtd for Expert
			 this.quota=this.managedWallet.getWalletValue()/this.infoExperts.size();
			 this.managedWallet.setUserID(userName);
			 
			 this.managedWalletDao.insertManagedWalletInfo(this.managedWallet);
			 
		 }catch(Exception e)
		 {
			e.printStackTrace(); 
		 } 
		 
		 //Criando map para controlar quantidade de dinheiro por agente 
		 for(Entry<String, ArrayList<Stock>>e:this.infoExperts.entrySet())
		 {
			 this.expertsQuota.put(e.getKey(), this.quota);
			 System.out.println(getLocalName()+" valor disponivel para "+e.getKey()+ " R$:"+this.quota);
		 }
		 
		 
	 }
	 
	 public double approveOrderBuy(String expertName)
	 {
		 double quota=0;
		 try
		 {
			 quota=this.expertsQuota.get(expertName);
			 this.expertsQuota.remove(expertName);
			 this.expertsQuota.put(expertName, 0.0);
			 			 
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 return quota;
	 }
	 public void approveOrderSell(String expertName, double returnValue)
	 {
		 try
		 {
			 this.expertsQuota.remove(expertName);
			 this.expertsQuota.put(expertName, returnValue); 
			 
		 }catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	 }
	 public boolean refreshWalletManager ()
	 {
		 ArrayList<ManagedStock> managedStockStored = this.managedStockDao.getManagedStock(userName);
		 ArrayList<Stock> stockList = new ArrayList<Stock>();
		 ManagedWallet  refresh= new ManagedWallet();
		 
		 if(managedStockStored!=null && managedStockStored.size()>1)
		 {
			 
			 for(ManagedStock ms: managedStockStored)
			 {
				 stockList.add(new Stock(ms.getCodeName(), ms.getSector()));
			 }
			 refresh.setStocksList(stockList);
			 refresh.setUserID(userName);
			 
			 return managedWalletDao.updateManagedWallet(refresh);
			  
			 
		 }else
			 
		 return false;
	 }
	 public  boolean  analyzeStockSuggestions(ArrayList<Stock> stockList, int correlLimit)
	 {
		 int count=0;
		 boolean result=false;
		 Statistical statistical= new Statistical();
		 ArrayList<Stock>stockList_temp=new ArrayList<Stock>();
		try
		{
			for(Stock s: stockList)
			{
				s.setCandleSticks(this.stockDao.getStockPrices_last30(s.getCodeName()));
				stockList_temp.add(s);
			}
			
			for(int i=0;i<stockList_temp.size();i++)
			 {
				
				//Buscando as candlesticks 
				// stockList.get(i).setCandleSticks(this.stockDao.getStockPrices_last30(stockList.get(i).getCodeName()));
				 
				 for(int j=i;j<stockList_temp.size();j++)
				 {
					
					double correl=statistical.calculeCorrelationCoefficient_15(stockList_temp.get(i).getCandleSticks(), stockList_temp.get(j).getCandleSticks());
					
					 System.out.println(i+" com "+j);
					 System.out.println(stockList_temp.get(i).getCodeName()+" com "+stockList_temp.get(j).getCodeName());
					System.out.println("Correl "+correl);
					
					if(correl>0 && i!=j)
					{
						 
						count++;
					}
				 }
			 }
			 System.out.println(" count:"+count);
			if(count<=correlLimit) 
				 result= true;
			 else  result= false;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		 
		 
		 
		return result;
		 
	 }
	 
	 
	
	 
	 
}
}
