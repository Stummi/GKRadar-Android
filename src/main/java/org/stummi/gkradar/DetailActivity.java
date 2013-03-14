package org.stummi.gkradar;

import org.stummi.gkradar.api.GKLocation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {

	private GKLocation currentGK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		currentGK = (GKLocation)getIntent().getExtras().getSerializable("gklocation");
		if(currentGK != null) {
			setLocation(currentGK);
		}	
	}

	private void setLocation(GKLocation gkl) {
		setTitle(gkl.getTitle());
		setTextView(R.id.detail_description, gkl.getDescription());
		setTextView(R.id.detail_date, gkl.getCreated());
		setDetail(R.id.detail_source, gkl.getSource(), R.id.detail_source_lbl);
		setTextView(R.id.detail_location, gkl.getAddress());
		String stateString = getResources().getString(gkl.getStatus().stringId());
		setTextView(R.id.detail_state, stateString);
	}

	private void setDetail(int textView, String value, int parentTextView) {
		if(value == null || value.length() == 0) {
			findViewById(parentTextView).setVisibility(View.GONE);
			findViewById(textView).setVisibility(View.GONE);
		} else {
			findViewById(parentTextView).setVisibility(View.VISIBLE);
			findViewById(textView).setVisibility(View.VISIBLE);
			setTextView(textView, value);
		}
	}

	private void setTextView(int textView, String value) {
		TextView tv = (TextView)findViewById(textView);
		tv.setText(value);
	}
	
	public void onSourceClick(View view) {
		Toast.makeText(this, "source", Toast.LENGTH_LONG).show();
	}
}
