package rcs.suport.util.requests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.wallet.Stock;

public class YahooFinanceTest {
	private YahooFinance yahoo;
	private String dir_1="/Users/alissonnunes/Desktop";
	private String subDir_1="/TestJade";
	private String subDir_2="/TestJade";
	private String sectorsCsvFilePath="/Users/alissonnunes/Desktop/Setores";

	@Before
	public void setUp() throws Exception 
	{
		yahoo = new YahooFinance(dir_1,subDir_1,subDir_2);
	}

	@After
	public void tearDown() throws Exception 
	{
		
		yahoo.dropMainFolder();
		
		
	}

	@Test
	public void testYahooFinanceStringStringString() {
		
		Assert.assertNotNull(yahoo);
		
	}

	@Test
	public void testYahooFinanceStringStringStringArrayListOfStock() {
		
		YahooFinance yahooFinance= new YahooFinance(dir_1, subDir_2, subDir_2, null);
		Assert.assertNotNull(yahooFinance);
	}

	@Test
	public void testGetCurrentValue() {
		
		yahoo.storeCsvCurrentPriceStock("PETR4.SA");
		
		Assert.assertNotNull(yahoo.getCurrentValue("PETR4.SA"));
	
	
	}
	

	@Test
	public void testGetHistoricalValue() {
		
		yahoo.storeCsvHistoricalPriceStock("PETR4.SA");
		Assert.assertNotNull(yahoo.getHistoricalValue("PETR4.SA"));
	}

	@Test
	public void testLoadStocksFromCsv() {
		
		yahoo.storeCsvHistoricalPriceStock("PETR4.SA");
		Assert.assertNotNull(yahoo.loadStocksFromCsv(sectorsCsvFilePath));
	
	}

	
	@Test
	public void testGetMainDirPath() {
		
		Assert.assertEquals(yahoo.getMainDirPath(), dir_1);
		
	}

	@Test
	public void testGetSubDirPath_1() {
		
		Assert.assertEquals(yahoo.getSubDirPath_1(), subDir_1);
	}

	@Test
	public void testGetSubDirPath_2() {
		
		Assert.assertEquals(yahoo.getSubDirPath_2(), subDir_2);
	}

	@Test
	public void testGetMkdir() {
		
		Assert.assertNotNull(yahoo.getMkdir());
	}

	@Test
	public void testGetStockList() {
		
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		stockList.add(new Stock("test","test"));
		yahoo.setStockList(stockList);
		Assert.assertNotNull(yahoo.getStockList());
		
		
	}

	
}
