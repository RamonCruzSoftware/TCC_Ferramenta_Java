package rcs.suport.util.database.mongoDB.pojo;

import static org.junit.Assert.*;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class InfoStrategiesTest {
	
	private InfoStrategies infoStrategies;

	@Before
	public void setUp() throws Exception {
		
		infoStrategies= new InfoStrategies();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStrategyName() {
		
		infoStrategies.setStrategyName("test");
		Assert.assertEquals("test", infoStrategies.getStrategyName());
	}

	@Test
	public void testGetPeriodicity() {
		
		infoStrategies.setPeriodicity("test");
		Assert.assertEquals("test", infoStrategies.getPeriodicity());
	}


	@Test
	public void testGetBuyed() {
		infoStrategies.setBuyed(new CandleStick(10, 10, 10, 10, 10, new Date()));
		Assert.assertEquals(10, infoStrategies.getBuyed().getClose(),0.1);
		
		
	}


	@Test
	public void testGetSelled() {
		
		infoStrategies.setSelled(new CandleStick(10, 10, 10, 10, 10, new Date()));
		Assert.assertEquals(10, infoStrategies.getSelled().getClose(),0.1);
	}


	@Test
	public void testGetProfit() {
		
		infoStrategies.setProfit(10.0);
		Assert.assertEquals(10.0, infoStrategies.getProfit(),0.0);
	}


	@Test
	public void testGetStockCodeName() {
		
		infoStrategies.setStockCodeName("test");
		Assert.assertEquals("test", infoStrategies.getStockCodeName());
	}


	@Test
	public void testGetUserIdentifier() {
		
		infoStrategies.setUserIdentifier("test");
		Assert.assertEquals("test", infoStrategies.getUserIdentifier());
	}


}
