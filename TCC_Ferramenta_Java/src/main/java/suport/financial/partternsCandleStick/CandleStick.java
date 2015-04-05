package suport.financial.partternsCandleStick;

import jade.util.leap.Serializable;

import java.util.Date;

public class CandleStick implements Serializable {

	private static final long serialVersionUID = 1L;
	private double open, high, low, close, volume;
	private Date date;

	public CandleStick(double open, double high, double low, double close,
			double volume, Date date) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.date = date;
	}

	public CandleStick(String open, String high, String low, String close,
			String volume, Date date) {
		this.date = date;
		this.open = Double.parseDouble(open);
		this.high = Double.parseDouble(high);
		this.low = Double.parseDouble(low);
		this.close = Double.parseDouble(close);
		this.volume = Double.parseDouble(volume);
	}

	public double getOpen() {
		return open;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getClose() {
		return close;
	}

	public double getVolume() {
		return volume;
	}

	public Date getDate() {
		return date;
	}

	public String getInformation() {
		return "Date:" + this.date + " Open:" + this.open + " High:"
				+ this.high + " Low:" + this.low + " Close:" + this.close
				+ " Volume:" + this.volume;
	}
}