package rcs.main;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.statistical.Statistical;

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
		
		
		
		
		Statistical statistical= new Statistical();
		ArrayList<CandleStick>x=new ArrayList<CandleStick>();
		ArrayList<CandleStick>y=new ArrayList<CandleStick>();
		
		x.add(new CandleStick(0, 0, 0, 10, 0, null));
		x.add(new CandleStick(0, 0, 0, 11, 0, null));
		x.add(new CandleStick(0, 0, 0, 12, 0, null));
		x.add(new CandleStick(0, 0, 0, 11, 0, null));
		x.add(new CandleStick(0, 0, 0, 13, 0, null));
		x.add(new CandleStick(0, 0, 0, 13, 0, null));
		x.add(new CandleStick(0, 0, 0, 14, 0, null));
		x.add(new CandleStick(0, 0, 0, 12.2, 0, null));
		x.add(new CandleStick(0, 0, 0, 15.5, 0, null));
		x.add(new CandleStick(0, 0, 0, 17.6, 0, null));
		x.add(new CandleStick(0, 0, 0, 18.5, 0, null));
		x.add(new CandleStick(0, 0, 0, 19.8, 0, null));
		x.add(new CandleStick(0, 0, 0, 29.0, 0, null));
		x.add(new CandleStick(0, 0, 0, 30.3, 0, null));
		x.add(new CandleStick(0, 0, 0, 25, 0, null));
		
		
		y.add(new CandleStick(0, 0, 0, 14, 0, null));
		y.add(new CandleStick(0, 0, 0, 23, 0, null));
		y.add(new CandleStick(0, 0, 0, 55, 0, null));
		y.add(new CandleStick(0, 0, 0, 6, 0, null));
		y.add(new CandleStick(0, 0, 0, 3, 0, null));
		y.add(new CandleStick(0, 0, 0, 23, 0, null));
		y.add(new CandleStick(0, 0, 0, 11, 0, null));
		y.add(new CandleStick(0, 0, 0, 14.2, 0, null));
		y.add(new CandleStick(0, 0, 0, 16.5, 0, null));
		y.add(new CandleStick(0, 0, 0, 18.6, 0, null));
		y.add(new CandleStick(0, 0, 0, 11.5, 0, null));
		y.add(new CandleStick(0, 0, 0, 1.8, 0, null));
		y.add(new CandleStick(0, 0, 0, 9.0, 0, null));
		y.add(new CandleStick(0, 0, 0, 36.3, 0, null));
		y.add(new CandleStick(0, 0, 0, 24, 0, null));
		
		System.out.println(statistical.calculeCorrelationCoefficient_15(x, y));
		
	 }
	 
	 
	 
	
}
