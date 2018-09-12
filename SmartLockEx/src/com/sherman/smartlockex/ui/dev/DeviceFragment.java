package com.sherman.smartlockex.ui.dev;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sherman.smartlockex.ui.common.SmartLockFragment;
import com.sherman.smartlockex.ui.smartlockex.SmartLockApplication;
import com.sherman.smartlockex.ui.util.MyAlertDialog;
import com.sherman.smartlockex.ui.util.RefreshableView;
import com.sherman.smartlockex.ui.util.RefreshableView.PullToRefreshListener;
import com.sherman.smartlockex.R;

public class DeviceFragment extends SmartLockFragment
		implements
			View.OnClickListener {
	private ListView mDevList = null;

	private String mFocusPlugId = "0";
	private boolean mFocusPlugPower = false;

	private RefreshableView mRefreshableView = null;

	private static DeviceFragment mFragment = null;

	private String mNewPlugName = "";
	private MyAlertDialog mModifyDlg = null;

	public static DeviceFragment newInstance() {
		if (null == mFragment) {
			mFragment = new DeviceFragment();
		}
		return mFragment;
	}

	public static void delete() {
		if (null != mFragment) {
			mFragment = null;
		}
	}

	private BroadcastReceiver mLoadPlugReveiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (null != mProgress) {
				mProgress.dismiss();
			}
//			if (intent.getAction().equals(PubDefine.PLUG_POWER_ACTION)
//					|| intent.getAction().equals(PubDefine.PLUG_LIGHT_ACTION)
//					|| intent.getAction().equals(PubDefine.PLUG_USB_ACTION)) {
//				timeoutHandler.removeCallbacks(timeoutProcess);
//				int code = intent.getIntExtra("RESULT", 0);
//				int status = intent.getIntExtra("STATUS", 0);
//				String message = intent.getStringExtra("MESSAGE");
//				switch (code) {
//					case 0 :
//						SmartPlugDefine plug = mPlugHelper
//								.getSmartPlug(mFocusPlugId);
//						if (null != plug) {
//							plug.mDeviceStatus = status;
//							if (0 < mPlugHelper.modifySmartPlug(plug)) {
//								doBackgroundLoad();
//							}
//						}
//
//						break;
//					default :
//						PubFunc.thinzdoToast(mContext, message);
//						break;
//				}
//			}
//
//			if (intent.getAction().equals(PubDefine.PLUG_UPDATE)) {
//				timeoutHandler.removeCallbacks(timeoutProcess);
//				int ret = intent.getIntExtra("RESULT", 0);
//				String message = intent.getStringExtra("MESSAGE");
//				if (0 == ret) {
//					mPlugHelper.clearSmartPlug();
//				}
//				if (null != message && !message.isEmpty()) {
//					PubFunc.thinzdoToast(mContext, message);
//				}
//				doBackgroundLoad();
//			}
//
//			if (intent.getAction().equals(PubDefine.PLUG_NOTIFY_POWER)) {
//				if (true == NotifyProcessor.powerNotify(mContext, intent)) {
//					doBackgroundLoad();
//				}
//			}
//
//			if (intent.getAction().equals(PubDefine.PLUG_NOTIFY_ONLINE)) {
//				if (true == NotifyProcessor.onlineNotify(mContext, intent)) {
//					qryPlugsFromServer();
//					// doBackgroundLoad();
//				}
//			}
//
//			if (intent.getAction().equals(PubDefine.PLUG_NOTIFY_LIGHT)) {
//				if (true == NotifyProcessor.lightNotify(mContext, intent)) {
//					doBackgroundLoad();
//				}
//			}
//
//			if (intent.getAction().equals(PubDefine.PLUG_NOTIFY_CURTAIN)) {
//				if (true == NotifyProcessor.curtainNotify(mContext, intent)) {
//					doBackgroundLoad();
//				}
//			}
//
//			if (intent.getAction().equals(PubDefine.PLUG_DELETE)) {
//				timeoutHandler.removeCallbacks(timeoutProcess);
//				int ret = intent.getIntExtra("RESULT", 0);
//				String message = intent.getStringExtra("MESSAGE");
//				switch (ret) {
//					case 0 :
//						if (true == mPlugHelper.deleteSmartPlug(mFocusPlugId)) {
//							doBackgroundLoad();
//						}
//
//						break;
//					default :
//						PubFunc.thinzdoToast(mContext, message);
//						break;
//				}
//			}
//			if (intent.getAction().equals(PubDefine.PLUG_MODIFY_PLUGNAME)) {
//				if (null != mProgress) {
//					mProgress.dismiss();
//				}
//				int ret = intent.getIntExtra("RESULT", 0);
//				String message = intent.getStringExtra("MESSAGE");
//				timeoutHandler.removeCallbacks(timeoutProcess);
//				SmartPlugDefine plug = mPlugHelper.getSmartPlug(mFocusPlugId);
//				switch (ret) {
//					case 0 :
//						plug.mPlugName = mNewPlugName;
//						if (0 < mPlugHelper.modifySmartPlug(plug)) {
//							doBackgroundLoad();
//						}
//
//						break;
//					default :
//						PubFunc.thinzdoToast(mContext, message);
//						break;
//				}
//			}
//			if (intent.getAction().equals(PubDefine.PLUG_BACK2AP_ACTION)) {
//				String plugId = intent.getStringExtra("PLUGID");
//				deletePlug(plugId);
//
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						qryPlugsFromServer();
//					}
//				}, 1);
//			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = getActivity();

//		IntentFilter filter = new IntentFilter();
//		filter.addAction(PubDefine.PLUG_POWER_ACTION);
//		filter.addAction(PubDefine.PLUG_UPDATE);
//		filter.addAction(PubDefine.PLUG_DELETE);
//		filter.addAction(PubDefine.PLUG_NOTIFY_POWER);
//		filter.addAction(PubDefine.PLUG_NOTIFY_ONLINE);
//		filter.addAction(PubDefine.PLUG_MODIFY_PLUGNAME);
//		filter.addAction(PubDefine.PLUG_NOTIFY_CURTAIN);
//		filter.addAction(PubDefine.PLUG_BACK2AP_ACTION);
//		mContext.registerReceiver(mLoadPlugReveiver, filter);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				qryPlugsFromServer();
			}
		}, 1);
	}

	private Handler mTimeoutHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (0 == msg.what) {
				timeoutHandler.removeCallbacks(timeoutProcess);
				if (null != mRefreshableView) {
					mRefreshableView.finishRefreshing();
				}
			}
		}
	};

	private void qryPlugsFromServer() {
		registerTimeoutHandler(mTimeoutHandler);

		StringBuffer sb = new StringBuffer();
//		sb.append(SmartLockMessage.CMD_SP_QRYPLUG)
//				.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL)
//				.append(PubStatus.g_CurUserName);

		sendMsg(true, sb.toString(), true);
	}

	private void setPlugsOffline() {
//		mPlugHelper.setAllPlugsOffline();
//		doBackgroundLoad();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (mFragmentView == null) {
			mFragmentView = inflater.inflate(R.layout.fragment_dev, container,
					false);
		}
		
		mDevList = (ListView) mFragmentView.findViewById(R.id.dev_list);

		mRefreshableView = (RefreshableView) mFragmentView
				.findViewById(R.id.refreshable_view);
		mRefreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				qryPlugsFromServer();
			}
		}, 0);

		return mFragmentView;
	}

	private Handler updateHandler = new Handler() {
		public void handleMessage(Message msg) {
			setPlugsOffline();
		};
	};

	@Override
	public void onResume() {
		super.onResume();
		SmartLockApplication.resetTask();
//		PubFunc.log("ControlFragment onResume");
//		doBackgroundLoad();
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mContext.unregisterReceiver(mLoadPlugReveiver);
	}
//
//	private void doBackgroundLoad() {
//		AsyncTask<Void, Void, ArrayList<SmartPlugDefine>> loadData = new AsyncTask<Void, Void, ArrayList<SmartPlugDefine>>() {
//			private ArrayList<Integer> hasTimer = new ArrayList<Integer>();
//			@Override
//			protected void onPreExecute() {
//				super.onPreExecute();
//			}
//
//			@Override
//			protected ArrayList<SmartPlugDefine> doInBackground(Void... arg0) {
//				ArrayList<SmartPlugDefine> plugs = mPlugHelper
//						.getAllSmartPlug(PubStatus.g_CurUserName);
////				if (null != plugs) {
////					for (int i = 0; i < plugs.size(); i++) {
////						hasTimer.add(mTimerHelper.getAllTimer(
////								plugs.get(i).mPlugId).size());
////					}
////				}
//
//				return plugs;
//			}
//
//			@Override
//			protected void onPostExecute(ArrayList<SmartPlugDefine> result) {
//				super.onPostExecute(result);
//				if (null != result) {
//					PluglistAdapter adapter = new PluglistAdapter(mContext,
//							result, hasTimer, mPressHandler);
//					mPlugList.setAdapter(adapter);
//				}
//
//				if (null != mRefreshableView) {
//					mRefreshableView.finishRefreshing();
//				}
//			}
//
//		};
//		loadData.execute();
//	}

	private Handler mPressHandler = new Handler() {
		public void handleMessage(Message msg) {
//			mFocusPlugId = (String) msg.obj;
//			SmartPlugDefine plug = mPlugHelper.getSmartPlug(mFocusPlugId);
//			PubFunc.log("mFocusPlugId=" + mFocusPlugId);
//			if (0 == msg.what) { // 修改模块名字
//				if (null != plug) {
//					modifyName(plug.mPlugName);
//				}
//			}
//			if (1 == msg.what) { // 删除模块
//				if (null != plug) {
//					final MyAlertDialog dialog = new MyAlertDialog(mContext)
//							.builder().setTitle("删除模块")
//							.setNegativeButton("取消", new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//
//								}
//							});
//					dialog.setPositiveButton("确定", new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							deletePlug(mFocusPlugId);
//						}
//					});
//					dialog.show();
//
//				}
//			}
		};
	};

	View.OnClickListener modifyClick = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (null == mModifyDlg) {
				return;
			}
			String text = mModifyDlg.getResult();
			if (!text.isEmpty()) {
				mNewPlugName = mModifyDlg.getResult();

				// 校验 NewPlugName：中英文占用的字节数必须小于20（最大20个byte）
				if (mNewPlugName.getBytes().length > 20) {
//					PubFunc.thinzdoToast(
//							mContext,
//							getString(R.string.smartplug_ctrl_mod_plugname_length_too_long));
					return;
				}

//				if (true == mPlugHelper.isPlugExists(PubStatus.g_CurUserName,
//						mNewPlugName)) {
//					PubFunc.thinzdoToast(mContext,
//							getString(R.string.smartplug_ctrl_samename_exist));
//					return;
//				}

//				mProgress = PubFunc.createProgressDialog(mContext,
//						getString(R.string.smartplug_ctrl_moding_name), false);
//				mProgress.show();
//
//				StringBuffer sb = new StringBuffer();
//				sb.append(SmartLockMessage.CMD_SP_MODYPLUG)
//						.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL)
//						.append(PubStatus.g_CurUserName)
//						.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL)
//						.append(mFocusPlugId)
//						.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL)
//						.append(mNewPlugName);
//				sendMsg(true, sb.toString(), true);
			}
		}
	};

	private void modifyName(String name) {
//		mModifyDlg = new MyAlertDialog(mContext);
//		mModifyDlg
//				.builder()
//				.setCancelable(true)
//				.setTitle(getString(R.string.smartplug_ctrl_mod_name))
//				.setEditText(name)
//				.setPositiveButton(mContext.getString(R.string.smartplug_ok),
//						modifyClick)
//				.setNegativeButton(
//						mContext.getString(R.string.smartplug_cancel),
//						new View.OnClickListener() {
//							@Override
//							public void onClick(View arg0) {
//
//							}
//
//						}).show();
	}

	private void deletePlug(String plugId) {
//		mErrorMsg = getString(R.string.smartplug_ctrl_delete_fail);
//		mProgress = PubFunc.createProgressDialog(mContext,
//				mContext.getString(R.string.smartplug_ctrl_delete), false);
//		mProgress.show();
//
//		if (mFocusPlugId.equalsIgnoreCase("0")) {
//			int a = 0;
//		}
//
//		StringBuffer sb = new StringBuffer();
//		sb.append(SmartLockMessage.CMD_SP_DELPLUG)
//				.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL)
//				.append(PubStatus.g_CurUserName)
//				.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL).append(plugId);
//		sendMsg(true, sb.toString(), true);
	}
}
