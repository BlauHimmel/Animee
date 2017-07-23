package com.zhouyuming.animee.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.zhouyuming.animee.R;
import com.zhouyuming.animee.event.AnimeInfoEvent;
import com.zhouyuming.animee.event.AnimeModifyEvent;
import com.zhouyuming.animee.event.AnimeRefreshEvent;
import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.param.CopyrightParams;
import com.zhouyuming.animee.utils.FileUtils;
import com.zhouyuming.animee.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhouYuming on 2017/7/23.
 */

public class ModifyActivity extends AppCompatActivity {

	@BindView(R.id.activity_modify_til_url)
	TextInputLayout mUrlTil;

	@BindView(R.id.activity_modify_til_total)
	TextInputLayout mTotalTil;

	@BindView(R.id.activity_modify_spinner_copyright)
	AppCompatSpinner mCopyrightSpinner;

	@BindView(R.id.activity_modify_tv_date)
	TextView mDateTv;

	@BindView(R.id.activity_modify_iv_select_date)
	ImageView mDateSelectIv;

	@BindView(R.id.activity_modify_tv_time)
	TextView mTimeTv;

	@BindView(R.id.activity_modify_iv_select_time)
	ImageView mTimeSelectIv;

	@BindView(R.id.activity_modify_fab_ok)
	FloatingActionButton mOkFab;

	private AnimeModel mOriginModel;
	private AnimeModel mModifiedModel;

	private Calendar mStartDate;
	private Calendar mModifiedDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Subscribe(sticky = true)
	public void initialize(AnimeModifyEvent animeModifyEvent) {
		mOriginModel = animeModifyEvent.getAnimeModel();
		mModifiedModel = new AnimeModel(
				mOriginModel.getIconUrl(),
				mOriginModel.getName(),
				mOriginModel.getStartDate(),
				mOriginModel.getCopyright(),
				mOriginModel.getTotal()
		);

		ActionBar actionbar = getSupportActionBar();
		if (actionbar != null) {
			actionbar.setTitle(mOriginModel.getName());
			actionbar.setDisplayHomeAsUpEnabled(true);
		}

		mTotalTil.getEditText().setText(String.valueOf(mOriginModel.getTotal()));
		mUrlTil.getEditText().setText(mOriginModel.getIconUrl());

		try {
			String start = mOriginModel.getStartDate();
			mStartDate = new GregorianCalendar();
			mModifiedDate = new GregorianCalendar();
			mStartDate.setTime(new SimpleDateFormat("yyyyMMddHHmm").parse(start));
			mModifiedDate.setTime(new SimpleDateFormat("yyyyMMddHHmm").parse(start));

			String startDate = MessageFormat.format(getString(R.string.start_format),
					new SimpleDateFormat(getString(R.string.date_format_3)).format(mModifiedDate.getTime()),
					TextUtils.getWeekName(ModifyActivity.this, mModifiedModel.getWeek()));

			mDateTv.setText(startDate);
			mTimeTv.setText(mOriginModel.getUpdateTime());

			SpinnerAdapter adapter= mCopyrightSpinner.getAdapter();
			if(mOriginModel.getCopyright().getName() != null) {
				for (int i = 0; i < adapter.getCount(); i++) {
					if (mOriginModel.getCopyright().getName().equals(adapter.getItem(i).toString())) {
						mCopyrightSpinner.setSelection(i, true);
						break;
					}
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@OnClick(R.id.activity_modify_iv_select_date)
	void onDateSelectClick() {

		int year = mStartDate.get(Calendar.YEAR);
		int month = mStartDate.get(Calendar.MONTH);
		int day = mStartDate.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {

			mModifiedDate.set(Calendar.YEAR, year1);
			mModifiedDate.set(Calendar.MONTH, monthOfYear);
			mModifiedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			int week = mModifiedDate.get(Calendar.DAY_OF_WEEK);
			if (week == Calendar.SUNDAY) {
				week = 7;
			} else {
				week -= 1;
			}

			mModifiedModel.setStartDate(new SimpleDateFormat("yyyyMMddHHmm").format(mModifiedDate.getTime()));

			String modifiedDate = MessageFormat.format(getString(R.string.start_format),
					new SimpleDateFormat(getString(R.string.date_format_3)).format(mModifiedDate.getTime()),
					TextUtils.getWeekName(ModifyActivity.this, mModifiedModel.getWeek()));

			mDateTv.setText(modifiedDate);

		}, year, month, day);

		datePickerDialog.show();
	}

	@OnClick(R.id.activity_modify_iv_select_time)
	void onTimeSelectClick() {

		int hour = mStartDate.get(Calendar.HOUR_OF_DAY);
		int min = mStartDate.get(Calendar.MINUTE);

		TimePickerDialog timePickerDialog = new TimePickerDialog(ModifyActivity.this, (view, hourOfDay, minute) -> {

			mModifiedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mModifiedDate.set(Calendar.MINUTE, minute);
			mModifiedModel.setStartDate(new SimpleDateFormat("yyyyMMddHHmm").format(mModifiedDate.getTime()));
			mTimeTv.setText(mModifiedModel.getUpdateTime());
		}, hour, min, true);
		timePickerDialog.show();
	}

	@OnClick(R.id.activity_modify_fab_ok)
	void onOkFabClick() {

		mModifiedModel.setTotal(Integer.parseInt(mTotalTil.getEditText().getText().toString()));
		mModifiedModel.setIconUrl(mUrlTil.getEditText().getText().toString());
		mModifiedModel.setCopyright(Enum.valueOf(CopyrightParams.class, mCopyrightSpinner.getSelectedItem().toString().toUpperCase()));
		FileUtils.replace(mOriginModel, mModifiedModel, AnimeModel.class);
		EventBus.getDefault().post(new AnimeInfoEvent(mModifiedModel));
		EventBus.getDefault().post(new AnimeRefreshEvent());
		finish();
	}
}
