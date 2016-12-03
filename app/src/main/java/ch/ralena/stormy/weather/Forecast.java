package ch.ralena.stormy.weather;

import ch.ralena.stormy.R;

/**
 * Created by crater-windoze on 10/22/2016.
 */

public class Forecast {
	private Current mCurrent;
	private Hour[] mHourlyForecast;
	private Day[] mDailyForecast;

	public Current getCurrent() {
		return mCurrent;
	}

	public void setCurrent(Current current) {
		mCurrent = current;
	}

	public Hour[] getHourlyForecast() {
		return mHourlyForecast;
	}

	public void setHourlyForecast(Hour[] hourlyForecast) {
		mHourlyForecast = hourlyForecast;
	}

	public Day[] getDailyForecast() {
		return mDailyForecast;
	}

	public void setDailyForecast(Day[] dailyForecast) {
		mDailyForecast = dailyForecast;
	}

	public static int getIconId(String icon) {
		int iconId = R.drawable.clear_day;
		if (icon.equals("clear-day")) {
			iconId = R.drawable.clear_day;
		}
		else if (icon.equals("clear-night")) {
			iconId = R.drawable.clear_night;
		}
		else if (icon.equals("rain")) {
			iconId = R.drawable.rain;
		}
		else if (icon.equals("snow")) {
			iconId = R.drawable.snow;
		}
		else if (icon.equals("sleet")) {
			iconId = R.drawable.sleet;
		}
		else if (icon.equals("wind")) {
			iconId = R.drawable.wind;
		}
		else if (icon.equals("fog")) {
			iconId = R.drawable.fog;
		}
		else if (icon.equals("cloudy")) {
			iconId = R.drawable.cloudy;
		}
		else if (icon.equals("partly-cloudy-day")) {
			iconId = R.drawable.partly_cloudy;
		}
		else if (icon.equals("partly-cloudy-night")) {
			iconId = R.drawable.cloudy_night;
		}
		return iconId;
	}
}
