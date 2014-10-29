package rcs.suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.util.database.mongoDB.MongoConnection;
import rcs.suport.util.database.mongoDB.pojo.ManagedStock;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ManagedStockDao {
	
	private DBCollection collection_managedStock;
	private MongoConnection connection;
	private DB db;
	
	public ManagedStockDao()
	{
		this.connection=MongoConnection.getInstance();
		this.db= this.connection.getDB();
		this.collection_managedStock=db.getCollection("managedStock");
		
	}
	
	public boolean insertCandleStick(String userId,String stockCodeName,CandleStick candleStick)
	{
		BasicDBObject where=null;
		DBCursor cursor=null;
		BasicDBObject managedStockStored=null;
		ArrayList<BasicDBObject> candleSticksStored=null;
		ArrayList<DBObject>candlSticksToStore=null;
		
		
		try
		{
			where=new BasicDBObject("codeName",stockCodeName)
								.append("userIdentifier", userId);
			
			cursor= this.collection_managedStock.find(where);
			
			while(cursor.hasNext())
			{
				managedStockStored=(BasicDBObject)cursor.next();
			}
			candleSticksStored=(ArrayList<BasicDBObject>)managedStockStored.get("candleSticks");
			
			if(candleSticksStored.get(candleSticksStored.size()-1).getDate("date")==candleStick.getDate())
			{
				return false;
			}else
			{
				candlSticksToStore= new ArrayList<DBObject>();
				for(BasicDBObject b:candleSticksStored)
				{
					candlSticksToStore.add(b);
				}
				candlSticksToStore.add(new BasicDBObject("date",candleStick.getDate())
									.append("open",candleStick.getOpen())
									.append("high",candleStick.getHigh())
									.append("low", candleStick.getLow())
									.append("close", candleStick.getClose())
									.append("volume", candleStick.getVolume()));
			}
			
					
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	public boolean insertManagedStock(ManagedStock mStock)
	{
		BasicDBObject managedStock=null;
		ArrayList<BasicDBObject>candlesticks=null;
		BasicDBObject buylled=null;
		BasicDBObject selled=null;
		
		try
		{
			candlesticks=new ArrayList<BasicDBObject>();
			
			for(CandleStick c:mStock.getCandleSticks())
			{
				candlesticks.add(new BasicDBObject("date",c.getDate())
										.append("open",c.getOpen())
										.append("high",c.getHigh())
										.append("low", c.getLow())
										.append("close", c.getClose())
										.append("volume", c.getVolume()));
			}
			
			if(mStock.getBuyed()!=null)
			{
				buylled= new BasicDBObject("date",mStock.getBuyed().getDate())
										.append("open",mStock.getBuyed().getOpen())
										.append("high",mStock.getBuyed().getHigh())
										.append("low", mStock.getBuyed().getLow())
										.append("close", mStock.getBuyed().getClose())
										.append("volume", mStock.getBuyed().getVolume());
				
			}
			if(mStock.getSelled()!=null)
			{
				selled= new BasicDBObject("date",mStock.getSelled().getDate())
									.append("open",mStock.getSelled().getOpen())
									.append("high",mStock.getSelled().getHigh())
									.append("low", mStock.getSelled().getLow())
									.append("close", mStock.getSelled().getClose())
									.append("volume", mStock.getSelled().getVolume());
			}
			
			managedStock= new BasicDBObject("codeName",mStock.getCodeName())
										   .append("sector", mStock.getSector())
										   .append("profitPercent", mStock.getProfitPercent())
										   .append("profitValue", mStock.getProfitValue())
										   .append("candleSticks", candlesticks)
										   .append("buyed", mStock.getBuyed())
										   .append("selled", mStock.getSelled())
										   .append("qtdStocksBought", mStock.getQtdStocksBought());
			
			
			this.collection_managedStock.insert(managedStock);
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateManagedStock(ManagedStock mStock)
	{
		BasicDBObject managedStockStored=null;
		BasicDBObject where=null;
	
		DBCursor cursor=null;
		
		BasicDBObject managedStockToStore=null;
		ArrayList<BasicDBObject>candlestickToStore=null;
		BasicDBObject buylledToStore=null;
		BasicDBObject selledToStore=null;
		
		
		try
		{
			where = new BasicDBObject("codeName",mStock.getCodeName())
									.append("userIdentifier", mStock.getUserIdentifier());
									
			cursor=this.collection_managedStock.find(where);
			
			while(cursor.hasNext())
			{
				managedStockStored=(BasicDBObject)cursor.next();
			}
			
			candlestickToStore= new ArrayList<BasicDBObject>();
			
			for (CandleStick c:mStock.getCandleSticks())
			{
				candlestickToStore.add(new BasicDBObject("date",c.getDate())
									.append("open",c.getOpen())
									.append("high",c.getHigh())
									.append("low", c.getLow())
									.append("close", c.getClose())
									.append("volume", c.getVolume()));
			}
				
			
			if(mStock.getBuyed()!=null)
			{
				buylledToStore= new BasicDBObject("date",mStock.getBuyed().getDate())
				.append("open",mStock.getBuyed().getOpen())
				.append("high",mStock.getBuyed().getHigh())
				.append("low", mStock.getBuyed().getLow())
				.append("close", mStock.getBuyed().getClose())
				.append("volume", mStock.getBuyed().getVolume());
			}
			
			if(mStock.getSelled()!=null)
			{
				selledToStore= new BasicDBObject("date",mStock.getSelled().getDate())
				.append("open",mStock.getSelled().getOpen())
				.append("high",mStock.getSelled().getHigh())
				.append("low", mStock.getSelled().getLow())
				.append("close", mStock.getSelled().getClose())
				.append("volume", mStock.getSelled().getVolume());

			}
			
			managedStockToStore= new BasicDBObject("userIdentifier",mStock.getUserIdentifier())
												.append("codeName", mStock.getCodeName())
												.append("sector", mStock.getSector())
												.append("profitValue", mStock.getProfitValue())
												.append("profitPercent", mStock.getProfitPercent())
												.append("candleSticks", candlestickToStore)
												.append("buylled", buylledToStore)
												.append("selled", selledToStore)
												.append("qtdStocksBought", mStock.getQtdStocksBought());
			
			this.collection_managedStock.remove(managedStockStored);
			this.collection_managedStock.insert(managedStockToStore);
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		
		
		
		
		return true;
	}
	
	public ManagedStock getManagedStock(String stockCodeName,String userIdentifier)
	{
		ManagedStock result=null;
		
		BasicDBObject where =null;
		BasicDBObject managedStockStored=null;
		ArrayList<BasicDBObject>candleSticksStored=null;
		BasicDBObject selledStored=null;
		BasicDBObject buyedStored=null;
		
		DBCursor cursor=null;
		
		CandleStick buyed=null;
		CandleStick selled=null;
		ArrayList<CandleStick> candlesticks=null;
		
		try
		{
			where = new BasicDBObject("userIdentifier",userIdentifier)
									.append("codeName", stockCodeName);
			
			cursor=this.collection_managedStock.find(where);
			
			while(cursor.hasNext())
			{
				managedStockStored=(BasicDBObject)cursor.next();
			}
			if(managedStockStored!=null)
			{
				selledStored=(BasicDBObject)managedStockStored.get("selled");
				buyedStored=(BasicDBObject)managedStockStored.get("buyed");
				candleSticksStored=(ArrayList<BasicDBObject>)managedStockStored.get("candleSticks");
				
				if(candleSticksStored.size()>0)
				{
					candlesticks=new ArrayList<CandleStick>();
					for(BasicDBObject b:candleSticksStored)
					{
						candlesticks.add(new CandleStick(b.getDouble("open"), 
											b.getDouble("high"),
											b.getDouble("low"),
											b.getDouble("close"),
											b.getDouble("volume"), 
											b.getDate("date")));
					}
					
				}
				buyed=new CandleStick(buyedStored.getDouble("open"), buyedStored.getDouble("high"),
										 buyedStored.getDouble("low"), 
										 buyedStored.getDouble("close"), 
										 buyedStored.getDouble("volume"), 
										 buyedStored.getDate("date"));
				
				selled= new CandleStick(selledStored.getDouble("open"),
						selledStored.getDouble("high"),
						selledStored.getDouble("low"), 
						selledStored.getDouble("close"), 
						selledStored.getDouble("volume"), 
						selledStored.getDate("date"));
				
				result= new ManagedStock();
				
				result.setUserIdentifier(managedStockStored.getString(userIdentifier));
				result.setCodeName(managedStockStored.getString("codeName"));
				result.setSector(managedStockStored.getString("sector"));
				result.setProfitPercent(managedStockStored.getDouble("profitPercent"));
				result.setProfitValue(managedStockStored.getDouble("profitValue"));
				result.setQtdStocksBought(managedStockStored.getInt("qtdStocksBought"));
				result.setBuyed(buyed);
				result.setSelled(selled);
				result.setCandleSticks(candlesticks);
			}
			
			
			
		}catch (Exception e)
		
		{
			e.printStackTrace();
			return null;
			
		}
		
		
		
		return result;
	}
	
	public ArrayList<ManagedStock> getManagedStock(String userIdentifier)
	{
		ManagedStock managedStock=null;
		
		ArrayList<ManagedStock> result;
		
		BasicDBObject where =null;
		BasicDBObject managedStockStored=null;
		ArrayList<BasicDBObject>candleSticksStored=null;
		BasicDBObject selledStored=null;
		BasicDBObject buyedStored=null;
		
		DBCursor cursor=null;
		
		CandleStick buyed=null;
		CandleStick selled=null;
		ArrayList<CandleStick> candlesticks=null;
		
		try
		{
			where = new BasicDBObject("userIdentifier",userIdentifier);
									
			result= new ArrayList<ManagedStock>();
			
			cursor=this.collection_managedStock.find(where);
			
			while(cursor.hasNext())
			{
				managedStockStored=(BasicDBObject)cursor.next();
			
			if(managedStockStored!=null)
			{
				selledStored=(BasicDBObject)managedStockStored.get("selled");
				buyedStored=(BasicDBObject)managedStockStored.get("buyed");
				candleSticksStored=(ArrayList<BasicDBObject>)managedStockStored.get("candleSticks");
				
				if(candleSticksStored.size()>0)
				{
					candlesticks=new ArrayList<CandleStick>();
					for(BasicDBObject b:candleSticksStored)
					{
						candlesticks.add(new CandleStick(b.getDouble("open"), 
											b.getDouble("high"),
											b.getDouble("low"),
											b.getDouble("close"),
											b.getDouble("volume"), 
											b.getDate("date")));
					}
					
				}
				buyed=new CandleStick(buyedStored.getDouble("open"), buyedStored.getDouble("high"),
										 buyedStored.getDouble("low"), 
										 buyedStored.getDouble("close"), 
										 buyedStored.getDouble("volume"), 
										 buyedStored.getDate("date"));
				
				selled= new CandleStick(selledStored.getDouble("open"),
						selledStored.getDouble("high"),
						selledStored.getDouble("low"), 
						selledStored.getDouble("close"), 
						selledStored.getDouble("volume"), 
						selledStored.getDate("date"));
				
				managedStock= new ManagedStock();
				
				managedStock.setUserIdentifier(managedStockStored.getString(userIdentifier));
				
				managedStock.setSector(managedStockStored.getString("sector"));
				managedStock.setProfitPercent(managedStockStored.getDouble("profitPercent"));
				managedStock.setProfitValue(managedStockStored.getDouble("profitValue"));
				managedStock.setQtdStocksBought(managedStockStored.getInt("qtdStocksBought"));
				managedStock.setBuyed(buyed);
				managedStock.setSelled(selled);
				managedStock.setCandleSticks(candlesticks);
				
				result.add(managedStock);
				
			}
			
			}//fim while
			
		}catch (Exception e)
		
		{
			e.printStackTrace();
			return null;
			
		}
		
		
		
		return result;
	}
	

}
