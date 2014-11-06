package rcs.suport.statistical;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.partternsCandleStick.CandlestickTest;
import rcs.suport.financial.wallet.Stock;

public class StatisticalTest {
	
	private Statistical statistical;

	@Test
	public void testCalculeAvarange()
	{
		statistical = new Statistical();
		double values[] = {5,5,5,5,5,5};
	
		Assert.assertEquals(5.0, statistical.calculeAvarange(values),0.0);
		
		
	}

	@Test
	public void testCalculeAvarange_30() 
	{
		statistical= new Statistical();
		ArrayList<CandleStick>candleStickList= new ArrayList<CandleStick>();
		
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10, 10, 10, new Date()));

		
		
		Assert.assertEquals(10.0,statistical.calculeAvarange_15(candleStickList) ,0.3);
	}

	@Test
	public void testCalculeAvarange_15()
	{
		statistical= new Statistical();
		ArrayList<CandleStick>candleStickList= new ArrayList<CandleStick>();
		
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		candleStickList.add(new CandleStick(10, 10, 10.00, 10, 10, new Date()));
		
		Assert.assertEquals(10,statistical.calculeAvarange_15(candleStickList) ,0.3);
	}

	@Test
	public void testCalculeCorrelationCoefficient()
	{
		statistical= new Statistical();
		
		double x[]={1,2,3,4,5,6,7,8,9,10};
		double y[]={1,2,3,4,5,6,7,8,9,10};
		
		Assert.assertEquals(1, statistical.calculeCorrelationCoefficient(x, y),0.3);
		
		double x1[]={1,2,3,4,5,6,7,8,9,10};
		double y1[]={10,9,8,7,6,5,4,3,2,1};
		
		Assert.assertEquals(-1, statistical.calculeCorrelationCoefficient(x1, y1),0.3);
		
		
		double x2[]={1,2,3,4,5,6,7,8,9,10};
		double y2[]={1,1,1,1,1,1,1,1,1,1};
		
		Assert.assertEquals(0, statistical.calculeCorrelationCoefficient(x2, y2),0.3);
		
		
	}

	@Test
	public void testCalculeCorrelationCoefficient_15() 
	{
		statistical= new Statistical();
		
		ArrayList<CandleStick>candleListA= new ArrayList<CandleStick>();
		ArrayList<CandleStick>candleListB= new ArrayList<CandleStick>();
		
		for(int i=1;i<16;i++)
		{
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}
		
		
		Assert.assertEquals(1, statistical.calculeCorrelationCoefficient_15(candleListA, candleListB),0.3);
		
		candleListB.clear();
		
		for(int i=15;i>0;i--)
		{
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}
		Assert.assertEquals(-1, statistical.calculeCorrelationCoefficient_15(candleListA, candleListB),0.3);
		
		candleListB.clear();
		
		for(int i=1;i<16;i++)
		{
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}
		Assert.assertEquals(0, statistical.calculeCorrelationCoefficient_15(candleListA, candleListB),0.3);
		
		
	}

	@Test
	public void testCalculeCorrelationCoefficient_30() 
	{
		statistical= new Statistical();
		
		ArrayList<CandleStick>candleListA= new ArrayList<CandleStick>();
		ArrayList<CandleStick>candleListB= new ArrayList<CandleStick>();
		
		for(int i=1;i<31;i++)
		{
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}
		
		
		Assert.assertEquals(1, statistical.calculeCorrelationCoefficient_15(candleListA, candleListB),0.3);
		
		candleListB.clear();
		
		for(int i=31;i>1;i--)
		{
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}
		Assert.assertEquals(-1, statistical.calculeCorrelationCoefficient_15(candleListA, candleListB),0.3);
		
		candleListB.clear();
		
		for(int i=1;i<31;i++)
		{
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}
		Assert.assertEquals(0, statistical.calculeCorrelationCoefficient_15(candleListA, candleListB),0.3);
		
		
	}

	@Test
	public void testCalculeVariance() 
	{
		statistical= new Statistical();
		double x[]={1,1,1,1,1,1,1,1};
		
		Assert.assertEquals(0, statistical.calculeVariance(x),0.3);
		
		double x2[]={1,2,3,4,5,6,7,8,9};
		
		Assert.assertEquals(6.66, statistical.calculeVariance(x2),0.3);
		
		
		
	}

	@Test
	public void testCalculeVariance_30() {
		
	}

	@Test
	public void testCalculeVariance_15() {
		
	}

	@Test
	public void testCalculeStandardDeviation() {
		
	}

	@Test
	public void testCalculeStandardDeviation_30() {
		
	}

	@Test
	public void testCalculeStandardDeviation_15() {
		
	}

	@Test
	public void testAverangeReturn() {
		
	}

	@Test
	public void testAverangeReturn_30() {
		
	}

	@Test
	public void testAverangeReturn_15() {
		
	}

	@Test
	public void testVarianceCoefficient() {
		
	}

	@Test
	public void testVarianceCoefficient_30() {
		
	}

	@Test
	public void testVarianceCoefficient_15() {
		
	}

}
