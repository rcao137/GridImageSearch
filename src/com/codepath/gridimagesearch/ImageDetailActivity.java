package com.codepath.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);

		ImageResult detail= (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivDetail = (SmartImageView) findViewById(R.id.ivDetail);
//		ivDetail.setImageUrl(detail.getFullUrl());
		Picasso.with(getBaseContext()).load(detail.getFullUrl()).into(ivDetail);
	}
}