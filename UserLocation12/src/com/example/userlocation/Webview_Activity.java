package com.example.userlocation;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview_Activity extends Activity {

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_);
	
		
		String url = "https://en.wikipedia.org/wiki/"+MainActivity.city;
		WebView view = (WebView) this.findViewById(R.id.webView1);
		view.getSettings().setJavaScriptEnabled(true);
		view.setWebViewClient(new myWebClient());
		view.loadUrl(url);
		
	}
	
	
	public class myWebClient extends WebViewClient 
	{

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}		
		
	}
}
