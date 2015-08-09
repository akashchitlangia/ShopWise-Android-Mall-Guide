package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.utopian.tools.JSONParser;

public class Reviews extends ActionBarActivity {

	EditText title, message;
	Button submit;
	RatingBar rating;
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	String shopid;
	SharedPreferences sp;
	private static final String POST_COMMENT_URL = "http://utopiansolutions.co.in/db/addcomment.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		// LayoutInflater layoutInflater= (LayoutInflater)getActivity()

		View popupView = layoutInflater.inflate(R.layout.reviews, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(popupView);
		// final EditText input = (EditText) popupView.findViewById(R.id.);
		// setContentView(R.layout.reviews);

		title = (EditText) popupView.findViewById(R.id.title);
		title.setHintTextColor(getResources().getColor(R.color.white));
		message = (EditText) popupView.findViewById(R.id.message);
		message.setHintTextColor(getResources().getColor(R.color.white));
		rating = (RatingBar) popupView.findViewById(R.id.ratingBar1);

		// /getting Intent
		Intent in = getIntent();
		shopid = in.getStringExtra("shopid");

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();

					}
				});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();

		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		if (sp.contains("username")) {
			submit = (Button) popupView.findViewById(R.id.submit);

			submit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (title.getText().toString().length() == 0) {
						show();
						title.setError("Please Enter UserName");
					} else if (message.getText().toString().length() == 0) {
						show_m();
						message.setError("Please Enter your review");
					}

					else if (title.getText().toString().length() == 0
							&& message.getText().toString().length() == 0)

					{
						show_m_p();
						title.setError("Please Enter UserName");
						message.setError("Please Enter your message");

					} else if (rating.getRating() == 0) {
						show_r();
					}

					// TODO Auto-generated method stub
					else {
						new PostComment().execute();
					}
					// finish();
				}

			});
		} else {
			submit = (Button) popupView.findViewById(R.id.submit);

			submit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(getApplicationContext(),
							"Login to Post Review", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	protected void show_r() {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		rating.startAnimation(shake);
	}

	protected void show_m_p() {
		// TODO Auto-generated method stub
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		title.startAnimation(shake);
		message.startAnimation(shake);
	}

	protected void show_m() {
		// TODO Auto-generated method stub
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		message.startAnimation(shake);
	}

	protected void show() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		title.startAnimation(shake);

	}

	class PostComment extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Reviews.this);
			pDialog.setMessage("Posting Review...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			String post_title = title.getText().toString();
			String post_message = message.getText().toString();
			float rate = rating.getRating();

			// We need to change this:
			SharedPreferences shared = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			// String post_username = sp.getString("username", "");

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", shared.getString(
						"username", "")));
				params.add(new BasicNameValuePair("shopid", shopid));
				params.add(new BasicNameValuePair("title", post_title));
				params.add(new BasicNameValuePair("rating", Float
						.toString(rate)));
				params.add(new BasicNameValuePair("message", post_message));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(POST_COMMENT_URL,
						"POST", params);

				// full json response
				Log.d("Post Comment attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Comment Added!", json.toString());

					/*
					 * FragmentManager fm = getFragmentManager();
					 * FragmentTransaction ft = fm.beginTransaction();
					 * 
					 * ReviewFragment frag = new ReviewFragment();
					 * ft.add(R.id.list_reviews, frag);
					 */

					Intent i = new Intent(Reviews.this, ShopReviews.class);
					i.putExtra("shopid", shopid);
					startActivity(i);
					finish();
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Comment Failure!", json.getString(TAG_MESSAGE));
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
			if (file_url != null) {
				// Toast.makeText(Reviews.this, file_url,
				// Toast.LENGTH_LONG).show();
			}

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event)

	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);
			finish();
		}
		return false;

	};

}
