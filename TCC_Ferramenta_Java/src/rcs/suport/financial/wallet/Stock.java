package rcs.suport.financial.wallet;

import java.util.Date;
import java.util.Map;
import rcs.suport.financial.partternsCandleStick.CandleStick;


public class Stock {

	 private String codeName;
	 private String type;
	 private String companyShortName;
	 private String sector;
	 private Map<Date,CandleStick> candleSticks;
	 private Map<Date, Double> volumes;
	 
	 private CandleStick currentCandleStick;
	 private Double currentVolume;
	 
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

	public String getCompanyShortName() {
		return companyShortName;
	}

	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Map<Date,CandleStick> getCandleSticks() {
		return candleSticks;
	}

	public void setCandleSticks(Map<Date,CandleStick> candleSticks) {
		this.candleSticks = candleSticks;
	}

	Map<Date, Double> getVolumes() {
		return volumes;
	}

	void setVolumes(Map<Date, Double> volumes) {
		this.volumes = volumes;
	}

	public CandleStick getCurrentCandleStick() {
		return currentCandleStick;
	}

	public void setCurrentCandleStick(CandleStick currentCandleStick) {
		this.currentCandleStick = currentCandleStick;
	}

	public Double getCurrentVolume() {
		return currentVolume;
	}

	public void setCurrentVolume(Double currentVolume) {
		this.currentVolume = currentVolume;
	}
	
	
	

	
}
