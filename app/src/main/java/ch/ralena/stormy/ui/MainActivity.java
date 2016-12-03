package ch.ralena.stormy.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ch.ralena.stormy.R;
import ch.ralena.stormy.fragments.MainFragment;
import ch.ralena.stormy.fragments.TabFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.ButtonClick {
	public static final String TAG = MainActivity.class.getSimpleName();

	public static final String DAILY_FORECAST = "DAILY_FORECAST";
	public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
	private static final String MAIN_FRAGMENT = "main_fragment";
	private static final String TAB_FRAGMENT = "tab_fragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// make sure fragment hasn't already been created
		TabFragment savedTabFragment = (TabFragment) getSupportFragmentManager()
				.findFragmentByTag(TAB_FRAGMENT);
		if (savedTabFragment == null) {
			// create and load fragment into our placeholder
			TabFragment tabFragment = new TabFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.add(R.id.placeHolder, tabFragment, TAB_FRAGMENT)
					.commit();
		}
	}

	@Override
	public void OnButtonClick(View v) {
		Log.d(TAG, "click");
		String tag = (String) v.getTag();
		if (tag.equals(MainFragment.KEY_HOURLY_BUTTON)) {
			MainFragment mainFragment = new MainFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.placeHolder, mainFragment, MAIN_FRAGMENT)
					.addToBackStack(null)
					.commit();
		} else {
			Toast.makeText(this, "Daily", Toast.LENGTH_SHORT).show();
		}
	}
}
