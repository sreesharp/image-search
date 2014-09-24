package com.sreesharp.imagesearch.adapters;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.sreesharp.imagesearch.R;
import com.sreesharp.imagesearch.models.ImageResult;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageResultAdapter extends ArrayAdapter<ImageResult> {

	
	
	public ImageResultAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.gallery_item, images);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		ImageResult image = getItem(position);
        if (v == null) {
           v = LayoutInflater.from(getContext()).inflate(R.layout.gallery_item, parent, false);
        }
		
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvTitle.setText(Html.fromHtml(image.title));
		ImageView ivThumbnail = (ImageView)v.findViewById(R.id.ivThumbnail);
		ivThumbnail.setImageResource(0);
		Picasso.with(v.getContext()).load(image.thumbnailUrl).into(ivThumbnail);
		return v;
	}
	
}
