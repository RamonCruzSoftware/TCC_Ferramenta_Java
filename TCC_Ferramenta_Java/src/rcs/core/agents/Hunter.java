package rcs.core.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.io.File;
import java.util.ArrayList;

import rcs.main.MainClass;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.requests.YahooFinance;


public class Hunter extends Agent {
	
	
	
	private static final long serialVersionUID = 1L;
	private Hunter hunter;
	private boolean conversations=false;
	
	private ArrayList<Stock> stockList=null;
	private StockDao stockDao=null;
	
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
			
			
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			
			//Create an service in yellow pages
			ServiceDescription service=new ServiceDescription();
			service.setType("StockHunter");
			service.setName("StockHunter");
			
			
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

		@Override
		public void action() 
		{
			try
			{
				ACLMessage messages= myAgent.receive();
				if(messages!=null &&conversations)
				{
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
				if(file.listFiles().length>2)
					System.out.println("Alguem ja baixou os arquivos CSV.. nao precisa baixar");
				
				
				hunter.stockList=hunter.stockDao.getAllStocks();
				System.out.println("OK ja existem "+hunter.stockList.size()+" no banco de dados");
				System.out.println("Sao esses ...");
				for(Stock s:hunter.stockList)
				{
					System.out.println("\t"+s.getCodeName() +" do setor: "+s.getSector());
				}

			}catch(Exception e)
			{
				System.out.println("Ainda nao baixaram os arquivos CSV, vou fazer isso.");
				hunter.downloadCsvFiles(hunter.dir_1,hunter.subDir_1,hunter.subDir_2, hunter.sectorsCsvFilePath);
			}
			
				
		}
	});
}
private  void loadDataBase()
{
	YahooFinance yahooFinance=new YahooFinance(this.dir_1, this.subDir_1, this.subDir_2);
	
	this.stockList=new ArrayList<Stock>();
	
	int count=0;
	
	for(Stock s: yahooFinance.loadStocksFromCsv(this.sectorsCsvFilePath))
	{
		s.setCandleSticks(yahooFinance.getHistoricalValue(s.getCodeName()));
		System.out.println(s.getCodeName()+ " Carregado com "+s.getCandleSticks().size()+ " Valores");
		
		if(s.getCandleSticks().size()>0)
			{
				this.stockDao.storeHistoricalStockValue(s);
				count++;
				this.stockList.add(s);
				
			}
	}
	System.out.println(count+ " Acoes persistidas com sucesso !");
	System.out.println("Tem em memoria "+this.stockList.size()+ " acoes");
	
}


}
