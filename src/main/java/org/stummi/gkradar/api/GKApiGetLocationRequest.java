package org.stummi.gkradar.api;

import java.util.HashMap;

import com.google.android.maps.GeoPoint;

public class GKApiGetLocationRequest {
	private static final int MAX_LIMIT = 75;
	
	private int radius;
	private int limit;
	private Double latitude;
	private Double longitude;
	private GKLocale localeFilter;
	private State stateFilter;
	private Category categoryFilter;
	private int since;
	private OrderBy orderBy;
	private Order order;

	public GKApiGetLocationRequest() {
		limit = MAX_LIMIT;
	}

	public GKApiGetLocationRequest radius(int radius) {
		this.radius = radius;
		return this;
	}
	
	public GKApiGetLocationRequest limit(int limit) {
		this.limit = limit;
		return this;
	}

	public GKApiGetLocationRequest location(GeoPoint location) {
		this.latitude = location.getLatitudeE6()/1000000D;
		this.longitude = location.getLongitudeE6()/1000000D;
		return this;
	}
	
	public GKApiGetLocationRequest location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		return this;
	}

	public GKApiGetLocationRequest localeFilter(GKLocale locale) {
		this.localeFilter = locale;
		return this;
	}

	public GKApiGetLocationRequest stateFilter(State state) {
		this.stateFilter = state;
		return this;
	}

	public GKApiGetLocationRequest categoryFilter(Category category) {
		this.categoryFilter = category;
		return this;
	}

	public GKApiGetLocationRequest since(int since) {
		this.since = since;
		return this;
	}

	public GKApiGetLocationRequest order(OrderBy orderBy, Order order) {
		this.order = order;
		this.orderBy = orderBy;
		return this;
	}

	public GKApiRequestData getRequestData() {
		StringBuilder path = new StringBuilder(
				"locations/?appID={APPLICATION_ID}&secret={APPLICATION_SECRET}");
		HashMap<String, String> params = new HashMap<String, String>();
		path.append("&limit={LIMIT}");
		params.put("LIMIT", String.valueOf(limit));

		if(latitude != null) {
			path.append("&geocode={GEOCODE}");
			params.put("GEOCODE", getGeoCode());
		}
		
		if(localeFilter != null) {
			path.append("&locale={LOCALE}");
			params.put("LOCALE", localeFilter.toString());
		}
		
		if(stateFilter != null) {
			path.append("&status={STATE}");
			params.put("STATE", stateFilter.toString());
		}
		
		if(categoryFilter != null) {
			path.append("&category={CATEGORY}");
			params.put("CATEGORY", categoryFilter.toString());
		}
		
		if(since > 0) {
			path.append("&since={SINCE}");
			params.put("SINCE", String.valueOf(since));
		}
		
		if(orderBy != null) {
			path.append("&orderby={ORDERBY}&order={ORDER}");
			params.put("ORDERBY", orderBy.getApiName());
			params.put("ORDER", order.toString());
		}
		
		return new GKApiRequestData(path.toString(), params);
	}
	
	private String getGeoCode() {
		String geocode = latitude + "," + longitude + "," + (int)Math.ceil(radius / 1000D);
		return geocode;
	}
}

