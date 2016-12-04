package ch.ralena.stormy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.ralena.stormy.R;
import ch.ralena.stormy.weather.Forecast;

/**
 * Created by crater-windoze on 12/3/2016.
 */

public class TabFragment extends Fragment {
	public static final String[] TABS = {"Summary","Hourly","Daily"};
	private static final String TAG = TabFragment.class.getSimpleName();
	public static final String KEY_FORECAST = "key_forecast";

	private static MainFragment mMainFragment;
	private static WeatherFragment mHourlyWeatherFragment;
	private static WeatherFragment mDailyWeatherFragment;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab, container, false);

		mMainFragment = new MainFragment();
		mHourlyWeatherFragment = new HourlyFragment();
		mDailyWeatherFragment = new DailyFragment();

		ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
			@Override
			public CharSequence getPageTitle(int position) {
				return TABS[position];
			}

			@Override
			public Fragment getItem(int position) {
				switch(position) {
					case 0:
						return mMainFragment;
					case 1:
						return mHourlyWeatherFragment;
					default:
						return mDailyWeatherFragment;
				}
			}

			@Override
			public int getCount() {
				return 3;
			}
		});
		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
		tabLayout.setupWithViewPager(viewPager);
		return view;
	}

	public void updateData(final Forecast forecast) {
		mHourlyWeatherFragment.updateWeather(forecast);
		mDailyWeatherFragment.updateWeather(forecast);
	}
}
