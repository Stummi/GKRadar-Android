package org.stummi.gkradar.api;

import com.google.gson.annotations.Expose;

public class GKLocation {
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
	String date;
	
	@Expose
	String source;
	
	@Expose
	boolean notification_push;
	
	@Expose
	boolean notification_email;
	
	@Expose
	boolean notification_sms;
	
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

	public String getDate() {
		return date;
	}

	public String getSource() {
		return source;
	}

	public boolean isNotification_push() {
		return notification_push;
	}

	public boolean isNotification_email() {
		return notification_email;
	}

	public boolean isNotification_sms() {
		return notification_sms;
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
		if(o == null)
			return false;
		
		if(!(o instanceof GKLocation))
			return false;
		return ((GKLocation)o).id.equals(id);
	}
	
	
}