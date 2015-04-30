package suport.util.database.mongoDB.daoTest;

import java.util.Date;
import junit.framework.Assert;
import suport.financial.partternsCandleStick.CandleStick;
import suport.util.database.mongoDB.dao.InfoStrategiesDao;
import suport.util.database.mongoDB.pojo.InfoStrategies;

public class InfoStrategiesDaoTest {

	private InfoStrategiesDao infoStrategiesDao;

	public void setUp() {

		infoStrategiesDao = new InfoStrategiesDao();

	}

	public void takeDown() {

		InfoStrategies infoFind = new InfoStrategies();

		infoFind.setPeriodicity("test");
		infoFind.setStockCodeName("unitTtest");
		infoFind.setUserIdentifier("junit");
		infoFind.setStrategyName("strategieTest");

		infoStrategiesDao.dropInfoStrategy(infoFind);

	}

	public void testInfoStrategiesDao() {

		Assert.assertEquals(infoStrategiesDao.getClass(),
				InfoStrategiesDao.class);
		Assert.assertNotNull(infoStrategiesDao.getCollection_infoStrategies());

	}

	public void testDropInfoStrategy() {
		InfoStrategies info = new InfoStrategies();

		InfoStrategies infoFind = new InfoStrategies();
		InfoStrategies recuvered = null;

		info.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		info.setPeriodicity("test");
		info.setStockCodeName("unitTtest");
		info.setUserIdentifier("junit");
		info.setStrategyName("strategieTest");

		infoFind.setPeriodicity("test");
		infoFind.setStockCodeName("unitTtest");
		infoFind.setUserIdentifier("junit");
		infoFind.setStrategyName("strategieTest");

		infoStrategiesDao.insertInfoStrategy(info);
		recuvered = infoStrategiesDao.getInfoStrategy(infoFind);

		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(),
				recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(),
				recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(),
				recuvered.getStockCodeName());

		infoStrategiesDao.dropInfoStrategy(info);

		recuvered = infoStrategiesDao.getInfoStrategy(infoFind);
		Assert.assertNull(recuvered);

	}

	public void testInsertInfoStrategy() {
		InfoStrategies info = new InfoStrategies();

		InfoStrategies infoFind = new InfoStrategies();
		InfoStrategies recuvered = null;

		info.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		info.setPeriodicity("test");
		info.setStockCodeName("unitTtest");
		info.setUserIdentifier("junit");
		info.setStrategyName("strategieTest");

		infoFind.setPeriodicity("test");
		infoFind.setStockCodeName("unitTtest");
		infoFind.setUserIdentifier("junit");
		infoFind.setStrategyName("strategieTest");

		infoStrategiesDao.insertInfoStrategy(info);
		recuvered = infoStrategiesDao.getInfoStrategy(infoFind);

		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(),
				recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(),
				recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(),
				recuvered.getStockCodeName());

	}

	public void testUpdateInfoStrategy() {
		InfoStrategies info = new InfoStrategies();

		InfoStrategies infoFind = new InfoStrategies();
		InfoStrategies recuvered = null;

		info.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		info.setPeriodicity("test");
		info.setStockCodeName("unitTtest");
		info.setUserIdentifier("junit");
		info.setStrategyName("strategieTest");

		infoFind.setPeriodicity("test");
		infoFind.setStockCodeName("unitTtest");
		infoFind.setUserIdentifier("junit");
		infoFind.setStrategyName("strategieTest");

		infoStrategiesDao.insertInfoStrategy(info);
		recuvered = infoStrategiesDao.getInfoStrategy(infoFind);

		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(),
				recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(),
				recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(),
				recuvered.getStockCodeName());

		info.setSelled(new CandleStick(11, 11, 11, 11, 11, new Date()));

		infoStrategiesDao.updateInfoStrategy(info);
		recuvered = infoStrategiesDao.getInfoStrategy(infoFind);

		Assert.assertNotNull(recuvered.getSelled());

	}

	public void testGetInfoStrategy() {

		InfoStrategies info = new InfoStrategies();

		InfoStrategies infoFind = new InfoStrategies();
		InfoStrategies recuvered = null;

		info.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		info.setPeriodicity("test");
		info.setStockCodeName("unitTtest");
		info.setUserIdentifier("junit");
		info.setStrategyName("strategieTest");

		infoFind.setPeriodicity("test");
		infoFind.setStockCodeName("unitTtest");
		infoFind.setUserIdentifier("junit");
		infoFind.setStrategyName("strategieTest");

		infoStrategiesDao.insertInfoStrategy(info);
		recuvered = infoStrategiesDao.getInfoStrategy(infoFind);

		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(),
				recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(),
				recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(),
				recuvered.getStockCodeName());

	}

}
