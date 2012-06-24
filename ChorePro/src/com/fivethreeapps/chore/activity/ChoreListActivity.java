package com.fivethreeapps.chore.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fivethreeapps.chore.R;

public class ChoreListActivity extends ListActivity {
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
	
	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listchore);  

		
		//setListAdapter(new ArrayAdapter<String>(this, R.layout.rule_item, RULES));
		ListView lv = getListView();
		lv.setItemsCanFocus(false);
		lv.setTextFilterEnabled(true);
		
		View footer = getLayoutInflater().inflate(R.layout.rowreward, null);
		lv.addFooterView(footer);
		
		
		
		//Might have add-button at top
        /*mAddButton = (ImageButton)findViewById(R.id.buttonAdd);
        if(mAddButton != null){
	        mAddButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					createRule();
				}
			});
        }*/
        fillData();
        registerForContextMenu(getListView());
	}    
	
	private void fillData() {
		// create the grid item mapping
		
        String[] from = new String[] {"Title", "TitleDetails", "Points"};
        int[] to = new int[] { R.id.choreRowTitle, R.id.choreRowTitleDetails, R.id.choreRowPoints};
 
        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < 10; i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Title", "Chore Name Goes Here " + i);
            map.put("TitleDetails", "Weekly: M-W-F");
            map.put("Points", Integer.toString(10*i));
            fillMaps.add(map);
        }
 
        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.rowchore, from, to);
        setListAdapter(adapter);

		
		
		//Fill with real data
		/*// Get all of the rows from the database and create the item list
    	Cursor rulesCursor = mDbHelper.fetchAllRules();
        startManagingCursor(rulesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{RulesDbAdapter.KEY_NAME, RulesDbAdapter.KEY_ENABLED};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.ruleTextTitle, R.id.ruleEnabledCheckBox};

        // Now create a simple cursor adapter and set it to display
        CustomListAdapter rules = 
            new CustomListAdapter(this, R.layout.rule_item, rulesCursor, from, to, mDbHelper);
        setListAdapter(rules);
        */
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.menuoptionschore, menu);
	   return true;
    }
	
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_chore:
                createChore();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
	
	
	private void createChore(){
		Intent i = new Intent(this, ChoreEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);		
	}
	
	
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        editChore(id);
    }
	
    private void editChore(long id){
        Intent i = new Intent(this, ChoreEditActivity.class);
        //i.putExtra(RulesDbAdapter.KEY_ROWID, id);
        Log.i("ID Test", "Id = " + id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
    

    /**
     * Creates ability to delete rules
     */
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
    	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menucontextchore, menu);
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info;
        switch(item.getItemId()) {
            case R.id.delete_chore:
                info = (AdapterContextMenuInfo) item.getMenuInfo();
                //mDbHelper.deleteRule(info.id);
                fillData();
                return true;
            case R.id.edit_chore:
            	info = (AdapterContextMenuInfo) item.getMenuInfo();
            	editChore(info.id);
            	return true;
        }
        return super.onContextItemSelected(item);
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
	
}
