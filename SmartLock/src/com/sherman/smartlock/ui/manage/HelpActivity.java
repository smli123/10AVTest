package com.sherman.smartlock.ui.manage;

import com.sherman.smartlock.ui.common.TitledActivity;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class HelpActivity extends TitledActivity implements OnClickListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_help, false);
		SmartPlugApplication.getInstance().addActivity(this);
		
		setTitleLeftButton(R.string.smartplug_goback, R.drawable.title_btn_selector, this);
		setTitle(R.string.app_help);
		
		WebView webview_help = (WebView)findViewById(R.id.webview_help);

		webview_help.loadUrl("file:///android_asset/help.htm");
	}

	@Override
	public void onClick(View v) {
		if (R.id.titlebar_leftbutton == v.getId()) {
			finish();
		}
	}
	
}
