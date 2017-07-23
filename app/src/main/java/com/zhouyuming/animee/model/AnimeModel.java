package com.zhouyuming.animee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zhouyuming.animee.param.CopyrightParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeModel implements Model{

	@Expose
	@SerializedName("url")
	private String iconUrl;

	@Expose
	@SerializedName("name")
	private String name;

	@Expose
	@SerializedName("date")
	private String startDate;	//201707150230->2017-07-15 02:30

	@Expose
	@SerializedName("copyright")
	private CopyrightParams copyright;

	@Expose
	@SerializedName("total")
	private int total;

	private int week = -1;

	private int episode = -1;

	public AnimeModel() {

	}

	public AnimeModel(String iconUrl, String name, String startDate, CopyrightParams copyright, int total) {
		this.iconUrl = iconUrl;
		this.name = name;
		this.startDate = startDate;
		this.copyright = copyright;
		this.total = total;
	}

	public int getEpisode() {
		long startTime = System.currentTimeMillis();
		try {
			startTime = new SimpleDateFormat("yyyyMMddHHmm").parse(startDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = (int) ((System.currentTimeMillis() - startTime) / (1000 * 3600 * 24));
		if (day < 0) {
			return 1;
		}
		if (total == -1) {
			episode = day / 7 + 1;
		} else {
			episode = ((day / 7 + 1) <= total ? (day / 7 + 1) : total);
		}
		return episode;
	}

	public String getUpdateTime() {
		return startDate.substring(8, 10) + ":" + startDate.substring(10);
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
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new SimpleDateFormat("yyyyMMddHHmm").parse(startDate));
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				week = 7;
			} else {
				week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return week;
	}

	public CopyrightParams getCopyright() {
		return copyright;
	}

	public void setCopyright(CopyrightParams copyright) {
		this.copyright = copyright;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int getPrimaryKey1() {
		return getWeek();
	}

	@Override
	public int getPrimaryKey2() {
		return Integer.parseInt(startDate.substring(8));
	}

	public boolean isFin() {
		if (total == -1) {
			return false;
		}
		long startTime = System.currentTimeMillis();
		try {
			startTime = new SimpleDateFormat("yyyyMMddHHmm").parse(startDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = (int) (System.currentTimeMillis() - startTime) / (1000 * 3600 * 24);
		int episode = day / 7 + 1;
		return episode > total;
	}

	@Override
	public String toString() {
		return "AnimeModel{" +
				"iconUrl='" + iconUrl + '\'' +
				", name='" + name + '\'' +
				", startDate='" + startDate + '\'' +
				", updateTime='" + getUpdateTime() + '\'' +
				", week=" + week +
				", copyright=" + copyright +
				", total=" + total +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnimeModel model = (AnimeModel) o;

		if (total != model.total) return false;
		if (iconUrl != null ? !iconUrl.equals(model.iconUrl) : model.iconUrl != null) return false;
		if (name != null ? !name.equals(model.name) : model.name != null) return false;
		if (startDate != null ? !startDate.equals(model.startDate) : model.startDate != null)
			return false;
		return copyright == model.copyright;

	}

	@Override
	public int hashCode() {
		int result = iconUrl != null ? iconUrl.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (copyright != null ? copyright.hashCode() : 0);
		result = 31 * result + total;
		return result;
	}
}