package com.zhouyuming.animee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zhouyuming.animee.param.CopyrightParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeModel extends Model{

	@Expose
	@SerializedName("url")
	private String iconUrl;

	@Expose
	@SerializedName("name")
	private String name;

	@Expose
	@SerializedName("date")
	private String startDate;	//201707150230->2017-07-15 02:30

	private String updateTime;  //02:30

	@Expose
	@SerializedName("week")
	private int week;

	@Expose
	@SerializedName("copyright")
	private CopyrightParams copyright;

	public AnimeModel() {
		super();
	}

	public AnimeModel(String iconUrl, String name, String startDate, int week, CopyrightParams copyright) {
		super();
		this.iconUrl = iconUrl;
		this.name = name;
		this.startDate = startDate;
		this.week = week;
		this.copyright = copyright;
		this.updateTime = startDate.substring(8, 10) + ":" + startDate.substring(10);
		this.primaryKey1 = week;
		this.primaryKey2 = Integer.parseInt(updateTime.replace(":", ""));
	}

	public int getEpisode() {
		long startTime = System.currentTimeMillis();
		try {
			startTime = new SimpleDateFormat("yyyyMMddHHmm").parse(startDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = (int) (System.currentTimeMillis() - startTime) / (1000 * 3600 * 24);
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
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
}