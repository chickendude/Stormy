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

	private static final String[] ERRORS = {
			"Error parsing location", "There was an error retrieving your device's location.",
	};


	public AlertDialog(FragmentManager fragmentManager, int errorType) {
		if (errorType > ERRORS.length / 2) {
			throw new IllegalArgumentException();
		} else {
			String title = ERRORS[errorType];
			String message = ERRORS[errorType+1];
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
