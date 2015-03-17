package com.bt.qiubai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity implements OnClickListener{
	
	private RelativeLayout login_title_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
		setContentView(R.layout.login_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.login_title);
		
		login_title_back = (RelativeLayout) findViewById(R.id.login_title_back);
		login_title_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_title_back:
			
			break;
		}
	}
}
