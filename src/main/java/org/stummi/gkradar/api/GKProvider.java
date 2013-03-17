package org.stummi.gkradar.api;

import java.util.Map;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.stummi.gkradar.R;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GKProvider {
	private static final String TAG = "GKProvider";
	private String endPoint;
	private String apiKey;
	private int apiId;
	private RestTemplate restTemplate;
	private int requestCount;
	private JsonParser parser;
	private Gson gson;

	private class RequestWorker extends Thread {
		String url;
		private Map<String, String> params;
		private GKLocationsCallback callback;

		public RequestWorker(GKApiRequestData request,
				GKLocationsCallback callback) {
			this.url = String.format("%s/%s", endPoint, request.getPath());
			this.params = request.getParams();
			this.params.put("APPLICATION_ID", String.valueOf(apiId));
			this.params.put("APPLICATION_SECRET", apiKey);
			this.callback = callback;
		}

		@Override
		public void run() {
			requestCount++;
			try {
				String retString = restTemplate.getForObject(url, String.class,
						params);
				JsonElement jelement = parser.parse(retString);
				if (jelement.isJsonArray()) {
					GKLocation[] locations = gson.fromJson(jelement,
							GKLocation[].class);
					callback.newLocations(locations);
				} else {
					Log.e(TAG, "got no array");
				}

			} finally {
				requestCount--;
			}
		}
	}

	public GKProvider(Context c) {
		this.endPoint = c.getResources().getString(R.string.gkr_api_endpoint);
		this.apiId = c.getResources().getInteger(R.integer.gkr_api_id);
		this.apiKey = c.getResources().getString(R.string.gkr_api_pass);

		parser = new JsonParser();
		gson = new Gson();
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
	}

	public void requestOverview(GKLocationsCallback cb) {
		requestLocationData(new GKApiGetLocationRequest(), cb);
	}

	public void requestLocationData(GKApiGetLocationRequest request,
			GKLocationsCallback cb) {
		new RequestWorker(request.getRequestData(), cb).start();
	}
}
