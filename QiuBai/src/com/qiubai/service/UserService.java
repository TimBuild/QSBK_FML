package com.qiubai.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.qiubai.entity.User;
import com.qiubai.util.HttpUtil;
import com.qiubai.util.ReadPropertiesUtil;
import com.qiubai.util.SharedPreferencesUtil;

public class UserService {
	
	private String protocol;
	private String ip;
	private String port;
	
	public UserService(){
		protocol = ReadPropertiesUtil.read("config", "protocol");
		ip = ReadPropertiesUtil.read("config", "ip");
		port = ReadPropertiesUtil.read("config", "port");
		
	}
	
	public String login(String userid, String password){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userid);
		params.put("password", password);
		return HttpUtil.doPost(params, protocol + ip + ":" + port + ReadPropertiesUtil.read("link", "login"));
	}
	
	public User parseLoginJson(String json){
		User user = new User();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			user.setUserid(jsonObject.getString("userid"));
			user.setNickname(jsonObject.getString("nickname"));
			user.setToken(jsonObject.getString("token"));
			user.setIcon(jsonObject.getString("icon"));
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
		return HttpUtil.doPost(params, protocol + ip + ":" + port + ReadPropertiesUtil.read("link", "register"));
	}
	
	public String forgetPassword(String email){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", email);
		return HttpUtil.doPost(params, protocol + ip + ":" + port + ReadPropertiesUtil.read("link", "forgetPassword"));
	}
	
	public void logout(Context context){
		SharedPreferencesUtil spUtil = new SharedPreferencesUtil(context);
		spUtil.removeToken();
		spUtil.removeUserid();
	}
	
	public String changeNickname(String nickname){
		return null;
	}
	
	public String changePassword(String password){
		return null;
	}
	
}
