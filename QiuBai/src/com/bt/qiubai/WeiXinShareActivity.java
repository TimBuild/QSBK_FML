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

import com.qiubai.dao.Constants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiXinShareActivity extends Activity implements IWXAPIEventHandler{

	// private static String APP_ID = "wx688b14884ad5efde";

	public static final String KEY_WEIXIN_TYPE = "key_weixin_type";
	public static final String KEY_WEIXIN_FRIENDS = "key_weixin_friends";
	public static final String KEY_WEIXIN_ZONE = "key_weixin_zone";
	public static final String KEY_CHARACTER_TEXT = "key_character_text";

	private EditText share_text;
	private Button weixin_share_button, weixin_share_friends;

	private IWXAPI api;

	private static String TAG = "WeiXinShareActivity";
	private String text, type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);

//		setContentView(R.layout.activity_weixin_share);

		text = getIntent().getExtras().getString(KEY_CHARACTER_TEXT);
		type = getIntent().getExtras().getString(KEY_WEIXIN_TYPE);

//		initView();

		Log.d(TAG, "type: " + type + " text: " + text);

		if (type.equals(KEY_WEIXIN_FRIENDS)) {
			if (api.isWXAppInstalled()) {
				if (api.isWXAppSupportAPI()) {
					SendMessageToFriend(text);
				} else {
					Toast.makeText(WeiXinShareActivity.this, "该版本微信版本不支持微信分享",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				Toast.makeText(WeiXinShareActivity.this, "请安装最新版本的微信",
						Toast.LENGTH_SHORT).show();
				finish();

			}
		} else if (type.equals(KEY_WEIXIN_ZONE)) {
			if (api.isWXAppInstalled()) {
				if (api.isWXAppSupportAPI()) {

					SendMessageToZone(text);
				} else {
					Toast.makeText(WeiXinShareActivity.this, "该版本微信版本不支持微信分享",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				Toast.makeText(WeiXinShareActivity.this, "请安装最新版本的微信",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

		api.handleIntent(getIntent(), this);

	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	private void initView() {
		share_text = (EditText) findViewById(R.id.weixin_share_edit);

		share_text.setText(text);

		weixin_share_button = (Button) findViewById(R.id.weixin_share_button);
		weixin_share_friends = (Button) findViewById(R.id.weixin_share_button_friends);

		weixin_share_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String text = share_text.getText().toString();
				if (text == null || text.length() == 0) {
					return;
				}

				SendMessageToZone(text);
			}

		});
		weixin_share_friends.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String text = share_text.getText().toString();
				if (text == null || text.length() == 0) {
					return;
				}

				SendMessageToFriend(text);
			}
		});

	}

	private void SendMessageToZone(String text) {
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;
		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = text;
		// 构造一个Req
		SendMessageToWX.Req requset = new SendMessageToWX.Req();
		// transaction字段用于唯一标识一个请求
		requset.transaction = String.valueOf(System.currentTimeMillis());
		requset.message = msg;
		requset.scene = SendMessageToWX.Req.WXSceneTimeline;// 朋友圈

		// 调用api接口发送数据到微信
		api.sendReq(requset);

		finish();

	}

	private void SendMessageToFriend(String text) {
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;
		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = text;
		// 构造一个Req
		SendMessageToWX.Req requset = new SendMessageToWX.Req();
		// transaction字段用于唯一标识一个请求
		requset.transaction = String.valueOf(System.currentTimeMillis());
		requset.message = msg;
		requset.scene = SendMessageToWX.Req.WXSceneSession;// 会话中

		// 调用api接口发送数据到微信
		api.sendReq(requset);

		finish();
	}

	@Override
	public void onReq(BaseReq arg0) {
		Log.d(TAG, "微信发送请求Request：");
	}

	@Override
	public void onResp(BaseResp arg0) {
		String result = "";
		Log.d(TAG, "微信发送请求onResp：");
		switch (arg0.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "发送成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "取消发送";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "发送被拒绝";
			break;
		default:
			result = "发送返回";
			break;
		}
		
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	}
}
