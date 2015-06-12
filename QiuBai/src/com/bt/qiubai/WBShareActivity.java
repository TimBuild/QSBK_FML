package com.bt.qiubai;

import com.qiubai.dao.Constants;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WBShareActivity extends Activity implements OnClickListener,
		Response {

	private static final String TAG = "WBShareActivity";

	public static final String KEY_SHARE_TYPE = "key_share_type";
	public static final int SHARE_CLIENT = 1;

	public static final String KEY_CHARACTER_TEXT = "key_character_text";

	private EditText edit_share;
	private Button but_share;

	/** 微博微博分享接口实例 */
	private IWeiboShareAPI mWeiboShareAPI = null;

	private int mShareType = SHARE_CLIENT;
	private String mCharacterText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sina_share);

		mShareType = getIntent().getIntExtra(KEY_SHARE_TYPE, SHARE_CLIENT);
		mCharacterText = getIntent().getStringExtra(KEY_CHARACTER_TEXT);
		Log.d("CharacterBaseAdapter", "mCharacterText:" + mCharacterText);

		initViews();

		// 创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this,
				Constants.SINA_APP_KEY);

		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		mWeiboShareAPI.registerApp();

		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
		// 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
		// 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
		// 失败返回 false，不调用上述回调
		if (savedInstanceState != null) {
			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
		mWeiboShareAPI.handleWeiboResponse(intent, this);
	}

	private void initViews() {

		edit_share = (EditText) findViewById(R.id.share_text);
		edit_share.setText(mCharacterText);

		but_share = (Button) findViewById(R.id.share_button);
		but_share.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (R.id.share_button == v.getId()) {
			// 点击事件
			sendMessage(edit_share.getText().toString());
		}

	}

	private void sendMessage(String share_string) {
		if (mShareType == SHARE_CLIENT) {
			if (mWeiboShareAPI.isWeiboAppSupportAPI()) {

				sendSingleMessage(share_string);

			} else {
				Toast.makeText(this, "微博客户端不支持 SDK 分享或微博客户端未安装或微博客户端是非官方版本。",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private void sendSingleMessage(String share_string) {
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		// 1. 初始化微博的分享消息
		weiboMessage.textObject = getTextObj(share_string);

		// 2. 初始化从第三方到微博的消息请求
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;

		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(WBShareActivity.this, request);
		
//		finish();
	}

	private TextObject getTextObj(String share_string) {
		TextObject textObject = new TextObject();
		textObject.text = share_string;

		return textObject;

	}

	@Override
	public void onResponse(BaseResponse arg0) {
		switch (arg0.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(this, "分享失败" + "Error Message: " + arg0.errMsg,
					Toast.LENGTH_LONG).show();
			break;

		}
	}

}
