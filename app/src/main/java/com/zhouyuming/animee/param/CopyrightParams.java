package com.zhouyuming.animee.param;

import com.zhouyuming.animee.R;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public enum CopyrightParams {

	YOUKU(R.drawable.ic_youku, "Youku"),
	IQIYI(R.drawable.ic_iqiyi, "iQiyi"),
	PPTV(R.drawable.ic_pptv, "PPTV"),
	BILIBILI(R.drawable.ic_bilibili, "Bilibili"),
	ACFUN(R.drawable.ic_acfun, "Acfun"),
	TENCENT(R.drawable.ic_tencent, "Tencent"),
	NONE(R.drawable.ic_baiduyun, "BaiduYun");

	private int resId;
	private String name;

	CopyrightParams(int resId, String name) {
		this.resId = resId;
		this.name = name;
	}

	public int getResId() {
		return resId;
	}

	public String getName() {
		return name;
	}
}
