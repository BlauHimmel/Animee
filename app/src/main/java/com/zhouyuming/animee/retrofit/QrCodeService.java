package com.zhouyuming.animee.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public interface QrCodeService {

	@GET("/")
	Call<ResponseBody> getContent();
}
