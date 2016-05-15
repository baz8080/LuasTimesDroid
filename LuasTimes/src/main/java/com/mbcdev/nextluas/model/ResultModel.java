package com.mbcdev.nextluas.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ResultModel {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
	
	private String name;
	
	private boolean terminus;
	
	private Date lastUpdated;
	
	private List<StopTime> inbound;
	
	private List<StopTime> outbound;

	public ResultModel() {		
		this.inbound 		= new ArrayList<>(5);
		this.outbound 		= new ArrayList<>(5);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: ").append(name).append("\n");
		sb.append("terminus: ").append(terminus).append("\n");
		sb.append("last updated: ").append(lastUpdated).append("\n");
		
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
}
