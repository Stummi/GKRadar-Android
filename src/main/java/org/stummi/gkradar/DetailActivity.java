package org.stummi.gkradar;

import org.stummi.gkradar.api.GKLocation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		GKLocation gkl = (GKLocation)getIntent().getExtras().getSerializable("gklocation");
		if(gkl != null) {
			setLocation(gkl);
		}	
	}

	private void setLocation(GKLocation gkl) {
		setTitle(gkl.getTitle());
		setTextView(R.id.detail_description, gkl.getDescription());
		setTextView(R.id.detail_date, gkl.getCreated());
	}

	private void setTextView(int textView, String value) {
		TextView tv = (TextView)findViewById(textView);
		tv.setText(value);
	}
}
