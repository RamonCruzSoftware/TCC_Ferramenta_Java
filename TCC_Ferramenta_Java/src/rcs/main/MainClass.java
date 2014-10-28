package rcs.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.strategy.MovingAvarangeSimpleStrategy;
import rcs.suport.financial.strategy.Strategy;
import rcs.suport.util.requests.YahooFinance;

public class MainClass 
{
	 static String dir_1="/Users/alissonnunes/Desktop";
	 static String subDir_1="/TCC2";
	 static String subDir_2="/Ativos";
	 static String sectorsCsvFilePath="/Users/alissonnunes/Desktop/Setores";

	 public static void main(String[] args)  
	 {
	
	/*	 
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
		 
		 System.out.println("concluido");
		 
		 //Convertendo dada 
		 
		 String txtData="10/08/2014";
		 String txtHora="11:00am";
		 
		 DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
		 try {
			Date date =(Date)format.parse(txtData+" "+txtHora);
			
			System.out.println("Dia "+date.getDay());
			System.out.println("Date "+date);
			
		} catch (ParseException e) 
		{
			
		}
		 
	*/
		
		
		
		
		
		
	 }
	 
	 
	 
	
}
