package com.utopian.listAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.utopian.imageloader.ImageLoader;
import com.utopian.shopwise.R;
import com.utopian.shopwise.Search;

public class LazyAdapter extends BaseAdapter {

	Context context;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	float rating;

	public LazyAdapter(Context context, ArrayList<HashMap<String, String>> d) {
		this.context = context;
		data = d;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(context.getApplicationContext());
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
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.single_shop_list, null);

		TextView shop_name = (TextView) vi.findViewById(R.id.list_shop_name); // title
		TextView mall = (TextView) vi.findViewById(R.id.list_mall); // artist
																	// name
		TextView floor = (TextView) vi.findViewById(R.id.floor);
		TextView shop_no = (TextView) vi.findViewById(R.id.list_shop_no);
		// duration
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image
		RatingBar rb = (RatingBar) vi.findViewById(R.id.ratingBar1);

		/***
		 * Offer image task reaining
		 ***/
		ImageView offer_image = (ImageView) vi.findViewById(R.id.offer);
		ImageView floor_image = (ImageView) vi.findViewById(R.id.floor_image);

		/****
		 * Hidden fields in view
		 * 
		 ****/
		TextView imageURL = (TextView) vi.findViewById(R.id.hidden_imageurl);
		TextView desc = (TextView) vi.findViewById(R.id.hidden_desc);
		TextView id = (TextView) vi.findViewById(R.id.hidden_id);
		TextView contact_no = (TextView) vi.findViewById(R.id.hidden_contact);
		TextView email = (TextView) vi.findViewById(R.id.hidden_email);
		TextView offers = (TextView) vi.findViewById(R.id.hidden_offers);
		TextView brand = (TextView) vi.findViewById(R.id.hidden_brands);
		TextView shop_fb = (TextView) vi.findViewById(R.id.hidden_fb);
		TextView shop_twitter = (TextView) vi.findViewById(R.id.hidden_twitter);
		TextView shop_gplus = (TextView) vi.findViewById(R.id.hidden_gplus);

		HashMap<String, String> shop = new HashMap<String, String>();
		shop = data.get(position);

		shop_name.setText(shop.get(Search.SHOP_NAME));
		mall.setText(shop.get(Search.MALL));
		floor.setText(shop.get(Search.FLOOR));
		shop_no.setText(shop.get(Search.SHOP_NO));
		desc.setText(shop.get(Search.DESC));
		imageURL.setText(shop.get(Search.THUMB_URL));

		imageLoader.DisplayImage(shop.get(Search.THUMB_URL), thumb_image);
		String RATING = shop.get(Search.RATING);
		rb.setRating(Float.parseFloat(RATING));

		// imageURL.setText(Search.THUMB_URL);
		id.setText(shop.get(Search.ID));
		contact_no.setText(shop.get(Search.CONTACT));
		email.setText(shop.get(Search.EMAIL));
		offers.setText(shop.get(Search.OFFERS));
		brand.setText(shop.get(Search.BRAND));
		shop_fb.setText(shop.get(Search.FB));
		shop_twitter.setText(shop.get(Search.TWITTER));
		shop_gplus.setText(shop.get(Search.GPLUS));

		if (shop.get(Search.SHOP_NO).equalsIgnoreCase("null")) {
			shop_no.setVisibility(View.INVISIBLE);
			vi.findViewById(R.id.text_shop_no).setVisibility(View.INVISIBLE);
		} else {
			shop_no.setVisibility(View.VISIBLE);
			vi.findViewById(R.id.text_shop_no).setVisibility(View.VISIBLE);
		}
		if (shop.get(Search.FLOOR).equalsIgnoreCase("null")) {

			floor.setVisibility(View.INVISIBLE);
			floor_image.setVisibility(View.INVISIBLE);
			vi.findViewById(R.id.text).setVisibility(View.INVISIBLE);
		} else {
			floor.setVisibility(View.VISIBLE);
			vi.findViewById(R.id.text).setVisibility(View.VISIBLE);
			floor_image.setVisibility(View.VISIBLE);

		}
		return vi;
	}
}
