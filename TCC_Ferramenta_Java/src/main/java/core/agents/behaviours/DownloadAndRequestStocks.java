package core.agents.behaviours;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import core.agents.util.StocksInMemory;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;
import suport.financial.wallet.Stock;
import suport.statistical.Statistical;
import suport.util.InfoConversations;
import suport.util.database.mongoDB.dao.StockDao;
import suport.util.requests.YahooFinance;

public class DownloadAndRequestStocks implements ProcedureBehaviour {
	private SequentialBehaviour sequentialBehaviour;
	private OneShotBehaviour downloadSocks;
	private OneShotBehaviour calculeStatistics;
	private TickerBehaviour pediodical;
	
	private InfoConversations info;
	private Agent agent;
	
	private String dir_1 = "/Users/ramon/Desktop";
	private String subDir_1 = "/TCC2";
	private String subDir_2 = "/Ativos";
	private String sectorsCsvFilePath = "/Users/ramon/Dropbox/UnB/TCC/workspace/java/Setores";
	private ArrayList<Stock> stockList = null;
	private StockDao stockDao = null;
	private Statistical statistical = null;
	private static final int CORAJOSO = 0;
	private static final int MODERADO = 1;
	private static final int CONSERVADOR = 2;
	private StocksInMemory simulationsData;
	private boolean isSimulation;
	private boolean conversations = false;
	
	private ACLMessage msg=null;
	private DownloadAndRequestStocks(){}
	
	public DownloadAndRequestStocks(Agent agent)
	{
		this.agent =agent;
		stockDao = new StockDao();
		statistical = new Statistical();
		
		simulationsData = StocksInMemory.getInstance();
		isSimulation=true;
		
		this.sequentialBehaviour= new SequentialBehaviour(agent)
		{
			
			private static final long serialVersionUID = 1L;

			public int onEnd()
			{
				System.out.println("Comportamento sequencial Concluido");
			
				return 0;
			}
		};
		
		buildBehaviour();

	}
	public void start()
	{
		
		this.sequentialBehaviour.addSubBehaviour(this.downloadSocks);
		this.sequentialBehaviour.addSubBehaviour(this.pediodical);
		
		agent.addBehaviour(sequentialBehaviour);
	}

	public Behaviour getBehaviour() {
		
		this.sequentialBehaviour.addSubBehaviour(this.downloadSocks);
		this.sequentialBehaviour.addSubBehaviour(this.pediodical);
		
		
		return this.sequentialBehaviour;
	}
	private void buildBehaviour()
	{
		 final Date date;
		 final long dailyInterval;
		this.downloadSocks=new OneShotBehaviour(agent) {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				try {
					File file = new File(dir_1 + subDir_1+ subDir_2);
					if (file.isDirectory()) 
					{
						if (file.listFiles().length > 2)//TODO
					   {
//							hunter.log.debug("Arquivos CSV ja foram baixados");
							System.out.println("Arquivos CSV ja foram baixados");
							loadDataBase();
						}
						else 
						{
							//hunter.downloadCsvFiles(hunter.dir_1,hunter.subDir_1, hunter.subDir_2,hunter.sectorsCsvFilePath);
						}
						if (stockDao.getStocksPricesCount() == 0) 
						{//TODO
//							hunter.log.debug("Carregar dados no Banco");
							System.out.println(":Carregar dados no Banco");
							loadDataBase();
//							hunter.log.debug("Banco carregado com "+hunter.stockDao.getStocksPricesCount()+"cotacoes");
							
						} else 
						{
							//TODO
							
//							hunter.log.debug("Banco ja carregado com "+ hunter.stockDao.getStocksPricesCount()+ " Cotacoes");
							System.out.println(":Banco jah carregado com "+ stockDao.getStocksPricesCount()+ " Cotacoes");
							
							if(!isSimulation)stockList = stockDao.getAllStocksWithPrices();
							else 
							{
								//hunter.stockList=hunter.stockDao.getAllStocksWithPricesBetweenInterval(hunter.simulationSetup.getStartDate(), hunter.simulationSetup.getStartDate());
								
								stockList=simulationsData.getStockList();
							
							}
							
//							hunter.log.debug("Iniciando procedimento de calculo de valores estatisticos");
							
							System.out.println(":Iniciando procedimento de calculo de valores estatisticos");
							if(!isSimulation)downloadCurrentCsvFiles(dir_1,subDir_1, subDir_2,sectorsCsvFilePath);
							else
								{
									calculateStatistical();
									
									PlatformController container = agent.getContainerController();
									
									try {//TODO
										//log.info("Criando Gertor:"+nameAgentManager);
										// Xms128m
										Object[] argument;
										argument = new Object[1];
										argument[0] = "Xms512m";

										AgentController agentController = container.createNewAgent(
												"Simulator", "core.agents.util.StockAgent", argument);
										agentController.start();

									} catch (Exception e) {
										e.printStackTrace();//TODO
										//log.error("Msg:"+e.getMessage()+"Causa:"+e.getCause());
									}
								}
							
							
						}
					} else
					{ //TODO
					//	hunter.log.debug("Iniciando procedimento de downloand de csv com cotacoes");
						conversations = false;
						System.out.println(":Baixar CSV");
						downloadCsvFiles(dir_1,subDir_1, subDir_2,sectorsCsvFilePath);
					}
				} catch (Exception e) { //TODO
					e.printStackTrace();
//					log.error("Msg:"+e.getMessage()+"Causa:"+e.getCause());
				}
			}
		};
	    
		new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.US);
		date = new Date();
		date.setMinutes(date.getMinutes() + 15);
		dailyInterval = 1000 * 60 * 60 * 24;
		
		this.pediodical=new TickerBehaviour(agent, dailyInterval) 
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				downloadCurrentCsvFiles(dir_1,subDir_1, subDir_2,sectorsCsvFilePath);
			}
		};
	}

	
	private void calculateStatistical() 
	{
		
//		this.conversations = false;
		for (Stock s : this.stockList) 
		{//TODO 
			
			//log.debug("Calculando valores estatiscos para "+ s.getCodeName());
			System.out.println("Calculando valores estatiscos para "+ s.getCodeName());
			s.setAvarangeReturn_15(this.statistical.averangeReturn_15(s.getCandleSticks()));
			s.setAvarangeReturn_30(this.statistical.averangeReturn_30(s.getCandleSticks()));
			s.setStandardDeviation_15(this.statistical.calculeStandardDeviation_15(s.getCandleSticks()));
			s.setStandardDeviation_30(this.statistical.calculeStandardDeviation_30(s.getCandleSticks()));
			s.setVariance_15(this.statistical.calculeVariance_15(s.getCandleSticks()));
			s.setVariance_30(this.statistical.calculeVariance_30(s.getCandleSticks()));
			s.setVarianceCoefficient_15(this.statistical.calculeVariance_15(s.getCandleSticks()));
			s.setVarianceCoefficient_30(this.statistical.calculeVariance_30(s.getCandleSticks()));
			stockDao.updateStock(s);
		}

		this.conversations = true;//TODO
		//log.debug("Calculos concluidos! ");
		
		System.out.println(": Calculos concuidos!");
	}
	private void updateDataBase() {
		YahooFinance yahooFinance = new YahooFinance(this.dir_1, this.subDir_1,this.subDir_2);
		try {//TODO
			//hunter.log.debug("updateDataBase()  stocks:");
			ArrayList<Stock> stockTemp = this.stockList;
			System.out.println(":Alualizar "+stockTemp.size()+" Stocks");
			for (int i = 1; i < stockTemp.size(); i++) 
			{
				try {//TODO
					//log.debug(stockTemp.get(i).getCodeName()+ " Atualizado :"+ stockTemp.get(i).addCurrentCandleStick(yahooFinance.getCurrentValue(stockTemp.get(i).getCodeName())));
					System.out.println(stockTemp.get(i).getCodeName()+ " Atualizado :"+ stockTemp.get(i).addCurrentCandleStick(yahooFinance.getCurrentValue(stockTemp.get(i).getCodeName())));
					if (stockTemp.get(i).getCandleSticks().size() > 0) {
					//	hunter.log.debug(stockTemp.get(i).getCodeName()+ " DB Atuazado? "+ this.stockDao.insertCurrentStock(stockTemp.get(i)));
						System.out.println(stockTemp.get(i).getCodeName()+ " DB Atuazado? "+ this.stockDao.insertCurrentStock(stockTemp.get(i)));
					}
				} catch (Exception e) { 
					//log.error("Msg:"+e.getMessage()+" Causa:"+e.getCause());	
					e.printStackTrace();
				}
			}
			//hunter.log.debug("Atualizando dados estatisticos.");
			System.out.println(":Atualizando dados estatisticos.");
			calculateStatistical();
		} catch (Exception e) 
		{//TODO
			//log.error("Msg:"+e.getMessage()+"Causa:"+e.getCause());	
			e.printStackTrace();
		}
	}
	private void loadDataBase() {
		YahooFinance yahooFinance;
		this.stockList = new ArrayList<Stock>();
		int countStocks = 0;
		int countCandleSticks = 0;
		
		try
		{ 
			yahooFinance = new YahooFinance(this.dir_1, this.subDir_1,this.subDir_2);
			ArrayList<Stock> stocksFromCsv = yahooFinance.loadStocksFromCsv(this.sectorsCsvFilePath);
			System.out.println("Setores =:"+this.sectorsCsvFilePath);
			if(stocksFromCsv!=null)
			{
				for (Stock s : stocksFromCsv) 
				{
					s.setCandleSticks(yahooFinance.getHistoricalValue(s.getCodeName()));//TODO
					//log.debug(s.getCodeName() + " Carregado com "+ s.getCandleSticks().size() + " Valores");
					if (s.getCandleSticks().size() > 0) 
					{
						this.stockDao.storeHistoricalStockValue(s);
						countCandleSticks += s.getCandleSticks().size();
						countStocks++;
						this.stockList.add(s);
					}
				}//TODO
//				log.debug(countStocks + " Acoes persistidas com sucesso !");
//				log.debug( this.stockList.size() + "Acoes em memoria");
//				log.debug("Total de CandleSticks: " + countCandleSticks);
//				log.debug("Iniciando calculo de valores estatisticos para catalogar");
				
				calculateStatistical();
			}
		}catch (Exception e) {
			
		}
		
		
	}
	private void downloadCurrentCsvFiles(String dir_1, String subdir_1,
			String subdir_2, String sectorsPath) {// TODO LOG
		YahooFinance yahoo = new YahooFinance(dir_1, subdir_1, subdir_2);
		final long ti = System.currentTimeMillis();

		// Criando a lista a ser de acoes para baixar a cotacao
		final ArrayList<Stock> stockList = yahoo.loadStocksFromCsv(sectorsPath);
		/*
		 * A ultima thread vai mudar o atributo conversation para true para isso
		 * eh preciso ter um boolean para cada thread, para indicar que ela
		 * terminou de fazer o download.
		 */
		final boolean thread_finish[] = { false, false, false };
		// Thread 1
		final int id1 = 1;
		Runnable y1 = new YahooFinance(dir_1, subdir_1, subdir_2, stockList) {
			public void run() {
				while (true) {
					String codeName = null;
					synchronized (stockList) {
						if (stockList.size() != 0) {
							codeName = stockList.get(0).getCodeName();
							stockList.remove(0);

						} else {
							try {// TODO LOG
								System.out.println("Thread " + id1
										+ ": Concluido!");
								long t = System.currentTimeMillis();
								System.out.println("\t\t\tTempo total :"
										+ (t - ti));
								// verifica se eh a ultima a terminar o servico
								thread_finish[0] = true;
								if (thread_finish[1] && thread_finish[2]) {
									conversations = true;
									System.out
											.println("Thread 1:Download(Current) concluido");
									updateDataBase();
								}
								stockList.wait();
							} catch (InterruptedException e) {// TODO LOG
								e.printStackTrace();
							}
						}
					}
					try
					{
						if (codeName != null) 
						{// TODO LOG
							System.out.println("Baixando dados de : " + codeName);
							System.out.println(codeName + "_current :"
									+ storeCsvCurrentPriceStock(codeName));
							System.out.print("\n");
						}
					}catch (Exception e) 
					{
						
					}
					
				}
			}//TODO Dar um jeito nessas threads 
		};
		// Thread 2
		final int id2 = 2;
		Runnable y2 = new YahooFinance(dir_1, subdir_1, subdir_2, stockList) {
			public void run() {
				while (true) {
					String codeName = null;
					synchronized (stockList) {
						if (stockList.size() != 0) {
							codeName = stockList.get(0).getCodeName();
							stockList.remove(0);
						} else {
							try {
								System.out.println("Thread " + id2
										+ ": Concluido!");

								long t = System.currentTimeMillis();
								System.out.println("\t\t\tTempo total :"
										+ (t - ti));
								// verifica se eh a ultima a terminar o servico
								thread_finish[1] = true;
								if (thread_finish[0] && thread_finish[2]) {
									conversations = true;
									System.out
											.println("Thread 2:Download(Current) concluido");
									updateDataBase();
								}
								stockList.wait();
							} catch (InterruptedException e) {// TODO LOG
																// e.printStackTrace();
							}
						}
					}
					try
					{
						if (codeName != null) 
						{// TODO LOG
							System.out.println("Baixando dados de : " + codeName);
							System.out.println(codeName + "_current :"
									+ storeCsvCurrentPriceStock(codeName));
							System.out.print("\n");
						}
					}catch (Exception e) 
					{
						
					}
				}
			}
		};
		// Thread 3
		final int id3 = 3;
		Runnable y3 = new YahooFinance(dir_1, subdir_1, subdir_2, stockList) {
			public void run() {
				while (true) {
					String codeName = null;
					synchronized (stockList) {
						if (stockList.size() != 0) {
							codeName = stockList.get(0).getCodeName();
							stockList.remove(0);
						} else {
							try {
								System.out.println("Thread " + id3+ ": Concluido!");
								long t = System.currentTimeMillis();
								System.out.println("\t\t\tTempo total :"
										+ (t - ti));
								// verifica se eh a ultima a terminar o servico
								thread_finish[2] = true;
								if (thread_finish[1] && thread_finish[0]) {
									conversations = true;
									System.out.println("Thread 3:Download(Current) concluido");
									updateDataBase();
								}
								stockList.wait();
							} catch (InterruptedException e) {// TODO LOG
																// e.printStackTrace();
							}
						}
					}
					try
					{
						if (codeName != null) 
						{// TODO LOG
							System.out.println("Baixando dados de : " + codeName);
							System.out.println(codeName + "_current :"+ storeCsvCurrentPriceStock(codeName));
							System.out.print("\n");
						}
					}catch (Exception e) 
					{
						
					}
				}
			}
		};
		Thread t1 = new Thread(y1);
		Thread t2 = new Thread(y2);
		Thread t3 = new Thread(y3);
		t1.start();
		t2.start();
		t3.start();
	}

	/*
	 * Dir 1: "/Users/alissonnunes/Desktop"Sub dir 1: "/Ramon"Sub dir 2 "/Cruz"
	 * SectorsPath "/Users/alissonnunes/Desktop/Setores"
	 */
	private void downloadCsvFiles(String dir_1, String subdir_1,String subdir_2, String sectorsPath) 
	{
		YahooFinance yahoo = new YahooFinance(dir_1, subdir_1, subdir_2);
		final long ti = System.currentTimeMillis();//TODO
	//	hunter.log.debug("Iniciando contagem de tempo e disparando threads");
		
		// Criando a lista a ser de acoes para baixar a cotacao
		final ArrayList<Stock> stockList = yahoo.loadStocksFromCsv(sectorsPath);

		/*
		 * A ultima thread vai mudar o atributo conversation para true para isso
		 * eh preciso ter um boolean para cada thread, para indicar que ela
		 * terminou de fazer o download.
		 */
		final boolean thread_finish[] = { false, false, false };
		// Thread 1
		final int id1 = 1;
		Runnable y1 = new YahooFinance(dir_1, subdir_1, subdir_2, stockList) {
			public void run() {
				while (true) {
					String codeName = null;
					synchronized (stockList) {
						if (stockList.size() != 0) {
							codeName = stockList.get(0).getCodeName();
							stockList.remove(0);
						} else {
							try {//TODO
								//hunter.log.debug("Thread " + id1+ ": Concluido!");
								long t = System.currentTimeMillis();
								//hunter.log.debug("Tempo total :"+ (t - ti));
								// verifica se eh a ultima a terminar o servico
								thread_finish[0] = true;
								if (thread_finish[1] && thread_finish[2]) {
									conversations = true;
								//	hunter.log.debug("Thread 1:Download concluido. Iniciar Carga do Banco.");
									loadDataBase();
								}
								stockList.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();//TODO
							//	log.error("Msg:"+e.getMessage()+"Causa:"+e.getCause());
							}
						}
					}
					if (codeName != null) {//TODO
//						hunter.log.debug("Baixando dados de : " + codeName);
//						hunter.log.debug(codeName + "_historical :"+ storeCsvHistoricalPriceStock(codeName));
//						hunter.log.debug(codeName + "_current :"+ storeCsvCurrentPriceStock(codeName));
						try
						{
							System.out.println("Baixando dados de : " + codeName);
							storeCsvHistoricalPriceStock(codeName);
							storeCsvCurrentPriceStock(codeName);
						}catch (Exception e) {
							
						}
					}
				}
			}
		};
		// Thread 2
		final int id2 = 2;
		Runnable y2 = new YahooFinance(dir_1, subdir_1, subdir_2, stockList) {
			public void run() {
				while (true) {
					String codeName = null;
					synchronized (stockList) {
						if (stockList.size() != 0) 
						{//TODO
							//hunter.log.debug("Baixando dados de : "+ stockList.get(0).getCodeName());
							codeName = stockList.get(0).getCodeName();
							stockList.remove(0);
							
						} else {
							try {//TODO
							//	hunter.log.debug("Thread " + id2+ ": Concluido!");
								long t = System.currentTimeMillis();
							//	hunter.log.debug("tempo total :"+ (t - ti));
							
								// verifica se eh a ultima a terminar o servico
								thread_finish[1] = true;
								if (thread_finish[0] && thread_finish[2])
								{//TODO
									conversations = true;
								//	hunter.log.debug("Thread 2:Download concluido. Iniciar Carga do Banco");
									loadDataBase();
								}
								stockList.wait();

							} catch (InterruptedException e) 
							{//TODO
								//log.error("Msg:"+e.getMessage()+"Causa:"+e.getCause());						// e.printStackTrace();
							}
						}
					}
					if (codeName != null) {//TODO
//						hunter.log.debug("Baixando dados de : " + codeName);
//						hunter.log.debug(codeName + "_historical :"+ storeCsvHistoricalPriceStock(codeName));
//						hunter.log.debug(codeName + "_current :"+ storeCsvCurrentPriceStock(codeName));
						try
						{
							System.out.println("Baixando dados de : " + codeName);
							storeCsvHistoricalPriceStock(codeName);
							storeCsvCurrentPriceStock(codeName);
						}catch (Exception e) {
							
						}
					}
				}
			}
		};
		// Thread 3
		final int id3 = 3;
		Runnable y3 = new YahooFinance(dir_1, subdir_1, subdir_2, stockList) {
			public void run() {
				while (true) {
					String codeName = null;
					synchronized (stockList) { 
						if (stockList.size() != 0)
						{//TODO
//							hunter.log.debug("Baixando dados de : "+ stockList.get(0).getCodeName());
							
							codeName = stockList.get(0).getCodeName();
							
//							hunter.log.debug("Baixando dados de : "+ stockList.get(0).getCodeName());
							codeName = stockList.get(0).getCodeName();

							stockList.remove(0);
							
						} else {
							try {//TODO
//								hunter.log.debug("Thread " + id3+ ": Concluido!");
							
								long t = System.currentTimeMillis();
//								hunter.log.debug("Tempo total :"+ (t - ti));
								// verifica se eh a ultima a terminar o servico
								thread_finish[2] = true;
								if (thread_finish[1] && thread_finish[0]) 
								{
									conversations = true;
//									hunter.log.debug("Thread 3:Download concluido. Iniciando carga de banco.");
									loadDataBase();
								}
								stockList.wait();
							} catch (InterruptedException e) {
//								log.error("Msg:"+e.getMessage()+"Causa:"+e.getCause());
							}
						}
					}
					if (codeName != null) {//TODO
//						hunter.log.debug("Baixando dados de : " + codeName);
//						hunter.log.debug(codeName + "_historical :"+ storeCsvHistoricalPriceStock(codeName));
//						hunter.log.debug(codeName + "_current :"	+ storeCsvCurrentPriceStock(codeName));
//					
						try
						{
							System.out.println("Baixando dados de : " + codeName);
							storeCsvHistoricalPriceStock(codeName);
							storeCsvCurrentPriceStock(codeName);
						}catch (Exception e) {
							
						}
						
			
					}
				}
			}
		};
		//TODO
		//log.debug("Inicializando Threads.");
		Thread t1 = new Thread(y1);
		Thread t2 = new Thread(y2);
		Thread t3 = new Thread(y3);
		t1.start();
		t2.start();
		t3.start();
		
	}

	public void start(ACLMessage msg) {
		// TODO Auto-generated method stub
		
	}

	public void start(Object object) {
		// TODO Auto-generated method stub
		
	}
}
