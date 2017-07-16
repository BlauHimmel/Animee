package com.zhouyuming.animee.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhouyuming.animee.model.Model;
import com.zhouyuming.animee.model.ModelArray;

import java.util.List;

public class JsonUtils {

	private static Gson mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	public static <T extends Model> String getJson(T model) {
		return mGson.toJson(model);
	}

	public static <T extends Model> String getJson(ModelArray<T> models) {
		return mGson.toJson(models);
	}

	public static <T extends Model> T getModel(String json, Class<T> classOfModel) {
		return mGson.fromJson(json, classOfModel);
	}

	public static <T extends Model> List<T> getModelArray(String json) {
		 return mGson.fromJson(json, new TypeToken<List<T>>(){}.getType());
	}
}