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
		
		redStops.add(new StopInformationModel("Saggart"));
		redStops.add(new StopInformationModel("Fortunestown"));
		redStops.add(new StopInformationModel("Citywest Campus"));
		redStops.add(new StopInformationModel("Cheeverstown"));
		redStops.add(new StopInformationModel("Fettercairn"));
		redStops.add(new StopInformationModel("Tallaght"));
		redStops.add(new StopInformationModel("Hospital"));
		redStops.add(new StopInformationModel("Cookstown"));
		redStops.add(new StopInformationModel("Belgard"));
		redStops.add(new StopInformationModel("Kingswood"));
		redStops.add(new StopInformationModel("Red Cow"));
		redStops.add(new StopInformationModel("Kylemore"));
		redStops.add(new StopInformationModel("Bluebell"));
		redStops.add(new StopInformationModel("Blackhorse"));
		redStops.add(new StopInformationModel("Drimnagh"));
		redStops.add(new StopInformationModel("Goldenbridge"));
		redStops.add(new StopInformationModel("Suir Road"));
		redStops.add(new StopInformationModel("Rialto"));
		redStops.add(new StopInformationModel("Fatima"));
		redStops.add(new StopInformationModel("James's"));
		redStops.add(new StopInformationModel("Heuston"));
		redStops.add(new StopInformationModel("Museum"));
		redStops.add(new StopInformationModel("Smithfield"));
		redStops.add(new StopInformationModel("Four Courts"));
		redStops.add(new StopInformationModel("Jervis"));
		redStops.add(new StopInformationModel("Abbey Street"));
		redStops.add(new StopInformationModel("Bus\u00E1ras"));
		redStops.add(new StopInformationModel("Connolly"));
		redStops.add(new StopInformationModel("George's Dock"));
		redStops.add(new StopInformationModel("Mayor Square - NCI"));
		redStops.add(new StopInformationModel("Spencer Dock"));
		redStops.add(new StopInformationModel("The Point"));

        greenStops.add(new StopInformationModel("Broombridge"));
        greenStops.add(new StopInformationModel("Cabra"));
        greenStops.add(new StopInformationModel("Phibsborough"));
        greenStops.add(new StopInformationModel("Grangegorman"));
        greenStops.add(new StopInformationModel("Broadstone - DIT"));
        greenStops.add(new StopInformationModel("Dominick"));
        greenStops.add(new StopInformationModel("Parnell"));
        greenStops.add(new StopInformationModel("Marlborough"));
        greenStops.add(new StopInformationModel("Trinity"));
        greenStops.add(new StopInformationModel("O'Connell Upper"));
        greenStops.add(new StopInformationModel("O'Connell - GPO"));
        greenStops.add(new StopInformationModel("Westmoreland"));
        greenStops.add(new StopInformationModel("Dawson"));

		greenStops.add(new StopInformationModel("St. Stephen's Green"));
		greenStops.add(new StopInformationModel("Harcourt"));
		greenStops.add(new StopInformationModel("Charlemont"));
		greenStops.add(new StopInformationModel("Ranelagh"));
		greenStops.add(new StopInformationModel("Beechwood"));
		greenStops.add(new StopInformationModel("Cowper"));
		greenStops.add(new StopInformationModel("Milltown"));
		greenStops.add(new StopInformationModel("Windy Arbour"));
		greenStops.add(new StopInformationModel("Dundrum"));
		greenStops.add(new StopInformationModel("Balally"));
		greenStops.add(new StopInformationModel("Kilmacud"));
		greenStops.add(new StopInformationModel("Stillorgan"));
		greenStops.add(new StopInformationModel("Sandyford"));
		greenStops.add(new StopInformationModel("Central Park"));
		greenStops.add(new StopInformationModel("Glencairn"));
		greenStops.add(new StopInformationModel("The Gallops"));
		greenStops.add(new StopInformationModel("Leopardstown Valley"));
		greenStops.add(new StopInformationModel("Ballyogan Wood"));
		greenStops.add(new StopInformationModel("Carrickmines"));
		greenStops.add(new StopInformationModel("Laughanstown"));
		greenStops.add(new StopInformationModel("Cherrywood"));
		greenStops.add(new StopInformationModel("Brides Glen", 194));
		
	}
		
	public static List<StopInformationModel> getRedStops() {
		return redStops;
	}
	
	public static List<StopInformationModel> getGreenStops() {
		return greenStops;
	}
}

