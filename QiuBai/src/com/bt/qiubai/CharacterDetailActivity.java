package com.bt.qiubai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qiubai.util.DensityUtil;

public class CharacterDetailActivity extends Activity implements OnClickListener, OnTouchListener{
	
	private RelativeLayout title_back,title_rel_right;
	private RelativeLayout cd_rel_comment, cd_rel_support, cd_rel_tread;
	private LinearLayout cd_action_share, cd_action_comment, cd_action_font;
	private ScrollView cd_scroll;
	private TextView cd_tv_content, cd_tv_from, cd_tv_title, cd_tv_time, cd_tv_comment, cd_tv_support, cd_tv_tread;
	private ImageView cd_iv_support, cd_iv_tread;
	
	private Dialog actionDialog;
	private Dialog fontDialog;
	private GestureDetector gestureDetector;
	private Animation anim_support, anim_tread;
	
	@SuppressLint("RtlHardcoded")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.cd_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cd_title);
		
		System.out.println(DensityUtil.dip2px(this, 45));
		
		Intent intent = getIntent();
		
		cd_tv_title = (TextView) findViewById(R.id.cd_tv_title);
		cd_tv_title.setText(intent.getStringExtra("fcd_char_title"));
		cd_tv_from = (TextView) findViewById(R.id.cd_tv_from);
		cd_tv_from.setText(intent.getStringExtra("fcd_user"));
		cd_tv_content = (TextView) findViewById(R.id.cd_tv_content);
		cd_tv_content.setText(intent.getStringExtra("fcd_context"));
		cd_tv_time = (TextView) findViewById(R.id.cd_tv_time);
		cd_tv_time.setText(dealTime(intent.getStringExtra("fcd_char_time")));
		cd_tv_comment = (TextView) findViewById(R.id.cd_tv_comment);
		cd_tv_comment.setText("12832");
		cd_tv_support = (TextView) findViewById(R.id.cd_tv_support);
		cd_tv_support.setText(intent.getStringExtra("fcd_char_support"));
		cd_tv_tread = (TextView) findViewById(R.id.cd_tv_tread);
		cd_tv_tread.setText(intent.getStringExtra("fcd_char_oppose"));
		
		gestureDetector = new GestureDetector(CharacterDetailActivity.this,onGestureListener);
		anim_support = AnimationUtils.loadAnimation(this, R.anim.cd_support);
		anim_tread = AnimationUtils.loadAnimation(this, R.anim.cd_tread);
		
		cd_scroll = (ScrollView) findViewById(R.id.cd_scroll);
		cd_scroll.setOnTouchListener(this);
		
		actionDialog = new Dialog(CharacterDetailActivity.this, R.style.CommonActionDialog);
		actionDialog.setContentView(R.layout.cd_action_bar);
		actionDialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
		
		cd_action_share = (LinearLayout) actionDialog.findViewById(R.id.cd_action_share);
		cd_action_share.setOnClickListener(this);
		cd_action_comment = (LinearLayout) actionDialog.findViewById(R.id.cd_action_comment);
		cd_action_comment.setOnClickListener(this);
		cd_action_font = (LinearLayout) actionDialog.findViewById(R.id.cd_action_font);
		cd_action_font.setOnClickListener(this);
		
		fontDialog = new Dialog(CharacterDetailActivity.this, R.style.CommonDialog);
		fontDialog.setContentView(R.layout.cd_dialog_font);
		
		title_back = (RelativeLayout) findViewById(R.id.detail_title_back);
		title_back.setOnClickListener(this);
		title_rel_right = (RelativeLayout) findViewById(R.id.title_rel_right);
		title_rel_right.setOnClickListener(this);
		cd_rel_comment = (RelativeLayout) findViewById(R.id.cd_rel_comment);
		cd_rel_comment.setOnClickListener(this);
		cd_rel_support = (RelativeLayout) findViewById(R.id.cd_rel_support);
		cd_rel_support.setOnClickListener(this);
		cd_rel_tread = (RelativeLayout) findViewById(R.id.cd_rel_tread);
		cd_rel_tread.setOnClickListener(this);
		cd_iv_support = (ImageView) findViewById(R.id.cd_iv_support);
		cd_iv_support.setTag("inactive");
		cd_iv_tread = (ImageView) findViewById(R.id.cd_iv_tread);
		cd_iv_tread.setTag("inactive");
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cd_action_share:
			actionDialog.dismiss();
			Intent intent_detail_to_pt = new Intent(CharacterDetailActivity.this, PictureTextActivity.class);
			startActivity(intent_detail_to_pt);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		case R.id.cd_action_comment:
			actionDialog.dismiss();
			Intent intent_detail_to_comment = new Intent(CharacterDetailActivity.this, CommentActivity.class);
			startActivity(intent_detail_to_comment);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		case R.id.cd_action_font:
			actionDialog.dismiss();
			fontDialog.show();
			break;
		case R.id.detail_title_back:
			CharacterDetailActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.title_rel_right:
			actionDialog.show();
			break;
		case R.id.cd_rel_comment:
			Intent intent_detail_to_comment_2 = new Intent(CharacterDetailActivity.this, CommentActivity.class);
			startActivity(intent_detail_to_comment_2);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		case R.id.cd_rel_support:
			doSupport();
			break;
		case R.id.cd_rel_tread:
			doTread();
			break;
		}
		
	}
	
	/**
	 * do support
	 */
	public void doSupport(){
		String tag_support = (String) cd_iv_support.getTag();
		String tag_tread = (String) cd_iv_tread.getTag();
		if("inactive".equals(tag_support)){
			cd_iv_support.setTag("active");
			if("active".equals(tag_tread)){
				cd_tv_tread.setText(String.valueOf(Integer.parseInt(cd_tv_tread.getText().toString()) - 1 ));
				Bitmap bitmap_tread = BitmapFactory.decodeResource(getResources(), R.drawable.cd_tread_inactive);
				cd_iv_tread.setImageBitmap(bitmap_tread);
				cd_iv_tread.setTag("inactive");
			}
			cd_tv_support.setText(String.valueOf(Integer.parseInt(cd_tv_support.getText().toString()) + 1 ));
			Bitmap bitmap_support = BitmapFactory.decodeResource(getResources(), R.drawable.cd_support_active);
			cd_iv_support.setImageBitmap(bitmap_support);
			cd_iv_support.startAnimation(anim_support);
		}
	}
	
	/**
	 * do tread
	 */
	public void doTread(){
		String tag_support = (String) cd_iv_support.getTag();
		String tag_tread = (String) cd_iv_tread.getTag();
		if("inactive".equals(tag_tread)){
			cd_iv_tread.setTag("active");
			if("active".equals(tag_support)){
				cd_tv_support.setText(String.valueOf(Integer.parseInt(cd_tv_support.getText().toString()) - 1));
				Bitmap bitmap_support = BitmapFactory.decodeResource(getResources(), R.drawable.cd_support_inactive);
				cd_iv_support.setImageBitmap(bitmap_support);
				cd_iv_support.setTag("inactive");
			}
			cd_tv_tread.setText(String.valueOf(Integer.parseInt(cd_tv_tread.getText().toString()) + 1 ));
			Bitmap bitmap_tread = BitmapFactory.decodeResource(getResources(), R.drawable.cd_tread_active);
			cd_iv_tread.setImageBitmap(bitmap_tread);
			cd_iv_tread.startAnimation(anim_tread);
			
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

	/**
	 * deal time string to yyyy/mm/dd
	 * @param str
	 * @return
	 */
	public String dealTime(String str){
		String year = (str.split(" ")[0]).split("-")[0];
		String month = (str.split(" ")[0]).split("-")[1];
		String day = (str.split(" ")[0]).split("-")[2];
		return year + "/" + month + "/" + day; 
	}
}
