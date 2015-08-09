package com.utopian.shopwise;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.utopian.adapter.NavDrawerListAdapter;
import com.utopian.model.NavDrawerItem;

/*
 import android.app.Fragment;
 import android.app.FragmentManager;*/

public class MainActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	SharedPreferences shared;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
    Boolean exit;
    Intent in;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// ActionBar actionBar = getSupportActionBar();
		// actionBar.setBackgroundDrawable(new
		// ColorDrawable(R.drawable.list_item_bg_pressed));

		in=getIntent();
		exit=in.getBooleanExtra("EXIT", true);
	
		
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
				.getResourceId(8, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons
				.getResourceId(9, -1)));

		/*navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons
				.getResourceId(10, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons
				.getResourceId(11, -1)));*/

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		ActionBar actionBar = getSupportActionBar();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setDisplayShowHomeEnabled(true);

		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name
		// nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				// invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				// invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.share:
			Intent i = new Intent(android.content.Intent.ACTION_SEND);

			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_SUBJECT, "ShopWise");
			i.putExtra(
					Intent.EXTRA_TEXT,
					"Hi, I tried ShopWise android app,I recommend you to try it once, Follow the link to install it from Google Play Market..Install ShopWise-Android App");
			startActivity(Intent.createChooser(i, "Share using"));

			return true;
		case R.id.logout:
			shared = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			Editor editor = shared.edit();

			editor.clear();
			editor.commit();

			Intent i1 = new Intent(MainActivity.this, Login.class);

			startActivity(i1);
			finish();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments

		shared = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		// getSupportActionBar().removeAllTabs();
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment() {

				@Override
				public void onClick(View v) {

				}
			};
			break;
	/*	case 1:
			if (shared.contains("username")) {
				fragment = new FavoriteFragment();
			} else {
				fragment = new HomeFragment() {

					@Override
					public void onClick(View v) {

					}
				};
				Toast.makeText(MainActivity.this,
						"Please Login to use this feature", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case 2:

			if (shared.contains("username")) {
				fragment = new RecentFragment();
			} else {
				fragment = new HomeFragment() {

					@Override
					public void onClick(View v) {

					}
				};
				Toast.makeText(MainActivity.this,
						"Please Login to use this feature", Toast.LENGTH_SHORT)
						.show();
			}
			break;*/
		case 1:
			fragment = new MallFfragment();

			break;
		case 2:
			if (shared.contains("username")) {
				fragment = new Offer();
			} else {
				fragment = new HomeFragment() {

					@Override
					public void onClick(View v) {

					}
				};
				Toast.makeText(MainActivity.this,
						"Please Login to use this feature", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case 3:
			fragment = new StoreFragment();
			break;

		case 4:
			fragment = new TopRatedFragment();
			break;
		case 5:
			if (shared.contains("username")) {
				fragment = new ShoppingFragment();
			} else {
				fragment = new HomeFragment() {

					@Override
					public void onClick(View v) {

					}
				};
				Toast.makeText(MainActivity.this,
						"Please Login to use this feature", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case 6:
			fragment = new ReviewFragment();
			break;

		case 7:
			if (shared.contains("username")) {
				fragment = new ProfileFragment();
			} else {
				fragment = new HomeFragment() {

					@Override
					public void onClick(View v) {

					}
				};
				Toast.makeText(MainActivity.this,
						"Please Login to use this feature", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case 8:

			fragment = new MapFragment();

			break;
		case 9:

			fragment = new InfoFragmet();

			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	/*
	 * @Override public void onBackPressed() { final Fragment fragment =
	 * (Fragment)
	 * getSupportFragmentManager().findFragmentById(R.id.frame_container);
	 * 
	 * if (fragment.allowBackPressed()) { // and then you define a method
	 * allowBackPressed with the logic to allow back pressed or not
	 * super.onBackPressed(); } }
	 */
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

/*	private boolean doubleBackToExitPressedOnce = false;

	*/
	private boolean doubleBackToExitPressedOnce=false;;

	@Override
	protected void onResume() {
	   if(doubleBackToExitPressedOnce)
		{
		   Toast.makeText(getApplicationContext(), "sbhdfjas", Toast.LENGTH_SHORT).show();
		   finish();
		 // .... other stuff in my onResume ....
		}
	   else{
		   super.onResume();		     
	   }
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage("Do you wish to exit? ");
			builder.setCancelable(false);
			builder.setPositiveButton(" Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MainActivity.this.finish();

							//
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();

						}
					});

			AlertDialog alert = builder.create();
			alert.show();

			

		}
		return false;
	}
}
