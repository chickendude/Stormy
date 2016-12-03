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

import java.util.ArrayList;
import java.util.List;

import ch.ralena.stormy.R;

/**
 * Created by crater-windoze on 12/3/2016.
 */

public class TabFragment extends Fragment {
	public static final String[] TABS = {"Summary","Hourly","Daily"};
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab, container, false);

		final List<MainFragment> fragments = new ArrayList<>();
		fragments.add(new MainFragment());
		fragments.add(new MainFragment());
		fragments.add(new MainFragment());

		ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
			@Override
			public CharSequence getPageTitle(int position) {
				return TABS[position];
			}

			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);
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
}
