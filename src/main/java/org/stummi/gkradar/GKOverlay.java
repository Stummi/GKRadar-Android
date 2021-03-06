package org.stummi.gkradar;

import java.util.ArrayList;
import java.util.HashMap;

import org.stummi.gkradar.api.GKApiGetLocationRequest;
import org.stummi.gkradar.api.GKLocation;
import org.stummi.gkradar.api.GKLocationsCallback;
import org.stummi.gkradar.api.GKProvider;
import org.stummi.gkradar.api.State;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

/**
 * Map-Overlay which displays the found locations
 * 
 * @author stummi
 */
public class GKOverlay extends BalloonItemizedOverlay<GKOverlayItem> implements
		GKLocationsCallback {
	private static final String TAG = "GKOverlay";
	private GKProvider provider;
	private ArrayList<GKLocation> locations;
	private Context context;

	private HashMap<State, Drawable> drawables;
	private Object populateLock = new Object();

	public GKOverlay(Context c, MapView mapView) {
		super(createDefaultDrawable(c), mapView);
		setBalloonBottomOffset(70);
		setShowDisclosure(true);
		setShowClose(false);
		this.provider = new GKProvider(c);
		this.context = c;
		initIcons();
		initLocations();
	}

	private void initIcons() {
		drawables = new HashMap<State, Drawable>();
		loadDrawable(State.HUNCH, R.drawable.icnpoisonpinhunch);
		loadDrawable(State.OFFICIAL, R.drawable.icnpoisonpinofficial);
		loadDrawable(null, R.drawable.icnpoisonpin);
	}

	private void loadDrawable(State state, int id) {
		Drawable drawable = context.getResources().getDrawable(id);
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		int left = (int) (width * 0.4);
		int right = width - left;
		int top = height - 1;
		int bbottom = 1;
		drawable.setBounds(-left, -top, right, bbottom);
		drawables.put(state, drawable);
	}

	private void initLocations() {
		locations = new ArrayList<GKLocation>();
		populate();
		provider.requestOverview(this);
	}

	private static Drawable createDefaultDrawable(Context c) {
		return boundCenterBottom(c.getResources().getDrawable(
				R.drawable.ic_launcher));
	}

	@Override
	protected GKOverlayItem createItem(int i) {
		GKLocation l = locations.get(i);
		GKOverlayItem gkoi = new GKOverlayItem(l);
		gkoi.setMarker(drawables.get(l.getStatus()));
		return gkoi;
	}

	@Override
	public int size() {
		return locations.size();
	}

	public void updateMap(GeoPoint location, int radius) {
		GKApiGetLocationRequest request = new GKApiGetLocationRequest().location(
				location).radius(radius);
		provider.requestLocationData(request, this);
	}

	@Override
	public void newLocations(GKLocation[] location) {
		synchronized (populateLock) {
			for (GKLocation l : location) {
				if (locations.contains(l)) {
					continue;
				}
				locations.add(l);
			}
			Log.d(TAG, "new Locations: " + location.length);
			Log.d(TAG, "Location Count: " + locations.size());
			populate();
		}
	}

	/**
	 * We have to synchronizes this method with newLocations() to avoid an race
	 * condition which causes the app to crash if size() returns an greater
	 * value than on the last populate()-call
	 */
	@Override
	protected int getIndexToDraw(int drawingOrder) {
		synchronized (populateLock) {
			return super.getIndexToDraw(drawingOrder);
		}
	}

	@Override
	protected boolean onBalloonTap(int index, GKOverlayItem item) {
		Intent i = new Intent(context, DetailActivity.class);
		i.putExtra("gklocation", item.getLocation());
		context.startActivity(i);
		return true;
	}

}
