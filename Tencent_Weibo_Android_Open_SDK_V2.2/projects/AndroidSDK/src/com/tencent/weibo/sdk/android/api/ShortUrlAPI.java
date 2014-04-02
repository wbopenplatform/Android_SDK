package com.tencent.weibo.sdk.android.api;

import android.content.Context;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;

public class ShortUrlAPI extends BaseAPI {
	public ShortUrlAPI(AccountModel account) {
		super(account);
	}

	/**
	 * 短链接转换成长链接url
	 */
	private static final String SERVER_URL_SHORT2LONG = API_SERVER
			+ "/short_url/expand";
	/**
	 * 长链接转换成短链接url
	 */
	private static final String SERVER_URL_LONG2SHORT = API_SERVER
			+ "/short_url/shorten";

	/**
	 * 长链接转换成短链接
	 * 
	 * @param context
	 *            上下文
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param longUrl
	 *            原始URL
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getShortUrl(Context context, String format, String longUrl,
			HttpCallback mCallBack, Class<? extends BaseVO> mTargetClass,
			int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));

		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("long_url", longUrl);
		startRequest(context, SERVER_URL_LONG2SHORT, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_GET, resultType);
	}

	/**
	 * 短链接转换成长链接
	 * 
	 * @param context
	 *            上下文
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param shortUrl
	 *            待转换的短URL
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void expandShortUrl(Context context, String format, String shortUrl,
			HttpCallback mCallBack, Class<? extends BaseVO> mTargetClass,
			int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("short_url", shortUrl);
		startRequest(context, SERVER_URL_SHORT2LONG, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_GET, resultType);
	}

}
