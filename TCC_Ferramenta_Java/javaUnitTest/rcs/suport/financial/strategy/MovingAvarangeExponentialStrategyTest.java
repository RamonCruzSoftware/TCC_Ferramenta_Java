package rcs.suport.financial.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.strategy.MovingAvarangeExponentialStrategy;
import rcs.suport.financial.strategy.Strategy;

public class MovingAvarangeExponentialStrategyTest {

private Strategy movingAvarangeStrategy;
	
	@Before
	public void setUp() 
	{
		
		movingAvarangeStrategy=null;

	}

	@Test
	public void testMovingAvarangeStrategy() 
	{
		movingAvarangeStrategy=new MovingAvarangeExponentialStrategy(0.00,0.0,0.0);
		Assert.assertEquals(MovingAvarangeExponentialStrategy.class, movingAvarangeStrategy.getClass());
	}

	@Test
	public void testMakeOrder() 
	{
		movingAvarangeStrategy=new MovingAvarangeExponentialStrategy(0.00,0.0,0.0);
		
		//valoes of ALLL3.SA 09/05/2013-> 11/06/2013  (dd/mm/yyyy)
		movingAvarangeStrategy.addValue(10.89);
		movingAvarangeStrategy.addValue(10.79);
		movingAvarangeStrategy.addValue(10.64);
		movingAvarangeStrategy.addValue(10.68); //Nothing
		Assert.assertEquals("nothing", movingAvarangeStrategy.makeOrder());
		
		movingAvarangeStrategy.addValue(10.72);
		movingAvarangeStrategy.addValue(10.64);
		movingAvarangeStrategy.addValue(10.69);
		movingAvarangeStrategy.addValue(10.65);
		movingAvarangeStrategy.addValue(10.75);
		movingAvarangeStrategy.addValue(11.02);
		movingAvarangeStrategy.addValue(11.05);
		movingAvarangeStrategy.addValue(11.00);
		movingAvarangeStrategy.addValue(11.03);
		movingAvarangeStrategy.addValue(11.01);
		movingAvarangeStrategy.addValue(10.80);
		movingAvarangeStrategy.addValue(10.65);
		movingAvarangeStrategy.addValue(10.67);
		movingAvarangeStrategy.addValue(10.79);
		movingAvarangeStrategy.addValue(10.75);
		movingAvarangeStrategy.addValue(10.88);
		movingAvarangeStrategy.addValue(10.45);
		movingAvarangeStrategy.addValue(10.19);
		movingAvarangeStrategy.addValue(0);
		movingAvarangeStrategy.addValue(0);//Order Buy
		
		//movingAvarangeStrategy.addValue(10.40);
		Assert.assertEquals("Sell", movingAvarangeStrategy.makeOrder());
		
		movingAvarangeStrategy.addValue(12);//Buy
		Assert.assertEquals("Buy", movingAvarangeStrategy.makeOrder());
		
		
	}

	@Test
	public void testAddValue() {
		MovingAvarangeExponentialStrategy movingExp=new MovingAvarangeExponentialStrategy(10.00,0.0,10.0);
		

	}


}
