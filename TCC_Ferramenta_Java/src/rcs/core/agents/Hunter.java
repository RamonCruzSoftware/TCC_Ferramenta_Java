package rcs.core.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.mongodb.util.MyAsserts.MyAssert;

import rcs.main.MainClass;
import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.statistical.Statistical;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.requests.YahooFinance;


public class Hunter extends Agent {
	
	private static final long serialVersionUID = 1L;
	private Hunter hunter;
	private boolean conversations=false;
	
	private ArrayList<Stock> stockList=null;
	private StockDao stockDao=null;
	private Statistical statistical=null;
	
	
	private String dir_1="/Users/alissonnunes/Desktop";
	private String subDir_1="/TCC2";
	private String subDir_2="/Ativos";
	private String sectorsCsvFilePath="/Users/alissonnunes/Desktop/Setores";
	
	protected void setup()
	{
		try
		{
			hunter=this;
			conversations=true;
			stockDao = new StockDao();
			statistical=new Statistical();
			
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			
			//Create an service in yellow pages
			ServiceDescription service=new ServiceDescription();
			service.setType("StockHunter");
			service.setName("Hunter");
			
			
			dfd.addServices(service);
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			initWork();
			communication(hunter);
			
		}catch(Exception e)
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
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

private void communication(Agent agent)
{
	addBehaviour(new CyclicBehaviour(agent) {
		
		private static final long serialVersionUID = 1L;
		private InfoConversations info;

		@Override
		public void action() 
		{
			try
			{
				ACLMessage messages= myAgent.receive();
				if(messages!=null &&conversations)
				{
					if(messages.getConversationId()==ConversationsID.STOCKS_SUGGESTIONS)
					{
						info=(InfoConversations)messages.getContentObject();
						
						switch (info.getUserProfile()) {
						case 0://corajoso
							info.setStockList(hunter.stockDao.getStockOrderByStandardDeviation_30(15, 30));
							break;
						case 1://moderado
							info.setStockList(hunter.stockDao.getStockOrderByStandardDeviation_30(5	, 10));
							break;
						case 2://conservador
							
							info.setStockList(hunter.stockDao.getStockOrderByStandardDeviation_30(0	, 6));
							break;

						default:
							break;
						}
						
						ACLMessage reply=messages.createReply();
						reply.setContentObject(info);
						myAgent.send(reply);
					}
					System.out.println(messages.getContent());
				}else block();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
		}
	});
}
/*
 *Dir 1: "/Users/alissonnunes/Desktop"
 *Sub dir 1: "/Ramon"
 *Sub dir 2 "/Cruz"
 *SectorsPath "/Users/alissonnunes/Desktop/Setores"
 */
private void downloadCsvFiles(String dir_1,String subdir_1,String subdir_2,String sectorsPath)
{
	
	 YahooFinance yahoo=new YahooFinance(dir_1,subdir_1,subdir_2);
	
	 final long ti=System.currentTimeMillis(); 
	 System.out.println("Iniciando contagem de tempo ");
	 
	 //Criando a lista a ser de acoes para baixar a cotacao 
	 final ArrayList<Stock> stockList = yahoo.loadStocksFromCsv(sectorsPath);

	/*
	 * A ultima thread vai mudar o atributo conversation para true
	 * para isso eh preciso ter um boolean para cada thread, para indicar 
	 * que ela terminou de fazer o download. 
	 * 
	 */
	
	 final boolean thread_finish[]={false,false,false};

	 //Thread 1
	 final int id1=1;
	 Runnable y1=new YahooFinance(dir_1,subdir_1,subdir_2,stockList)
	 {
		
		 public void run()
		 { 
			 while (true)
			 {
				 String codeName=null;
				 synchronized (stockList) 
				 { 
					if(stockList.size()!=0)
					{
						codeName=stockList.get(0).getCodeName();
						stockList.remove(0);
						
					}else 
					{
						try {
							System.out.println("Thread "+id1+": Concluido!");
							
							long t=System.currentTimeMillis();
							System.out.println("\t\t\tTempo total :"+(t-ti));
							
							//verifica se eh  a ultima a terminar o  servico
							thread_finish[0]=true;
							if(thread_finish[1]&&thread_finish[2])
							{
								conversations=true;
								System.out.println("Download concluido");
								loadDataBase();
							
							}
							
							stockList.wait();
							
						} catch (InterruptedException e) 
						{
							
							e.printStackTrace();
						}
					}
					
				 }
				 
				 if(codeName!=null)
				 {
					System.out.println("Baixando dados de : "+codeName); 
					System.out.println(codeName+"_historical :"+ storeCsvHistoricalPriceStock(codeName));
					System.out.println(codeName+"_current :"+storeCsvCurrentPriceStock(codeName));
					System.out.print("\n");
				 }
				 
			 }
			
		 }
	 };
	 //Thread 2
	 final int id2=2;
	 Runnable y2=new YahooFinance(dir_1,subdir_1,subdir_2,stockList)
	 {
		
		 public void run()
		 {
			while(true)
			{
				 String codeName=null;
				 synchronized (stockList) 
				 { 
					if(stockList.size()!=0)
					{
						codeName=stockList.get(0).getCodeName();
						stockList.remove(0);
							
					}else 
					{
						try {
							System.out.println("Thread "+id2+": Concluido!");
							
							long t=System.currentTimeMillis();
							System.out.println("\t\t\tTempo total :"+(t-ti));
							
							//verifica se eh  a ultima a terminar o  servico
							thread_finish[1]=true;
							if(thread_finish[0]&&thread_finish[2])
							{
								conversations=true;
								System.out.println("Download concluido");
								loadDataBase();
							}
							
							stockList.wait();
							
						} catch (InterruptedException e) {
							
							//e.printStackTrace();
						}
					}
					
				 }
				 
				 if(codeName!=null)
				 {
					 System.out.println("Baixando dados de : "+codeName); 
						System.out.println(codeName+"_historical :"+ storeCsvHistoricalPriceStock(codeName));
						System.out.println(codeName+"_current :"+storeCsvCurrentPriceStock(codeName));
						System.out.print("\n");
				 }
				 
			 }
			
		 }
	 };
	 
	 //Thread 3
	 
	 final int id3=3;
	 Runnable y3=new YahooFinance(dir_1,subdir_1,subdir_2,stockList)
	 {
		
		 public void run()
		 {
			 while (true)
			 {
				 String codeName=null;
				 synchronized (stockList) 
				 { 
					if(stockList.size()!=0)
					{
						codeName=stockList.get(0).getCodeName();
						stockList.remove(0);
						
						
						System.out.println("Baixando dados de : "+stockList.get(0).getCodeName()); 
						codeName=stockList.get(0).getCodeName();
						
						stockList.remove(0);
						
						System.out.println(storeCsvHistoricalPriceStock(codeName));
						System.out.println(storeCsvCurrentPriceStock(codeName));
						System.out.print("\n");
						
						
					}else 
					{
						try {
							System.out.println("Thread "+id3+": Concluido!");
							
							long t=System.currentTimeMillis();
							System.out.println("\t\t\tTempo total :"+(t-ti));
							
							//verifica se eh  a ultima a terminar o  servico
							thread_finish[2]=true;
							if(thread_finish[1]&&thread_finish[0])
							{
								conversations=true;
								System.out.println("Download concluido");
								loadDataBase();
							}
							
							stockList.wait();
							
						} catch (InterruptedException e) {
							
							//e.printStackTrace();
						}
					}
					
				 }
				 
				 if(codeName!=null)
				 {
					 System.out.println("Baixando dados de : "+codeName); 
						System.out.println(codeName+"_historical :"+ storeCsvHistoricalPriceStock(codeName));
						System.out.println(codeName+"_current :"+storeCsvCurrentPriceStock(codeName));
						System.out.print("\n");
				 }
				 
			 }
			
		 }
	 };
	 
	 
	 Thread t1=new Thread(y1); 
	 Thread t2=new Thread(y2);
	 Thread t3=new Thread(y3);
	 
	 t1.start();
	 t2.start();
	 t3.start();
}


//Esse metodo ainda nao funciona bem
//ele encontra as pastas vazias mas nao as apaga
private  void deleteEmptyFolders(String foldersMainPath)
{
	File folders=new File(foldersMainPath);
	StringBuilder deletedFolders=new StringBuilder();
	
	deletedFolders.append("Pastas deletadas por estarem vazias");
	deletedFolders.append("\n\n");
	
	ArrayList<File> emptyFolder=new ArrayList<File>();
	
	
	for(File f:folders.listFiles())
	{
	
		try{
			if(f.isDirectory() && f.listFiles().length==1)
	 		{
	 			for(File sf:f.listFiles())
	 			{
	 				if(sf.getName().equalsIgnoreCase(".DS_Store"))
	 				{
	 					deletedFolders.append("\t"+f.getName());
	 		 			deletedFolders.append("\n");
	 		 			emptyFolder.add(f);
	 		 			System.out.println("Deleted? "+f.delete());
	 		 			
	 				}
	 			}
	 			
	 		}
	 		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

	
	System.out.println(deletedFolders);
	
		
}
private void initWork()
{
	addBehaviour(new OneShotBehaviour(hunter)
	{
		
		
		@Override
		public void action() {
		
			try
			{
				
				File file=new File(hunter.dir_1+hunter.subDir_1+hunter.subDir_2);
				
				System.out.println(" is Directory :"+file.isDirectory());
				
				if(file.isDirectory())
				{
					if(file.listFiles().length>2)
						System.out.println("Alguem ja baixou os arquivos CSV.. nao precisa baixar");
					hunter.stockList=hunter.stockDao.getAllStocksPrices();
					System.out.println("OK ja existem "+hunter.stockList.size()+" no banco de dados");
					System.out.println("Vou calcular os valores estatisticos para catalogar");
					calculateStatistical();
				}else 
				{
					System.out.println("Ainda nao baixaram os arquivos CSV, vou fazer isso.");
					hunter.conversations=false;
					hunter.downloadCsvFiles(hunter.dir_1,hunter.subDir_1,hunter.subDir_2, hunter.sectorsCsvFilePath);
				}
					
								

			}catch(Exception e)
			{
				e.printStackTrace();
			
			}
			
			
			
				
		}
	});
}
private  void loadDataBase()
{
	YahooFinance yahooFinance=new YahooFinance(this.dir_1, this.subDir_1, this.subDir_2);
	
	this.stockList=new ArrayList<Stock>();
	
	int countStocks=0;
	int countCandleSticks=0;
	
	
	for(Stock s: yahooFinance.loadStocksFromCsv(this.sectorsCsvFilePath))
	{
		s.setCandleSticks(yahooFinance.getHistoricalValue(s.getCodeName()));
		System.out.println(s.getCodeName()+ " Carregado com "+s.getCandleSticks().size()+ " Valores");
		
		if(s.getCandleSticks().size()>0)
			{
				this.stockDao.storeHistoricalStockValue(s);
				
				countCandleSticks+=s.getCandleSticks().size();
				countStocks++;
				this.stockList.add(s);
				
			}
	}
	System.out.println(countStocks+ " Acoes persistidas com sucesso !");
	System.out.println("Tem em memoria "+this.stockList.size()+ " acoes");
	System.out.println("O total de CandleSticks eh "+countCandleSticks);
	
	System.out.println("Vou calcular os valores estatisticos para catalogar");
	
	calculateStatistical();
	
}

private void calculateStatistical()
{
	this.conversations=false;
	for(Stock s:this.stockList)
	{
		System.out.println("Calculando valores estatiscos para "+s.getCodeName());
		
		s.setAvarangeReturn_15(this.statistical.averangeReturn_15(s.getCandleSticks()));
		s.setAvarangeReturn_30(this.statistical.averangeReturn_30(s.getCandleSticks()));
		
		s.setStandardDeviation_15(this.statistical.calculeStandardDeviation_15(s.getCandleSticks()));
		s.setStandardDeviation_30(this.statistical.calculeStandardDeviation_30(s.getCandleSticks()));
		
		s.setVariance_15(this.statistical.calculeVariance_15(s.getCandleSticks()));
		s.setVariance_30(this.statistical.calculeVariance_30(s.getCandleSticks()));
		
		s.setVarianceCoefficient_15(this.statistical.calculeVariance_15(s.getCandleSticks()));
		s.setVarianceCoefficient_30(this.statistical.calculeVariance_30(s.getCandleSticks()));
		
		this.hunter.stockDao.updateStock(s);
	}
	
	this.conversations=true;
	System.out.println(" Calculos concluidos! ");
	
}
private void stocksSorted(double lowerLimit,double upperLimit)
{
	
	System.out.println("Stock Sorted by Standard Deviation 30 "+this.stockDao.getStockOrderByStandardDeviation_30(lowerLimit,upperLimit).size());
	for(Stock s:this.stockDao.getStockOrderByStandardDeviation_30(lowerLimit,upperLimit))
	{
		System.out.println(s.getCodeName());
		System.out.println("Standard Deviation 30: "+s.getStandardDeviation_30());
	}
}


}
