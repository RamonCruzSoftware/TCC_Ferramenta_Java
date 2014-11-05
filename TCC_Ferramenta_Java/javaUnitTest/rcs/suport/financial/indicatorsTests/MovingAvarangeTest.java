package rcs.suport.financial.indicatorsTests;

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
		movingAvarange = new MovingAvarange(0,5);
		movingAvarangeExp=new MovingAvarange(0, 10.25, 3);
		
	}

	@Test
	public void testMovingAvarange() {
		
		
		Assert.assertTrue(movingAvarange.getClass().isInstance(movingAvarange));;
	}

	@Test
	public void testSimpleAvarange() {
		
		values.clear();
		values.add(10.25);
		values.add(10.25);
		values.add(10.50);
		values.add(10.75);
		values.add(10.00);
		
//		movingAvarange.setValues(values);
		Assert.assertEquals(10.35, movingAvarange.simpleAvarange());
		
	}

	@Test
	public void testExponencialAvarange() {
		
		
		Assert.assertEquals(5.125, movingAvarangeExp.exponencialAvarange());
		
	}

	@Test
	public void testSetValues() {
		ArrayList<Double> list_1=new ArrayList<Double>();
		ArrayList<Double> list_2=new ArrayList<Double>();
		
		list_1.add(3.0);
		list_1.add(3.0);
		
		list_2.add(6.0);
		list_2.add(6.0);
		//MovingAvarange movingAvarange=new MovingAvarange(list_1, 3);
		
		Assert.assertEquals(2.0,movingAvarange.simpleAvarange());
		
	//	movingAvarange.setValues(list_2);
		Assert.assertEquals(4.0,movingAvarange.simpleAvarange());
 	}
	@Test
	public void testSetLastMMEandCurrentPrice()
	{
		ArrayList<Double> MME3=new ArrayList<Double>();
		MovingAvarange movingAvarangeExp=new MovingAvarange(0, 10.25, 3);
		MME3.add(movingAvarangeExp.exponencialAvarange());
		movingAvarangeExp.setLastMMEandCurrentPrice(MME3.get(0), 10.25);
		
		Assert.assertEquals(7.6875, movingAvarangeExp.exponencialAvarange());
		
	}
	



}
