package com.zhouyuming.animee.param;

import com.zhouyuming.animee.R;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public enum CopyrightParams {

	YOUKU(R.drawable.ic_youku),
	IQIYI(R.drawable.ic_iqiyi),
	PPTV(R.drawable.ic_pptv),
	BILIBILI(R.drawable.ic_bilibili),
	ACFUN(R.drawable.ic_acfun),
	TENCENT(R.drawable.ic_tencent),
	NONE(R.drawable.ic_baiduyun);

	private int resId;

	CopyrightParams(int resId) {
		this.resId = resId;
	}

	public int getResId() {
		return resId;
	}
}
