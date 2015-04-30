package rcs.suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.pojo.ManagedWallet;

public class ManagedWalletDaoTest {
	
	private ManagedWalletDao managedWalletDao;

	

	@Before
	public void setUp() throws Exception {
		
		managedWalletDao= new ManagedWalletDao();
	}

	@After
	public void tearDown() throws Exception {
		ManagedWallet mWallet= new ManagedWallet();
		mWallet.setUserID("userTest");
		managedWalletDao.dropManagedWallet(mWallet);
	}

	@Test
	public void testManagedWalletDao() {
		
		Assert.assertNotNull(managedWalletDao);
		Assert.assertNotNull(managedWalletDao.getCollection_managedWallet());
	}

	@Test
	public void testInsertManagedWalletInfo() {
		
		ManagedWallet mWallet= new ManagedWallet();
		ManagedWallet mRecuvered=null;
		
		mWallet.setUserID("userTest");
		mWallet.setWalletProfitPercent(0.1);
		mWallet.setWalletProfitValue(0.1);
		mWallet.setWalletRisck(0.1);
		mWallet.setWalletValue(0.1);
		
		managedWalletDao.insertManagedWalletInfo(mWallet);
		mRecuvered=managedWalletDao.getManagedWallet(mWallet.getUserID());
		
		Assert.assertNotNull(mRecuvered);
		
		
		
	}

	@Test
	public void testUpdateManagedWallet() {
		
		ManagedWallet mWallet= new ManagedWallet();
		ManagedWallet mRecuvered=null;
		ArrayList<Stock>stockList=null;
		
		mWallet.setUserID("userTest");
		mWallet.setWalletProfitPercent(0.1);
		mWallet.setWalletProfitValue(0.1);
		mWallet.setWalletRisck(0.1);
		mWallet.setWalletValue(0.1);
		
		managedWalletDao.insertManagedWalletInfo(mWallet);
		mRecuvered=managedWalletDao.getManagedWallet(mWallet.getUserID());
		
		Assert.assertNotNull(mRecuvered);
		Assert.assertNull(mRecuvered.getStocksList());
		Assert.assertEquals(0.1, mRecuvered.getWalletProfitPercent(),0.1);
		Assert.assertEquals(0.1, mRecuvered.getWalletProfitValue(),0.1);
		Assert.assertEquals(0.1, mRecuvered.getWalletRisck(),0.1);
		Assert.assertEquals(0.1, mRecuvered.getWalletValue(),0.1);
		
		mRecuvered=null;
		stockList= new ArrayList<Stock>();
		stockList.add(new Stock("testStock", "testSector"));
		
		mWallet.setStocksList(stockList);
		mWallet.setWalletProfitPercent(0.4);
		mWallet.setWalletProfitValue(0.4);
		mWallet.setWalletRisck(0.4);
		mWallet.setWalletValue(0.4);
		
		managedWalletDao.updateManagedWallet(mWallet);
		mRecuvered=managedWalletDao.getManagedWallet(mWallet.getUserID());
		
		Assert.assertNotNull(mRecuvered);
		Assert.assertNotNull(mRecuvered.getStocksList());
		Assert.assertEquals(0.4, mRecuvered.getWalletProfitPercent(),0.1);
		Assert.assertEquals(0.4, mRecuvered.getWalletProfitValue(),0.1);
		Assert.assertEquals(0.4, mRecuvered.getWalletRisck(),0.1);
		Assert.assertEquals(0.4, mRecuvered.getWalletValue(),0.1);
	}

	@Test
	public void testGetManagedWallet() {
		ManagedWallet mWallet= new ManagedWallet();
		ManagedWallet mRecuvered=null;
		
		mWallet.setUserID("userTest");
		mWallet.setWalletProfitPercent(0.1);
		mWallet.setWalletProfitValue(0.1);
		mWallet.setWalletRisck(0.1);
		mWallet.setWalletValue(0.1);
		
		managedWalletDao.insertManagedWalletInfo(mWallet);
		mRecuvered=managedWalletDao.getManagedWallet(mWallet.getUserID());
		
		Assert.assertNotNull(mRecuvered);
		
	}
	@Test
	public void testDropManagedWallet()
	{
		ManagedWallet mWallet= new ManagedWallet();
		ManagedWallet mRecuvered=null;
		
		mWallet.setUserID("userTest");
		mWallet.setWalletProfitPercent(0.1);
		mWallet.setWalletProfitValue(0.1);
		mWallet.setWalletRisck(0.1);
		mWallet.setWalletValue(0.1);
		
		managedWalletDao.insertManagedWalletInfo(mWallet);
		mRecuvered=managedWalletDao.getManagedWallet(mWallet.getUserID());
		
		Assert.assertNotNull(mRecuvered);
		
		managedWalletDao.dropManagedWallet(mWallet);
		mRecuvered=managedWalletDao.getManagedWallet(mWallet.getUserID());
		
		Assert.assertNull(mRecuvered);
	}

}
