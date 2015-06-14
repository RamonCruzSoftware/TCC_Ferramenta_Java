package suport.financial.strategyTest;

import org.junit.Test;

import junit.framework.Assert;
import suport.financial.strategy.MovingAvarangeSimpleStrategy;

public class MovingAvarangeSimpleStrategyTest {

	private MovingAvarangeSimpleStrategy movingAvarangeSimpleStrategy;
	@Test
	public void testMovingAvarangeSimpleStrategy() {
		movingAvarangeSimpleStrategy = new MovingAvarangeSimpleStrategy(13, 21);
		Assert.assertEquals(MovingAvarangeSimpleStrategy.class,
				movingAvarangeSimpleStrategy.getClass());
	}
	@Test
	public void testMakeOrder() {
		movingAvarangeSimpleStrategy = new MovingAvarangeSimpleStrategy(13, 21);
		movingAvarangeSimpleStrategy.addValue(10.79);
		movingAvarangeSimpleStrategy.addValue(10.64);
		movingAvarangeSimpleStrategy.addValue(10.68);
		movingAvarangeSimpleStrategy.addValue(10.72);
		movingAvarangeSimpleStrategy.addValue(10.64);
		movingAvarangeSimpleStrategy.addValue(10.75);
		movingAvarangeSimpleStrategy.addValue(11.02);
		movingAvarangeSimpleStrategy.addValue(11.05);
		movingAvarangeSimpleStrategy.addValue(11);
		movingAvarangeSimpleStrategy.addValue(11.03);
		movingAvarangeSimpleStrategy.addValue(11.01);
		movingAvarangeSimpleStrategy.addValue(10.80);
		movingAvarangeSimpleStrategy.addValue(10.65);
		movingAvarangeSimpleStrategy.addValue(10.67);
		movingAvarangeSimpleStrategy.addValue(10.79);
		movingAvarangeSimpleStrategy.addValue(10.75);
		movingAvarangeSimpleStrategy.addValue(10.88);
		movingAvarangeSimpleStrategy.addValue(10.19);
		movingAvarangeSimpleStrategy.addValue(10.34);
		movingAvarangeSimpleStrategy.addValue(10.35);
		movingAvarangeSimpleStrategy.addValue(12);
		movingAvarangeSimpleStrategy.addValue(12);// Nothing

		Assert.assertEquals("nothing", movingAvarangeSimpleStrategy.makeOrder());

		movingAvarangeSimpleStrategy.addValue(12);
		movingAvarangeSimpleStrategy.addValue(10);

		Assert.assertEquals("Sell", movingAvarangeSimpleStrategy.makeOrder());

		movingAvarangeSimpleStrategy.addValue(10);
		movingAvarangeSimpleStrategy.addValue(12);

		Assert.assertEquals("Buy", movingAvarangeSimpleStrategy.makeOrder());

		movingAvarangeSimpleStrategy.addValue(12);

	}

}
