package ch.ralena.stormy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.ralena.stormy.adapters.HourAdapter;
import ch.ralena.stormy.adapters.ListAdapter;
import ch.ralena.stormy.weather.Forecast;
import ch.ralena.stormy.weather.Hour;

/**
 * R.layout.fragment_weather_list
 */

public class HourlyFragment extends WeatherFragment {
	private static final String TAG = HourlyFragment.class.getSimpleName();
	private static final String KEY_HOURS = "key_hours";
	Hour[] mHours;

	@Override
	public ListAdapter getAdapter() {
		if (mHours==null) {
			mHours = new Hour[0];
		}
		return new HourAdapter(mHours);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.getParcelableArray(KEY_HOURS) != null) {
			mHours = (Hour[]) savedInstanceState.getParcelableArray(KEY_HOURS);
			Forecast forecast = new Forecast();
			forecast.setHourlyForecast(mHours);
			getAdapter().updateWeather(forecast);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArray(KEY_HOURS, mHours);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void updateWeather(Forecast forecast) {
		mHours = forecast.getHourlyForecast();
		final HourAdapter adapter = (HourAdapter) getListAdapter();
		adapter.updateWeather(forecast);
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
			}
		});
	}
}
