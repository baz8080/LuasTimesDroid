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

	private static final String MAIN_SELECTOR = "div.stop-table";

	public ResultModel getStopInfo(final int stopNumber, final String stopName) throws IOException {
		
		String url = StopConstants.BASE_URL + stopNumber;

		ResultModel model = new ResultModel();

        Response response = Jsoup
                .connect(url)
                .validateTLSCertificates(false)
                .timeout(15000)
                .execute();

		parseResults(response, model);

		model.setLastUpdated(new Date());
		return model;
	}

	private void parseResults(Response response, ResultModel model) throws IOException {

		Document doc = response.parse();
		Elements stopTables = doc.select(MAIN_SELECTOR);

		// Expecting two divs with class 'stop-table'
		if (stopTables != null && stopTables.size() == 2) {
			extractTimesFromTable(stopTables.get(0), model.getInbound());
			extractTimesFromTable(stopTables.get(1), model.getOutbound());
		}
	}

	private void extractTimesFromTable(Element table, List<StopTime> stops) {
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

							stops.add(stopTime);
						}
					}
				}
			}
		}
	}
}
