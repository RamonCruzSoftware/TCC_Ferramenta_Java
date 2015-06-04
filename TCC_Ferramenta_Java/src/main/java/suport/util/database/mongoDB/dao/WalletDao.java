package suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import suport.financial.wallet.Stock;
import suport.financial.wallet.Wallet;
import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.ManagedWallet;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

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
