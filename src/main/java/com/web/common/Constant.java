package com.web.common;


/**
 * Created by cheng on 2015/4/10.
 */
public class Constant {
	 /**
	 * jsesssionid_cookie过期时间:1天,单位秒
	 */
	public static final int JSEESIONID_COOKIE_MAX_AGE= 86400;
	public static final String COOKIE_DOMAIN = "/";
	//前台用户信息标示
	public static final String Session_User = "session_user";
	/**
	 * 加密的accesstoken
	 */
	public static final String SESSION_ACCESSTOKEN_KEY = "uenjeokwnqkm";
	/**
	 * accesstoken 的有效期
	 */
	public static final long SESSION_ACCESSTOKEN_KEY_TIME = 86400;
	public static final String SESSION_FRONT_KEY = "1" ;
	/**
	 * 分页对象
	 */
	public static final String PAGE_VIEW = "pageView";

}
