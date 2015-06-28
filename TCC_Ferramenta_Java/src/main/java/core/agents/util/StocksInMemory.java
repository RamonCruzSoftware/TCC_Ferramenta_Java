package core.agents.util;

import java.util.ArrayList;

import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.dao.StockDao;

public class StocksInMemory {
	
	private ArrayList<Stock> stockList;
	private static StocksInMemory instance;
	private SimulationSetup simulationSetup;
	private StockDao stockDao;
	
	private StocksInMemory()
	{
		this.simulationSetup = new SimulationSetup();
		this.stockList = new ArrayList<Stock>();
		this.stockDao= new StockDao();
		
	}
	
	public static  StocksInMemory getInstance()
	{
		if(instance==null)instance= new StocksInMemory();
		
		 return instance;
			
	}
	
	public ArrayList<Stock> getStockList()
	{
		if(this.stockList.size()==0)
			 return  this.stockList=this.stockDao.getAllStocksWithPricesBetweenInterval(this.simulationSetup.getStartDate(), this.simulationSetup.getFinishDate());
		else 
			return this.stockList;
	}
	
	
 
}
