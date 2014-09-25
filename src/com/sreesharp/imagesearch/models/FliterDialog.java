package com.sreesharp.imagesearch.models;

import com.sreesharp.imagesearch.R;
import com.sreesharp.imagesearch.R.id;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FliterDialog extends DialogFragment {
	
	public interface FilterDialogListener {
        void onFinishFliterDialog(String size, String type, String color);
    }
	
	public FliterDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public static FliterDialog newInstance() {
		FliterDialog frag = new FliterDialog();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.edit_filters, container);
		getDialog().setTitle(R.string.title_filter_dialog);
		
		Button btnSave = (Button)view.findViewById(id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 FilterDialogListener listener = (FilterDialogListener) getActivity();
				 
				 String type = ((Spinner)view.findViewById(id.spinnerType)).getSelectedItem().toString();
				 String size = ((Spinner)view.findViewById(id.spinnerSize)).getSelectedItem().toString();
				 String color = ((Spinner)view.findViewById(id.spinnerColor)).getSelectedItem().toString();
		         listener.onFinishFliterDialog(size, type,color);
				 dismiss();
			}
		});
		
		Button btnCancel = (Button)view.findViewById(id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		return view;
	}
}
