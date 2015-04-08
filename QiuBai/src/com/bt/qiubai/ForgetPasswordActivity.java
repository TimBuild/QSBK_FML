package com.bt.qiubai;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ForgetPasswordActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.fpw_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fpw_title);
	}
	
}
