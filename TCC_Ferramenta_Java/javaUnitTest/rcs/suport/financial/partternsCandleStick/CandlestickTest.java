package rcs.suport.financial.partternsCandleStick;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CandlestickTest {

	private CandleStick candlestick;
	@Before
	public void setUp() throws Exception 
	{
		candlestick=null;
	}

	@Test
	public void testCandleStickDoubleDoubleDoubleDoubleDoubleDate()
	{
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertNotNull(candlestick);
	}

	@Test
	public void testCandleStickStringStringStringStringStringDate() {
		
		candlestick=new CandleStick("1", "1", "1", "1", "1", new Date());
		Assert.assertNotNull(candlestick);
	}

	@Test
	public void testGetOpen() {
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertEquals(1.0, candlestick.getOpen(), 0.0);
		
		candlestick=new CandleStick("1", "1", "1", "1", "1", new Date());
		Assert.assertEquals(1.0, candlestick.getOpen(), 0.0);
		
	}

	@Test
	public void testGetHigh() {
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertEquals(1.0, candlestick.getHigh(), 0.0);
		
		candlestick=new CandleStick("1", "1", "1", "1", "1", new Date());
		Assert.assertEquals(1.0, candlestick.getHigh(), 0.0);
	}

	@Test
	public void testGetLow() {
		
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertEquals(1.0, candlestick.getLow(), 0.0);
		
		candlestick=new CandleStick("1", "1", "1", "1", "1", new Date());
		Assert.assertEquals(1.0, candlestick.getLow(), 0.0);
	}

	
	@Test
	public void testGetClose() {
		
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertEquals(1.0, candlestick.getClose(), 0.0);
		
		candlestick=new CandleStick("1.0", "1.0", "1.0", "1.0", "1.0", new Date());
		Assert.assertEquals(1.0, candlestick.getClose(),.0);
	}

	@Test
	public void testGetVolume() {
		
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertEquals(1.0, candlestick.getVolume(), 0.0);
		
		candlestick=new CandleStick("1", "1", "1", "1", "1", new Date());
		Assert.assertEquals(1.0, candlestick.getVolume(), 0.0);
	}

	@Test
	public void testGetDate() {
	
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertEquals(new Date(), candlestick.getDate());
		
		candlestick=new CandleStick("1", "1", "1", "1", "1", new Date());
		Assert.assertEquals(new Date(), candlestick.getDate());
	}

	@Test
	public void testGetInformation() {
		candlestick= new CandleStick(1.0, 1.0, 1.0, 1.0, 1.0, new Date());
		Assert.assertNotNull(candlestick.getInformation());
		
		
	}

}
