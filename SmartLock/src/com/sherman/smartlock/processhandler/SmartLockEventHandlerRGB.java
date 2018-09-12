package com.sherman.smartlock.processhandler;

import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.common.PubFunc;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;

import android.content.Intent;
import android.os.Message;

public class SmartLockEventHandlerRGB extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.PLUG_RGB_ACTION);
	@Override
	public void handleMessage(Message msg) {
		String[] buffer = (String[]) msg.obj;
		int ret = PubFunc.hexStringToAlgorism(buffer[EVENT_MESSAGE_HEADER+0]);
		
		if (0 == ret) {
			mIntent.putExtra("RESULT", 0);
		} else {
	    	mIntent.putExtra("RESULT", ret);
	    	mIntent.putExtra("MESSAGE", 
    				SmartPlugApplication.getContext().getString(R.string.smartplug_oper_light_fail));	    	
	    	/*int resid = AppServerReposeDefine.getServerResponse(ret);
	    	if (0 != resid) {
	    		mIntent.putExtra("MESSAGE", SmartPlugApplication.getContext().getString(resid));
	    	} else {
	    		mIntent.putExtra("MESSAGE", 
	    				SmartPlugApplication.getContext().getString(R.string.smartplug_oper_light_fail));
	    	}*/
	    }
		SmartPlugApplication.getContext().sendBroadcast(mIntent);
		
	}
}
