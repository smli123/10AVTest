package com.sherman.smartlock.processhandler;

import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.common.PubFunc;
import com.sherman.smartlock.ui.smartplug.AppServerReposeDefine;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;

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
		    	SmartPlugApplication.getContext().sendBroadcast(mIntent);
			} else {
		    	mIntent.putExtra("RESULT", 1);
		    	int resid = AppServerReposeDefine.getServerResponse(ret);
		    	if (0 != resid) {
		    		mIntent.putExtra("MESSAGE", SmartPlugApplication.getContext().getString(resid));
		    	} else {
		    		mIntent.putExtra("MESSAGE", 
		    				SmartPlugApplication.getContext().getString(R.string.smartplug_ctrl_modifyplugname_fail));
		    	}
		    	SmartPlugApplication.getContext().sendBroadcast(mIntent);	    	
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
