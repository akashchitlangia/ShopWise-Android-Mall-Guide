package com.utopian.shopwise;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.utopian.tools.JSONParser;


public class Login extends BaseActivity {


	ImageButton login,register,guest,final_login;
	TextView companyname;
	EditText username,password;
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	
	JSONArray jsonArray;
	JSONObject jsonObject;

	
	ScrollView scrollview;

	SharedPreferences shared;
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;	


	private static final String LOGIN_URL = "http://utopiansolutions.co.in/db/login.php";


	// JSON element ids from repsonse of php script:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		

		setContentView(R.layout.login);
		  ImageView mImageView = (ImageView)findViewById(R.id.image);
	        
	        RelativeLayout rl=(RelativeLayout) findViewById(R.id.relative);

	        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_in_from_top_corner);
	        rl.setAnimation(anim);
	        anim.start();	
	        mBackground = mImageView;
	        
	        moveBackground();
		/***animation***/
		//	overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);


		/***********Full Screen*********/	
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




		register = (ImageButton)findViewById(R.id.register);

		guest = (ImageButton)findViewById(R.id.guest);
		final_login = (ImageButton)findViewById(R.id.final_login);


		//	companyname = (TextView)findViewById(R.id.companyname);

		username = (EditText)findViewById(R.id.username);
		username.setHintTextColor(getResources().getColor(R.color.white));

		password = (EditText)findViewById(R.id.password);
		password.setHintTextColor(getResources().getColor(R.color.white));



		cd = new ConnectionDetector(getApplicationContext());
		/**
		 * Check Internet status button click event
		 * */
		// get Internet status
		isInternetPresent = cd.isConnectingToInternet();

		// check for Internet status
		if (isInternetPresent) {
			// Internet Connection is Present
			// make HTTP requests
			//    showAlertDialog(MainActivity.this, "Internet Connection","You have internet connection", true);

			register = (ImageButton)findViewById(R.id.register);
			guest = (ImageButton)findViewById(R.id.guest);
			final_login = (ImageButton)findViewById(R.id.final_login);
			username = (EditText)findViewById(R.id.username);
			username.setHintTextColor(getResources().getColor(R.color.white));
			password = (EditText)findViewById(R.id.password);
			password.setHintTextColor(getResources().getColor(R.color.white));
			/*******
			 * To pop up Keyboard when edittext is clicked
			 * ******/

			username.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					v.setFocusable(true);
					v.setFocusableInTouchMode(true);
					return false;
				}
			});

			/*******
			 * To pop up Keyboard when edittext is clicked
			 * ******/
			password.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					v.setFocusable(true);
					v.setFocusableInTouchMode(true);
					return false;
				}
			});
			
			/*******
			 * register button OnClick
			 * *******/
			register.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(Login.this,Register.class);
					startActivity(i);
				}
			});

			/*******
			 * Login button OnClick
			 * *******/
			final_login.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					String user = username.getText().toString();
					String pass = password.getText().toString();

					if(user.equalsIgnoreCase("") && pass.equalsIgnoreCase(""))

					{
						//Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);		
						//username.startAnimation(shake);
						
					//	username.setError("Please fill all the Credentials");
						
						shakeUsername();
						
					}
					else if(user.equalsIgnoreCase("") )

					{

						
						shakeUsername();
						//	toast.setGravity(Gravity.CENTER, 0,0);
					}
					else if(pass.equalsIgnoreCase("") )

					{
						shakePassword();

					}

					else{
						
						
						isInternetPresent = cd.isConnectingToInternet();

						// check for Internet status
						if (isInternetPresent) {
						
						new LoginAttempt().execute();
					
						}
						
						
						
						}
					
					

				}
			});



				
			
			/*****
			 * network check if end 
			 * 
			 */

		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			AlertDialogX.showAlertDialog(Login.this, "No Internet Connection",
					"You don't have internet connection.", false);
		}

		guest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					//Intent i = new Intent(MainActivity.this, Grid.class);
					
					Intent i = new Intent(Login.this, MainActivity.class);

				//Intent i = new Intent(MainActivity.this, Categories.class);

				startActivity(i);
				finish();
			}
		});

	}




	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * */

	
	protected void shakeUsername() {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		username.startAnimation(shake);
	}

	protected void shakeUsernamePassword() {
		// TODO Auto-generated method stub
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		username.startAnimation(shake);
		password.startAnimation(shake);
		
	}

	protected void shakePassword() {
		// TODO Auto-generated method stub
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		password.startAnimation(shake);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Login.this);
			builder.setMessage("Do you wish to exit? ");
			builder.setCancelable(false);
			builder.setPositiveButton(" Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Login.this.finish();

							//
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();

						}
					});

			AlertDialog alert = builder.create();
			alert.show();

			// TODO Auto-generated method stub

		}
		return false;
	}




	/****Login AsyncTask
	 * @return 
	 * 
	 * ***/


	class LoginAttempt extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Attempting login...");
			pDialog.setIndeterminate(false);

			pDialog.setCancelable(false);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			String user = username.getText().toString();
			String pass = password.getText().toString();
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", user));
				params.add(new BasicNameValuePair("password", pass));

				//Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",params);

				// check your log for json response
				//Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.d("Login Successful!", json.toString());
					// save user data
					
					jsonArray=json.getJSONArray("info");
					jsonObject=jsonArray.getJSONObject(0);
					
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					Editor edit = sp.edit();
					
					// save user data
					edit.putString("username", user);
					edit.putString("password",pass);
					edit.putString("mobile", jsonObject.getString("mobile"));
					edit.putString("email", jsonObject.getString("email"));
					edit.putString("name", jsonObject.getString("name"));
					edit.commit();

					Intent i = new Intent(Login.this,  MainActivity.class);
				
					startActivity(i);
					finish();
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after Login
			pDialog.dismiss();
			if (file_url != null) {
			Toast.makeText(Login.this, file_url, Toast.LENGTH_SHORT).show();


			}

		}
	}

	 @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        if (Utils.hasHoneycomb()) {
	            View demoContainerView = findViewById(R.id.image);
	            demoContainerView.setAlpha(0);
	            ViewPropertyAnimator animator = demoContainerView.animate();
	            animator.alpha(1);
	            if (Utils.hasICS()) {
	                animator.setStartDelay(250);
	            }
	            animator.setDuration(1000);
	        }
	    }

	    @Override
	    protected void onPause() {
	        super.onPause();
	        overridePendingTransition(0, 0);
	    }
	
	
	@Override
	protected void onResume()
	{
		shared=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			      if (shared.contains("username"))
			      {
			      if(shared.contains("password")){
			         Intent i = new Intent(this,MainActivity.class);
			         startActivity(i);
			         finish();
			      }
			      }
			      super.onResume();	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
