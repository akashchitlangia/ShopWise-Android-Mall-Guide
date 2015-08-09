package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.utopian.adapter.GalleryImagePagerAdapter;
import com.utopian.adapter.ImagePagerAdapter;

import com.utopian.tools.JSONParser;

public class Gallery extends ActionBarActivity {


	private static final String OFFER_URL = "http://utopiansolutions.co.in/tipbox/getimages.php";



	JSONObject jsonobject;
	JSONArray jsonarray;
	ProgressDialog mProgressDialog;
	JSONObject json;
	int success;
	JSONParser jsonParser= new JSONParser();
	/*
	 * for offers
	 * **/
	private AutoScrollViewPager viewPager;
	private TextView            indexText,Title;
	private List<Integer>       imageIdList;
	String articleId;
	private ArrayList<HashMap<String, String>> imageUrlList;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.gallery);
		articleId = "1";
		viewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);

		//View rootView = inflater.inflate(R.layout.fragment_community, container, false);
	/*	LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
		View popupView = layoutInflater.inflate(R.layout.gallery, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

		alertDialogBuilder.setView(popupView);


		//Intent i  = getActivity().getIntent();
		articleId = "1";


		viewPager = (AutoScrollViewPager)popupView.findViewById(R.id.view_pager);

		alertDialogBuilder
		.setCancelable(true);
		

// create an alert dialog
AlertDialog alertD = alertDialogBuilder.create();

alertD.show();
*/
		
		
		new OfferJSON().execute();	
		//return rootView;
	}




	private class OfferJSON extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			// Create an array
			imageUrlList = new ArrayList<HashMap<String, String>>();
			try {

				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("article_id",articleId));
				//json = jsonParser.getJSONFromUrl(OFFER_URL);
				json=jsonParser.makeHttpRequest(OFFER_URL, "POST", params);
				Log.d("Login attempt", json.toString());	
				success = json.getInt("success");
				if (success == 1) {

					jsonarray = json.getJSONArray("posts");

					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						map.put("article_id", jsonobject.getString("article_id"));
						map.put("image_url", jsonobject.getString("image_url"));
						map.put("image_height", jsonobject.getString("image_height"));
						map.put("image_width", jsonobject.getString("Image_width"));

						imageUrlList.add(map);
					}
				}

				else {
					Log.d("Login Failure!", json.getString("message"));
					return json.getString("message");
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

				//  indexText = (TextView)findViewById(R.id.view_pager_index);


				//	initImageUrlList();


				viewPager.setAdapter(new GalleryImagePagerAdapter(getApplicationContext(), imageUrlList));
				viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
				//   indexText.setText(new StringBuilder().append("1/").append(imageIdList.size()));

				viewPager.setInterval(2500);
				viewPager.startAutoScroll();


				// the more properties whose you can set
				// // set whether stop auto scroll when touching, default is true
				//   viewPager.setStopScrollWhenTouch(false);
				// // set whether automatic cycle when auto scroll reaching the last or first item
				// // default is true
				//    viewPager.setCycle(false);
				// /** set auto scroll direction, default is AutoScrollViewPager#RIGHT **/
				//  viewPager.setDirection(AutoScrollViewPager.LEFT);
				// // set how to process when sliding at the last or first item
				// // default is AutoScrollViewPager#SLIDE_BORDER_NONE
				//  viewPager.setBorderProcessWhenSlide(AutoScrollViewPager.SLIDE_BORDER_CYCLE);
				viewPager.setAutoScrollDurationFactor(2);
				//   viewPager.setBorderAnimation(false);



			}
			else{
				//mProgressDialog.dismiss();

				//	Toast.makeText(Gallery.this, "No Such Brand!!",Toast.LENGTH_LONG).show();

			}

		}/*onPost end*/



	}



	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			//   indexText.setText(new StringBuilder().append(position + 1).append("/").append(imageIdList.size()));
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}








}


