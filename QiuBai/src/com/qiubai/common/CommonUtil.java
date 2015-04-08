package com.qiubai.common;

public class CommonUtil {
	/**
	 * 文字版块从服务器获取的数据
	 */
	private static String CHARACTER_URL = "http://192.168.1.69:8081/QiuBaiServer/rest/CharacterService/getCharacters";
	private static String ADD_CHARACTER_SUPPORT_OPPOSE = "http://192.168.1.69:8081/QiuBaiServer/rest/CharacterService/addCharacterSupportOppose";

	public static String getCharacterUrl() {
		return CHARACTER_URL;
	}

	public static String getADD_CHARACTER_SUPPORT_OPPOSE() {
		return ADD_CHARACTER_SUPPORT_OPPOSE;
	}

}
