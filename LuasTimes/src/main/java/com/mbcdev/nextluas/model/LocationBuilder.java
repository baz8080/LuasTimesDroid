package com.mbcdev.nextluas.model;

import android.location.Location;

public class LocationBuilder {

	private Location location;
	
	public LocationBuilder() {
		location = new Location("luastimes");
	}
	
	public LocationBuilder latitude(double latitude) {
		location.setLatitude(latitude);
		return this;
	}
	
	public LocationBuilder longitude(double longitude) {
		location.setLongitude(longitude);
		return this;
	}
	
	public Location location() {
		return location;
	}
	
}
