package com.zhouyuming.animee.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zhouyuming.animee.model.Model;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

	private static Gson mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	@NonNull
	public static <T extends Model> String getJson(@NonNull T model) {
		String json = mGson.toJson(model);
		Log.i("Utils", "[JsonUtils]get json from model  -> " + json);
		return json;
	}

	@NonNull
	public static <T extends Model> String getJson(@NonNull List<T> models) {
		String json = mGson.toJson(models);
		Log.i("Utils", "[JsonUtils]get json from models -> " + json);
		return json;
	}

	@Nullable
	public static <T extends Model> T getModel(@NonNull String json, @NonNull Class<T> classOfModel) {
		T model = mGson.fromJson(json, classOfModel);
		Log.i("Utils", "[JsonUtils]get model from json -> " + model);
		return model;
	}

	@NonNull
	public static <T extends Model> List<T> getModelArray(@NonNull String json, @NonNull Class<T> classOfModel) {
		List<T> models = new ArrayList<>();
		if (json.equals("")) {
			return models;
		}
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (JsonElement element : array) {
			models.add(new Gson().fromJson(element, classOfModel));
		}
		Log.i("Utils", "[JsonUtils]get models from json -> size : " + models.size());
		for (int i = 0; i < models.size(); i++) {
			Log.i("Utils", "[JsonUtils]model["+ i + "] -> : " + models.get(i));
		}
		return models;
	}
}