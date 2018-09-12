package com.sherman.smartlock.RevMsgNotification;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;

import com.sherman.smartlock.ui.common.MsgSoundUtil;

public abstract class MsgDlgInterface {
	protected Context mContext;
	protected MsgSoundUtil mSounder;
	
	MsgDlgInterface(Context context) {
		mContext = context; 
		mSounder = new MsgSoundUtil(mContext);
	}
    abstract public void showDlg(final long command, String sms, ArrayList<String> paramsArray) ;
    abstract public void playSound(final long command);
    
    protected AlertDialog show(AlertDialog.Builder builder) {
        AlertDialog ad = builder.create();  
        ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);  
        ad.setCanceledOnTouchOutside(false); //����������򲻻���dialog��ʧ  
        ad.show();
        return ad;
    }
}
