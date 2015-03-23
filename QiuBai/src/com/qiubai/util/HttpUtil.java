package com.qiubai.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	public static void doPost(){
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("email", "test@126.com");
		params.put("nickname", "tester");
		params.put("password", "123456");
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://192.168.1.78:8080/QiuBai/rest/UserService/register");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		try {
			parameters.add(new BasicNameValuePair("email", "test@126.com"));
			parameters.add(new BasicNameValuePair("nickname", "tester"));
			parameters.add(new BasicNameValuePair("password", "123456"));
			HttpEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			String str = EntityUtils.toString(response.getEntity());
			System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
