package org.stummi.gkradar.api;

import java.util.Map;

public class GKApiRequestData {
	private final String path;
	private final Map<String, String> params;

	public GKApiRequestData(String path, Map<String, String> params) {
		super();
		this.path = path;
		this.params = params;
	}
	
	public String getPath() {
		return path;
	}

	public Map<String, String> getParams() {
		return params;
	}
}
