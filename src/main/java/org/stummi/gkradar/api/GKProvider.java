package org.stummi.gkradar.api;

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
		private Object[] params;
		private GKLocationsCallback callback;

		public RequestWorker(String request, GKLocationsCallback callback,
				Object... params) {
			this.url = String.format("%s/%s", endPoint, request);
			this.params = params;
			this.callback = callback;
		}

		@Override
		public void run() {
			requestCount++;
			try {
			String retString = restTemplate.getForObject(url, String.class,
					params);
			JsonElement jelement = parser.parse(retString);
			if(jelement.isJsonArray()) {
				GKLocation[] locations = gson.fromJson(jelement, GKLocation[].class);
				callback.newLocations(locations);
			} else {
				Log.d(TAG, "got no array");
			}
				
			}finally {
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
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	}

	public void requestOverview(GKLocationsCallback cb) {
		String request = "locations/?appID={APPLICATION_ID}&secret={APPLICATION_SECRET}&limit=75";
		submitRequest(request, cb, apiId, apiKey);
	}

	private void submitRequest(String request, GKLocationsCallback cb,
			Object... args) {
		new RequestWorker(request, cb, args).start();
		Log.d(TAG, "requestcount: " + requestCount);
	}

	public void requestLocationData(GKLocationsCallback cb, double latitude,
			double longitude, int radius) {
		String geocode = latitude + "," + longitude + "," + radius / 1000;
		String request = "locations/?appID={APPLICATION_ID}&secret={APPLICATION_SECRET}&geocode={GEOCODE}&limit=75";
		submitRequest(request, cb, apiId, apiKey, geocode);
	}
}
