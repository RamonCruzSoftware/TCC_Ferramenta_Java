package suport.financial.walletTest;

import suport.financial.wallet.Wallet;
import junit.framework.Assert;


public class WalletTest {

	 
	public void testWallet() {
		
		Wallet wallet= new Wallet();
		
		Assert.assertEquals(wallet.getClass(), Wallet.class);
	}

}
