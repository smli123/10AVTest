package com.lishimin.memo;


import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener, TextToSpeech.OnInitListener {
	private RelativeLayout rl_total;
	private ImageView iv_confiuration;
	private ImageView iv_status;
	private AutoFitTextView tv_show_memo;
	private String str_car_card = "";
	
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor editor;
	
	private TextToSpeech tSpeech = null;
	private boolean b_speech = true;
	
	private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        //全屏    
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,      
                       WindowManager.LayoutParams. FLAG_FULLSCREEN);
        
        // 常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
        setContentView(R.layout.activity_main);
        
		mSharedPreferences = getSharedPreferences("MEMO", Activity.MODE_PRIVATE);
		
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        
		loadData();
		
        init();
        
        updateUI();
        
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
			@Override
			public void run() {
				ReadInfo();
			}
        };
        
        timer.schedule(task, 1000, 2000);
    }
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = tSpeech.setLanguage(Locale.CHINA);	
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(getApplicationContext(), "Language is not available.", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "init failed", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		tSpeech = new TextToSpeech(getApplicationContext(), this);
		if (tSpeech == null) {
			showTip("TSpeech初始化失败！");
		}
	}
    
	@Override
	protected void onPause() {
		super.onPause();
		
		init();
	}

	@Override
	protected void onDestroy() {
		saveData();
		
		if (tSpeech != null) {
			tSpeech.stop();
			tSpeech.shutdown();
		}

		super.onDestroy();
	}
	
	private void showTip(final String str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}
	
	@SuppressWarnings({ "deprecation"})
	private void MySpeech(String command) {
		if (b_speech == true && tSpeech != null) {
			String speech_text = command;
			
			HashMap<String, String> myAlarm = new HashMap();
			//把播放类型,通过闹钟流实现
			myAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
			String.valueOf(AudioManager.STREAM_ALARM));
			tSpeech.speak(speech_text,  TextToSpeech.QUEUE_ADD, null);
		}
	}
	
	private void StopSpeech() {
		if (tSpeech != null) {
			tSpeech.stop();
		}
	}

	private void saveData() {
		editor = mSharedPreferences.edit();
		editor.putString("CAR_CARD", str_car_card);
		
		editor.commit();
	}

	private void loadData() {
		str_car_card = mSharedPreferences.getString("CAR_CARD", "");
	}
    
    private void init() {
    	rl_total = (RelativeLayout) findViewById(R.id.rl_total);
    	tv_show_memo = (AutoFitTextView) findViewById(R.id.tv_show_memo);
    	iv_confiuration = (ImageView) findViewById(R.id.iv_confiuration);
    	iv_status = (ImageView) findViewById(R.id.iv_status);
    	iv_status.setVisibility(View.GONE);
    	
    	rl_total.setOnClickListener(this);
    	tv_show_memo.setOnClickListener(this);
    	iv_status.setOnClickListener(this);
    	iv_confiuration.setOnClickListener(this);
    	
    }
    private void updateUI() {
    	tv_show_memo.setText(str_car_card);
    	
    	if (str_car_card.isEmpty()) {
    		showTip("未设置车牌号信息，请在配置中设置");
//    		Toast.makeText(this, "未设置车牌号信息，请在配置中设置", Toast.LENGTH_LONG).show();
    	}
    }
    
    private void ReadInfo() {
    	MySpeech(str_car_card);
    }
    
    @Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_confiuration :
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,
						ConfigurationActivity.class);
				startActivity(intent);
				
				finish();
				break;
			case R.id.tv_show_memo:
				speech_change();
				break;
			case R.id.iv_status:
				speech_change();
				break;
			case R.id.rl_total:
				speech_change();
				break;
		}
    }
    
    private void speech_change() {
    	b_speech = !b_speech;
		if (b_speech == true) {
			iv_status.setVisibility(View.GONE);
		} else {
			StopSpeech();
			iv_status.setVisibility(View.VISIBLE);
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
}
