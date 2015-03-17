package com.bt.qiubai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity implements OnClickListener{
	
	private RelativeLayout login_title_back;
	private RelativeLayout login_layout_to_register;
	private LinearLayout login_account_qq, login_account_sina;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
		setContentView(R.layout.login_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.login_title);
		
		login_title_back = (RelativeLayout) findViewById(R.id.login_title_back);
		login_title_back.setOnClickListener(this);
		
		login_layout_to_register = (RelativeLayout) findViewById(R.id.login_layout_to_register);
		login_layout_to_register.setOnClickListener(this);
		
		login_account_qq = (LinearLayout) findViewById(R.id.login_account_qq);
		login_account_qq.setOnClickListener(this);
		
		login_account_sina = (LinearLayout) findViewById(R.id.login_account_sina);
		login_account_sina.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_title_back:
			LoginActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.login_layout_to_register:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		case R.id.login_account_qq:
			break;
		case R.id.login_account_sina:
			break;
		}
	}
}
