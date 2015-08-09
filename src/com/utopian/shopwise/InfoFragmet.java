package com.utopian.shopwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFragmet extends Fragment {
public InfoFragmet(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.info, container, false);
        
       
    /*  //  Button utopian =(Button) rootView.findViewById(R.id.utopian);
        
        
        
       
        utopian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),UtopianWebView.class);
				startActivity(intent); 
		        
				// TODO Auto-generated method stub
				
			}
		});*/
         
        return rootView;
	}
	
	
	
}
