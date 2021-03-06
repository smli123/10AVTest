package com.sherman.smartlock.processhandler;

import android.content.Intent;
import android.os.Message;

import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.smartplug.PubStatus;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;

public class SmartLockEventHandlerNotifyPower extends SmartLockEventHandler {
	Intent mIntent = new Intent(PubDefine.PLUG_NOTIFY_POWER);
	@Override
	public void handleMessage(Message msg) {
		String[] buffer = (String[]) msg.obj;
		
		int power = Integer.parseInt(buffer[EVENT_MESSAGE_HEADER+1]);
		mIntent.putExtra("PLUGID", PubStatus.g_moduleId);
		mIntent.putExtra("POWER", power);
		SmartPlugApplication.getContext().sendBroadcast(mIntent);
	}
}
