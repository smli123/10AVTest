package com.sherman.smartlockex.processhandler;

import android.content.Intent;
import android.os.Message;

import com.sherman.smartlockex.R;
import com.sherman.smartlockex.ui.common.PubDefine;
import com.sherman.smartlockex.ui.common.PubFunc;
import com.sherman.smartlockex.ui.common.PubStatus;
import com.sherman.smartlockex.ui.smartlockex.AppServerReposeDefine;
import com.sherman.smartlockex.ui.smartlockex.SmartLockApplication;

public class SmartLockEventHandlerNotifyOnline extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.PLUG_NOTIFY_ONLINE);
	@Override
	public void handleMessage(Message msg) {
		String[] buffer = (String[]) msg.obj;
	
		int online = Integer.parseInt(buffer[EVENT_MESSAGE_HEADER+1]);
		mIntent.putExtra("PLUGID", PubStatus.g_moduleId);
		mIntent.putExtra("ONLINE", online);
		SmartLockApplication.getContext().sendBroadcast(mIntent);
	}
}
