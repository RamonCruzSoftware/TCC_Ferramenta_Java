package suport.util.requestsTest;

import java.util.ArrayList;

import junit.framework.Assert;
import suport.financial.wallet.Stock;
import suport.util.requests.YahooFinance;

public class YahooFinanceTest {
	private YahooFinance yahoo;
	private String dir_1="/Users/alissonnunes/Desktop";
	private String subDir_1="/TestJade";
	private String subDir_2="/TestJade";
	private String sectorsCsvFilePath="/Users/alissonnunes/Desktop/Setores";

	 
	public void setUp() throws Exception 
	{
		yahoo = new YahooFinance(dir_1,subDir_1,subDir_2);
	}

	 
	public void tearDown() throws Exception 
	{
		
		yahoo.dropMainFolder();
		
		
	}

	 
	public void testYahooFinanceStringStringString() {
		
		Assert.assertNotNull(yahoo);
		
	}

	 
	public void testYahooFinanceStringStringStringArrayListOfStock() {
		
		YahooFinance yahooFinance= new YahooFinance(dir_1, subDir_2, subDir_2, null);
		Assert.assertNotNull(yahooFinance);
	}

	 
	public void testGetCurrentValue() {
		
		yahoo.storeCsvCurrentPriceStock("PETR4.SA");
		
		Assert.assertNotNull(yahoo.getCurrentValue("PETR4.SA"));
	
	
	}
	

	 
	public void testGetHistoricalValue() {
		
		yahoo.storeCsvHistoricalPriceStock("PETR4.SA");
		Assert.assertNotNull(yahoo.getHistoricalValue("PETR4.SA"));
	}

	 
	public void testLoadStocksFromCsv() {
		
		yahoo.storeCsvHistoricalPriceStock("PETR4.SA");
		Assert.assertNotNull(yahoo.loadStocksFromCsv(sectorsCsvFilePath));
	
	}
 
	public void testGetMainDirPath() {
		
		Assert.assertEquals(yahoo.getMainDirPath(), dir_1);
		
	}

	 
	public void testGetSubDirPath_1() {
		
		Assert.assertEquals(yahoo.getSubDirPath_1(), subDir_1);
	}

	 
	public void testGetSubDirPath_2() {
		
		Assert.assertEquals(yahoo.getSubDirPath_2(), subDir_2);
	}

	
	public void testGetMkdir() {
		
		Assert.assertNotNull(yahoo.getMkdir());
	}

	
	public void testGetStockList() {
		
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		stockList.add(new Stock("test","test"));
		yahoo.setStockList(stockList);
		Assert.assertNotNull(yahoo.getStockList());
		
		
	}

	
}
