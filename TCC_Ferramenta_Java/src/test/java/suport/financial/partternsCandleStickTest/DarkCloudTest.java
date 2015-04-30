package suport.financial.partternsCandleStickTest;

import java.util.ArrayList;

import junit.framework.Assert;

import suport.financial.partternsCandleStick.*;

public class DarkCloudTest {
	private DarkCloud darkCloud;

	public void setUp() throws Exception {

		darkCloud = null;
	}

	public void testFindCandleSticksPatterns() {
		ArrayList<CandleStick> candleTestList = new ArrayList<CandleStick>();

		candleTestList.add(new CandleStick(2, 4, 1, 3, 0, null));
		candleTestList.add(new CandleStick(6, 8, 5, 7, 0, null));
		candleTestList.add(new CandleStick(10, 12, 9, 11, 0, null));

		candleTestList.add(new CandleStick(9, 12, 8, 11, 0, null));
		candleTestList.add(new CandleStick(12.5, 13, 9, 9.25, 0, null));

		darkCloud = new DarkCloud(candleTestList);
		Assert.assertEquals(candleTestList.get(4).getClose(), darkCloud
				.findCandleSticksPatterns().get(0).getClose(), 0.0);
	}

	public void testDarkCloud() {
		darkCloud = new DarkCloud();

		Assert.assertNotNull(darkCloud);
	}

}
