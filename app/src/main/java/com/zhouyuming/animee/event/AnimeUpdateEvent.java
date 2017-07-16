package com.zhouyuming.animee.event;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class AnimeUpdateEvent {

	int week;
	boolean isUpdated;

	public AnimeUpdateEvent(int week, boolean isUpdated) {
		this.week = week;
		this.isUpdated = isUpdated;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean updated) {
		isUpdated = updated;
	}
}
