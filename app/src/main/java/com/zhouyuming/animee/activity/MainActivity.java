package com.zhouyuming.animee.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhouyuming.animee.R;
import com.zhouyuming.animee.adapter.AnimeViewPagerAdapter;
import com.zhouyuming.animee.event.AnimeUpdateEvent;
import com.zhouyuming.animee.event.QRCodeEvent;
import com.zhouyuming.animee.fragment.AnimeFragment;
import com.zhouyuming.animee.param.FragmentParams;
import com.zhouyuming.animee.utils.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.xudaojie.qrcodelib.CaptureActivity;

import static java.util.Calendar.SHORT;
import static java.util.Calendar.SUNDAY;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	@BindView(R.id.activity_main_coordinator_layout)
	CoordinatorLayout mCoordinatorLayout;

	@BindView(R.id.activity_main_fab_add)
	FloatingActionButton mFabAddAnime;

	//@BindView(R.id.activity_main_drawer_layout)
	//DrawerLayout mDrawerLayout;

	//@BindView(R.id.activity_main_nav_view)
	//NavigationView mNavigationView;

	@BindView(R.id.activity_main_toolbar)
	Toolbar mToolbar;

	@BindView(R.id.activity_main_tab_layout)
	TabLayout mAnimeTab;

	@BindView(R.id.activity_main_view_pager)
	ViewPager mAnimePager;

	private static final int REQUEST_QR_CODE = 0x01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initStatusBar();
		setContentView(R.layout.activity_main);
		EventBus.getDefault().register(this);
		ButterKnife.bind(this);
		initialize();
		createFragments();
		selectToday();
		FileUtils.init(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onBackPressed() {
		//if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
		//	mDrawerLayout.closeDrawer(GravityCompat.START);
		//} else {
			super.onBackPressed();
		//}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.menu_about) {
			Snackbar.make(mCoordinatorLayout, getString(R.string.author), Snackbar.LENGTH_SHORT).show();
			ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			cm.setPrimaryClip(ClipData.newPlainText("Github", "https://github.com/BlauHimmel"));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int id = item.getItemId();

		//mDrawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_QR_CODE && data != null) {
			String result = data.getStringExtra("result");
			EventBus.getDefault().post(new QRCodeEvent(result));
		}
	}

	@Subscribe
	public void onQRCodeScanned(AnimeUpdateEvent animeUpdateEvent) {
		int week = animeUpdateEvent.getWeek();
		mAnimePager.setCurrentItem(week % 7);
		if (animeUpdateEvent.isUpdated()) {
			Snackbar.make(mCoordinatorLayout, getString(R.string.add_success), Snackbar.LENGTH_SHORT).show();
		} else {
			Snackbar.make(mCoordinatorLayout, getString(R.string.add_fail), Snackbar.LENGTH_SHORT).show();
		}
	}

	@OnClick(R.id.activity_main_fab_add)
	public void onAddAnimeFabClick() {
		Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_QR_CODE);
	}

	private void initialize() {
		setSupportActionBar(mToolbar);
/*
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				mToolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		mDrawerLayout.addDrawerListener(toggle);
		mDrawerLayout.setFitsSystemWindows(true);
		mDrawerLayout.setClipToPadding(false);
		toggle.syncState();

		mNavigationView.setNavigationItemSelectedListener(this);
*/
		mAnimePager.setOffscreenPageLimit(6);




	}

	private void createFragments() {

		SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_format_1));
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int today = calendar.get(Calendar.DAY_OF_WEEK);

		Bundle bundle;

		AnimeFragment animeSun = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 7);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 1 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeSun.setArguments(bundle);

		AnimeFragment animeMon = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 1);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 2 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeMon.setArguments(bundle);

		AnimeFragment animeTues = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 2);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 3 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeTues.setArguments(bundle);

		AnimeFragment animeWed = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 3);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 4 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeWed.setArguments(bundle);

		AnimeFragment animeThur = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 4);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 5 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeThur.setArguments(bundle);

		AnimeFragment animeFri = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 5);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 6 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeFri.setArguments(bundle);

		AnimeFragment animeSat = new AnimeFragment();
		bundle = new Bundle();
		bundle.putInt(FragmentParams.INT_WEEK.getValue(), 6);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 7 - today);
		bundle.putString(FragmentParams.STRING_DATE.getValue(), sdf.format(calendar.getTime()));
		animeSat.setArguments(bundle);

		AnimeViewPagerAdapter animeViewPagerAdapter = new AnimeViewPagerAdapter(getSupportFragmentManager());

		animeViewPagerAdapter.addPage(getString(R.string.sunday), animeSun);
		animeViewPagerAdapter.addPage(getString(R.string.monday), animeMon);
		animeViewPagerAdapter.addPage(getString(R.string.tuesday), animeTues);
		animeViewPagerAdapter.addPage(getString(R.string.wednesday), animeWed);
		animeViewPagerAdapter.addPage(getString(R.string.thursday), animeThur);
		animeViewPagerAdapter.addPage(getString(R.string.friday), animeFri);
		animeViewPagerAdapter.addPage(getString(R.string.saturday), animeSat);

		mAnimePager.setAdapter(animeViewPagerAdapter);

		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.sunday));
		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.monday));
		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.tuesday));
		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.wednesday));
		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.thursday));
		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.friday));
		mAnimeTab.addTab(mAnimeTab.newTab().setText(R.string.saturday));

		mAnimeTab.setSelectedTabIndicatorColor(Color.WHITE);
		mAnimeTab.setupWithViewPager(mAnimePager);
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

	private void selectToday() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		if (week == SUNDAY) {
			mAnimePager.setCurrentItem(0);
		} else {
			mAnimePager.setCurrentItem(week - 1);
		}
	}
}
