package com.fivethreeapps.chore.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.fivethreeapps.chore.R;

public class ChildActivityEdit extends Activity {
	private static final String TAG = "EditChildActivity";
	private static final int REQ_TAKE_PIC  = 0;
	private static final int REQ_CHOOSE_PIC = 1;
	private static final String TEMP_PHOTO_FILE = "FiveThreeAppsPickTemp.jpg";

	//Members
	private ImageView mChildPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_child);

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
				inflater.inflate(R.menu.picture_menu, menu);

			}
		});
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {
		if (isSDCARDMounted()) {

			File f = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
			try { f.createNewFile(); } 
			catch (IOException e) {
				Log.e(TAG, "Error getting temporary file: " + e.toString());
			}
			return f;
		} 
		return null;
	}

	private boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		
		Log.w(TAG, "SD Card is not mounted");
		return false;
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.take_picture:
			Log.i(TAG, "Taking picture from camera.");
			Intent takePickIntent = new Intent("android.media.action.IMAGE_CAPTURE");
			takePickIntent.putExtra("crop", "true");
			takePickIntent.putExtra("aspectX", 1);
			takePickIntent.putExtra("aspectY", 1);
			takePickIntent.putExtra("noFaceDetection",false);
			takePickIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			startActivityForResult(takePickIntent, REQ_TAKE_PIC);
			return true;
		case R.id.choose_picture:
			Log.i(TAG, "Picking picture from gallery");
			Intent choosePicIntent = new Intent(Intent.ACTION_GET_CONTENT);
			choosePicIntent.putExtra("crop", "true");
			choosePicIntent.putExtra("aspectX", 1);
			choosePicIntent.putExtra("aspectY", 1);
			choosePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
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
				File tempFile = getTempFile();
				//Bitmap photo = BitmapFactory.decodeFileDescriptor(tempFile);
				//setChildPicture(photo);
			}
		}
	}
}
