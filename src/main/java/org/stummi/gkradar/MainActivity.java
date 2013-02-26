package org.stummi.gkradar;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class MainActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onResume() {
		getMapView().onResume();
		super.onResume();
	}
	
	private GKMapView getMapView() {
		return (GKMapView) findViewById(R.id.mapview);
	}

	@Override
	protected void onPause() {
		getMapView().onPause();
		super.onPause();
	}

}
