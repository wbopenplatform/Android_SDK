package com.tencent.weibo.sdk.android.api;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.network.ReqParam;

public class WeiboAPI extends BaseAPI {
	/**
	 * 多类型发表（可同时发表带音频、视频、图片的微博）请求url
	 */
	private static final String SERVER_URL_ADD = API_SERVER + "/t/add_multi";

	/**
	 * 获取视频信息 请求url
	 */
	private static final String SERVER_URL_VIDEO = API_SERVER
			+ "/t/getvideoinfo";

	/**
	 * 发表一条微博请求url
	 */
	private static final String SERVER_URL_ADDWEIBO = API_SERVER + "/t/add";//

	/**
	 * 发表一条带图片的微博 请求url
	 */
	private static final String SERVER_URL_ADDPIC = API_SERVER + "/t/add_pic";

	/**
	 * 获取单条微博的转发列表或评论列表请求url
	 */
	private static final String SERVER_URL_RLIST = API_SERVER + "/t/re_list";

	/**
	 * 用图片URL发表带图片的微博请求url
	 */
	private static final String SERVER_URL_ADDPICURL = API_SERVER
			+ "/t/add_pic_url";

	/**
	 * 发送多媒体微博的URL
	 */
	private static final String SERVER_URL_ADDMULTIURL = API_SERVER
			+ "/t/add_multi";

	/**
	 * @param account
	 */
	public WeiboAPI(AccountModel account) {
		super(account);
	}

	/**
	 * 多类型发表（可同时发表带音频、视频、图片的微博）
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            微博内容（若在此处@好友，需正确填写好友的微博账号，而非昵称），不超过140字
	 * @param picUrl
	 *            图片URL，可填空（URL最长为1024字节）
	 * @param videoUrl
	 *            视频URL，可填空（URL最长为1024字节）
	 * @param musicUrl
	 *            音乐URL，可填空（如果填写该字段，则music_title和music_author都必须填写）
	 * @param musicTitle
	 *            歌曲名 （UTF8编码，最长200字节）
	 * @param musicAuthor
	 *            歌曲作者（UTF8编码，最长64字节）
	 * @param mCallBack
	 *            回调对象
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void reAddWeibo(Context context, String content, String picUrl,
			String videoUrl, String musicUrl, String musicTitle,
			String musicAuthor, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("content", content);
		mParam.addParam("pic_url", picUrl);
		mParam.addParam("video_url", videoUrl);
		mParam.addParam("music_url", musicUrl);
		mParam.addParam("music_title", musicTitle);
		mParam.addParam("music_author", musicAuthor);
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("pageflag", "0");
		mParam.addParam("type", "0");
		mParam.addParam("format", "json");// 返回数据的格式
		mParam.addParam("reqnum", "30");
		mParam.addParam("pagetime", "0");
		mParam.addParam("contenttype", "0");
		startRequest(context, SERVER_URL_ADD, mParam, mCallBack, mTargetClass,
				BaseAPI.HTTP_REQUEST_METHOD_POST, resultType);
	}

	/**
	 * 获取视频信息
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            微博内容（若在此处@好友，需正确填写好友的微博账号，而非昵称），不超过140字
	 * @param videoUrl
	 *            视频URL，可填空（URL最长为1024字节）
	 * @param mCallBack
	 *            回调对象
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void getVideoInfo(Context context, String videoUrl,
			HttpCallback mCallBack, Class<? extends BaseVO> mTargetClass,
			int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("format", "json");// 返回数据的格式
		mParam.addParam("video_url", videoUrl);
		startRequest(context, SERVER_URL_VIDEO, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_POST, resultType);
	}

	/**
	 * 发表一条微博
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            微博内容（若在此处@好友，需正确填写好友的微博账号，而非昵称），不超过140字
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param longitude
	 *            经度，为实数，如113.421234（最多支持10位有效数字，可以填空）不是必填
	 * @param latitude
	 *            纬度，为实数，如22.354231（最多支持10位有效数字，可以填空） 不是必填
	 * @param syncflag
	 *            微博同步到空间分享标记（可选，0-同步，1-不同步，默认为0），目前仅支持oauth1.0鉴权方式 不是必填
	 * @param compatibleflag
	 *            容错标志，支持按位操作，默认为0。 0x20-微博内容长度超过140字则报错 0-以上错误做容错处理，即发表普通微博
	 *            不是必填
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void addWeibo(Context context, String content, String format,
			double longitude, double latitude, int syncflag,
			int compatibleflag, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("scope", "all");
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("content", content);
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		if (longitude != 0d) {
			mParam.addParam("longitude", longitude);
		}
		if (latitude != 0d) {
			mParam.addParam("latitude", latitude);
		}
		mParam.addParam("syncflag", syncflag);
		mParam.addParam("compatibleflag", compatibleflag);
		startRequest(context, SERVER_URL_ADDWEIBO, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_POST, resultType);
	}

	/**
	 * 发表一条带图片的微博
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            微博内容（若在此处@好友，需正确填写好友的微博账号，而非昵称），不超过140字
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param longitude
	 *            经度，为实数，如113.421234（最多支持10位有效数字，可以填空）不是必填
	 * @param latitude
	 *            纬度，为实数，如22.354231（最多支持10位有效数字，可以填空） 不是必填
	 * @param bm
	 *            本地图片bitmap对象
	 * @param syncflag
	 *            微博同步到空间分享标记（可选，0-同步，1-不同步，默认为0），目前仅支持oauth1.0鉴权方式 不是必填
	 * @param compatibleflag
	 *            容错标志，支持按位操作，默认为0。 0x20-微博内容长度超过140字则报错 0-以上错误做容错处理，即发表普通微博
	 *            不是必填
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void addPic(Context context, String content, String format,
			double longitude, double latitude, Bitmap bm, int syncflag,
			int compatibleflag, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("scope", "all");
		mParam.addParam("format", format);// 返回数据的格式
		if (content == null || "".equals(content)) {
			content = "#分享图片#";
		}
		mParam.addParam("content", content);
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		if (longitude != 0d) {
			mParam.addParam("longitude", longitude);
		}
		if (latitude != 0d) {
			mParam.addParam("latitude", latitude);
		}
		mParam.addParam("syncflag", syncflag);
		mParam.addParam("compatibleflag", compatibleflag);
		mParam.setBitmap(bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		mParam.addParam("pic", baos.toByteArray());

		startRequest(context, SERVER_URL_ADDPIC, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_POST, resultType);
	}

	/**
	 * 用图片URL发表带图片的微博
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            微博内容（若在此处@好友，需正确填写好友的微博账号，而非昵称），不超过140字
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param longitude
	 *            经度，为实数，如113.421234（最多支持10位有效数字，可以填空）不是必填
	 * @param latitude
	 *            纬度，为实数，如22.354231（最多支持10位有效数字，可以填空） 不是必填
	 * @param pic
	 *            网络图片url
	 * @param syncflag
	 *            微博同步到空间分享标记（可选，0-同步，1-不同步，默认为0），目前仅支持oauth1.0鉴权方式 不是必填
	 * @param compatibleflag
	 *            容错标志，支持按位操作，默认为0。 0x20-微博内容长度超过140字则报错 0-以上错误做容错处理，即发表普通微博
	 *            不是必填
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 
	 *            BaseVO.TYPE_LIST=1 
	 *            BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 
	 *            BaseVO.TYPE_JSON=4
	 */
	public void addPicUrl(Context context, String content, String format,
			double longitude, double latitude, String pic, int syncflag,
			int compatibleflag, HttpCallback mCallBack,
			Class<? extends BaseVO> mTargetClass, int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("scope", "all");
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("content", content);
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		if (longitude != 0d) {
			mParam.addParam("longitude", longitude);
		}
		if (latitude != 0d) {
			mParam.addParam("latitude", latitude);
		}
		mParam.addParam("syncflag", syncflag);
		mParam.addParam("compatibleflag", compatibleflag);
		mParam.addParam("pic_url", pic);

		startRequest(context, SERVER_URL_ADDPICURL, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_POST, resultType);
	}

	/**
	 * 发表多媒体微博
	 * 
	 * @param context
	 *            上下文
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param content
	 *            微博内容（若在此处@好友，需正确填写好友的微博账号，而非昵称），不超过140字
	 * @param longitude
	 *            经度，为实数，如113.421234（最多支持10位有效数字，可以填空）
	 * @param latitude
	 *            纬度，为实数，如22.354231（最多支持10位有效数字，可以填空）
	 * @param picUrl
	 *            图片URL，可填空（URL最长为1024字节）
	 * @param videoUrl
	 *            视频URL，可填空（URL最长为1024字节）
	 * @param videoTitle
	 * @param musicUrl
	 *            音乐URL，可填空（如果填写该字段，则music_title和music_author都必须填写）
	 * @param musicTitle
	 *            歌曲名 （UTF8编码，最长200字节）
	 * @param musicAuthor
	 *            歌曲作者（UTF8编码，最长64字节）
	 * @param syncFlag
	 *            微博同步到空间分享标记（可选，0-同步，1-不同步，默认为0），目前仅支持oauth1.0鉴权方式
	 * @param compatibleflag
	 *            容错标志，支持按位操作，默认为0。 0x1-下载图片失败则报错 0x2-下载到的图片大小错误则报错
	 *            0x4-检查图片格式不支持则报错 0x8-上传图片失败则报错 0x10-获取视频信息失败则报错
	 *            0x20-微博内容长度超过140字则报错 0x40-url参数非法（如pic_url=aaa）则报错
	 *            0-以上错误均做容错处理，即发表普通微博
	 *            0x1|0x2|0x4|0x8|0x10|0x20|0x40-同旧模式，以上各种情况均报错，不做兼容处理
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 
	 *            BaseVO.TYPE_LIST=1 
	 *            BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 
	 *            BaseVO.TYPE_JSON=4
	 */
	public void addMulti(Context context, String format, String content,
			double longitude, double latitude, String picUrl, String videoUrl,
			String videoTitle, String musicUrl, String musicTitle,
			String musicAuthor, int syncFlag, int compatibleflag,
			HttpCallback mCallBack, Class<? extends BaseVO> mTargetClass,
			int resultType) {
		// FIXME
		ReqParam mParam = new ReqParam();
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("scope", "all");
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("content", content);
		mParam.addParam("clientip", Util.getLocalIPAddress(context));

		if (longitude != 0d && latitude != 0d) {
			mParam.addParam("longitude", longitude);
			mParam.addParam("latitude", latitude);
		}

		if (null != picUrl && !"".equals(picUrl)) {
			mParam.addParam("pic_url", picUrl);
		}

		if (null != videoUrl && !"".equals(videoUrl)) {
			mParam.addParam("video_url", videoUrl);
			if (null != videoTitle && !"".equals(videoTitle)) {
				mParam.addParam("video_title", videoTitle);
			}
		}

		if (null != musicUrl && !"".equals(musicUrl) && null != musicTitle
				&& !"".equals(musicTitle) && null != musicAuthor
				&& !"".equals(musicAuthor)) {
			mParam.addParam("music_url", musicUrl);
			mParam.addParam("music_title", musicTitle);
			mParam.addParam("music_author", musicAuthor);
		}

		mParam.addParam("syncflag", syncFlag);
		mParam.addParam("compatibleflag", compatibleflag);

		startRequest(context, SERVER_URL_ADDMULTIURL, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_POST, resultType);
	}

	/**
	 * 获取单条微博的转发或点评列表
	 * 
	 * @param context
	 *            上下文
	 * @param format
	 *            返回数据的格式（json或xml）
	 * @param flag
	 *            类型标识。0－转播列表，1－点评列表，2－点评与转播列表
	 * @param rootid
	 *            转发或回复的微博根结点id（源微博id）
	 * @param pageflag
	 *            分页标识，用于翻页（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间，与pageflag、twitterid共同使用，实现翻页功能（第一页：填0，向上翻页：
	 *            填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-100条）
	 * @param twitterid
	 *            微博id，与pageflag、pagetime共同使用，实现翻页功能（第1页填0，继续向下翻页，
	 *            填上一次请求返回的最后一条记录id）
	 * @param mCallBack
	 *            回调函数
	 * @param mTargetClass
	 *            返回对象类，如果返回json则为null
	 * @param resultType
	 *            BaseVO.TYPE_BEAN=0 BaseVO.TYPE_LIST=1 BaseVO.TYPE_OBJECT=2
	 *            BaseVO.TYPE_BEAN_LIST=3 BaseVO.TYPE_JSON=4
	 */
	public void reList(Context context, String format, int flag, String rootid,
			int pageflag, String pagetime, int reqnum, String twitterid,
			HttpCallback mCallBack, Class<? extends BaseVO> mTargetClass,
			int resultType) {
		ReqParam mParam = new ReqParam();
		mParam.addParam("oauth_version", "2.a");
		mParam.addParam("oauth_consumer_key",
				Util.getSharePersistent(context, "CLIENT_ID"));
		mParam.addParam("openid", Util.getSharePersistent(context, "OPEN_ID"));
		mParam.addParam("scope", "all");
		mParam.addParam("clientip", Util.getLocalIPAddress(context));
		mParam.addParam("format", format);// 返回数据的格式
		mParam.addParam("flag", flag);
		mParam.addParam("rootid", rootid);
		mParam.addParam("pageflag", pageflag);
		mParam.addParam("pagetime", pagetime);
		mParam.addParam("reqnum", reqnum);
		mParam.addParam("twitterid", twitterid);
		startRequest(context, SERVER_URL_RLIST, mParam, mCallBack,
				mTargetClass, BaseAPI.HTTP_REQUEST_METHOD_GET, resultType);
	}

}
