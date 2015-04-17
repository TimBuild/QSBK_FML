package com.qiubai.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits")
public class SharedPreferencesUtil {

	private SharedPreferences sharedPreferences;
	private Context context;
	private static final String QIUBAIXML = "qiubai";
	public SharedPreferencesUtil(Context context){
		this.context = context;
	}
	
	/**
	 * store token
	 * @param token
	 */
	public void storeToken(String token) {
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("token", token);
		editor.commit();
	}
	
	/**
	 * get token
	 * @return token
	 */
	public String getToken(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String token = sharedPreferences.getString("token", null);
		return token;
	}
	
	/**
	 * get font
	 * @return font
	 */
	public String getFont(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String font = sharedPreferences.getString("font", null);
		return font;
	}
	
	/**
	 * store font
	 * @param font
	 */
	public void storeFont(String font){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("font", font);
		editor.commit();
	}
	
	/**
	 * get userid
	 * @return userid
	 */
	public String getUserid(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String email = sharedPreferences.getString("userid", null);
		return email;
	}
	
	/**
	 * store userid
	 * @param userid
	 */
	public void storeUserid(String userid){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("userid", userid);
		editor.commit();
	}
	
	/**
	 * get nickname
	 * @return nickname
	 */
	public String getNickname(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String nickname = sharedPreferences.getString("nickname", null);
		return nickname;
	}
	
	/**
	 * store nickname
	 * @param nickname
	 */
	public void storeNickname(String nickname){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("nickname", nickname);
		editor.commit();
	}
	
	/**
	 * get icon
	 * @return icon
	 */
	public String getIcon(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String icon = sharedPreferences.getString("icon", null);
		return icon;
	}
	
	/**
	 * store icon
	 * @param icon
	 */
	public void storeIcon(String icon){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("icon", icon);
		editor.commit();
	}
}
