package com.sherman.smartlockex.processhandler;

import com.sherman.smartlockex.R;
import com.sherman.smartlockex.ui.common.PubDefine;
import com.sherman.smartlockex.ui.common.PubFunc;
import com.sherman.smartlockex.ui.smartlockex.AppServerReposeDefine;
import com.sherman.smartlockex.ui.smartlockex.SmartLockApplication;

import android.content.Intent;
import android.os.Message;

public class SmartLockEventHandlerLight extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.PLUG_LIGHT_ACTION);
	@Override
	public void handleMessage(Message msg) {
		String[] buffer = (String[]) msg.obj;
		try{
			int code = PubFunc.hexStringToAlgorism(buffer[EVENT_MESSAGE_HEADER+0]);
			if (2 > buffer.length) {
		    	mIntent.putExtra("RESULT", code);
		    	mIntent.putExtra("MESSAGE", 
		    				SmartLockApplication.getContext().getString(R.string.smartlock_oper_light_fail));
		    	SmartLockApplication.getContext().sendBroadcast(mIntent);
				return;
			}			
			
			int status = Integer.parseInt(buffer[EVENT_MESSAGE_HEADER+1]);
			
			if (0 == code) {
				mIntent.putExtra("RESULT", 0);
				mIntent.putExtra("STATUS", status);
		    	
			} else {
		    	mIntent.putExtra("RESULT", code);
		    	mIntent.putExtra("STATUS", status);
		    	int resid = AppServerReposeDefine.getServerResponse(code);
		    	if (0 != resid) {
		    		mIntent.putExtra("MESSAGE", SmartLockApplication.getContext().getString(resid));
		    	} else {
		    		mIntent.putExtra("MESSAGE", 
		    				SmartLockApplication.getContext().getString(R.string.smartlock_oper_light_fail));
		    	}
		    }
			SmartLockApplication.getContext().sendBroadcast(mIntent);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
