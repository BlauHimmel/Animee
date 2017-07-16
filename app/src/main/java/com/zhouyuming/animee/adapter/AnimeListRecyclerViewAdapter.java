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
import com.zhouyuming.animee.model.AnimeModel;

import java.util.List;

import butterknife.Bind;

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
		return  new AnimeListViewHolder(view);
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

		void updateUI(AnimeModel animeData) {
			Picasso.with(mContext).load(animeData.getIconUrl()).into(mIconIv);
			Picasso.with(mContext).load(animeData.getCopyright().getResId()).into(mCopyrightIv);
			mNameTv.setText(animeData.getName());
			mEpisodeTv.setText(animeData.getEpisode());
			mDateTv.setText(animeData.getUpdateTime());
		}
	}
}
