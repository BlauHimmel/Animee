package com.zhouyuming.animee.param;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public enum FragmentParams {

	INT_WEEK("int_week"),
	STRING_DATE("string_date");

	private String mValue;

	FragmentParams(String value) {
		mValue = value;
	}

	public String getValue() {
		return mValue;
	}
}
