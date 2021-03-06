package com.sherman.smartlock.processhandler;

import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.common.PubFunc;
import com.sherman.smartlock.ui.login.LoginActivity;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;
import android.content.Intent;
import android.os.Message;

public class SmartLockEventHandlerLogout extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.LOGOUT_BROADCAST);	
	
	@Override
	public void handleMessage(Message msg) {
		String[] buffer = (String[]) msg.obj;
		
		int ret = PubFunc.hexStringToAlgorism(buffer[EVENT_MESSAGE_HEADER+0]);
		mIntent.putExtra("LOGOUT", ret);
    	SmartPlugApplication.getContext().sendBroadcast(mIntent);
	}
}
