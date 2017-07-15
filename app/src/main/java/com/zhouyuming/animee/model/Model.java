package com.zhouyuming.animee.model;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class Model<T> {

	protected T primaryKey;

	public Model(T primaryKey) {
		this.primaryKey = primaryKey;
	}

	public T getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(T primaryKey) {
		this.primaryKey = primaryKey;
	}
}
