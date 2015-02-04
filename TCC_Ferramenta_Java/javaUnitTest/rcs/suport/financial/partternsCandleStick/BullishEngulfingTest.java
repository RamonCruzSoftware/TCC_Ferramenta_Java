package rcs.suport.financial.partternsCandleStick;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class BullishEngulfingTest {

	private BullishEngulfing bull;
	@Before
	public void setUp() throws Exception {
		
		bull=null;
	}

	@Test
	public void testFindCandleSticksPatterns()
	{
		ArrayList<CandleStick>candleTestList=new ArrayList<CandleStick>();
		
							  //CandleStick(open, high, low, close, volume, date)
		 candleTestList.add(new CandleStick(26,29,25,28,0,null));
		 candleTestList.add(new CandleStick(26,27,23,24,0,null));
		 candleTestList.add(new CandleStick(25,26,22,23,0,null));
		
		 candleTestList.add(new CandleStick(24,25,21,22,0,null));
		 candleTestList.add(new CandleStick(21,25.1,20.9,25,0,null));
		 
		 bull=new BullishEngulfing(candleTestList);
		
		 Assert.assertEquals(candleTestList.get(4).getClose(), bull.findCandleSticksPatterns().get(0).getClose(),0.0);
		 
	}

	@Test
	public void testBullishEngulfingArrayListOfCandleStick() {
		fail("Not yet implemented");
	}

}
