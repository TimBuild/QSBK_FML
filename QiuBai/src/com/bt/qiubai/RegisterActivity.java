package com.bt.qiubai;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class RegisterActivity extends Activity implements OnClickListener{
	
	private RelativeLayout register_title_back;
	private RelativeLayout register_user_register;
	
	//private UserService userService = new UserService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.register_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.register_title);
		
		register_title_back = (RelativeLayout) findViewById(R.id.register_title_back);
		register_title_back.setOnClickListener(this);
		register_user_register = (RelativeLayout) findViewById(R.id.register_user_register);
		register_user_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_title_back:
			RegisterActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.register_user_register:
			userRegister();
			
			break;
		}
	}
	public void userRegister(){
		new Thread(){
			public void run() {
				//userService.userRegister(, nickname, password);
			};
		}.start();
	}
	
	private Handler registerHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			
		};
	};
}
