package rcs.suport.util.database.mongoDB.dao;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import org.bson.NewBSONDecoder;

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
	
	private DBCollection collection_stock;
	
	
	public StockDao()
	{
		super();
		try
		{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			this.collection_stock=db.getCollection("stocks");
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean insertCaculatedValues()
	{
		
		return false;
	}
	
	public boolean insertCurrentStock(Stock stock)
	{
		
		BasicDBObject where=new BasicDBObject("_id",stock.getCodeName());
		DBObject stockStored=null;
		ArrayList<BasicDBObject> stockValuesToStore=new ArrayList<BasicDBObject>();
		ArrayList<BasicDBObject> stockValuesStored=new ArrayList<BasicDBObject>();
		
		DBCursor cursor=collection_stock.find(where);
		
		while(cursor.hasNext())
		{
			stockStored=cursor.next();
			
		}
		
		BasicDBObject updateStock=new BasicDBObject("_id",stock.getCodeName()).append("sector", stock.getSector());
	
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
			
			collection_stock.remove(stockStored);
			collection_stock.insert(updateStock);
		
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
			collection_stock.insert(newStock);
			return true;
			
		}catch(MongoException.DuplicateKey e)
		{
			return false;
		}
		
	}
	
	public ArrayList<Stock> getAllStocks()
	{
		
		DBCursor cursor= collection_stock.find();
		
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
