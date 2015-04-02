package com.bt.qiubai;

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

public class CharacterDetailActivity extends Activity implements OnClickListener, OnTouchListener{
	
	private RelativeLayout title_back,title_rel_right;
	private Dialog actionDialog;
	
	private GestureDetector gestureDetector;
	
	private LinearLayout action_share,action_comment;
	private ScrollView scroll_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.cd_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cd_title);
		
		gestureDetector = new GestureDetector(CharacterDetailActivity.this,onGestureListener);
		
		scroll_content = (ScrollView) findViewById(R.id.detail_scroll_content);
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	
}
