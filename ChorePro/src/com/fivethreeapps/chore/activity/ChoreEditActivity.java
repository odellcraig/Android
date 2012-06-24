package com.fivethreeapps.chore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

import com.fivethreeapps.chore.R;
import com.fivethreeapps.chore.adapter.CustomSpinnerAdapter;

public class ChoreEditActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editchore);
		
		Spinner repeatFirstSpinner = (Spinner)findViewById(R.id.repeatFirstSpinner);
		String[] repeatListItems = getResources().getStringArray(R.array.repeatListItems);
		CustomSpinnerAdapter repeatFirstCustomSpinnerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, repeatListItems);
		repeatFirstCustomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		repeatFirstSpinner.setAdapter(repeatFirstCustomSpinnerAdapter);
		
	}
}
