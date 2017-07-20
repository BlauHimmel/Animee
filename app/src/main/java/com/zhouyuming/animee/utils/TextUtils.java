package com.zhouyuming.animee.utils;

import android.content.Context;

import com.zhouyuming.animee.R;

/**
 * Created by ZhouYuming on 2017/7/19.
 */

public class TextUtils {

	public static String getWeekName(Context context, int week) {
		switch (week) {
			case 1:
				return context.getString(R.string.monday);
			case 2:
				return context.getString(R.string.tuesday);
			case 3:
				return context.getString(R.string.wednesday);
			case 4:
				return context.getString(R.string.thursday);
			case 5:
				return context.getString(R.string.friday);
			case 6:
				return context.getString(R.string.saturday);
			case 7:
				return context.getString(R.string.sunday);
		}
		return "";
	}
}
