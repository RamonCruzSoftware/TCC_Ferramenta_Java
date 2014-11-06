package rcs.suport.financial.wallet;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class WalletTest {

	@Test
	public void testWallet() {
		
		Wallet wallet= new Wallet();
		
		Assert.assertEquals(wallet.getClass(), Wallet.class);
	}

}
