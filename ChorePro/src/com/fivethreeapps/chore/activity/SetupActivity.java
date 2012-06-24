package com.fivethreeapps.chore.activity;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.fivethreeapps.chore.R;
import com.fivethreeapps.chore.adapter.SetupExpandableListAdapter;


/**
 * Demonstrates expandable lists backed by a Simple Map-based adapter
 */
public class SetupActivity extends ExpandableListActivity {
	private static final String TAG = "SetupActivity";
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private static final int GROUP_COUNT = 4;
    
    private static final int GROUP_CHILDREN = 0;
    private static final int GROUP_CHORES 	= 1;
    private static final int GROUP_REWARDS	= 2;
    private static final int GROUP_SETUP	= 3;
    
    private static final int CHILD_INDEX_VIEW_EDIT = 0;
    private static final int CHILD_INDEX_QUICK_ADD = 1;
    
    
	public static final String KEY_ROWID = "_id"; //TODO: move to dbAdapter class
	private SetupExpandableListAdapter mAdapter;
	
	private ArrayList<Boolean> isRowExpanded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRowExpanded = new ArrayList<Boolean>();
        
        final ExpandableListView expandableListView = getExpandableListView(); 
        for(int i = 0; i < GROUP_COUNT; ++i) {
        	isRowExpanded.add(true);
        }
        
        fillData();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	saveTemporaryState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }
    
    
    protected void saveTemporaryState() {
    	
        final ExpandableListView expandableListView = getExpandableListView(); 
        for(int group = 0; group < GROUP_COUNT; ++group){
        	if(expandableListView.isGroupExpanded(group)) {
        		isRowExpanded.set(group, true);
        	}
        	else {
        		isRowExpanded.set(group, false);
        	}
        }
    }
    
    
    
    @Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		if(groupPosition == GROUP_CHILDREN) {
			//If editing an existing child
			if(childPosition != (mAdapter.getChildrenCount(groupPosition)-1)) {
				editChild(id);
			}
			else {
				createChild();
			}
			return true;
		}
		if(groupPosition == GROUP_CHORES) {
			if(childPosition == CHILD_INDEX_VIEW_EDIT)
				viewChores();
			if(childPosition == CHILD_INDEX_QUICK_ADD)
				addChore();
			return true;
		}
		if(groupPosition == GROUP_REWARDS) {
			if(childPosition == CHILD_INDEX_VIEW_EDIT)
				viewRewards();
			if(childPosition == CHILD_INDEX_QUICK_ADD)
				addReward();
			return true;
		}
    	return true;
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    
    private void fillData() {
        
    	mAdapter = new SetupExpandableListAdapter(this, null,
                R.layout.expandablerowtitle,
                R.layout.expandablerowstatic,
                new String[] { Contacts.DISPLAY_NAME }, // Name for group layouts
                new int[] { android.R.id.text1 },
                new String[] { Phone.NUMBER }, // Number for child layouts
                new int[] { android.R.id.text1 });


    	setListAdapter(mAdapter);
    	
    	
    	// Expand the groups as they were before
    	final ExpandableListView expandableListView = getExpandableListView(); 
        for(int group = 0; group < GROUP_COUNT; ++group){
        	if(isRowExpanded.get(group).booleanValue()) {
        		expandableListView.expandGroup(group);
        	}
        }
    }
    
    
	private void createChild(){
		Log.i(TAG, "Creating new child.");
		Intent i = new Intent(this, ChildEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);		
	}
	
		
    private void editChild(long id){
    	Log.i(TAG, "Editing existing child: " + id);
        Intent i = new Intent(this, ChildEditActivity.class);
        i.putExtra(KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
    
    private void viewChores() {
    	Intent i = new Intent(this, ChoreListActivity.class);
    	startActivity(i);
    }
    
    private void addChore() {
    	
    }

    
    private void viewRewards() {
    	Intent i = new Intent(this, RewardListActivity.class);
    	startActivity(i);
    }
    
    private void addReward() {
    	
    }
    
}