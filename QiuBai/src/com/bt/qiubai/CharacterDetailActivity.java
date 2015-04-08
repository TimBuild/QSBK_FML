package com.bt.qiubai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CharacterDetailActivity extends Activity implements OnClickListener, OnTouchListener{
	
	private RelativeLayout title_back,title_rel_right;
	private Dialog actionDialog;
	
	private GestureDetector gestureDetector;
	
	private LinearLayout action_share,action_comment;
	private ScrollView scroll_content;
	
	private TextView cd_content, cd_from, cd_title, cd_time, cd_zan;
	
	@SuppressLint("RtlHardcoded")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.cd_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cd_title);
		
		Intent intent = getIntent();
		String str_title = intent.getStringExtra("fcd_char_title");
		String str_from = intent.getStringExtra("fcd_user");
		String str_content = intent.getStringExtra("fcd_context");
		String str_zan = intent.getStringExtra("fcd_char_support");
		String str_time = intent.getStringExtra("fcd_char_time");
		
		cd_from = (TextView) findViewById(R.id.cd_tv_from);
		cd_title = (TextView) findViewById(R.id.cd_tv_title);
		cd_content = (TextView) findViewById(R.id.cd_tv_content);
		cd_time = (TextView) findViewById(R.id.cd_tv_time);
		cd_zan = (TextView) findViewById(R.id.cd_tv_support);
		cd_title.setText(str_title);
		cd_from.setText("来自：" + str_from);
		cd_content.setText(str_content);
		cd_time.setText(dealTime(str_time));
		cd_zan.setText(str_zan+"赞");
		
		
		gestureDetector = new GestureDetector(CharacterDetailActivity.this,onGestureListener);
		
		scroll_content = (ScrollView) findViewById(R.id.cd_scroll_content);
		scroll_content.setOnTouchListener(this);
		
		actionDialog = new Dialog(CharacterDetailActivity.this, R.style.CommonActionDialog);
		actionDialog.setContentView(R.layout.common_action_bar);
		actionDialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
		
		action_share = (LinearLayout) actionDialog.findViewById(R.id.common_action_share);
		action_comment = (LinearLayout) actionDialog.findViewById(R.id.common_action_comment);	
		
		action_share.setOnClickListener(this);
		//click this to open comment activity
		action_comment.setOnClickListener(this);
		
		title_back = (RelativeLayout) findViewById(R.id.detail_title_back);
		title_rel_right = (RelativeLayout) findViewById(R.id.title_rel_right);
		
		title_back.setOnClickListener(this);
		title_rel_right.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.common_action_share:
			actionDialog.dismiss();
			Intent intent_detail_to_pt = new Intent(CharacterDetailActivity.this, PictureTextActivity.class);
			startActivity(intent_detail_to_pt);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		case R.id.common_action_comment:
			actionDialog.dismiss();
			Intent intent_detail_to_comment = new Intent(CharacterDetailActivity.this, CommentActivity.class);
			startActivity(intent_detail_to_comment);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		case R.id.detail_title_back:
			CharacterDetailActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.title_rel_right:
			actionDialog.show();
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
					CharacterDetailActivity.this.finish();
					overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
					return true;
				}else if(e2.getX() - e1.getX() < -200){
					Intent intent = new Intent(CharacterDetailActivity.this, CommentActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
					return true;
				}
			}else {
				return false;
			}
			
			return false;
		}
	};

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public String dealTime(String str){
		String year = (str.split(" ")[0]).split("-")[0];
		String month = (str.split(" ")[0]).split("-")[1];
		String day = (str.split(" ")[0]).split("-")[2];
		return year + "/" + month + "/" + day; 
	}
}
