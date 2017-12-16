package com.mbcdev.nextluas.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ResultModel {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);

	private Date lastUpdated;
	
	private List<StopTime> inbound;
	
	private List<StopTime> outbound;

	public ResultModel() {		
		this.inbound 		= new ArrayList<>(5);
		this.outbound 		= new ArrayList<>(5);
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
