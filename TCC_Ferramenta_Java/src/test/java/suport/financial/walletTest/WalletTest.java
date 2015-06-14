package suport.financial.walletTest;

import org.junit.Test;

import suport.financial.wallet.Wallet;
import junit.framework.Assert;

public class WalletTest {

	@Test
	public void testWallet() {

		Wallet wallet = new Wallet();

		Assert.assertEquals(wallet.getClass(), Wallet.class);
	}

}
