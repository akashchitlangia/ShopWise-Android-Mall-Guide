package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.utopian.adapter.ImagePagerAdapter;
import com.utopian.tools.JSONParser;

public abstract class HomeFragment extends Fragment implements OnClickListener {

	private static final int REQUEST_CODE = 1234;

	ArrayList<HashMap<String, String>> arraylist;

	AutoCompleteTextView searchtxt;

	private ProgressDialog pDialog;

	/*
	 * for offers *
	 */
	private ArrayList<HashMap<String, String>> imageUrlList;
	public static final String ID = "shop_id";
	public static final String URL = "image_url";
	public static String MESSAGE = "success";
	private static final String OFFER_URL = "http://www.utopiansolutions.co.in/db/offer_image_url.php";

	JSONObject jsonobject;
	JSONArray jsonarray;

	ProgressDialog mProgressDialog;
	JSONObject json;
	int success;

	JSONParser jsonParser = new JSONParser();

	/*
	 * for offers *
	 */

	// private ArrayList<String> imageUrlList;
	private AutoScrollViewPager viewPager;
	private TextView indexText;

	private List<Integer> imageIdList;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	SharedPreferences shared;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final View rootView = inflater.inflate(R.layout.fragment_home,
				container, false);

		new OfferJSON().execute();

		// this.getActivity().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		ImageButton google = (ImageButton) rootView
				.findViewById(R.id.speakButton);
		google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Search.class);
				startActivity(intent);
			}
		});

		ImageButton mens = (ImageButton) rootView
				.findViewById(R.id.button_mens);
		mens.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent0 = new Intent(getActivity(), SearchCategory.class);
				intent0.putExtra("category", "m");
				startActivity(intent0);// TODO Auto-generated method stub

			}
		});

		ImageButton women = (ImageButton) rootView
				.findViewById(R.id.button_women);
		women.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(getActivity(), SearchCategory.class);
				intent1.putExtra("category", "wo");
				startActivity(intent1);
			}
		});

		ImageButton kids = (ImageButton) rootView
				.findViewById(R.id.button_kids);
		kids.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(getActivity(), SearchCategory.class);
				intent2.putExtra("category", "k");
				startActivity(intent2);
			}
		});

		ImageButton dining = (ImageButton) rootView
				.findViewById(R.id.button_dining);
		dining.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent3 = new Intent(getActivity(), SearchCategory.class);
				intent3.putExtra("category", "r");
				startActivity(intent3);
			}
		});
		ImageButton acce = (ImageButton) rootView
				.findViewById(R.id.button_acce);
		acce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent4 = new Intent(getActivity(), SearchCategory.class);
				intent4.putExtra("category", "a");
				startActivity(intent4);// TODO Auto-generated method stub

			}
		});
		ImageButton footware = (ImageButton) rootView
				.findViewById(R.id.button_footware);
		footware.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent5 = new Intent(getActivity(), SearchCategory.class);
				intent5.putExtra("category", "f");
				startActivity(intent5);// TODO Auto-generated method stub

			}
		});
		ImageButton enter = (ImageButton) rootView
				.findViewById(R.id.button_enter);
		enter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent6 = new Intent(getActivity(), SearchCategory.class);
				intent6.putExtra("category", "en");
				startActivity(intent6);// TODO Auto-generated method stub

			}
		});
		ImageButton elec = (ImageButton) rootView
				.findViewById(R.id.button_elec);
		elec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent7 = new Intent(getActivity(), SearchCategory.class);
				intent7.putExtra("category", "el");
				startActivity(intent7);// TODO Auto-generated method stub

			}
		});
		ImageButton sports = (ImageButton) rootView
				.findViewById(R.id.button_sports);
		sports.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent8 = new Intent(getActivity(), SearchCategory.class);
				intent8.putExtra("category", "sw");
				startActivity(intent8);// TODO Auto-generated method stub

			}
		});
		ImageButton depatment = (ImageButton) rootView
				.findViewById(R.id.button_department);
		depatment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent9 = new Intent(getActivity(), SearchCategory.class);
				intent9.putExtra("category", "d");
				startActivity(intent9);
			}
		});

		ImageButton bar = (ImageButton) rootView.findViewById(R.id.button_bar);
		bar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent10 = new Intent(getActivity(),
						SearchCategory.class);
				intent10.putExtra("category", "bar");
				startActivity(intent10);// TODO Auto-generated method stub

			}
		});

		ImageButton other = (ImageButton) rootView
				.findViewById(R.id.button_other);
		other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent11 = new Intent(getActivity(),
						SearchCategory.class);
				intent11.putExtra("category", "ot");
				startActivity(intent11);// TODO Auto-generated method stub

			}
		});

		ImageButton spa = (ImageButton) rootView.findViewById(R.id.button_spa);
		spa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent12 = new Intent(getActivity(),
						SearchCategory.class);
				intent12.putExtra("category", "spa");
				startActivity(intent12); // TODO Auto-generated method stub

			}
		});
		ImageButton salon = (ImageButton) rootView
				.findViewById(R.id.button_salon);
		salon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent13 = new Intent(getActivity(),
						SearchCategory.class);
				intent13.putExtra("category", "saloon");
				startActivity(intent13);
			}
		});

		ImageButton health = (ImageButton) rootView
				.findViewById(R.id.button_health);
		health.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent14 = new Intent(getActivity(),
						SearchCategory.class);
				intent14.putExtra("category", "h");
				startActivity(intent14);
			}
		});

		ImageButton central = (ImageButton) rootView
				.findViewById(R.id.imageButton55);
		central.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(getActivity(), Mall_Specific.class);
				intent2.putExtra("mall", "Central Naman Mall");
				intent2.putExtra("rating", "4");
				intent2.putExtra("address", "RNT Marg Marg");

				startActivity(intent2);
			}
		});
		ImageButton ti = (ImageButton) rootView
				.findViewById(R.id.imageButton11);
		ti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(getActivity(), Mall_Specific.class);
				intent2.putExtra("mall", "Treasure Island");
				intent2.putExtra("rating", "4.5");
				intent2.putExtra("address", "MG Road");
				startActivity(intent2);
			}
		});
		ImageButton malhar = (ImageButton) rootView
				.findViewById(R.id.imageButton22);
		malhar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(getActivity(), Mall_Specific.class);
				intent2.putExtra("mall", "Malhar Mega Mall");
				intent2.putExtra("rating", "4");
				intent2.putExtra("address", "AB Road");
				startActivity(intent2);
			}
		});
		ImageButton mangal = (ImageButton) rootView
				.findViewById(R.id.imageButton33);
		mangal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(getActivity(), Mall_Specific.class);
				intent2.putExtra("mall", "Mangal City Mall");
				intent2.putExtra("rating", "4");
				intent2.putExtra("address", "Vijay Nagar");
				startActivity(intent2);
			}
		});
		ImageButton c21 = (ImageButton) rootView
				.findViewById(R.id.imageButton44);
		c21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(getActivity(), Mall_Specific.class);
				intent2.putExtra("mall", "Century 21");
				intent2.putExtra("rating", "4.5");
				intent2.putExtra("address", "AB Road");
				startActivity(intent2);
			}
		});

		// TODO Auto-generated method stub

		searchtxt = (AutoCompleteTextView) rootView
				.findViewById(R.id.search_bar);
		// searchtxt.setHintTextColor(getResources().getColor(R.color.black));
		/*
		 * ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getActivity(),
		 * android.R.layout.simple_dropdown_item_1line,
		 * getResources().getStringArray(R.array.brand));
		 * 
		 * searchtxt.setAdapter(adaptr);
		 * 
		 * /** Check Internet status button click event
		 */
		// get Internet status

		cd = new ConnectionDetector(getActivity().getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		// check for Internet status
		if (isInternetPresent) {

			// Auto Search
			searchtxt.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Intent intent = new Intent(getActivity(), Search.class);

					startActivity(intent);
					// TODO Auto-generated method stub
					return false;
				}
			});
			/*
			 * searchtxt.addTextChangedListener(new TextWatcher() {
			 * 
			 * @Override public void onTextChanged(CharSequence arg0, int arg1,
			 * int arg2, int arg3) { Intent intent = new
			 * Intent(getActivity(),Search.class); intent.putExtra("brand",
			 * searchtxt.getText().toString());
			 * 
			 * 
			 * 
			 * startActivity(intent); // finish(); }
			 * 
			 * @Override public void beforeTextChanged(CharSequence arg0, int
			 * arg1, int arg2, int arg3) { // TODO Auto-generated method stub
			 * 
			 * }
			 * 
			 * @Override public void afterTextChanged(Editable arg0) { // TODO
			 * Auto-generated method stub
			 * 
			 * } });
			 */
		}
		//
		else {
			// Internet connection is not present
			// Ask user to connect to Internet
			AlertDialogX.showAlertDialog(getActivity(),
					"No Internet Connection",
					"You don't have internet connection.", false);
		}

		/*viewPager = (AutoScrollViewPager) rootView
				.findViewById(R.id.view_pager);
		// indexText = (TextView)findViewById(R.id.view_pager_index);

		initImageUrlList();

		viewPager
				.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		// indexText.setText(new
		// StringBuilder().append("1/").append(imageIdList.size()));

		viewPager.setInterval(3000);
		viewPager.startAutoScroll();

		// the more properties whose you can set
		// // set whether stop auto scroll when touching, default is true
		// viewPager.setStopScrollWhenTouch(false);
		// // set whether automatic cycle when auto scroll reaching the last or
		// first item
		// // default is true
		// viewPager.setCycle(false);
		// /** set auto scroll direction, default is AutoScrollViewPager#RIGHT
		// **/
		// viewPager.setDirection(AutoScrollViewPager.LEFT);
		// // set how to process when sliding at the last or first item
		// // default is AutoScrollViewPager#SLIDE_BORDER_NONE
		// viewPager.setBorderProcessWhenSlide(AutoScrollViewPager.SLIDE_BORDER_CYCLE);
/*		viewPager.setScrollDurationFactor(2);
		// viewPager.setBorderAnimation(false);
*/
		return rootView;
	}

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public void searchButtonClicked(View v) {
		startSearchActivity();
	}

	private void startSearchActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), Search.class);
		intent.putExtra("brand", searchtxt.getText().toString());

		startActivity(intent);
	}

	public void speakButtonClicked(View v) {
		startVoiceRecognitionActivity();
	}

	/**
	 * Fire an intent to start the voice recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == 1234) {
			// Populate the wordsList with the String values the recognition
			// engine thought it heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			/******
			 * setting the google searched array First value to the
			 * AutoCompleteTextView
			 * *****/

			// ye cooment kiya hai

			// / searchtxt.setText(matches.get(0));
			/******
			 * setting the google searched array to the AutoCompleteTextView
			 * *****/
			// searchtxt.setAdapter(new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1,matches));
			/******
			 * setting the google searched array to the ListView
			 * *****/
			// wordsList.setAdapter(new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1, matches));

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * @Override protected void onPause() { super.onPause(); // stop auto scroll
	 * when onPause viewPager.stopAutoScroll(); }
	 * 
	 * @Override protected void onResume() { super.onResume(); // start auto
	 * scroll when onResume viewPager.startAutoScroll(); }
	 */
	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			// indexText.setText(new StringBuilder().append(position +
			// 1).append("/").append(imageIdList.size()));
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	
	private class OfferJSON extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Wait a moment...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			// Create an array
			imageUrlList = new ArrayList<HashMap<String, String>>();
			try {
			
				json = jsonParser.getJSONFromUrl(OFFER_URL);
				Log.d("Login attempt", json.toString());	
				success = json.getInt(MESSAGE);
				if (success == 1) {

					jsonarray = json.getJSONArray("posts");

					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						map.put("shop_id", jsonobject.getString(ID));
						map.put("image_url", jsonobject.getString(URL));
						imageUrlList.add(map);
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
				
				viewPager = (AutoScrollViewPager)getActivity().findViewById(R.id.view_pager);
				//  indexText = (TextView)findViewById(R.id.view_pager_index);


			//	initImageUrlList();


				viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList));
				viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
				//   indexText.setText(new StringBuilder().append("1/").append(imageIdList.size()));

				viewPager.setInterval(3000);
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
				
				pDialog.dismiss();
		

			}
			else{
				//mProgressDialog.dismiss();
				
				Toast.makeText(getActivity(), "No Such Brand!!",Toast.LENGTH_LONG).show();
				pDialog.dismiss();


			}

		}/*onPost end*/



	}


	/*private void initImageUrlList() {
		imageUrlList = new ArrayList<String>();
		imageUrlList
				.add("http://t0.gstatic.com/images?q=tbn:ANd9GcSNUWgrP_5yPq_47WeXouk7KAozaLDkMEyYucGKrvNJ3NmnPYdkVw");
		imageUrlList
				.add("http://t0.gstatic.com/images?q=tbn:ANd9GcTuV5soxD1gNIFXyPplgC6nRKGfZRqmTVX4uWsBaAoIHC7ni7J_");
		imageUrlList
				.add("http://t3.gstatic.com/images?q=tbn:ANd9GcQpJsp6GdxwviMdtz_seCrK9IMqPfBFu4UaylUTZaE8l6GRs87rKw");
		imageUrlList
				.add("http://www.roastbrief.com.mx/wp-content/uploads/2012/11/levis.png");
	}
*/
}
