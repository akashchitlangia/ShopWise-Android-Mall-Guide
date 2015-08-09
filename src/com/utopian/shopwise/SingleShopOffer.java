package com.utopian.shopwise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.utopian.imageloader.ImageLoader;
import com.utopian.listAdapter.CommentsLazyAdapter;
import com.utopian.listAdapter.LazyAdapter;
import com.utopian.tools.JSONParser;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SingleShopOffer extends ActionBarActivity {

	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	LazyAdapter adapter;
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
	int indexRecents;
	Boolean toInsertInRecents = true;

	ArrayList<HashMap<String, String>> arraylist;

	public static final String ID = "shop_id";
	public static final String MALL = "mall";
	public static final String SHOP_NO = "shop_no"; // parent node
	public static final String SHOP_NAME = "shop_name"; // parent node
	public static final String FLOOR = "floor_no";
	public static final String THUMB_URL = "image";
	public static final String CONTACT = "contact_no"; // parent node
	public static final String EMAIL = "email"; // parent node
	public static final String OFFERS = "offers";
	public static final String RATING = "rating"; // parent node
	public static final String DESC = "description_shop";
	public static final String BRAND = "brand";
	public static final String FB = "shop_fb";
	public static final String TWITTER = "shop_twitter";
	public static final String GPLUS = "shop_gplus";

	public static String MESSAGE = "success";
	private static final String Search_URL = "http://utopiansolutions.co.in/db/banner_shop.php";

	TextView lblName;
	TextView lblMall;
	TextView lblDesc;
	TextView lblFloor;
	TextView lblShopNo;
	RatingBar lblRating;
	TextView lblOffer;
	
	String SName;
	String SMall;
	String SDesc;
	String SFloor;
	String SShopNo;
	float SRating;
	String SOffer;
	
	String shopId;
	String imageUrl;
	String desc;
	String email;
	 String contact_no;
 
	String brand;
	 String fb ;
	String twitter ;
	String gplus ;
	JSONParser jsonParser = new JSONParser();

	String shop_id;
    
	public ImageLoader imageLoader; 

	ImageView im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		

		setContentView(R.layout.single_shop);
		
		imageLoader=new ImageLoader(SingleShopOffer.this);

		Intent intent = getIntent();
		shop_id = intent.getStringExtra("shop_id");

		lblName = (TextView) findViewById(R.id.s_name);
		lblMall = (TextView) findViewById(R.id.s_mall);
		lblDesc = (TextView) findViewById(R.id.s_desc);
		lblFloor = (TextView) findViewById(R.id.floor);
		lblShopNo = (TextView) findViewById(R.id.s_shop_no);
		lblRating = (RatingBar) findViewById(R.id.ratingBar1);
		lblOffer = (TextView) findViewById(R.id.s_offer);
		
		lblDesc.setText("akash");

		final RelativeLayout rl_offers = (RelativeLayout) findViewById(R.id.box2);
		im = (ImageView) findViewById(R.id.s_image);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		new DownloadOfferStore().execute();
		
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		/*// for Recents
				if (sp.contains("username")) {
					user = sp.getString("username", "");

					try {

						jsonObject.put(Search.SHOP_NAME, lblName.getText().toString());
						jsonObject.put(Search.MALL, lblMall.getText().toString());
						jsonObject.put(Search.DESC, lblDesc.getText().toString());
						jsonObject.put(Search.FLOOR, lblFloor.getText().toString());
						jsonObject.put(Search.SHOP_NO, lblShopNo.getText().toString());
						jsonObject.put(Search.RATING, lblRating.getRating());
						jsonObject.put(Search.ID,shop_id);
						jsonObject.put(Search.OFFERS, lblOffer.getText().toString());
						jsonObject.put(Search.THUMB_URL, imageUrl);
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
		
*/		
		
		

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
					Toast toast = Toast.makeText(SingleShopOffer.this,
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
				Intent i = new Intent(SingleShopOffer.this, Email.class);
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
				Intent i = new Intent(SingleShopOffer.this, Facebook.class);
				i.putExtra("link", fb);
				startActivity(i);

			}
		});
		
		Button postButton = (Button) findViewById(R.id.btn_post_review);
		postButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent post = new Intent(SingleShopOffer.this, ShopReviews.class);
				post.putExtra("shopid", shop_id);
				startActivity(post);

			}
		});
		
		
		Button offer_button = (Button) findViewById(R.id.button3);

		offer_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub.
			//	rl_offers.setVisibility(1);
				
				Intent in = new Intent(SingleShopOffer.this,ShopOffers.class);
			//	Bitmap image= in.getDrawingCache();
			//	Bundle sending_image = new Bundle();
			//	sending_image.putParcelable("image", image);
				
				in.putExtra(SHOP_NAME, SName);
				in.putExtra(MALL, SMall);
			//	in.putExtras(sending_image);//image
				in.putExtra(SHOP_NO,SShopNo);
				in.putExtra(RATING, SRating);
				in.putExtra(FLOOR,SFloor);
				
				/**hidden*/
				in.putExtra(DESC, desc);
              in.putExtra("url", imageUrl);
			    in.putExtra(ID, shop_id);
				in.putExtra(CONTACT, contact_no);
				in.putExtra(EMAIL, email);
			//	in.putExtra(OFFERS, offers);
				in.putExtra(BRAND, brand);
				in.putExtra(FB, fb);
			//	in.putExtra(TWITTER, shop_twitter);
			//	in.putExtra(GPLUS, shop_gplus);		
				in.putExtra("offer_url", SOffer);
				startActivity(in);

			}
		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.single_shop_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	private class DownloadOfferStore extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			
			  mProgressDialog = new ProgressDialog(SingleShopOffer.this);
			 
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
				params.add(new BasicNameValuePair("shop_id", shop_id));
				json = jsonParser.makeHttpRequest(Search_URL, "POST", params);

				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(MESSAGE);
				if (success == 1) {

					Log.d("BRAND EXISTS!", json.toString());

					jsonarray = json.getJSONArray("posts");

						jsonobject = jsonarray.getJSONObject(0);
						
						SName=jsonobject.getString(SHOP_NAME);
						SMall=jsonobject.getString(MALL);
						SDesc=jsonobject.getString(DESC);
						SFloor=jsonobject.getString(FLOOR);
						SShopNo=jsonobject.getString(SHOP_NO);
						SRating=Float.parseFloat(jsonobject.getString(RATING));
						SOffer=jsonobject.getString(OFFERS);
						contact_no=jsonobject.getString(CONTACT);
						
						shopId=jsonobject.getString(ID);
						
						imageUrl=jsonobject.getString(THUMB_URL);
						
						email= jsonobject.getString(EMAIL);
						
						fb= jsonobject.getString(FB);
					
						

					
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

			
				imageLoader.DisplayImage(imageUrl, im);
				lblName.setText(SName);
				lblMall.setText(SMall);
				lblDesc.setText(SDesc);
				lblFloor.setText(SFloor);
				lblShopNo.setText(SShopNo);
				lblRating.setRating(SRating);
				lblOffer.setText(SOffer);
				mProgressDialog.dismiss();
				
			} else {

				Toast.makeText(SingleShopOffer.this, "No Such Brand!!",
						Toast.LENGTH_LONG).show();

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
