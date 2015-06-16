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

public class QQShareActivity extends Activity {

	private EditText qq_text;
	private Button qqZone_Share, qqFriends_Share;

	public static final String KEY_QQ_TYPE = "key_qq_type";
	public static final String KEY_QQ_FRIENDS = "key_qq_friends";
	public static final String KEY_QQ_ZONE = "key_qq_zone";
	public static final String KEY_CHARACTER_TEXT = "key_character_text";
	public static final String KEY_QQ_TITLE = "key_qq_title";

	public Tencent mTencent;
	private static String TAG = "QQShareActivity";
	private String text, type,title;

	// private static String APP_ID = "1104704216";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_share_qq);

		mTencent = Tencent.createInstance(com.qiubai.dao.Constants.QQ_APP_ID,
				this);

		text = getIntent().getExtras().getString(KEY_CHARACTER_TEXT);
		type = getIntent().getExtras().getString(KEY_QQ_TYPE);
		title = getIntent().getExtras().getString(KEY_QQ_TITLE);

		// initViews();
		// Log.d(TAG, "type: " + type + " text: " + text);

		if (type.equals(KEY_QQ_FRIENDS)) {
			// qq好友
			sendMessageToQQ(text,title);

		} else if (type.equals(KEY_QQ_ZONE)) {
			// qq空间
			sendMessageToZone(text,title);
		}

	}

	private void initViews() {
		qq_text = (EditText) findViewById(R.id.qq_share_edit);

		qq_text.setText(text);
		qqZone_Share = (Button) findViewById(R.id.qq_share_zone);
		qqFriends_Share = (Button) findViewById(R.id.qq_share_friends);

	}

	IUiListener qqShareListener = new IUiListener() {

		@Override
		public void onError(UiError arg0) {
			Toast.makeText(QQShareActivity.this,
					"onError:" + arg0.errorMessage, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Object arg0) {
			Toast.makeText(QQShareActivity.this, "分享成功", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(QQShareActivity.this, "取消分享", Toast.LENGTH_SHORT)
					.show();
		}
	};

	protected void sendMessageToZone(String text,String title) {

		Bundle params_zone = new Bundle();
		params_zone.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
				QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params_zone.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		params_zone.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, text);
		params_zone.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
				"http://www.baidu.com");
		params_zone.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
				QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

		mTencent.shareToQQ(QQShareActivity.this, params_zone, qqShareListener);
		finish();
	}

	protected void sendMessageToQQ(String text,String title) {
		final Bundle params_friends = new Bundle();
		params_friends.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);
		params_friends.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params_friends.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
				"http://www.baidu.com");

		mTencent.shareToQQ(QQShareActivity.this, params_friends,
				qqShareListener);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode="
				+ resultCode);

		if (requestCode == Constants.REQUEST_QQ_SHARE) {
			if (resultCode == Constants.ACTIVITY_OK) {
				Tencent.handleResultData(data, qqShareListener);
			}
		}
	}
}
