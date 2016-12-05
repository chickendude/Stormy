package ch.ralena.stormy.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ch.ralena.stormy.R;
import ch.ralena.stormy.fragments.MainFragment;
import ch.ralena.stormy.fragments.TabFragment;
import ch.ralena.stormy.weather.Forecast;

// TODO: look into other way to pass around the forecast variable
public class MainActivity extends AppCompatActivity implements MainFragment.OnUpdatedWeatherDataListener {
	public static final String TAG = MainActivity.class.getSimpleName();

	private static final String TAB_FRAGMENT = "tab_fragment";
	private TabFragment mTabFragment;

	public static Forecast forecast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// make sure fragment hasn't already been created
		TabFragment savedTabFragment = (TabFragment) getSupportFragmentManager()
				.findFragmentByTag(TAB_FRAGMENT);
		if (savedTabFragment == null) {
			// create and load fragment into our placeholder
			mTabFragment = new TabFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.add(R.id.placeHolder, mTabFragment, TAB_FRAGMENT)
					.commit();
		}
	}

	@Override
	public void onUpdatedData(Forecast forecast) {
		// TODO: check cause of null pointer exception on mTabFragment
		this.forecast = forecast;
		Log.d(TAG, "Updated weather data");
		mTabFragment.updateData(forecast);
	}
}
