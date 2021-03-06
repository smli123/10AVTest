package com.sherman.smartlock.processhandler;

import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.common.PubFunc;
import com.sherman.smartlock.ui.smartplug.AppServerReposeDefine;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;

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
		    				SmartPlugApplication.getContext().getString(R.string.smartplug_oper_light_fail));
		    	SmartPlugApplication.getContext().sendBroadcast(mIntent);
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
		    		mIntent.putExtra("MESSAGE", SmartPlugApplication.getContext().getString(resid));
		    	} else {
		    		mIntent.putExtra("MESSAGE", 
		    				SmartPlugApplication.getContext().getString(R.string.smartplug_oper_light_fail));
		    	}
		    }
			SmartPlugApplication.getContext().sendBroadcast(mIntent);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
