package com.fivethreeapps.chore.adapter;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;


public class ChoreListAdapter extends SimpleCursorAdapter {

	public ChoreListAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
	}
	
	
	
    //private static final String TAG_NAME = "CustomListAdapter";
	
	/*private Context mContext;
    
    private RulesDbAdapter mDbHelper;
    private Cursor mCurrentCursor;

    public CustomListAdapter(Context context, int layout, Cursor c,
            String[] from, int[] to, RulesDbAdapter dbHelper) {
        super(context, layout, c, from, to);
        this.mCurrentCursor = c;
        this.mContext = context;
        this.mDbHelper = dbHelper;

    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.rule_item, null);
        }

        if (!this.mCurrentCursor.moveToPosition(pos)) {
            throw new SQLException("CustomCursorAdapter.getView: Unable to move to position: "+pos);
        }

        CheckBox cBox = (CheckBox) v.findViewById(R.id.ruleEnabledCheckBox);

        // save the row's _id value in the checkbox's tag for retrieval later
        cBox.setTag(Integer.valueOf(this.mCurrentCursor.getInt(RulesDbAdapter.KEY_ID_idx)));
        
        if (this.mCurrentCursor.getInt(RulesDbAdapter.KEY_ENABLED_idx) != 0) {
            cBox.setChecked(true);
        } else {
            cBox.setChecked(false);
        }
        
        //cBox.setOnClickListener(this);
        cBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isChecked = ((CheckBox)v).isChecked();
				//Log.d(TAG_NAME, "Selected a CheckBox and in onCheckedChanged: " + isChecked);
            	//Get the tag we stored away earlier
                Integer _rowid = (Integer) v.getTag();
                if(!mDbHelper.setEnabled(_rowid, isChecked)){
                	//Log.e(TAG_NAME, "Could not set the enabled field for rule in position " + _rowid);
                }
                
                //Show a toast to the user
                String isEnabledStr = (isChecked)? "enabled" : "disabled";
                String ruleName = mDbHelper.fetchRuleName(_rowid);
                Toast t = Toast.makeText(mContext, "\"" + ruleName + "\" is now " + isEnabledStr, Toast.LENGTH_SHORT); t.show();
                //Log.d(TAG_NAME, "-- _id="+_rowid+", isChecked="+isChecked);
			}
		}); 
        //Put the name value in the correct text view
        TextView txtTitle = (TextView) v.findViewById(R.id.ruleTextTitle);
        txtTitle.setText(this.mCurrentCursor.getString(this.mCurrentCursor.getColumnIndex(RulesDbAdapter.KEY_NAME)));
        TextView msgText = (TextView) v.findViewById(R.id.ruleTextMessage);
        msgText.setText(this.mCurrentCursor.getString(this.mCurrentCursor.getColumnIndex(RulesDbAdapter.KEY_MESSAGE)));
        return (v);
    }    */
}
