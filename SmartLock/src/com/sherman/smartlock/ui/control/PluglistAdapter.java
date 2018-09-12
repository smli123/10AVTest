package com.sherman.smartlock.ui.control;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sherman.smartlock.dataprovider.SmartPlugHelper;
import com.sherman.smartlock.ui.SmartPlugDefine;
import com.sherman.smartlock.ui.common.PubDefine;
import com.sherman.smartlock.ui.smartplug.SmartPlugApplication;
import com.sherman.smartlock.ui.wheelutils.ActionSheetDialog;
import com.sherman.smartlock.ui.wheelutils.ActionSheetDialog.OnSheetItemClickListener;
import com.sherman.smartlock.ui.wheelutils.ActionSheetDialog.SheetItemColor;
import com.sherman.smartlock.R;

public class PluglistAdapter extends BaseAdapter {

	private ArrayList<SmartPlugDefine> mPluglist = null;
	private LayoutInflater mInflater = null;
	private Context mContext = null;
	private ArrayList<Integer> mHasTimer = null;
	private Handler mHandler = null;

	public PluglistAdapter(Context context,
			ArrayList<SmartPlugDefine> plugList, ArrayList<Integer> hasTimer,
			Handler handler) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mPluglist = plugList;
		mHasTimer = hasTimer;
		mHandler = handler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPluglist.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return mPluglist.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	private class ViewHolder {
		public ImageView imgPlug;
		public TextView txtPlugName;
		public ImageView imgOnline;
		public ImageView imgTimer;
		public ImageView imgPower;
		public ImageView imgLight;
		public ImageView imgUsb;
		public ImageView imgParent;
		public ImageView imgPower2;
		public RelativeLayout layoutPlug;
		public TextView txtConnect;
		public TextView txtMac;

		private void setImageRes(ImageView view, int resId) {
			view.setImageResource(resId);
		}

		@SuppressLint("ResourceAsColor") public void ViewData(SmartPlugDefine plug, int position) {
			if (plug != null) {
				txtPlugName.setSingleLine(true);

				if (!TextUtils.isEmpty(plug.mPlugId)) {
					txtPlugName.setText(plug.mPlugName);
					txtMac.setText("MAC: " + plug.mMAC);
					imgPlug.setImageResource(0);
					imgTimer.setImageResource(0);
					imgPower.setImageBitmap(null);
					imgLight.setImageBitmap(null);
					imgUsb.setImageBitmap(null);
					imgParent.setImageBitmap(null);
					imgPower2.setImageBitmap(null);

					int img_type = R.drawable.smp_plug_small;
					imgPlug.setImageResource(img_type);
					if (true == plug.mIsOnline) {
						imgOnline.setImageResource(R.drawable.smp_online);
						txtConnect.setVisibility(View.INVISIBLE);
						txtConnect.clearAnimation();
						txtConnect.setTextColor(R.color.black);
						txtPlugName.setTextColor(R.color.black);
						txtMac.setTextColor(R.color.black);
						layoutPlug.setBackgroundColor(Color.TRANSPARENT);
						layoutPlug.setOnClickListener(selectPlugClick);

						imgTimer.setVisibility(View.GONE);
						
//						if (true == ((plug.mDeviceStatus & 1) == 1)) {
//							setImageRes(imgPower, R.drawable.smp_power_on_small);
//						} else {
//							setImageRes(imgPower, R.drawable.smp_power_off_small);
//						}
					} else {
						imgOnline.setImageResource(R.drawable.smp_offline);
						txtPlugName.setTextColor(R.color.gray);
						txtMac.setTextColor(R.color.gray);
						layoutPlug.setBackgroundColor(Color.LTGRAY);
						layoutPlug.setOnClickListener(selectPlugClick);
						txtConnect.setTextColor(R.color.gray);
						txtConnect.setVisibility(View.VISIBLE);
						txtConnect
								.setText(mContext.getString(R.string.connect));

						// show connect animation
						AnimationSet set = new AnimationSet(true);
						AlphaAnimation alpha = new AlphaAnimation(1, 0);
						alpha.setDuration(1000);
						alpha.setRepeatCount(Animation.INFINITE);
						alpha.setRepeatMode(Animation.RESTART);
						set.addAnimation(alpha);
						txtConnect.startAnimation(set);

//						setImageRes(imgPower, R.drawable.smp_power_off_small);
//						setImageRes(imgLight, R.drawable.smp_light_off_small);
//						setImageRes(imgUsb, R.drawable.smp_usb_off_small);
//						setImageRes(imgPower2, R.drawable.smp_power_off_small);
					}

					layoutPlug.setContentDescription(String
							.valueOf(plug.mPlugId));
					layoutPlug.setOnLongClickListener(deletePlug);
				}
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if ((mPluglist == null) || (mPluglist.size() == 0)) {
			return convertView;
		}
		if ((position < 0) || (position > mPluglist.size())) {
			return convertView;
		}
		if (mInflater == null) {
			return convertView;
		}

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.view_plug_item, null);
			holder.txtPlugName = (TextView) convertView
					.findViewById(R.id.plug_txt_name);
			holder.imgPlug = (ImageView) convertView
					.findViewById(R.id.plug_img_icon);
			holder.imgOnline = (ImageView) convertView
					.findViewById(R.id.plug_img_online);
			holder.imgTimer = (ImageView) convertView
					.findViewById(R.id.plug_img_timer);
			holder.imgPower = (ImageView) convertView
					.findViewById(R.id.plug_img_power);
			holder.imgLight = (ImageView) convertView
					.findViewById(R.id.plug_img_light);
			holder.imgUsb = (ImageView) convertView
					.findViewById(R.id.plug_img_usb);
			holder.imgParent = (ImageView) convertView
					.findViewById(R.id.plug_img_parent);
			holder.layoutPlug = (RelativeLayout) convertView
					.findViewById(R.id.layout_plug_item);
			holder.txtMac = (TextView) convertView
					.findViewById(R.id.plug_txt_mac);
			holder.txtConnect = (TextView) convertView
					.findViewById(R.id.plug_txt_connect);
			holder.imgPower2 = (ImageView) convertView
					.findViewById(R.id.plug_img_power2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder != null && mPluglist != null && position < mPluglist.size()) {
			SmartPlugDefine FavoriteItem = mPluglist.get(position);
			convertView.setBackgroundColor(Color.TRANSPARENT);
			holder.ViewData(FavoriteItem, position);

			holder.txtMac.setVisibility(View.GONE);

			if (FavoriteItem.mSubDeviceType == PubDefine.DEVICE_SMART_CURTAIN) { // 3_1
				holder.imgPlug.setImageResource(R.drawable.smp_curtain_small);
				// holder.imgPower.setVisibility(View.GONE);
				// holder.imgLight.setVisibility(View.GONE);
				// holder.imgUsb.setVisibility(View.GONE);
				// holder.imgParent.setVisibility(View.GONE);
				holder.imgPower2.setVisibility(View.GONE);
			} else {
				holder.imgPlug.setImageResource(R.drawable.smp_unknown_small);
				holder.imgPower2.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	View.OnClickListener selectPlugClick = new View.OnClickListener() {

		@SuppressWarnings("null")
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			String plugId = v.getContentDescription().toString();
			intent.putExtra("PLUGID", plugId);

			SmartPlugHelper mPlugHelper = new SmartPlugHelper(
					SmartPlugApplication.getContext());
			SmartPlugDefine mPlug = mPlugHelper.getSmartPlug(plugId);
			if (null == mPlug) {
				return;
			}
			intent.putExtra("ONLINE", mPlug.mIsOnline);

			if (mPlug.mSubDeviceType == PubDefine.DEVICE_SMART_CURTAIN) { // 3_1
				intent.setClass(mContext, DetailCurtainActivity.class);
			} else {
				return;
			}

			mContext.startActivity(intent);
		}
	};

	View.OnLongClickListener deletePlug = new View.OnLongClickListener() {

		@Override
		public boolean onLongClick(final View v) {
			new ActionSheetDialog(mContext)
					.builder()
					.setCancelable(true)
					.setCanceledOnTouchOutside(true)
					.addSheetItem(
							v.getContext().getString(R.string.plug_modify_name),
							SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Message msg = new Message();
									msg.what = 0; // modify name
									msg.obj = v.getContentDescription()
											.toString();
									mHandler.sendMessage(msg);
								}
							})
					.addSheetItem(
							v.getContext().getString(
									R.string.register_info_delete),
							SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Message msg = new Message();
									msg.what = 1; // delete
									msg.obj = v.getContentDescription()
											.toString();
									mHandler.sendMessage(msg);
								}
							}).show();
			return false;
		}
	};

}
