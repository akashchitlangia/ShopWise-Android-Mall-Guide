package com.utopian.shopwise;

import java.io.BufferedReader;
import java.io.File;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utopian.imageloader.ImageLoader;
import com.utopian.listAdapter.CommentsLazyAdapter;
import com.utopian.tools.JSONParser;

public class SingleShop extends ActionBarActivity {

	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	CommentsLazyAdapter adapter;
	ProgressDialog mProgressDialog;
	JSONObject json;
	int success;

	SharedPreferences sp;
	String user;
	String result;
	JSONObject oldJsonObject;
	JSONObject jsonObject;
	JSONObject jsonrecents = null;
	JSONArray oldJson = null;
	JSONArray oldJsonrecents = null;
	JSONArray testOldJsonrecents = null;
	JSONArray jsonArray;
	JSONObject jsonfavorites = null;
	JSONArray oldJsonfavorites = null;
	JSONArray testOldJsonfavourites = null;
	boolean fav = false;

	public static final String ID = "shop_id";
	public static final String MALL = "mall";
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

	ArrayList<HashMap<String, String>> arraylist;

	public static String MESSAGE = "success";
	private static final String GET_REVIEWS_URL = "http://utopiansolutions.co.in/db/comments.php";

	JSONParser jsonParser = new JSONParser();

	int indexRecents;
	Boolean toInsertInRecents = true;
	String offers;

	String name;
	String mall;
	String floor;
	String shop_no;
	Float rating;
	String id;

	/*** hidden ***/
	String desc;
	String email;
	String contact_no;

	String brand;
	String fb;
	String twitter;
	String gplus;
	String url;
	ImageView im;

	public ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_shop);
		
		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final Context c = getApplicationContext();
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		// Get JSON values from previous intent
		Bundle received_image = getIntent().getExtras();
		Intent in = getIntent();
		
		Button whatsnew = (Button)findViewById(R.id.button1);
		whatsnew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			/*	Fragment fragment = new Gallery();
				FragmentManager fragmentManager =((FragmentActivity) c).getSupportFragmentManager();

				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();


*/
				
				
				Toast.makeText(c, "No Current Updates", Toast.LENGTH_SHORT).show();
			/*	Intent i = new Intent(SingleShop.this, Gallery.class);
				startActivity(i);
				*///	FragmentTransaction ft =	((ActionBarActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ft).commit();;

				
			}
		});
		Button map = (Button)findViewById(R.id.button2);
		map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			/*	Fragment fragment = new Gallery();
				FragmentManager fragmentManager =((FragmentActivity) c).getSupportFragmentManager();

				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();


*/
				
				
				Toast.makeText(c, "Correct Address not available", Toast.LENGTH_SHORT).show();
			/*	Intent i = new Intent(SingleShop.this, Gallery.class);
				startActivity(i);
				*///	FragmentTransaction ft =	((ActionBarActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ft).commit();;

				
			}
		});

		name = in.getStringExtra(Search.SHOP_NAME);
		mall = in.getStringExtra(Search.MALL);
		floor = in.getStringExtra(Search.FLOOR);
		shop_no = in.getStringExtra(Search.SHOP_NO);
		rating = in.getFloatExtra(Search.RATING, 0);
		id = in.getStringExtra(Search.ID);

		/*** hidden ***/
		desc = in.getStringExtra(Search.DESC);
		email = in.getStringExtra(Search.EMAIL);
		contact_no = in.getStringExtra(Search.CONTACT);
		offers = in.getStringExtra(Search.OFFERS);
		brand = in.getStringExtra(Search.BRAND);
		fb = in.getStringExtra(Search.FB);
		twitter = in.getStringExtra(Search.TWITTER);
		gplus = in.getStringExtra(Search.GPLUS);
		url = in.getStringExtra("url");

		// Displaying all values on the screen
		TextView lblName = (TextView) findViewById(R.id.s_name);
		TextView lblMall = (TextView) findViewById(R.id.s_mall);
		TextView lblDesc = (TextView) findViewById(R.id.s_desc);
		TextView lblFloor = (TextView) findViewById(R.id.floor);
		TextView lblShopNo = (TextView) findViewById(R.id.s_shop_no);
		RatingBar lblRating = (RatingBar) findViewById(R.id.ratingBar1);
		TextView lblOffer = (TextView) findViewById(R.id.s_offer);

		final RelativeLayout rl_offers = (RelativeLayout) findViewById(R.id.box2);
		ImageView im = (ImageView) findViewById(R.id.s_image);

		lblName.setText(name);
		lblMall.setText(mall);
		lblDesc.setText(desc);
		lblFloor.setText(floor);
		lblShopNo.setText(shop_no);
		lblRating.setRating(rating);
		lblOffer.setText(offers);
		imageLoader = new ImageLoader(SingleShop.this);
		imageLoader.DisplayImage(url, im);
		/* im.setImageResource(received_image); */
		jsonObject = new JSONObject();

		// for Recents
		if (sp.contains("username")) {
			user = sp.getString("username", "");

			try {

				jsonObject.put(Search.SHOP_NAME, name);

				jsonObject.put(Search.MALL, mall);
				jsonObject.put(Search.DESC, desc);
				jsonObject.put(Search.FLOOR, floor);
				jsonObject.put(Search.SHOP_NO, shop_no);
				jsonObject.put(Search.RATING, rating);
				jsonObject.put(Search.ID, id);
				jsonObject.put(Search.OFFERS, offers);
				jsonObject.put(Search.THUMB_URL, url);
				// json.put(Search.THUMB_URL, value);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			// /Recents
			if (!new File(getApplicationContext().getFilesDir(), "recents.json")
					.exists()) {

				String recentfilename = "recents.json";

				String recents = "{" + user + ":[]}";
				try {
					FileOutputStream outputStream = openFileOutput(
							recentfilename, Context.MODE_WORLD_READABLE);
					outputStream.write(recents.getBytes());
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			try {
				File recentsJsonFile = new File(getApplicationContext()
						.getFilesDir(), "recents.json");
				InputStream is = new FileInputStream(recentsJsonFile);

				if (is != null) {
					try {
						InputStreamReader tmp = new InputStreamReader(is);
						BufferedReader reader = new BufferedReader(tmp);
						String str;
						StringBuilder buf = new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str);

						}

						result = buf.toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			}
			try {
				if (!new JSONObject(result).has(user)) {

					String recents = (new JSONObject(result).put(user,
							new JSONArray())).toString();
					String recentfilename = "recents.json";

					try {
						FileOutputStream outputStream = openFileOutput(
								recentfilename, Context.MODE_WORLD_READABLE);
						outputStream.write(recents.getBytes());
						outputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			try {
				File recentsJsonFile = new File(getApplicationContext()
						.getFilesDir(), "recents.json");
				InputStream is = new FileInputStream(recentsJsonFile);

				if (is != null) {
					try {
						InputStreamReader tmp = new InputStreamReader(is);
						BufferedReader reader = new BufferedReader(tmp);
						String str;
						StringBuilder buf = new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str);

						}

						result = buf.toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			}

			oldJsonrecents = null;
			try {
				oldJsonrecents = new JSONObject(result).getJSONArray(user);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jsonArray = new JSONArray();
			JSONObject newJson = jsonObject;
			testOldJsonrecents = new JSONArray();
			for (int i = 0; i < oldJsonrecents.length(); i++) {
				try {
					if (newJson.get(Search.ID).equals(
							oldJsonrecents.getJSONObject(i).get(Search.ID))) {
						toInsertInRecents = false;
						indexRecents = i;
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {

				if (toInsertInRecents == true) {

					testOldJsonrecents.put(newJson);
					for (int i = 0; i < oldJsonrecents.length(); i++) {
						try {

							testOldJsonrecents.put(oldJsonrecents
									.getJSONObject(i));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {

					testOldJsonrecents.put(newJson);
					for (int i = 0; i < oldJsonrecents.length(); i++) {
						try {
							if (i != indexRecents)
								testOldJsonrecents.put(oldJsonrecents
										.getJSONObject(i));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				jsonrecents = new JSONObject(result).put(user,
						testOldJsonrecents);
				// jsonrecents.put(user,testOldJsonrecents);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// getMenuInflater().inflate(R.menu.profile_menu2, null);
			// NAME=(String) ((TextView)findViewById(R.id.dc_name)).getText();
			// File file=new File(getApplicationContext().getFilesDir(),
			// "a.txt");

			String recentsfilename = "recents.json";

			String recent = jsonrecents.toString();

			try {
				FileOutputStream outputStream = openFileOutput(recentsfilename,
						Context.MODE_WORLD_READABLE);
				outputStream.write(recent.getBytes());
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				// //System.out.println(jsonToStringFromAssetFolder(filename,
				// getApplicationContext()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// /

			// Code for favourites Fragment
			if (!new File(getApplicationContext().getFilesDir(),
					"favourites.json").exists()) {

				String filename = "favourites.json";

				String string = "{" + user + ":[]}";
				try {
					FileOutputStream outputStream = openFileOutput(filename,
							Context.MODE_WORLD_READABLE);
					outputStream.write(string.getBytes());
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			try {
				File recentsJsonFile = new File(getApplicationContext()
						.getFilesDir(), "favourites.json");
				InputStream infav = new FileInputStream(recentsJsonFile);

				if (infav != null) {
					try {
						InputStreamReader tmp = new InputStreamReader(infav);
						BufferedReader reader = new BufferedReader(tmp);
						String str;
						StringBuilder buf = new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str);

						}

						result = buf.toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							infav.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			}
			try {
				if (!new JSONObject(result).has(user)) {

					String recents = (new JSONObject(result).put(user,
							new JSONArray())).toString();
					String recentfilename = "favourites.json";

					try {
						FileOutputStream outputStream = openFileOutput(
								recentfilename, Context.MODE_WORLD_READABLE);
						outputStream.write(recents.getBytes());
						outputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			try {
				File recentsJsonFile = new File(getApplicationContext()
						.getFilesDir(), "favourites.json");
				InputStream is = new FileInputStream(recentsJsonFile);

				if (is != null) {
					try {
						InputStreamReader tmp = new InputStreamReader(is);
						BufferedReader reader = new BufferedReader(tmp);
						String str;
						StringBuilder buf = new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str);

						}

						result = buf.toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			}

			oldJsonfavorites = null;
			try {
				oldJsonfavorites = new JSONObject(result).getJSONArray(user);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			jsonArray = new JSONArray();
			JSONObject newJsonfav = jsonObject;
			for (int i = 0; i < oldJsonfavorites.length(); i++) {
				try {
					if (newJsonfav.get(Search.ID).equals(
							oldJsonfavorites.getJSONObject(i).get(Search.ID))) {
						fav = true;
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		// new DownloadJSONReviews().execute();

		/***
		 * for loading any offers detail if any
		 * ****/

		Button offer_button = (Button) findViewById(R.id.button3);

		offer_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub.
				// rl_offers.setVisibility(1);

				Intent in = new Intent(SingleShop.this, ShopOffers.class);
				// Bitmap image= in.getDrawingCache();
				// Bundle sending_image = new Bundle();
				// sending_image.putParcelable("image", image);

				in.putExtra(SHOP_NAME, name);
				in.putExtra(MALL, mall);
				// in.putExtras(sending_image);//image
				in.putExtra(SHOP_NO, shop_no);
				in.putExtra(RATING, rating);
				in.putExtra(FLOOR, floor);

				/** hidden */
				in.putExtra(DESC, desc);
				in.putExtra("url", url);
				in.putExtra(ID, id);
				in.putExtra(CONTACT, contact_no);
				in.putExtra(EMAIL, email);
				// in.putExtra(OFFERS, offers);
				in.putExtra(BRAND, brand);
				in.putExtra(FB, fb);
				// in.putExtra(TWITTER, shop_twitter);
				// in.putExtra(GPLUS, shop_gplus);

				in.putExtra("offer_url", offers);
				startActivity(in);

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

				if (contact_no.equalsIgnoreCase("null") || contact_no.length() == 0
						|| contact_no.isEmpty()) {
				Toast.makeText(SingleShop.this,"No Contact details ", Toast.LENGTH_SHORT).show();
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
				if(email.equalsIgnoreCase("null")){
					Toast.makeText(getApplicationContext(), "Not available", Toast.LENGTH_SHORT).show();;
					
				}
				else{
				Intent i = new Intent(SingleShop.this, Email.class);
				i.putExtra("mall_email", email);
				startActivity(i);

				}
			}
		});

		ImageButton fb_click = (ImageButton) findViewById(R.id.imageButton3);
		fb_click.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(fb.equalsIgnoreCase("null")){
					Toast.makeText(getApplicationContext(), "No email info available", Toast.LENGTH_SHORT).show();
					
				}
				else{
				Intent i = new Intent(SingleShop.this, Facebook.class);
				i.putExtra("link", fb);
				startActivity(i);
				}
			}
		});

		/*
		 * requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		 * setContentView(R.layout.main);
		 * getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		 * R.layout.test);
		 */
		// Post Review
		Button postButton = (Button) findViewById(R.id.btn_post_review);
		postButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent post = new Intent(SingleShop.this, ShopReviews.class);
				post.putExtra("shopid", id);
				startActivity(post);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.single_shop_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		boolean toInsertInFav = true;
		switch (item.getItemId()) {
		case R.id.star:
			sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			if(sp.contains("username")){
			fav = true;
			try {
				File jsonFile = new File(getApplicationContext().getFilesDir(),
						"favourites.json");
				InputStream in = new FileInputStream(jsonFile);

				if (in != null) {
					try {
						InputStreamReader tmp = new InputStreamReader(in);
						BufferedReader reader = new BufferedReader(tmp);
						String str;
						StringBuilder buf = new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str);

						}

						result = buf.toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			}

			oldJson = null;
			try {
				oldJson = new JSONObject(result).getJSONArray(user);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jsonArray = new JSONArray();
			JSONObject newJson = jsonObject;
			json = new JSONObject();
			for (int i = 0; i < oldJson.length(); i++) {
				try {
					if (newJson.get(Search.ID).equals(
							oldJson.getJSONObject(i).get(Search.ID))) {
						toInsertInFav = false;
						break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			try {
				if (toInsertInFav == true) {
					oldJson.put(newJson);

				}
				json = new JSONObject(result).put(user, oldJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String filename = "favourites.json";

			String string = json.toString();

			try {
				FileOutputStream outputStream = openFileOutput(filename,
						Context.MODE_WORLD_READABLE);
				outputStream.write(string.getBytes());
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			invalidateOptionsMenu();
			}
			else{
				Toast.makeText(getApplicationContext(), "Login to Mark Favourites", Toast.LENGTH_SHORT).show();
			}
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.star2:
			fav = false;
			try {
				File jsonFile = new File(getApplicationContext().getFilesDir(),
						"favourites.json");
				InputStream in = new FileInputStream(jsonFile);

				if (in != null) {
					try {
						InputStreamReader tmp = new InputStreamReader(in);
						BufferedReader reader = new BufferedReader(tmp);
						String str;
						StringBuilder buf = new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str);

						}

						result = buf.toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			}
			oldJsonfavorites = null;
			try {
				oldJsonfavorites = new JSONObject(result).getJSONArray(user);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			jsonArray = new JSONArray();
			json = new JSONObject();
			testOldJsonfavourites = new JSONArray();
			JSONObject newJsonfav = jsonObject;
			for (int i = 0; i < oldJsonfavorites.length(); i++) {
				try {
					if (newJsonfav.get(Search.ID).equals(
							oldJsonfavorites.getJSONObject(i).get(Search.ID))) {
					} else {
						testOldJsonfavourites.put(oldJsonfavorites
								.getJSONObject(i));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				json = new JSONObject(result).put(user, testOldJsonfavourites);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// String filename = "myfile.json";

			String jsonstring = json.toString();

			try {
				FileOutputStream outputStream = openFileOutput(
						"favourites.json", Context.MODE_WORLD_READABLE);
				outputStream.write(jsonstring.getBytes());
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			invalidateOptionsMenu();
			return true;
		default:
			return true;
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.single_shop_menu, menu);
		if (fav) {
			menu.getItem(1).setVisible(true);
			menu.getItem(0).setVisible(false);
		} else {
			menu.getItem(1).setVisible(false);
			menu.getItem(0).setVisible(true);
		}
		return true;
	}

	// monitor phone call activities
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

	public void postReview(View v) {
		Intent i = new Intent(SingleShop.this, Reviews.class);
		startActivity(i);
	}

	// DownloadJSON AsyncTask
	private class DownloadJSONReviews extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(SingleShop.this);
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

			// int success;

			try {

				json = jsonParser.getJSONFromUrl(GET_REVIEWS_URL);

				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(MESSAGE);
				if (success == 1) {

					// Log.d("BRAND EXISTS!", json.toString());

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

			if (success == 1) {
				listview = (ListView) findViewById(R.id.list_r);
				// Pass the results into ListViewAdapter.java
				adapter = new CommentsLazyAdapter(SingleShop.this, arraylist);
				listview.setAdapter(adapter);

				mProgressDialog.dismiss();

			} else {
				mProgressDialog.dismiss();
				Toast.makeText(SingleShop.this, "No Reviews", Toast.LENGTH_LONG)
						.show();

			}

		}/* onPost end */

	}

}
