package suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.ManagedWallet;

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
		this.setConnection(MongoConnection.getInstance());
		this.setDb(getConnection().getDB());
		this.setCollection_managedWallet(getDb().getCollection("JADE_managedWallet"));
		
	}
	
	public boolean insertManagedWalletInfo(ManagedWallet info)
	{
		BasicDBObject manageWallet=null;
		ArrayList<BasicDBObject>stocksList=null;
		
		
		try
		{
			stocksList= new ArrayList<BasicDBObject>();
			
			if(info.getStocksList()!=null)
			{
				for(Stock s:info.getStocksList())
				{
					stocksList.add(new BasicDBObject("codeName",s.getCodeName()).append("sector", s.getSector()));
					
				}
			}
			
			manageWallet= new BasicDBObject("userId",info.getUserID())
											.append("walletProfitPercent", info.getWalletProfitPercent())
											.append("walletProfitValue", info.getWalletProfitValue())
											.append("walletRisck", info.getWalletRisck())
											.append("walletValue", info.getWalletValue())
											.append("stocksList",stocksList);
			
			this.getCollection_managedWallet().insert(manageWallet);
			
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
			cursor=this.getCollection_managedWallet().find(where);
			
			while(cursor.hasNext())
			{
				manageWalletStored=(BasicDBObject)cursor.next();
			}
			
			if(info.getStocksList()!=null)
			{
				stockListToStore= new ArrayList<BasicDBObject>();
				for(Stock s:info.getStocksList())
				{
					stockListToStore.add(new BasicDBObject("codeName",s.getCodeName()).append("sector", s.getSector()));
				}
			}
			
			
			manageWalletToStore = new BasicDBObject("userId",info.getUserID())
										.append("walletProfitPercent", info.getWalletProfitPercent())
										.append("walletProfitValue", info.getWalletProfitValue())
										.append("walletRisck", info.getWalletRisck())
										.append("walletValue", info.getWalletValue())
										.append("stocksList",stockListToStore);
			
			this.getCollection_managedWallet().remove(manageWalletStored);
			this.getCollection_managedWallet().insert(manageWalletToStore);
			
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	@SuppressWarnings("unchecked")
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
			cursor= this.getCollection_managedWallet().find(where);
			
			while(cursor.hasNext())
			{
				managedWalletStored=(BasicDBObject)cursor.next();
			}
			stocksListStored=(ArrayList<BasicDBObject>)managedWalletStored.get("stocksList");
			
			
			if(managedWalletStored!=null )
			{
				
				if(stocksListStored!=null && stocksListStored.size()>0)
				{
					stockList=new ArrayList<Stock>();
					for(BasicDBObject b: stocksListStored)
					{
						stockList.add(new Stock(b.getString("codeName"), b.getString("sector")));
					}
				}
				
				result= new ManagedWallet();
				result.setUserID(managedWalletStored.getString("userId"));
				result.setWalletValue(managedWalletStored.getDouble("walletValue"));
				result.setWalletRisck(managedWalletStored.getDouble("walletRisck"));
				result.setWalletProfitValue(managedWalletStored.getDouble("walletProfitValue"));
				result.setWalletProfitPercent(managedWalletStored.getDouble("walletProfitPercent"));
				result.setStocksList(stockList);
			}
			
			
			
			
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	public boolean dropManagedWallet(ManagedWallet mWallet)
	{ 
		boolean result= false;
		
		BasicDBObject where=null;
		BasicDBObject managedWalletStored =null;
		DBCursor cursor=null;
		
		try
		{
			where=new BasicDBObject("userId",mWallet.getUserID());
			cursor= this.getCollection_managedWallet().find(where);
			
			while(cursor.hasNext())
			{
				managedWalletStored=(BasicDBObject)cursor.next();
				getCollection_managedWallet().remove(managedWalletStored);
				result=true;
			}
		
			
		}catch (Exception e)
		{
			e.printStackTrace();
			result=false;
		}
		
		return result;
		
	}

	public DBCollection getCollection_managedWallet() {
		return collection_managedWallet;
	}

	public void setCollection_managedWallet(DBCollection collection_managedWallet) {
		this.collection_managedWallet = collection_managedWallet;
	}

	public MongoConnection getConnection() {
		return connection;
	}

	public void setConnection(MongoConnection connection) {
		this.connection = connection;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}
		
}
