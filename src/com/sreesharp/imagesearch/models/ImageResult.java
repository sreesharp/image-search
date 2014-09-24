package com.sreesharp.imagesearch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	public String title;
	public String imageUrl;
	public String thumbnailUrl;
	
	public ImageResult(JSONObject jsonObj)
	{
		try {
			title = jsonObj.getString("titleNoFormatting");
			imageUrl = jsonObj.getString("url");
			thumbnailUrl = jsonObj.getString("tbUrl");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ImageResult> getImageListFromJSONList(JSONArray jsonArray) 
	{
		ArrayList<ImageResult> imageList = new ArrayList<ImageResult>();
		try {
			for (int i=0; i<jsonArray.length(); i++){
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				if(jsonObj!=null)
					imageList.add(new ImageResult(jsonObj));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageList;
	}
}
