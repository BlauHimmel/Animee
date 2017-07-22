package com.zhouyuming.animee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouyuming.animee.R;
import com.zhouyuming.animee.event.MarkUpdateEvent;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.model.EpisodeModel;
import com.zhouyuming.animee.utils.AnimationUtils;
import com.zhouyuming.animee.utils.RecordUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhouYuming on 2017/7/19.
 */

public class AnimeEpisodeRecyclerViewAdapter extends RecyclerView.Adapter<AnimeEpisodeRecyclerViewAdapter.AnimeEpisodeViewHolder> {

	private AnimeModel mAnimeModel;
	private List<EpisodeModel> mEpisodeModels;
	private LayoutInflater mLayoutInflater;
	private Context mContext;

	public AnimeEpisodeRecyclerViewAdapter(Context context, AnimeModel animeModel) {

		mAnimeModel = animeModel;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mEpisodeModels = new ArrayList<>();

		Calendar calendar = new GregorianCalendar();
		int year = Integer.parseInt(mAnimeModel.getStartDate().substring(0, 4));
		int mouth = Integer.parseInt(mAnimeModel.getStartDate().substring(4, 6));
		int day = Integer.parseInt(mAnimeModel.getStartDate().substring(6, 8));
		calendar.set(year, mouth - 1, day);
		SimpleDateFormat sdf = new SimpleDateFormat(mContext.getString(R.string.date_format_1));

		int episode = mAnimeModel.getEpisode();
		for (int i = 1; i <= episode; i++) {
			EpisodeModel model = new EpisodeModel(i, sdf.format(calendar.getTime()));
			mEpisodeModels.add(model);
			calendar.add(Calendar.DAY_OF_YEAR, 7);
		}
	}

	@Override
	public AnimeEpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mLayoutInflater.inflate(R.layout.cardview_anime_episode, parent, false);
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

	public class AnimeEpisodeViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.anime_episode_item_episode)
		TextView mEpisodeTv;

		@BindView(R.id.anime_episode_item_date)
		TextView mDate;

		@BindView(R.id.anime_episode_item_state)
		ImageView mStateIv;

		private boolean isMarked = false;

		private EpisodeModel mEpisodeModel;

		AnimeEpisodeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		void updateUI(EpisodeModel episodeModel) {
			mEpisodeModel = episodeModel;

			mEpisodeTv.setText(MessageFormat.format(mContext.getString(R.string.episode_format), mEpisodeModel.getEpisode()));

			isMarked = RecordUtils.loadMark(mContext, mAnimeModel.getName(), mEpisodeModel.getEpisode());
			if (isMarked) {
				mStateIv.setImageResource(R.drawable.ic_marked);
			} else {
				mStateIv.setImageResource(R.drawable.ic_mark);
			}

			if (isMarked) {
				String date = RecordUtils.loadDate(mContext, mAnimeModel.getName(), mEpisodeModel.getEpisode());
				mDate.setText(MessageFormat.format(mContext.getString(R.string.watched_time_format), mEpisodeModel.getDate(), date));
			} else {
				mDate.setText(mEpisodeModel.getDate());
			}
		}

		@OnClick(R.id.anime_episode_item_state)
		void onAnimeEpisodeStateClick() {
			if (!isMarked) {
				RecordUtils.store(mContext, mAnimeModel.getName(), mEpisodeModel.getEpisode(), true);
				AnimationUtils.playStateChange(mStateIv, R.drawable.ic_marked);
				String date = RecordUtils.loadDate(mContext, mAnimeModel.getName(), mEpisodeModel.getEpisode());
				mDate.setText(MessageFormat.format(mContext.getString(R.string.watched_time_format), mEpisodeModel.getDate(), date));
				isMarked = true;
			} else {
				RecordUtils.store(mContext, mAnimeModel.getName(), mEpisodeModel.getEpisode(), false);
				AnimationUtils.playStateChange(mStateIv, R.drawable.ic_mark);
				mDate.setText(mEpisodeModel.getDate());
				isMarked = false;
			}
			EventBus.getDefault().post(new MarkUpdateEvent());
		}
	}
}
