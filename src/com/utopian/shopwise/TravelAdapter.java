package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aphidmobile.utils.AphidLog;
import com.aphidmobile.utils.IO;
import com.aphidmobile.utils.UI;

public class TravelAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	private int repeatCount = 1;

	private List<Travels.Data> travelData;

	public TravelAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
	}

	@Override
	public int getCount() {
		return travelData.size() * repeatCount;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = convertView;
		if (convertView == null) {
			layout = inflater.inflate(R.layout.mall_flip, null);
			AphidLog.d("created new view from adapter: %d", position);
		}

		final Travels.Data data = travelData.get(position % travelData.size());

		/*  UI
        .<TextView>findViewById(layout, R.id.title)
        .setText(AphidLog.format("%d. %s", position, data.title));*/








		/*imp part


		 */






		UI
		.<ImageView>findViewById(layout, R.id.imageView1)
		.setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename1));
		UI
		.<ImageView>findViewById(layout, R.id.imageView2)
		.setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename2));

		UI
		.<TextView>findViewById(layout, R.id.textView1)
		.setText(Html.fromHtml(data.mall_name1));
		UI
		.<TextView>findViewById(layout, R.id.textView2)
		.setText(Html.fromHtml(data.mall_name2));


		UI
		.<TextView>findViewById(layout, R.id.textView3)
		.setText(Html.fromHtml(data.address1));
		UI
		.<TextView>findViewById(layout, R.id.textView4)
		.setText(Html.fromHtml(data.address2));

		UI
		.<RatingBar>findViewById(layout, R.id.ratingBar1)
		.setRating(Float.parseFloat(data.rating1));
		//.setText(Html.fromHtml(data.address1));
		UI
		.<RatingBar>findViewById(layout, R.id.ratingBar2)
		.setRating(Float.parseFloat(data.rating2));
		//.setText(Html.fromHtml(data.address2));




		/*buttons*/  
		UI
		.<Button>findViewById(layout, R.id.button1)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(inflater.getContext(),(Class<? extends Activity>)data.name); 
				intent.putExtra("mall", data.mall_name1);
				intent.putExtra("rating", data.rating1);
				intent.putExtra("address", data.address1);
				//  intent.putExtra("no", data.no1);
				//  intent.putExtra("fb", data.fb1);


				/* Bitmap image= UI
	    		    .<ImageView>findViewById(, R.id.imageView2).getDrawingCache();
			Bundle sending_image = new Bundle();
			sending_image.putParcelable("image", image);


	        intent.putExtras(sending_image);
				 */   inflater.getContext().startActivity(intent);	
			}
		});


		UI
		.<Button>findViewById(layout, R.id.button2)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(inflater.getContext(),(Class<? extends Activity>)data.name); 


				intent.putExtra("mall", data.mall_name2);
				intent.putExtra("rating", data.rating2);
				intent.putExtra("address", data.address2);
				//  startActivity(intent);

				inflater.getContext().startActivity(intent);	
			}
		});

		return layout;
	}

	public void removeData(int index) {
		if (travelData.size() > 1) {
			travelData.remove(index);
		}
	}
}
