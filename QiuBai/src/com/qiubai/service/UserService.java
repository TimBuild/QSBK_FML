package com.qiubai.service;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.os.Handler;
import android.os.Message;

import com.qiubai.util.HttpUtil;

public class UserService {
	
	private Handler handler;
	
	public UserService(Handler handler){
		this.handler = handler;
	}
	
	public String login(String email, String password){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);
		return null;
	}
	
	public String logout(){
		return null;
	}
	
	/*public Handler userRegister(final String email, final String nickname, final String password){
		new Thread(){
			public void run() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("nickname", nickname);
				params.put("password", password);
				//return HttpUtil.doPost(params, url);
				Message msg = new Message();
				//msg.
				handler.sendMessage(msg);
			};
		}.start();
		
	}*/
	
	public String changeNickname(String nickname){
		return null;
	}
	
	public String changePassword(String password){
		return null;
	}
	
}
