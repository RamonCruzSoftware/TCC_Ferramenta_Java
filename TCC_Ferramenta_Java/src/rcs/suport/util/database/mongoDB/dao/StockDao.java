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
	
	private DBCollection collection_stock_prices;
	private DBCollection collection_stocks;
	private DBCollection collection_userStockSugestions;
	
	
	public StockDao()
	{
		super();
		try
		{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			this.collection_stock_prices=db.getCollection("stocks_prices");
			this.collection_stocks=db.getCollection("stocks");
			this.collection_userStockSugestions=db.getCollection("stock");
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean insertStocksSuggestion(Stock stock,String userIdentifier)
	{
		//colocar criteria 
		try
		{
			BasicDBObject where = new BasicDBObject("codeName",stock.getCodeName()).append("userId", userIdentifier);
			DBCursor cursor=collection_userStockSugestions.find(where);

			if(cursor.count()==0)
			{
			
				BasicDBObject suggestion= new BasicDBObject()
				.append("codeName",stock.getCodeName())
				.append("avarangeReturn_15", stock.getAvarangeReturn_15())
				.append("avarangeReturn_30", stock.getAvarangeReturn_30())
				.append("standardDeviation_15", stock.getStandardDeviation_15())
				.append("standardDeviation_30", stock.getStandardDeviation_30())
				.append("varianceCoeffientt_15", stock.getVarianceCoefficient_15())
				.append("varianceCoeffientt_30", stock.getVarianceCoefficient_30())
				.append("variance_15", stock.getVariance_15())
				.append("variance_30", stock.getVariance_30())
				.append("userId",userIdentifier)
				.append("version", 0);
				
				collection_userStockSugestions.insert(suggestion);
				return true;
			}else return false;
			
		}catch (MongoException.DuplicateKey e)
		{
			return false;
		}
		
		
		
	}
	public void updateStock(Stock stock)
	{	
		BasicDBObject where=new BasicDBObject("_id",stock.getCodeName());
		DBObject stockValues=null;
		DBObject stocks=null;
			
		
		ArrayList<BasicDBObject> candleSticksList=new ArrayList<BasicDBObject>();
		
		DBCursor cursorStock=collection_stocks.find(where);
		DBCursor cursorStockPrices=collection_stock_prices.find(where);
		
		while(cursorStock.hasNext())
		{
			stocks=cursorStock.next();
			
		}
		while(cursorStockPrices.hasNext())
		{
			stockValues=cursorStockPrices.next();
			
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
		
		BasicDBObject updateStockPrices = new BasicDBObject("_id",stock.getCodeName()).append("values", candleSticksList);
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
						
			collection_stocks.remove(stocks);
			collection_stock_prices.remove(stockValues);
			
			collection_stocks.insert(updateStock);
			collection_stock_prices.insert(updateStockPrices);
			
			
		
	}
	
	public boolean insertCurrentStock(Stock stock)
	{
		BasicDBObject where=new BasicDBObject("_id",stock.getCodeName());
		
		DBObject stockStored=null;
		DBObject stockToStore=null;
		
		DBObject stockStoredPrices=null;
		DBObject stockPricesToStore=null;
		
		
		ArrayList<BasicDBObject> stockPricesToStoreList=new ArrayList<BasicDBObject>();
		ArrayList<BasicDBObject> stockPricesStoredList=new ArrayList<BasicDBObject>();
		
		
		DBCursor cursorStock=collection_stocks.find(where);
		DBCursor cursorStockPrices=collection_stock_prices.find(where);
		
		while(cursorStock.hasNext())
		{
			stockStored=cursorStock.next();
			
		}
		
		while(cursorStockPrices.hasNext())
		{
			stockStoredPrices=cursorStockPrices.next();
		}
		
		
		 stockToStore=new BasicDBObject("_id",stock.getCodeName())
													.append("sector", stock.getSector())
													.append("avarangeReturn_15", stock.getAvarangeReturn_15())
													.append("standardDeviation_15", stock.getStandardDeviation_15())
													.append("variance_15", stock.getVariance_15())
													.append("varianceCoefficient_15", stock.getVarianceCoefficient_15())
													.append("avarangeReturn_30", stock.getAvarangeReturn_30())
													.append("standardDeviation_30", stock.getStandardDeviation_30())
													.append("variance_30", stock.getVariance_30())
													.append("varianceCoefficient_30", stock.getVarianceCoefficient_30());
													
	
		stockPricesStoredList =(ArrayList<BasicDBObject>)stockStoredPrices.get("values");
		
		//Verifico se a candle ja existe persistida no banco de dados 
		if(stockPricesStoredList.get(stockPricesStoredList.size()-1).get("date").toString().
				equalsIgnoreCase(stock.getCurrentCandleStick().getDate().toString()))
		{
			
			return false;
		
		}else
		{
			for(BasicDBObject c:stockPricesStoredList)
			{
				
				stockPricesToStoreList.add(c);
				
			}
			stockPricesToStoreList.add(new BasicDBObject("_id",stock.getCodeName()).
					append("date",stock.getCurrentCandleStick().getDate()).
					append("open",stock.getCurrentCandleStick().getOpen()).
					append("high",stock.getCurrentCandleStick().getHigh()).
					append("low", stock.getCurrentCandleStick().getLow()).
					append("close", stock.getCurrentCandleStick().getClose()).
					append("volume", stock.getCurrentCandleStick().getVolume()));
			
			stockPricesToStore = new BasicDBObject("_id",stock.getCodeName()).
					append("values", stockPricesToStoreList);
			
			collection_stocks.remove(stockStored);
			collection_stocks.insert(stockToStore);
			
			collection_stock_prices.remove(stockStoredPrices);
			collection_stock_prices.insert(stockPricesToStore);
		
			return true;
		}
	
	}
	
	public boolean storeHistoricalStockValue(Stock stock)
	{
		BasicDBObject newStock=new BasicDBObject("_id",stock.getCodeName()).append("sector", stock.getSector());
		BasicDBObject stockPrices =null;
		
		ArrayList<BasicDBObject> list=new ArrayList<BasicDBObject>();
		
		for(CandleStick c:stock.getCandleSticks())
		{
			
			list.add(new BasicDBObject("date",c.getDate()).append("open",c.getOpen()).append("high",c.getHigh()).append("low", c.getLow()).append("close", c.getClose()).append("volume", c.getVolume()));
		}
		
		stockPrices =new BasicDBObject("_id",stock.getCodeName()).append("values", list);
		
		try
		{
			collection_stock_prices.insert(stockPrices);
			collection_stocks.insert(newStock);
			
			return true;
			
		}catch(MongoException.DuplicateKey e)
		{
			return false;
		}
		
	}
	/**
	 * Get only statistical values of the stocks 
	 * @return
	 */
	
	public ArrayList<Stock> getAllStocks()
	{
		
		DBCursor cursor= collection_stocks.find();
		
		DBObject mongo_stock=null;
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		
		
		Stock stock=null;
		
		
		while(cursor.hasNext())
		{
			
			mongo_stock=cursor.next();
			stock=new Stock(mongo_stock.get("_id").toString(), mongo_stock.get("sector").toString());
			
			stock.setAvarangeReturn_15(Double.parseDouble( mongo_stock.get("avarangeReturn_15").toString()));
			stock.setAvarangeReturn_30(Double.parseDouble( mongo_stock.get("avarangeReturn_30").toString()));
			
			stock.setStandardDeviation_15(Double.parseDouble(mongo_stock.get("standardDeviation_15").toString()));
			stock.setStandardDeviation_30(Double.parseDouble(mongo_stock.get("standardDeviation_30").toString()));
			
			stock.setVariance_15(Double.parseDouble(mongo_stock.get("variance_15").toString()));
			stock.setVariance_30(Double.parseDouble(mongo_stock.get("variance_30").toString()));	
			
			stock.setVarianceCoefficient_15(Double.parseDouble(mongo_stock.get("varianceCoefficient_15").toString()));
			stock.setVarianceCoefficient_30(Double.parseDouble(mongo_stock.get("varianceCoefficient_30").toString()));

			stockList.add(stock);
			
		}
		
		return stockList;
		
	}
	
	/**
	 * Get only values of the stocks in dataBase
	 * @return
	 */
	
	public ArrayList<Stock> getAllStocksPrices()
	{
		DBCursor cursor= collection_stock_prices.find();
		
		DBObject mongo_stock=null;
		ArrayList<BasicDBObject> mongo_candleList=null;
		ArrayList<Stock> stockPricesList= new ArrayList<Stock>();
		Stock stock=null;
		while(cursor.hasNext())
		{
			mongo_stock=cursor.next();
			mongo_candleList=(ArrayList<BasicDBObject>) mongo_stock.get("values");
			stock=new Stock(mongo_stock.get("_id").toString(), null);
			
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
			stockPricesList.add(stock);
			
		}
		
		return stockPricesList;
	}
	
	public ArrayList<CandleStick> getStockPrices_last10(String codeName)
	{
		BasicDBObject where = new BasicDBObject("_id",codeName);
		DBCursor cursor= collection_stock_prices.find(where);
		
		DBObject mongo_stock=null;
		ArrayList<BasicDBObject> mongo_candleList=null;
		ArrayList<Stock> stockPricesList= new ArrayList<Stock>();
		
		
		while(cursor.hasNext())
		{
			mongo_stock=cursor.next();
			
		}
			mongo_candleList=(ArrayList<BasicDBObject>) mongo_stock.get("values");
			
			//Pega todo historico existente no banco de dados
			ArrayList<CandleStick>candleList=new ArrayList<CandleStick>();
			
			for(int i=(mongo_candleList.size()-10);i<(mongo_candleList.size());i++)
			{
				
				candleList.add(new CandleStick(
												mongo_candleList.get(i).getDouble("open"), 
												mongo_candleList.get(i).getDouble("high"), mongo_candleList.get(i).getDouble("low"),
												mongo_candleList.get(i).getDouble("close"), mongo_candleList.get(i).getInt("volume"), 
												mongo_candleList.get(i).getDate("date"))
											);
			}
			
			
	
		return candleList;
	}
	public ArrayList<CandleStick> getStockPrices_last30(String codeName)
	{
		BasicDBObject where = new BasicDBObject("_id",codeName);
		DBCursor cursor= collection_stock_prices.find(where);
		
		DBObject mongo_stock=null;
		ArrayList<BasicDBObject> mongo_candleList=null;
		ArrayList<Stock> stockPricesList= new ArrayList<Stock>();
		
		
		while(cursor.hasNext())
		{
			mongo_stock=cursor.next();
			
		}
			mongo_candleList=(ArrayList<BasicDBObject>) mongo_stock.get("values");
			
			//Pega todo historico existente no banco de dados
			ArrayList<CandleStick>candleList=new ArrayList<CandleStick>();
			
			for(int i=(mongo_candleList.size()-30);i<(mongo_candleList.size());i++)
			{
				
				candleList.add(new CandleStick(
												mongo_candleList.get(i).getDouble("open"), 
												mongo_candleList.get(i).getDouble("high"), mongo_candleList.get(i).getDouble("low"),
												mongo_candleList.get(i).getDouble("close"), mongo_candleList.get(i).getInt("volume"), 
												mongo_candleList.get(i).getDate("date"))
											);
			}
			
			
	
		return candleList;
	}
	public ArrayList<CandleStick> getStockPrices_last40(String codeName)
	{
		BasicDBObject where = new BasicDBObject("_id",codeName);
		DBCursor cursor= collection_stock_prices.find(where);
		
		DBObject mongo_stock=null;
		ArrayList<BasicDBObject> mongo_candleList=null;
		ArrayList<Stock> stockPricesList= new ArrayList<Stock>();
		
		
		while(cursor.hasNext())
		{
			mongo_stock=cursor.next();
			
		}
			mongo_candleList=(ArrayList<BasicDBObject>) mongo_stock.get("values");
			
			//Pega todo historico existente no banco de dados
			ArrayList<CandleStick>candleList=new ArrayList<CandleStick>();
			
			if(mongo_candleList.size()>=40)
			{
				for(int i=(mongo_candleList.size()-40);i<(mongo_candleList.size());i++)
				{
					
					candleList.add(new CandleStick(
													mongo_candleList.get(i).getDouble("open"), 
													mongo_candleList.get(i).getDouble("high"), mongo_candleList.get(i).getDouble("low"),
													mongo_candleList.get(i).getDouble("close"), mongo_candleList.get(i).getInt("volume"), 
													mongo_candleList.get(i).getDate("date"))
												);
				}
			}else
			{
				for(int i=0;i<(mongo_candleList.size());i++)
				{
					
					candleList.add(new CandleStick(
													mongo_candleList.get(i).getDouble("open"), 
													mongo_candleList.get(i).getDouble("high"), mongo_candleList.get(i).getDouble("low"),
													mongo_candleList.get(i).getDouble("close"), mongo_candleList.get(i).getInt("volume"), 
													mongo_candleList.get(i).getDate("date"))
												);
				}
			}
			
			
			
	
		return candleList;
	}
	
	public ArrayList<Stock> getStockOrderByStandardDeviation_30(double lowerLimit,double upperLimit)
	{
		BasicDBObject sort=new BasicDBObject("standardDeviation_30",1);
		BasicDBObject find_1=new BasicDBObject("$gt",lowerLimit).append("$lt", upperLimit);
		BasicDBObject find_2=new BasicDBObject("standardDeviation_30", find_1);
		
		DBCursor cursor= collection_stocks.find(find_2).sort(sort);
		
		DBObject mongo_stock=null;
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		Stock stock=null;
		
		while(cursor.hasNext())
		{
			
			mongo_stock=cursor.next();
			stock=new Stock(mongo_stock.get("_id").toString(), null);
			
			stock.setAvarangeReturn_15(Double.parseDouble( mongo_stock.get("avarangeReturn_15").toString()));
			stock.setAvarangeReturn_30(Double.parseDouble( mongo_stock.get("avarangeReturn_30").toString()));
			
			stock.setStandardDeviation_15(Double.parseDouble(mongo_stock.get("standardDeviation_15").toString()));
			stock.setStandardDeviation_30(Double.parseDouble(mongo_stock.get("standardDeviation_30").toString()));
			
			stock.setVariance_15(Double.parseDouble(mongo_stock.get("variance_15").toString()));
			stock.setVariance_30(Double.parseDouble(mongo_stock.get("variance_30").toString()));	
			
			stock.setVarianceCoefficient_15(Double.parseDouble(mongo_stock.get("varianceCoefficient_15").toString()));
			stock.setVarianceCoefficient_30(Double.parseDouble(mongo_stock.get("varianceCoefficient_30").toString()));

			stockList.add(stock);
			
		}
		
		return stockList;
	}
	public ArrayList<Stock> getStockOrderByStandardDeviation_15()
	{
		BasicDBObject sort=new BasicDBObject("standardDeviation_15",1);
		DBCursor cursor= collection_stocks.find().sort(sort);
		
		DBObject mongo_stock=null;
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		Stock stock=null;
		
		while(cursor.hasNext())
		{
			
			mongo_stock=cursor.next();
			stock=new Stock(mongo_stock.get("_id").toString(), null);
			
			stock.setAvarangeReturn_15(Double.parseDouble( mongo_stock.get("avarangeReturn_15").toString()));
			stock.setAvarangeReturn_30(Double.parseDouble( mongo_stock.get("avarangeReturn_30").toString()));
			
			stock.setStandardDeviation_15(Double.parseDouble(mongo_stock.get("standardDeviation_15").toString()));
			stock.setStandardDeviation_30(Double.parseDouble(mongo_stock.get("standardDeviation_30").toString()));
			
			stock.setVariance_15(Double.parseDouble(mongo_stock.get("variance_15").toString()));
			stock.setVariance_30(Double.parseDouble(mongo_stock.get("variance_30").toString()));	
			
			stock.setVarianceCoefficient_15(Double.parseDouble(mongo_stock.get("varianceCoefficient_15").toString()));
			stock.setVarianceCoefficient_30(Double.parseDouble(mongo_stock.get("varianceCoefficient_30").toString()));

			stockList.add(stock);
			
		}
		
		return stockList;
	}

}
