package org.stummi.gkradar.api;

import org.stummi.gkradar.R;

public enum State {
	OFFICIAL(R.string.state_official), HUNCH(R.string.state_hunch);
	int stringId;

	private State(int stringId) {
		this.stringId = stringId;
	}
	
	public int stringId() {
		return stringId;
	}
}
