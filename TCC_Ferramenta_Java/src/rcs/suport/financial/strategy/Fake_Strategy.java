package rcs.suport.financial.strategy;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class Fake_Strategy implements Strategy {

	@Override
	public String makeOrder() 
	{
		double i= Math.ceil( Math.random()*100);
		
		return (i%2==0)?"Buy":"Sell";
	}

	@Override
	public void addValue(double value) {
		
		
	}

	@Override
	public void addCandleStick(CandleStick candleStick) {
	
		
	}

}
