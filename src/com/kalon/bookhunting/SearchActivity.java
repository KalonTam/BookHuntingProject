package com.kalon.bookhunting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchActivity extends Activity {
	public static final String TAG = SearchActivity.class.getSimpleName();
	protected JSONObject mBookData;
	private final String KEY_TITLE = "title";
	private final String KEY_PUBDATE = "publishdate";
	private final String KEY_AUTHOR = "author";
	private final String KEY_DCPRICE = "dcstoreprice";
	private final String KEY_AMAPRICE = "amastoreprice";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		fillSpinner();
		GetBooksTask getBooksTask = new GetBooksTask();
		getBooksTask.execute();

	}

	private class GetBooksTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... arg0) {
			JSONObject jsonResponse = null;

			try {
				InputStream in = getAssets().open("Data.txt");
				ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
				int oneByte;
				while ((oneByte = in.read()) != -1) {
					baos.write(oneByte);
				}
				String json = baos.toString();
				jsonResponse = new JSONObject(json);

			} catch (IOException e) {

				logException(e);
			} catch (JSONException e) {

				logException(e);
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			mBookData = result;

			handleBookResponse();
		}

		private void handleBookResponse() {
			if (mBookData == null) {
				updateDisplayForError();
			} else {
				try {
					JSONArray jsonPosts = mBookData.getJSONArray("posts");
					ArrayList<HashMap<String, String>> bookPosts = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < jsonPosts.length(); i++) {
						JSONObject post = jsonPosts.getJSONObject(i);
						String title = post.getString(KEY_TITLE);
						title = title.toString();
						String author = post.getString(KEY_AUTHOR);
						author = author.toString();
						String pubDate = post.getString(KEY_PUBDATE);
						pubDate = pubDate.toString();
						String dcPrice = post.getString(KEY_DCPRICE);
						dcPrice = dcPrice.toString();
						String amaPrice = post.getString(KEY_AMAPRICE);
						amaPrice = amaPrice.toString();

						HashMap<String, String> blogPost = new HashMap<String, String>();
						blogPost.put(KEY_TITLE, title);
						blogPost.put(KEY_AUTHOR, author);
						blogPost.put(KEY_PUBDATE, pubDate);
						blogPost.put(KEY_DCPRICE, dcPrice);
						blogPost.put(KEY_AMAPRICE, amaPrice);

						HashMap<String, String> book = new HashMap<String, String>();
						book.put(KEY_TITLE, title);
						book.put(KEY_AUTHOR, author);

						bookPosts.add(book);

					}

				} catch (JSONException e) {
					Log.e(TAG, "Exception caught!");
				}
			}
		}

	}

	private void fillSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_items,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void clickBack(View view) {
		Intent i = new Intent(this, MainActivity.class);
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

}
