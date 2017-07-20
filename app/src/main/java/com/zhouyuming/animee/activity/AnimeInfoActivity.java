package com.zhouyuming.animee.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhouyuming.animee.R;
import com.zhouyuming.animee.adapter.AnimeEpisodeRecyclerViewAdapter;
import com.zhouyuming.animee.event.AnimeInfoEvent;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class AnimeInfoActivity extends AppCompatActivity {

	@BindView(R.id.activity_anime_info_collapsing_toolbar_layout)
	CollapsingToolbarLayout mCollapsingToolbarLayout;

	@BindView(R.id.activity_anime_info_toolbar)
	Toolbar mToolbar;

	@BindView(R.id.activity_anime_info_name)
	TextView mAnimeNameTv;

	@BindView(R.id.activity_anime_info_time)
	TextView mTimeTv;

	@BindView(R.id.activity_anime_info_copyright)
	TextView mCopyrightTv;

	@BindView(R.id.activity_anime_info_progress)
	TextView mProgressTv;

	@BindView(R.id.activity_anime_info_icon)
	ImageView mIconIv;

	@BindView(R.id.activity_anime_info_recycler_view)
	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anime_info);
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(sticky = true)
	public void onAnimeInfoOpen(AnimeInfoEvent animeInfoEvent) {
		AnimeModel model = animeInfoEvent.getAnimeModel();
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		AnimeEpisodeRecyclerViewAdapter animeEpisodeRecyclerViewAdapter = new AnimeEpisodeRecyclerViewAdapter(this, model);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(animeEpisodeRecyclerViewAdapter);

		mCollapsingToolbarLayout.setTitle(model.getName());
		Picasso.with(this).load(model.getIconUrl()).into(mIconIv);
		mAnimeNameTv.setText(model.getName());
		mTimeTv.setText(MessageFormat.format(getString(R.string.time_format), TextUtils.getWeekName(this, model.getWeek()), model.getUpdateTime()));
		mCopyrightTv.setText(model.getCopyright().getName());
		mProgressTv.setText(MessageFormat.format(getString(R.string.progress_format), model.getEpisode(), model.getTotal()));
	}
}
