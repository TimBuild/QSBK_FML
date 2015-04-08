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

}
