package com.utopian.listAdapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.utopian.shopwise.R;
import com.utopian.shopwise.ReviewFragment;

public class CommentsLazyAdapter extends BaseAdapter {
	Context context;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater=null;
	//public ImageLoader imageLoader; 

	float rating;

	public CommentsLazyAdapter( Context context, ArrayList<HashMap<String, String>> d) {
		this.context = context;
		data=d;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//imageLoader=new ImageLoader(context.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.single_review, null);

		TextView tittle = (TextView)vi.findViewById(R.id.tittle); // title
		TextView message = (TextView)vi.findViewById(R.id.message); // artist name
		TextView username = (TextView)vi.findViewById(R.id.user_name);
		//TextView shop_no = (TextView)vi.findViewById(R.id.list_shop_no);
		// duration
		//ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
		RatingBar rb= (RatingBar)vi.findViewById(R.id.ratingBar1);
		
		/***
		 * Offer image task reaining
		 * **/
		//ImageView offer_image=(ImageView)vi.findViewById(R.id.offer); 

		
		
		
		/****
		 * Hidden fields in view
		 * 
		 * */
		//TextView desc = (TextView)vi.findViewById(R.id.hidden_desc);
		
		
		

		HashMap<String, String> shop = new HashMap<String, String>();
		shop = data.get(position);
		tittle.setText(shop.get(ReviewFragment.TITLE));
		message.setText(shop.get(ReviewFragment.REVIEW));
		username.setText(shop.get(ReviewFragment.USERNAME));
		//shop_no.setText(shop.get(Search.SHOP_NO));
		//desc.setText(shop.get(Search.DESC));

		//imageLoader.DisplayImage(shop.get(Search.THUMB_URL), thumb_image);
		String RATING = shop.get(ReviewFragment.RATING);
		rb.setRating( Float.parseFloat(RATING));
		
		return vi;
}
}
