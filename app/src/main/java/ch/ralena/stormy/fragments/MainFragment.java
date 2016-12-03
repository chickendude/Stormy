package ch.ralena.stormy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ralena.stormy.R;
import ch.ralena.stormy.ui.DailyForecastActivity;
import ch.ralena.stormy.ui.HourlyForecastActivity;
import ch.ralena.stormy.weather.Forecast;

import static ch.ralena.stormy.ui.MainActivity.DAILY_FORECAST;
import static ch.ralena.stormy.ui.MainActivity.HOURLY_FORECAST;

/**
 * Created by crater-windoze on 12/3/2016.
 */

public class MainFragment extends Fragment {
	public static final String KEY_HOURLY_BUTTON = "key_hourly_button";
	private static final String KEY_DAILY_BUTTON = "key_daily_button";
	private Forecast mForecast;

	TextView mTimeLabel;
	TextView mTemperatureLabel;
	TextView mHumidityValue;
	TextView mPrecipValue;
	TextView mSummaryLabel;
	ImageView mIconImageView;
	ImageView mRefreshImageView;
	ProgressBar mProgressBar;

	// interface to handle hourly/weekly button clicks
	public interface ButtonClick {
		void OnButtonClick(View v);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(getActivity());
		mTimeLabel = (TextView) view.findViewById(R.id.timeLabel);
		mTemperatureLabel = (TextView) view.findViewById(R.id.temperatureLabel);
		mHumidityValue = (TextView) view.findViewById(R.id.humidityValue);
		mPrecipValue = (TextView) view.findViewById(R.id.precipValue);
		mSummaryLabel = (TextView) view.findViewById(R.id.summaryLabel);
		mIconImageView = (ImageView) view.findViewById(R.id.iconImageView);
		mRefreshImageView = (ImageView) view.findViewById(R.id.refreshImageView);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		mProgressBar.setVisibility(View.INVISIBLE);
		Button hourlyButton = (Button) view.findViewById(R.id.hourlyButton);
		hourlyButton.setTag(KEY_HOURLY_BUTTON);
		Button dailyButton = (Button) view.findViewById(R.id.dailyButton);
		dailyButton.setTag(KEY_DAILY_BUTTON);
		return view;
	}


	@OnClick(R.id.dailyButton)
	public void startDailyActivity(View view) {
		if (mTemperatureLabel.getText().toString().equals("--")) {
			Toast.makeText(getActivity(), "Data hasn't loaded yet...", Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(getActivity(), DailyForecastActivity.class);
			intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
			startActivity(intent);
		}
	}

	public void startHourlyActivity(View view) {
		// Make sure data is loaded first, otherwise app will crash
		if (mTemperatureLabel.getText().toString().equals("--")) {
			Log.d("H", "not loaded");
			Toast.makeText(getActivity(), "Data hasn't loaded yet...", Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(getActivity(), HourlyForecastActivity.class);
			intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
			startActivity(intent);
			Log.d("H", "loaded");
		}
	}

}
