package com.utopian.shopwise;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.utopian.listAdapter.LazyAdapter;
import com.utopian.tools.JSONParser;

public class SearchCategory extends ActionBarActivity{

		AutoCompleteTextView searchtxt;
		JSONObject jsonobject;
		JSONArray jsonarray;
		ListView listview;
		LazyAdapter adapter;
		ProgressDialog mProgressDialog;
		JSONObject json;
		int success;
		String category;
		String search_brand;

		ArrayList<HashMap<String, String>> arraylist;

		private static final int REQUEST_CODE = 1234;
		public static final String ID = "shop_id";
		public static final String MALL ="mall";
		public static final String SHOP_NO = "shop_no"; 
		public static final String SHOP_NAME = "shop_name"; 
		public static final String FLOOR = "floor_no";
		public static final String THUMB_URL = "image";
		public static final String CONTACT = "contact_no";
		public static final String EMAIL = "email"; 
		public static final String OFFERS = "offers";
		public static final String RATING = "rating"; 
		public static final String DESC = "description_shop";
		public static final String BRAND = "brand";
		public static final String FB = "shop_fb";
		public static final String TWITTER = "shop_twitter";
		public static final String GPLUS = "shop_gplus";

		public static String MESSAGE="success";
		private static final String Search_URL = "http://utopiansolutions.co.in/db/category.php";
		private static final String Search_URL_SEARCH = "http://utopiansolutions.co.in/db/shop_info.php";


		JSONParser jsonParser= new JSONParser();
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.search);
			
			this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			ActionBar actionbar=getSupportActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);

			Intent intent = getIntent();
			category = intent.getStringExtra("category");
			

			searchtxt = (AutoCompleteTextView) findViewById(R.id.search_bar);
			
			searchtxt.setHintTextColor(getResources().getColor(R.color.white));
		
			new DownloadJSONCategory().execute();	
			
			searchtxt.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					new DownloadJSON().execute();					
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			ArrayAdapter<String> adaptr = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.brand));

			searchtxt.setAdapter(adaptr);

			/******************google voice **************/

			ImageButton speakButton = (ImageButton)findViewById(R.id.speakButton);
speakButton.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which Brand?");
		startActivityForResult(intent, REQUEST_CODE);
		
	}
});

			PackageManager pm = getPackageManager();
			List<ResolveInfo> activities = pm.queryIntentActivities(
					new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
			if (activities.size() == 0)
			{
				speakButton.setEnabled(false);
				//  speakButton.setText("Recognizer not present");
			}


		}
		/***********google methods************//////////

		public void speakButtonClicked(View v)
		{
			startVoiceRecognitionActivity();
		}
		/**
		 * Fire an intent to start the voice recognition activity.
		 */
		private void startVoiceRecognitionActivity()
		{
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which Brand?");
			startActivityForResult(intent, REQUEST_CODE);
		}
		/**
		 * Handle the results from the voice recognition activity.
		 */
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent map)
		{
			if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
			{
				// Populate the wordsList with the String values the recognition engine thought it heard
				ArrayList<String> matches = map.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				/******
				 * setting the google searched array First value to the AutoCompleteTextView
				 * *****/
				searchtxt.setText(matches.get(0));
			}
			super.onActivityResult(requestCode, resultCode, map);
		}
		// DownloadJSON AsyncTask
		private class DownloadJSONCategory extends AsyncTask<String, String, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				mProgressDialog = new ProgressDialog(SearchCategory.this);
				mProgressDialog.setTitle("Fetching Shops");
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
				
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("category","%"+category+"%"));
					json = jsonParser.makeHttpRequest(Search_URL, "POST",params);

					Log.d("Login attempt", json.toString());

					// json success tag
					success = json.getInt(MESSAGE);
					if (success == 1) {

						Log.d("BRAND EXISTS!", json.toString());

						jsonarray = json.getJSONArray("posts");

						for (int i = 0; i < jsonarray.length(); i++) {
							HashMap<String, String> map = new HashMap<String, String>();
							jsonobject = jsonarray.getJSONObject(i);
							// Retrive JSON Objects
							map.put("shop_id", jsonobject.getString(ID));
							map.put("mall", jsonobject.getString(MALL));
							map.put("shop_no", jsonobject.getString(SHOP_NO));
							map.put("shop_name", jsonobject.getString(SHOP_NAME));
							map.put("floor_no", jsonobject.getString(FLOOR));
							map.put("image", jsonobject.getString(THUMB_URL));
							map.put("contact_no", jsonobject.getString(CONTACT));
							map.put("email", jsonobject.getString(EMAIL));
							map.put("offers", jsonobject.getString(OFFERS));
							map.put("rating", jsonobject.getString(RATING));
							map.put("description_shop", jsonobject.getString(DESC));
							map.put("brand", jsonobject.getString(BRAND));
							map.put("shop_fb", jsonobject.getString(FB));
							map.put("shop_twitter", jsonobject.getString(TWITTER));
							map.put("shop_gplus", jsonobject.getString(GPLUS));
							// Set the JSON Objects into the array
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
					listview = (ListView) findViewById(R.id.list);
					// Pass the results into ListViewAdapter.java
					adapter = new LazyAdapter(SearchCategory.this, arraylist);
					listview.setAdapter(adapter);



					listview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							// getting values from selected ListItem
							String name = ((TextView) view.findViewById(R.id.list_shop_name)).getText().toString();
							String mall = ((TextView) view.findViewById(R.id.list_mall)).getText().toString();
							String floor = ((TextView) view.findViewById(R.id.floor)).getText().toString();
							String shop_no = ((TextView) view.findViewById(R.id.list_shop_no)).getText().toString();
	                        float rating = ((RatingBar) view.findViewById(R.id.ratingBar1)).getRating();

	                        ImageView imageView= ((ImageView)view.findViewById(R.id.list_image));
							imageView.buildDrawingCache();

							Bitmap image= imageView.getDrawingCache();
							Bundle sending_image = new Bundle();
							sending_image.putParcelable(THUMB_URL, image);

							
							/**
							 * 
							 * Hidden
							 * **/
							String shop_id = ((TextView) view.findViewById(R.id.hidden_id)).getText().toString();
						    String contact_no = ((TextView) view.findViewById(R.id.hidden_contact)).getText().toString();
							String email = ((TextView) view.findViewById(R.id.hidden_email)).getText().toString();
							String offers = ((TextView) view.findViewById(R.id.hidden_offers)).getText().toString();
							String brand = ((TextView) view.findViewById(R.id.hidden_brands)).getText().toString();
							String shop_fb = ((TextView) view.findViewById(R.id.hidden_fb)).getText().toString();
							String shop_twitter = ((TextView) view.findViewById(R.id.hidden_twitter)).getText().toString();
							String shop_gplus = ((TextView) view.findViewById(R.id.hidden_gplus)).getText().toString();					
							String description = ((TextView) view.findViewById(R.id.hidden_desc)).getText().toString();
							String url=((TextView) view.findViewById(R.id.hidden_imageurl)).getText().toString();
							
							
													// Starting single contact activity
							Intent in = new Intent(getApplicationContext(),SingleShop.class);
							in.putExtra(SHOP_NAME, name);
							in.putExtra(MALL, mall);
							in.putExtras(sending_image);//image
							in.putExtra(SHOP_NO,shop_no);
							in.putExtra(RATING, rating);
							in.putExtra(FLOOR,floor);
							
							/**hidden*/
							in.putExtra(DESC, description);
							in.putExtra("url", url);
							in.putExtra(ID, shop_id);
							in.putExtra(CONTACT, contact_no);
							in.putExtra(EMAIL, email);
							in.putExtra(OFFERS, offers);
							in.putExtra(BRAND, brand);
							in.putExtra(FB, shop_fb);
							in.putExtra(TWITTER, shop_twitter);
							in.putExtra(GPLUS, shop_gplus);
							
							
							startActivity(in);


						}
					});

					mProgressDialog.dismiss();

				}
				else{
					mProgressDialog.dismiss();
					Toast.makeText(SearchCategory.this, "No Brand",Toast.LENGTH_LONG).show();

				}

			}/*onPost end*/



		}
		
		
		
		private class DownloadJSON extends AsyncTask<String, String, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				mProgressDialog = new ProgressDialog(SearchCategory.this);
				mProgressDialog.setTitle("Fetching Shops");
				mProgressDialog.setMessage("Loading...");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.setCancelable(false);
				mProgressDialog.setCanceledOnTouchOutside(false);
		//		mProgressDialog.show();
			}

			@Override
			protected String doInBackground(String... args) {
				// Create an array
				arraylist = new ArrayList<HashMap<String, String>>();
				search_brand=searchtxt.getText().toString();
				//int success;

				
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("brand",search_brand+"%"));
					json = jsonParser.makeHttpRequest(Search_URL_SEARCH, "POST",params);

					Log.d("Login attempt", json.toString());

					// json success tag
					success = json.getInt(MESSAGE);
					if (success == 1) {

						Log.d("BRAND EXISTS!", json.toString());

						jsonarray = json.getJSONArray("posts");

						for (int i = 0; i < jsonarray.length(); i++) {
							HashMap<String, String> map = new HashMap<String, String>();
							jsonobject = jsonarray.getJSONObject(i);
							// Retrive JSON Objects
							map.put("shop_id", jsonobject.getString(ID));
							map.put("mall", jsonobject.getString(MALL));
							map.put("shop_no", jsonobject.getString(SHOP_NO));
							map.put("shop_name", jsonobject.getString(SHOP_NAME));
							map.put("floor_no", jsonobject.getString(FLOOR));
							map.put("image", jsonobject.getString(THUMB_URL));
							map.put("contact_no", jsonobject.getString(CONTACT));
							map.put("email", jsonobject.getString(EMAIL));
							map.put("offers", jsonobject.getString(OFFERS));
							map.put("rating", jsonobject.getString(RATING));
							map.put("description_shop", jsonobject.getString(DESC));
							map.put("brand", jsonobject.getString(BRAND));
							map.put("shop_fb", jsonobject.getString(FB));
							map.put("shop_twitter", jsonobject.getString(TWITTER));
							map.put("shop_gplus", jsonobject.getString(GPLUS));
							// Set the JSON Objects into the array
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
					listview = (ListView) findViewById(R.id.list);
					// Pass the results into ListViewAdapter.java
					adapter = new LazyAdapter(SearchCategory.this, arraylist);
					listview.setAdapter(adapter);



					listview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							// getting values from selected ListItem
							String name = ((TextView) view.findViewById(R.id.list_shop_name)).getText().toString();
							String mall = ((TextView) view.findViewById(R.id.list_mall)).getText().toString();
							String floor = ((TextView) view.findViewById(R.id.floor)).getText().toString();
							String shop_no = ((TextView) view.findViewById(R.id.list_shop_no)).getText().toString();
	                        float rating = ((RatingBar) view.findViewById(R.id.ratingBar1)).getRating();

							ImageView imageView= ((ImageView)view.findViewById(R.id.list_image));
							imageView.buildDrawingCache();

							Bitmap image= imageView.getDrawingCache();
							Bundle sending_image = new Bundle();
							sending_image.putParcelable(THUMB_URL, image);

							
							/**
							 * 
							 * Hidden
							 * **/
							String shop_id = ((TextView) view.findViewById(R.id.hidden_id)).getText().toString();
						    String contact_no = ((TextView) view.findViewById(R.id.hidden_contact)).getText().toString();
							String email = ((TextView) view.findViewById(R.id.hidden_email)).getText().toString();
							String offers = ((TextView) view.findViewById(R.id.hidden_offers)).getText().toString();
							String brand = ((TextView) view.findViewById(R.id.hidden_brands)).getText().toString();
							String shop_fb = ((TextView) view.findViewById(R.id.hidden_fb)).getText().toString();
							String shop_twitter = ((TextView) view.findViewById(R.id.hidden_twitter)).getText().toString();
							String shop_gplus = ((TextView) view.findViewById(R.id.hidden_gplus)).getText().toString();					
							String description = ((TextView) view.findViewById(R.id.hidden_desc)).getText().toString();

							
													// Starting single contact activity
							Intent in = new Intent(getApplicationContext(),SingleShop.class);
							in.putExtra(SHOP_NAME, name);
							in.putExtra(MALL, mall);
							in.putExtras(sending_image);//image
							in.putExtra(SHOP_NO,shop_no);
							in.putExtra(RATING, rating);
							in.putExtra(FLOOR,floor);
							
							/**hidden*/
							in.putExtra(DESC, description);

							in.putExtra(ID, shop_id);
							in.putExtra(CONTACT, contact_no);
							in.putExtra(EMAIL, email);
							in.putExtra(OFFERS, offers);
							in.putExtra(BRAND, brand);
							in.putExtra(FB, shop_fb);
							in.putExtra(TWITTER, shop_twitter);
							in.putExtra(GPLUS, shop_gplus);
							
							
							startActivity(in);


						}
					});

				mProgressDialog.dismiss();

				}
				else{
				mProgressDialog.dismiss();
				Toast.makeText(SearchCategory.this, "No Brand",Toast.LENGTH_LONG).show();

				}

			}/*onPost end*/



		}

		
}

