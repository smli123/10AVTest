package com.sherman.smartlock.util;

import java.util.TimerTask;

import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;

public class AppTimeoutTask extends TimerTask{

	@Override
	public void run() {
		SmartPlugApplication.setLogined(false);
		SmartPlugApplication.getInstance().exit();
	}

}