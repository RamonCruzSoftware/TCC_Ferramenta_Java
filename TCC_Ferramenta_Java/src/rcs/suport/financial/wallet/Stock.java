package rcs.suport.financial.wallet;

import java.util.Date;
import java.util.Map;
import rcs.suport.financial.partternsCandleStick.CandleStick;


public class Stock {

	 String codeName;
	 String type;
	 String companyShortName;
	 String sector;
	 Map<Date,CandleStick> candleSticks;
	 Map<Date, Double> volumes;
	 
	 CandleStick currentCandleStick;
	 Double currentVolume;
	 
	 Stock(){}
	
	
	

	
}
