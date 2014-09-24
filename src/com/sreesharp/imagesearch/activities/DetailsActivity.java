package com.sreesharp.imagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sreesharp.imagesearch.R;
import com.sreesharp.imagesearch.R.id;
import com.sreesharp.imagesearch.R.layout;
import com.sreesharp.imagesearch.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.os.Build;

public class DetailsActivity extends Activity {
	
	private ShareActionProvider shareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		String url = getIntent().getStringExtra("url");
		ImageView ivDetail = (ImageView)findViewById(R.id.ivDetail);
		Picasso.with(this).load(url).into(ivDetail, new Callback() {
			@Override
			public void onSuccess() {
				 setupShareIntent();
			}
			@Override
			public void onError() {
	
			}
		});
		
	}
	
	private void setupShareIntent() {
		ImageView ivImage = (ImageView) findViewById(R.id.ivDetail);
	    Uri bmpUri = getLocalBitmapUri(ivImage); 
	    // Create share intent as described above
	    Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	    shareIntent.setType("image/*");
	    // Attach share event to the menu item provider
	    shareAction.setShareIntent(shareIntent);
		
	}
	
	public Uri getLocalBitmapUri(ImageView imageView) {
	    // Extract Bitmap from ImageView drawable
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp = null;
	    if (drawable instanceof BitmapDrawable){
	       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else {
	       return null;
	    }
	    // Store image to default external storage directory
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
		        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
	        file.getParentFile().mkdirs();
	        FileOutputStream out = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		
		MenuItem item = menu.findItem(R.id.menu_item_share);
		// Fetch reference to the share action provider
	    shareAction = (ShareActionProvider) item.getActionProvider();
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
