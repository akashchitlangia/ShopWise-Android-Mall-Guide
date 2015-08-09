package com.utopian.shopwise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.utopian.validations.Validation;

public class Email extends ActionBarActivity {
	ImageButton buttonSend;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	EditText textName;
	EditText textNumber;
 EditText textEmail;
  String body,mall_email;
  String shopWise="Query form via ShopWise Android App";
  String downloadLink;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
 
		buttonSend = (ImageButton) findViewById(R.id.submit);
		

		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		Intent i =getIntent();
	mall_email = i.getStringExtra("mall_email");

		//textTo = (EditText) findViewById(R.id.);
		textSubject = (EditText) findViewById(R.id.subject);
		textSubject.setHintTextColor(getResources().getColor(R.color.white));
		
		
		textMessage = (EditText) findViewById(R.id.message);
		textMessage.setHintTextColor(getResources().getColor(R.color.white));
		
		textName = (EditText) findViewById(R.id.name_r);
		textName.setHintTextColor(getResources().getColor(R.color.white));
		
		
		textEmail = (EditText) findViewById(R.id.email_r);
		textEmail.setHintTextColor(getResources().getColor(R.color.white));
		
		textNumber = (EditText) findViewById(R.id.mobile_r);
		textNumber.setHintTextColor(getResources().getColor(R.color.white));
	
		
		SharedPreferences shared = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (shared.contains("username")) {
			textName.setText(shared.getString("name", ""));
		//	password.setText(shared.getString("password", ""));
			textEmail.setText(shared.getString("email", ""));
			textNumber.setText(shared.getString("mobile", ""));
			//name.setText(shared.getString("name", ""));
		}
		
		textName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(textName);

			}
		});

		textMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(textMessage);

			}
		});
		
	
		textSubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(textSubject);

			}
		});
		textEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(textEmail);

			}
		});
		textNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				Validation.hasText(textNumber);

			}
		});
		
		
		
		
		textName.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(textName);

					return true;
				}
				
				
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(textName);

					return true;
				}
				return false;
			}
		});
		textMessage.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(textMessage);
					return true;
				}
				return false;
			}
		});

		textSubject.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.hasText(textSubject);

					return true;
				}
				return false;
			}
		});
		textEmail.setOnKeyListener(new OnKeyListener() {

			//String eml = textEmail.getText().toString();
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					//boolean x= validateEmail(eml);
					//Validation.isEmailAddress(email,true);
					if(!textEmail.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))  
				    {
						textEmail.setError( "Please Enter valid Email" );  
				    	return false;
				    }
					
					textNumber.requestFocus();
					//return x;
				}
				return true;
			}
		});
		textNumber.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if ((event.getAction() == KeyEvent.ACTION_DOWN) ||(keyCode == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				//   Toast.makeText(Register.this, username.getText(), Toast.LENGTH_SHORT).show();
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					Validation.isPhoneNumber(textNumber,true);
					return true;
				}
				return false;
			}
		});


		
		
	
		
		buttonSend.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {


				
				//String name = textName.getText().toString();
				String mob= textNumber.getText().toString();
				String email1 = textEmail.getText().toString();
				
				
 
			  //String to = textTo.getText().toString();
			  String subject = textSubject.getText().toString();
			  String message = textMessage.getText().toString();
 
			  if(textName.getText().toString().length()==0)  
	          {   
				  
				  textName.setError( "Please Enter UserName" );  
	          }  
	    else if(textSubject.getText().toString().length()==0)  
	          {           
	    	textSubject.setError( "Subject Cant Be Empty" );  
	          }      
	    else if(textNumber.getText().toString().length()<10)  
	          {           
	    	textNumber.setError( "Please Enter Valid Mobile No" );  
	          }  
	    else if(textMessage.getText().toString().length()>10)  
          {           
	    	textMessage.setError( "Please Enter Valid Mobile No" );  
          }  
	    
	    else if(textEmail.getText().toString().length()==0 )
          {           
	    	textEmail.setError( "Please Enter your Email" );  
          } 
	    else if(!textEmail.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))  
	    {
	    	textEmail.setError( "Please Enter valid Email" );  
	    }
		
			  
	    else
	    {
			  
			  final String body=
						 shopWise+"<br><br>Name : "+textName.getText().toString()+"<br>Mobile :"+textNumber.getText().toString()+
					 "<br>Email :"+textEmail.getText().toString()+"<br>Message:"+textMessage.getText().toString()+"<br><br>"+downloadLink;
			  
			  
	     
			  
			  /*
			  if(name.equalsIgnoreCase("") || email1.equalsIgnoreCase("") ||
						subject.equalsIgnoreCase("") || message.equalsIgnoreCase("") || mob.equalsIgnoreCase(""))

				{

					Toast toast=Toast.makeText(Email.this, " Please fill all the Details ",Toast.LENGTH_SHORT);
					//	toast.setGravity(Gravity.CENTER, 0,0);
					toast.show();
				}*/
			  
			  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{mall_email});
			  //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
			  //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
			  email.putExtra(Intent.EXTRA_SUBJECT, subject);
			//  email.putExtra(Intent.EXTRA_TEXT, textMessage.getText().toString());
			   email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body)); 
			  //need this to prompts email client only
			  email.setType("message/rfc822");
 
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));
	    }
			}
		});
	}
}


