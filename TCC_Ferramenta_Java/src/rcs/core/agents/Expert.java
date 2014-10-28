package rcs.core.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.strategy.MovingAvarangeExponentialStrategy;
import rcs.suport.financial.strategy.MovingAvarangeSimpleStrategy;
import rcs.suport.financial.strategy.Strategy;
import rcs.suport.financial.wallet.Stock;
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
	
	private String dir_1="/Users/alissonnunes/Desktop";
	private String subDir_1="/TCC2";
	private String subDir_2="/Ativos";
	private String sectorsCsvFilePath="/Users/alissonnunes/Desktop/Setores";

	private ManagedStock managedStock;
	private ManagedStockDao managedStockDao;
	private Map<Stock, Strategy> stocksMap;
	private Map<Stock,String> ordensToBuyOrSell; //Buy,Sell,Nothing
	
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
										//Date date = (Date)format.parse("10/26/2014 10:02pm");
										Date date = new Date();
										date.setMinutes(date.getMinutes()+1);
										expert.requestRoutine(date, 0, 60*1000);
										
									} catch (Exception e)
									{
										e.printStackTrace();
									}
									
								}if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_MMS_13_21)
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
									
								}if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_MME_21_34)
								{
									System.out.println(expert.getLocalName()+" Strategy MME 21/34");
								}
								
								if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_BEARISH_ENGULFING_BULLSH_ENGULF)
								{
									System.out.println(expert.getLocalName()+" Strategy Bearish engulfing bullsh engulf");
									
								}if(msg.getConversationId()==ConversationsID.EXPERT_STRATEGY_DARK_CLOUD_BULLISH_ENGULF)
								{
									System.out.println(expert.getLocalName()+" Strategy dark cloud bullish engulf");
								}
								
						} catch (UnreadableException e) {
						
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
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.deregister(this, dfd);
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
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
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		
		if(!(candleStick.getDate().getTime()
				==
				stock.getCandleSticks().get(stock.getCandleSticks().size()-1).getDate().getTime()))
		{
			
			return candleStick;
			
		}else return null;	
		
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
						System.out.println("Request Current Value of "+e.getKey().getCodeName());
						
						current=expert.requestStocksPrices(e.getKey());
						if(current!=null)
						{
							strategy=e.getValue();
							strategy.addValue(current.getClose());
							
							System.out.println(e.getKey().getCodeName()+" Order  "+strategy.makeOrder());
							System.out.println(" Add new Candle: "+stockTemp.addCurrentCandleStick(current));
							
							
							//Armazeno as ordens para pedir autorizacao ao manager 
							if(expert.ordensToBuyOrSell.get(e.getKey())!=null)
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
								System.out.println("Request Current Value of "+e.getKey().getCodeName());
								
								current=expert.requestStocksPrices(e.getKey());
								if(current!=null)
								{
									strategy=e.getValue();
									strategy.addValue(current.getClose());
									
									System.out.println(e.getKey().getCodeName()+" Order  "+strategy.makeOrder());	
									System.out.println(" Add new Candle: "+stockTemp.addCurrentCandleStick(current));
									
									//Armazeno as ordens para pedir autorizacao ao manager 
									if(expert.ordensToBuyOrSell.get(e.getKey())!=null)
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
							
							
						}catch(Exception e)
						{
							e.printStackTrace();
						}
						
						
					}
				});
			}
			
		});
		
	}
	
	
	
	

}
