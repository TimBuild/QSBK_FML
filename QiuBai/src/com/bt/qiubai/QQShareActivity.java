package com.bt.qiubai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQShareActivity extends Activity{
	
	private EditText qq_text;
	private Button qqZone_Share,qqFriends_Share;
	
	public static final String KEY_QQ_TYPE = "key_qq_type";
	public static final String KEY_QQ_FRIENDS = "key_qq_friends";
	public static final String KEY_QQ_ZONE = "key_qq_zone";
	public static final String KEY_CHARACTER_TEXT = "key_character_text";
	
	public Tencent mTencent;
	private static String TAG = "QQShareActivity";
	private String text, type;
	
//	private static String APP_ID = "1104704216";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_qq);
		
		mTencent = Tencent.createInstance(com.qiubai.dao.Constants.QQ_APP_ID, this);
		
		text = getIntent().getExtras().getString(KEY_CHARACTER_TEXT);
		type = getIntent().getExtras().getString(KEY_QQ_TYPE);
		
		initViews();
		
	}

	private void initViews() {
		qq_text = (EditText) findViewById(R.id.qq_share_edit);
		
		qq_text.setText(text);
		qqZone_Share = (Button) findViewById(R.id.qq_share_zone);
		qqFriends_Share = (Button) findViewById(R.id.qq_share_friends);
		
		qqZone_Share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle params = new Bundle();
				String text = qq_text.getText().toString();
				params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "测试");
				params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, text);
				params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
				sendMessageToZone(params);
				
			}
		});
		
		qqFriends_Share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle params = new Bundle();
				String text = qq_text.getText().toString();
				params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);
				params.putString(QQShare.SHARE_TO_QQ_TITLE, "测试");
				params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
				sendMessageToQQ(params);
			}
		});
	}
	
	IUiListener qqShareListener = new IUiListener() {
		
		@Override
		public void onError(UiError arg0) {
			Toast.makeText(QQShareActivity.this, "onError:"+arg0.errorMessage, Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onComplete(Object arg0) {
			Toast.makeText(QQShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();			
		}
		
		@Override
		public void onCancel() {
			Toast.makeText(QQShareActivity.this, "取消分享", Toast.LENGTH_SHORT).show();			
		}
	};
	
	
	protected void sendMessageToZone(Bundle params) {
		mTencent.shareToQQ(QQShareActivity.this, params, qqShareListener);
	}

	protected void sendMessageToQQ(Bundle params) {
		mTencent.shareToQQ(QQShareActivity.this, params, qqShareListener);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG , "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
		
		if(requestCode == Constants.REQUEST_QQ_SHARE){
			if(resultCode == Constants.ACTIVITY_OK){
				Tencent.handleResultData(data, qqShareListener);
			}
		}
	}
}
