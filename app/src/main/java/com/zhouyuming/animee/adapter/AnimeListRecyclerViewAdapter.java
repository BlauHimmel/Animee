package com.zhouyuming.animee.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhouyuming.animee.R;
import com.zhouyuming.animee.activity.AnimeInfoActivity;
import com.zhouyuming.animee.event.AnimeInfoEvent;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.utils.AnimationUtils;
import com.zhouyuming.animee.utils.RecordUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeListRecyclerViewAdapter extends RecyclerView.Adapter<AnimeListRecyclerViewAdapter.AnimeListViewHolder> {

	private List<AnimeModel> mAnimeModels;
	private LayoutInflater mLayoutInflater;
	private Context mContext;

	public AnimeListRecyclerViewAdapter(Context context, List<AnimeModel> animeModels) {
		mContext = context;
		mAnimeModels = animeModels;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void loadAnimeModel(List<AnimeModel> animeModels) {
		mAnimeModels = animeModels;
		notifyDataSetChanged();
	}

	public void addAnimeModel(AnimeModel model) {
		mAnimeModels.add(model);
		notifyItemInserted(mAnimeModels.size() - 1);
	}

	@Override
	public AnimeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mLayoutInflater.inflate(R.layout.cardview_anime_list, parent, false);
		return new AnimeListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(AnimeListViewHolder holder, int position) {
		AnimeModel data = mAnimeModels.get(position);
		holder.updateUI(data);
	}

	@Override
	public int getItemCount() {
		return mAnimeModels.size();
	}

	class AnimeListViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.anime_list_cardview)
		CardView mItemCv;

		@BindView(R.id.anime_list_icon)
		ImageView mIconIv;

		@BindView(R.id.anime_list_copyright)
		ImageView mCopyrightIv;

		@BindView(R.id.anime_list_state)
		ImageView mStateIv;

		@BindView(R.id.anime_list_name)
		TextView mNameTv;

		@BindView(R.id.anime_list_episode)
		TextView mEpisodeTv;

		@BindView(R.id.anime_list_date)
		TextView mDateTv;

		private boolean isMarked = false;

		private AnimeModel mAnimeModel;

		AnimeListViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		void updateUI(AnimeModel animeModel) {
			this.mAnimeModel = animeModel;
			Picasso.with(mContext).load(animeModel.getIconUrl()).into(mIconIv);
			Picasso.with(mContext).load(animeModel.getCopyright().getResId()).into(mCopyrightIv);
			mNameTv.setText(animeModel.getName());

			isMarked = RecordUtils.load(mContext, animeModel.getName(), animeModel.getEpisode());
			if (isMarked) {
				mStateIv.setImageResource(R.drawable.ic_marked);
			} else {
				mStateIv.setImageResource(R.drawable.ic_mark);
			}

			if (animeModel.isFin()) {
				mEpisodeTv.setText(mContext.getString(R.string.fin));
				mStateIv.setVisibility(View.GONE);
			} else {
				mEpisodeTv.setText(MessageFormat.format(mContext.getString(R.string.episode_format), animeModel.getEpisode()));
				mDateTv.setText(animeModel.getUpdateTime());
			}
		}

		@OnClick(R.id.anime_list_state)
		void onAnimeListStateClick() {
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

		@OnClick(R.id.anime_list_cardview)
		void onAnimeListItemClick() {
			Intent intent = new Intent(mContext, AnimeInfoActivity.class);
			ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, mIconIv, "anime_icon");
			mContext.startActivity(intent, option.toBundle());
			EventBus.getDefault().postSticky(new AnimeInfoEvent(mAnimeModel));
		}
	}
}
