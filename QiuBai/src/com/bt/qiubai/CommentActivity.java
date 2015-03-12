package com.bt.qiubai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class CommentActivity extends Activity {
	
	private RelativeLayout comment_title_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 下面三行代码顺序不能颠倒
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 声明使用自定义标题
		setContentView(R.layout.comment_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.comment_title);
		
		comment_title_back = (RelativeLayout) findViewById(R.id.comment_title_back);
		comment_title_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentActivity.this.finish();
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			}
		});
		
	}
}
