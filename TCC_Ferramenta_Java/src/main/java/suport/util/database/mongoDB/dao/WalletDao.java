package suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import suport.financial.wallet.Stock;
import suport.financial.wallet.Wallet;
import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.ManagedWallet;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import jade.util.leap.Serializable;

public class WalletDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DBCollection collection_wallet;
	private MongoConnection connection;
	private DB db;

	public WalletDao() {
		this.connection= MongoConnection.getInstance();
		this.db=this.connection.getDB();
		this.collection_wallet=(this.db.getCollection("wallet"));

	}
	public void updateRiskWallet(String userId,double risk)
	{
		boolean result = false;
		try {
			BasicDBObject where = new BasicDBObject("userId", userId);
		
			DBObject wallet = null;

			DBCursor cursorWallet = this.collection_wallet.find(where);
		

			while (cursorWallet.hasNext()) {
				wallet = cursorWallet.next();

			}
		

			BasicDBObject updateStock = new BasicDBObject("userId",userId)
			.append("walletValue",Double.parseDouble(wallet.get("walletValue").toString()))
			.append("walletRisck", risk)
			.append("walltetPercent", Double.parseDouble(wallet.get("walltetPercent").toString()));
					

			this.collection_wallet.remove(wallet);

			// getCollection_stock_prices().remove(stockValues);

			this.collection_wallet.insert(updateStock);
			// getCollection_stock_prices().insert(updateStockPrices);
			result = true;

			cursorWallet.close();
			// cursorStockPrices.close();

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		
	}
	public boolean insertWallet(Wallet walletInfo) {
		
		BasicDBObject wallet = null;
		try {
			
			wallet = new BasicDBObject("userId", walletInfo.getUserID())
					.append("walletValue",walletInfo.getWalletValue())
					.append("walletRisck", walletInfo.getWalletRisck())
					.append("walltetPercent", walletInfo.getWallterPercent());

			this.collection_wallet.save(wallet);

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

}
