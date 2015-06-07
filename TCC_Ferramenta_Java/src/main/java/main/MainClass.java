package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import core.agents.util.SimulationSetup;
import core.agents.util.StocksInMemory;
import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.dao.SimulationDataDao;
import suport.util.database.mongoDB.dao.StockDao;
import suport.util.database.mongoDB.pojo.SimulationData;




public class MainClass {
	
	
	static String dir_1 = "/Users/ramoncruz/Desktop";
	static String subDir_1 = "/TCC2";
	static String subDir_2 = "/Ativos";
	static String sectorsCsvFilePath = "/Users/ramoncruz/Dropbox/UnB/TCC/workspace/java/Setores";

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		/*
		 * //Persisitencia StockDao stockDao=new StockDao(); YahooFinance ya=new
		 * YahooFinance("/Users/alissonnunes/Desktop","/Ramon_1","/Cruz");
		 * 
		 * ArrayList<Stock> stockListPersistence=ya.loadStocksFromCsv(
		 * "/Users/alissonnunes/Desktop/EmpresasBolsa");
		 * 
		 * // for(Stock s:stockListPersistence) // { // stockDao.insertStock(s);
		 * // } // System.out.println("Dados carregados no BD");
		 * 
		 * ArrayList<CandleStick> listCandleStick = new
		 * ArrayList<CandleStick>(); DateFormat formatTest = new
		 * SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);
		 * 
		 * try { listCandleStick.add(new CandleStick(10, 10, 10, 10, 1000,
		 * (Date)formatTest.parse("10/08/2014 00:00am")));
		 * listCandleStick.add(new CandleStick(11, 11, 11, 11, 1000,
		 * (Date)formatTest.parse("10/07/2014 00:00am")));
		 * listCandleStick.add(new CandleStick(12, 12, 12, 12, 1000,
		 * (Date)formatTest.parse("10/06/2014 00:00am"))); } catch
		 * (ParseException e1) {
		 * 
		 * e1.printStackTrace(); }
		 * 
		 * 
		 * Stock testStock=new Stock("RAMON.SA", "TCC");
		 * 
		 * testStock.setCandleSticks(listCandleStick);
		 * System.out.println("Insercao historico "+
		 * stockDao.storeHistoricalStockValue(testStock));
		 * 
		 * try { testStock.setCurrentCandleStick(new CandleStick(13, 13, 13, 13,
		 * 1000, (Date)formatTest.parse("13/06/2014 00:00am"))); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } System.out.println("insercao:  "+
		 * stockDao.insertCurrentStock(testStock));
		 * 
		 * // stockDao.insertCurrentStock(testStock);
		 * 
		 * System.out.println("concluido");
		 * 
		 * //Convertendo dada
		 * 
		 * String txtData="10/08/2014"; String txtHora="11:00am";
		 * 
		 * DateFormat format = new
		 * SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US); try { Date date
		 * =(Date)format.parse(txtData+" "+txtHora);
		 * 
		 * System.out.println("Dia "+date.getDay());
		 * System.out.println("Date "+date);
		 * 
		 * } catch (ParseException e) {
		 * 
		 * }
		 */

		/*
		 * Statistical statistical= new Statistical();
		 * ArrayList<CandleStick>x=new ArrayList<CandleStick>();
		 * ArrayList<CandleStick>y=new ArrayList<CandleStick>();
		 * 
		 * x.add(new CandleStick(0, 0, 0, 10, 0, null)); x.add(new
		 * CandleStick(0, 0, 0, 11, 0, null)); x.add(new CandleStick(0, 0, 0,
		 * 12, 0, null)); x.add(new CandleStick(0, 0, 0, 11, 0, null));
		 * x.add(new CandleStick(0, 0, 0, 13, 0, null)); x.add(new
		 * CandleStick(0, 0, 0, 13, 0, null)); x.add(new CandleStick(0, 0, 0,
		 * 14, 0, null)); x.add(new CandleStick(0, 0, 0, 12.2, 0, null));
		 * x.add(new CandleStick(0, 0, 0, 15.5, 0, null)); x.add(new
		 * CandleStick(0, 0, 0, 17.6, 0, null)); x.add(new CandleStick(0, 0, 0,
		 * 18.5, 0, null)); x.add(new CandleStick(0, 0, 0, 19.8, 0, null));
		 * x.add(new CandleStick(0, 0, 0, 29.0, 0, null)); x.add(new
		 * CandleStick(0, 0, 0, 30.3, 0, null)); x.add(new CandleStick(0, 0, 0,
		 * 25, 0, null));
		 * 
		 * 
		 * y.add(new CandleStick(0, 0, 0, 14, 0, null)); y.add(new
		 * CandleStick(0, 0, 0, 23, 0, null)); y.add(new CandleStick(0, 0, 0,
		 * 55, 0, null)); y.add(new CandleStick(0, 0, 0, 6, 0, null)); y.add(new
		 * CandleStick(0, 0, 0, 3, 0, null)); y.add(new CandleStick(0, 0, 0, 23,
		 * 0, null)); y.add(new CandleStick(0, 0, 0, 11, 0, null)); y.add(new
		 * CandleStick(0, 0, 0, 14.2, 0, null)); y.add(new CandleStick(0, 0, 0,
		 * 16.5, 0, null)); y.add(new CandleStick(0, 0, 0, 18.6, 0, null));
		 * y.add(new CandleStick(0, 0, 0, 11.5, 0, null)); y.add(new
		 * CandleStick(0, 0, 0, 1.8, 0, null)); y.add(new CandleStick(0, 0, 0,
		 * 9.0, 0, null)); y.add(new CandleStick(0, 0, 0, 36.3, 0, null));
		 * y.add(new CandleStick(0, 0, 0, 24, 0, null));
		 * 
		 * System.out.println(statistical.calculeCorrelationCoefficient_15(x,
		 * y));
		 */

		// StockDao stockDao= new StockDao();
		// Statistical statistical= new Statistical();
		//
		// Stock stk1,stk2,stk3,stk4;
		// stk1= stockDao.getStock("BBAS3.SA");
		// stk2= stockDao.getStock("BBDC4.SA");
		// stk3= stockDao.getStock("BEES3.SA");
		// stk4= stockDao.getStock("BEMA3.SA");
		//
		//
		// stk1.setCandleSticks(stockDao.getStockPrices_last30("GEPA4.SA"));
		// stk2.setCandleSticks(stockDao.getStockPrices_last30("BMKS3.SA"));
		//
		// System.out.println("GEPA4 tem "+stk1.getCandleSticks().size());
		// System.out.println("BMKS3 tem "+stk2.getCandleSticks().size());
		//
		// System.out.println("correl "+statistical.calculeCorrelationCoefficient_30(stk1.getCandleSticks(),
		// stk2.getCandleSticks()));
		//
		// System.out.println("========Sort======");
		// System.out.println("Variance 15 GEPA4 "+stk1.getVariance_15());
		// System.out.println("Variance 15 BMKS3 "+stk2.getVariance_15());
		// System.out.println("Variance 15 BEES3 "+stk3.getVariance_15());
		// System.out.println("Variance 15 BEMA3 "+stk4.getVariance_15());
		//
		// System.out.println("=========ArrayList=========");
		//
		// List<Stock>stockList= new ArrayList<Stock>();
		//
		// stockList.add(stk1);
		// stockList.add(stk2);
		// stockList.add(stk3);
		// stockList.add(stk4);
		//
		// for(Stock s:stockList)
		// {
		// System.out.println("->"+s.getCodeName()+" Variance 15: "+s.getVariance_15());
		// }
		//
		// System.out.println("=========Sort ArrayList=========");
		//
		// Collections.sort(stockList);
		// for(Stock s:stockList)
		// {
		// System.out.println("->"+s.getCodeName()+" Variance 15: "+s.getVariance_15());
		// }
//
//		String managerAnswer = "RAMON.SA_40.21";
//
//		System.out.println("Lengh " + managerAnswer.length());
//		int underScore = 0;
//		for (int i = 0; i < managerAnswer.length(); i++) {
//
//			if ((managerAnswer.charAt(i) + "").equals("_")) {
//				underScore = i;
//			}
//
//		}
//		String codeName = managerAnswer.substring(0, underScore);
//		String value = managerAnswer.substring(underScore + 1,
//				managerAnswer.length());
//
//		System.out.println(" name " + codeName);
//		System.out.println(" value " + value);
		
		
//		ClasseA a=new ClasseA(new Date(113, 1, 1),new Date(113, 2, 3));
//		
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
//		System.out.println("content :"+a.simulation("BAZA3.SA").getDate());
		//PETR4.SA PRBC4.SA SANB4.SA
		
//		ClasseA classe=new ClasseA();
//		
//		ArrayList<Stock> list= new ArrayList<Stock>();
//		
//		Stock a=new Stock("RAMON","CASA");
//		Stock b=new Stock("RENAN","CASA");
//		Stock c=new Stock("NAYANE","CASA");
//		Stock d=new Stock("PRISCILA","CASA");
//		Stock e=new Stock("DIONA","CASA");
//		Stock f=new Stock("IARA","CASA");
//		
//		list.add(a);
//		list.add(b);
//		list.add(a);
//		list.add(c);
//		list.add(d);
//		list.add(d);
//		list.add(b);
//		list.add(e);
//		list.add(f);
//		list.add(c);
//		
//		for(Stock s:list)
//		{
//			System.out.println("->"+s.getCodeName());
//		}
//		System.out.println("===");
//		ArrayList<Stock>list1;
//		list=classe.removeRepetitions(list);;
//		
//		for(Stock s:list)
//		{
//			System.out.println("->"+s.getCodeName());
//		}
		
		
		//================Simulacao
	
	String code="RCSL4.SA";
	SimulationDataDao dao= new SimulationDataDao();
	System.out.println("=>"+dao.findDataOfSimulation(code).size());
	
	ArrayList<SimulationData> teste= dao.findDataOfSimulation(code);
	for(int i=0; i<teste.size();i++)
	{
		System.out.println("\""+teste.get(i).getCodeName()+"\","
				+ ""+"\""+teste.get(i).getDate().getDay()+"/"+teste.get(i).getDate().getMonth()+"/"+(teste.get(i).getDate().getYear()+1900)+"\","
				+"\""+teste.get(i).getOrder()+"\","+teste.get(i).getValue());
	}
		
//"ABCB4.SA","4/10/2015","5:05pm",12.50,12.57,12.32,12.55,85300
 

//		SimulationSetup setup=new SimulationSetup();
//		StockDao stDao= new StockDao();
//		
//	//	stDao.getAllDatesOfPricesBetweenInterval(setup.getStartDate(), setup.getFinishDate());
//	//	System.out.println("====finalizado====");
//		
//		System.out.println("day->"+setup.getStartDate().getDay());
//		System.out.println("month->"+setup.getStartDate().getMonth());
//		System.out.println("year->"+setup.getStartDate().getYear());
//	
//		System.out.println(" inserir data:"+new Date(setup.getStartDate().getYear(),
//																	setup.getStartDate().getMonth(),
//																	setup.getStartDate().getDate()+2));
//	
//		System.out.println("=="+stDao.getCandleStickOfStockByDate("USIM3.SA", 
//																new 
//																Date(setup.getStartDate().getYear(),
//																	setup.getStartDate().getMonth(),
//																	setup.getStartDate().getDate()+2
//																	)
//																).getDate());
//		
//		
		
	}
	
	
}
