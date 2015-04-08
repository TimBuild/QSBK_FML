package com.qiubai.service;

import java.util.HashMap;
import java.util.Map;

import com.qiubai.util.HttpUtil;

public class UserService {
	
	public String login(String email, String password){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);
		return HttpUtil.doPost(params, "http://192.168.1.78:8080/QiuBaiServer/rest/UserService/login");
	}
	
	public String register(String email, String nickname, String password){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("nickname", nickname);
		params.put("password", password);
		return HttpUtil.doPost(params, "http://192.168.1.78:8080/QiuBaiServer/rest/UserService/register");
	}
	
	public String logout(){
		return null;
	}
	
	public String changeNickname(String nickname){
		return null;
	}
	
	public String changePassword(String password){
		return null;
	}
	
}
