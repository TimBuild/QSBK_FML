package com.bt.qiubai;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class DetailActivity extends Activity {
	
	private RelativeLayout title_rel_left,title_rel_right;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//测试中文乱码
		// 下面三行代码顺序不能颠倒
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 声明使用自定义标题
		setContentView(R.layout.activity_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.detail_title);
		
		title_rel_left = (RelativeLayout) findViewById(R.id.title_rel_left);
		title_rel_right = (RelativeLayout) findViewById(R.id.title_rel_right);
		
		title_rel_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		title_rel_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}
}
