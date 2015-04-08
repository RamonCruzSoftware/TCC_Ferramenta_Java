package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import suport.financial.partternsCandleStick.CandleStick;
import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.dao.StockDao;


public class ClasseA {

	static final Logger logg= LogManager.getLogger(ClasseA.class.getName());
	private Map<String, ArrayList<CandleStick>>stockCandleList;
	private Date startDate;
	private Date finishDate;
	
	public ClasseA(Date start,Date finish)
	{
		startDate=start;
		finishDate=finish;
		new HashMap<String, ArrayList<Stock>>();
		stockCandleList=new HashMap<String, ArrayList<CandleStick>>();	
		this.loadSimulationData();
	}
	public ClasseA()
	{
		new HashMap<String, ArrayList<Stock>>();
		stockCandleList=new HashMap<String, ArrayList<CandleStick>>();
		logg.debug("DEBUG");
		logg.info("INFO");
		logg.error("ERROR");
		logg.fatal("FATAL");
	}
//	public Stock simulationData(String codeName)
//	{
//		ArrayList<Stock>stockList=null;
//		Stock stockReturn=null;
//		int indexToReturn=0;
//		StockDao stockDao= new StockDao();
//		
//		if(this.stocks.containsKey(codeName))
//		{
//			stockList=this.stocks.get(codeName);
//			indexToReturn=stockList.size()-1;
//			stockReturn=stockList.get(indexToReturn);
//			stockList.remove(indexToReturn);	
//			this.stocks.remove(codeName);
//			this.stocks.put(codeName, stockList);
//			
//		}else
//		{
//			stockReturn=stockDao.getStocksWithPricesBetweenInterval(codeName,startDate, finishDate);
//			
//		}
//		return stockReturn;
//	}
//	
	public CandleStick simulation(String codeName)
	{
		Stock stockAux=null;
		int indexToReturn=0;
		StockDao stockDao= new StockDao();
		
		CandleStick returnCandle=null;
		ArrayList<CandleStick>candleList=null;
		
		if(this.stockCandleList.containsKey(codeName))
		{
			candleList=this.stockCandleList.get(codeName);
			indexToReturn=candleList.size()-1;
			returnCandle=candleList.get(indexToReturn);
			candleList.remove(indexToReturn);	
			this.stockCandleList.remove(codeName);
			this.stockCandleList.put(codeName, candleList);
			
		}else
		{
			stockAux=stockDao.getStocksWithPricesBetweenInterval(codeName,startDate, finishDate);
			if(stockAux!=null&& stockAux.getCandleSticks().size()>0)
			{
				candleList=new ArrayList<CandleStick>();
				for(CandleStick candle:stockAux.getCandleSticks())
				{
					candleList.add(candle);
				}
			}else returnCandle=null;
		}
		return returnCandle;
	}
	public void loadSimulationData()
	{
		StockDao stockDao= new StockDao();
		ArrayList<Stock>stockList=null;
		
		stockList=stockDao.getAllStocksWithPricesBetweenInterval(startDate, finishDate);
		this.stockCandleList = new HashMap<String, ArrayList<CandleStick>>();
		if(stockList!=null && stockList.size()>0)
		{
			for(Stock stock : stockList)
			{
				if(stock.getCandleSticks().size()>0)
					this.stockCandleList.put(stock.getCodeName(), stock.getCandleSticks());
			}
		}
		System.out.println("Carregado com "+this.stockCandleList.size());
		for(Entry<String, ArrayList<CandleStick>>c:this.stockCandleList.entrySet())
		{
			System.out.println("Code "+c.getKey());
		}
		
	}

}
