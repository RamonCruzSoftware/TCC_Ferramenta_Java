package suport.util.database.mongoDB.pojo;

import java.util.Date;


public class SimulationData
{
	private String expertName;
	private String order;
	private Date date;
	private double value;
	private String codeName;
	
	public SimulationData(){}
	public SimulationData(String expertName, String order, Date date, double value, String codeName)
	{
		this.expertName=expertName;
		this.order=order;
		this.date=date;
		this.value=value;
		this.codeName=codeName;
		
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	
}
