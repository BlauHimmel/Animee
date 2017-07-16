package com.zhouyuming.animee.utils;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.zhouyuming.animee.exception.FileUtilsNotInitialzeException;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.model.Model;
import com.zhouyuming.animee.model.ModelArray;
import com.zhouyuming.animee.param.UpdateParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

	private static <T extends Model> void updateFiles(List<T> models, UpdateParams updateParams)
			throws FileUtilsNotInitialzeException {
		if (updateParams == UpdateParams.OVERRIDE) {
			updateFilesOverride(models);
		} else if (updateParams == UpdateParams.MODIFY) {
			updateFilesModify(models);
		}
	}

	private static <T extends Model> void updateFilesOverride(List<T> models)
			throws FileUtilsNotInitialzeException {

		if (!mIsInitialized) {
			throw new FileUtilsNotInitialzeException(
					"FileUtils is used before initialize.Try call init(Context context) first!");
		}

		SparseArray<List<T>> weeks = new SparseArray<>();
		for (int i = 0; i < 7; i++) {
			weeks.put(i, new ArrayList<T>());
		}
		for (T model : models) {
			weeks.get(model.getPrimaryKey1()).add(model);
		}

		try {
			for (int i = 0; i < 7; i++) {
				FileOutputStream fos = new FileOutputStream(mAnimeeFiles[i]);
				weeks.get(i).sort((model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
				List<T> sortedModels = weeks.get(i);
				ModelArray<T> modelArray = new ModelArray<T>(sortedModels);
				String json = JsonUtils.getJson(modelArray);
				fos.write(json.getBytes());
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static <T extends Model> void updateFilesModify(List<T> models)
			throws FileUtilsNotInitialzeException {

		if (!mIsInitialized) {
			throw new FileUtilsNotInitialzeException(
					"FileUtils is used before initialize.Try call init(Context context) first!");
		}
/*
		SparseArray<List<T>> weeks = new SparseArray<>();
		for (int i = 0; i < 7; i++) {
			weeks.put(i, new ArrayList<T>());
		}
		for (T model : models) {
			weeks.get(model.getPrimaryKey1()).add(model);
		}

		try {
			for (int i = 0; i < 7; i++) {
				FileOutputStream fos = new FileOutputStream(mAnimeeFiles[i]);
				weeks.get(i).sort((model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
				List<T> sortedModels = weeks.get(i);
				ModelArray<T> modelArray = new ModelArray<T>(sortedModels);
				String json = JsonUtils.getJson(modelArray);
				fos.write(json.getBytes());
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
	}

	public static <T extends Model> SparseArray<List<T>> readFiles()
			throws FileUtilsNotInitialzeException {

		if (!mIsInitialized) {
			throw new FileUtilsNotInitialzeException(
					"FileUtils is used before initialize.Try call init(Context context) first!");
		}

		SparseArray<List<T>> weeks = new SparseArray<>();

		try {
			for (int i = 0; i < 7; i++) {
				FileInputStream fis = new FileInputStream(mAnimeeFiles[i]);
				StringBuilder sb = new StringBuilder();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = fis.read(buffer)) != -1) {
					sb.append(new String(buffer, 0, length));
				}
				List<T> sortedModels = JsonUtils.getModelArray(sb.toString());
				weeks.put(i, sortedModels);
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weeks;
	}

	public static <T extends Model> List<T> readFiles(int week)
			throws FileUtilsNotInitialzeException {

		if (!mIsInitialized) {
			throw new FileUtilsNotInitialzeException(
					"FileUtils is used before initialize.Try call init(Context context) first!");
		}

		List<T> day = new ArrayList<>();

		if (week < 1 || week > 7) {
			return day;
		}

		try {
			FileInputStream fis = new FileInputStream(mAnimeeFiles[week - 1]);
			StringBuilder sb = new StringBuilder();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = fis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, length));
			}
			day = JsonUtils.getModelArray(sb.toString());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return day;
	}
}
