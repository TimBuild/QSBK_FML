package com.bt.qiubai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class FirstEnterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity_detail1);

		boolean mFirst = isFirstEnter(FirstEnterActivity.this,
				FirstEnterActivity.this.getClass().getName());

		if (mFirst) {
			mHandler.sendEmptyMessageDelayed(SWITCH_GUIDECTIVITY, 0);
		} else {
			mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY, 0);
		}

	}

	private static final String SHAREDPREFERENCES_FIRSTENTER = "first_enter_activity";
	private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

	/**
	 * 判断应用是不是初次加载，读取sharedpreferences中的guide_activity字段
	 * 
	 * @param firstEnterActivity
	 * @param name
	 * @return
	 */
	private boolean isFirstEnter(Context context, String name) {
		if (context == null || name == null || "".equalsIgnoreCase(name)) {
			return false;
		}

		String mResultStr = context.getSharedPreferences(
				SHAREDPREFERENCES_FIRSTENTER, Context.MODE_PRIVATE)
				.getString(KEY_GUIDE_ACTIVITY, "");
		if(mResultStr.equalsIgnoreCase("false")){
			return false;
		}
		else{
			return true;
		}
		
	}

	// Handler跳转至不同页面
	private final static int SWITCH_MAINACTIVITY = 1000;
	private final static int SWITCH_GUIDECTIVITY = 1001;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SWITCH_GUIDECTIVITY:
				Intent mIntent = new Intent();
				mIntent.setClass(FirstEnterActivity.this, GuideActivity.class);
				FirstEnterActivity.this.startActivity(mIntent);
				FirstEnterActivity.this.finish();

				break;
			case SWITCH_MAINACTIVITY:
				mIntent = new Intent();
				mIntent.setClass(FirstEnterActivity.this, MainActivity.class);
				FirstEnterActivity.this.startActivity(mIntent);
				FirstEnterActivity.this.finish();
				break;

			}
			super.handleMessage(msg);
		}
	};
}