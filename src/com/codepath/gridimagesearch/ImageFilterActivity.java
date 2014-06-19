package com.codepath.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class ImageFilterActivity extends Activity {

	Spinner spSize;
	Spinner spColor;
	Spinner spType;
	EditText etSite;
	Filter filter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_filter);
		
		filter = (Filter) getIntent().getSerializableExtra("filter");
	
		spSize = (Spinner) findViewById(R.id.spSize);
		spColor = (Spinner) findViewById(R.id.spColor);
		spType = (Spinner) findViewById(R.id.spType);
		etSite = (EditText) findViewById(R.id.etSite);
		
		setSpinnerToValue(spSize, filter.getSize());
		setSpinnerToValue(spColor, filter.getColor());
		setSpinnerToValue(spType, filter.getType());
		etSite.setText(filter.getSite());
	}

	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}

	public void onSave(View v) {
		String size = spSize.getSelectedItem().toString();
		String color = spColor.getSelectedItem().toString();
		String itype = spType.getSelectedItem().toString();
		String site = etSite.getText().toString();
		
		filter.setSize(size);
		filter.setColor(color);
		filter.setType(itype);
		filter.setSite(site);
		
		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("filter", filter);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}

}
