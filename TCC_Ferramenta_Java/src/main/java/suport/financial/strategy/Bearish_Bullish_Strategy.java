package suport.financial.strategy;

import java.util.ArrayList;

import suport.financial.partternsCandleStick.BearishEngulfing;
import suport.financial.partternsCandleStick.BullishEngulfing;
import suport.financial.partternsCandleStick.CandleStick;

public class Bearish_Bullish_Strategy implements Strategy {
	private BearishEngulfing bearish;
	private BullishEngulfing bullish;
	private ArrayList<CandleStick> candleSticks;

	public Bearish_Bullish_Strategy() {
		this.candleSticks = new ArrayList<CandleStick>();
		this.bearish = new BearishEngulfing();
		this.bullish = new BullishEngulfing();
	}

	public String makeOrder() {
		String order = null;

		this.bearish.setList(candleSticks);
		this.bullish.setList(candleSticks);
		int bearish_value = this.bearish.findCandleSticksPatterns().size();
		int bullish_value = this.bullish.findCandleSticksPatterns().size();

		if (bearish_value > 0 && bullish_value > 0)
			order = "nothing";
		else {
			if (bearish_value > 0)
				order = "Sell";
			if (bullish_value > 0)
				order = "Buy";
		}
		return order;
	}

	public void addValue(double value) {

	}

	public void addCandleStick(CandleStick candleStick) {
		this.candleSticks.add(candleStick);
	}
}