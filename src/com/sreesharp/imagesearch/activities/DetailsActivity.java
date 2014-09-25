package com.sreesharp.imagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sreesharp.imagesearch.R;
import com.sreesharp.imagesearch.models.TouchImageView;

public class DetailsActivity extends Activity {
	
	private ShareActionProvider shareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		String url = getIntent().getStringExtra("url");
		int height = getIntent().getIntExtra("height", 600);
		int width = getIntent().getIntExtra("width", 600);
		TouchImageView ivDetail = (TouchImageView)findViewById(R.id.ivDetail);
		Picasso.with(this).load(url).resize(width, height).into(ivDetail, new Callback() {
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
		try
		{
			TouchImageView ivImage = (TouchImageView) findViewById(R.id.ivDetail);
		    Uri bmpUri = getLocalBitmapUri(ivImage); 
		    // Create share intent as described above
		    Intent shareIntent = new Intent();
		    shareIntent.setAction(Intent.ACTION_SEND);
		    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		    shareIntent.setType("image/*");
		    // Attach share event to the menu item provider
		    shareAction.setShareIntent(shareIntent);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public Uri getLocalBitmapUri(TouchImageView imageView) {
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
