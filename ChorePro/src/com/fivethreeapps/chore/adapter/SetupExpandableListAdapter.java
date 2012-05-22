package com.fivethreeapps.chore.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivethreeapps.chore.R;

public class SetupExpandableListAdapter extends BaseExpandableListAdapter {
	private static final int GROUP_COUNT = 4;
	private static final int CHILD_COUNT = 2;
	private String[] mGroups;
	private String[] mStaticTags;
	private String[] mChildren =   {"Braxton","Kaden","Logan","Maximus"};//FIXME: change this source to cursor from hard data
	private Context mContext;
	LayoutInflater mInflator;

	public SetupExpandableListAdapter(Context context, Cursor childCursor, int groupLayout,
            int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
            int[] childrenTo) {
		super();
		mContext = context;
		Resources res = mContext.getResources();
        mGroups = res.getStringArray(R.array.setup_groups);
    	mStaticTags = res.getStringArray(R.array.setup_static_menu_items);
    	mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(groupPosition == 0) {
			//Get linear layout and slap in name/pic
			LinearLayout layout = (LinearLayout)mInflator.inflate(R.layout.expandable_row_child, null);
			((TextView)layout.getChildAt(1)).setText((String)getChild(groupPosition,childPosition));
			return layout;
		}
		
		TextView v = (TextView)mInflator.inflate(R.layout.expandable_row_static, null);
		v.setText((String)getChild(groupPosition,childPosition));
        return v;
	}
	
	@Override
	public int getGroupCount() {
		return GROUP_COUNT;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if(groupPosition == 0){
			if(childPosition < mChildren.length)
				return mChildren[childPosition];
			return mStaticTags[1];
		}
		return mStaticTags[childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupPosition == 0){
			//Return Count of database plus one
			return mChildren.length+1;
		}
		else {
			return CHILD_COUNT;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroups[groupPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
		TextView v = (TextView)mInflator.inflate(R.layout.expandable_row_title, null);
		v.setText((String)getGroup(groupPosition));
		return v;
    }

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}