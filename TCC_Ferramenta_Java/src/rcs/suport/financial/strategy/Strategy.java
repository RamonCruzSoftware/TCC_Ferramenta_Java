package rcs.suport.financial.strategy;

import rcs.suport.financial.partternsCandleStick.CandleStick;
public interface Strategy {

	public String makeOrder();
	public void addValue(double value);
	public void addCandleStick(CandleStick candleStick);
	
}