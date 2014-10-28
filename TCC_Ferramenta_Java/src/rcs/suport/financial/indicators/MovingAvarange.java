package rcs.suport.financial.indicators;

import java.util.ArrayList;
import java.util.Iterator;

public class MovingAvarange {


	private double value;
	private ArrayList<Double>values;
	private double currentPrice,lastMME;
	private int period;
	
	/**
	 * For to calculate the Simple MovingAvarange 
	 * pass the values list and period
	 * @param values
	 * @param period
	 */
	public MovingAvarange(double value,int period)
	{
		this.value=value;
		this.period=period;
		this.values= new ArrayList<Double>();
		
		
	}
	/**
	 * For to calculate the Exponencial Moving Avarange
	 * pass the values last Avarange in lastMME, Currente price in 
	 * currentPrice and period in period
	 * 
	 * @param lastMME
	 * @param currentPrice
	 * @param period
	 */
	public MovingAvarange(double lastMME,double currentPrice, int period)
	{
		this.lastMME=lastMME;
		this.currentPrice=currentPrice;
		this.period=period;
		
	}

	public double simpleAvarange()
	{
		double result=0;
		
		if(this.values.size()>0 && this.values.size()>=this.period)
		{
			for(int i=(this.values.size()-1);i>(this.values.size()-1-this.period);i--)
			{
				result+=this.values.get(i);
			}
		}
		return (result/period);
	}
	public double exponencialAvarange()
	{
		double K,result;
		K=(1+period);
		result=(currentPrice-lastMME)*(2/K)+lastMME;
		return result;
	}
	/**
	 * 
	 * @param value 
	 * insert an list of values always
	 * that you want to calculate a new value of 
	 * moving avarange
	 */
	public void addValue(double value)
	{
		this.values.add(value);
	}
	
	/**
	 * For to recalcute the Exponencial value 
	 * inform the new values in paramters 
	 * @param lastMME
	 * @param currentPrice
	 */
	public void setLastMMEandCurrentPrice(double lastMME, double currentPrice)
	{
		this.lastMME=lastMME;
		this.currentPrice=currentPrice;
		
	}
	
}