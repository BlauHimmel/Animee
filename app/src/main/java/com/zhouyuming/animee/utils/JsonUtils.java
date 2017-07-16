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
		return mGson.toJson(model);
	}

	@NonNull
	public static <T extends Model> String getJson(@NonNull List<T> models) {
		return mGson.toJson(models);
	}

	@Nullable
	public static <T extends Model> T getModel(@NonNull String json, @NonNull Class<T> classOfModel) {
		return mGson.fromJson(json, classOfModel);
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
		return models;
	}
}