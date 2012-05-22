package com.fivethreeapps.chore.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.fivethreeapps.chore.R;

public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
	    //Log.i(TAG, "If not shown, showing EULA");
	    //new SimpleEula(this).show();
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Reusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    //intent = new Intent().setClass(this, ApproveActivity.class);
	    //spec = tabHost.newTabSpec("approve").setIndicator(res.getString(R.string.tabLabelApprove), res.getDrawable(android.R.drawable.ic_dialog_info)).setContent(intent);
	    //tabHost.addTab(spec);

	    // Change the intent for the StatisticsTab
	    intent = new Intent().setClass(this, PreviewActivity.class);
	    spec = tabHost.newTabSpec("preview").setIndicator(res.getString(R.string.tabLabelPreview), res.getDrawable(android.R.drawable.ic_dialog_info)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    //Change the intent for the StatisticsTab
	    //intent = new Intent().setClass(this, AssignActivity.class);
	    //spec = tabHost.newTabSpec("assign").setIndicator(res.getString(R.string.tabLabelAssign), res.getDrawable(android.R.drawable.ic_dialog_info)).setContent(intent);
	    //tabHost.addTab(spec);
	    
	    //Change the intent for the StatisticsTab
	    intent = new Intent().setClass(this, SetupActivity.class);
	    spec = tabHost.newTabSpec("setup").setIndicator(res.getString(R.string.tabLabelSetup), res.getDrawable(android.R.drawable.ic_dialog_info)).setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
    }
}