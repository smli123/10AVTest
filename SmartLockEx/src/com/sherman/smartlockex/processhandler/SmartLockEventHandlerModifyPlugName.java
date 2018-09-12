package com.sherman.smartlockex.processhandler;

import com.sherman.smartlockex.R;
import com.sherman.smartlockex.ui.common.PubDefine;
import com.sherman.smartlockex.ui.common.PubFunc;
import com.sherman.smartlockex.ui.smartlockex.AppServerReposeDefine;
import com.sherman.smartlockex.ui.smartlockex.SmartLockApplication;

import android.content.Intent;
import android.os.Message;

public class SmartLockEventHandlerModifyPlugName extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.PLUG_MODIFY_PLUGNAME);
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		String[] buffer = (String[]) msg.obj;
		try{
			int ret = PubFunc.hexStringToAlgorism(buffer[EVENT_MESSAGE_HEADER+0]);
			
			if (0 == ret) {
		    	mIntent.putExtra("RESULT", 0);
		    	SmartLockApplication.getContext().sendBroadcast(mIntent);
			} else {
		    	mIntent.putExtra("RESULT", 1);
		    	int resid = AppServerReposeDefine.getServerResponse(ret);
		    	if (0 != resid) {
		    		mIntent.putExtra("MESSAGE", SmartLockApplication.getContext().getString(resid));
		    	} else {
		    		mIntent.putExtra("MESSAGE", 
		    				SmartLockApplication.getContext().getString(R.string.smartlock_ctrl_modifyplugname_fail));
		    	}
		    	SmartLockApplication.getContext().sendBroadcast(mIntent);	    	
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
