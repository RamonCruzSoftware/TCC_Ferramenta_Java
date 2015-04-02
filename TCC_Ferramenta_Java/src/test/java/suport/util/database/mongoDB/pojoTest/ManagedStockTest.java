package suport.util.database.mongoDB.pojoTest;
 
import java.util.ArrayList;
import java.util.Date;
import junit.framework.Assert;
 

import suport.financial.partternsCandleStick.CandleStick;
import suport.util.database.mongoDB.pojo.ManagedStock;

public class ManagedStockTest {
	
	private ManagedStock managedStock;

	 
	public void setUp() throws Exception {
	
		managedStock= new ManagedStock();
	}
	 
	public void tearDown() throws Exception {
	}
 
	public void testGetCodeName() {
		
		managedStock.setCodeName("test");
		Assert.assertEquals("test", managedStock.getCodeName());
	}

	 
	public void testGetSector() {
		managedStock.setSector("test");
		Assert.assertEquals("test", managedStock.getSector());
		
	}

	 
	public void testGetCandleSticks() {
		
		ArrayList<CandleStick>candleSticks = new ArrayList<CandleStick>();
		candleSticks.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		
		managedStock.setCandleSticks(candleSticks);
		Assert.assertEquals(10, managedStock.getCandleSticks().get(0).getClose(),0.1);
	}

	 
	public void testGetBuyed() {
		
		managedStock.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		Assert.assertEquals(10, managedStock.getBuyed().getClose(),0.1);
		
	}

	 
	public void testGetProfitPercent() {
		
		managedStock.setProfitPercent(0.1);
		Assert.assertEquals(0.1, managedStock.getProfitPercent(),0.1);
	}

	 
	public void testGetProfitValue() {
		
		managedStock.setProfitValue(1.0);
		Assert.assertEquals(1.0, managedStock.getProfitValue(),0.1);
	}

	 
	public void testGetSelled() {
		
		managedStock.setSelled(new CandleStick(10, 10, 10, 10, 10, null));
		Assert.assertEquals(10, managedStock.getSelled().getHigh(),0.1);
	}

	 
	public void testGetUserIdentifier() {
		
		managedStock.setUserIdentifier("test");
		Assert.assertEquals("test", managedStock.getUserIdentifier());
	}

	 
	public void testGetQtdStocksBought() {
	

		managedStock.setQtdStocksBought(1);
		Assert.assertEquals(1, managedStock.getQtdStocksBought());
	}

}
