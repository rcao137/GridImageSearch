package com.codepath.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {
	
	public ImageResultArrayAdapter(Context context, List<ImageResult> image) {
		super(context, R.layout.image_list_view, image);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
    SmartImageView ivImage;

		ImageResult imageInfo = this.getItem(position);
    if (convertView == null) {
    	LayoutInflater inflator = LayoutInflater.from(getContext());
    	ivImage = (SmartImageView) inflator.inflate(R.layout.image_list_view, parent, false);
    } else{
	    ivImage = (SmartImageView) convertView;
	    ivImage.setImageResource(android.R.color.transparent);
    }
    ivImage.setImageUrl(imageInfo.getThumbUrl());
    return ivImage;
	}

	
}
