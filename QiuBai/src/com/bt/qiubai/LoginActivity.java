package com.bt.qiubai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, OnTouchListener, OnFocusChangeListener{
	
	private RelativeLayout login_title_back;
	private RelativeLayout login_layout_to_register;
	private LinearLayout login_login, login_account_qq, login_account_sina;
	private ScrollView login_scroll;
	private EditText login_user_email,login_user_password;
	private ImageView login_user_email_iv_cancel, login_user_password_iv_cancel;
	
	private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
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
		
		login_scroll = (ScrollView) findViewById(R.id.login_scroll);
		gestureDetector = new GestureDetector(LoginActivity.this,onGestureListener);
		login_scroll.setOnTouchListener(this);
		
		login_user_email_iv_cancel = (ImageView) findViewById(R.id.login_user_email_iv_cancel);
		login_user_email_iv_cancel.setOnClickListener(this);
		login_user_password_iv_cancel = (ImageView) findViewById(R.id.login_user_password_iv_cancel);
		login_user_password_iv_cancel.setOnClickListener(this);
		
		login_user_email = (EditText) findViewById(R.id.login_user_email);
		login_user_email.setOnFocusChangeListener(this);
		login_user_email.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!"".equals(s.toString())){
					login_user_email_iv_cancel.setVisibility(View.VISIBLE);
				} else {
					login_user_email_iv_cancel.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		login_user_password = (EditText) findViewById(R.id.login_user_password);
		login_user_password.setOnFocusChangeListener(this);
		login_user_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!"".equals(s.toString())){
					login_user_password_iv_cancel.setVisibility(View.VISIBLE);
				} else{
					login_user_password_iv_cancel.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		login_login = (LinearLayout) findViewById(R.id.login_login_lin);
		login_login.setOnClickListener(this);
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.login_user_email:
			if(hasFocus){
				if(!"".equals(login_user_email.getText().toString())){
					login_user_email_iv_cancel.setVisibility(View.VISIBLE);
				}
			} else {
				login_user_email_iv_cancel.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.login_user_password:
			if(hasFocus){
				if(!"".equals(login_user_password.getText().toString())){
					login_user_password_iv_cancel.setVisibility(View.VISIBLE);
				}
			} else {
				login_user_password_iv_cancel.setVisibility(View.INVISIBLE);
			}
			break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
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
		case R.id.login_user_email_iv_cancel:
			login_user_email.setText("");
			break;
		case R.id.login_user_password_iv_cancel:
			login_user_password.setText("");
			break;
		case R.id.login_login_lin:
			if("".equals(login_user_email.getText().toString()) && "".equals(login_user_password.getText().toString())){
				Toast.makeText(this, "请输入邮箱和密码", Toast.LENGTH_SHORT).show();
			} else if("".equals(login_user_email.getText().toString()) && !"".equals(login_user_password.getText().toString())){
				Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
			} else if(!"".equals(login_user_email.getText().toString()) && !"".equals(login_user_password.getText().toString())){
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			}
			Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = Math.abs(e2.getX() - e1.getX());
			float y = Math.abs(e2.getY() - e1.getY());
			if(y < x){
				if(e2.getX() - e1.getX() > 200){
					LoginActivity.this.finish();
					overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
					return true;
				}else if(e2.getX() - e1.getX() < -200){					
					return false;
				}
			}else {
				return false;
			}
			
			return false;
		}
	};

	
	
}
