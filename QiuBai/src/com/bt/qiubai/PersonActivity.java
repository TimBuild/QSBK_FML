package com.bt.qiubai;

import java.io.File;

import com.qiubai.service.UserService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class PersonActivity extends Activity implements OnClickListener, OnTouchListener{

	private RelativeLayout person_title_back, person_rel_nickname, person_rel_header_icon;
	private ScrollView person_scroll;
	private LinearLayout person_lin_logout;
	
	private GestureDetector gestureDetector;
	private UserService userService = new UserService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.person_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.person_title);
		
		person_scroll = (ScrollView) findViewById(R.id.person_scroll);
		gestureDetector = new GestureDetector(PersonActivity.this,onGestureListener);
		person_scroll.setOnTouchListener(this);
		
		person_title_back = (RelativeLayout) findViewById(R.id.person_title_back);
		person_title_back.setOnClickListener(this);
		person_lin_logout = (LinearLayout) findViewById(R.id.person_lin_logout);
		person_lin_logout.setOnClickListener(this);
		person_rel_nickname = (RelativeLayout) findViewById(R.id.person_rel_nickname);
		person_rel_nickname.setOnClickListener(this);
		person_rel_header_icon = (RelativeLayout) findViewById(R.id.person_rel_header_icon);
		person_rel_header_icon.setOnClickListener(this);
		
		File file = new File("/data/data/com.bt.qiubai/userinfo");
		if (!file.exists()) {
			System.out.println("okokok");
			file.mkdirs();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_title_back:
			PersonActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.person_lin_logout:
			userService.logout(this);
			PersonActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.person_rel_nickname:
			
			break;
		case R.id.person_rel_header_icon:
			break;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			PersonActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = Math.abs(e2.getX() - e1.getX());
			float y = Math.abs(e2.getY() - e1.getY());
			if(y < x){
				if(e2.getX() - e1.getX() > 200){
					PersonActivity.this.finish();
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
