package com.qiubai.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpStatus;
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
	
	public boolean logout(Context context){
		SharedPreferencesUtil spUtil = new SharedPreferencesUtil(context);
		if(spUtil.removeToken() && spUtil.removeUserid()){
			return true;
		} else {
			return false;
		}
	}
	
	public String changeNickname(String userid, String token, String nickname){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userid);
		params.put("nickname", nickname);
		return HttpUtil.doPost(params, protocol + ip + ":" + port + ReadPropertiesUtil.read("link", "changeNickname") + token);
	}
	
	public String changePassword(String userid, String token, String originPassword, String newPassword){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userid);
		params.put("originPassword", originPassword);
		params.put("newPassword", newPassword);
		return HttpUtil.doPost(params, protocol + ip + ":" + port + ReadPropertiesUtil.read("link", "changePassword") + token);
	}
	
	public String uploadIcon(){
		try {
			URL url = new URL("");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + UUID.randomUUID().toString());
			conn.getOutputStream();
			if(conn.getResponseCode() == HttpStatus.SC_OK){
				System.out.println("ok");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
