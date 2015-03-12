package com.bt.qiubai;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class CommentActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 下面三行代码顺序不能颠倒
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 声明使用自定义标题
		setContentView(R.layout.comment_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.detail_title);
	}
}
