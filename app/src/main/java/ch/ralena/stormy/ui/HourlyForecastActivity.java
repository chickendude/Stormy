package ch.ralena.stormy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import ch.ralena.stormy.R;
import ch.ralena.stormy.adapters.HourAdapter;
import ch.ralena.stormy.weather.Hour;

public class HourlyForecastActivity extends AppCompatActivity {

	private Hour[] mHours;

	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hourly_forecast);

		Intent intent = getIntent();
		Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
		mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

		HourAdapter adapter = new HourAdapter(this, mHours);
		mRecyclerView.setAdapter(adapter);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);

		mRecyclerView.setHasFixedSize(true);
	}

}
