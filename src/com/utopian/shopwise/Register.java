package com.utopian.shopwise;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.utopian.tools.JSONParser;
import com.utopian.validations.Validation;

public class Register extends ActionBarActivity {

	private static final String REGISTER_URL = "http://utopiansolutions.co.in/db/register.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	SharedPreferences sp;
	EditText  username,password,name,email,mobile;
	ImageButton submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);


		setContentView(R.layout.register);
		

		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		username = (EditText)findViewById(R.id.username_r);
		username.setHintTextColor(getResources().getColor(R.color.white));

		password = (EditText)findViewById(R.id.password_r);
		password.setHintTextColor(getResources().getColor(R.color.white));

		name = (EditText)findViewById(R.id.name_r);
		name.setHintTextColor(getResources().getColor(R.color.white));

		email = (EditText)findViewById(R.id.email_r);
		email.setHintTextColor(getResources().getColor(R.color.white));

		mobile = (EditText)findViewById(R.id.mobile_r);
		mobile.setHintTextColor(getResources().getColor(R.color.white));




		username.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(username);

			}
		});
		password.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(password);

			}
		});
		name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(name);

			}
		});
		
		email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			//String eml = email.getText().toString();

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				

			//	Validation.isEmailAddress(email, true);

			}
		});
		/*
		mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(mobile);

			}
		});

*/

		username.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(username);

					return true;
				}
				
				return false;
			}
		});
		password.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(password);
					return true;
				}
				return false;
			}
		});
		
		name.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(name);

					return true;
				}
				return false;
			}
		});
		email.setOnKeyListener(new OnKeyListener() {
			String eml = email.getText().toString();
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				/*if(keyCode == KeyEvent.KEYCODE_ENTER) {
					//boolean x= validateEmail(eml);
					//Validation.isEmailAddress(email,true);
					if(!email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))  
				    {
				    	email.setError( "Please Enter valid Email" );  
				    	return false;
				    }
					
					mobile.requestFocus();
					//return x;
				}*/
				return true;
			}
		});
		mobile.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					
					Validation.isPhoneNumber(mobile,true);
					return true;
				}
				return false;
			}
		});


		/*password.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				password.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
						          // Perform action on key press
						       //   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
							if(keyCode == KeyEvent.KEYCODE_ENTER) {
								name.requestFocus();
						          return true;
						        }
						return false;
					}
				});

				return false;
			}

		});*/

		/*name.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				name.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
						          // Perform action on key press
						       //   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
							if(keyCode == KeyEvent.KEYCODE_ENTER) {
								email.requestFocus();
						          return true;
						        }
						return false;
					}
				});

				return false;
			}

		});

		email.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				email.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
						          // Perform action on key press
						       //   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
							if(keyCode == KeyEvent.KEYCODE_ENTER) {
								mobile.requestFocus();
						          return true;
						        }
						return false;
					}
				});

				return false;
			}

		});
		mobile.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mobile.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
						          // Perform action on key press
						       //   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
							if(keyCode == KeyEvent.KEYCODE_ENTER) {


								submit.requestFocus();


						          return true;
						        }
						return false;
					}
				});

				return false;
			}

		});





		 */




		submit = (ImageButton)findViewById(R.id.submit);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub




				String user = username.getText().toString();
				String pass = password.getText().toString();
				String nme = name.getText().toString();
				String mob= mobile.getText().toString();
				String eml = email.getText().toString();
				/*

				Boolean t1 = 	Validation.isPhoneNumber(mobile,true);
				Boolean t2=Validation.isEmailAddress(email,true);
				Boolean t3=Validation.hasText(username);
				Boolean t4=Validation.hasText(password);
				Boolean t5=Validation.hasText(name);
				 */

				if(username.getText().toString().length()==0)  
		          {           
					username.setError( "Please Enter UserName" );  
		          }  
		    else if(password.getText().toString().length()==0)  
		          {           
		    	password.setError( "Please Enter Password" );  
		          }      
		    else if(mobile.getText().toString().length()<10)  
		          {           
		           mobile.setError( "Please Enter Valid Mobile No" );  
		          }  
		    else if(mobile.getText().toString().length()>10)  
	          {           
	           mobile.setError( "Please Enter Valid Mobile No" );  
	          }  
		    else if(name.getText().toString().length()==0)  
		          {           
		    	name.setError( "Please Enter your name" );  
		          } 
		    else if(email.getText().toString().length()==0 )
	          {           
		    	email.setError( "Please Enter valid Email" );  
	          } 
		    else if(!email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))  
		    {
		    	email.setError( "Please Enter valid Email" );  
		    }
				
/*
				if(user.equalsIgnoreCase("") || pass.equalsIgnoreCase("") ||
						nme.equalsIgnoreCase("") || mob.equalsIgnoreCase("") || eml.equalsIgnoreCase(""))

				{

					Toast toast=Toast.makeText(Register.this, " Please fill all the Details ",Toast.LENGTH_SHORT);
					//	toast.setGravity(Gravity.CENTER, 0,0);
					toast.show();
				}
*/



				else{

					new CreateUser().execute();
				}

			}


		});

	}


	public static boolean validateEmail(String email2)
	{
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email2;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if(matcher.matches()) 
	    {
	        isValid = true;
	    }

	    return isValid;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		//closing transition animations
		//overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
	}




	class CreateUser extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setMessage("Creating User...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			String user = username.getText().toString();
			String pass = password.getText().toString();
			String nme = name.getText().toString();
			String mob= mobile.getText().toString();
			String eml = email.getText().toString();
			// String d = dob.getText().toString();

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", user));
				params.add(new BasicNameValuePair("password", pass));
				params.add(new BasicNameValuePair("name", nme));
				params.add(new BasicNameValuePair("mobile", mob));
				params.add(new BasicNameValuePair("email", eml));
				//params.add(new BasicNameValuePair("dob", dob1));

				Log.d("request!", "starting");

				//Posting user data to script 
				JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);

				// full json response
				Log.d("Registering attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("User Created!", json.toString());              	
					
					sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					Editor edit=sp.edit();
					edit.putString("username", user);
					edit.commit();
					return json.getString(TAG_MESSAGE);
				}else{
					Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			

			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
			}
			
			Intent i=new Intent(Register.this,MainActivity.class);
			startActivity(i);
			finish();
			

		}

	}


}
