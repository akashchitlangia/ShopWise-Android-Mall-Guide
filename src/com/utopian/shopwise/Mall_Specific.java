package com.utopian.shopwise;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.utopian.listAdapter.LazyAdapter;

public class Mall_Specific extends ActionBarActivity {
	private static final int REQUEST_CODE = 1234;
	
	
	
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	LazyAdapter adapter;
	ProgressDialog mProgressDialog;
	JSONObject json;
	int success;
	ImageButton mail,call,button_fb;
	String search_brand;
	String email;
	String name_mall;
	String contact__no;
	ConnectionDetector cd;	
	Boolean isInternetPresent = false;
	AutoCompleteTextView searchtxt;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlemall);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		
		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		Intent in = getIntent();


	    name_mall = in.getStringExtra("mall");
		String address = in.getStringExtra("address");
		
		String rating = in.getStringExtra("rating");
		ImageView mall_image=(ImageView)findViewById(R.id.imageView1);
		
		TextView desc = (TextView)findViewById(R.id.s_desc);
		//new DownloadJSONMall().execute();	
		mail = (ImageButton) findViewById(R.id.imageButton2);
		
		call=(ImageButton) findViewById(R.id.imageButton1);
		button_fb=(ImageButton) findViewById(R.id.imageButton3);
		
		
		ImageButton google=(ImageButton) findViewById(R.id.speakButton);
		google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Mall_Specific.this,Search_In_Mall.class);
				intent.putExtra("mall", name_mall);
				startActivity(intent);
			}
		});
		
		Button postButton = (Button) findViewById(R.id.btn_post_review);
		postButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent post = new Intent(Mall_Specific.this, ShopReviews.class);
				post.putExtra("shopid",name_mall );
				startActivity(post);

			}
		});
		
		searchtxt = (AutoCompleteTextView) findViewById(R.id.search_bar);

		searchtxt.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				
				Intent i =new Intent(Mall_Specific.this, Search_In_Mall.class);
				i.putExtra("mall", name_mall);
			startActivity(i);
				
				
				// TODO Auto-generated method stub
				return true;
			}
		
		    
		   
		});
		
		
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
		
		
		
		
		//sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
     //     user=sp.getString("username", "");		
		// Get JSON values from previous intent
		//Bundle received_image = getIntent().getExtras();
		

		if(name_mall.equalsIgnoreCase("Treasure Island"))
		{
			final String contact_no = "09981112044";
			mall_image.setImageResource(R.drawable.ti);
			desc.setText(R.string.ti);
			
			call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phn_no=	"tel:"+contact_no;
			        Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse(phn_no));
					startActivity(callIntent);
				}
			});
			
			
			
			mail.setOnClickListener(new OnClickListener() {
				String email="info@timalls.com";
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Email.class);
					intent.putExtra("mall_email", email);
					
					startActivity(intent);
				}
			});

			button_fb.setOnClickListener(new OnClickListener() {
				String url ="https://www.facebook.com/TI.indore";
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Facebook.class);
					intent.putExtra("link", url);
					
					startActivity(intent);
				}
			});
			
			
		}
		
		else if(name_mall.equalsIgnoreCase("Century 21"))
		{
			final String contact_no = "07314007892";
			mall_image.setImageResource(R.drawable.c21);
			desc.setText(R.string.c21);
            call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phn_no=	"tel:"+contact_no;
			        Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse(phn_no));
					startActivity(callIntent);
				}
			});
			
			mail.setOnClickListener(new OnClickListener() {
				String email="info@c21malls.co.in";
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Email.class);
					intent.putExtra("mall_email", email);
					
					startActivity(intent);
				}
			});
			button_fb.setOnClickListener(new OnClickListener() {
				String url="https://www.facebook.com/pages/C21-MALL-INDORE/348806423778?v=info";
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Facebook.class);
					intent.putExtra("link", url);
					
					startActivity(intent);
				}
			});

		}
		else if(name_mall.equalsIgnoreCase("Central Naman Mall"))
		{
			final String contact_no = "07313911995";
			mall_image.setImageResource(R.drawable.central);
			desc.setText(R.string.central);
call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phn_no=	"tel:"+contact_no;
			        Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse(phn_no));
					startActivity(callIntent);
				}
			});
			
			
			mail.setOnClickListener(new OnClickListener() {
				String email="hr.ctindore@futurelifestyle.in";
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Email.class);
					intent.putExtra("mall_email", email);
					
					startActivity(intent);
				}
			});
			
			button_fb.setOnClickListener(new OnClickListener() {
			String url="https://www.facebook.com/pages/Central-Mall-Indore/104051216320002";
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Facebook.class);
					intent.putExtra("link", url);
					
					startActivity(intent);
				}
			});
			
			
			
			

		}

		else if(name_mall.equalsIgnoreCase("Mangal City Mall"))
		{final String contact_no = "07314001532";
			
			mall_image.setImageResource(R.drawable.mangal);
			desc.setText(R.string.mangal);
call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phn_no=	"tel:"+contact_no;
			        Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse(phn_no));
					startActivity(callIntent);
				}
			});
			
			mail.setOnClickListener(new OnClickListener() {
				String email="mangalgroup_mall@yahoo.com";
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Email.class);
					intent.putExtra("mall_email", email);
					
					startActivity(intent);
				}
			});
			
			
			
			
			
			button_fb.setOnClickListener(new OnClickListener() {
				//String url="https://www.facebook.com/pages/Central-Mall-Indore/104051216320002";
				String url="https://www.facebook.com/pages/Mangal-City-Mall-Indore/165902133480097?rf=269580553111867";
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Mall_Specific.this,Facebook.class);
						intent.putExtra("link", url);
						
						startActivity(intent);
					}
				});

			
		}
		else if(name_mall.equalsIgnoreCase("Malhar Mega Mall"))
		{
			final String contact_no = "07314007892";
			mall_image.setImageResource(R.drawable.icon_malhar);
			desc.setText(R.string.malhar);
call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phn_no=	"tel:"+contact_no;
			        Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse(phn_no));
					startActivity(callIntent);
				}
			});
			
			
			mail.setOnClickListener(new OnClickListener() {
				String email="info@c21malls.co.in";
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Email.class);
					intent.putExtra("mall_email", email);
					
					startActivity(intent);
				}
			});

			
		}
		
		button_fb.setOnClickListener(new OnClickListener() {
		//	String url="https://www.facebook.com/pages/Central-Mall-Indore/104051216320002";
			String url="https://www.facebook.com/pages/Malhar-Mega-Mall/217565951649505";	
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Mall_Specific.this,Facebook.class);
					intent.putExtra("link", url);
					
					startActivity(intent);
				}
			});
		
		
		



	/*	Bitmap bmp = (Bitmap) received_image.getParcelable("image");
    
		ImageView mall_image=(ImageView)findViewById(R.id.imageView1);
		mall_image.setImageBitmap(bmp );
		
	*/	
		TextView mall_name = (TextView)findViewById(R.id.textView1);
		mall_name.setText(name_mall);
		
		TextView mall_address = (TextView)findViewById(R.id.textView2);
		mall_address.setText(address);
		
		RatingBar r = (RatingBar)findViewById(R.id.ratingBar1);
		r.setRating(Float.parseFloat(rating));

		

        //add PhoneStateListener
	/*	PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
		
	 final TextView time1= (TextView)findViewById(R.id.textView1);
		Button time= (Button) findViewById(R.id.button1);
		time.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time1.setVisibility(View.VISIBLE);
				
			}
		});
        //button click 
        ImageButton call= (ImageButton)findViewById(R.id.call_button);
        call.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		        Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:9423514375"));
				startActivity(callIntent);
		
			}
		});

        ImageButton email=(ImageButton)findViewById(R.id.email_button);
        email.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i =new Intent(Mall_Specific.this,Email.class);
				//startActivity(i);
				
			}
		});
        	
     /*   
	 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
     setContentView(R.layout.main);
     getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.test);*/
}
	
	
	//monitor phone call activities
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
			
		/**
		 * Fire an intent to start the voice recognition activity.
		 */
		
				

	
}
