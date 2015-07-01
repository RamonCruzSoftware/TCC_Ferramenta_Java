package suport.util.database.mongoDB.dao;

import java.util.ArrayList;
import java.util.Date;

import suport.financial.wallet.Stock;
import suport.financial.wallet.Wallet;
import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.ManagedWallet;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import core.agents.suport.WalletManagerAuxiliary;
import jade.util.leap.Serializable;

public class WalletDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DBCollection collection_wallet;
	private DBCollection collection_risk;
	private MongoConnection connection;
	private DB db;

	public WalletDao() {
		this.connection= MongoConnection.getInstance();
		this.db=this.connection.getDB();
		this.collection_wallet=(this.db.getCollection("wallet"));
		this.collection_risk=(this.db.getCollection("JADE_risk"));
		

	}
	
	@SuppressWarnings("unchecked")
	public void updateRiskWallet(String userId,double risk, String codeName)
	{
		boolean result = false;
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		ArrayList<String> codeNames=null;
		StockDao stockDao = new StockDao();
		Date today= new Date();
		WalletManagerAuxiliary auxiliary= new WalletManagerAuxiliary();
		double riskValue=0;
		Stock aux=null;
		
		try {
			BasicDBObject whereRisk = new BasicDBObject("_id", userId);
			BasicDBObject where = new BasicDBObject("userId", userId);
			DBObject wallet = null;
			DBObject riskcollection = null;

			DBCursor cursorWallet = this.collection_wallet.find(where);
			DBCursor cursorRisk = this.collection_risk.find(where);
			
			BasicDBObject updateStock=null;

			while (cursorWallet.hasNext()) {
				wallet = cursorWallet.next();

			}
			while(cursorRisk.hasNext())
			{
				riskcollection=cursorRisk.next();
			}
			if(riskcollection!=null)
			{
				codeNames=(ArrayList<String>)riskcollection.get("stockCodeNames");
				
				if(codeNames.size()>0 && codeNames!=null)
				{
					boolean existe=false;
					for(String code: codeNames) existe=(code.equalsIgnoreCase(codeName)?true:false);
					if(!existe)codeNames.add(codeName);
					for(String code: codeNames)
					{
						aux=stockDao.getStock(code);
						aux.setCandleSticks(stockDao.getStockPrices_last30(code));
						stockList.add(aux);
						aux=null;
					}
					riskValue=auxiliary.calculeRisk(stockList);
					
					updateStock = new BasicDBObject("userId",userId)
					.append("walletValue",Double.parseDouble(wallet.get("walletValue").toString()))
					.append("walletRisck", riskValue)
					.append("walltetPercent", Double.parseDouble(wallet.get("walltetPercent").toString()));
					
				}else
				{
					codeNames= new ArrayList<String>();
					codeNames.add(codeName);
					BasicDBObject riskUpdate=new BasicDBObject();
					riskUpdate.append("_id", riskcollection.get("_id"));
					riskUpdate.append("stockCodeNames", codeNames);
					
					this.collection_risk.remove(riskcollection);
					this.collection_risk.save(riskUpdate);
					
					updateStock = new BasicDBObject("userId",userId)
					.append("walletValue",Double.parseDouble(wallet.get("walletValue").toString()))
					.append("walletRisck", risk)
					.append("walltetPercent", Double.parseDouble(wallet.get("walltetPercent").toString()));
					
					
				}
				
			}else
			{
				codeNames= new ArrayList<String>();
				codeNames.add(codeName);
				BasicDBObject riskUpdate=new BasicDBObject();
				riskUpdate.append("_id", userId);
				riskUpdate.append("stockCodeNames", codeNames);
				
				this.collection_risk.save(riskUpdate);
				
				updateStock = new BasicDBObject("userId",userId)
				.append("walletValue",Double.parseDouble(wallet.get("walletValue").toString()))
				.append("walletRisck", risk)
				.append("walltetPercent", Double.parseDouble(wallet.get("walltetPercent").toString()));
			}

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
		BasicDBObject risk=null;
		try {
			
			wallet = new BasicDBObject("userId", walletInfo.getUserID())
					.append("walletValue",walletInfo.getWalletValue())
					.append("walletRisck", walletInfo.getWalletRisck())
					.append("walltetPercent", walletInfo.getWallterPercent());

			risk= new BasicDBObject("_id",walletInfo.getUserID()).append("stockCodeNames", null);
			this.collection_wallet.save(wallet);
			this.collection_risk.save(risk);
			

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

}
