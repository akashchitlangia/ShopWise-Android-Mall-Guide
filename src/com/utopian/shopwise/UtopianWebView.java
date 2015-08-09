package com.utopian.shopwise;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;


public class UtopianWebView extends ActionBarActivity{

	
	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.utopian_webview);

		ActionBar actionbar=getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.utopiansolutions.co.in");
 
	}
}
