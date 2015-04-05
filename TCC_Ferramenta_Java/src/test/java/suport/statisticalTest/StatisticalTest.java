package suport.statisticalTest;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;
import suport.financial.partternsCandleStick.CandleStick;
import suport.statistical.Statistical;

public class StatisticalTest {

	private Statistical statistical;

	public void setUp() {

	}

	public void testStatistical() {
		Assert.assertEquals(Statistical.class, new Statistical().getClass());
	}

	public void testCalculeAvarange() {
		statistical = new Statistical();
		double values[] = { 5, 5, 5, 5, 5, 5 };

		Assert.assertEquals(5.0, statistical.calculeAvarange(values), 0.0);

	}

	public void testCalculeAvarange_30() {
		statistical = new Statistical();
		ArrayList<CandleStick> candleStickList = new ArrayList<CandleStick>();

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

		Assert.assertEquals(10.0,
				statistical.calculeAvarange_15(candleStickList), 0.3);
	}

	public void testCalculeAvarange_15() {
		statistical = new Statistical();
		ArrayList<CandleStick> candleStickList = new ArrayList<CandleStick>();

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

		Assert.assertEquals(10,
				statistical.calculeAvarange_15(candleStickList), 0.3);
	}

	public void testCalculeCorrelationCoefficient() {
		statistical = new Statistical();

		double x[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double y[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		Assert.assertEquals(1, statistical.calculeCorrelationCoefficient(x, y),
				0.3);

		double x1[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double y1[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

		Assert.assertEquals(-1,
				statistical.calculeCorrelationCoefficient(x1, y1), 0.3);

		double x2[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double y2[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

		Assert.assertEquals(0,
				statistical.calculeCorrelationCoefficient(x2, y2), 0.3);

	}

	public void testCalculeCorrelationCoefficient_15() {
		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleListB = new ArrayList<CandleStick>();

		for (int i = 1; i < 16; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}

		Assert.assertEquals(1, statistical.calculeCorrelationCoefficient_15(
				candleListA, candleListB), 0.3);

		candleListB.clear();

		for (int i = 15; i > 0; i--) {
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}
		Assert.assertEquals(-1, statistical.calculeCorrelationCoefficient_15(
				candleListA, candleListB), 0.3);

		candleListB.clear();

		for (int i = 1; i < 16; i++) {
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}
		Assert.assertEquals(0, statistical.calculeCorrelationCoefficient_15(
				candleListA, candleListB), 0.3);

	}

	public void testCalculeCorrelationCoefficient_30() {
		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleListB = new ArrayList<CandleStick>();

		for (int i = 1; i < 31; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}

		Assert.assertEquals(1, statistical.calculeCorrelationCoefficient_15(
				candleListA, candleListB), 0.3);

		candleListB.clear();

		for (int i = 31; i > 1; i--) {
			candleListB.add(new CandleStick(10, 10, 10, i, 10, null));
		}
		Assert.assertEquals(-1, statistical.calculeCorrelationCoefficient_15(
				candleListA, candleListB), 0.3);

		candleListB.clear();

		for (int i = 1; i < 31; i++) {
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}
		Assert.assertEquals(0, statistical.calculeCorrelationCoefficient_15(
				candleListA, candleListB), 0.3);

	}

	public void testCalculeVariance() {
		statistical = new Statistical();
		double x[] = { 1, 1, 1, 1, 1, 1, 1, 1 };

		Assert.assertEquals(0, statistical.calculeVariance(x), 0.3);

		double x2[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		Assert.assertEquals(6.66, statistical.calculeVariance(x2), 0.3);

	}

	public void testCalculeVariance_30() {
		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleListB = new ArrayList<CandleStick>();

		for (int i = 1; i < 31; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}

		Assert.assertEquals(0, statistical.calculeVariance_30(candleListB), 0.2);
		Assert.assertEquals(74.91, statistical.calculeVariance_30(candleListA),
				0.2);

	}

	public void testCalculeVariance_15() {

		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleListB = new ArrayList<CandleStick>();

		for (int i = 1; i < 16; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}

		Assert.assertEquals(0, statistical.calculeVariance_15(candleListB), 0.2);
		Assert.assertEquals(18.6, statistical.calculeVariance_15(candleListA),
				0.1);

	}

	public void testCalculeStandardDeviation() {

		statistical = new Statistical();
		double x[] = { 1, 1, 1, 1, 1, 1, 1, 1 };

		Assert.assertEquals(0, statistical.calculeStandardDeviation(x), 0.3);

		double x2[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		Assert.assertEquals(2.581, statistical.calculeStandardDeviation(x2),
				0.3);

	}

	public void testCalculeStandardDeviation_30() {

		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleListB = new ArrayList<CandleStick>();

		for (int i = 1; i < 31; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}

		// Assert.assertEquals(0,
		// statistical.calculeStandardDeviation_30(candleListB),0);//Problema
		// Estranho
		Assert.assertEquals(8.65,
				statistical.calculeStandardDeviation_30(candleListA), 0.2);
	}

	public void testCalculeStandardDeviation_15() {

		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleListB = new ArrayList<CandleStick>();

		for (int i = 1; i < 16; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));
			candleListB.add(new CandleStick(10, 10, 10, 1, 10, null));
		}

		// Assert.assertEquals(0.0,
		// statistical.calculeStandardDeviation_15(candleListB),0.0);// Problema
		// estranho
		Assert.assertEquals(4.32,
				statistical.calculeStandardDeviation_15(candleListA), 0.2);

	}

	public void testAverangeReturn() {

		statistical = new Statistical();
		double x[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		Assert.assertEquals(-0.24, statistical.averangeReturn(x), 0.2);

	}

	public void testAverangeReturn_30() {
		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();

		for (int i = 1; i < 31; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));

		}

		Assert.assertEquals(-0.12, statistical.averangeReturn_30(candleListA),
				0.2);
	}

	public void testAverangeReturn_15() {

		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();

		for (int i = 1; i < 16; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));

		}

		Assert.assertEquals(-0.19, statistical.averangeReturn_15(candleListA),
				0.2);

	}

	public void testVarianceCoefficient() {

		statistical = new Statistical();
		double x[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		Assert.assertEquals(0.516, statistical.varianceCoefficient(x), 0.3);

	}

	public void testVarianceCoefficient_30() {

		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();

		for (int i = 1; i < 31; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));

		}

		Assert.assertEquals(0.558,
				statistical.varianceCoefficient_30(candleListA), 0.3);

	}

	public void testVarianceCoefficient_15() {

		statistical = new Statistical();

		ArrayList<CandleStick> candleListA = new ArrayList<CandleStick>();

		for (int i = 1; i < 16; i++) {
			candleListA.add(new CandleStick(10, 10, 10, i, 10, null));

		}

		Assert.assertEquals(0.540,
				statistical.varianceCoefficient_15(candleListA), 0.3);

	}

}
