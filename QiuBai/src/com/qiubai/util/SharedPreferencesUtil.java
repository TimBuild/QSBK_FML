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
	 * store user login flag
	 * @param flag
	 */
	public void storeUserLoginFlag(String flag){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("isLogin", flag);
		editor.commit();
	}
	
	/**
	 * get user login flag
	 * @return flag
	 */
	public String getUserLoginFlag(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String flag = sharedPreferences.getString("isLogin", null);
		return flag;
	}
	
	/**
	 * get email
	 * @return email
	 */
	public String getEmail(){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		String email = sharedPreferences.getString("email", null);
		return email;
	}
	
	/**
	 * store email
	 * @param email
	 */
	public void storeEmail(String email){
		sharedPreferences = context.getSharedPreferences(QIUBAIXML, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("email", email);
		editor.commit();
	}
}
