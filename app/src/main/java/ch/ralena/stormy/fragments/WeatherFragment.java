package ch.ralena.stormy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.ralena.stormy.R;
import ch.ralena.stormy.adapters.ListAdapter;
import ch.ralena.stormy.weather.Forecast;

/**
 * This fragment loads a recycler view using ListAdapter
 */

public abstract class WeatherFragment extends Fragment {

	public ListAdapter mListAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		mListAdapter = getAdapter();
		recyclerView.setAdapter(mListAdapter);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		return view;
	}

	public ListAdapter getListAdapter() {
		return mListAdapter;
	}

	public abstract ListAdapter getAdapter();

	public abstract void updateWeather(Forecast forecast);
}
