package com.zhouyuming.animee.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhouyuming.animee.model.AnimeListModel;

public class JsonUtils {

	private static Gson mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	public static <T> String getJson(T model) {
		return mGson.toJson(model);
	}

	public static <T> T getModel(String json, Class<T> classOfT) {
		return mGson.fromJson(json, classOfT);
	}
}