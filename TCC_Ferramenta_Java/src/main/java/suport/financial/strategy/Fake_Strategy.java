package suport.financial.strategy;

import suport.financial.partternsCandleStick.CandleStick;

public class Fake_Strategy implements Strategy {

	public String makeOrder() {
		double i = Math.ceil(Math.random() * 100);
		return (i % 2 == 0) ? "Buy" : "Sell";
	}

	public void addValue(double value) {
	}

	public void addCandleStick(CandleStick candleStick) {
	}
}