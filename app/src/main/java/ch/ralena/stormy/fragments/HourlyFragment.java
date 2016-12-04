package ch.ralena.stormy.fragments;

import ch.ralena.stormy.adapters.HourAdapter;
import ch.ralena.stormy.adapters.ListAdapter;
import ch.ralena.stormy.weather.Forecast;

/**
 * R.layout.fragment_weather_list
 */

public class HourlyFragment extends WeatherFragment {
	@Override
	public ListAdapter getAdapter() {
		return new HourAdapter();
	}

	@Override
	public void updateWeather(Forecast forecast) {
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
