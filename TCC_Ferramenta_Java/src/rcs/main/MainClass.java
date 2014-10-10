package rcs.main;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.CsvHandle;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.requests.YahooFinance;

public class MainClass 
{

	 public static void main(String[] args)  
	 {
		
		/*
		String stockNameCode="POMO3.SA";
		YahooFinance stockYahoo=new YahooFinance();
		
		System.out.println(stockYahoo.storeCsvCurrentPriceStock(stockNameCode));
		System.out.println(stockYahoo.storeCsvHistoricalPriceStock(stockNameCode));
		CandleStick candle = stockYahoo.getCurrentValue(stockNameCode);
		
		System.out.println("candle: "+candle.getClose());
		System.out.println("candles List contem: "+stockYahoo.getHistoricalValue(stockNameCode).size()+ "");
 */
		
		 
/*		 
		//Download sequencial 
		CsvHandle csvHandle=new CsvHandle();
		YahooFinance stockYahoo=new YahooFinance("/Users/alissonnunes/Desktop","/RamonSeq","/Cruz");
		 
		long t0=System.currentTimeMillis();
		System.out.println("Start Time");
		for(Stock s:stockYahoo.loadStocksFromCsv("/Users/alissonnunes/Desktop/EmpresasBolsa"))
		{
			System.out.println("Current "+s.getCodeName()+" :"+ stockYahoo.storeCsvCurrentPriceStock(s.getCodeName()));
			System.out.println("Historical "+s.getCodeName()+" :"+stockYahoo.storeCsvHistoricalPriceStock(s.getCodeName()));
		}
		long t1=System.currentTimeMillis();
		System.out.println("End Time: "+(t1-t0));
		System.out.println("t0 "+t0);
		System.out.println("t1 "+t1);
		
		*/
		 
		 
		 
		 //Com uso de Threads e classes anonimas 
//		 YahooFinance yahoo=new YahooFinance("/Users/alissonnunes/Desktop","/Ramon","/Cruz");
//		 
//		 final long ti=System.currentTimeMillis();
//		 
//		 System.out.println("Iniciando contagem de tempo ");
//		 
//		 //Criando a lista a ser de acoes para baixar a cotacao 
//		 final ArrayList<Stock> stockList = yahoo.loadStocksFromCsv("/Users/alissonnunes/Desktop/EmpresasBolsa");
//
//		 final int id1=1;
//		 Runnable y1=new YahooFinance("/Users/alissonnunes/Desktop","/Ramon_y1","/Cruz",stockList)
//		 {
//			
//			 public void run()
//			 {
//				 
//				 while (true)
//				 {
//					 String codeName=null;
//					 synchronized (stockList) 
//					 { 
//						if(stockList.size()!=0)
//						{
//							codeName=stockList.get(0).getCodeName();
//							stockList.remove(0);
//							
//							
////							System.out.println("Baixando dados de : "+stockList.get(0).getCodeName()); 
////							codeName=stockList.get(0).getCodeName();
////							
////							stockList.remove(0);
////							
////							System.out.println(storeCsvHistoricalPriceStock(codeName));
////							System.out.println(storeCsvCurrentPriceStock(codeName));
////							System.out.print("\n");
//							
//							
//						}else 
//						{
//							try {
//								System.out.println("Thread "+id1+": Concluido!");
//								
//								long t=System.currentTimeMillis();
//								System.out.println("\t\t\tTempo total :"+(t-ti));
//								stockList.wait();
//								
//							} catch (InterruptedException e) {
//								
//								//e.printStackTrace();
//							}
//						}
//						
//					 }
//					 
//					 if(codeName!=null)
//					 {
//						System.out.println("Baixando dados de : "+codeName); 
//						System.out.println(codeName+"_historical :"+ storeCsvHistoricalPriceStock(codeName));
//						System.out.println(codeName+"_current :"+storeCsvCurrentPriceStock(codeName));
//						System.out.print("\n");
//					 }
//					 
//				 }
//				
//			 }
//		 };
//		 
//		 
//		 final int id2=2;
//		 Runnable y2=new YahooFinance("/Users/alissonnunes/Desktop","/Ramon_y2","/Cruz",stockList)
//		 {
//			
//			 public void run()
//			 {
//				while(true)
//				{
//					 String codeName=null;
//					 synchronized (stockList) 
//					 { 
//						if(stockList.size()!=0)
//						{
//							codeName=stockList.get(0).getCodeName();
//							stockList.remove(0);
//							
//
////							System.out.println("Baixando dados de : "+stockList.get(0).getCodeName()); 
////							codeName=stockList.get(0).getCodeName();
////							
////							stockList.remove(0);
////							
////							System.out.println(storeCsvHistoricalPriceStock(codeName));
////							System.out.println(storeCsvCurrentPriceStock(codeName));
////							System.out.print("\n");
//							
//							
//						}else 
//						{
//							try {
//								System.out.println("Thread "+id2+": Concluido!");
//								
//								long t=System.currentTimeMillis();
//								System.out.println("\t\t\tTempo total :"+(t-ti));
//								stockList.wait();
//								
//							} catch (InterruptedException e) {
//								
//								//e.printStackTrace();
//							}
//						}
//						
//					 }
//					 
//					 if(codeName!=null)
//					 {
//						 System.out.println("Baixando dados de : "+codeName); 
//							System.out.println(codeName+"_historical :"+ storeCsvHistoricalPriceStock(codeName));
//							System.out.println(codeName+"_current :"+storeCsvCurrentPriceStock(codeName));
//							System.out.print("\n");
//					 }
//					 
//				 }
//				
//			 }
//		 };
//		 
//		 
//		 final int id3=3;
//		 Runnable y3=new YahooFinance("/Users/alissonnunes/Desktop","/Ramon_y3","/Cruz",stockList)
//		 {
//			
//			 public void run()
//			 {
//				 while (true)
//				 {
//					 String codeName=null;
//					 synchronized (stockList) 
//					 { 
//						if(stockList.size()!=0)
//						{
//							codeName=stockList.get(0).getCodeName();
//							stockList.remove(0);
							
//							
//							System.out.println("Baixando dados de : "+stockList.get(0).getCodeName()); 
//							codeName=stockList.get(0).getCodeName();
//							
//							stockList.remove(0);
//							
//							System.out.println(storeCsvHistoricalPriceStock(codeName));
//							System.out.println(storeCsvCurrentPriceStock(codeName));
//							System.out.print("\n");
							
//							
//						}else 
//						{
//							try {
//								System.out.println("Thread "+id3+": Concluido!");
//								
//								long t=System.currentTimeMillis();
//								System.out.println("\t\t\tTempo total :"+(t-ti));
//								stockList.wait();
//								
//							} catch (InterruptedException e) {
//								
//								//e.printStackTrace();
//							}
//						}
//						
//					 }
//					 
//					 if(codeName!=null)
//					 {
//						 System.out.println("Baixando dados de : "+codeName); 
//							System.out.println(codeName+"_historical :"+ storeCsvHistoricalPriceStock(codeName));
//							System.out.println(codeName+"_current :"+storeCsvCurrentPriceStock(codeName));
//							System.out.print("\n");
//					 }
//					 
//				 }
//				
//			 }
//		 };
//		 
//		 
//		 Thread t1=new Thread(y1); 
//		 Thread t2=new Thread(y2);
//		 Thread t3=new Thread(y3);
//		 
//		 t1.start();
//		 t2.start();
//		 t3.start();
//		 
		
		 
		//Persisitencia 
		 StockDao stockDao=new StockDao();
		 YahooFinance ya=new YahooFinance("/Users/alissonnunes/Desktop","/Ramon_1","/Cruz");
		 
		 ArrayList<Stock> stockListPersistence=ya.loadStocksFromCsv("/Users/alissonnunes/Desktop/EmpresasBolsa");
		 
//		 for(Stock s:stockListPersistence)
//		 {
//			 stockDao.insertStock(s);
//		 }
//		 System.out.println("Dados carregados no BD");
		 
		 ArrayList<CandleStick> listCandleStick = new ArrayList<CandleStick>();
		 DateFormat formatTest = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
		 
		 try {
			listCandleStick.add(new CandleStick(10, 10, 10, 10, 1000, (Date)formatTest.parse("10/08/2014 00:00am")));
			listCandleStick.add(new CandleStick(11, 11, 11, 11, 1000, (Date)formatTest.parse("10/07/2014 00:00am")));
			listCandleStick.add(new CandleStick(12, 12, 12, 12, 1000, (Date)formatTest.parse("10/06/2014 00:00am")));
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		 
		 
		 Stock testStock=new Stock("RAMON.SA", "TCC");
		 
		 testStock.setCandleSticks(listCandleStick);
		 System.out.println("Insercao historico "+ stockDao.storeHistoricalStockValue(testStock));
		 
		 try {
			testStock.setCurrentCandleStick(new CandleStick(13, 13, 13, 13, 1000, (Date)formatTest.parse("13/06/2014 00:00am")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("insercao:  "+ stockDao.insertCurrentStock(testStock));
		 
		// stockDao.insertCurrentStock(testStock);
		 
//		 System.out.println("concluido");
//		 
//		 //Convertendo dada 
//		 
//		 String txtData="10/08/2014";
//		 String txtHora="11:00am";
//		 
//		 DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
//		 try {
//			Date date =(Date)format.parse(txtData+" "+txtHora);
//			
//			System.out.println("Dia "+date.getDay());
//			System.out.println("Date "+date);
//			
//		} catch (ParseException e) 
//		{
//			
//		}
//		 
//	
		 
	 }
}
