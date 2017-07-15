package com.zhouyuming.animee.param;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public enum BundleParams {

	INT_WEEK("int_week");

	private String mValue;

	BundleParams(String value) {
		mValue = value;
	}

	public String getValue() {
		return mValue;
	}
}
