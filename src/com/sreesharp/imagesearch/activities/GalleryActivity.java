package com.sreesharp.imagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sreesharp.imagesearch.R;
import com.sreesharp.imagesearch.adapters.ImageResultAdapter;
import com.sreesharp.imagesearch.models.EndlessScrollListener;
import com.sreesharp.imagesearch.models.FliterDialog;
import com.sreesharp.imagesearch.models.FliterDialog.FilterDialogListener;
import com.sreesharp.imagesearch.models.ImageResult;

public class GalleryActivity extends FragmentActivity implements FilterDialogListener  {
	
	private EditText etQuery;
	private GridView gvResults;
	private AsyncHttpClient httpClient;
	ArrayList<ImageResult> imageList;
	ImageResultAdapter imageAdapter;
	private String searchText;
	private String imageType = "Any"; //JPG/PNG etc
	private String imageSize = "Any"; //medium, Large etc
	private String imageColor = "Any"; //Image filter colors
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        setupViews();
        
        httpClient = new AsyncHttpClient();
        
        imageList = new ArrayList<ImageResult>();
        imageAdapter = new ImageResultAdapter(this, imageList);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Intent intent = new Intent(GalleryActivity.this,DetailsActivity.class) ;
				ImageResult image = imageAdapter.getItem(position);
				intent.putExtra("url", image.imageUrl);
				intent.putExtra("width", image.width);
				intent.putExtra("height", image.height);
				startActivity(intent);
				
			}
		});
        
        gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadImages(page);
			}
		});
     }

    private void loadImages(int page) {
    	
    	//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=query
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=") 
    			.append(searchText).append("&start=").append(page);
    	if(!imageType.equals("Any"))
    		sbUrl.append("&as_filetype=").append(imageType);
    	if(!imageSize.equals("Any"))
    		sbUrl.append("&imgsz=").append(imageSize);
    	if(!imageColor.equals("Any"))
    		sbUrl.append("&imgcolor=").append(imageColor);
    	httpClient.get(sbUrl.toString(), new JsonHttpResponseHandler(){
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					if(response != null)
					{
						imageList = ImageResult.getImageListFromJSONList(
								response.getJSONObject("responseData").getJSONArray("results"));
						imageAdapter.addAll(imageList);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		
    	});
	}

    private void setupViews() {
    	gvResults = (GridView) findViewById(R.id.gvResults);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
        	   
        	   if(!isNetworkAvailable())
        	   {
        		   Toast.makeText(GalleryActivity.this, "Please check your network!!", Toast.LENGTH_LONG).show();
        	   }
                // perform query here
        	   else if(searchText != query)
	           	{
	       	    	//Clear the result if different search string
	       	    	imageList.clear();
	       	    	imageAdapter.clear();
	       	    	//Load the initial page
	           		searchText = query;
	           		loadImages(1);
	           	}
	            return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
       return super.onCreateOptionsMenu(menu);
    }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	showFilterDialog();
            return true;
        }
        return false;
    }
	 
	 private void showFilterDialog() {
	      FragmentManager fm = getSupportFragmentManager();
	      FliterDialog filterDlg = FliterDialog.newInstance();
	      filterDlg.show(fm, "fragment_edit_filters");
	  }

	@Override
	public void onFinishFliterDialog(String size, String type, String color) {
		
		imageType = type;
		imageColor = color;
		if(size == "None")
			imageSize = "None";
		else if (size == "Small")
			imageSize = "icon";
		else if (size == "Medium")
			imageSize = "small|medium|large|xlarge";
		else if (size == "Large")
			imageSize = "xxlarge";
		else if (size == "Extra-Large")
			imageSize = "huge";

	}

	
	private Boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
}
