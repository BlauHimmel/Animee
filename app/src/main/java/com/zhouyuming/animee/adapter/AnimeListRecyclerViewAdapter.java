package com.zhouyuming.animee.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhouyuming.animee.R;
import com.zhouyuming.animee.model.AnimeListModel;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeListRecyclerViewAdapter extends RecyclerView.Adapter<AnimeListRecyclerViewAdapter.AnimeListViewHolder> {

	private List<AnimeListModel> mAnimeModels;
	private LayoutInflater mLayoutInflater;
	private Context mContext;

	public AnimeListRecyclerViewAdapter(Context context, List<AnimeListModel> animeDatas) {
		mContext = context;
		mAnimeModels = animeDatas;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void addAnimeModel(AnimeListModel model) {
		mAnimeModels.add(model);
		notifyItemInserted(mAnimeModels.size() - 1);
	}

	@Override
	public AnimeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mLayoutInflater.inflate(R.layout.cardview_anime_list, parent, false);
		return  new AnimeListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(AnimeListViewHolder holder, int position) {
		AnimeListModel data = mAnimeModels.get(position);
		holder.updateUI(data);
	}

	@Override
	public int getItemCount() {
		return mAnimeModels.size();
	}

	class AnimeListViewHolder extends RecyclerView.ViewHolder {

		@Bind(R.id.animee_list_cardview)
		CardView mItemCv;

		@Bind(R.id.animee_list_icon)
		ImageView mIconIv;

		@Bind(R.id.animee_list_copyright)
		ImageView mCopyrightIv;

		@Bind(R.id.animee_list_state)
		ImageView mStateIv;

		@Bind(R.id.animee_list_name)
		TextView mNameTv;

		@Bind(R.id.animee_list_episode)
		TextView mEpisodeTv;

		@Bind(R.id.animee_list_date)
		TextView mDateTv;

		AnimeListViewHolder(View itemView) {
			super(itemView);
		}

		void updateUI(AnimeListModel animeData) {
			/*Picasso.with(mContext).load(animeData.getIconUrl()).into(mIconIv);
			Picasso.with(mContext).load(animeData.getCopyright().getResId()).into(mCopyrightIv);
			mNameTv.setText(animeData.getName());
			mEpisodeTv.setText(animeData.getEpisode());
			mDateTv.setText(animeData.getUpdateTime());*/
		}
	}
}
