package com.qiubai.dao;

public interface Constants {

	/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
	public static final String SINA_APP_KEY = "426108333";

	/**
	 * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
	 * 
	 * <p>
	 * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
	 * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
	 * </p>
	 */
	public static final String SINA_REDIRECT_URL = "https://www.sina.com.cn";
	
	public static final String WEIXIN_APP_ID = "wxf1ad48abe932ead3";
	
	public static final String QQ_APP_ID = "1104632167";

}
