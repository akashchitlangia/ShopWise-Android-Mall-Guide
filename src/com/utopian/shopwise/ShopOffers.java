package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.utopian.imageloader.ImageLoader;
import com.utopian.listAdapter.LazyAdapter;
import com.utopian.tools.JSONParser;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopOffers extends ActionBarActivity {
	JSONParser jsonParser = new JSONParser();

	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	LazyAdapter adapter;
	ProgressDialog mProgressDialog;
	JSONObject json;

	int success;

	public static String MESSAGE = "success";
	private static final String Search_URL = "http://utopiansolutions.co.in/db/shop_offer.php";

	ArrayList<HashMap<String, String>> arraylist;
	String SOffer;
	String offers;

	String name;
	String mall;
	String floor;
	String shop_no ;
	Float rating;
	String id;

	String desc;
	String email;
	String contact_no;

	String brand;
	String fb ;
	String twitter ;
	String gplus ;
	String url ;



	public static final String ID = "shop_id";
	public static final String MALL ="mall";
	public static final String SHOP_NO = "shop_no"; // parent node
	public static final String SHOP_NAME = "shop_name"; // parent node
	public static final String FLOOR = "floor_no";
	public static final String THUMB_URL = "image";
	public static final String CONTACT = "contact_no"; // parent node
	public static final String EMAIL = "email"; // parent node
	public static final String OFFERS = "offers";
	public static final String DESC = "description_shop";
	public static final String BRAND = "brand";
	public static final String FB = "shop_fb";
	public static final String TWITTER = "shop_twitter";
	public static final String GPLUS = "shop_gplus";


	public static final String TITLE = "title";
	public static final String RATING = "rating";
	public static final String POSTS = "posts";
	public static final String POST_ID = "post_id";
	public static final String USERNAME = "username";
	public static final String REVIEW = "message";


	public ImageLoader imageLoader; 

	ImageView offer;


	TextView lblName ;
	TextView lblMall ;
	TextView lblDesc ;
	TextView lblFloor ;
	TextView lblShopNo ;
	RatingBar lblRating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.single_offer);

		//   Bundle received_image = getIntent().getExtras();
		Intent i = getIntent();

		imageLoader=new ImageLoader(ShopOffers.this);



		lblName = (TextView) findViewById(R.id.s_name);
		lblMall = (TextView) findViewById(R.id.s_mall);
		lblDesc = (TextView) findViewById(R.id.s_desc);
		lblFloor = (TextView) findViewById(R.id.floor);
		lblShopNo = (TextView) findViewById(R.id.s_shop_no);
		lblRating = (RatingBar) findViewById(R.id.ratingBar1);

		final RelativeLayout rl_offers = (RelativeLayout) findViewById(R.id.box2);
		ImageView im = (ImageView) findViewById(R.id.s_image);
	name = i.getStringExtra(SHOP_NAME);
		mall = i.getStringExtra(MALL);
		floor = i.getStringExtra(FLOOR);
		shop_no = i.getStringExtra(SHOP_NO);
		rating = i.getFloatExtra(RATING, 0);
		id = i.getStringExtra(ID);
		desc = i.getStringExtra(Search.DESC);
		email = i.getStringExtra(EMAIL);
		contact_no = i.getStringExtra(CONTACT);
		brand = i.getStringExtra(BRAND);
		fb = i.getStringExtra(FB);
		offer = (ImageView)findViewById(R.id.imageView1);	
		imageLoader.DisplayImage(i.getStringExtra("url"), im);


		lblName.setText(name);
		lblMall.setText(mall);
		//lblDesc.setText(desc);
		lblFloor.setText(floor);
		lblShopNo.setText(shop_no);
		lblRating.setRating(rating);

		new DownloadOfferStore().execute();
		
		
		
		
		
		
		Button postButton = (Button) findViewById(R.id.btn_post_review);
		postButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent post = new Intent(ShopOffers.this, ShopReviews.class);
				post.putExtra("shopid", id);
				startActivity(post);

			}
		});
		

		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,
				PhoneStateListener.LISTEN_CALL_STATE);

		ImageButton call = (ImageButton) findViewById(R.id.imageButton1);
		call.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (contact_no.equalsIgnoreCase("") || contact_no.length() == 0
						|| contact_no.isEmpty()) {
					Toast toast = Toast.makeText(ShopOffers.this,
							"No Contact details ", Toast.LENGTH_SHORT);
					/* toast */
				} else {
					String phn_no = "tel:" + contact_no;
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse(phn_no));
					startActivity(callIntent);
				}

			}
		});

		ImageButton btnemail = (ImageButton) findViewById(R.id.imageButton2);
		btnemail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShopOffers.this, Email.class);
				i.putExtra("mall_email", email);
				startActivity(i);

			}
		});

		ImageButton fb_click = (ImageButton) findViewById(R.id.imageButton3);
		fb_click.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// String phn_no= "tel:"+contact_no;
				Intent i = new Intent(ShopOffers.this, Facebook.class);
				i.putExtra("link", fb);
				startActivity(i);

			}
		});
		
		
		


		
		
		
		
		

	}

	private class DownloadOfferStore extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			mProgressDialog = new ProgressDialog(ShopOffers.this);

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

			// int success;



			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("shop_id", id));
				json = jsonParser.makeHttpRequest(Search_URL, "POST", params);

				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(MESSAGE);
				if (success == 1) {

					Log.d("BRAND EXISTS!", json.toString());

					jsonarray = json.getJSONArray("posts");

					jsonobject = jsonarray.getJSONObject(0);


					SOffer=jsonobject.getString("image_url");



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

			if (success == 1) {



				imageLoader.DisplayImage(SOffer,offer);

				mProgressDialog.dismiss();



			} else {


				offer.setImageResource(R.drawable.icon_nooffer);

				lblDesc.setVisibility(View.GONE);
				mProgressDialog.dismiss();

			}
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event)

	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(getApplicationContext(),
					com.utopian.shopwise.MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);

		}
		return false;

	};
	
	
	private class PhoneCallListener extends PhoneStateListener {

		private boolean isPhoneCalling = false;

		String LOG_TAG = "LOGGING 123";

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			if (TelephonyManager.CALL_STATE_RINGING == state) {
				// phone ringing
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(LOG_TAG, "OFFHOOK");

				isPhoneCalling = true;
			}

			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// run when class initial and phone call ended,
				// need detect flag from CALL_STATE_OFFHOOK
				Log.i(LOG_TAG, "IDLE");

				if (isPhoneCalling) {

					Log.i(LOG_TAG, "restart app");

					// restart app
					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					isPhoneCalling = false;
				}

			}
		}
	}
}
