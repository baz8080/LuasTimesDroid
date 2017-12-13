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
		model.setName(stopName);

        Response response = Jsoup
                .connect(url)
                .validateTLSCertificates(false)
                .timeout(15000)
                .execute();

		parseResults(response, model.getOutbound(), model.getInbound());

		model.setLastUpdated(new Date());
		return model;
	}

	private void parseResults(Response response, List<StopTime> outboundTimes, List<StopTime> inboundTimes) throws IOException {

		Document doc = response.parse();
		Elements stopTables = doc.select(MAIN_SELECTOR);

		if (stopTables != null && stopTables.size() == 2) {

			Element outboundTable = stopTables.get(0);

			if (outboundTable != null) {
				Elements rows = outboundTable.getElementsByTag("tr");

				if (rows.size() > 1) {
					for (int i = 1; i < rows.size(); i++) {
						Element row = rows.get(i);

						if (row != null) {
							Elements cells = row.getElementsByTag("td");

							if (cells != null && cells.size() == 2) {
								StopTime stopTime = new StopTime();
								stopTime.setName(cells.get(0).text());
								stopTime.setMinutes(cells.get(1).text());
								inboundTimes.add(stopTime);
							}
						}
					}
				}
			}
		}
	}

}
