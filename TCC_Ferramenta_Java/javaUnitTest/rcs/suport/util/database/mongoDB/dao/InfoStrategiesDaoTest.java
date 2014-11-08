package rcs.suport.util.database.mongoDB.dao;

import static org.junit.Assert.*;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.util.database.mongoDB.pojo.InfoStrategies;

public class InfoStrategiesDaoTest {
	
	private InfoStrategiesDao infoStrategiesDao;
	
	@Before
	public void setUp()
	{
		
		infoStrategiesDao= new InfoStrategiesDao();

	}
	@After
	public void takeDown()
	{
		
	
		InfoStrategies infoFind= new InfoStrategies();

		infoFind.setPeriodicity("test");
		infoFind.setStockCodeName("unitTtest");
		infoFind.setUserIdentifier("junit");
		infoFind.setStrategyName("strategieTest");
		
		
		infoStrategiesDao.dropInfoStrategy(infoFind);
		
		
	}

	@Test
	public void testInfoStrategiesDao() 
	{
		
		
		Assert.assertEquals(infoStrategiesDao.getClass(), InfoStrategiesDao.class);
		Assert.assertNotNull(infoStrategiesDao.getCollection_infoStrategies());
		
		
	}

	@Test
	public void testDropInfoStrategy()
	{
		InfoStrategies info= new InfoStrategies();
		
		InfoStrategies infoFind= new InfoStrategies();
		InfoStrategies recuvered=null;
		
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
		recuvered= infoStrategiesDao.getInfoStrategy(infoFind);
		
		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(), recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(), recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(), recuvered.getStockCodeName());
		
		infoStrategiesDao.dropInfoStrategy(info);
		
		recuvered= infoStrategiesDao.getInfoStrategy(infoFind);
		Assert.assertNull(recuvered);
		
		
		
	}
	@Test
	public void testInsertInfoStrategy() 
	{
		InfoStrategies info= new InfoStrategies();
		
		InfoStrategies infoFind= new InfoStrategies();
		InfoStrategies recuvered=null;
		
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
		recuvered= infoStrategiesDao.getInfoStrategy(infoFind);
		
		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(), recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(), recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(), recuvered.getStockCodeName());
	  
		
	}

	@Test
	public void testUpdateInfoStrategy() 
	{
		InfoStrategies info= new InfoStrategies();
		
		InfoStrategies infoFind= new InfoStrategies();
		InfoStrategies recuvered=null;
		
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
		recuvered= infoStrategiesDao.getInfoStrategy(infoFind);
		
		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(), recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(), recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(), recuvered.getStockCodeName());
		
		info.setSelled(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		infoStrategiesDao.updateInfoStrategy(info);
		recuvered= infoStrategiesDao.getInfoStrategy(infoFind);
		
		Assert.assertNotNull(recuvered.getSelled());
		
		
	}

	@Test
	public void testGetInfoStrategy() {
		
		InfoStrategies info= new InfoStrategies();
		
		InfoStrategies infoFind= new InfoStrategies();
		InfoStrategies recuvered=null;
		
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
		recuvered= infoStrategiesDao.getInfoStrategy(infoFind);
		
		Assert.assertNotNull(recuvered);
		Assert.assertEquals(infoFind.getUserIdentifier(), recuvered.getUserIdentifier());
		Assert.assertEquals(infoFind.getStrategyName(), recuvered.getStrategyName());
		Assert.assertEquals(infoFind.getStockCodeName(), recuvered.getStockCodeName());
		
		
		
		
	}

}
