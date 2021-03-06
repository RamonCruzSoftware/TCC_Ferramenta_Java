package rcs.suport.util.database.mongoDB.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.util.database.mongoDB.pojo.ManagedStock;

public class ManagedStockDaoTest {

	private ManagedStockDao managedStockDao;

	@Before
	public void setUp() throws Exception 
	{
		managedStockDao= new ManagedStockDao();
		
	}

	@After
	public void tearDown() 
	{
		
		ManagedStock managedStock= new ManagedStock();
		managedStock.setCodeName("codeTest");
		managedStock.setUserIdentifier("userTest");
		
		managedStockDao.dropManagedStockDao(managedStock);
	}

	@Test
	public void testManagedStockDao()
	{
		
		Assert.assertEquals(ManagedStockDao.class, managedStockDao.getClass());
		Assert.assertNotNull(managedStockDao.getCollection_managedStock());
	}

	@Test
	public void testInsertCandleStick() 
	{
		
		CandleStick candleStick= new CandleStick(10, 10, 10, 10, 10, new Date(10000));
		ManagedStock managedStock= new ManagedStock();
		ArrayList<CandleStick>candleList= new ArrayList<CandleStick>();
		ManagedStock recuvered=null;
		
		
		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		managedStock.setCodeName("codeTest");
		managedStock.setUserIdentifier("userTest");
		managedStock.setCandleSticks(candleList);
		managedStock.setBuyed(candleStick);
		managedStock.setProfitPercent(0);
		managedStock.setProfitValue(0);
		managedStock.setQtdStocksBought(0);
		managedStock.setSector("test");
		
		Assert.assertTrue(managedStockDao.insertManagedStock(managedStock));
	    Assert.assertTrue(managedStockDao.insertCandleStick(managedStock.getUserIdentifier(), managedStock.getCodeName(), candleStick));
	
		
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		Assert.assertEquals(recuvered.getCodeName(),managedStock.getCodeName());
		Assert.assertEquals(recuvered.getUserIdentifier(),managedStock.getUserIdentifier());
		Assert.assertEquals(2, recuvered.getCandleSticks().size());
		
	}

	@Test
	public void testInsertManagedStock() 
	{
		
		ManagedStock managedStock= new ManagedStock();
		ArrayList<CandleStick>candleList= new ArrayList<CandleStick>();
		ManagedStock recuvered=null;
			
		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		managedStock.setCodeName("codeTest");
		managedStock.setUserIdentifier("userTest");
		managedStock.setCandleSticks(candleList);
		
		managedStockDao.insertManagedStock(managedStock);
		
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		Assert.assertEquals(recuvered.getCodeName(),managedStock.getCodeName());
		Assert.assertEquals(recuvered.getUserIdentifier(),managedStock.getUserIdentifier());
	}

	@Test
	public void testUpdateManagedStock() 
	{
		ManagedStock managedStock= new ManagedStock();
		ArrayList<CandleStick>candleList= new ArrayList<CandleStick>();
		ManagedStock recuvered=null;
			
		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		managedStock.setCodeName("codeTest");
		managedStock.setUserIdentifier("userTest");
		managedStock.setCandleSticks(candleList);
		managedStock.setBuyed(new CandleStick(10, 01, 01, 10, 10, null));
		
		managedStockDao.insertManagedStock(managedStock);	
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		Assert.assertEquals(recuvered.getCodeName(),managedStock.getCodeName());
		Assert.assertEquals(recuvered.getUserIdentifier(),managedStock.getUserIdentifier());
		Assert.assertNull(recuvered.getSelled());
		
		//Update
		managedStock.setSelled(new CandleStick(8, 8, 8, 8, 8, new Date()));
		recuvered=null;
		managedStockDao.updateManagedStock(managedStock);	
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		
		Assert.assertEquals(recuvered.getCodeName(),managedStock.getCodeName());
		Assert.assertEquals(recuvered.getUserIdentifier(),managedStock.getUserIdentifier());
		Assert.assertNotNull(recuvered.getSelled());
	}

	@Test
	public void testGetManagedStockStringString() {
		

		ManagedStock managedStock= new ManagedStock();
		ArrayList<CandleStick>candleList= new ArrayList<CandleStick>();
		ManagedStock recuvered=null;
			
		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		managedStock.setCodeName("codeTest");
		managedStock.setUserIdentifier("userTest");
		managedStock.setCandleSticks(candleList);
		
		managedStockDao.insertManagedStock(managedStock);
		
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		Assert.assertEquals(recuvered.getCodeName(),managedStock.getCodeName());
		Assert.assertEquals(recuvered.getUserIdentifier(),managedStock.getUserIdentifier());
		
	}

	@Test
	public void testGetManagedStockString() {
		

		ManagedStock managedStock_1= new ManagedStock();
		ManagedStock managedStock_2= new ManagedStock();
		
		ArrayList<CandleStick>candleList= new ArrayList<CandleStick>();
		ArrayList<ManagedStock> recuvered=null;
			
		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		managedStock_1.setCodeName("codeTest");
		managedStock_1.setUserIdentifier("userTest");
		managedStock_1.setCandleSticks(candleList);
		
		managedStock_2.setCodeName("codeTest");
		managedStock_2.setUserIdentifier("userTest");
		managedStock_2.setCandleSticks(candleList);
		
		managedStockDao.insertManagedStock(managedStock_1);
		managedStockDao.insertManagedStock(managedStock_2);
		
		recuvered=managedStockDao.getManagedStock(managedStock_1.getUserIdentifier());
		
		
		Assert.assertEquals(2,recuvered.size());
		
	}
	@Test
	public void testDropManagedStockDao()
	{
		ManagedStock managedStock= new ManagedStock();
		ArrayList<CandleStick>candleList= new ArrayList<CandleStick>();
		ManagedStock recuvered=null;
			
		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date()));
		
		managedStock.setCodeName("codeTest");
		managedStock.setUserIdentifier("userTest");
		managedStock.setCandleSticks(candleList);
		
		managedStockDao.insertManagedStock(managedStock);
		
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		Assert.assertEquals(recuvered.getCodeName(),managedStock.getCodeName());
		Assert.assertEquals(recuvered.getUserIdentifier(),managedStock.getUserIdentifier());
		
		Assert.assertTrue(managedStockDao.dropManagedStockDao(managedStock));
		recuvered=managedStockDao.getManagedStock(managedStock.getCodeName(), managedStock.getUserIdentifier());
		
		Assert.assertNull(recuvered);
		
		
		
	}

}
