package com.mbcdev.nextluas.constants;

import com.mbcdev.nextluas.model.StopInformationModel;

import java.util.ArrayList;
import java.util.List;

public class StopConstants {
	
	public static final String BASE_URL = "https://www.luas.ie/luas-pid.html?stop=";
	
	public static CharSequence[] getStopNameArray(List<StopInformationModel> list) {
		
		CharSequence[] names = new CharSequence[list.size()];
		
		int i = 0;
		
		for (StopInformationModel model: list) {
			names[i++] = model.getDisplayName();
		}
		
		return names;
	}
	
	private static final List<StopInformationModel> redStops = new ArrayList<>(30);
	
	
	private static final List<StopInformationModel> greenStops = new ArrayList<>(30);
	
	static {
		
		redStops.add(new StopInformationModel("Saggart", 713));
		redStops.add(new StopInformationModel("Fortunestown", 712));
		redStops.add(new StopInformationModel("Citywest Campus", 711));
		redStops.add(new StopInformationModel("Cheeverstown", 710));
		redStops.add(new StopInformationModel("Fettercairn", 709));
		redStops.add(new StopInformationModel("Tallaght", 103));
		redStops.add(new StopInformationModel("Hospital", 102));
		redStops.add(new StopInformationModel("Cookstown", 101));
		redStops.add(new StopInformationModel("Belgard", 100));
		redStops.add(new StopInformationModel("Kingswood", 99));
		redStops.add(new StopInformationModel("Red Cow", 98));
		redStops.add(new StopInformationModel("Kylemore", 97));
		redStops.add(new StopInformationModel("Bluebell", 96));
		redStops.add(new StopInformationModel("Blackhorse", 95));
		redStops.add(new StopInformationModel("Drimnagh", 94));
		redStops.add(new StopInformationModel("Goldenbridge", 93));
		redStops.add(new StopInformationModel("Suir Road", 92));
		redStops.add(new StopInformationModel("Rialto", 91));
		redStops.add(new StopInformationModel("Fatima", 90));
		redStops.add(new StopInformationModel("James's", 89));
		redStops.add(new StopInformationModel("Heuston", 88));
		redStops.add(new StopInformationModel("Museum", 85));
		redStops.add(new StopInformationModel("Smithfield", 84));
		redStops.add(new StopInformationModel("Four Courts", 83));
		redStops.add(new StopInformationModel("Jervis", 82));
		redStops.add(new StopInformationModel("Abbey Street", 80));
		redStops.add(new StopInformationModel("Bus\u00E1ras", 78));
		redStops.add(new StopInformationModel("Connolly", 79));
		redStops.add(new StopInformationModel("George's Dock", 77));
		redStops.add(new StopInformationModel("Mayor Square - NCI", 76));
		redStops.add(new StopInformationModel("Spencer Dock", 74));
		redStops.add(new StopInformationModel("The Point", 58));

        greenStops.add(new StopInformationModel("Broombridge", 2832));
        greenStops.add(new StopInformationModel("Cabra", 2833));
        greenStops.add(new StopInformationModel("Phibsborough", 2834));
        greenStops.add(new StopInformationModel("Grangegorman", 2835));
        greenStops.add(new StopInformationModel("Broadstone - DIT", 2836));
        greenStops.add(new StopInformationModel("Dominick", 2837));
        greenStops.add(new StopInformationModel("Parnell", 2838));
        greenStops.add(new StopInformationModel("Marlborough", 2839));
        greenStops.add(new StopInformationModel("Trinity", 2840));
        greenStops.add(new StopInformationModel("O'Connell Upper", 2843));
        greenStops.add(new StopInformationModel("O'Connell - GPO", 2842));
        greenStops.add(new StopInformationModel("Westmoreland", 2841));
        greenStops.add(new StopInformationModel("Dawson", 2865));

		greenStops.add(new StopInformationModel("St. Stephen's Green", 104));
		greenStops.add(new StopInformationModel("Harcourt", 105));
		greenStops.add(new StopInformationModel("Charlemont", 106));
		greenStops.add(new StopInformationModel("Ranelagh", 107));
		greenStops.add(new StopInformationModel("Beechwood", 108));
		greenStops.add(new StopInformationModel("Cowper", 109));
		greenStops.add(new StopInformationModel("Milltown", 110));
		greenStops.add(new StopInformationModel("Windy Arbour", 111));
		greenStops.add(new StopInformationModel("Dundrum", 112));
		greenStops.add(new StopInformationModel("Balally", 113));
		greenStops.add(new StopInformationModel("Kilmacud", 114));
		greenStops.add(new StopInformationModel("Stillorgan", 115));
		greenStops.add(new StopInformationModel("Sandyford", 116));
		greenStops.add(new StopInformationModel("Central Park", 184));
		greenStops.add(new StopInformationModel("Glencairn", 185));
		greenStops.add(new StopInformationModel("The Gallops", 186));
		greenStops.add(new StopInformationModel("Leopardstown Valley", 187));
		greenStops.add(new StopInformationModel("Ballyogan Wood", 188));
		greenStops.add(new StopInformationModel("Carrickmines", 190));
		greenStops.add(new StopInformationModel("Laughanstown", 192));
		greenStops.add(new StopInformationModel("Cherrywood", 193));
		greenStops.add(new StopInformationModel("Brides Glen", 194));
		
	}
		
	public static List<StopInformationModel> getRedStops() {
		return redStops;
	}
	
	public static List<StopInformationModel> getGreenStops() {
		return greenStops;
	}
}

