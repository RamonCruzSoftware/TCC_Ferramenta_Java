package rcs.suport.financial.strategy;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.BullishEngulfing;
import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.partternsCandleStick.DarkCloud;

public class DarckCloud_Bullish_Strategy implements Strategy{
	private DarkCloud darkCloud;
	private BullishEngulfing bullish;
	
	public DarckCloud_Bullish_Strategy()
	{
		this.candleSticks=new ArrayList<CandleStick>();
		this.darkCloud= new DarkCloud();
		this.bullish= new BullishEngulfing();
	}
	private ArrayList<CandleStick>candleSticks;
	@Override
	public String makeOrder() 
	{
		String order=null;	
		this.darkCloud.setList(candleSticks);
		this.bullish.setList(candleSticks);
		int dark_value=this.darkCloud.findCandleSticksPatterns().size();
		int bullish_value=this.bullish.findCandleSticksPatterns().size();
		
		if(dark_value>0 && bullish_value>0) order ="nothing";
		else 
		{
			if(dark_value>0)order="Sell";
			if(bullish_value>0)order="Buy";
		}
		return order;
	}
	@Override
	public void addValue(double value) 
	{	
	}
	@Override
	public void addCandleStick(CandleStick candleStick) 
	{
		this.candleSticks.add(candleStick);
	}
}