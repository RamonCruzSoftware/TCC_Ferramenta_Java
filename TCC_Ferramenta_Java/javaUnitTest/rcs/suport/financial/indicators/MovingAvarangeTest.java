package rcs.suport.financial.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.financial.indicators.MovingAvarange;

public class MovingAvarangeTest {

	MovingAvarange movingAvarange;
	MovingAvarange movingAvarangeExp;
	ArrayList<Double> values;
	
	@Before
	public void setUp() throws Exception {
		values=new ArrayList<Double>();
		movingAvarange = new MovingAvarange(5);
		movingAvarangeExp=new MovingAvarange(0, 10.25, 3);
		
	}

	@Test
	public void testMovingAvarange() {
		
		
		Assert.assertTrue(movingAvarange.getClass().isInstance(movingAvarange));;
	}

	@Test
	public void testSimpleAvarange() {
		
		movingAvarange= new MovingAvarange(5);
		movingAvarange.addValue(10.25);
		movingAvarange.addValue(10.25);
		movingAvarange.addValue(10.50);
		movingAvarange.addValue(10.75);
		movingAvarange.addValue(10.00);
		
		Assert.assertEquals(10.35, movingAvarange.simpleAvarange(),0.3);
		
	}

	@Test
	public void testExponencialAvarange() {
		
		
		Assert.assertEquals(5.125, movingAvarangeExp.exponencialAvarange(),0.3);
		
	}

	@Test
	public void testSetLastMMEandCurrentPrice()
	{
		ArrayList<Double> MME3=new ArrayList<Double>();
		MovingAvarange movingAvarangeExp=new MovingAvarange(0, 10.25, 3);
		MME3.add(movingAvarangeExp.exponencialAvarange());
		movingAvarangeExp.setLastMMEandCurrentPrice(MME3.get(0), 10.25);
		
		Assert.assertEquals(7.6875, movingAvarangeExp.exponencialAvarange(),0.3);
		
	}
	



}
