package com.codepath.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fullUrl;
	private String thumbUrl;

	public ImageResult(JSONObject json) {
		try{
			fullUrl = json.getString("url");
			thumbUrl = json.getString("tbUrl");
		} catch (JSONException e)
		{
			fullUrl = null;
			thumbUrl = null;
		}
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public String getThumbUrl(){
		return thumbUrl;
	}

	public String toString() {
		return thumbUrl;
	}

	public static ArrayList<ImageResult> convertfromJSONArray(
			JSONArray imageJSONResults) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		int len = imageJSONResults.length();
		for (int i=0; i<len; i++)
			try{
				results.add(new ImageResult(imageJSONResults.getJSONObject(i)));
			}catch (JSONException e){
				e.printStackTrace();
			}
		return results;
	}
}
