package com.zhouyuming.animee.model;

/**
 * Created by ZhouYuming on 2017/7/15.
 */

public class Model {

	protected int primaryKey1;
	protected int primaryKey2;

	public Model() {
		this.primaryKey1 = 0;
		this.primaryKey2 = 0;
	}

	public Model(int primaryKey1) {
		this.primaryKey1 = primaryKey1;
		this.primaryKey2 = 0;
	}

	public Model(int primaryKey1, int primaryKey2) {
		this.primaryKey1 = primaryKey1;
		this.primaryKey2 = primaryKey2;
	}

	public int getPrimaryKey1() {
		return primaryKey1;
	}

	public void setPrimaryKey1(int primaryKey1) {
		this.primaryKey1 = primaryKey1;
	}

	public int getPrimaryKey2() {
		return primaryKey2;
	}

	public void setPrimaryKey2(int primaryKey2) {
		this.primaryKey2 = primaryKey2;
	}
}
