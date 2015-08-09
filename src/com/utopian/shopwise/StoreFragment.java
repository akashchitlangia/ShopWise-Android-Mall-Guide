package com.utopian.shopwise;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.utopian.customgridview.CustomGridViewAdapter;
import com.utopian.customgridview.Item;

public class StoreFragment extends Fragment {
	
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	 CustomGridViewAdapter customGridAdapter;
	
public StoreFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.categories, container, false);
   
        
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        
		Bitmap mens = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_mens);
		Bitmap womens = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_women);
		
		Bitmap kids = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_kids);
		
		
		Bitmap footwear = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_footware);
		
		Bitmap accessories = BitmapFactory.decodeResource(this.getResources(), R.drawable.acce_100);
		Bitmap electronics = BitmapFactory.decodeResource(this.getResources(), R.drawable.elec_100);
		Bitmap sportswear = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_sports);
		Bitmap entertainment = BitmapFactory.decodeResource(this.getResources(), R.drawable.enter_100);
		Bitmap restaurants = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_dining);
		Bitmap bar = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_bar);
		Bitmap department = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_dept);
		Bitmap salon = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_salon);
		Bitmap spa = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_spa);
		Bitmap other = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_other);
		Bitmap health = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_health);

		
		
		
		
		
		gridArray.add(new Item(mens,"Men's"));
		gridArray.add(new Item(womens,"Women's"));
		gridArray.add(new Item(kids,"Kids"));
		gridArray.add(new Item(restaurants,"Restraunts"));

	
		gridArray.add(new Item(accessories,"Accessories"));
		gridArray.add(new Item(footwear,"Foot wear"));
		gridArray.add(new Item(entertainment,"Entertainment"));
		gridArray.add(new Item(electronics,"Electronics"));
		gridArray.add(new Item(sportswear,"Sports wear"));
		gridArray.add(new Item(salon,"Salon"));
		gridArray.add(new Item(spa,"Spa"));
		
		gridArray.add(new Item(health,"Heath"));
		gridArray.add(new Item(bar,"Bar"));
		gridArray.add(new Item(department,"Departmental"));
		gridArray.add(new Item(other,"Other"));
		
		
		// selection=(TextView)findViewById(R.id.textView1);
		
		
		gridView = (GridView)rootView.findViewById(R.id.category_grid);
		customGridAdapter = new CustomGridViewAdapter(getActivity(), R.layout.customgridcell,gridArray);
		gridView.setAdapter(customGridAdapter);
		
		
		Animation anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fly_in_from_top_corner);
        gridView.setAnimation(anim);
        anim.start();	
		
		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			//	selection.setText(items[position]);
				
				
				switch (position)
				{
				
				case 0:
					Intent intent0 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent0.putExtra("category", "m");
					startActivity(intent0);
					break;
				case 1:
					Intent intent1 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent1.putExtra("category", "wo");
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent2.putExtra("category", "k");
					startActivity(intent2);					break;
					
				case 3:
					Intent intent3 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent3.putExtra("category", "r");
					startActivity(intent3);					break;
				case 4:
					Intent intent4 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent4.putExtra("category", "a");
					startActivity(intent4);					break;
				case 5:
					Intent intent5 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent5.putExtra("category", "f");
					startActivity(intent5);					break;
				case 6:
					Intent intent6 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent6.putExtra("category", "en");
					startActivity(intent6);					break;
				case 7:
					Intent intent7 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent7.putExtra("category", "el");
					startActivity(intent7);					break;
				case 8:
					Intent intent8 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent8.putExtra("category", "sw");
					startActivity(intent8);					break;
				case 9:
					Intent intent9 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent9.putExtra("category", "bar");
					startActivity(intent9);					break;
				case 10:
					Intent intent10 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent10.putExtra("category", "d");
					startActivity(intent10);					break;
				case 11:
					Intent intent11 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent11.putExtra("category", "ot");
					startActivity(intent11);					break;
				case 12:
					Intent intent12 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent12.putExtra("category", "spa");
					startActivity(intent12);					break;
				case 13:
					Intent intent13 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent13.putExtra("category", "saloon");
					startActivity(intent13);					break;
				case 14:
					Intent intent14 = new Intent(getActivity().getApplicationContext(),SearchCategory.class);
					intent14.putExtra("category", "h");
					startActivity(intent14);					break;
					
				
					
				
				
				}
				
			}
		});	
		
		
		
		
        return rootView;
    }
}