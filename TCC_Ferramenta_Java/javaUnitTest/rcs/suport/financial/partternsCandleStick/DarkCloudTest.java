package rcs.suport.financial.partternsCandleStick;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DarkCloudTest {
	private DarkCloud darkCloud;

	@Before
	public void setUp() throws Exception {
		
		darkCloud=null;
	}

	@Test
	public void testFindCandleSticksPatterns() 
	{
		ArrayList<CandleStick>candleTestList=new ArrayList<CandleStick>();
		
		 candleTestList.add(new CandleStick(2, 4, 1, 3, 0, null));
		 candleTestList.add(new CandleStick(6, 8, 5, 7, 0, null));
		 candleTestList.add(new CandleStick(10, 12, 9, 11, 0, null));
		
		 candleTestList.add(new CandleStick(9, 12, 8, 11, 0, null));
		 candleTestList.add(new CandleStick(12.5, 13, 9, 9.25,0, null));
		 
		 darkCloud = new DarkCloud(candleTestList);
		 Assert.assertEquals(candleTestList.get(4).getClose(), darkCloud.findCandleSticksPatterns().get(0).getClose(),0.0);
	}

	@Test
	public void testDarkCloud()
	{
		darkCloud= new DarkCloud();
		
		Assert.assertNotNull(darkCloud);
	}

}
