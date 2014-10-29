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
	
	private String dir_1="/Users/alissonnunes/Desktop";
	private String subDir_1="/TCC2";
	private String subDir_2="/Ativos";
	private String sectorsCsvFilePath="/Users/alissonnunes/Desktop/Setores";

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
									
									expert.managerName=msg.getSender().getLocalName();
									
								}
								if(msg.getConversationId()==ConversationsID.EXPERT_USER_NAME)
								{
									expert.userIdentifier=(String)msg.getContentObject();
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
								
								if(msg.getConversationId()==ConversationsID.EXPERT_ORDER_BUY)
								{
									
									System.out.println(getLocalName()+ ": "+msg.getSender().getLocalName()+" Enviou R$ "+msg.getContent());
									expert.quota=Double.parseDouble(msg.getContent().toString());
									System.out.println(getLocalName()+ ": D‡ R$:"+expert.quota/expert.orderToApproveBuy.size()+" para cada acao");
									
									for(Stock s: expert.orderToApproveBuy)
									{
										if(s!=null)
										{
											int qtd=(int)(expert.quota/s.getCurrentCandleStick().getClose());
											if(qtd>0 )
											{
												expert.stockManager.orderBuy(s,qtd);
											}
										}
										
										
									}
									expert.orderToApproveBuy.clear();
									
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
						
						System.out.println(getLocalName()+ ": Request Current Value of "+e.getKey().getCodeName());
						
						current=expert.requestStocksPrices(e.getKey());
						if(current!=null)
						{
							strategy=e.getValue();
							strategy.addValue(current.getClose());
							
							//TODO apagar isso
							System.out.println(e.getKey().getCodeName()+" Order  "+strategy.makeOrder());
						//	System.out.println(getLocalName()+": Add new Candle: "+stockTemp.addCurrentCandleStick(current));
							
							
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
					
					//TODO apagar esses prints 
					//Se existir oportunidades de compra ou venda pede autorizacao pro manager 
					//System.out.println(getLocalName()+ "vou pedir autorizacao.. achei "+expert.ordensToBuyOrSell.size()+" oportunidades ");
					//System.out.println(getLocalName()+ " meu gerente eh o "+expert.managerName);
					
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
					if( e.getValue().equalsIgnoreCase("buy"))
					{
						  //Autorizacao de compra 

						expert.orderToApproveBuy.add(e.getKey());
						
						
					}else 
						if(e.getValue().equalsIgnoreCase("sell"))
						{
							//Autorizacao de venda 
							
							
							expert.orderToApproveSell.add(e.getKey());
							
						}
				}else
				{
					if(managedStock!=null && managedStock.getBuyed()==null && e.getValue().equalsIgnoreCase("buy"))
					{
						  //Autorizacao de compra 
						expert.orderToApproveBuy.add(e.getKey());
						
					}
				}
				
				}catch(Exception error)
				{
					//error.printStackTrace();
				}

			}//Fim for
		}
		
		System.out.println(getLocalName()+" :Pedir autorizacao para  comprar "+expert.orderToApproveBuy.size()+" Acoes");
		System.out.println(getLocalName()+" :Vender  "+expert.orderToApproveBuy.size()+" Acoes");
		
		
		if(expert.orderToApproveBuy.size()>0)
		{
			addBehaviour( new OneShotBehaviour(expert)
			{	
				@Override
				public void action()
				{
					
					try 
					{
						ACLMessage msg= new ACLMessage(ACLMessage.INFORM);
						msg.setConversationId(ConversationsID.EXPERT_ORDER_BUY);
						msg.addReceiver(new AID(expert.managerName, AID.ISLOCALNAME));
						
							myAgent.send(msg);
						
					} catch (Exception e1) 
						{
						
							e1.printStackTrace();
						}
					
					
				}
			});
			
		}
		if(expert.orderToApproveSell.size()>0)
		{
			
			for(Stock stock: expert.orderToApproveSell)
			{
				if(stock!=null)expert.stockManager.orderSell(stock);
			}
			
			addBehaviour(new OneShotBehaviour(expert)
			{
				
				@Override
				public void action()
				{
					try 
					{
						double profitValue;
						ACLMessage msg= new ACLMessage(ACLMessage.INFORM);
						msg.setConversationId(ConversationsID.EXPERT_ORDER_SELL);
						msg.addReceiver(new AID(expert.managerName, AID.ISLOCALNAME));
						
						profitValue=expert.stockManager.getProfitValue();
						msg.setContent(""+profitValue);
						
							myAgent.send(msg);
						
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
		private ManagedStock managedStock;
		private ManagedStockDao managedStockDao;
		
		 private double profitValue;
		 private double profitPercent;
		
		public StockManager()
		{
			this.setStockDao(new StockDao());
			this.setManagedStockDao(new ManagedStockDao());
		}
		public void orderBuy(Stock stock,int qtdStocksBought)
		{
			try
			{
				this.setProfitPercent(0);
				this.setProfitValue(0);
				
				this.managedStock= new ManagedStock();
				this.managedStock.setBuyed(stock.getCurrentCandleStick());
				this.managedStock.setCodeName(stock.getCodeName());
				this.managedStock.setUserIdentifier(userIdentifier);
				this.managedStock.setQtdStocksBought(qtdStocksBought);
				this.managedStock.setSector(stock.getSector());
				
				this.getManagedStockDao().insertManagedStock(getManagedStock());
				
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
			
			if(managedStockStored!=null)
			{
				profitValue=stock.getCurrentCandleStick().getClose()- managedStockStored.getBuyed().getClose();
				profitPercent=stock.getCurrentCandleStick().getClose()/profitValue;
				
				this.setProfitPercent(this.getProfitPercent() + profitPercent);
				this.setProfitValue(this.getProfitValue() + profitValue);
			}
			
			this.managedStock= new ManagedStock();
			this.managedStock.setSelled(stock.getCurrentCandleStick());
			this.managedStock.setCodeName(stock.getCodeName());
			this.managedStock.setUserIdentifier(userIdentifier);
			this.managedStock.setProfitPercent(profitPercent);
			this.managedStock.setProfitValue(profitValue);
			
			
		}
		
		
		public StockDao getStockDao() {
			return stockDao;
		}
		public void setStockDao(StockDao stockDao) {
			this.stockDao = stockDao;
		}
		public ManagedStock getManagedStock() {
			return managedStock;
		}
		public void setManagedStock(ManagedStock managedStock) {
			this.managedStock = managedStock;
		}
		public ManagedStockDao getManagedStockDao() {
			return managedStockDao;
		}
		public void setManagedStockDao(ManagedStockDao managedStockDao) {
			this.managedStockDao = managedStockDao;
		}
		public double getProfitValue() {
			return profitValue;
		}
		public void setProfitValue(double profitValue) {
			this.profitValue = profitValue;
		}
		public double getProfitPercent() {
			return profitPercent;
		}
		public void setProfitPercent(double profitPercent) {
			this.profitPercent = profitPercent;
		}
		
	}
	

}
