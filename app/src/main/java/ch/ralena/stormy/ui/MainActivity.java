package ch.ralena.stormy.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ch.ralena.stormy.R;
import ch.ralena.stormy.fragments.DailyFragment;
import ch.ralena.stormy.fragments.HourlyFragment;
import ch.ralena.stormy.fragments.MainFragment;
import ch.ralena.stormy.fragments.WeatherFragment;
import ch.ralena.stormy.weather.Forecast;

// TODO: look into other way to pass around the mForecast variable
public class MainActivity extends AppCompatActivity implements MainFragment.OnUpdatedWeatherDataListener,
		View.OnClickListener,
		LocationListener {
	public static final String TAG = MainActivity.class.getSimpleName();
	public static final String[] TABS = {"Summary", "Hourly", "Daily"};
	private static final int GPS_REQUEST_CODE = 1;
	private static final String KEY_ISFAHRENHEIT = "key_isfahrenheit";
	public static int gUpdates = 0;

	private static Forecast mForecast;
	private boolean mIsFahrenheit = true;


	private static MainFragment mMainFragment;
	private static WeatherFragment mHourlyWeatherFragment;
	private static WeatherFragment mDailyWeatherFragment;
	private FloatingActionButton mFloatingActionButton;
	private TextView mFABText;

	private LocationManager mLocationManager;
	private String mProvider;
	private Location mLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
		mFABText = (TextView) findViewById(R.id.floatingActionButtonText);
		mFloatingActionButton.setOnClickListener(this);

		if (savedInstanceState == null) {
			mMainFragment = new MainFragment();
			mHourlyWeatherFragment = new HourlyFragment();
			mDailyWeatherFragment = new DailyFragment();
		} else {
			mIsFahrenheit = savedInstanceState.getBoolean(KEY_ISFAHRENHEIT);
			mFABText.setText(mIsFahrenheit ? "F" : "C");
		}

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public CharSequence getPageTitle(int position) {
				return TABS[position];
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {
					case 0:
						return mMainFragment;
					case 1:
						return mHourlyWeatherFragment;
					case 2:
						return mDailyWeatherFragment;
					default:
						return null;
				}
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				Fragment fragment = (Fragment) super.instantiateItem(container, position);
				switch (position) {
					case 0:
						mMainFragment = (MainFragment) fragment;
						break;
					case 1:
						mHourlyWeatherFragment = (WeatherFragment) fragment;
						break;
					case 2:
						mDailyWeatherFragment = (WeatherFragment) fragment;
						break;
				}
				return fragment;
			}

			@Override
			public int getCount() {
				return TABS.length;
			}
		});
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setupWithViewPager(viewPager);
		// set up location listener
		if (!hasLocationPermission()) {
			ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
			return;
		}
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		mProvider = mLocationManager.getBestProvider(new Criteria(), false);
		//noinspection ResourceType (we check permissions in the haslocationpermission method)
		mLocation = mLocationManager.getLastKnownLocation(mProvider);
		if (mLocation != null) {
			mMainFragment.setLatitude(mLocation.getLatitude());
			mMainFragment.setLongitude(mLocation.getLongitude());
			mMainFragment.setLocationName(getLocationName(mLocation));
		}
		//noinspection ResourceType
		mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
	}

	private String getLocationName(Location location) {
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		String locationName = "Couldn't parse location";
		try {
			List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			if (addressList != null && addressList.size() > 0) {
				locationName = addressList.get(0).getLocality();
				mMainFragment.setLocationName(locationName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locationName;
	}

	@Override
	public void onUpdatedData(Forecast forecast) {
//		getLocation();
		mForecast = forecast;
		mForecast.setIsFahrenheit(mIsFahrenheit);
		Log.d(TAG, "Updated weather data");
		mMainFragment.updateDisplay(forecast);
		mHourlyWeatherFragment.updateWeather(forecast);
		mDailyWeatherFragment.updateWeather(forecast);
	}

	// Floating Action Button's onclick listener
	@Override
	public void onClick(View v) {
		mIsFahrenheit = !mIsFahrenheit;
		mFABText.setText(mIsFahrenheit ? "F" : "C");
		if (mForecast != null) {
			onUpdatedData(mForecast);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (hasLocationPermission()) {
			//noinspection ResourceType
			mLocationManager.removeUpdates(this);
		}
	}

	private boolean hasLocationPermission() {
		return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(KEY_ISFAHRENHEIT, mIsFahrenheit);
		super.onSaveInstanceState(outState);
	}

	// ########## Location Listener methods ############
	@Override
	public void onLocationChanged(Location location) {
		mLocation = location;
		mMainFragment.setLatitude(mLocation.getLatitude());
		mMainFragment.setLongitude(mLocation.getLongitude());
		mMainFragment.setLocationName(getLocationName(mLocation));
		mMainFragment.getForecast();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
	// ########## End Location Listener Methods ############

}
