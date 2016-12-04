package ch.ralena.stormy.adapters;

import android.support.v7.widget.RecyclerView;

import ch.ralena.stormy.weather.Forecast;

/**
 * Created by crater-windoze on 12/3/2016.
 */

public abstract class ListAdapter extends RecyclerView.Adapter {
	public abstract void updateWeather(Forecast forecast);
}
