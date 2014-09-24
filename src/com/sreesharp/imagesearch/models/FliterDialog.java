package com.sreesharp.imagesearch.models;

import com.sreesharp.imagesearch.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class FliterDialog extends DialogFragment {
	
	public FliterDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public static FliterDialog newInstance() {
		FliterDialog frag = new FliterDialog();
		Bundle args = new Bundle();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.edit_filters, container);
		/*
		String title = getArguments().getString("title", "Enter Name");
		getDialog().setTitle(title);
		// Show soft keyboard automatically
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				*/
		return view;
	}

}
