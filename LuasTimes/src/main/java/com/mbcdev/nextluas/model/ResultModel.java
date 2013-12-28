package com.mbcdev.nextluas.model;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ResultModel {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	private String name;
	
	private boolean terminus;
	
	private Date lastUpdated;
	
	private List<StopTime> inbound;
	
	private List<StopTime> outbound;

    private PrettyTime mPrettyTime;
	
	public ResultModel() {		
		this.inbound 		= new ArrayList<StopTime>(5);
		this.outbound 		= new ArrayList<StopTime>(5);
        this.mPrettyTime    = new PrettyTime();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("name: "	 + name).append("\n");
		sb.append("terminus: " + terminus).append("\n");
		sb.append("last updated: "     + lastUpdated).append("\n");
		
		sb.append("Inbound").append("\n");
		
		for (StopTime time: inbound) {
			sb.append("\t").append("Stop: ").append(time.getName()).append("\t");
			sb.append("\t").append("Mins: ").append(time.getMinutes()).append("\n");
		}
		
		sb.append("Outbound").append("\n");
		
		for (StopTime time: outbound) {
			sb.append("\t").append("Stop: ").append(time.getName()).append("\t");
			sb.append("\t").append("Mins: ").append(time.getMinutes()).append("\n");
		}
		
		return sb.toString();
	}
	
	public void addInbound(String name, String minutes) {
		StopTime time = new StopTime();
		time.setName(name);
		time.setMinutes(minutes);
		inbound.add(time);
	}
	
	public void addOutbound(String name, String minutes) {
		StopTime time = new StopTime();
		time.setName(name);
		time.setMinutes(minutes);
		outbound.add(time);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTerminus() {
		return terminus;
	}

	public void setTerminus(boolean terminus) {
		this.terminus = terminus;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public String getFormattedLastUpdated() {
		if (lastUpdated == null) {
			return "";
		}
		
		return sdf.format(lastUpdated);

        //return mPrettyTime.format(lastUpdated);
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public List<StopTime> getInbound() {
		return inbound;
	}

	public List<StopTime> getOutbound() {
		return outbound;
	}

	public class StopTime {
		
		private String name;
		
		private String minutes;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMinutes() {
			return minutes;
		}

		public void setMinutes(String minutes) {
			this.minutes = minutes;
		}	
	}	
}
