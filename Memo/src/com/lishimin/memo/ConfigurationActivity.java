package com.lishimin.memo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfigurationActivity  extends Activity implements OnClickListener {
	private EditText et_car_card;
	private String str_car_card = "";
	
	private Button btn_cancel;
	private Button btn_ok;
	
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ÎÞtitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        //È«ÆÁ    
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,      
                       WindowManager.LayoutParams. FLAG_FULLSCREEN);
        
        // ³£ÁÁ
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
        setContentView(R.layout.activity_configuration);
        
		mSharedPreferences = getSharedPreferences("MEMO", Activity.MODE_PRIVATE);
        
		loadData();
		
        init();
        
        updateUI();
    }
    
	@Override
	protected void onPause() {
		super.onPause();
		
		init();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void saveData() {
		str_car_card = et_car_card.getText().toString();
		
		editor = mSharedPreferences.edit();
		editor.putString("CAR_CARD", str_car_card);
		
		editor.commit();
	}

	private void loadData() {
		str_car_card = mSharedPreferences.getString("CAR_CARD", "");
	}

    private void init() {
    	et_car_card = (EditText) findViewById(R.id.et_car_card);
    	btn_cancel =  (Button) findViewById(R.id.btn_cancel);
    	btn_ok =  (Button) findViewById(R.id.btn_ok);
    	
    	btn_cancel.setOnClickListener(this);
    	btn_ok.setOnClickListener(this);
    	
    }
    private void updateUI() {
    	et_car_card.setText(str_car_card);
    	
    }
    
    @Override
	public void onClick(View view) {
    	Intent intent = new Intent();
		switch (view.getId()) {
			case R.id.btn_ok :
				saveData();
				
				intent.setClass(ConfigurationActivity.this,
						MainActivity.class);
				startActivity(intent);
				
				finish();
			case R.id.btn_cancel :
				intent.setClass(ConfigurationActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
				break;
		}
    }
    
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0 :
					break;
				case 1 :
					break;
				default :
					break;
			}
		};
	};
	
   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			btn_cancel.performClick();
			
			return true;
		}
    	return super.onKeyDown(keyCode, event);
    } 
}
