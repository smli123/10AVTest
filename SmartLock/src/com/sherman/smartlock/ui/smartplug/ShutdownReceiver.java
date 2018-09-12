package com.sherman.smartlock.ui.smartplug;

import com.sherman.smartlock.ui.common.PubDefine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ShutdownReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
			//PubDefine.disconnect();	
		}
	}
}
