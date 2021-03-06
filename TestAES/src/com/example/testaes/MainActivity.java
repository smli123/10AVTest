package com.example.testaes;

import com.examples.utils.AESUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
	private EditText et_password;
	private EditText et_text;
	private Button btn_encrypt;
	private TextView tv_encrypt_text;
	private Button btn_decrypt;
	private TextView tv_decrypt_text;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }
    
    private void init() {
    	et_password = (EditText) findViewById(R.id.et_password);
    	et_text = (EditText) findViewById(R.id.et_text);
    	btn_encrypt = (Button) findViewById(R.id.btn_encrypt);
    	tv_encrypt_text = (TextView) findViewById(R.id.tv_encrypt_text);
    	btn_decrypt = (Button) findViewById(R.id.btn_decrypt);
    	tv_decrypt_text = (TextView) findViewById(R.id.tv_decrypt_text);
    	
    	btn_encrypt.setOnClickListener(this);
    	btn_decrypt.setOnClickListener(this);
    	
    }
    
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_encrypt:
			encrypt();
			break;
		case R.id.btn_decrypt:
			decrypt();
			break;
		default:
			break;
		}
	}

	private void encrypt() {
		String password = et_password.getText().toString();
		String text = et_text.getText().toString();
		
		String after_text = AESUtil.encrypt(password, text);
		tv_encrypt_text.setText(after_text);
		
	}
	
	private void decrypt() {
		String password = et_password.getText().toString();
		String text = tv_encrypt_text.getText().toString();
		
		String after_text = AESUtil.decrypt(password, text);
		tv_decrypt_text.setText(after_text);
	}
}
