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
	
	public void userRegister(final String email, final String nickname, final String password, final Handler handler){
		final String url = "http://192.168.1.78:8082/QiuBaiServer/rest/UserService/register";
		new Thread(){
			public void run() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", email);
				params.put("nickname", nickname);
				params.put("password", password);
				String result = HttpUtil.doPost(params, url);
				Message msg = handler.obtainMessage(1);
				msg.obj = result;
				handler.sendMessage(msg);
			};
		}.start();
		
	}
	
	public String changeNickname(String nickname){
		return null;
	}
	
	public String changePassword(String password){
		return null;
	}
	
}
