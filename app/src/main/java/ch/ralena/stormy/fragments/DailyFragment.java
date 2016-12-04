package ch.ralena.stormy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.ralena.stormy.adapters.DayAdapter;
import ch.ralena.stormy.adapters.ListAdapter;
import ch.ralena.stormy.weather.Day;
import ch.ralena.stormy.weather.Forecast;

/**
 * R.layout.fragment_weather_list
 */

public class DailyFragment extends WeatherFragment {
	private static final String KEY_DAYS = "key_days";
	private Day[] mDays;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mDays = (Day[]) savedInstanceState.getParcelableArray(KEY_DAYS);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public ListAdapter getAdapter() {
		return new DayAdapter();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArray(KEY_DAYS, mDays);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void updateWeather(Forecast forecast) {
		mDays = forecast.getDailyForecast();
		final DayAdapter adapter = (DayAdapter) getListAdapter();
		if (adapter != null) {
			adapter.updateWeather(forecast);
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
				}
			});
		}
	}
}
