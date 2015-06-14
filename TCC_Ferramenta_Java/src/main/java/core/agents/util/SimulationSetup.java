package core.agents.util;

import java.util.Calendar;
import java.util.Date;

public class SimulationSetup {
	
	private  Date startDate;
	
	private  Date finishDate;
	private  Calendar startDateCalendar;
	
	@SuppressWarnings("deprecation")
	public SimulationSetup()
	{
		// 113 = 2013 -1900
		this.startDate=new Date(112, 3, 1);
		this.finishDate=new Date(115, 3, 1);
		
		this.startDateCalendar=Calendar.getInstance();
		this.startDateCalendar.set(2012, 2, 1);
		
	
//		// 113 = 2013 -1900
//				this.startDate=new Date(114, 1, 1);
//				this.finishDate=new Date(115, 3, 1);
//				
//				this.startDateCalendar=Calendar.getInstance();
//				this.startDateCalendar.set(2014, 1, 1);
//		
	}

	public  Date getStartDate() {
		return startDate;
	}

	public  void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public  Date getFinishDate() {
		return finishDate;
	}

	public  void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public  Calendar getStartDateCalendar() {
		return startDateCalendar;
	}

	public  void setStartDateCalendar(Calendar startDateCalendar) {
		this.startDateCalendar = startDateCalendar;
	}

}
