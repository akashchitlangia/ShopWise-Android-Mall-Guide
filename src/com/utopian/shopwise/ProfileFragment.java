package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.utopian.tools.JSONParser;

public class ProfileFragment extends Fragment {
	
	JSONObject jsonobject;
	JSONArray jsonarray;
	EditText username, password, mobile, email, name;
	Button update;
	SharedPreferences shared;
	
	private static final String UPDATE_PROFILE_URL = "http://utopiansolutions.co.in/db/updateprofile.php";
    private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private ProgressDialog pDialog;
/*	private static final String Search_URL = "http://www.samridhitimbers.com/db/getprofile.php";
	*/
		JSONParser jsonParser = new JSONParser();

public ProfileFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View rootView = inflater.inflate(R.layout.profile, container, false);
     /*  Intent intent = new Intent(getActivity(),Profile.class);
		startActivity(intent); 
        */
        
    
        username = (EditText) rootView.findViewById(R.id.username_r);
		username.setHintTextColor(getResources().getColor(R.color.white));

		password = (EditText) rootView.findViewById(R.id.password_r);
		password.setHintTextColor(getResources().getColor(R.color.white));

		name = (EditText)rootView. findViewById(R.id.name_r);
		name.setHintTextColor(getResources().getColor(R.color.white));

		email = (EditText)rootView. findViewById(R.id.email_r);
		email.setHintTextColor(getResources().getColor(R.color.white));

		mobile = (EditText)rootView.  findViewById(R.id.mobile_r);
		mobile.setHintTextColor(getResources().getColor(R.color.white));

		update = (Button) rootView. findViewById(R.id.submit);


		shared = PreferenceManager
				.getDefaultSharedPreferences(getActivity().getApplicationContext());
		if (shared.contains("username")) {
			username.setText(shared.getString("username", ""));
			password.setText(shared.getString("password", ""));
			email.setText(shared.getString("email", ""));
			mobile.setText(shared.getString("mobile", ""));
			name.setText(shared.getString("name", ""));
		}

		update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String user = username.getText().toString();
				String nme = name.getText().toString();
				String mob = mobile.getText().toString();
				String eml = email.getText().toString();

				if (user.equalsIgnoreCase("") || nme.equalsIgnoreCase("")
						|| mob.equalsIgnoreCase("") || eml.equalsIgnoreCase(""))

				{

					Toast toast = Toast.makeText(getActivity(),
							" Please Fill all Details ", Toast.LENGTH_SHORT);
					// toast.setGravity(Gravity.CENTER, 0,0);
					toast.show();
				}

				else {
					new UpdateUser().execute();
				}

			}
		});


	
        
        
        
        
        
        
        return rootView;
    }class UpdateUser extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Updating...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Check for success tag
			int success;
	
			String user = username.getText().toString();
			String pass=password.getText().toString();
			String nme = name.getText().toString();
			String mob = mobile.getText().toString();
			String eml = email.getText().toString();
			
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", user));
				params.add(new BasicNameValuePair("password", pass));
				params.add(new BasicNameValuePair("name", nme));
				params.add(new BasicNameValuePair("mobile", mob));
				params.add(new BasicNameValuePair("email", eml));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						UPDATE_PROFILE_URL, "POST", params);

				// full json response
				Log.d("Updating...", json.toString());
				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					shared = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
					Editor edit = shared.edit();
					edit.putString("password", pass);
					edit.putString("mobile", mob);
					edit.putString("email", eml);
					edit.putString("name", nme);
					edit.commit();
					Log.d("Updated!", json.toString());
				//	finish();
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Update Failed...!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String message) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (message != null) {
				Toast.makeText(getActivity(), message, Toast.LENGTH_LONG)
						.show();
			}

		}

	}
}