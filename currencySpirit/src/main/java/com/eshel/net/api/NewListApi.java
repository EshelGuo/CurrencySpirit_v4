package com.eshel.net.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * createBy Eshel
 * createTime: 2017/10/5 02:58
 * desc: 新闻类别网络请求
 * https://fengzhihen.com/btcapp/newsInfo?start=1&count=20    // 精华专栏
 * https://fengzhihen.com/btcapp/weiboInfo?start=1&count=20    // 微博专栏
 * https://fengzhihen.com/btcapp/coinInfo?start=1&count=20    // 牛币专栏
 */

public interface NewListApi {
//	@Headers("Cache-Control: public, max-age=3600")
	@GET("newsInfo")
	Call<ResponseBody> essence(@Query("start") int start,@Query("count")int count);
//	@Headers("Cache-Control: public, max-age=3600")
	@GET("weiboInfo")
	Call<ResponseBody> information(@Query("start") int start,@Query("count")int count);
//	@Headers("Cache-Control: public, max-age=3600")
	@GET("coinInfo")
	Call<ResponseBody> coinInfo(@Query("start") int start,@Query("count")int count);
}
