package com.zhouyuming.animee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zhouyuming.animee.param.CopyrightParams;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeListModel extends Model<Integer>{

	@Expose
	@SerializedName("url")
	private String iconUrl;

	@Expose
	@SerializedName("name")
	private String name;

	@Expose
	@SerializedName("date")
	private long startDate;

	@Expose
	@SerializedName("week")
	private int week;

	@Expose
	@SerializedName("copyright")
	private CopyrightParams copyright;

	private String updateTime;

	public AnimeListModel() {
		super(0);
	}

	public AnimeListModel(String iconUrl, String name, long startDate, int week, CopyrightParams copyright) {
		super(week);
		this.iconUrl = iconUrl;
		this.name = name;
		this.startDate = startDate;
		this.week = week;
		this.copyright = copyright;
		this.updateTime = new SimpleDateFormat("HH:MM").format(new Date());
	}

	public int getEpisode() {
		int day = (int) (System.currentTimeMillis() - startDate) / (1000 * 3600 * 24);
		return day / 7 + 1;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public CopyrightParams getCopyright() {
		return copyright;
	}

	public void setCopyright(CopyrightParams copyright) {
		this.copyright = copyright;
	}

	@Override
	public String toString() {
		return "AnimeListModel{" +
				"iconUrl='" + iconUrl + '\'' +
				", name='" + name + '\'' +
				", startDate=" + startDate +
				", week=" + week +
				", copyright=" + copyright +
				", updateTime='" + updateTime + '\'' +
				'}';
	}
}