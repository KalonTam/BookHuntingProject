package com.kalon.bookhunting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * This represents the homescreen
 * @author kalon  
 */

public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void clickSearch(View view) {
    	Intent i = new Intent(this, SearchScreen.class);
     	startActivity(i);
    }
    
    public void clickAbout(View view) {
     	Intent i = new Intent(this, AboutActivity.class);
     	startActivity(i);
    }
    
    public void clickContact(View view) {
     	Intent i = new Intent(this, ContactActivity.class);
     	startActivity(i);
     	
    }
    
    
    
}
