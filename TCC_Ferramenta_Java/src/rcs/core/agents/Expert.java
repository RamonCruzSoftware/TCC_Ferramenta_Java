package rcs.core.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.util.MyAsserts.MyAssert;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.strategy.Bearish_Bullish_Strategy;
import rcs.suport.financial.strategy.Fake_Strategy;
import rcs.suport.financial.strategy.MovingAvarangeExponentialStrategy;
import rcs.suport.financial.strategy.MovingAvarangeSimpleStrategy;
import rcs.suport.financial.strategy.Strategy;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.ManagedStockDao;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.database.mongoDB.pojo.ManagedStock;
import rcs.suport.util.requests.YahooFinance;


public class Expert extends Agent {

	private static final long serialVersionUID = 1L;
	private Expert expert;
	private ArrayList<Stock> stockList;
	private StockDao stockDao;
	private YahooFinance yahooFinances;
	
	
	private String dir_1="/home/ramon/Desktop";
	private String subDir_1="/TCC2";
	private String subDir_2="/Ativos";
	private String sectorsCsvFilePath="/home/ramon/Documents/Workspace/Setores";
	
	
	private ManagedStock managedStock;
	private ManagedStockDao managedStockDao;
	private Map<Stock, Strategy> stocksMap;
	private Map<Stock,String> ordensToBuyOrSell; //Buy,Sell,Nothing
	
	private ArrayList<Stock> orderToApproveBuy;
	private ArrayList<Stock> orderToApproveSell;
	
	private String userIdentifier;
	private String managerName;
	
	private double quota;
	private StockManager stockManager;
	
	private boolean ordersLocker;
	
	protected void setup()
	{
		try{
			
			expert=this;
			stockDao=new StockDao();
			yahooFinances= new YahooFinance(dir_1, subDir_1, subDir_2);
			
			managedStock=null;
			managedStockDao=new ManagedStockDao();
			
			stocksMap = new HashMap<Stock, Strategy>();
			ordensToBuyOrSell=new HashMap<Stock, String>();
			
			stockManager = new StockManager();
			
			ordersLocker=false;
			
			    
			DFAgentDescription dfd =new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("Hi, I'm live , my name is "+this.getLocalName());
			
			//Conversations
			addBehaviour(new CyclicBehaviour(expert) 
			{
				@Override
				public void action() {
				
					ACLMessage msg = myAgent.receive();
					
					if(msg!=null)
					{
						try {
							
							
							switch (msg.getPerformative())
							{
							case ACLMessage.INFORM:
							{
								
								if(msg.getConversationId()==ConversationsID.INIT_WORK)
								{
									ArrayList<Stock> temp=(ArrayList<Stock>)msg.getContentObject();
									
									if(temp!=null)
									{
										expert.stockList= new ArrayList<Stock>();	
										for(Stock s : temp)
										{
											s.setCandleSticks(expert.stockDao.getStockPrices_last40(s.getCodeName()));
											expert.stockList.add(s);
										}
										
										if(stockList.size()>0)
										{
											System.out.println("Expert :"+expert.getLocalName()+" Manager sendMe these Stocks:");
											for(Stock s:stockList)
											{
												System.out.println(s.getCodeName()+" with "+s.getCandleSticks().size()+" prices");
											}
										}
									}
									
									expert.managerName=msg.getSender().getLocalName();
									
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_USER_NAME)
								{
									expert.userIdentifier=(String)msg.getContentObject();
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_FAKE)
								{
									
									System.out.println(expert.getLocalName()+" Strategy FAKE");
									Strategy mms=null;
									
									for(Stock s : expert.stockList)
									{
										
										mms=new Fake_Strategy();
										
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											
											mms.addValue(s.getCandleSticks().get(i).getClose());
										}
										stocksMap.put(s,mms);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_MME_13_21)
								{	
									System.out.println(expert.getLocalName()+" Strategy MME 13/21");
									Strategy mme=null;
									
									for(Stock s : expert.stockList)
									{
										
										mme=new MovingAvarangeExponentialStrategy(0, 0, 0);
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											mme.addValue(s.getCandleSticks().get(i).getClose());
										}
										stocksMap.put(s,mme);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
									
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_MMS_13_21)
								{
									
									System.out.println(expert.getLocalName()+" Strategy MMS 13/21");
									Strategy mms=null;
									
									for(Stock s : expert.stockList)
									{
										
										mms=new MovingAvarangeSimpleStrategy(13, 21);
										
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											
											mms.addValue(s.getCandleSticks().get(i).getClose());
										}
										stocksMap.put(s,mms);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_MMS_21_34)
								{
									System.out.println(expert.getLocalName()+" Strategy MMS 21/34");
									Strategy mms=null;
									
									for(Stock s : expert.stockList)
									{
										
										mms=new MovingAvarangeSimpleStrategy(21, 34);
										
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											
											mms.addValue(s.getCandleSticks().get(i).getClose());
										}
										stocksMap.put(s,mms);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
									
									
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_MME_21_34)
								{
									System.out.println(expert.getLocalName()+" Strategy MME 21/34");
									
									Strategy mms=null;
									
									for(Stock s : expert.stockList)
									{
										
										mms=new MovingAvarangeExponentialStrategy(0,21, 34);
										
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											
											mms.addValue(s.getCandleSticks().get(i).getClose());
										}
										stocksMap.put(s,mms);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_BEARISH_ENGULFING_BULLSH_ENGULF)
								{
									System.out.println(expert.getLocalName()+" Strategy Bearish engulfing bullsh engulf");
									
									Strategy BearBull=null;
									
									for(Stock s : expert.stockList)
									{
										
										BearBull=new Bearish_Bullish_Strategy();
										
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											
											BearBull.addCandleStick(s.getCandleSticks().get(i));
										}
										stocksMap.put(s,BearBull);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
									
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_DARK_CLOUD_BULLISH_ENGULF)
								{
									System.out.println(expert.getLocalName()+" Strategy dark cloud bullish engulf");
									Strategy DarkBull=null;
									
									for(Stock s : expert.stockList)
									{
										
										DarkBull=new Bearish_Bullish_Strategy();
										
										for(int i=0;i<s.getCandleSticks().size();i++)
										{
											
											DarkBull.addCandleStick(s.getCandleSticks().get(i));
										}
										stocksMap.put(s,DarkBull);
												
									}
									try 
									{
										DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
								
									{
										e.printStackTrace();
									}
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_REMOVE_STOCK)
								{
									
								}
								
							}break;
							
							case ACLMessage.AGREE:
							{ //TODO INCLUIR O ID PARA AUTORIZACAO DE VENDAS 
								
								if(msg.getConversationId()==ConversationsID.EXPERT_ORDER_BUY)
								{
									
//									String managerAnswer=msg.getContent();
//									while(managerAnswer.)
									 String managerAnswer=msg.getContent().toString();
									 int underScore=0;
									for(int i=0;i<managerAnswer.length();i++)
									{
										
										if ((managerAnswer.charAt(i)+"").equals("_"))
										{
											underScore=i;
										}
										
									}
									String codeName=managerAnswer.substring(0, underScore);
									String value = managerAnswer.substring(underScore+1, managerAnswer.length());
									
									expert.quota=Double.parseDouble(value);
									System.out.println(getLocalName()+ ": D� R$:"+expert.quota/expert.orderToApproveBuy.size()+" para cada acao");
									
									for(Stock s: expert.orderToApproveBuy)
									{
										
										
										if(s!=null)
										{
											int qtd=(int)(expert.quota/s.getCurrentCandleStick().getClose());
											System.out.println(getLocalName()+" comprando "+s.getCodeName());
											System.out.println(getLocalName()+" preco unitario R$: "+(s.getCurrentCandleStick().getClose()));
											System.out.println(getLocalName()+" Quantidade a comprar "+qtd);
											
											if(qtd>0 )
											{
												expert.stockManager.orderBuy(s,qtd);
											}
										}
										
										
										
									}
									
									expert.ordersLocker=true;
									
									expert.orderToApproveBuy.clear();
									
								}
								
								
							}break;
							
							case ACLMessage.REFUSE:
							{
								if(msg.getConversationId()==ConversationsID.EXPERT_ORDER_BUY)
								{
									System.out.println(this.getAgent().getLocalName()+": Manager dind't approved");
									
									expert.ordersLocker=false;
								}
								
							}break;

							default:
								break;
							}
	
								
						} catch (Exception e) {
						
							e.printStackTrace();
						}
					}else block();
					
				}
			});
			  
		
		}
        catch (Exception e) {
            System.out.println( "Saw exception in HostAgent: " + e );
            e.printStackTrace();
        }
	}

	protected void takeDown()
	{
		System.out.println(getLocalName()+" says: Bye");
		try
		{
			
			addBehaviour(new OneShotBehaviour() 
			{
				
	
				private static final long serialVersionUID = 1L;

				@Override
				public void action() 
				{
					
					ACLMessage message=new ACLMessage(ACLMessage.INFORM);
					message.addReceiver(new AID(managerName, AID.ISLOCALNAME));
					message.setConversationId(ConversationsID.DEAD_EXPERT);

					myAgent.send(message);
					
				}
			});
			
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.deregister(this, dfd);

		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private CandleStick requestStocksPrices_FAKE(Stock stock)
	{
		System.out.println("Fake requestPrices");
		return new CandleStick(10, 10, 10, 10, 100, new Date());
	}
	private CandleStick requestStocksPrices(Stock stock)
	{
		
		CandleStick candleStick=null;
		try
		{
			 
			 if(expert.yahooFinances.storeCsvCurrentPriceStock(stock.getCodeName()))
			 {
				 candleStick= expert.yahooFinances.getCurrentValue(stock.getCodeName());
			 }

			// candleStick= expert.yahooFinances.getCurrentValue(stock.getCodeName());
		if(!(candleStick.getDate().getTime()
				==
				stock.getCandleSticks().get(stock.getCandleSticks().size()-1).getDate().getTime()
				)
//				&&
//				candleStick!=null
			)
		{
			
			return candleStick;
			
		}else return null;	
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	private void requestRoutine(Date startDate,int periodicity,final int testInterval)
	{
		final long periodicDailyInterval= 24*60*60*1000;
		final long periodicWeeklyInterval=7*24*60*60*1000;;
		final long periodicMothlyInterval=4*7*24*60*60*1000;
		
		final long periodicInteval;
		
		switch (periodicity) {
		case 0:
			periodicInteval=periodicDailyInterval;
			break;
		case 1:
			periodicInteval=periodicWeeklyInterval;
			break;
			
		case 2:
			periodicInteval=periodicMothlyInterval;
			break;

		default:
			periodicInteval=0;
			break;
		}
		
		//inicia o trabalho requistando o preco corrente
		addBehaviour(new OneShotBehaviour(this) 
		{
			
			@Override
			public void action() 
			{
				try 
				{
					Strategy strategy=null;
					CandleStick current=null;
					Stock stockTemp=null;
					
					for(Entry<Stock, Strategy>e:expert.stocksMap.entrySet())
					{
						
						stockTemp=e.getKey();
						
						System.out.println(getLocalName()+ ": Request Current Value of "+e.getKey().getCodeName());
						
						//TODO
						
							current=expert.requestStocksPrices_FAKE(e.getKey());
						
						//	current=expert.requestStocksPrices(e.getKey());
						//TODO
						System.out.println("Current = "+current);
						if(current!=null)
						{
							strategy=e.getValue();
							strategy.addValue(current.getClose());
							
						//TODO apagar isso
							System.out.println(e.getKey().getCodeName()+" Order  "+strategy.makeOrder());
							//System.out.println(getLocalName()+": Add new Candle: "+stockTemp.addCurrentCandleStick(current));
							
							
							//Armazeno as ordens para pedir autorizacao ao manager 
							if(expert.ordensToBuyOrSell.get(e.getKey())!=null && !strategy.makeOrder().equalsIgnoreCase("nothing") && !strategy.makeOrder().equalsIgnoreCase(null))
							{
								expert.ordensToBuyOrSell.remove(e.getKey());
								expert.ordensToBuyOrSell.put(e.getKey(), strategy.makeOrder());
								
								//TODO
								System.out.println("pedir para vender essa acao "+e.getKey());
								
							}else
							{
								//TODO acredito q essa linha nao seja necessaria 
								//expert.ordensToBuyOrSell.put(stockTemp, strategy.makeOrder());
							}
										
							expert.stocksMap.remove(e);
							expert.stocksMap.put(stockTemp, strategy);
						}
						
					}
					
					//TODO apagar esses prints 
					//Se existir oportunidades de compra ou venda pede autorizacao pro manager 
					//System.out.println(getLocalName()+ "vou pedir autorizacao.. achei "+expert.ordensToBuyOrSell.size()+" oportunidades ");
					//System.out.println(getLocalName()+ " meu gerente eh o "+expert.managerName);
					
					if(expert.ordensToBuyOrSell.size()>0)
					{
						expert.ordersToBuyOrSell();
						
						//TODO
						System.out.println("Existem acoes para vender ou comprar ");
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
			}
		});
		
		//Configura a rotina de requisicao de valores 
		addBehaviour(new WakerBehaviour(this,startDate)
		{
			private static final long serialVersionUID = 1L;
			protected void onWake()
			{
				//colocar periodicInteval 
				addBehaviour(new TickerBehaviour(expert, testInterval) 
				{
					
					private static final long serialVersionUID = 1L;

					@Override
					protected void onTick()
					{
						try 
						{
							Strategy strategy=null;
							CandleStick current=null;
							Stock stockTemp=null;
							
							
							for(Entry<Stock, Strategy>e:expert.stocksMap.entrySet())
							{
								
								stockTemp=e.getKey();
								
								System.out.println(getLocalName()+ ": Request Current Value of "+e.getKey().getCodeName());
								
								current=expert.requestStocksPrices(e.getKey());
								
								if(current!=null)
								{
									strategy=e.getValue();
									strategy.addValue(current.getClose());
									
									//TODO apagar isso 
									System.out.println(e.getKey().getCodeName()+" Order  "+strategy.makeOrder());
									//System.out.println(getLocalName()+": Add new Candle: "+stockTemp.addCurrentCandleStick(current));
									
									
									//Armazeno as ordens para pedir autorizacao ao manager 
									if(expert.ordensToBuyOrSell.get(e.getKey())!=null && !strategy.makeOrder().equalsIgnoreCase("nothing"))
									{
										expert.ordensToBuyOrSell.remove(e.getKey());
										expert.ordensToBuyOrSell.put(e.getKey(), strategy.makeOrder());
										
									}else
									{
										expert.ordensToBuyOrSell.put(stockTemp, strategy.makeOrder());
									}
												
									expert.stocksMap.remove(e);
									expert.stocksMap.put(stockTemp, strategy);
								}
								
							}
							//Se existir oportunidades de compra ou venda pede autorizacao pro manager 
							if(expert.ordensToBuyOrSell.size()>0)
							{
								expert.ordersToBuyOrSell();
							}
							
							
						}catch(Exception e)
						{
							e.printStackTrace();
						}
						
						
					}
				});
			}
			
		});
		
	}
	
	//Rotina de solicitacao de autorizacao de compra e vendas 
	//Ver mensagens do tipo proposta 
	private void ordersToBuyOrSell()
	{
		ManagedStock managedStock=null;
		
		expert.orderToApproveBuy= new ArrayList<Stock>();
		expert.orderToApproveSell= new ArrayList<Stock>();
		
		//TODO apagar eeses prints 
		
//		System.out.println("Ordens "+expert.ordensToBuyOrSell.size());
//		System.out.println(expert.ordensToBuyOrSell);
//		
		if( expert.ordensToBuyOrSell.size()>0)
		{	
			for(Entry<Stock, String>e:expert.ordensToBuyOrSell.entrySet())
			{
				managedStock=expert.managedStockDao.getManagedStock(e.getKey().getCodeName(),expert.userIdentifier); 
				
				
				try
				{
					
				if(managedStock==null)
				{
					if( e.getValue().equalsIgnoreCase("Buy"))
					{
						  //Autorizacao de compra 
						Stock stockTemp=e.getKey();
						stockTemp.setSuggestion(ConversationsID.BUY_REQUEST);
						
						expert.orderToApproveBuy.add(stockTemp);
						
						
					}else 
						if(e.getValue().equalsIgnoreCase("Sell"))
						{
							//Autorizacao de venda 
							
							Stock stockTemp=e.getKey();
							stockTemp.setSuggestion(ConversationsID.SELL_REQUEST);
							
							expert.orderToApproveSell.add(stockTemp);
							
						}
				}else
				{
					if(managedStock!=null && managedStock.getBuyed()==null && e.getValue().equalsIgnoreCase("Buy"))
					{
						  //Autorizacao de compra 
						Stock stockTemp=e.getKey();
						stockTemp.setSuggestion(ConversationsID.BUY_REQUEST);
						
						expert.orderToApproveBuy.add(stockTemp);
						
					}
					
					
				}
				
				}catch(Exception error)
				{
					//error.printStackTrace();
				}

			}//Fim for
		}
		
		if(expert.orderToApproveBuy.size()>0 && !expert.ordersLocker)
		{
			System.out.println(getLocalName()+" :Pedir autorizacao para  comprar "+expert.orderToApproveBuy.size()+" Acoes");
			System.out.println(getLocalName()+" :Comprar  "+expert.orderToApproveBuy.size()+" Acoes");
		}
		
		
		//TODO mudar paramentros perfomative
		if(expert.orderToApproveBuy.size()>0 && !expert.ordersLocker)
		{
			addBehaviour( new OneShotBehaviour(expert)
			{	
				@Override
				public void action()
				{
					
					try 
					{
						ACLMessage msg= new ACLMessage(ACLMessage.PROPOSE);
						msg.setConversationId(ConversationsID.EXPERT_ORDER_BUY);
						msg.addReceiver(new AID(expert.managerName, AID.ISLOCALNAME));
						msg.setContentObject(expert.orderToApproveBuy);
						
						myAgent.send(msg);
						
					} catch (Exception e1) 
						{
						
							e1.printStackTrace();
						}
					
					
				}
			});
			
		}
		
		//TODO Melhorar esse esquema 
		if(expert.orderToApproveSell.size()>0)
		{

			addBehaviour(new OneShotBehaviour(expert)
			{
				
				@Override
				public void action()
				{
					try 
					{
						for(Stock stock: expert.orderToApproveSell)
						{
							if(expert.stockManager.stockBougth(stock))
							{
								if(stock!=null)expert.stockManager.orderSell(stock);
								double profitValue;
								ACLMessage msg= new ACLMessage(ACLMessage.PROPOSE);
								msg.setConversationId(ConversationsID.EXPERT_ORDER_SELL);
								msg.addReceiver(new AID(expert.managerName, AID.ISLOCALNAME));
								
								profitValue=expert.stockManager.getProfitValue(stock);
								msg.setContent(""+profitValue);
								
								expert.stockManager.removeManagedStock(stock);
								myAgent.send(msg);
								
								
							}
						}
						
						//Caso tenha liquidado tudo, volta a solicitar dinheiro pra comprar 
						
						if(expert.stockManager.listManagedStock.size()==0) expert.ordersLocker=false;
						
					} catch (Exception e1) 
						{
						
							e1.printStackTrace();
						}
					
					
				}
			});
		}
		
	}
	
	
private class StockManager
{
		private StockDao stockDao;
		private ManagedStockDao managedStockDao;
		
		
		private Map<String, ManagedStock>listManagedStock;
		private double profitValue; 
	    private double profitPercent;
		
		public StockManager()
		{
			this.setStockDao(new StockDao());
			this.setManagedStockDao(new ManagedStockDao());
			this.listManagedStock= new HashMap<String, ManagedStock>();
		}
		public void orderBuy(Stock stock,int qtdStocksBought)
		{
			try
			{
				this.profitPercent=0;
				this.profitValue=0;
				
				ManagedStock managedStock= new ManagedStock();
				
				managedStock.setBuyed(stock.getCurrentCandleStick());
				managedStock.setCodeName(stock.getCodeName());
				managedStock.setUserIdentifier(userIdentifier);
				managedStock.setQtdStocksBought(qtdStocksBought);
				managedStock.setSector(stock.getSector());
				
				this.getManagedStockDao().insertManagedStock(managedStock);
				
				this.listManagedStock= new HashMap<String, ManagedStock>();
				this.listManagedStock.put(stock.getCodeName(), managedStock);
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		public void orderSell(Stock stock)
		{
			ManagedStock managedStockStored=this.managedStockDao.getManagedStock(stock.getCodeName(), userIdentifier);
			double profitPercent=0;
			double profitValue=0;
			
			try
			{
				if(managedStockStored!=null)
				{
					profitValue=stock.getCurrentCandleStick().getClose()- managedStockStored.getBuyed().getClose();
					profitPercent=stock.getCurrentCandleStick().getClose()/profitValue;
					
					ManagedStock managedStock_temp=this.listManagedStock.get(stock.getCodeName());
					managedStock_temp.setProfitPercent(managedStock_temp.getProfitPercent() + profitPercent);
					managedStock_temp.setProfitValue(managedStock_temp.getProfitValue()+profitValue);
					
					this.listManagedStock.put(stock.getCodeName(), managedStock_temp);
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		}
		public void removeManagedStock(Stock stock)
		{
			this.listManagedStock.remove(stock.getCodeName());
		}
		
		public void clearListManagedStockList()
		{
			this.listManagedStock.clear();
		}
		
		public boolean stockBougth(Stock stockToVerify)
		{
			ManagedStock managedStockStored=this.managedStockDao.getManagedStock(stockToVerify.getCodeName(), userIdentifier);
			
			if(managedStockStored!=null)
				return true;
				else return false;
		}
		
		
		public StockDao getStockDao() {
			return stockDao;
		}
		public void setStockDao(StockDao stockDao) {
			this.stockDao = stockDao;
		}
		
		public ManagedStockDao getManagedStockDao() {
			return managedStockDao;
		}
		public void setManagedStockDao(ManagedStockDao managedStockDao) {
			this.managedStockDao = managedStockDao;
		}
		public double getProfitValue(Stock stock) {
			
			return this.listManagedStock.get(stock.getCodeName()).getProfitValue();
		}
		
		public double getProfitPercent(Stock stock) {
			return this.listManagedStock.get(stock.getCodeName()).getProfitPercent();
		}
		
		
	}
	

}
