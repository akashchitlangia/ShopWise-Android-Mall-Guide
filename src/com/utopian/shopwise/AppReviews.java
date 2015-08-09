package com.utopian.shopwise;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.utopian.listAdapter.CommentsLazyAdapter;
import com.utopian.tools.JSONParser;





public class AppReviews extends ActionBarActivity{

	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	CommentsLazyAdapter adapter;
	ProgressDialog mProgressDialog;
	JSONObject json;
	int success;

	public static final String TITLE = "title";
	public static final String RATING = "rating";
	public static final String POSTS = "posts";
	public static final String POST_ID = "post_id";
	public static final String USERNAME = "username";
	public static final String REVIEW = "message";

	


	ArrayList<HashMap<String, String>> arraylist;


	public static String MESSAGE="success";
	private static final String GET_REVIEWS_URL = "http://www.utopiansolutions.co.in/db/comments.php";

	JSONParser jsonParser= new JSONParser();

	// static  String IMAGE = "image";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.list_reviews);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		new DownloadJSONReviews().execute();	
		
		
		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		// Execute DownloadJSON AsyncTask
		/*ImageButton search=(ImageButton) findViewById(R.id.bt_search);
		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DownloadJSON().execute();	
			}
		});*/


		Button btn_post_review = (Button)findViewById(R.id.btn_post_review);
		btn_post_review.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(AppReviews.this, Reviews.class);
				startActivity(i);
			    

				// setup a dialog window
				

			    
			    
			}	
			
		});
		
	}
	
	
	
	


	// DownloadJSON AsyncTask
	private class DownloadJSONReviews extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(AppReviews.this);
			mProgressDialog.setTitle("Fetching Reviews");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();

			//int success;



			try {


				json = jsonParser.getJSONFromUrl(GET_REVIEWS_URL);

				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(MESSAGE);
				if (success == 1) {

					//Log.d("BRAND EXISTS!", json.toString());

					jsonarray = json.getJSONArray("posts");

					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects
						


						map.put("username", jsonobject.getString(USERNAME));
						map.put("title", jsonobject.getString(TITLE));
						map.put("message", jsonobject.getString(REVIEW));
     					map.put("rating", jsonobject.getString(RATING));
						
						arraylist.add(map);
					}
				}

				else {
					Log.d("Login Failure!", json.getString(MESSAGE));
					return json.getString(MESSAGE);
				}


			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String args) {

			if(success == 1){
				listview = (ListView) findViewById(R.id.list_r);
				// Pass the results into ListViewAdapter.java
				adapter = new CommentsLazyAdapter(AppReviews.this, arraylist);
				listview.setAdapter(adapter);

				mProgressDialog.dismiss();

			}
			else{
				mProgressDialog.dismiss();
				Toast.makeText(AppReviews.this, "No Reviews",Toast.LENGTH_LONG).show();

			}

		}/*onPost end*/



	}
}

