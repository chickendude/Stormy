package ch.ralena.stormy.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.ralena.stormy.R;
import ch.ralena.stormy.ui.AlertDialogFragment;
import ch.ralena.stormy.ui.MainActivity;
import ch.ralena.stormy.weather.Current;
import ch.ralena.stormy.weather.Day;
import ch.ralena.stormy.weather.Forecast;
import ch.ralena.stormy.weather.Hour;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by crater-windoze on 12/3/2016.
 */

public class MainFragment extends Fragment {
	public static final String KEY_HOURLY_BUTTON = "key_hourly_button";
	private static final String KEY_DAILY_BUTTON = "key_daily_button";
	private static final String TAG = MainFragment.class.getSimpleName();
	private Forecast mForecast;

	private OnUpdatedWeatherDataListener listener;

	TextView mTimeLabel;
	TextView mTemperatureLabel;
	TextView mHumidityValue;
	TextView mPrecipValue;
	TextView mSummaryLabel;
	ImageView mIconImageView;
	ImageView mRefreshImageView;
	ProgressBar mProgressBar;

	public interface OnUpdatedWeatherDataListener {
		void onUpdatedData(Forecast forecast);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		listener = (OnUpdatedWeatherDataListener) context;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		// get widgets
		mTimeLabel = (TextView) view.findViewById(R.id.timeLabel);
		mTemperatureLabel = (TextView) view.findViewById(R.id.temperatureLabel);
		mHumidityValue = (TextView) view.findViewById(R.id.humidityValue);
		mPrecipValue = (TextView) view.findViewById(R.id.precipValue);
		mSummaryLabel = (TextView) view.findViewById(R.id.summaryLabel);
		mIconImageView = (ImageView) view.findViewById(R.id.iconImageView);
		mRefreshImageView = (ImageView) view.findViewById(R.id.refreshImageView);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		mProgressBar.setVisibility(View.INVISIBLE);

		final double latitude = 37.8267;
		final double longitude = -122.4233;

		mRefreshImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getForecast(latitude, longitude);
			}
		});

		if (MainActivity.forecast != null) {
			mForecast = MainActivity.forecast;
			updateDisplay();
		} else
			getForecast(latitude, longitude);

		// return our view
		return view;
	}

	private void getForecast(double latitude, double longitude) {
		String apiKey = "9d42ab51a43f67995b496895110fb7d7";
		String forecastUrl = "https://api.darksky.net/forecast/" + apiKey +
				"/" + latitude + "," + longitude;

		if (isNetworkAvailable()) {
			toggleRefresh();
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(forecastUrl)
					.build();
			Call call = client.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							toggleRefresh();
						}
					});
					Log.d(TAG, "Entering onFailure");
					alertUserAboutError("Oops! Error!", "There was an error. " +
							"Likely the URL was incorrect. Please try again.");
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							toggleRefresh();
						}
					});
					try {
						String jsonData = response.body().string();
						Log.v(TAG, jsonData);
						if (response.isSuccessful()) {
							mForecast = parseForecastDetails(jsonData);
							listener.onUpdatedData(mForecast);
							updateDisplay();
						} else {
							Log.d(TAG, "failure!");
							alertUserAboutError("Oops! Error!", "There was an error. " +
									"Perhaps the coordinates are incorrect. " +
									"Please try again.");
						}
					} catch (IOException e) {
						Log.e(TAG, "Exception caught: ", e);
					} catch (JSONException e) {
						Log.e(TAG, "Exception caught: ", e);
					}
				}
			});
		} else {
			alertUserAboutError("Oops! Error!", getResources().getString(R.string.network_unavailable_message));
		}
	}

	private void toggleRefresh() {
		if (mProgressBar.getVisibility() == View.INVISIBLE) {
			mProgressBar.setVisibility(View.VISIBLE);
			mRefreshImageView.setVisibility(View.INVISIBLE);
		} else {
			mProgressBar.setVisibility(View.INVISIBLE);
			mRefreshImageView.setVisibility(View.VISIBLE);
		}
	}

	private void updateDisplay() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Current current = mForecast.getCurrent();
				Log.d(TAG, current.getTemp() + " is the current temperature");
				mTemperatureLabel.setText(current.getTemp() + "");
				mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
				mHumidityValue.setText(current.getHumidity() + "");
				mPrecipValue.setText(current.getPrecipChance() + "%");
				mSummaryLabel.setText(current.getSummary());
				Drawable drawable = getResources().getDrawable(current.getIconId());
				mIconImageView.setImageDrawable(drawable);
			}
		});
	}

	private Forecast parseForecastDetails(String jsonData) throws JSONException {
		Forecast forecast = new Forecast();
		forecast.setCurrent(getCurrentDetails(jsonData));
		forecast.setHourlyForecast(getHourlyForecast(jsonData));
		forecast.setDailyForecast(getDailyForecast(jsonData));
		return forecast;
	}

	private Hour[] getHourlyForecast(String jsonData) throws JSONException {
		JSONObject forecast = new JSONObject(jsonData);
		String timezone = forecast.getString("timezone");
		JSONObject hourly = forecast.getJSONObject("hourly");
		JSONArray data = hourly.getJSONArray("data");
		Hour[] hours = new Hour[data.length()];
		for (int i = 0; i < data.length(); i++) {
			JSONObject hourObj = data.getJSONObject(i);
			Hour hour = new Hour();
			hour.setTime(hourObj.getLong("time"));
			hour.setSummary(hourObj.getString("summary"));
			hour.setTemperature(hourObj.getDouble("temperature"));
			hour.setIcon(hourObj.getString("icon"));
			hour.setTimezone(timezone);
			hours[i] = hour;
		}
		return hours;
	}

	private Day[] getDailyForecast(String jsonData) throws JSONException {
		JSONObject forecast = new JSONObject(jsonData);
		String timezone = forecast.getString("timezone");
		JSONObject daily = forecast.getJSONObject("daily");
		JSONArray data = daily.getJSONArray("data");
		Day[] days = new Day[data.length()];
		for (int i = 0; i < data.length(); i++) {
			JSONObject dayObj = data.getJSONObject(i);
			Day day = new Day();
			day.setTime(dayObj.getLong("time"));
			day.setSummary(dayObj.getString("summary"));
			day.setTemperatureMax(dayObj.getDouble("temperatureMax"));
			day.setIcon(dayObj.getString("icon"));
			day.setTimezone(timezone);
			days[i] = day;
		}
		return days;
	}

	private Current getCurrentDetails(String jsonData) throws JSONException {
		JSONObject forecast = new JSONObject(jsonData);
		JSONObject currently = forecast.getJSONObject("currently");

		Current current = new Current();
		current.setTimeZone(forecast.getString("timezone"));
		current.setIcon(currently.getString("icon"));
		current.setTime(currently.getLong("time"));
		current.setTemp(currently.getDouble("temperature"));
		current.setHumidity(currently.getDouble("humidity"));
		current.setPrecipChance(currently.getDouble("precipProbability"));
		current.setSummary(currently.getString("summary"));

		Log.d(TAG, current.getFormattedTime());

		return current;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager)
				getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}
		return isAvailable;
	}

	private void alertUserAboutError(String title, String message) {
		Bundle bundle = new Bundle();
		bundle.putString(AlertDialogFragment.TITLE, title);
		bundle.putString(AlertDialogFragment.MESSAGE, message);
		AlertDialogFragment dialog = new AlertDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(getActivity().getFragmentManager(), "error_dialog");
	}


}
