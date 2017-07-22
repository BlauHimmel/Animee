package com.zhouyuming.animee.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zhouyuming.animee.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class RecordUtils {

	public static void store(Context context, String name, int episode, boolean isMarked) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.date_format_2));
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		String date = sdf.format(calendar.getTime());
		SharedPreferences.Editor editor = sp.edit();
		if (isMarked) {
			editor.putInt(String.valueOf(episode), 1);
		} else {
			editor.putInt(String.valueOf(episode), 0);
		}
		editor.putString(String.valueOf(episode) + "_date", date);
		editor.apply();
		Log.i("Utils", "[RecordUtils]store <<" + name + ">> (" + episode + ") marked = " + isMarked);
	}

	public static boolean loadMark(Context context, String name, int episode) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		boolean isMarked = sp.getInt(String.valueOf(episode), 0) == 1;
		Log.i("Utils", "[RecordUtils]loadMark <<" + name + ">> (" + episode + ") marked = " + isMarked);
		return isMarked;
	}

	public static String loadDate(Context context, String name, int episode) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String date = sp.getString(String.valueOf(episode) + "_date", "");
		Log.i("Utils", "[RecordUtils]loadDate <<" + name + ">> (" + episode + ") date = " + date);
		return date;
	}

	public static void remove(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.apply();
	}
}
