package com.fivethreeapps.chore.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.ImageView;

import com.fivethreeapps.chore.R;

public class ChildEditActivity extends Activity {
	private static final String TAG = "EditChildActivity";
	private static final int REQ_TAKE_PIC  = 0;
	private static final int REQ_CHOOSE_PIC = 1;
	private static final int REQ_CHOOSE_REWARD = 2;
	private static final String TEMP_PHOTO_FILE = "FiveThreeAppsPickTemp.jpg";

	//Members
	private ImageView mChildPicture;
	private Button mRewardButton;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editchild);

		mChildPicture = (ImageView)findViewById(R.id.imageButtonPicture);
		mChildPicture.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openContextMenu(v);
			}
		});

		mChildPicture.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				MenuInflater inflater = getMenuInflater();
				inflater.inflate(R.menu.menucontextpictures, menu);

			}
		});
		
		//Set listener for reward button
		mRewardButton = (Button)findViewById(R.id.buttonReward);
		mRewardButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Reward Button was clicked");
				Intent i = new Intent(v.getContext(), RewardListActivity.class);
		        startActivityForResult(i, REQ_CHOOSE_REWARD);	
			}
		});
		
		
	}

	/**
	 * Get a temporary URI for storing the selected file
	 * @return Uri if properly created from file, null otherwise
	 */
	private Uri getTempUri() {
		File tempFile = getTempFile();
		if(tempFile != null) 
			return Uri.fromFile(tempFile);
		else
			return null;
	}

	/**
	 * Get a temporary file on the SD card will raise dialog if no SD card
	 * @return file created if successful, otherwise raises dialog and returns null
	 */
	private File getTempFile() {
		if (isSDCARDMounted()) {

			File f = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
			try { f.createNewFile(); } 
			catch (IOException e) {
				Log.e(TAG, "Error getting temporary file: " + e.toString());
			}
			return f;
		} 
		
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle(R.string.sdTitle);
		alert.setMessage(getResources().getString(R.string.sdMessage));
		
		//Set an OK message to make user feel nice
		alert.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "User clicked ok from no-SD dialog.");
			}
		});
		
		
		alert.show();
		return null;
	}

	/**
	 * Is the SD card present/mounted
	 * @return true if SD card is mounted, false otherwise
	 */
	private boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		
		Log.w(TAG, "SD Card is not mounted");
		return false;
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			
		case R.id.takePicture:
			Log.i(TAG, "Taking picture from camera.");
			Intent takePickIntent = new Intent("android.media.action.IMAGE_CAPTURE");
			takePickIntent.putExtra("crop", "true");
			takePickIntent.putExtra("aspectX", 1);
			takePickIntent.putExtra("aspectY", 1);
			takePickIntent.putExtra("noFaceDetection",false);
			takePickIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			startActivityForResult(takePickIntent, REQ_TAKE_PIC);
			return true;
			
		case R.id.choosePicture:
			Log.i(TAG, "Picking picture from gallery");
			Intent choosePicIntent = new Intent(Intent.ACTION_GET_CONTENT);
			choosePicIntent.putExtra("crop", "true");
			choosePicIntent.putExtra("aspectX", 1);
			choosePicIntent.putExtra("aspectY", 1);
			
			//Only attempt if SDCard is mounted
			Uri tempUri = getTempUri();
			if(tempUri != null) { choosePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri()); }
			else {return false; }
			
			
			choosePicIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			choosePicIntent.putExtra("noFaceDetection",false); 
			choosePicIntent.setType("image/*");
			startActivityForResult(choosePicIntent, REQ_CHOOSE_PIC);
			return true;
		}
		return super.onContextItemSelected(item);
	}



	private void setChildPicture(Bitmap photo) {
		photo = Bitmap.createScaledBitmap(photo, 90, 90, false);
		mChildPicture.setImageBitmap(photo);
	}

	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_TAKE_PIC && resultCode == RESULT_OK)
		{
			if(data != null) {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				setChildPicture(photo);
			}
		}

		if(requestCode == REQ_CHOOSE_PIC && resultCode == RESULT_OK) {
			if(data != null) {
				Uri tempUri = getTempUri();
				Bitmap photo = BitmapFactory.decodeFile(tempUri.getPath());
				setChildPicture(photo);
			}
		}
		
		if(requestCode == REQ_CHOOSE_REWARD && resultCode == RESULT_OK) {
			Log.i(TAG, "Reward was choosen.");
			//If we have enough points, reward the reward and decrease the points, otherwise prompt for confirmation.
		}
	}
}
