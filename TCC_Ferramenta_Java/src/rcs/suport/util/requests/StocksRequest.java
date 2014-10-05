package rcs.suport.util.requests;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public interface StocksRequest {
	
	public CandleStick getCurrentValue(String stockCodeName);
	public ArrayList<CandleStick> getHistoryValue(String stockCodeName);
	
	

}
