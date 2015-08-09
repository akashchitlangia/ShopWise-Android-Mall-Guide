package com.utopian.shopwise.map.ti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import com.utopian.shopwise.R;

public class MainActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_ti);

		
		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setOnPageChangeListener(onPageChangeListener);
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		addActionBarTabs();
	}

	private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			actionBar.setSelectedNavigationItem(position);
		}
	};

	private void addActionBarTabs() {
		actionBar = getSupportActionBar();
		String[] tabs = { "GROUND", "FIRST", "SECOND" };
		for (String tabTitle : tabs) {
			ActionBar.Tab tab = actionBar.newTab().setText(tabTitle)
					.setTabListener(tabListener);
			actionBar.addTab(tab);
		}
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			viewPager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event )

	{
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	
	 Intent intent = new Intent(getApplicationContext(),com.utopian.shopwise.MainActivity.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    intent.putExtra("EXIT", true);
	    startActivity(intent);
	    finish();
	    }
	    return false;
		
	};
}
