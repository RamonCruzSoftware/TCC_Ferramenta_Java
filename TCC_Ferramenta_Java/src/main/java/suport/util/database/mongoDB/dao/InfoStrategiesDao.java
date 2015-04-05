package suport.util.database.mongoDB.dao;

import suport.financial.partternsCandleStick.CandleStick;
import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.InfoStrategies;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;

public class InfoStrategiesDao {

	private DBCollection collection_infoStrategies;
	private MongoConnection connection;
	private DB db;

	public InfoStrategiesDao() {
		try {
			this.setConnection(MongoConnection.getInstance());
			this.setDb(this.getConnection().getDB());

			this.setCollection_infoStrategies(getDb().getCollection(
					"JADE_infoStrategies"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean insertInfoStrategy(InfoStrategies info) {
		BasicDBObject buyed = new BasicDBObject("date", info.getBuyed()
				.getDate()).append("high", info.getBuyed().getHigh())
				.append("low", info.getBuyed().getLow())
				.append("close", info.getBuyed().getClose())
				.append("open", info.getBuyed().getOpen())
				.append("volume", info.getBuyed().getVolume());

		BasicDBObject insertInfo = new BasicDBObject("userIdentifier",
				info.getUserIdentifier())
				.append("strategyName", info.getStrategyName())
				.append("stockCodeName", info.getStockCodeName())
				.append("periodicity", info.getPeriodicity())
				.append("buyed", buyed);

		this.getCollection_infoStrategies().insert(insertInfo);

		try {

		} catch (MongoException.DuplicateKey e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean updateInfoStrategy(InfoStrategies info) {
		BasicDBObject where = null;
		BasicDBObject infoStrategyStored = null;
		BasicDBObject infoStrategyToStore = null;
		BasicDBObject selled = null;
		DBCursor cursor = null;

		where = new BasicDBObject("userIdentifier", info.getUserIdentifier())
				.append("strategyName", info.getStrategyName())
				.append("stockCodeName", info.getStockCodeName())
				.append("periodicity", info.getPeriodicity());

		try {
			cursor = this.getCollection_infoStrategies().find(where);
			while (cursor.hasNext()) {
				infoStrategyStored = (BasicDBObject) cursor.next();
			}

			selled = new BasicDBObject("date", info.getSelled().getDate())
					.append("high", info.getSelled().getHigh())
					.append("low", info.getSelled().getLow())
					.append("close", info.getSelled().getClose())
					.append("open", info.getSelled().getOpen())
					.append("volume", info.getSelled().getVolume());

			infoStrategyToStore = new BasicDBObject("userIdentifier",
					info.getUserIdentifier())
					.append("strategyName", info.getStrategyName())
					.append("stockCodeName", info.getStockCodeName())
					.append("periodicity",
							infoStrategyStored.get("periodicity"))
					.append("buyed", infoStrategyStored.get("buyed"))
					.append("selled", selled)
					.append("profit", info.getProfit());

			this.getCollection_infoStrategies().remove(infoStrategyStored);
			this.getCollection_infoStrategies().insert(infoStrategyToStore);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public InfoStrategies getInfoStrategy(InfoStrategies info) {
		InfoStrategies result = null;
		BasicDBObject where = null;
		BasicDBObject infoStored = null;
		BasicDBObject buyedStored = null;
		BasicDBObject selledStored = null;
		DBCursor cursor = null;

		CandleStick buyed = null;
		CandleStick selled = null;

		try {
			where = new BasicDBObject("userIdentifier",
					info.getUserIdentifier())
					.append("strategyName", info.getStrategyName())
					.append("stockCodeName", info.getStockCodeName())
					.append("periodicity", info.getPeriodicity());

			cursor = this.getCollection_infoStrategies().find(where);

			while (cursor.hasNext()) {
				infoStored = (BasicDBObject) cursor.next();
			}
			buyedStored = (BasicDBObject) infoStored.get("buyed");
			selledStored = (BasicDBObject) infoStored.get("selled");

			if (buyedStored != null) {
				buyed = new CandleStick(buyedStored.getDouble("open"),
						buyedStored.getDouble("high"),
						buyedStored.getDouble("low"),
						buyedStored.getDouble("close"),
						buyedStored.getDouble("volume"),
						buyedStored.getDate("date"));
			}

			if (selledStored != null) {
				selled = new CandleStick(selledStored.getDouble("open"),
						selledStored.getDouble("high"),
						selledStored.getDouble("low"),
						selledStored.getDouble("close"),
						selledStored.getDouble("volume"),
						selledStored.getDate("date"));

			}

			result = new InfoStrategies();
			result.setUserIdentifier(infoStored.getString("userIdentifier"));
			result.setStrategyName(infoStored.get("strategyName").toString());
			result.setStockCodeName(infoStored.get("stockCodeName").toString());
			result.setPeriodicity(infoStored.get("periodicity").toString());
			result.setProfit(infoStored.getDouble("profit"));
			result.setBuyed(buyed);
			result.setSelled(selled);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean dropInfoStrategy(InfoStrategies info) {
		boolean result = false;
		BasicDBObject where = null;
		BasicDBObject infoStored = null;

		DBCursor cursor = null;

		try {
			where = new BasicDBObject("userIdentifier",
					info.getUserIdentifier())
					.append("strategyName", info.getStrategyName())
					.append("stockCodeName", info.getStockCodeName())
					.append("periodicity", info.getPeriodicity());

			cursor = this.getCollection_infoStrategies().find(where);

			while (cursor.hasNext()) {
				infoStored = (BasicDBObject) cursor.next();
				if (infoStored != null) {
					collection_infoStrategies.remove(infoStored);
					result = true;
				} else
					result = false;

			}

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

	public DBCollection getCollection_infoStrategies() {
		return collection_infoStrategies;
	}

	public void setCollection_infoStrategies(
			DBCollection collection_infoStrategies) {
		this.collection_infoStrategies = collection_infoStrategies;
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
