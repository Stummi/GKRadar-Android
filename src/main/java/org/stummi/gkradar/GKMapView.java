package org.stummi.gkradar;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MyLocationOverlay;
import com.readystatesoftware.maps.OnSingleTapListener;
import com.readystatesoftware.maps.TapControlledMapView;

public class GKMapView extends TapControlledMapView {
	private static final String TAG = "GKMapView";
	private int currentZoomLevel = -1;
	private GeoPoint currentCenter;
	private GKOverlay gkoverlay;
	private static final int EQUATOR_METERS = 40075035;
	private static final double METERS_PER_PIXEL_ZOOM_0 = EQUATOR_METERS / 128;
	private MyLocationOverlay myLocationOverlay;

	public GKMapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GKMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GKMapView(Context context, String apiKey) {
		super(context, apiKey);
		init();
	}

	private void init() {
		this.gkoverlay = new GKOverlay(getContext(), this);
		getOverlays().add(gkoverlay);
		this.myLocationOverlay = new MyLocationOverlay(getContext(), this);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				updateLocation();
			}
		});
		
		getOverlays().add(myLocationOverlay);
		setBuiltInZoomControls(true);
		
		setOnSingleTapListener(new OnSingleTapListener() {		
			@Override
			public boolean onSingleTap(MotionEvent e) {
				gkoverlay.hideAllBalloons();
				return true;
			}
		});
	}

	private void updateLocation() {
		currentCenter = myLocationOverlay.getMyLocation();
		getController().animateTo(currentCenter);
		getController().setZoom(15);
		updateMap();
	}

	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			GeoPoint centerGeoPoint = this.getMapCenter();
			if (isNewCenter(centerGeoPoint)) {
				updateMap();
			}
		}
		return super.onTouchEvent(ev);
	}

	private boolean isNewCenter(GeoPoint centerGeoPoint) {
		if (currentCenter == null) {
			currentCenter = centerGeoPoint;
			return true;
		}

		if (currentCenter.getLatitudeE6() != centerGeoPoint.getLatitudeE6()
				|| currentCenter.getLongitudeE6() != centerGeoPoint
						.getLongitudeE6()) {
			currentCenter = centerGeoPoint;
			return true;
		}

		return false;
	}

	private void updateMap() {
		Log.d(TAG, "updateMap");
		if (currentCenter == null)
			return;
		int radius = getVisibleRadiusMeters();
		if (radius < 1000000) {
			if (radius > 100000)
				radius = 100000;
			//double latitude = currentCenter.getLatitudeE6() / 1000000D;
			//double longitude = currentCenter.getLongitudeE6() / 1000000D;
			gkoverlay.updateMap(currentCenter, radius);
		}
	}

	// quick'n'hacky
	public int getVisibleRadiusMeters() {
		int latSpan = getLatitudeSpan();
		int lonSpan = getLongitudeSpan();
		int latCenter = currentCenter.getLatitudeE6();
		int lonCenter = currentCenter.getLongitudeE6();
		int latTop = latCenter - latSpan/2; 
		int latBottom = latCenter + latSpan/2;
		int lonLeft = lonCenter - lonSpan/2;
		int lonRight = lonCenter + lonSpan/2;
		
		Location topLeft = createLocation(latTop, lonLeft);
		Location bottomRight = createLocation(latBottom, lonRight);
		return (int)topLeft.distanceTo(bottomRight);
	}

	private Location createLocation(int latitude, int longitude) {
		Location l = new Location("CALCULATED");
		l.setLatitude(latitude/1000000D);
		l.setLongitude(longitude/1000000D);
		return l;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (getZoomLevel() != currentZoomLevel) {
			currentZoomLevel = getZoomLevel();
			updateMap();
		}
	}

	public void onResume() {
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();		
	}
	
	public void onPause() {
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();		
	}
}
