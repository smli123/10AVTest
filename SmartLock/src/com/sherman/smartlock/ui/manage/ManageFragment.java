package com.sherman.smartlock.ui.manage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sherman.smartlock.processhandler.SmartLockMessage;
import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.common.SmartPlugFragment;
import com.sherman.smartlock.ui.common.StringUtils;
import com.sherman.smartlock.ui.login.LoginActivity;
import com.sherman.smartlock.ui.smartplug.PubStatus;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.R;

public class ManageFragment extends SmartPlugFragment
		implements
			View.OnClickListener {
	// private static ManageFragment mFragment = null;

	private TextView tvUserName = null;

	private Button mBtnLogout = null;
	private static ManageFragment mFragment = null;

	/*
	 * public static ManageFragment newInstance() { if (null == mFragment) {
	 * mFragment = new ManageFragment(); } return mFragment; }
	 */

	private Handler mHandler = null;
	// public ManageFragment(Handler handler) {
	// mHandler = handler;
	// }

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	public static ManageFragment newInstance() {
		if (null == mFragment) {
			mFragment = new ManageFragment();
		}
		return mFragment;
	}

	public static void delete() {
		if (null != mFragment) {
			mFragment = null;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreateView(inflater, container, savedInstanceState);
		mFragmentView = inflater.inflate(R.layout.fragment_manage, container,
				false);

		RelativeLayout layout1 = (RelativeLayout) mFragmentView
				.findViewById(R.id.detail_person_num_layout);
		layout1.setOnClickListener(this);

		RelativeLayout layoutFeedback = (RelativeLayout) mFragmentView
				.findViewById(R.id.lay_mgr_feedback);
		layoutFeedback.setOnClickListener(this);

		RelativeLayout layoutUpdate = (RelativeLayout) mFragmentView
				.findViewById(R.id.lay_mgr_update);
		layoutUpdate.setOnClickListener(this);

		RelativeLayout lay_update_debug = (RelativeLayout) mFragmentView
				.findViewById(R.id.lay_update_debug);
		lay_update_debug.setOnClickListener(new View.OnClickListener() {
			// 需要监听几次点击事件数组的长度就为几
			// 如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
			long[] mHints = new long[5]; // 初始全部为0
			@Override
			public void onClick(View v) {
				// 将mHints数组内的所有元素左移一个位置
				System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
				// 获得当前系统已经启动的时间
				mHints[mHints.length - 1] = SystemClock.uptimeMillis();
				if (SystemClock.uptimeMillis() - mHints[0] <= 500) {
					// Toast.makeText(mContext, "当你点击三次之后才会出现",
					// Toast.LENGTH_SHORT)
					// .show();
					update(1); // DEBUG
				}
			}
		});

		RelativeLayout layouthelp = (RelativeLayout) mFragmentView
				.findViewById(R.id.lay_mgr_help);
		layouthelp.setOnClickListener(this);

		RelativeLayout layoutAboutus = (RelativeLayout) mFragmentView
				.findViewById(R.id.lay_mgr_about);
		layoutAboutus.setOnClickListener(this);

		RelativeLayout layoutShare = (RelativeLayout) mFragmentView
				.findViewById(R.id.lay_mgr_share);
		layoutShare.setOnClickListener(this);

		TextView layoutLogout = (TextView) mFragmentView
				.findViewById(R.id.detail_user_logout);
		layoutLogout.setOnClickListener(this);

		tvUserName = (TextView) mFragmentView
				.findViewById(R.id.detail_user_name);
		if (PubStatus.g_CurUserName == null
				|| PubStatus.g_CurUserName.isEmpty()) {
			tvUserName.setText("Test");
		} else {
			tvUserName.setText(PubStatus.g_CurUserName);
		}

		mBtnLogout = (Button) mFragmentView.findViewById(R.id.login_out);
		mBtnLogout.setOnClickListener(this);

		return mFragmentView;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SmartPlugApplication.resetTask();
	}

	@Override
	public void onClick(View v) {

		Intent intent = null;
		switch (v.getId()) {
			case R.id.titlebar_leftbutton :
				// finish();
				break;

			case R.id.detail_person_num_layout :
				intent = new Intent();
				intent.setClass(this.getActivity(),
						AccountSecurityActivity.class);// ModifyPasswdActivity.class);
				startActivity(intent);
				break;
			case R.id.lay_mgr_feedback :
				intent = new Intent();
				intent.setClass(this.getActivity(), FeedbackActivity.class);
				startActivity(intent);
				break;
			case R.id.lay_mgr_help :
				intent = new Intent();
				intent.setClass(this.getActivity(), HelpActivity.class);
				startActivity(intent);
				break;
			case R.id.lay_mgr_about :
				intent = new Intent();
				intent.setClass(this.getActivity(), AboutUsActivity.class);
				startActivity(intent);
				break;
			case R.id.lay_mgr_update :
				this.update(0); // RELEASE
				break;
			case R.id.detail_user_logout :
				intent = new Intent();
				intent.setClass(this.getActivity(), LoginActivity.class);
				startActivity(intent);
			case R.id.lay_mgr_share :
				break;
			case R.id.login_out :
				logout();
				/*
				 * intent = new Intent();
				 * //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * intent.setClass(this.getActivity(), LoginActivity.class);
				 * startActivity(intent);
				 * SmartPlugApplication.getInstance().finishSmartPlugActivity();
				 */
				break;
			default :
				break;
		}
	}
	public void update(int verType) {
		// 使用网址进行下载更新
		// downloadAppFile();

		// 自定义自动更新机制
		checkUpdate(verType);

		// // 使用 第三方工具进行APP下载更新
		// UmengUpdateAgent.setUpdateAutoPopup(false);
		// UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		// @Override
		// public void onUpdateReturned(int updateStatus, UpdateResponse
		// updateInfo) {
		// // TODO Auto-generated method stub
		// switch (updateStatus) {
		// case UpdateStatus.Yes: // has update
		// UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
		// break;
		// case UpdateStatus.No: // has no update
		// PubFunc.thinzdoToast(mContext,
		// getString(R.string.smartplug_mgr_newver));
		// break;
		// case UpdateStatus.NoneWifi: // none wifi
		// PubFunc.thinzdoToast(mContext,
		// getString(R.string.smartplug_mgr_nowifi));
		// break;
		// case UpdateStatus.Timeout: // time out
		// PubFunc.thinzdoToast(mContext,
		// getString(R.string.smartplug_mgr_updatetimeout));
		// break;
		// }
		// }
		// });
		// UmengUpdateAgent.update(mContext);
	}

	private void checkUpdate(int verType) {
		UpdateManager update = new UpdateManager(mContext, verType);
		update.XMLFile_UpdateCheck();

	}

	private void downloadAppFile() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri
				.parse("http://fx.thingzdo.com/download/SmartPlug_UDP.apk");
		intent.setData(content_url);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	};

	private void logout() {
		mHandler.sendEmptyMessage(0);
		StringBuffer sb = new StringBuffer();
		sb.append(SmartLockMessage.CMD_SP_LOGINOUT)
				.append(StringUtils.PACKAGE_RET_SPLIT_SYMBOL)
				.append(PubStatus.g_CurUserName);
		sendMsg(true, sb.toString(), false);
	}

}
