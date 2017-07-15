package com.zhouyuming.animee.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class FileUtils {

	private static File mRoot = Environment.getExternalStorageDirectory();

	public FileUtils() {
		Log.i("info", mRoot.getPath());
	}

}
