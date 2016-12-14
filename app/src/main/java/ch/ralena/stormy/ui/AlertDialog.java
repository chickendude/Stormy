package ch.ralena.stormy.ui;

import android.app.FragmentManager;
import android.os.Bundle;

import ch.ralena.stormy.fragments.AlertDialogFragment;

/**
 * Created by crater-windoze on 12/13/2016.
 */

public class AlertDialog {
	private Bundle bundle;
	private FragmentManager mFragmentManager;
	public static final int LOCATION_ERROR = 0;
	public static final int NETWORK_ERROR = 1;

	private static final String[] ERRORS = {
			"Location Error", "There was an error retrieving your device's location.",
			"Network Error", "Your device doesn't appear to be connected to the internet",
	};


	public AlertDialog(FragmentManager fragmentManager, int errorType) {
		if (errorType > ERRORS.length / 2) {
			throw new IllegalArgumentException();
		} else {
			String title = ERRORS[errorType*2];
			String message = ERRORS[errorType*2+1];
			initialize(fragmentManager, title, message);
		}
	}

	public AlertDialog(FragmentManager fragmentManager, String title, String message) {
		initialize(fragmentManager, title, message);
	}

	private void initialize(FragmentManager fragmentManager, String title, String message) {
		mFragmentManager = fragmentManager;
		bundle = new Bundle();
		bundle.putString(AlertDialogFragment.TITLE, title);
		bundle.putString(AlertDialogFragment.MESSAGE, message);
	}

	public void show() {
		AlertDialogFragment dialog = new AlertDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(mFragmentManager, "error_dialog");
	}
}
