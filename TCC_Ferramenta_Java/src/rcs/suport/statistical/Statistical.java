package rcs.suport.statistical;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class Statistical {

	public double calculeAvarange(double[] values)
	{
		double avarange = 0.0;
		
		for(int i = 0; i < values.length; i++)
		{
			avarange += values[i] / values.length;
		}
		
		return avarange;
	}
	public double calculeAvarange_30(ArrayList<CandleStick> candlesticks)
	{
		double avarange=0;
		
		if(candlesticks.size()>=30)
		{
			for(int i=0;i<30;i++)
			{
				avarange+=candlesticks.get(i).getClose()/30;
			}
			
		}else
		{
			for(int i=0;i<candlesticks.size();i++)
			{
				avarange+=candlesticks.get(i).getClose()/candlesticks.size();
			}
			
		}
		
		
		return avarange;
	}
	public double calculeAvarange_15(ArrayList<CandleStick> candlesticks)
	{
		double avarange=0;
		
		if(candlesticks.size()>=15)
		{
			for(int i=0;i<15;i++)
			{
				avarange+=candlesticks.get(i).getClose()/15;
				
			}
		}else
		{
			for(int i=0;i<candlesticks.size();i++)
			{
				avarange+=candlesticks.get(i).getClose()/candlesticks.size();
				
			}
		}
		
		return avarange;
	}
	
	public double calculeVariance(double[] values)
	{
		double variance = 0.0;
		double avarange = calculeAvarange(values);
		
		for(int i = 0; i < values.length; i++)
		{
			variance += Math.pow((values[i] - avarange), 2)/(values.length);
		}
		
		return variance;
		
	}
	public double calculeVariance_30(ArrayList<CandleStick> candlesticks)
	{
		double variance=0;
		double avarange = calculeAvarange_30(candlesticks);
		
		if(candlesticks.size()>=30)
		{
			for(int i=0;i<30;i++)
			{
				variance+=Math.pow((candlesticks.get(i).getClose()-avarange), 2)/30;
			}
		}else
		{
			for(int i=0;i<candlesticks.size();i++)
			{
				variance+=Math.pow((candlesticks.get(i).getClose()-avarange), 2)/candlesticks.size();
			}
		}
		
		
		return variance;
	}
	public double calculeVariance_15(ArrayList<CandleStick>candleSticks)
	{
		double variance=0;
		double avarange=calculeAvarange_15(candleSticks);
		
		if(candleSticks.size()>=15)
		{
			for(int i=0;i<15;i++)
			{
				variance+=Math.pow((candleSticks.get(i).getClose()-avarange), 2)/15;
			}
			
		}else
		{
			for(int i=0;i<candleSticks.size();i++)
			{
				variance+=Math.pow((candleSticks.get(i).getClose()-avarange), 2)/candleSticks.size();
			}
			
		}
		
		
		
		return variance;
	}
	
	public double calculeStandardDeviation(double[] values)
	{	
		return Math.sqrt(calculeVariance(values));
		
	}
	public double calculeStandardDeviation_30(ArrayList<CandleStick> candlesticks)
	{
		
		return Math.sqrt(calculeAvarange_30(candlesticks));
	}
	
	public double calculeStandardDeviation_15(ArrayList<CandleStick> candlesticks)
	{
		
		return Math.sqrt(calculeAvarange_15(candlesticks));
	}
	
	
	public double averangeReturn(double[] values )
	{

		double averangeReturn=0;
		for(int i=0;i<(values.length-1);i++)
		averangeReturn+=Math.log(values[i]/values[i+1])/(values.length-1);
	
		return averangeReturn;
	}
	
	public double averangeReturn_30(ArrayList<CandleStick> candlesticks)
	{
		double avarangeReturn=0;
		
		if(candlesticks.size()>=30)
		{
			for(int i=0;i<30;i++)
			{
				avarangeReturn+=Math.log(candlesticks.get(i).getClose()/candlesticks.get(i+1).getClose())/(30);
			}
		}else
		{
			for(int i=0;i<candlesticks.size()-1;i++)
			{
				avarangeReturn+=Math.log(candlesticks.get(i).getClose()/candlesticks.get(i+1).getClose())/(candlesticks.size());
			}
		}
		
		
		return avarangeReturn;
	}
	public double averangeReturn_15(ArrayList<CandleStick> candlesticks)
	{
		double avarangeReturn=0;
		
		if(candlesticks.size()>15)
		{
			for(int i=0;i<15;i++)
			{
				avarangeReturn+=Math.log(candlesticks.get(i).getClose()/candlesticks.get(i+1).getClose())/(15);
			}
		}else
		{
			for(int i=0;i<candlesticks.size()-1;i++)
			{
				avarangeReturn+=Math.log(candlesticks.get(i).getClose()/candlesticks.get(i+1).getClose())/(candlesticks.size());
			}
		}
		
		
		return avarangeReturn;
	}
	
	public double varianceCoefficient (double[] values)
	{
	 return calculeStandardDeviation(values)/calculeAvarange(values);
	}
	
	public double varianceCoefficient_30(ArrayList<CandleStick>candlesticks)
	{
		
		return calculeAvarange_30(candlesticks)/calculeAvarange_30(candlesticks);
	}
	
	public double varianceCoefficient_15(ArrayList<CandleStick>candlesticks)
	{
		
		return calculeAvarange_15(candlesticks)/calculeAvarange_15(candlesticks);
	}
	
}
