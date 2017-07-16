package com.zhouyuming.animee.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class RecordUtils {

	public static void store(Context context, String name, int episode, boolean isMarked) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		if (isMarked) {
			editor.putInt(String.valueOf(episode), 1);
		} else {
			editor.putInt(String.valueOf(episode), 0);
		}
		editor.apply();
	}

	public static boolean load(Context context, String name, int episode) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getInt(String.valueOf(episode), 0) == 1;
	}
}
