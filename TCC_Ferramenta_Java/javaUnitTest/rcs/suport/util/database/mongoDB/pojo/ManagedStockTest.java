package rcs.suport.util.database.mongoDB.pojo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class ManagedStockTest {
	
	private ManagedStock managedStock;

	@Before
	public void setUp() throws Exception {
	
		managedStock= new ManagedStock();
	}
	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCodeName() {
		
		managedStock.setCodeName("test");
		Assert.assertEquals("test", managedStock.getCodeName());
	}

	@Test
	public void testGetSector() {
		managedStock.setSector("test");
		Assert.assertEquals("test", managedStock.getSector());
		
	}

	@Test
	public void testGetCandleSticks() {
		
		ArrayList<CandleStick>candleSticks = new ArrayList<CandleStick>();
		candleSticks.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		
		managedStock.setCandleSticks(candleSticks);
		Assert.assertEquals(10, managedStock.getCandleSticks().get(0).getClose(),0.1);
	}

	@Test
	public void testGetBuyed() {
		
		managedStock.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		Assert.assertEquals(10, managedStock.getBuyed().getClose(),0.1);
		
	}

	@Test
	public void testGetProfitPercent() {
		
		managedStock.setProfitPercent(0.1);
		Assert.assertEquals(0.1, managedStock.getProfitPercent(),0.1);
	}

	@Test
	public void testGetProfitValue() {
		
		managedStock.setProfitValue(1.0);
		Assert.assertEquals(1.0, managedStock.getProfitValue(),0.1);
	}

	@Test
	public void testGetSelled() {
		
		managedStock.setSelled(new CandleStick(10, 10, 10, 10, 10, null));
		Assert.assertEquals(10, managedStock.getSelled().getHigh(),0.1);
	}

	@Test
	public void testGetUserIdentifier() {
		
		managedStock.setUserIdentifier("test");
		Assert.assertEquals("test", managedStock.getUserIdentifier());
	}

	@Test
	public void testGetQtdStocksBought() {
	

		managedStock.setQtdStocksBought(1);
		Assert.assertEquals(1, managedStock.getQtdStocksBought());
	}

}
