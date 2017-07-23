package com.zhouyuming.animee.event;

import com.zhouyuming.animee.model.AnimeModel;

/**
 * Created by ZhouYuming on 2017/7/23.
 */

public class AnimeModifyEvent {private AnimeModel animeModel;

	public AnimeModifyEvent(AnimeModel animeModel) {
		this.animeModel = animeModel;
	}

	public AnimeModel getAnimeModel() {
		return animeModel;
	}

	public void setAnimeModel(AnimeModel animeModel) {
		this.animeModel = animeModel;
	}
}
