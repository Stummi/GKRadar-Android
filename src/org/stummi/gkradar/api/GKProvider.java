package org.stummi.gkradar.api;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.stummi.gkradar.R;

import android.content.Context;
import android.util.Log;

public class GKProvider {
	private static final String TAG = "GKProvider";
	private String endPoint;
	private String apiKey;
	private int apiId;
	private RestTemplate restTemplate;
	private int requestCount;

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
			Location[] ret = restTemplate.getForObject(url, Location[].class,
					params);
			callback.newLocations(ret);
			} finally {
				requestCount--;
			}
		}
	}

	public GKProvider(Context c) {
		this.endPoint = c.getResources().getString(R.string.gkr_api_endpoint);
		this.apiId = c.getResources().getInteger(R.integer.gkr_api_id);
		this.apiKey = c.getResources().getString(R.string.gkr_api_pass);

		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
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
