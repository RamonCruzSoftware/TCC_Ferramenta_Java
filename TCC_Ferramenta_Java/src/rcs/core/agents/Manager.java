package rcs.core.agents;



import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
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

import rcs.core.agents.suport.WalletManagerAuxiliary;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;

import com.mongodb.MongoException;



public class Manager  extends Agent{

private static final long serialVersionUID = 1L;
private OrdersCreate user;	
private Map<String,ArrayList<Stock>> infoExperts; // Key:AID Expert ; Value: list of Stocks
private Map <String,String> strategyExperts;      //Key:AID Expert  ; Value: Strategy Name
private ArrayList<Stock>stockListManaged;		  //List of Stocks workeds	
private ArrayList<Stock> stockListForUserApprove;//
private Manager manager;
private StockDao stockDao;
private String userName;
private InfoConversations info;
private WalletManagerAuxiliary walletManagerAuxiliary;

private int STOCK_QTD_CORAJOSO=8;
private int STOCK_QTD_MODERADO=13;
private int STOCK_QTD_CONSERVADOR=30;


protected void setup()
{
	
		/*
		 * Create the hashMap with informations: FullName and an list of Stock Names
		 * 
		 */
		
	 manager=this;
	 manager.stockDao=new StockDao();
	 stockListForUserApprove= new ArrayList<Stock>();		 
	 
		
		try{
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			addBehaviour(new CyclicBehaviour(manager) 
			{
				
				private static final long serialVersionUID = 1L;

				@Override
				public void action() 
				{
					ACLMessage message=myAgent.receive();
					if(message!=null)
					{
					try {
						switch (message.getPerformative())
						{
						case ACLMessage.INFORM:
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
											
											ACLMessage hunterMessage=new ACLMessage(ACLMessage.CFP);
											hunterMessage.addReceiver(new AID(hunterName, AID.ISLOCALNAME));
											
											hunterMessage.setConversationId(ConversationsID.STOCKS_HUNTER_SUGGESTIONS);
											hunterMessage.setContentObject(manager.info);
											
											//Request Stock Suggestions 
											myAgent.send(hunterMessage);
								
											
										}catch(Exception e)
										{
											e.printStackTrace();
										}
									}
									
								});
								
							}
							
							//Se usuario estiver logado
							if(message.getConversationId()==ConversationsID.USER_LOGGED)
							{
								try
								{
									
//									for(Entry<String, ArrayList<Stock>>s:manager.infoExperts.entrySet())
//									{
//										for(Stock stk:s.getValue())
//										{
//											manager.stockDao.insertStocksSuggestion(stk,manager.userName);
//											
//										}
//									}
									if(manager.stockListForUserApprove.size()>0)
									{
										for(Stock s:manager.stockListForUserApprove)
										{
											manager.stockDao.insertStocksSuggestion(s, manager.userName);
										}
									}
									
									
									
								}catch(MongoException e)
								{
									e.printStackTrace();
								}
								
							}
							
							if(message.getConversationId()==ConversationsID.EXPERT_ORDER_SELL)
							{
								System.out.println(getLocalName()+": "+ message.getSender().getLocalName()+ " vendeu acoes e lucrou.. .");
								
								double profitValue= Double.parseDouble((String)message.getContent());
								
									System.out.println(profitValue);
								
							}
						}break;
						
						case ACLMessage.PROPOSE:
						{
							System.out.println("Propose Recived");
							if(message.getConversationId()==ConversationsID.STOCKS_HUNTER_SUGGESTIONS)
							{
								
								manager.info= (InfoConversations) message.getContentObject();
								manager.stockListManaged= new ArrayList<Stock>();
								
								System.out.println("=====================");
								System.out.println("stock List size"+manager.info.getStockList().size());
								
								System.out.println("user value "+manager.user.getUserValue());
								System.out.println("user profile "+manager.info.getUserProfile());
								System.out.println("=====================");
								
								
								SequentialBehaviour suggestions= new SequentialBehaviour(manager);
								suggestions.addSubBehaviour(new OneShotBehaviour(manager)
								{
									
									@Override
									public void action() 
									{
										try
										{
											ArrayList<ArrayList<Stock>> listTemp;
											ArrayList<Stock> listTemp_approved;
											ArrayList<Stock> listTemp_refused;
											
											walletManagerAuxiliary= new WalletManagerAuxiliary(manager.info.getStockList(),manager.user.getUserValue(),manager.info.getUserProfile());
											//TODO apagar print
											System.out.println("Suggetions: "+manager.info.getStockList().size()+ " Acoes");
											System.out.println("Lower Percent: "+manager.info.getLowerPercent());
											System.out.println("Upper Percent: "+manager.info.getUpperPercent());
											
											System.out.println("Value user "+manager.user.getUserValue());
											System.out.println("Profile User "+manager.info.getUserProfile());
											
											listTemp=manager.walletManagerAuxiliary.analyzeStocksSuggestionsList();
											//TODO apagar 
											System.out.println("Lista de acoes recebidas "+listTemp);
											
											listTemp_approved=listTemp.get(0);
											listTemp_refused=listTemp.get(1);
											
											/*
											 * Esse trecho pode da problema ... melhorar isso 
											 * do jeito q esta, se a quantidade de acoes for menor do que o limite ... vai dar erro 
											 * 
											 */
								
											switch (manager.info.getUserProfile()) {
											case 0://Corajoso
											{
												if(listTemp_approved.size()>=manager.STOCK_QTD_CORAJOSO)
												{
													for(int i=0;i<manager.STOCK_QTD_CORAJOSO;i++)
													{
														manager.stockListManaged.add(listTemp_approved.get(i));
													}
													
												}else
												{
													for(Stock stock : listTemp_approved)
													{
														manager.stockListManaged.add(stock);
													}
													for(int i=0;i<2;i++)
													{
														manager.stockListManaged.add(listTemp_refused.get(i));
													}
												}
												
											}
												break;
											case 1://Moderado
											{
												if(listTemp_approved.size()>=manager.STOCK_QTD_MODERADO)
												{
													for(int i=0;i<manager.STOCK_QTD_MODERADO;i++)
													{
														manager.stockListManaged.add(listTemp_approved.get(i));
													}
												}else
												{
													for(Stock stock : listTemp_approved)
													{
														manager.stockListManaged.add(stock);
													}
													for(int i=0;i<2;i++)
													{
														manager.stockListManaged.add(listTemp_refused.get(i));
													}
												}
												
											}
												break;
											case 2://Conservador
											{
												if(listTemp_approved.size()>=manager.STOCK_QTD_CONSERVADOR)
												{
													for(int i=0;i<manager.STOCK_QTD_CONSERVADOR;i++)
													{
														manager.stockListManaged.add(listTemp_approved.get(i));
													}
												}else
												{
													for(Stock stock : listTemp_approved)
													{
														manager.stockListManaged.add(stock);
													}
													for(int i=0;i<2;i++)
													{
														manager.stockListManaged.add(listTemp_refused.get(i));
													}
												}
												
												
											}
												break;

											default:
												break;
											}
											
											
											//Criando os agentes experts
											manager.createExperts(manager.info.getUserProfile(), manager.info.getUserName(), manager.stockListManaged);
										}catch(Exception e)
										{
											e.printStackTrace();
										}
										
										
									}
									
									
								});
								
							
								//Reparticao das acoes 
								suggestions.addSubBehaviour(new WakerBehaviour(manager,200) {
									
		
									@Override
									public void onWake() {
										
											//Informando nome dos experts para trabalhar a gestao de valores 
										    manager.walletManagerAuxiliary.putInfoExperts(manager.infoExperts);
										   
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
								
								
								//Reparticao das estratégias entre os agentes 
								suggestions.addSubBehaviour(new WakerBehaviour(manager,200) 
								{
									
									@Override
									public void onWake() {
										
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
								
								
								//Informa aos experts o nome do usuario 
								suggestions.addSubBehaviour(new OneShotBehaviour(manager) {
									
									@Override
									public void action() {
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
								
								
								addBehaviour(suggestions);

								
							}
							
							
							if(message.getConversationId()==ConversationsID.EXPERT_ORDER_BUY)
							{
								
//								double value=0;
//								System.out.println(message.getSender().getLocalName()+ " pediu dinheiro para comprar..");
//								
//								ACLMessage reply=message.createReply();
//
//								value=manager.walletManagerAuxiliary.approveOrderBuy(message.getSender().getLocalName());
//								
//								if(value>0)
//								{
//									reply.setConversationId(ConversationsID.EXPERT_ORDER_BUY);
//									reply.setPerformative(ACLMessage.AGREE);
//									reply.setContent(""+value);
//									
//								}else
//								{
//									reply.setConversationId(ConversationsID.EXPERT_ORDER_BUY);
//									reply.setPerformative(ACLMessage.REFUSE);
//								}
//								
//								
//								myAgent.send(reply);
								
								System.out.println(message.getSender().getLocalName()+ " pediu dinheiro para comprar..");
								
								ArrayList<Stock> contentObject= (ArrayList<Stock>) message.getContentObject();
								
								if(contentObject!=null && contentObject.size()>0)
								{
									for(Stock s:contentObject)
									{
										manager.stockListForUserApprove.add(s);
									}
								}
								
							}

							
						}break;
						
						case 0:
						{
							
						}

						default:
							break;
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
		if(listStocks.size()>=8)
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
			if(listStocks.size()>=13)
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
				if(listStocks.size()<=30)
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

}

