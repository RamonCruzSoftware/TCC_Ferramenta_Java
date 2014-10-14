package rcs.suport.statistical;

public class Statistical {

	double calculeAvarange(double[] values)
	{
		double avarange = 0.0;
		
		for(int i = 0; i < values.length; i++)
		{
			avarange += values[i] / values.length;
		}
		
		return avarange;
	}
	
	double calculeVariance(double[] values)
	{
		double variance = 0.0;
		double avarange = calculeAvarange(values);
		
		for(int i = 0; i < values.length; i++)
		{
			variance += Math.pow((values[i] - avarange), 2)/(values.length-1);
		}
		
		return variance;
		
	}
	double calculeStandardDeviation(double[] values)
	{	
		return Math.sqrt(calculeVariance(values));
		
	}
	
	double averangeReturn(double[] values )
	{

		double averangeReturn=0;
		for(int i=0;i<(values.length-1);i++)
		averangeReturn+=Math.log(values[i]/values[i+1])/(values.length-1);
	
		return averangeReturn;
	}
	
	double varianceCoefficient (double[] values)
	{
	 return calculeStandardDeviation(values)/calculeAvarange(values);
	}
	
}
