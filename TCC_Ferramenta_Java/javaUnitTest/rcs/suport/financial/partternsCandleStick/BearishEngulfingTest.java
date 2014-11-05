package rcs.suport.financial.partternsCandleStick;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class BearishEngulfingTest {
	
	private BearishEngulfing bearishEngulfing;
	

	@Before
	public void setUp() throws Exception 
	{
		bearishEngulfing= new BearishEngulfing();
	}
	
	 @Test
	 public void findCandleStickPatternsTest()
	 {
		 ArrayList<CandleStick>candleTestList=new ArrayList<CandleStick>();
		
		 candleTestList.add(new CandleStick(2, 4, 1, 3, 0, null));
		 candleTestList.add(new CandleStick(6, 8, 5, 7, 0, null));
		 candleTestList.add(new CandleStick(10, 12, 9, 11, 0, null));
		 candleTestList.add(new CandleStick(9, 12, 8, 11, 0, null));
		 candleTestList.add(new CandleStick(12.5, 13, 9, 8,0, null));
		
		 
		 Assert.assertNotNull(bearishEngulfing);
		 
		 bearishEngulfing.setList(candleTestList);
		 Assert.assertNotNull(bearishEngulfing.findCandleSticksPatterns());
		
		 
		 Assert.assertEquals(bearishEngulfing.findCandleSticksPatterns().get(0).getClose(), candleTestList.get(4).getClose());
		
	 }

}
