package com.zhouyuming.animee.utils;

import android.content.Context;

import java.io.File;
import java.util.Comparator;
import java.util.List;


/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class FileUtils {

	private static boolean mIsInitialized = false;
	private static File mRoot;
	private static File[] mAnimeeFiles;

	public static void init(Context context) {
		mRoot = context.getFilesDir();
		mIsInitialized = true;
		mAnimeeFiles  = new File[7];
		for (int i = 0; i < 7; i++) {
			mAnimeeFiles[i] = new File(mRoot, "animee_data_" + (i + 1));
		}
	}

	public static <T> void update(int week, List<T> models) {

		if (!mIsInitialized) {
			return;
		}

		

		String[] jsons = new String[models.size()];
		for (int i = 0; i < models.size(); i++) {
			jsons[i] = JsonUtils.getJson(models);
		}
	}


}
