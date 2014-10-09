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
	
	
	public void insertCurrentStock(Stock stock)
	{
		BasicDBObject where=new BasicDBObject("_id",stock.getCodeName());
		DBCursor newStock=collection_stock.find(where);
		ArrayList<DBObject> list=new ArrayList<DBObject>();
		
		while(newStock.hasNext())
		{
			
			list=(ArrayList<DBObject>) newStock.next().get("values");
			
			
		}
				
		System.out.println("Tamanho Lista "+list.size());
		
		for(DBObject d:list)
		{
			System.out.println(d.get("open"));
			
		}
		
	}
	public void storeHistoricalStockValue(ArrayList<CandleStick> candleSticks,Stock stock)
	{
		BasicDBObject newStock=new BasicDBObject("_id",stock.getCodeName()).append("sector", stock.getSector());
		
		BasicDBObject stockValues = new BasicDBObject();
		ArrayList<BasicDBObject> list=new ArrayList<BasicDBObject>();
		
		for(CandleStick c:candleSticks)
		{
			
			list.add(new BasicDBObject("date",c.getDate()).append("open",c.getOpen()).append("high",c.getHigh()).append("low", c.getLow()).append("close", c.getClose()).append("volume", c.getVolume()));
		}
		
		
		newStock.append("values", list);
		
		collection_stock.insert(newStock);
		
		//Date,Open,High,Low,Close,Volume,Adj Close
		
	}

}
