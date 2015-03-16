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

public class DetailActivity extends Activity {
	
	private RelativeLayout title_back,title_rel_right;
	private Dialog actionDialog;
	
	private GestureDetector gestureDetector;
	
	private LinearLayout action_share,action_comment;
	private ScrollView scroll_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//下面三行代码顺序不能颠倒
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
		setContentView(R.layout.detail_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.detail_title);
		
		gestureDetector = new GestureDetector(DetailActivity.this,onGestureListener);
		
		scroll_content = (ScrollView) findViewById(R.id.detail_scroll_content);
		scroll_content.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
		
		actionDialog = new Dialog(DetailActivity.this, R.style.DetailActionDialog);
		actionDialog.setContentView(R.layout.detail_action_bar);
		actionDialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
		
		action_share = (LinearLayout) actionDialog.findViewById(R.id.detail_action_share);
		action_comment = (LinearLayout) actionDialog.findViewById(R.id.detail_action_comment);
				
		
		
		action_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				actionDialog.dismiss();
			}
		});
		//click this to open comment activity
		action_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				actionDialog.dismiss();
				Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			}
		});
		
		title_back = (RelativeLayout) findViewById(R.id.detail_title_back);
		title_rel_right = (RelativeLayout) findViewById(R.id.title_rel_right);
		
		title_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DetailActivity.this.finish();
				overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
				finish();
			}
		});
		
		title_rel_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				actionDialog.show();
			}
		});
		
		
	}
	
	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = Math.abs(e2.getX() - e1.getX());
			float y = Math.abs(e2.getY() - e1.getY());
			if(y < x){
				if(e2.getX() - e1.getX() > 200){
					DetailActivity.this.finish();
					overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
					return true;
				}else if(e2.getX() - e1.getX() < -200){
					Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
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
}
