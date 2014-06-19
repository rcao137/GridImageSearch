package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	private final int REQUEST_CODE = 20;
	private EditText edQuery;
	private GridView gvImageResult;
	private ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	private ImageResultArrayAdapter imageArrayAdapter;
	private String searchQuery;
	private Filter filter;
	private int startNumber= 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);

		setViews();

		imageArrayAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvImageResult.setAdapter(imageArrayAdapter);

		gvImageResult.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long id) {
				Intent i = new Intent(getApplicationContext(), ImageDetailActivity.class);
				i.putExtra("result", imageResults.get(position));
				startActivity(i);
			}
		});

		gvImageResult.setOnScrollListener(new EndlessScrollListener() {
			public void onLoadMore(int page, int totalItemsCount) {
				startNumber = totalItemsCount;
				customLoadMoreDataFromApi(page); 
			}
			private void customLoadMoreDataFromApi(int offset) {
				runQuery();
			}
		});

	}

	private void setViews() {
		edQuery = (EditText) findViewById(R.id.etQuery);
		gvImageResult = (GridView) findViewById(R.id.gvImageResult);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

	public void onSetfilterAction(MenuItem mi) {
		//		Toast.makeText(getApplicationContext(), "action menu clicked", Toast.LENGTH_SHORT).show();
		// set the default filter
		if (filter == null)
			filter = new Filter("medium", "blue", "car", null);
		Intent i = new Intent(this, ImageFilterActivity.class);
		i.putExtra("filter", filter);
		startActivityForResult(i, REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name and position values from result extras
			filter = (Filter) data.getSerializableExtra("filter");
			startNumber = 0;
			imageResults.clear();
			imageArrayAdapter.clear();
			runQuery();
		}
	}	

	public void submitQuery(View v)
	{ 
			startNumber = 0;
			imageResults.clear();
			imageArrayAdapter.clear();
			runQuery();
	}

	public void runQuery()
	{
		String completeFilter;
		String extraFilter;

		searchQuery = edQuery.getText().toString();

		if (filter!= null) {
			String extraSizeFilter = "&imgsz="+filter.getSize();
			String extraColorFilter = "&imgcolor="+filter.getColor();
			String extraSiteFilter = "&as_sitesearch"+filter.getSite();
			String extraTypeFilter = "&imgtype="+filter.getType();
			extraFilter = extraSizeFilter + extraColorFilter + extraSiteFilter + extraTypeFilter;

			completeFilter  ="https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start="+ startNumber +"&v=1.0"+extraFilter;
		}
		else
			completeFilter = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start="+ startNumber +"&v=1.0";

		if (searchQuery!=null && searchQuery.length()!=0)
			completeFilter = completeFilter + "&q="+Uri.encode(searchQuery);
		else
			completeFilter = completeFilter + "&q=fuzzy";

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(completeFilter, 
				new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject response) {
				JSONArray imageJSONResults = null;
				try {
					imageJSONResults = response.getJSONObject("responseData").getJSONArray("results");
					imageArrayAdapter.addAll(ImageResult.convertfromJSONArray(imageJSONResults));
				} catch (JSONException e){
					e.printStackTrace();
				}
			}
		});
	}	

	private Boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

}

