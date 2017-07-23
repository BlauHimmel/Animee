package com.zhouyuming.animee.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.model.Model;
import com.zhouyuming.animee.param.UpdateParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class FileUtils {

	private static boolean mIsInitialized = false;
	private static File mRoot;
	private static File[] mAnimeeFiles;

	public static void init(Context context) {
		try {
			mRoot = context.getFilesDir();
			mIsInitialized = true;
			mAnimeeFiles = new File[7];
			Log.i("Utils", "[FileUtils]initialized");
			for (int i = 0; i < 7; i++) {
				mAnimeeFiles[i] = new File(mRoot, "animee_data_" + (i + 1));
				mAnimeeFiles[i].createNewFile();
				Log.i("Utils", "[FileUtils]init file : " + mAnimeeFiles[i].getName() + " path : " + mAnimeeFiles[i].getPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void clear() {
		try {
			for (int i = 0; i < 7; i++) {
				mAnimeeFiles[i].delete();
				mAnimeeFiles[i].createNewFile();
			}
			Log.i("Utils", "[FileUtils]delete all files");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T extends Model> boolean updateFile(T model, Class<T> classOfModel) {

		if (!mIsInitialized || model == null) {
			Log.i("Utils", "[FileUtils]not initialized");
			return false;
		}

		try {
			int week = model.getPrimaryKey1();
			List<T> models = readFiles(week, classOfModel);

			if (!models.contains(model)) {
				models.add(model);
				Collections.sort(models, (model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
				String json = JsonUtils.getJson(models);
				FileOutputStream fos = new FileOutputStream(mAnimeeFiles[week - 1]);
				fos.write(json.getBytes());
				fos.close();
				Log.i("Utils", "[FileUtils]update file " + mAnimeeFiles[week - 1].getName() + " model : " + model);
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static <T extends Model> boolean updateFiles(List<T> models, Class<T> classOfModel, UpdateParams updateParams) {

		if (updateParams == UpdateParams.OVERRIDE) {
			return updateFilesOverride(models);
		} else if (updateParams == UpdateParams.APPEND) {
			return updateFilesAppend(models, classOfModel);
		} else {
			return false;
		}
	}

	private static <T extends Model> boolean updateFilesOverride(List<T> models) {

		if (!mIsInitialized || models == null) {
			Log.i("Utils", "[FileUtils]not initialized");
			return false;
		}

		SparseArray<List<T>> weeks = new SparseArray<>();
		for (int i = 0; i < 7; i++) {
			weeks.put(i, new ArrayList<>());
		}

		for (T model : models) {
			weeks.get(model.getPrimaryKey1() - 1).add(model);
		}

		try {
			for (int i = 0; i < 7; i++) {
				FileOutputStream fos = new FileOutputStream(mAnimeeFiles[i]);
				Collections.sort(weeks.get(i), (model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
				List<T> sortedModels = weeks.get(i);
				String json = JsonUtils.getJson(sortedModels);
				fos.write(json.getBytes());
				fos.close();
				Log.i("Utils", "[FileUtils]update file " + mAnimeeFiles[i].getName());
				for (int j = 0; j < sortedModels.size(); j++) {
					Log.i("Utils", "[FileUtils]model[" + j + "]" + sortedModels.get(j));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static <T extends Model> boolean updateFilesAppend(List<T> models, Class<T> classOfModel) {

		if (!mIsInitialized || models == null) {
			Log.i("Utils", "[FileUtils]not initialized");
			return false;
		}

		SparseArray<List<T>> weeks = readFiles(classOfModel);

		boolean isUpdated = false;

		for (T model : models) {
			List<T> week = weeks.get(model.getPrimaryKey1() - 1);
			if (!week.contains(model)) {
				week.add(model);
				isUpdated = true;
			}
		}

		if (!isUpdated) {
			return false;
		}

		try {
			for (int i = 0; i < 7; i++) {
				FileOutputStream fos = new FileOutputStream(mAnimeeFiles[i]);
				Collections.sort(weeks.get(i), (model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
				List<T> sortedModels = weeks.get(i);
				String json = JsonUtils.getJson(sortedModels);
				fos.write(json.getBytes());
				fos.close();
				Log.i("Utils", "[FileUtils]update file " + mAnimeeFiles[i].getName());
				for (int j = 0; j < sortedModels.size(); j++) {
					Log.i("Utils", "[FileUtils]model[" + j + "]" + sortedModels.get(i));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	@NonNull
	public static <T extends Model> SparseArray<List<T>> readFiles(Class<T> classOfModel) {

		SparseArray<List<T>> weeks = new SparseArray<>();
		for (int i = 0; i < 7; i++) {
			weeks.put(i, new ArrayList<>());
		}

		if (!mIsInitialized) {
			Log.i("Utils", "[FileUtils]not initialized");
			return weeks;
		}

		try {
			for (int i = 0; i < 7; i++) {
				FileInputStream fis = new FileInputStream(mAnimeeFiles[i]);
				StringBuilder sb = new StringBuilder();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = fis.read(buffer)) != -1) {
					sb.append(new String(buffer, 0, length));
				}
				List<T> models = JsonUtils.getModelArray(sb.toString(), classOfModel);
				weeks.put(i, models);
				fis.close();
				Log.i("Utils", "[FileUtils]read file " + mAnimeeFiles[i].getName() + " json : " + sb.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weeks;
	}

	@NonNull
	public static <T extends Model> List<T> readFiles(int week, Class<T> classOfModel) {

		List<T> day = new ArrayList<>();

		if (!mIsInitialized || week < 1 || week > 7) {
			Log.i("Utils", "[FileUtils]not initialized");
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
			day = JsonUtils.getModelArray(sb.toString(), classOfModel);
			fis.close();
			Log.i("Utils", "[FileUtils]read file " + mAnimeeFiles[week - 1].getName() + " json : " + sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return day;
	}

	public static <T extends Model> void remove(T model, Class<T> classOfModel) {
		List<T> day = readFiles(model.getPrimaryKey1(), classOfModel);
		if (day.contains(model)) {
			try {
				day.remove(model);
				FileOutputStream fos = new FileOutputStream(mAnimeeFiles[model.getPrimaryKey1() - 1]);
				String json = JsonUtils.getJson(day);
				fos.write(json.getBytes());
				fos.close();
				Log.i("Utils", "[FileUtils]remove model : " + model);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T extends Model> boolean replace(T from, T to, Class<T> classOfModel) {
		try {
			if (from.getPrimaryKey1() == to.getPrimaryKey1()) {
				List<T> day = readFiles(from.getPrimaryKey1(), classOfModel);
				if (day.contains(from)) {
					day.remove(from);
					day.add(to);
					Collections.sort(day, (model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
					String json = JsonUtils.getJson(day);
					FileOutputStream fos = new FileOutputStream(mAnimeeFiles[to.getPrimaryKey1() - 1]);
					fos.write(json.getBytes());
					fos.close();
					Log.i("Utils", "[FileUtils]replace model : " + from + " with model : " + to + "on file : " + mAnimeeFiles[to.getPrimaryKey1() - 1].getName());
					return true;
				}
			} else {
				List<T> fromDay = readFiles(from.getPrimaryKey1(), classOfModel);
				List<T> toDay = readFiles(to.getPrimaryKey1(), classOfModel);
				if (fromDay.contains(from)) {
					fromDay.remove(from);
					toDay.add(to);
					Collections.sort(toDay, (model1, model2) -> model1.getPrimaryKey2() - model2.getPrimaryKey2());
					String fromJson = JsonUtils.getJson(fromDay);
					String toJson = JsonUtils.getJson(toDay);
					FileOutputStream fos = new FileOutputStream(mAnimeeFiles[from.getPrimaryKey1() - 1]);
					fos.write(fromJson.getBytes());
					fos.close();
					fos = new FileOutputStream(mAnimeeFiles[to.getPrimaryKey1() - 1]);
					fos.write(toJson.getBytes());
					fos.close();
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
