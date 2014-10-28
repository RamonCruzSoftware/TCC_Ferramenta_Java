package rcs.suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.MongoConnection;
import rcs.suport.util.database.mongoDB.pojo.ManagedWallet;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class ManagedWalletDao 
{
	private DBCollection collection_managedWallet;
	private MongoConnection connection;
	private DB db;
	
	public ManagedWalletDao()
	{
		this.connection=MongoConnection.getInstance();
		this.db=connection.getDB();
		this.collection_managedWallet=db.getCollection("managedWallet");
		
	}
	
	public boolean insertManagedWalletInfo(ManagedWallet info)
	{
		BasicDBObject manageWallet=null;
		ArrayList<BasicDBObject>stocksList=null;
		
		
		try
		{
			stocksList= new ArrayList<BasicDBObject>();
			
			for(Stock s:info.getStocksList())
			{
				stocksList.add(new BasicDBObject("codeName",s.getCodeName()).append("sector", s.getSector()));
				
			}
			manageWallet= new BasicDBObject("userId",info.getUserID())
											.append("walletProfitePercent", info.getWalletProfitPercent())
											.append("walletProfitValue", info.getWalletProfitValue())
											.append("walletRisck", info.getWalletRisck())
											.append("walletValue", info.getWalletValue())
											.append("stocksList",stocksList);
			
			this.collection_managedWallet.insert(manageWallet);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
			
		}
		
		return true;
		
	}
	public boolean updateManagedWallet(ManagedWallet info)
	{
		BasicDBObject where=null;
		BasicDBObject manageWalletStored=null;
		DBCursor cursor=null;
		
		BasicDBObject manageWalletToStore=null;
		ArrayList<BasicDBObject> stockListToStore=null;
		
		try
		{
			where=new BasicDBObject("userId",info.getUserID());
			cursor=this.collection_managedWallet.find(where);
			
			while(cursor.hasNext())
			{
				manageWalletStored=(BasicDBObject)cursor.next();
			}
			
			stockListToStore= new ArrayList<BasicDBObject>();
			for(Stock s:info.getStocksList())
			{
				stockListToStore.add(new BasicDBObject("codeName",s.getCodeName()).append("sector", s.getSector()));
			}
			
			manageWalletToStore = new BasicDBObject("userId",info.getUserID())
										.append("walletProfitePercent", info.getWalletProfitPercent())
										.append("walletProfitValue", info.getWalletProfitValue())
										.append("walletRisck", info.getWalletRisck())
										.append("walletValue", info.getWalletValue())
										.append("stocksList",stockListToStore);
			
			this.collection_managedWallet.remove(manageWalletStored);
			this.collection_managedWallet.insert(manageWalletToStore);
			
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public ManagedWallet getManagedWallet(String userIdentifier)
	{
		ManagedWallet result=null;
		BasicDBObject where=null;
		BasicDBObject managedWalletStored =null;
		DBCursor cursor=null;
		
		ArrayList<BasicDBObject> stocksListStored=null;
		ArrayList<Stock> stockList=null;
		
		try
		{
			where=new BasicDBObject("userId",userIdentifier);
			cursor= this.collection_managedWallet.find(where);
			
			while(cursor.hasNext())
			{
				managedWalletStored=(BasicDBObject)cursor.next();
			}
			stocksListStored=(ArrayList<BasicDBObject>)managedWalletStored.get("stocksList");
			stockList=new ArrayList<Stock>();
			
			for(BasicDBObject b: stocksListStored)
			{
				stockList.add(new Stock(b.getString("codeName"), b.getString("sector")));
			}
			
			result= new ManagedWallet();
			result.setUserID(managedWalletStored.getString("userId"));
			result.setWalletValue(managedWalletStored.getDouble("walletValue"));
			result.setWalletRisck(managedWalletStored.getDouble("walletRisck"));
			result.setWalletProfitValue(managedWalletStored.getDouble("walletProfitValue"));
			result.setWalletProfitPercent(managedWalletStored.getDouble("walletProfitPercent"));
			result.setStocksList(stockList);
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
		
}
