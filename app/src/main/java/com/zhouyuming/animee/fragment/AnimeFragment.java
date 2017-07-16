package com.zhouyuming.animee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouyuming.animee.R;
import com.zhouyuming.animee.adapter.AnimeListRecyclerViewAdapter;
import com.zhouyuming.animee.event.AnimeUpdateEvent;
import com.zhouyuming.animee.event.QRCodeEvent;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.param.BundleParams;
import com.zhouyuming.animee.utils.FileUtils;
import com.zhouyuming.animee.utils.JsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeFragment extends Fragment {

	@Bind(R.id.fragment_anime_recyclerview)
	RecyclerView mRecyclerView;

	private AnimeListRecyclerViewAdapter mAnimeListRecyclerViewAdapter;

	private int mWeek;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anime, container, false);
		ButterKnife.bind(this, view);
		EventBus.getDefault().register(this);
		initialize();
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
		ButterKnife.unbind(this);
	}

	@Subscribe
	public void onQRCodeScanned(QRCodeEvent qrCodeEvent) {
		AnimeModel model = JsonUtils.getModel(qrCodeEvent.getContent(), AnimeModel.class);
		if (model == null || model.getWeek() != mWeek) {
			return;
		}
		boolean isUpdated = FileUtils.updateFile(model, AnimeModel.class);
		if (isUpdated) {
			List<AnimeModel> animeModels = FileUtils.readFiles(mWeek, AnimeModel.class);
			mAnimeListRecyclerViewAdapter.loadAnimeModel(animeModels);
			EventBus.getDefault().post(new AnimeUpdateEvent(model.getWeek(), true));
		} else {
			EventBus.getDefault().post(new AnimeUpdateEvent(model.getWeek(), false));
		}
	}

	private void initialize() {
		mWeek = getArguments().getInt(BundleParams.INT_WEEK.getValue());
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		mAnimeListRecyclerViewAdapter = new AnimeListRecyclerViewAdapter(getContext(), getAnimeModels());
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mAnimeListRecyclerViewAdapter);
	}

	private List<AnimeModel> getAnimeModels() {
		int week = getArguments().getInt(BundleParams.INT_WEEK.getValue());
		return FileUtils.readFiles(week, AnimeModel.class);
	}
}
