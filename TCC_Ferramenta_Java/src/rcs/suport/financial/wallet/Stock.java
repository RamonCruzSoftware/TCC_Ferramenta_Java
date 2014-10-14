package rcs.suport.financial.wallet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import rcs.suport.financial.partternsCandleStick.CandleStick;


public class Stock {

	 private String codeName;
	 private String type;
	 private String sector;
	 private ArrayList<CandleStick> candleSticks;
	 
	 private CandleStick currentCandleStick;
	 
	
	 
	 public Stock(){}
	 public Stock(String codeName,String sector)
	 {
		 this.codeName=codeName;
		 this.sector=sector;
	 }

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	 public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public ArrayList<CandleStick> getCandleSticks() {
		return candleSticks;
	}

	public void setCandleSticks(ArrayList<CandleStick> candleSticks) {
		this.candleSticks = candleSticks;
	}

	public CandleStick getCurrentCandleStick() 
	{
		return this.candleSticks.get(this.candleSticks.size()-1);
	}

	public void setCurrentCandleStick(CandleStick currentCandleStick) {
		this.currentCandleStick = currentCandleStick;
	}

	
	
}
