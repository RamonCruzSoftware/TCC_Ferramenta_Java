package suport.util.database.mongoDB.pojoTest;

import java.util.Date;

import junit.framework.Assert;
import suport.financial.partternsCandleStick.CandleStick;
import suport.util.database.mongoDB.pojo.InfoStrategies;

public class InfoStrategiesTest {
	
	private InfoStrategies infoStrategies;

	 
	public void setUp() throws Exception {
		
		infoStrategies= new InfoStrategies();
	}

	 
	public void tearDown() throws Exception {
	}

	 
	public void testGetStrategyName() {
		
		infoStrategies.setStrategyName("test");
		Assert.assertEquals("test", infoStrategies.getStrategyName());
	}

	 
	public void testGetPeriodicity() {
		
		infoStrategies.setPeriodicity("test");
		Assert.assertEquals("test", infoStrategies.getPeriodicity());
	}


	 
	public void testGetBuyed() {
		infoStrategies.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		Assert.assertEquals(10, infoStrategies.getBuyed().getClose(),0.1);
		
		
	}


	 
	public void testGetSelled() {
		
		infoStrategies.setSelled(new CandleStick(10, 10, 10, 10, 10, new Date()));
		Assert.assertEquals(10, infoStrategies.getSelled().getClose(),0.1);
	}


	 
	public void testGetProfit() {
		
		infoStrategies.setProfit(10.0);
		Assert.assertEquals(10.0, infoStrategies.getProfit(),0.0);
	}


	 
	public void testGetStockCodeName() {
		
		infoStrategies.setStockCodeName("test");
		Assert.assertEquals("test", infoStrategies.getStockCodeName());
	}


	 
	public void testGetUserIdentifier() {
		
		infoStrategies.setUserIdentifier("test");
		Assert.assertEquals("test", infoStrategies.getUserIdentifier());
	}


}
