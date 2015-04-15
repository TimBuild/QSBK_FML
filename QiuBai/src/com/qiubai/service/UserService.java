package com.qiubai.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.qiubai.entity.User;
import com.qiubai.util.HttpUtil;

public class UserService {
	
	public String login(String email, String password){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);
		return HttpUtil.doPost(params, "http://192.168.31.103:8080/QiuBaiServer/rest/UserService/login");
	}
	
	public User parseLoginJson(String json){
		User user = new User();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			user.setUserid(jsonObject.getString("userid"));
			user.setToken(jsonObject.getString("token"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public String register(String email, String nickname, String password){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("nickname", nickname);
		params.put("password", password);
		return HttpUtil.doPost(params, "http://192.168.31.103:8080/QiuBaiServer/rest/UserService/register");
	}
	
	public String forgetPassword(String email){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		return HttpUtil.doPost(params, "http://192.168.31.103:8080/QiuBaiServer/rest/UserService/forgetPassword");
	}
	
	public String publishComment(String token, String email, String content){
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("content", content);
		return HttpUtil.doPost(params, "http://192.168.1.78:8080/QiuBaiServer/rest/UserService/publishComment/" + token);
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
