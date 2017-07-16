package com.zhouyuming.animee.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zhouyuming.animee.R;

import butterknife.ButterKnife;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class AnimeInfoActivity extends AppCompatActivity {

	CollapsingToolbarLayout mCollapsingToolbarLayout;

	TextView mAnimeNameTv;

	TextView mTimeTv;

	TextView mCopyrightTv;

	TextView mProgressTv;

	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anime_info);
		ButterKnife.bind(this);
	}


}
