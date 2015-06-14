package suport.financial.walletTest;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import junit.framework.Assert;
import suport.financial.partternsCandleStick.CandleStick;
import suport.financial.wallet.Stock;

public class StockTest {

	private Stock stock;
	@Test
	public void testStock() {
		stock = new Stock();
		Assert.assertNotNull(stock);
	}
	@Test
	public void testStockStringString() {
		stock = new Stock("nameTest", "sectorTest");
		Assert.assertNotNull(stock);
		Assert.assertEquals("nameTest", stock.getCodeName());
		Assert.assertEquals("sectorTest", stock.getSector());

	}
	@Test
	public void testAddCurrentCandleStick() {

		CandleStick candlestick_A = new CandleStick(10, 10, 10, 10, 1000,
				new Date());
		CandleStick candlestick_B = new CandleStick(11, 11, 11, 11, 1000,
				new Date());
		stock = new Stock("nameTest", "sectorTest");

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();
		candleList.add(new CandleStick(9, 9, 9, 9, 9, new Date()));

		stock.setCandleSticks(candleList);

		stock.addCurrentCandleStick(candlestick_A);
		stock.addCurrentCandleStick(candlestick_B);

		Assert.assertEquals(9, stock.getCurrentCandleStick().getClose(), 0.0);

	}
	@Test
	public void testGetCodeName() {

		stock = new Stock("nameTest", "sectorTest");
		Assert.assertEquals("nameTest", stock.getCodeName());
	}
	@Test
	public void testSetCodeName() {
		stock = new Stock("nameTest", "sectorTest");
		stock.setCodeName("test");
		Assert.assertEquals("test", stock.getCodeName());
	}
	@Test
	public void testGetSector() {
		stock = new Stock("nameTest", "sectorTest");
		Assert.assertEquals("sectorTest", stock.getSector());
	}
	@Test
	public void testSetSector() {

		stock = new Stock("nameTest", "sectorTest");
		stock.setSector("test");
		Assert.assertEquals("test", stock.getSector());
	}
	@Test
	public void testGetCandleSticks() {

		stock = new Stock("nameTest", "sectorTest");

		ArrayList<CandleStick> candleStickList = new ArrayList<CandleStick>();
		CandleStick candleStick = new CandleStick(10, 10, 10, 10, 10,
				new Date());

		candleStickList.add(candleStick);
		stock.setCandleSticks(candleStickList);

		Assert.assertEquals(10, stock.getCandleSticks().get(0).getClose(), 0.0);

	}
	@Test
	public void testSetCandleSticks() {

		stock = new Stock("nameTest", "sectorTest");

		ArrayList<CandleStick> candleStickList = new ArrayList<CandleStick>();
		CandleStick candleStick = new CandleStick(10, 10, 10, 10, 10,
				new Date());

		candleStickList.add(candleStick);
		stock.setCandleSticks(candleStickList);

		Assert.assertEquals(10, stock.getCandleSticks().get(0).getClose(), 0.0);
	}
	@Test
	public void testGetCurrentCandleStick() {

		CandleStick candlestick_A = new CandleStick(10, 10, 10, 10, 1000,
				new Date());
		CandleStick candlestick_B = new CandleStick(11, 11, 11, 11, 1000,
				new Date());
		stock = new Stock("nameTest", "sectorTest");

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();
		candleList.add(new CandleStick(9, 9, 9, 9, 9, new Date()));

		stock.setCandleSticks(candleList);

		stock.addCurrentCandleStick(candlestick_A);
		stock.addCurrentCandleStick(candlestick_B);

		Assert.assertEquals(9, stock.getCurrentCandleStick().getClose(), 0.0);
	}
	@Test
	public void testGetStandardDeviation_30() {
		stock = new Stock();
		stock.setStandardDeviation_30(10.0);
		Assert.assertEquals(10.0, stock.getStandardDeviation_30(), 0.0);
	}
	@Test
	public void testSetStandardDeviation_30() {
		stock = new Stock();
		stock.setStandardDeviation_30(10.0);
		Assert.assertEquals(10.0, stock.getStandardDeviation_30(), 0.0);
	}
	@Test
	public void testGetVariance_30() {
		stock = new Stock();
		stock.setVariance_30(10.0);
		Assert.assertEquals(10.0, stock.getVariance_30(), 0.0);
	}
	@Test
	public void testSetVariance_30() {

		stock = new Stock();
		stock.setVariance_30(10.0);
		Assert.assertEquals(10.0, stock.getVariance_30(), 0.0);
	}
	@Test
	public void testGetVarianceCoefficient_30() {

		stock = new Stock();
		stock.setVarianceCoefficient_30(10.0);
		Assert.assertEquals(10.0, stock.getVarianceCoefficient_30(), 0.0);

	}
	@Test
	public void testSetVarianceCoefficient_30() {

		stock = new Stock();
		stock.setVarianceCoefficient_30(10.0);
		Assert.assertEquals(10.0, stock.getVarianceCoefficient_30(), 0.0);

	}
	@Test
	public void testGetStandardDeviation_15() {

		stock = new Stock();
		stock.setStandardDeviation_15(10.0);
		Assert.assertEquals(10.0, stock.getStandardDeviation_15(), 0.0);

	}
	@Test
	public void testSetStandardDeviation_15() {

		stock = new Stock();
		stock.setStandardDeviation_15(10.0);
		Assert.assertEquals(10.0, stock.getStandardDeviation_15(), 0.0);
	}
	@Test
	public void testGetVariance_15() {
		stock = new Stock();
		stock.setVariance_15(10.0);
		Assert.assertEquals(10.0, stock.getVariance_15(), 0.0);
	}
	@Test
	public void testSetVariance_15() {

		stock = new Stock();
		stock.setVariance_15(10.0);
		Assert.assertEquals(10.0, stock.getVariance_15(), 0.0);

	}
	@Test
	public void testGetVarianceCoefficient_15() {

		stock = new Stock();
		stock.setVarianceCoefficient_15(10.0);
		Assert.assertEquals(10.0, stock.getVarianceCoefficient_15(), 0.0);
	}
	@Test
	public void testSetVarianceCoefficient_15() {
		stock = new Stock();
		stock.setVarianceCoefficient_15(10.0);
		Assert.assertEquals(10.0, stock.getVarianceCoefficient_15(), 0.0);
	}
	@Test
	public void testGetAvarangeReturn_15() {
		stock = new Stock();
		stock.setAvarangeReturn_15(10.0);
		Assert.assertEquals(10.0, stock.getAvarangeReturn_15(), 0.0);
	}
	@Test
	public void testSetAvarangeReturn_15() {

		stock = new Stock();
		stock.setAvarangeReturn_15(10.0);
		Assert.assertEquals(10.0, stock.getAvarangeReturn_15(), 0.0);
	}
	@Test
	public void testGetAvarangeReturn_30() {

		stock = new Stock();
		stock.setAvarangeReturn_30(10.0);
		Assert.assertEquals(10.0, stock.getAvarangeReturn_30(), 0.0);
	}
	@Test
	public void testSetAvarangeReturn_30() {

		stock = new Stock();
		stock.setAvarangeReturn_30(10.0);
		Assert.assertEquals(10.0, stock.getAvarangeReturn_30(), 0.0);
	}

}
