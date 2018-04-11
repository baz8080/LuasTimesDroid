package com.mbcdev.nextluas.net;

import com.mbcdev.nextluas.constants.StopConstants;
import com.mbcdev.nextluas.model.StopTime;
import com.mbcdev.nextluas.model.StopTimes;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalTranscodeSiteConnector implements LuasInfoConnector {

	private static final String MAIN_SELECTOR = "div.stop-table";

	public StopTimes getStopTimes(final int stopNumber, final String stopName) throws IOException {
		
		String url = StopConstants.BASE_URL + stopNumber;

        Response response = Jsoup
                .connect(url)
                .validateTLSCertificates(false)
                .timeout(15000)
                .execute();

        StopTimes model = parseResults(response);

		return model;
	}

	private StopTimes parseResults(Response response) throws IOException {

		Document doc = response.parse();
		Elements stopTables = doc.select(MAIN_SELECTOR);

		List<StopTime> inbound = new ArrayList<>();
        List<StopTime> outbound = new ArrayList<>();

		// Expecting two divs with class 'stop-table'
		if (stopTables != null && stopTables.size() == 2) {
			inbound = extractTimesFromTable(stopTables.get(0));
			outbound = extractTimesFromTable(stopTables.get(1));
		}

		return new StopTimes(inbound, outbound);
	}

	private List<StopTime> extractTimesFromTable(Element table) {

	    List<StopTime> stopTimes = new ArrayList<>();

		if (table != null) {
			Elements rows = table.getElementsByTag("tr");

			if (rows.size() > 1) {
				for (int i = 1; i < rows.size(); i++) {
					Element row = rows.get(i);

					if (row != null) {
						Elements cells = row.getElementsByTag("td");

						if (cells != null && cells.size() == 2) {
							String stopName = cells.get(0).text();
							String dueAtInMinutes = cells.get(1).text();

							StopTime stopTime = new StopTime(stopName, dueAtInMinutes);
                            stopTimes.add(stopTime);
						}
					}
				}
			}
		}

		return stopTimes;
	}
}
