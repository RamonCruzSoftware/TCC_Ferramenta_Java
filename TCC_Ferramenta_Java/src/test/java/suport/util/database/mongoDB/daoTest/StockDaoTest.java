package suport.util.database.mongoDB.daoTest;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import suport.financial.partternsCandleStick.CandleStick;
import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.dao.StockDao;

public class StockDaoTest {

	private StockDao stockDao;
@Before
	public void setUp() throws Exception {

		stockDao = new StockDao();

	}
@After
	public void tearDown() throws Exception {

		Stock stock = new Stock("test", "test");
		Stock stock2 = new Stock("test2", "test");

		stockDao.dropStockSuggestions("userTest");
		stockDao.dropStock(stock);

		stockDao.dropStockSuggestions("userTest");
		stockDao.dropStock(stock2);

	}
@Test
	public void testStockDao() {

		Assert.assertEquals(StockDao.class, stockDao.getClass());
		Assert.assertNotNull(stockDao.getCollection_stock_prices());
		Assert.assertNotNull(stockDao.getCollection_stocks());
		Assert.assertNotNull(stockDao.getCollection_userStockSugestions());

	}
@Test
	public void testInsertStocksSuggestion() {
		Stock suggestion = new Stock("test", "test");
		suggestion.setAvarangeReturn_15(1);
		suggestion.setAvarangeReturn_30(1);
		suggestion.setStandardDeviation_15(1);
		suggestion.setStandardDeviation_30(1);
		suggestion.setVariance_15(1);
		suggestion.setVariance_30(1);
		suggestion.setVarianceCoefficient_15(1);
		suggestion.setVarianceCoefficient_30(1);

		Assert.assertTrue(stockDao.insertStocksSuggestion(suggestion,
				"userTest"));
		Assert.assertNotNull(stockDao.getStocksSuggestion("userTest"));

		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getAvarangeReturn_15(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getAvarangeReturn_30(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getStandardDeviation_15(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getStandardDeviation_30(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getVariance_15(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getVariance_30(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getVarianceCoefficient_15(), 0.0);
		Assert.assertEquals(1, stockDao.getStocksSuggestion("userTest").get(0)
				.getVarianceCoefficient_30(), 0.0);

	}

	
	@Test
	public void testUpdateStock() {

		Stock stock = new Stock("test", "test");
		Stock stockRecurvered = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1);
		stock.setAvarangeReturn_30(1);
		stock.setStandardDeviation_15(1);
		stock.setStandardDeviation_30(1);
		stock.setVariance_15(1);
		stock.setVariance_30(1);
		stock.setVarianceCoefficient_15(1);
		stock.setVarianceCoefficient_30(1);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		stock.setAvarangeReturn_15(2);
		stock.setAvarangeReturn_30(2);
		stock.setStandardDeviation_15(2);
		stock.setStandardDeviation_30(2);
		stock.setVariance_15(2);
		stock.setVariance_30(2);
		stock.setVarianceCoefficient_15(2);
		stock.setVarianceCoefficient_30(2);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.updateStock(stock));

		stockRecurvered = stockDao.getStock(stock.getCodeName());

		Assert.assertNotNull(stockRecurvered);

		Assert.assertEquals(2, stockRecurvered.getAvarangeReturn_15(), 0.0);

	}

	@Test
	
	public void testInsertCurrentStock() {

		Stock stock = new Stock("test", "test");
		Stock stockRecurvered = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(1.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));

		stock.setCandleSticks(candleList);

		stockRecurvered = stockDao.getStock(stock.getCodeName());

		Assert.assertTrue(stockDao.insertCurrentStock(stock));
		Assert.assertNotNull(stockRecurvered);

	}
	@Test
	
	public void testStoreHistoricalStockValue() {

		Stock stock = new Stock("test", "test");
		Stock stock2 = new Stock("test", "test");

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setCandleSticks(candleList);

		stock2.setCandleSticks(candleList);
		stockDao.storeHistoricalStockValue(stock);

		Assert.assertFalse(stockDao.storeHistoricalStockValue(stock));

	}
	@Test
	
	public void testGetAllStocks() {

		Stock stock = new Stock("test", "test");
		ArrayList<Stock> stockRecurvered = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(1.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));

		stock.setCandleSticks(candleList);

		stockRecurvered = stockDao.getAllStocks();

		Assert.assertTrue(stockDao.insertCurrentStock(stock));
		Assert.assertNotNull(stockRecurvered);
		Assert.assertEquals(1, stockRecurvered.size());

	}
	@Test
	
	public void testGetAllStocksPrices() {

		Stock stock = new Stock("test", "test");
		ArrayList<Stock> stockRecurvered = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(1.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));

		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		stockRecurvered = stockDao.getAllStocksWithPrices();

		Assert.assertNotNull(stockRecurvered);
		Assert.assertEquals(1, stockRecurvered.size());
		Assert.assertEquals(2, stockRecurvered.get(0).getCandleSticks().size(),
				0.0);
	}
	@Test
	@SuppressWarnings("deprecation")
	public void testGetStockPrices_last10() {

		Stock stock = new Stock("test", "test");
		ArrayList<CandleStick> last10 = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(1.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 4)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 3)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 2)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 1)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				30)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				29)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				28)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				27)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		last10 = stockDao.getStockPrices_last10(stock.getCodeName());

		Assert.assertNotNull(last10);
		Assert.assertEquals(10, last10.size());

	}
	@Test
	
	public void testGetStockPrices_last30() {

		Stock stock = new Stock("test", "test");
		ArrayList<CandleStick> last30 = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(1.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 4)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 3)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 2)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 1)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				30)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				29)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				28)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				27)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				26)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				25)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				24)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				23)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				22)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				21)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				20)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				19)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				18)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				17)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				16)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				15)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				14)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				13)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				12)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				11)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				10)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 9)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		last30 = stockDao.getStockPrices_last30(stock.getCodeName());

		Assert.assertNotNull(last30);
		Assert.assertEquals(30, last30.size());

		// Testa menos de 30 valores
		stockDao.dropStock(stock);
		stock.getCandleSticks().clear();

		candleList = new ArrayList<CandleStick>();
		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 4)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 3)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 2)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 1)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				30)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				29)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				28)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				27)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				26)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				25)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				24)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				23)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				22)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		last30 = stockDao.getStockPrices_last30(stock.getCodeName());

		Assert.assertNotNull(last30);
		Assert.assertEquals(17, last30.size());

	}
	@Test
	
	public void testGetStockPrices_last40() {

		Stock stock = new Stock("test", "test");
		ArrayList<CandleStick> last40 = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(1.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 4)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 3)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 2)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 1)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				30)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				29)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				28)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				27)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				26)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				25)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				24)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				23)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				22)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				21)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				20)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				19)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				18)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				17)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				16)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				15)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				14)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				13)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				12)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				11)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				10)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 9)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 8)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 7)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 6)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 5)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 4)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 3)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 2)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 10, 1)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 9, 30)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				29)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		last40 = stockDao.getStockPrices_last40(stock.getCodeName());
		Assert.assertNotNull(last40);
		Assert.assertEquals(40, last40.size());

		// Testa menos de 40 valores
		stockDao.dropStock(stock);
		stock.getCandleSticks().clear();

		candleList = new ArrayList<CandleStick>();
		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 7)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 6)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 5)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 4)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 3)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 2)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11,
				new Date(2014, 11, 1)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				30)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				29)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				28)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				27)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				26)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				25)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				24)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				23)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		candleList.add(new CandleStick(11, 11, 11, 11, 11, new Date(2014, 10,
				22)));
		stock.setCandleSticks(candleList);
		Assert.assertTrue(stockDao.insertCurrentStock(stock));

		last40 = stockDao.getStockPrices_last40(stock.getCodeName());

		Assert.assertNotNull(last40);
		Assert.assertEquals(17, last40.size());

	}
	@Test
	
	public void testGetStockOrderByStandardDeviation_30() {

		Stock stock = new Stock("test", "test");
		Stock stock2 = new Stock("test2", "test");

		ArrayList<Stock> stockRecurvered = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(1.0);
		stock.setStandardDeviation_30(13.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		stock2.setAvarangeReturn_15(1.0);
		stock2.setAvarangeReturn_30(1.0);
		stock2.setStandardDeviation_15(1.0);
		stock2.setStandardDeviation_30(45.0);
		stock2.setVariance_15(1.0);
		stock2.setVariance_30(1.0);
		stock2.setVarianceCoefficient_15(1.0);
		stock2.setVarianceCoefficient_30(1.0);
		stock2.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock2));
		Assert.assertTrue(stockDao.updateStock(stock2));

		stockRecurvered = stockDao.getStockOrderByStandardDeviation_30(0, 20);

		Assert.assertNotNull(stockRecurvered);
		Assert.assertEquals(1, stockRecurvered.size());

	}
	@Test
	
	public void testGetStockOrderByStandardDeviation_15() {

		Stock stock = new Stock("test", "test");
		ArrayList<Stock> stockRecurvered = null;

		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();

		candleList.add(new CandleStick(10, 10, 10, 19, 10,
				new Date(2014, 11, 8)));

		stock.setAvarangeReturn_15(1.0);
		stock.setAvarangeReturn_30(1.0);
		stock.setStandardDeviation_15(13.0);
		stock.setStandardDeviation_30(13.0);
		stock.setVariance_15(1.0);
		stock.setVariance_30(1.0);
		stock.setVarianceCoefficient_15(1.0);
		stock.setVarianceCoefficient_30(1.0);
		stock.setCandleSticks(candleList);

		Assert.assertTrue(stockDao.storeHistoricalStockValue(stock));
		Assert.assertTrue(stockDao.updateStock(stock));

		stockRecurvered = stockDao.getStockOrderByStandardDeviation_15();

		Assert.assertNotNull(stockRecurvered);
		Assert.assertEquals(1, stockRecurvered.size());

	}

}
