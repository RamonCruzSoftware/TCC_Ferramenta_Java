package rcs.agents.suport;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.core.agents.suport.StockChooser;
import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.dao.StockDao;

public class StockChooserTest {
	
	StockChooser stkChooser;
	ArrayList<Stock> stockList_a;
	ArrayList<Stock> stockList_b;
	

	@Before
	public void setUp() throws Exception 
	{
	
	
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testStockChooser() 
	{
		stkChooser=new StockChooser(this.stockTestList(0), 0);
		Assert.assertEquals(stkChooser.getClass(), StockChooser.class);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAnalyzeStock() {
		
		Stock stockTest= new Stock("Local","Test");
		
		stockList_a=this.stockTestList(0);	
		stkChooser=new StockChooser(stockList_a, 0);
		
		stockTest.setCandleSticks(stockList_a.get(0).getCandleSticks());
		
		Assert.assertFalse(stkChooser.analyzeStock(stockTest));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAnalyzeStockList() 
	{
		
		stockList_a=this.stockTestList(0);	
		stkChooser=new StockChooser(stockList_a, 0);
		
		Assert.assertEquals(0, stkChooser.analyzeStockList().get(1).size());
		Assert.assertEquals(4, stkChooser.analyzeStockList().get(0).size());
		
		// Com uma correlacao positiva 
		stockList_a=this.stockTestList(1);	
		stkChooser=new StockChooser(stockList_a, 1);
		
		Assert.assertEquals(0, stkChooser.analyzeStockList().get(1).size());
		Assert.assertEquals(4, stkChooser.analyzeStockList().get(0).size());

	}
	
	/*
	 * qtdPositiveCorrel=0 sem correlacao
	 * qtdPositiveCorrel=1 Uma correlacao positiva 
	 * qtdPositiveCorrel=2 Duas correlacao positivas
	 * qtdPositiveCorrel=3 Tres correlacao positivas 
	 */
	private ArrayList<Stock> stockTestList(int qtdPositiveCorrel)
	{
		
		ArrayList<Stock>stockList= new ArrayList<Stock>();
		
		Stock stock_1=new Stock("Test_1", "test");
		Stock stock_2=new Stock("Test_1", "test");
		Stock stock_3=new Stock("Test_1", "test");
		Stock stock_4=new Stock("Test_1", "test");
		
		ArrayList<CandleStick>candleList_1= new ArrayList<CandleStick>();
		ArrayList<CandleStick>candleList_2= new ArrayList<CandleStick>();
		ArrayList<CandleStick>candleList_3= new ArrayList<CandleStick>();
		ArrayList<CandleStick>candleList_4= new ArrayList<CandleStick>();
		
		
		switch (qtdPositiveCorrel)
		{
		case 0://Nenhuma correlacao positiva 
		{
			//1
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//2
			for(int i=0;i<31;i++)
			{
				candleList_2.add(new CandleStick(1, 1, 1,(i%2==0?i:i*3), 10, null));
			}
			
			//3
			for(int i=31;i>0;i--)
			{
				candleList_3.add(new CandleStick(1, 1, 1, i, 10, null));
			}
			
			//4
			for(int i=0;i<31;i++)
			{
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		case 1://Uma correlacao positiva 
		{
			//1
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//2
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//3
			for(int i=31;i>0;i--)
			{
				candleList_3.add(new CandleStick(1, 1, 1, i, 10, null));
			}
			
			//4
			for(int i=0;i<31;i++)
			{
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		case 2://Duas Correlacoes positivas 
	
		{
			//1
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//2
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//3
			for(int i=31;i>0;i--)
			{
				candleList_3.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
			
			//4
			for(int i=0;i<31;i++)
			{
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		case 3://Tres correlacoes positivas 
		{
			//1
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//2
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//3
			for(int i=31;i>0;i--)
			{
				candleList_3.add(new CandleStick(1, 1, 1, i, 10, null));
			}
			
			//4
			for(int i=0;i<31;i++)
			{
				candleList_4.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
		}
			break;

		default://Duas correlacoes positivas 
		{
			//1
			for(int i=0;i<31;i++)
			{
				candleList_1.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//2
			for(int i=0;i<31;i++)
			{
				candleList_2.add(new CandleStick(1, 1, 1, i+1, 10, null));
			}
			
			//3
			for(int i=31;i>0;i--)
			{
				candleList_3.add(new CandleStick(1, 1, 1, 1, 10, null));
			}
			
			//4
			for(int i=0;i<31;i++)
			{
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		}
		
		
		stock_1.setCandleSticks(candleList_1);
		stock_2.setCandleSticks(candleList_2);
		stock_3.setCandleSticks(candleList_3);
		stock_4.setCandleSticks(candleList_4);
		
		stockList.add(stock_1);
		stockList.add(stock_2);
		stockList.add(stock_3);
		stockList.add(stock_4);
		
		return stockList;
		
	}

}
