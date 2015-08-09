/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with trinea.cn.
 */
package com.utopian.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.utopian.imageloader.ImageLoader;
import com.utopian.shopwise.SingleShopOffer;



import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import cn.trinea.android.common.util.ListUtils;

/**
 * ImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapterForNew extends PagerAdapter {

    private Context       context;
  //  private List<Integer> imageIdList;
    
    ArrayList<HashMap<String, String>> imageIdList;

    
	//public ImageLoader imageLoader; 


    public ImagePagerAdapterForNew(Context context, ArrayList<HashMap<String, String>> imageIdList){
        this.context = context;
        this.imageIdList = imageIdList;
		//imageLoader=new ImageLoader(context.getApplicationContext());

    }

    @Override
    public int getCount() {
        return ListUtils.getSize(imageIdList);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
     /*   ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIdList.get(position));
        ((ViewPager)container).addView(imageView, 0);
        
        
        
     */ 
    	// ImageView imageView = new ImageView(context);
    	
    	STGVImageView imageView = new STGVImageView(context);
       //  imageView.setImageResource(imageIdList.get(position));
         ((ViewPager)container).addView(imageView, 0);
         
         imageView.mHeight = 500;
         imageView.mWidth = 1000;

         


			HashMap<String, String> shop = new HashMap<String, String>();
			shop = imageIdList.get(position);
			final String shop_id = shop.get("shop_id");
			
	         Picasso.with(context.getApplicationContext()).load(shop.get("image_url")).into(imageView);


		//	imageLoader.DisplayImage(shop.get("image_url"), imageView);
         
		//imageLoader.DisplayImage(imageIdList.get(position), imageView);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
		//	Toast.makeText(context, ""+position,Toast.LENGTH_LONG).show();
				
				
				Intent i = new Intent(context,SingleShopOffer.class);
				i.putExtra("shop_id",shop_id);
				context.startActivity(i);

			}
		});

    	
    	return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }
}












/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with trinea.cn.
 
package com.utopian.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.utopian.imageloader.ImageLoader;
import com.utopian.shopwise.Search;
import com.utopian.shopwise.SingleShopOffer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import cn.trinea.android.common.util.ListUtils;

*//**
 * ImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 *//*
public class ImagePagerAdapter extends PagerAdapter {

    private Context       context;
  //  private List<Integer> imageIdList;
    
    ArrayList<HashMap<String, String>> imageIdList;

    
	public ImageLoader imageLoader; 


    public ImagePagerAdapter(Context context, ArrayList<HashMap<String, String>> imageIdList){
        this.context = context;
        this.imageIdList = imageIdList;
		imageLoader=new ImageLoader(context.getApplicationContext());

    }

    @Override
    public int getCount() {
        return ListUtils.getSize(imageIdList);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIdList.get(position));
        ((ViewPager)container).addView(imageView, 0);
        
        
        
      
    	 ImageView imageView = new ImageView(context);
       //  imageView.setImageResource(imageIdList.get(position));
         ((ViewPager)container).addView(imageView, 0);
         


			HashMap<String, String> shop = new HashMap<String, String>();
			shop = imageIdList.get(position);
			final String shop_id = shop.get("shop_id");

			imageLoader.DisplayImage(shop.get("image_url"), imageView);
         
		//imageLoader.DisplayImage(imageIdList.get(position), imageView);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//Toast.makeText(context, ""+position,Toast.LENGTH_LONG).show();
				
				
				Intent i = new Intent(context,SingleShopOffer.class);
				i.putExtra("shop_id",shop_id);
				context.startActivity(i);

			}
		});

    	
    	return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }
}
*/