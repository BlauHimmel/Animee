package com.zhouyuming.animee.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouyuming.animee.R;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.model.EpisodeModel;
import com.zhouyuming.animee.utils.AnimationUtils;
import com.zhouyuming.animee.utils.RecordUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class AnimeEpisodeRecylerViewAdapter extends RecyclerView.Adapter<AnimeEpisodeRecylerViewAdapter.AnimeEpisodeViewHolder> {

	private AnimeModel mAnimeModel;
	private List<EpisodeModel> mEpisodeModels;
	private LayoutInflater mLayoutInflater;
	private Context mContext;

	public AnimeEpisodeRecylerViewAdapter(AnimeModel animeModel, Context context) {

		mAnimeModel = animeModel;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mEpisodeModels = new ArrayList<>();

		Calendar calendar = new GregorianCalendar();
		int year = Integer.parseInt(mAnimeModel.getStartDate().substring(0, 4));
		int mouth = Integer.parseInt(mAnimeModel.getStartDate().substring(4, 6));
		int day = Integer.parseInt(mAnimeModel.getStartDate().substring(6, 8));
		calendar.set(year, mouth, day);
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

		for (int i = 1; i <= mAnimeModel.getEpisode(); i++) {

			calendar.add(Calendar.DAY_OF_MONTH, 7 * (i - 1));
			EpisodeModel episode = new EpisodeModel(i, sdf.format(calendar.getTime()));
			mEpisodeModels.add(episode);
		}
	}

	@Override
	public AnimeEpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mLayoutInflater.inflate(R.layout.anime_episode_item, parent, false);
		return new AnimeEpisodeViewHolder(view);
	}

	@Override
	public void onBindViewHolder(AnimeEpisodeViewHolder holder, int position) {
		EpisodeModel data = mEpisodeModels.get(position);
		holder.updateUI(data);
	}

	@Override
	public int getItemCount() {
		return mEpisodeModels.size();
	}

	class AnimeEpisodeViewHolder extends RecyclerView.ViewHolder {

		@Bind(R.id.animee_episode_item_episode)
		TextView mEpisodeTv;

		@Bind(R.id.animee_episode_item_date)
		TextView mDate;

		@Bind(R.id.animee_episode_item_state)
		ImageView mStateIv;

		private boolean isMarked = false;

		AnimeEpisodeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		void updateUI(EpisodeModel episodeModel) {
			mEpisodeTv.setText(episodeModel.getEpisode());
			mDate.setText(episodeModel.getDate());

			isMarked = RecordUtils.load(mContext, mAnimeModel.getName(), episodeModel.getEpisode());
			if (isMarked) {
				mStateIv.setImageResource(R.drawable.ic_marked);
			} else {
				mStateIv.setImageResource(R.drawable.ic_mark);
			}
		}

		@OnClick(R.id.animee_list_state)
		void onStateClick() {
			if (!isMarked) {
				RecordUtils.store(mContext, mAnimeModel.getName(), mAnimeModel.getEpisode(), true);
				AnimationUtils.playStateChange(mStateIv, R.drawable.ic_marked);
				isMarked = true;
			} else {
				RecordUtils.store(mContext, mAnimeModel.getName(), mAnimeModel.getEpisode(), false);
				AnimationUtils.playStateChange(mStateIv, R.drawable.ic_mark);
				isMarked = false;
			}
		}
	}
}
