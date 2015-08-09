package com.utopian.shopwise;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aphidmobile.flip.FlipViewController;

public class MallFfragment extends Fragment {
	
	  private FlipViewController flipView;


public MallFfragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_mall, container, false);
     /*  Intent intent = new Intent(getActivity(),MallFlipView.class);
		startActivity(intent); 
       */  
		
		  flipView = new FlipViewController(getActivity());

		    //Use RGB_565 can reduce peak memory usage on large screen device, but it's up to you to choose the best bitmap format 
		    flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);

		    flipView.setAdapter(new TravelAdapter(getActivity()));

		    getActivity().setContentView(flipView);
        
        
        return rootView;
    }
	
	
	
	
}