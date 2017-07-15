package com.zhouyuming.animee.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhouYuming on 2017/7/9.
 */

public class AnimeViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragments;
	private List<String> mTitles;

	public AnimeViewPagerAdapter(FragmentManager fm) {
		super(fm);
		mFragments = new ArrayList<>();
		mTitles = new ArrayList<>();
	}

	public void addPage(String title, Fragment fragment) {
		mFragments.add(fragment);
		mTitles.add(title);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.get(position);
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}
}
