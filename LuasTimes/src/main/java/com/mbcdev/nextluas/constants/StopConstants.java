package com.mbcdev.nextluas.constants;

import com.mbcdev.nextluas.model.StopInformation;

import java.util.ArrayList;
import java.util.List;

public class StopConstants {
	
	public static final String BASE_URL = "https://www.luas.ie/luas-pid.html?stop=";
	
	public static CharSequence[] getStopNameArray(List<StopInformation> list) {
		
		CharSequence[] names = new CharSequence[list.size()];
		
		int i = 0;
		
		for (StopInformation model: list) {
			names[i++] = model.getDisplayName();
		}
		
		return names;
	}
	
	private static final List<StopInformation> redStops = new ArrayList<>(30);
	
	
	private static final List<StopInformation> greenStops = new ArrayList<>(30);
	
	static {
		
		redStops.add(new StopInformation("Saggart", 713));
		redStops.add(new StopInformation("Fortunestown", 712));
		redStops.add(new StopInformation("Citywest Campus", 711));
		redStops.add(new StopInformation("Cheeverstown", 710));
		redStops.add(new StopInformation("Fettercairn", 709));
		redStops.add(new StopInformation("Tallaght", 103));
		redStops.add(new StopInformation("Hospital", 102));
		redStops.add(new StopInformation("Cookstown", 101));
		redStops.add(new StopInformation("Belgard", 100));
		redStops.add(new StopInformation("Kingswood", 99));
		redStops.add(new StopInformation("Red Cow", 98));
		redStops.add(new StopInformation("Kylemore", 97));
		redStops.add(new StopInformation("Bluebell", 96));
		redStops.add(new StopInformation("Blackhorse", 95));
		redStops.add(new StopInformation("Drimnagh", 94));
		redStops.add(new StopInformation("Goldenbridge", 93));
		redStops.add(new StopInformation("Suir Road", 92));
		redStops.add(new StopInformation("Rialto", 91));
		redStops.add(new StopInformation("Fatima", 90));
		redStops.add(new StopInformation("James's", 89));
		redStops.add(new StopInformation("Heuston", 88));
		redStops.add(new StopInformation("Museum", 85));
		redStops.add(new StopInformation("Smithfield", 84));
		redStops.add(new StopInformation("Four Courts", 83));
		redStops.add(new StopInformation("Jervis", 82));
		redStops.add(new StopInformation("Abbey Street", 80));
		redStops.add(new StopInformation("Bus\u00E1ras", 78));
		redStops.add(new StopInformation("Connolly", 79));
		redStops.add(new StopInformation("George's Dock", 77));
		redStops.add(new StopInformation("Mayor Square - NCI", 76));
		redStops.add(new StopInformation("Spencer Dock", 74));
		redStops.add(new StopInformation("The Point", 58));

        greenStops.add(new StopInformation("Broombridge", 2832));
        greenStops.add(new StopInformation("Cabra", 2833));
        greenStops.add(new StopInformation("Phibsborough", 2834));
        greenStops.add(new StopInformation("Grangegorman", 2835));
        greenStops.add(new StopInformation("Broadstone - DIT", 2836));
        greenStops.add(new StopInformation("Dominick", 2837));
        greenStops.add(new StopInformation("Parnell", 2838));
        greenStops.add(new StopInformation("Marlborough", 2839));
        greenStops.add(new StopInformation("Trinity", 2840));
        greenStops.add(new StopInformation("O'Connell Upper", 2843));
        greenStops.add(new StopInformation("O'Connell - GPO", 2842));
        greenStops.add(new StopInformation("Westmoreland", 2841));
        greenStops.add(new StopInformation("Dawson", 2865));

		greenStops.add(new StopInformation("St. Stephen's Green", 104));
		greenStops.add(new StopInformation("Harcourt", 105));
		greenStops.add(new StopInformation("Charlemont", 106));
		greenStops.add(new StopInformation("Ranelagh", 107));
		greenStops.add(new StopInformation("Beechwood", 108));
		greenStops.add(new StopInformation("Cowper", 109));
		greenStops.add(new StopInformation("Milltown", 110));
		greenStops.add(new StopInformation("Windy Arbour", 111));
		greenStops.add(new StopInformation("Dundrum", 112));
		greenStops.add(new StopInformation("Balally", 113));
		greenStops.add(new StopInformation("Kilmacud", 114));
		greenStops.add(new StopInformation("Stillorgan", 115));
		greenStops.add(new StopInformation("Sandyford", 116));
		greenStops.add(new StopInformation("Central Park", 184));
		greenStops.add(new StopInformation("Glencairn", 185));
		greenStops.add(new StopInformation("The Gallops", 186));
		greenStops.add(new StopInformation("Leopardstown Valley", 187));
		greenStops.add(new StopInformation("Ballyogan Wood", 188));
		greenStops.add(new StopInformation("Carrickmines", 190));
		greenStops.add(new StopInformation("Laughanstown", 192));
		greenStops.add(new StopInformation("Cherrywood", 193));
		greenStops.add(new StopInformation("Brides Glen", 194));
		
	}
		
	public static List<StopInformation> getRedStops() {
		return redStops;
	}
	
	public static List<StopInformation> getGreenStops() {
		return greenStops;
	}
}

