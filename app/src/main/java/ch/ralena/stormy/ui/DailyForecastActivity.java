package ch.ralena.stormy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import ch.ralena.stormy.R;
import ch.ralena.stormy.adapters.DayAdapter;
import ch.ralena.stormy.weather.Day;

public class DailyForecastActivity extends Activity {

	private Day[] mDays;

	ListView mListView;
	TextView mEmptyTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_forecast);

		Intent intent = getIntent();
		Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
		mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
		DayAdapter adapter = new DayAdapter(this, mDays);
		mListView.setAdapter(adapter);
		mListView.setEmptyView(mEmptyTextView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String dayOfTheWeek = mDays[position].getDayOfTheWeek();
				String conditions = mDays[position].getSummary().toLowerCase();
				String highTemp = mDays[position].getTemperatureMax()+"";
				String message = String.format("On %s the high will be %s and it will be %s",dayOfTheWeek, highTemp, conditions);
				Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_LONG).show();
			}
		});
	}
}
