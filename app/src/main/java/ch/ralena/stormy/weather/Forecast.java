package ch.ralena.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import ch.ralena.stormy.R;

/**
 * Created by crater-windoze on 10/22/2016.
 */

public class Forecast implements Parcelable {
	private static final String KEY_HOURLY_FORECAST = "key_hourly_forecast";
	private Current mCurrent;
	private Hour[] mHourlyForecast;
	private Day[] mDailyForecast;

	public Forecast() {}

	protected Forecast(Parcel in) {
		mHourlyForecast = in.createTypedArray(Hour.CREATOR);
		mDailyForecast = in.createTypedArray(Day.CREATOR);
	}

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedArray(mHourlyForecast, 0);
		dest.writeTypedArray(mDailyForecast, 0);
	}

	public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
		@Override
		public Forecast createFromParcel(Parcel in) {
			return new Forecast(in);
		}

		@Override
		public Forecast[] newArray(int size) {
			return new Forecast[size];
		}
	};
}
