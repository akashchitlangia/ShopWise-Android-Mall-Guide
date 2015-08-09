package com.utopian.shopwise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.utopian.listAdapter.LazyAdapter;

public class FavoriteFragment extends Fragment {
	
	String jsonStr;
	String jsonStrfromAssets;
	String NAME[];
	ArrayList<HashMap<String, String>> contactList;
	JSONArray contacts = null;
	JSONArray contactsfav = null;
	ListView listView;
	private ProgressDialog pDialog;

	HashMap<String, String> contactRec = new HashMap<String, String>();
	 View rootView;
	
	public FavoriteFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_find_people, container, false);
		
		
		
		  try {
		    	File jsonFile = new File(getActivity().getFilesDir(), "favourites.json");
		         InputStream in=new FileInputStream(jsonFile);

		      if (in != null) {
		        try {
		          InputStreamReader tmp=new InputStreamReader(in);
		          BufferedReader reader=new BufferedReader(tmp);
		          String str;
		          StringBuilder buf=new StringBuilder();

		          while ((str=reader.readLine()) != null) {
		            buf.append(str);
		          
		          }

		          jsonStr=buf.toString();
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        finally {
		          try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        }
		      }
		    }
		    catch (java.io.FileNotFoundException e) {
		      // that's OK, we probably haven't created it yet
		    }

		  
		  
		  
		  contactList = new ArrayList<HashMap<String, String>>();

			// Used to hold the string, either from the web service or local
			// file
			/*read from assets
			 * 	jsonStrfromAssets = jsonToStringFromAssetFolder("info.json",
					getActivity().getApplicationContext());
           */
			//read from shred preferences
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		
		   String user=preferences.getString("username","");
			
			
			
			
			try {
			

              JSONObject json=new JSONObject(jsonStr);
				JSONArray jsonarray = json.getJSONArray(user);
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects

						HashMap<String, String> contact = new HashMap<String, String>();
						contact.put(Search.SHOP_NAME, jsonobject.getString(Search.SHOP_NAME));
						contact.put(Search.DESC, jsonobject.getString(Search.DESC));					
						contact.put(Search.MALL, jsonobject.getString(Search.MALL));
						contact.put(Search.FLOOR, jsonobject.getString(Search.FLOOR));
						contact.put(Search.SHOP_NO, jsonobject.getString(Search.SHOP_NO));
						contact.put(Search.RATING, jsonobject.getString(Search.RATING));
						contact.put(Search.OFFERS, jsonobject.getString(Search.OFFERS));
						contact.put(Search.THUMB_URL, jsonobject.getString(Search.THUMB_URL));	  
						contact.put(Search.ID, jsonobject.getString(Search.ID));
						     
						// Adding single contact to list
				contactList.add(contact);

							// Set the JSON Objects into the array
						}	

					
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
		  
		  
			listView = (ListView) rootView.findViewById(R.id.list);
			// Pass the results into ListViewAdapter.java
			LazyAdapter adapter = new LazyAdapter(getActivity(), contactList);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					String name = ((TextView) view.findViewById(R.id.list_shop_name)).getText().toString();
					String mall = ((TextView) view.findViewById(R.id.list_mall)).getText().toString();
					String floor = ((TextView) view.findViewById(R.id.floor)).getText().toString();
					String shop_no = ((TextView) view.findViewById(R.id.list_shop_no)).getText().toString();
	                float rating = ((RatingBar) view.findViewById(R.id.ratingBar1)).getRating();

					ImageView imageView= ((ImageView)view.findViewById(R.id.list_image));
					imageView.buildDrawingCache();

					Bitmap image= imageView.getDrawingCache();
					Bundle sending_image = new Bundle();
					sending_image.putParcelable(Search.THUMB_URL, image);

					
					/***
					 * 
					 * Hidden
					 ***/
					String shop_id = ((TextView) view.findViewById(R.id.hidden_id)).getText().toString();
				    String contact_no = ((TextView) view.findViewById(R.id.hidden_contact)).getText().toString();
					String email = ((TextView) view.findViewById(R.id.hidden_email)).getText().toString();
					String offers = ((TextView) view.findViewById(R.id.hidden_offers)).getText().toString();
					String brand = ((TextView) view.findViewById(R.id.hidden_brands)).getText().toString();
					String shop_fb = ((TextView) view.findViewById(R.id.hidden_fb)).getText().toString();
					String shop_twitter = ((TextView) view.findViewById(R.id.hidden_twitter)).getText().toString();
					String shop_gplus = ((TextView) view.findViewById(R.id.hidden_gplus)).getText().toString();					
					String description = ((TextView) view.findViewById(R.id.hidden_desc)).getText().toString();
	                String imageURL=((TextView) view.findViewById(R.id.hidden_imageurl)).getText().toString();
					
											// Starting single contact activity
					Intent in = new Intent(getActivity().getApplicationContext(),SingleShop.class);
					in.putExtra(Search.SHOP_NAME, name);
					in.putExtra(Search.MALL, mall);
					in.putExtras(sending_image);//image
					in.putExtra(Search.SHOP_NO,shop_no);
					in.putExtra(Search.RATING, rating);
					in.putExtra(Search.FLOOR,floor);
					
					/**hidden*/
					in.putExtra(Search.DESC, description);
	                in.putExtra("url", imageURL);
					in.putExtra(Search.ID, shop_id);
					in.putExtra(Search.CONTACT, contact_no);
					in.putExtra(Search.EMAIL, email);
					in.putExtra(Search.OFFERS, offers);
					in.putExtra(Search.BRAND, brand);
					in.putExtra(Search.FB, shop_fb);
					in.putExtra(Search.TWITTER, shop_twitter);
					in.putExtra(Search.GPLUS, shop_gplus);
					
					
					startActivity(in);


				}
			});

		  
		//  new GetContacts().execute();
        return rootView;
    }
	
	
	/*private class GetContacts extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			Log.d("JFN", "About to download contacts in background");

			// This contact list will hold all the string data for all the
			// contacts
			contactList = new ArrayList<HashMap<String, String>>();

			// Used to hold the string, either from the web service or local
			// file
			read from assets
			 * 	jsonStrfromAssets = jsonToStringFromAssetFolder("info.json",
					getActivity().getApplicationContext());
             
			//read from shred preferences
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		
		   String user=preferences.getString("username","");
			
			
			
			
			try {
			
 
                JSONObject json=new JSONObject(jsonStr);
				JSONArray jsonarray = json.getJSONArray(user);
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects

						HashMap<String, String> contact = new HashMap<String, String>();
						contact.put(Search.SHOP_NAME, jsonobject.getString(Search.SHOP_NAME));
						contact.put(Search.DESC, jsonobject.getString(Search.DESC));					
						contact.put(Search.MALL, jsonobject.getString(Search.MALL));
						contact.put(Search.FLOOR, jsonobject.getString(Search.FLOOR));
						contact.put(Search.SHOP_NO, jsonobject.getString(Search.SHOP_NO));
						contact.put(Search.RATING, jsonobject.getString(Search.RATING));
						contact.put(Search.OFFERS, jsonobject.getString(Search.OFFERS));
						contact.put(Search.THUMB_URL, jsonobject.getString(Search.THUMB_URL));	  
						contact.put(Search.ID, jsonobject.getString(Search.ID));
						     
						// Adding single contact to list
				contactList.add(contact);

							// Set the JSON Objects into the array
						}	

					
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			
			return null;
		}


		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			*//**
			 * Updating parsed JSON data into ListView
			 * *//*
			// Create the adapter class using the contactList data
			listView = (ListView) rootView.findViewById(R.id.list);
			// Pass the results into ListViewAdapter.java
			LazyAdapter adapter = new LazyAdapter(getActivity(), contactList);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					String name = ((TextView) view.findViewById(R.id.list_shop_name)).getText().toString();
					String mall = ((TextView) view.findViewById(R.id.list_mall)).getText().toString();
					String floor = ((TextView) view.findViewById(R.id.floor)).getText().toString();
					String shop_no = ((TextView) view.findViewById(R.id.list_shop_no)).getText().toString();
	                float rating = ((RatingBar) view.findViewById(R.id.ratingBar1)).getRating();

					ImageView imageView= ((ImageView)view.findViewById(R.id.list_image));
					imageView.buildDrawingCache();

					Bitmap image= imageView.getDrawingCache();
					Bundle sending_image = new Bundle();
					sending_image.putParcelable(Search.THUMB_URL, image);

					
					*//***
					 * 
					 * Hidden
					 ***//*
					String shop_id = ((TextView) view.findViewById(R.id.hidden_id)).getText().toString();
				    String contact_no = ((TextView) view.findViewById(R.id.hidden_contact)).getText().toString();
					String email = ((TextView) view.findViewById(R.id.hidden_email)).getText().toString();
					String offers = ((TextView) view.findViewById(R.id.hidden_offers)).getText().toString();
					String brand = ((TextView) view.findViewById(R.id.hidden_brands)).getText().toString();
					String shop_fb = ((TextView) view.findViewById(R.id.hidden_fb)).getText().toString();
					String shop_twitter = ((TextView) view.findViewById(R.id.hidden_twitter)).getText().toString();
					String shop_gplus = ((TextView) view.findViewById(R.id.hidden_gplus)).getText().toString();					
					String description = ((TextView) view.findViewById(R.id.hidden_desc)).getText().toString();
	                String imageURL=((TextView) view.findViewById(R.id.hidden_imageurl)).getText().toString();
					
											// Starting single contact activity
					Intent in = new Intent(getActivity().getApplicationContext(),SingleShop.class);
					in.putExtra(Search.SHOP_NAME, name);
					in.putExtra(Search.MALL, mall);
					in.putExtras(sending_image);//image
					in.putExtra(Search.SHOP_NO,shop_no);
					in.putExtra(Search.RATING, rating);
					in.putExtra(Search.FLOOR,floor);
					
					*//**hidden*//*
					in.putExtra(Search.DESC, description);
	                in.putExtra("url", imageURL);
					in.putExtra(Search.ID, shop_id);
					in.putExtra(Search.CONTACT, contact_no);
					in.putExtra(Search.EMAIL, email);
					in.putExtra(Search.OFFERS, offers);
					in.putExtra(Search.BRAND, brand);
					in.putExtra(Search.FB, shop_fb);
					in.putExtra(Search.TWITTER, shop_twitter);
					in.putExtra(Search.GPLUS, shop_gplus);
					
					
					startActivity(in);


				}
			});

			// Past the adapter class to the ListView and ListActivity
			// setListAdapter(adapter);
		}

	}
*/}
