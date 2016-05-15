package com.mbcdev.nextluas.net;

import com.mbcdev.nextluas.constants.StopConstants;
import com.mbcdev.nextluas.model.ResultModel;
import com.mbcdev.nextluas.model.StopTime;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class LocalTranscodeSiteConnector implements LuasInfoConnector {

	private static final String MAIN_SELECTOR = "div";

	private static final String LOCATION_SELECTOR 	= "div.location";
	
	private static final String TIME_SELECTOR 		= "div.time";
	
	public ResultModel getStopInfo(final String stopSuffix, final String stopName) throws IOException {
		
		String url = StopConstants.BASE_URL + stopSuffix;

		ResultModel model = new ResultModel();
		model.setName(stopName);

        Response inboundResponse = Jsoup
                .connect(url + "&direction=Inbound")
                .validateTLSCertificates(false)
                .timeout(15000)
                .execute();

		parseResults(inboundResponse, model.getInbound());

		Response outboundResponse = Jsoup
				.connect(url + "&direction=Outbound")
				.validateTLSCertificates(false)
				.timeout(15000)
				.execute();

		parseResults(outboundResponse, model.getOutbound());

		model.setLastUpdated(new Date());
		return model;
	}

	private void parseResults(Response response, List<StopTime> stopTimes) throws IOException {

		Document doc = response.parse();
		Element stage = doc.select(MAIN_SELECTOR).first();
		
		if (stage != null) {
						
			Elements names = stage.select(LOCATION_SELECTOR);
			Elements times = stage.select(TIME_SELECTOR);
			
			for (int i = 0; i < names.size(); i++) {
				StopTime stopTime = new StopTime();
				stopTime.setName(names.get(i).text());
				stopTime.setMinutes(times.get(i).text());
				stopTimes.add(stopTime);
			}
			
		} else {
			// LOG EMPTY
		}
	}

}
