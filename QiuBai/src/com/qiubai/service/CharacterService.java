package com.qiubai.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qiubai.entity.Character;
import com.qiubai.util.DateUtil;

public class CharacterService {

	/**
	 * 通过uri请求Character数据
	 * 
	 * @param uri
	 * @return json数据的格式
	 */
	public String getCharacter(String uri) {

		try {
			HttpGet httpRequest = new HttpGet(uri);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strResult = EntityUtils.toString(
						httpResponse.getEntity(), "utf-8");
				return strResult;
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Character> getCharacterByJson(String json) {
		List<Character> listChar = new ArrayList<Character>();
		Character character = null;

		DateUtil std = new DateUtil();
		if (json != null) {
			try {
				JSONArray jsonObjs = new JSONObject(json)
						.getJSONArray("character");
				for (int i = 0; i < jsonObjs.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonObjs
							.getJSONObject(i);

					character = new Character();
					character.setId(jsonObject.getInt("id"));
					character.setUserid(jsonObject.getInt("userid"));
					character.setChar_title(jsonObject.getString("char_title"));
					character.setChar_context(jsonObject
							.getString("char_context"));
					character.setChar_support(jsonObject
							.getString("char_support"));
					character.setChar_oppose(jsonObject
							.getString("char_oppose"));
					character.setChar_time(jsonObject.getString("char_time"));
					character.setChar_comment(jsonObject
							.getString("char_comment"));
					listChar.add(character);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		System.out.println(listChar.size());

		return listChar;

	}
	/*
	 * public static void main(String[] args) { CharacterService charService =
	 * new CharacterService(); String uri =
	 * "http://192.168.1.69:8081/QiuBaiServer/rest/CharacterService/getCharacters"
	 * ; charService.getCharacterByJson(charService.getCharacter(uri)); }
	 */
}
