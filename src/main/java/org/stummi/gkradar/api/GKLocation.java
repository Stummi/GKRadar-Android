package org.stummi.gkradar.api;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class GKLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose
	String id;

	@Expose
	State status;

	@Expose
	Category category;

	@Expose
	String title;

	@Expose
	String description;

	@Expose
	String country;

	@Expose
	String street;

	@Expose
	String zipcode;

	@Expose
	String city;

	@Expose
	double latitude;

	@Expose
	double longitude;

	@Expose
	String created;

	@Expose
	String source;

	@Expose
	String permalink;

	public String getId() {
		return id;
	}

	public State getStatus() {
		return status;
	}

	public Category getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCountry() {
		return country;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getCreated() {
		return created;
	}

	public String getSource() {
		return source;
	}

	public String getPermalink() {
		return permalink;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (!(o instanceof GKLocation))
			return false;
		return ((GKLocation) o).id.equals(id);
	}

	public String getAddress() {
		StringBuilder sb = new StringBuilder();
		if (street != null && street.length() > 0) {
			sb.append(street + "\n");
		}
		sb.append(zipcode);
		sb.append(" ");
		sb.append(city);
		return sb.toString();
	}

}
