package com.kalon.bookhunting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResultsScreen extends ListActivity {
	private static final String TAG = ResultsScreen.class.getSimpleName();
	private JSONObject mBookData;
	private final String KEY_ID = "id";
	private final String KEY_TITLE = "title";
	private final String KEY_PUBDATE = "publishdate";
	private final String KEY_AUTHOR = "author";
	private final String KEY_DCPRICE = "dcstoreprice";
	private final String KEY_AMAPRICE = "amastoreprice";
	private final String KEY_SEARCHTERM = "searchterm";
	private String searchTerm = null;
	private ArrayList<HashMap<String, String>> bookPosts = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_screen);
		Intent intent = getIntent();
		searchTerm = intent.getExtras().getString(KEY_SEARCHTERM).toUpperCase()
				.toString();
		GetBooksTask getBooksTask = new GetBooksTask();
		getBooksTask.execute();

	}

	private class GetBooksTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... arg0) {
			JSONObject jsonResponse = null;

			try {
				ByteArrayOutputStream baos = writeTextFileToOutputStream();
				String json = baos.toString();
				jsonResponse = new JSONObject(json);

			} catch (IOException e) {

				logException(e);
			} catch (JSONException e) {

				logException(e);
			}

			return jsonResponse;
		}

		private ByteArrayOutputStream writeTextFileToOutputStream()
				throws IOException {
			InputStream in = getInputStream();
			ByteArrayOutputStream baos = writeToOutputStream(in);
			return baos;
		}

		private ByteArrayOutputStream writeToOutputStream(InputStream in)
				throws IOException {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
			int oneByte;
			boolean notAtEndOfStream = (oneByte = in.read()) != -1;

			while (notAtEndOfStream) {
				outputStream.write(oneByte);
				notAtEndOfStream = (oneByte = in.read()) != -1;
			}

			return outputStream;
		}

		private InputStream getInputStream() throws IOException {
			InputStream in = getAssets().open("Data.txt");
			return in;
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
					String[] titles = new String[jsonPosts.length()];

					for (int i = 0; i < jsonPosts.length(); i++) {
						HashMap<String, String> bookPost = getData(jsonPosts,
								titles, i);

						if (bookPost.toString().toUpperCase()
								.contains(searchTerm)) {
							Log.d(TAG, bookPost.toString());
							bookPosts.add(bookPost);

						}

						createList(bookPosts);
					}

				} catch (JSONException e) {
					Log.e(TAG, "Exception caught!");
				}
			}
		}

		private HashMap<String, String> getData(JSONArray jsonPosts,
				String[] titles, int i) throws JSONException {
			JSONObject post = jsonPosts.getJSONObject(i);
			String id = post.getString(KEY_ID);
			id = id.toString();
			String title = post.getString(KEY_TITLE);
			title = title.toString();
			titles[i] = title;
			String author = post.getString(KEY_AUTHOR);
			author = author.toString();
			String pubDate = post.getString(KEY_PUBDATE);
			pubDate = pubDate.toString();
			String dcPrice = post.getString(KEY_DCPRICE);
			dcPrice = dcPrice.toString();
			String amaPrice = post.getString(KEY_AMAPRICE);
			amaPrice = amaPrice.toString();

			HashMap<String, String> bookPost = new HashMap<String, String>();
			bookPost.put(KEY_ID, id);
			bookPost.put(KEY_TITLE, title);
			bookPost.put(KEY_AUTHOR, author);
			bookPost.put(KEY_PUBDATE, pubDate);
			bookPost.put(KEY_DCPRICE, dcPrice);
			bookPost.put(KEY_AMAPRICE, amaPrice);
			return bookPost;
		}

		private void createList(ArrayList<HashMap<String, String>> bookPosts) {
			String[] keys = { KEY_TITLE, KEY_AUTHOR };
			int[] ids = { android.R.id.text1, android.R.id.text2 };

			ListAdapter listItemAdapter = new SimpleAdapter(ResultsScreen.this,
					bookPosts, android.R.layout.simple_list_item_2, keys, ids);

			setListAdapter(listItemAdapter);

		}

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, ResultsDetail.class);
		String title=bookPosts.get(position).get(KEY_TITLE);
		String author=bookPosts.get(position).get(KEY_AUTHOR);
		String pubDate=bookPosts.get(position).get(KEY_PUBDATE);
		String dcPrice=bookPosts.get(position).get(KEY_DCPRICE);
		String amaPrice =bookPosts.get(position).get(KEY_AMAPRICE);
		
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_AUTHOR, author);
		intent.putExtra(KEY_PUBDATE, pubDate);
		intent.putExtra(KEY_DCPRICE, dcPrice);
		intent.putExtra(KEY_AMAPRICE, amaPrice);
		
		startActivity(intent);
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
