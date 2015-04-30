package rcs.suport.util.database.mongoDB.pojo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.wallet.Stock;

public class ManagedWalletTest {
	
	private ManagedWallet managedWallet;

	@Before
	public void setUp() throws Exception {
		
		managedWallet= new ManagedWallet();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testManagedWallet() {
		
		Assert.assertNotNull(managedWallet);
	}

	@Test
	public void testGetUserID() {
		
		managedWallet.setUserID("test");
		Assert.assertEquals("test", managedWallet.getUserID());
	}

	@Test
	public void testGetWalletValue() {
		
		managedWallet.setWalletValue(1);
		Assert.assertEquals(1, managedWallet.getWalletValue(),0.1);
		
	}

	@Test
	public void testGetWalletRisck() {
		
		managedWallet.setWalletRisck(1);
		Assert.assertEquals(1, managedWallet.getWalletRisck(),0.1);
	}

	@Test
	public void testGetWalletProfitPercent() {
		
		managedWallet.setWalletProfitPercent(1);
		Assert.assertEquals(1, managedWallet.getWalletProfitPercent(),0.1);
	}

	@Test
	public void testGetWalletProfitValue() {
		
		managedWallet.setWalletProfitValue(1);
		Assert.assertEquals(1, managedWallet.getWalletProfitValue(),0.1);
	}

	@Test
	public void testGetStocksList() {
		
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		stockList.add(new Stock("test", "test"));
		
		managedWallet.setStocksList(stockList);
		Assert.assertEquals("test", managedWallet.getStocksList().get(0).getCodeName());
	}

}
