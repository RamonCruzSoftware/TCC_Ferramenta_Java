package suport.financial.partternsCandleStickTest;

import java.util.ArrayList;

import junit.framework.Assert;

import suport.financial.partternsCandleStick.BullishEngulfing;
import suport.financial.partternsCandleStick.CandleStick;


public class BullishEngulfingTest {

	private BullishEngulfing bull;
	
	public void setUp() throws Exception {
		
		bull=null;
	}

	
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

	 
	public void testBullishEngulfingArrayListOfCandleStick() {
		 
	}


	 

}
