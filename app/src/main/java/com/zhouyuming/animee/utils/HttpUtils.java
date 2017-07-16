package com.zhouyuming.animee.utils;

import com.zhouyuming.animee.retrofit.QrCodeService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class HttpUtils {

	public static void httpGet(String url, Callback<ResponseBody> callback) {
		if (url.charAt(url.length() - 1) != '/') {
			url = url + "/";
		}
		Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
		QrCodeService qrCodeService = retrofit.create(QrCodeService.class);
		Call<ResponseBody> call = qrCodeService.getContent();
		call.enqueue(callback);
	}
}
