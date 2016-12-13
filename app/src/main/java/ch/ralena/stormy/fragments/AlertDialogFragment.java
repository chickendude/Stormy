package ch.ralena.stormy.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import ch.ralena.stormy.R;

public class AlertDialogFragment extends DialogFragment {

	public static final String TITLE = "title";
	public static final String MESSAGE = "message";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		String title = bundle.getString(TITLE);
		String message = bundle.getString(MESSAGE);
		Context context = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(R.string.error_ok_button_text, null);
		AlertDialog dialog = builder.create();
		return dialog;
	}
}
