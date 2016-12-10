package ch.ralena.stormy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
	private static final String KEY_FORECAST = "key_forecast";
	private Forecast mForecast;
	Hour[] mHours;

	@Override
	public ListAdapter getAdapter() {
		if (mForecast==null) {
			mForecast = new Forecast();
			mForecast.setHourlyForecast(new Hour[0]);
			mForecast.setIsFahrenheit(true);
		}
		return new HourAdapter(mForecast.getHourlyForecast(),mForecast.isFahrenheit());
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.getParcelable(KEY_FORECAST) != null) {
			mForecast = (Forecast) savedInstanceState.getParcelable(KEY_FORECAST);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(KEY_FORECAST, mForecast);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "activity created");
	}

	@Override
	public void updateWeather(Forecast forecast) {
		mForecast = forecast;
		final HourAdapter adapter = (HourAdapter) getListAdapter();
		if (!isAdded()) {
			forecast = new Forecast();
			forecast.setHourlyForecast(new Hour[0]);
		}
		adapter.updateWeather(forecast);
		if (isAdded()) {
			FragmentActivity fragmentActivity = getActivity();
			fragmentActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
				}
			});
		}
	}
}
