package rcs.suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.MongoConnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class StockDao {
	
	private DBCollection collection_stock_values;
	private DBCollection collection_stock;
	
	public StockDao()
	{
		super();
		try
		{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			this.collection_stock_values=db.getCollection("stocks");
		//	this.collection_stock=db.getCollection("stocks");
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void updateStock(Stock stock)
	{
		
		BasicDBObject where=new BasicDBObject("_id",stock.getCodeName());
		DBObject stockStored=null;
		ArrayList<BasicDBObject> candleSticksList=new ArrayList<BasicDBObject>();
		
		
		DBCursor cursor=collection_stock_values.find(where);
		
		
		while(cursor.hasNext())
		{
			stockStored=cursor.next();
			
		}
		//Convertendo informacoes 
		for(CandleStick c:stock.getCandleSticks())
		{
			candleSticksList.add(new BasicDBObject("date",stock.getCurrentCandleStick().getDate()).
							append("open",stock.getCurrentCandleStick().getOpen()).
							append("high",stock.getCurrentCandleStick().getHigh()).
							append("low", stock.getCurrentCandleStick().getLow()).
							append("close", stock.getCurrentCandleStick().getClose()).
							append("volume", stock.getCurrentCandleStick().getVolume()));
		}		
		
		BasicDBObject updateStock=new BasicDBObject("_id",stock.getCodeName())
										.append("sector", stock.getSector())
										.append("avarangeReturn_15", stock.getAvarangeReturn_15())
										.append("standardDeviation_15", stock.getStandardDeviation_15())
										.append("variance_15", stock.getVariance_15())
										.append("varianceCoefficient_15", stock.getVarianceCoefficient_15())
										.append("avarangeReturn_30", stock.getAvarangeReturn_30())
										.append("standardDeviation_30", stock.getStandardDeviation_30())
										.append("variance_30", stock.getVariance_30())
										.append("varianceCoefficient_30", stock.getVarianceCoefficient_30())
										.append("values", candleSticksList);						
						
			collection_stock_values.remove(stockStored);
			collection_stock_values.insert(updateStock);
		
	}
	
	public boolean insertCurrentStock(Stock stock)
	{
		
		BasicDBObject where=new BasicDBObject("_id",stock.getCodeName());
		DBObject stockStored=null;
		ArrayList<BasicDBObject> stockValuesToStore=new ArrayList<BasicDBObject>();
		ArrayList<BasicDBObject> stockValuesStored=new ArrayList<BasicDBObject>();
		
		DBCursor cursor=collection_stock_values.find(where);
		
		while(cursor.hasNext())
		{
			stockStored=cursor.next();
			
		}
		
		BasicDBObject updateStock=new BasicDBObject("_id",stock.getCodeName())
													.append("sector", stock.getSector())
													.append("avarangeReturn_15", stock.getAvarangeReturn_15())
													.append("standardDeviation_15", stock.getStandardDeviation_15())
													.append("variance_15", stock.getVariance_15())
													.append("varianceCoefficient_15", stock.getVarianceCoefficient_15())
													.append("avarangeReturn_30", stock.getAvarangeReturn_30())
													.append("standardDeviation_30", stock.getStandardDeviation_30())
													.append("variance_30", stock.getVariance_30())
													.append("varianceCoefficient_30", stock.getVarianceCoefficient_30());
													
	
		stockValuesStored =(ArrayList<BasicDBObject>)stockStored.get("values");
		
		//Verifico se a candle ja existe persistida no banco de dados 
		if(stockValuesStored.get(stockValuesStored.size()-1).get("date").toString().
				equalsIgnoreCase(stock.getCurrentCandleStick().getDate().toString()))
		{
			
			return false;
		
		}else
		{
			for(BasicDBObject c:stockValuesStored)
			{
				
				stockValuesToStore.add(c);
				
			}
			stockValuesToStore.add(new BasicDBObject("date",stock.getCurrentCandleStick().getDate()).
					append("open",stock.getCurrentCandleStick().getOpen()).
					append("high",stock.getCurrentCandleStick().getHigh()).
					append("low", stock.getCurrentCandleStick().getLow()).
					append("close", stock.getCurrentCandleStick().getClose()).
					append("volume", stock.getCurrentCandleStick().getVolume()));
			
			updateStock.put("values", stockValuesToStore);
			
			collection_stock_values.remove(stockStored);
			collection_stock_values.insert(updateStock);
		
			return true;
		}
	
	}
	
	public boolean storeHistoricalStockValue(Stock stock)
	{
		BasicDBObject newStock=new BasicDBObject("_id",stock.getCodeName()).append("sector", stock.getSector());
		
		BasicDBObject stockValues = new BasicDBObject();
		ArrayList<BasicDBObject> list=new ArrayList<BasicDBObject>();
		
		for(CandleStick c:stock.getCandleSticks())
		{
			
			list.add(new BasicDBObject("date",c.getDate()).append("open",c.getOpen()).append("high",c.getHigh()).append("low", c.getLow()).append("close", c.getClose()).append("volume", c.getVolume()));
		}
		
		newStock.append("values", list);
		
		try
		{
			collection_stock_values.insert(newStock);
			return true;
			
		}catch(MongoException.DuplicateKey e)
		{
			return false;
		}
		
	}
	
	public ArrayList<Stock> getAllStocks()
	{
		
		DBCursor cursor= collection_stock_values.find();
		
		DBObject mongo_stock=null;
		ArrayList<BasicDBObject> mongo_candleList=null;
		
		
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		
		
		Stock stock=null;
		
		
		while(cursor.hasNext())
		{
			
			mongo_stock=cursor.next();
			mongo_candleList=(ArrayList<BasicDBObject>) mongo_stock.get("values");
			stock=new Stock(mongo_stock.get("_id").toString(), mongo_stock.get("sector").toString());
			
			
			//Pega todo historico existente no banco de dados
			ArrayList<CandleStick>candleList=new ArrayList<CandleStick>();
			for(BasicDBObject c:mongo_candleList)
			{
				
				candleList.add(new CandleStick(
												c.getDouble("open"), 
												c.getDouble("high"), c.getDouble("low"),
												c.getDouble("close"), c.getInt("volume"), 
												c.getDate("date"))
											);
			}
			
			stock.setCandleSticks(candleList);
			stockList.add(stock);
			
		}
		
		return stockList;
		
	}
	
	

}
