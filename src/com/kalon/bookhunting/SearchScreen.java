package com.kalon.bookhunting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Screen to search for books
 * 
 * @author kalon
 * 
 */

public class SearchScreen extends Activity {
	private static final String TAG = SearchScreen.class.getSimpleName();
private final String KEY_SEARCHTERM = "searchterm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		this.setTitle("Search For Books");
		fillGenreSpinner();

	}

	private void fillGenreSpinner() {
		Spinner genre = (Spinner) findViewById(R.id.genreSpinner);

		ArrayAdapter<CharSequence> genreAdapter = createAdapterForGenre();
		genre.setAdapter(genreAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	private ArrayAdapter<CharSequence> createAdapterForGenre() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_items,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public void clickBack(View view) {
		Intent i = new Intent(this, HomeScreen.class);
		startActivity(i);
	}

	private void logException(Exception e) {
		Log.e(TAG, "Exception caught!", e);
	}

	private void updateDisplayForError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.error_title));
		builder.setMessage(R.string.error_message);
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();

	}

	public void clickTitle(View view) {

		EditText searchText = (EditText) findViewById(R.id.txtSearchTitle);
		String searchValue = searchText.getText().toString();
	
		try {
			Intent intent = new Intent(this, ResultsScreen.class);
			intent.putExtra(KEY_SEARCHTERM, searchValue);

			startActivity(intent);

		} catch (Exception e) {
			logException(e);
		}
	}


	public void clickGenre(View view) {
		Spinner mySpinner = (Spinner) findViewById(R.id.genreSpinner);
		String searchValue = mySpinner.getSelectedItem().toString();

		try {
			Intent intent = new Intent(this, ResultsScreen.class);
			intent.putExtra(KEY_SEARCHTERM, searchValue);

			startActivity(intent);

		} catch (Exception e) {
			logException(e);
		}

	}

}
