package com.zhouyuming.animee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class ModelArray<T> {

	@Expose
	@SerializedName("models")
	private List<T> models;

	public ModelArray(List<T> models) {
		this.models = models;
	}

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}
}
