package com.kalon.bookhunting;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsDetail extends Activity {
	private final String KEY_TITLE = "title";
	private final String KEY_PUBDATE = "publishdate";
	private final String KEY_AUTHOR = "author";
	private final String KEY_DCPRICE = "dcstoreprice";
	private final String KEY_AMAPRICE = "amastoreprice";
	private final String KEY_THUMBNAIL="thumbnail";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_detail);
		
		Intent intent = getIntent();
	
		String title = intent.getExtras().getString(KEY_TITLE);
		String author= intent.getExtras().getString(KEY_AUTHOR);
		String pubDate=intent.getExtras().getString(KEY_PUBDATE);
		String dcPrice = intent.getExtras().getString(KEY_DCPRICE);
		String amaPrice = intent.getExtras().getString(KEY_AMAPRICE);
		String thumbnail= intent.getExtras().getString(KEY_THUMBNAIL);
		
		TextView titleText = (TextView) findViewById(R.id.txtTitle);
		titleText.setText(title);
		TextView authorText = (TextView) findViewById(R.id.txtAuthor);
		authorText.setText(author);
		TextView pubText = (TextView) findViewById(R.id.txtPubDate);
		pubText.setText(pubDate);
		TextView dcPriceText = (TextView) findViewById(R.id.txtDcPrice);
		dcPriceText.setText(dcPrice);
		TextView amaPriceText = (TextView) findViewById(R.id.txtAmaPrice);
		amaPriceText.setText(amaPrice);
		
		String mDrawableName = thumbnail;
		int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
		
		
		ImageView image  = (ImageView) findViewById(R.id.imageView1);
		Resources res = getResources(); 
		image.setImageDrawable(res.getDrawable(resID));
		
		this.setTitle(title);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results_detail, menu);
		return true;
		
		
		
		
	}

}
