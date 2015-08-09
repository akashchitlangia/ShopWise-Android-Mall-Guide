package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.utopian.listAdapter.CommentsLazyAdapter;
import com.utopian.tools.JSONParser;

public class ReviewFragment extends Fragment {
	
	
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	CommentsLazyAdapter adapter;
	ProgressDialog mProgressDialog;
	JSONObject json;
	int success;
	
	
	
	EditText title,message;
	Button submit;
	RatingBar rating;
	private ProgressDialog pDialog;
	public static final String TITLE = "title";
	public static final String RATING = "rating";
	public static final String POSTS = "posts";
	public static final String POST_ID = "post_id";
	public static final String USERNAME = "username";
	public static final String REVIEW = "message";

	SharedPreferences sp;
	
	ArrayList<HashMap<String, String>> arraylist;
	 View rootView ;

	public static String MESSAGE="success";
	private static final String GET_REVIEWS_URL = "http://utopiansolutions.co.in/db/comments.php";

	JSONParser jsonParser= new JSONParser();
public ReviewFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
 
        rootView = inflater.inflate(R.layout.list_reviews, container, false);
        
        
        
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
   

		new DownloadJSONReviews().execute();
		
		Button btn_post_review = (Button)rootView.findViewById(R.id.btn_post_review);
	
        sp=PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		
btn_post_review.setOnClickListener(new View.OnClickListener() {
	
    	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		if(sp.contains("username")){
		Intent i = new Intent(getActivity(), Reviews.class);
		i.putExtra("shopid", "shopwise");
		startActivity(i);
		}

		else{
			Toast.makeText(getActivity(), "Please Login to Post Reviews", Toast.LENGTH_SHORT).show();
		}
		// setup a dialog window
	    
	    
	}	
	
});

    /*    Intent intent = new Intent(getActivity(),AppReviews.class);
		startActivity(intent); 
         
        */
        
        return rootView;
    }

	/*
	public void postReview(View v) {
		Intent i = new Intent(getActivity(), Reviews.class);
		startActivity(i);
	}*/


	// DownloadJSON AsyncTask
	private class DownloadJSONReviews extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(getActivity());
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

			//int success;


			

			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("shopid","shopwise"));


//				json = jsonParser.getJSONFromUrl(GET_REVIEWS_URL);
                json=jsonParser.makeHttpRequest(GET_REVIEWS_URL, "POST", params);
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(MESSAGE);
				if (success == 1) {

					//Log.d("BRAND EXISTS!", json.toString());

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

			if(success == 1){
				listview = (ListView) rootView.findViewById(R.id.list_r);
				// Pass the results into ListViewAdapter.java
				adapter = new CommentsLazyAdapter(getActivity(), arraylist);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(null);

				mProgressDialog.dismiss();

			}
			else{
				mProgressDialog.dismiss();
				Toast.makeText(getActivity(), "No Reviews",Toast.LENGTH_LONG).show();

			}

		}/*onPost end*/



	}
}