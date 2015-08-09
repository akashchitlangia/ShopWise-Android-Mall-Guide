package com.utopian.shopwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utopian.shopwise.map.ti.MainActivity;

public class MapFragment extends Fragment {
	
	public MapFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
        Intent intent = new Intent(getActivity(),MainActivity.class);
      		startActivity(intent); 
      		//getActivity().finish();
         
        return rootView;
    }
}
