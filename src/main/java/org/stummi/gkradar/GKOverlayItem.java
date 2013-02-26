package org.stummi.gkradar;

import org.stummi.gkradar.api.GKLocation;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class GKOverlayItem extends OverlayItem {

	private GKLocation location;

	public GKOverlayItem(GKLocation gkl) {
		super(pointFromLocation(gkl), titleFromLocation(gkl),
				snippetFromLocation(gkl));
		this.location = gkl;
	}

	private static String snippetFromLocation(GKLocation l) {
		StringBuilder sb = new StringBuilder();
		String street = l.getStreet();
		if (street != null && street.length() > 0) {
			sb.append(street + "\n");
		}
		sb.append(l.getZipcode());
		sb.append(" ");
		sb.append(l.getCity());
		return sb.toString();
	}

	private static String titleFromLocation(GKLocation l) {
		return l.getTitle();
	}

	private static GeoPoint pointFromLocation(GKLocation l) {
		int latE6 = (int)(l.getLatitude() * 1000000);
		int lonE6 = (int)(l.getLongitude() * 1000000);
		return new GeoPoint(latE6, lonE6);
	}

	public GKLocation getLocation() {
		return location;
	}
}
