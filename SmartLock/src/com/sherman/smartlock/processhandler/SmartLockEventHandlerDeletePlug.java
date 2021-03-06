package com.sherman.smartlock.processhandler;

import android.content.Intent;
import android.os.Message;

import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.common.PubFunc;
import com.sherman.smartlock.ui.smartplug.AppServerReposeDefine;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;

public class SmartLockEventHandlerDeletePlug extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.PLUG_DELETE);
	
	@Override
	public void handleMessage(Message msg) {
		String[] buffer = (String[]) msg.obj;
		
		int ret = PubFunc.hexStringToAlgorism(buffer[EVENT_MESSAGE_HEADER+0]);
		
		if (0 == ret) {
	    	mIntent.putExtra("RESULT", 0);
	    	SmartPlugApplication.getContext().sendBroadcast(mIntent);
		} else {
	    	mIntent.putExtra("RESULT", 1);
	    	int resid = AppServerReposeDefine.getServerResponse(ret);
	    	if (0 == resid) {
	    		mIntent.putExtra("MESSAGE", 
	    				SmartPlugApplication.getContext().getString(R.string.smartplug_ctrl_delete_fail));
	    	} else {
	    		mIntent.putExtra("MESSAGE", 
	    				SmartPlugApplication.getContext().getString(resid));
	    	}
	    	SmartPlugApplication.getContext().sendBroadcast(mIntent);	    	
	    }		
	}
}
