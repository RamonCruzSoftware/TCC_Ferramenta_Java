package suport.util.database.mongoDB.pojoTest;

import java.util.ArrayList;
import junit.framework.Assert;
import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.pojo.ManagedWallet;

public class ManagedWalletTest {

	private ManagedWallet managedWallet;

	public void setUp() throws Exception {

		managedWallet = new ManagedWallet();

	}

	public void tearDown() throws Exception {
	}

	public void testManagedWallet() {

		Assert.assertNotNull(managedWallet);
	}

	public void testGetUserID() {

		managedWallet.setUserID("test");
		Assert.assertEquals("test", managedWallet.getUserID());
	}

	public void testGetWalletValue() {

		managedWallet.setWalletValue(1);
		Assert.assertEquals(1, managedWallet.getWalletValue(), 0.1);

	}

	public void testGetWalletRisck() {

		managedWallet.setWalletRisck(1);
		Assert.assertEquals(1, managedWallet.getWalletRisck(), 0.1);
	}

	public void testGetWalletProfitPercent() {

		managedWallet.setWalletProfitPercent(1);
		Assert.assertEquals(1, managedWallet.getWalletProfitPercent(), 0.1);
	}

	public void testGetWalletProfitValue() {

		managedWallet.setWalletProfitValue(1);
		Assert.assertEquals(1, managedWallet.getWalletProfitValue(), 0.1);
	}

	public void testGetStocksList() {

		ArrayList<Stock> stockList = new ArrayList<Stock>();
		stockList.add(new Stock("test", "test"));

		managedWallet.setStocksList(stockList);
		Assert.assertEquals("test", managedWallet.getStocksList().get(0)
				.getCodeName());
	}

}
