package com.zhouyuming.animee.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhouyuming.animee.R;
import com.zhouyuming.animee.adapter.AnimeEpisodeRecyclerViewAdapter;
import com.zhouyuming.animee.event.AnimeInfoEvent;
import com.zhouyuming.animee.event.AnimeRefreshEvent;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.utils.FileUtils;
import com.zhouyuming.animee.utils.RecordUtils;
import com.zhouyuming.animee.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class AnimeInfoActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

	@BindView(R.id.activity_anime_info_coordinator_layout)
	CoordinatorLayout mCoordinatorLayout;

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

	@BindView(R.id.activity_anime_info_appbar_layout)
	AppBarLayout mAppBarLayout;

	@BindView(R.id.activity_anime_info_fab_modify)
	FloatingActionButton mModifyFab;

	private AnimeModel mModel;

	private CollapsingToolbarLayoutState mState;

	private enum CollapsingToolbarLayoutState {
		EXPANDED,
		COLLAPSED,
		INTERNEDIATE
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//initStatusBar();
		setContentView(R.layout.activity_anime_info);
		ButterKnife.bind(this);
		initialize();
		EventBus.getDefault().register(this);
		mAppBarLayout.addOnOffsetChangedListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(sticky = true)
	public void onAnimeInfoOpen(AnimeInfoEvent animeInfoEvent) {
		mModel = animeInfoEvent.getAnimeModel();
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		AnimeEpisodeRecyclerViewAdapter animeEpisodeRecyclerViewAdapter = new AnimeEpisodeRecyclerViewAdapter(this, mModel);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(animeEpisodeRecyclerViewAdapter);

		Picasso.with(this).load(mModel.getIconUrl()).into(mIconIv);
		mAnimeNameTv.setText(mModel.getName());
		mTimeTv.setText(MessageFormat.format(getString(R.string.time_format), TextUtils.getWeekName(this, mModel.getWeek()), mModel.getUpdateTime()));
		mCopyrightTv.setText(mModel.getCopyright().getName());
		if (mModel.getTotal() != -1) {
			mProgressTv.setText(MessageFormat.format(getString(R.string.progress_format), mModel.getEpisode(), mModel.getTotal()));
		} else {
			mProgressTv.setText(MessageFormat.format(getString(R.string.progress_format2), mModel.getEpisode()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
			case R.id.menu_delete:
				Snackbar.make(mCoordinatorLayout, getString(R.string.delete_confirm), Snackbar.LENGTH_LONG)
						.setAction(getString(R.string.ok), view -> {
							FileUtils.remove(mModel, AnimeModel.class);
							RecordUtils.remove(AnimeInfoActivity.this, mModel.getName());
							EventBus.getDefault().post(new AnimeRefreshEvent());
							finish();
						})
						.show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
		if (verticalOffset == 0) {
			if (mState != CollapsingToolbarLayoutState.EXPANDED) {
				mState = CollapsingToolbarLayoutState.EXPANDED;
				mCollapsingToolbarLayout.setTitle(" ");
				ActionBar actionbar = getSupportActionBar();
				if (actionbar != null) {
					actionbar.setDisplayHomeAsUpEnabled(false);
				}
			}
		} else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
			if (mState != CollapsingToolbarLayoutState.COLLAPSED) {
				mCollapsingToolbarLayout.setTitle(mModel.getName());
				ActionBar actionbar = getSupportActionBar();
				if (actionbar != null) {
					actionbar.setDisplayHomeAsUpEnabled(true);
				}
				mState = CollapsingToolbarLayoutState.COLLAPSED;
			}
		} else {
			if (mState != CollapsingToolbarLayoutState.INTERNEDIATE) {
				mCollapsingToolbarLayout.setTitle(" ");
				ActionBar actionbar = getSupportActionBar();
				if (actionbar != null) {
					actionbar.setDisplayHomeAsUpEnabled(false);
				}
				mState = CollapsingToolbarLayoutState.INTERNEDIATE;
			}
		}
	}

	@OnClick(R.id.activity_anime_info_fab_modify)
	void onModifyFabClick() {

	}

	private void initStatusBar()
	{
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		Window window = getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
				| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(Color.TRANSPARENT);
		window.setNavigationBarColor(Color.TRANSPARENT);
	}

	private void initialize() {
		mCollapsingToolbarLayout.setTitle(" ");
		setSupportActionBar(mToolbar);
	}

}
