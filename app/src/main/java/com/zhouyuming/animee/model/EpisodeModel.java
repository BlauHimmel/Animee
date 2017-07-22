package com.zhouyuming.animee.model;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class EpisodeModel implements Model{

	private int episode;
	private String date;

	public EpisodeModel() {

	}

	public EpisodeModel(int episode, String date) {
		this.episode = episode;
		this.date = date;
	}

	public int getEpisode() {
		return episode;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int getPrimaryKey1() {
		return 0;
	}

	@Override
	public int getPrimaryKey2() {
		return 0;
	}
}
