package org.stummi.gkradar.api;

public enum OrderBy {
	CREATED("created"), CITY("city"), ZIPCODE("zipcode"), DISTANCE("distance");
	
	private String apiName;

	OrderBy(String apiName) {
		this.apiName = apiName;
	}
	
	public String getApiName() {
		return apiName;
	}
}
