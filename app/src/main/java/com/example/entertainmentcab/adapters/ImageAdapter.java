package com.example.entertainmentcab.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entertainmentcab.R;
import com.example.entertainmentcab.models.Upload;
import com.squareup.picasso.Picasso;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private android.content.Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.images_layout, parent, false);
       /* GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        params.height = (parent.getMeasuredHeight()) - 2;
        params.width = (parent.getMinimumWidth()) - 2;
        view.setLayoutParams(params);
        //return new myViewHolder(myView);*/

        return new ImageViewHolder(view);
    }

    private static final String TAG = "ImageAdapter";
    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {

        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
                int itemviewh=  holder.itemView.getHeight();
        Log.d(TAG, "itemviewh: "+itemviewh);
        // Set the height by params
        params.height= (int) mContext.getResources().getDimension(R.dimen.itemview_height);
        params.width= (int) mContext.getResources().getDimension(R.dimen.itemview_width);
        params.topMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);;
        params.bottomMargin= (int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        params.leftMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        params.rightMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        holder.itemView.setLayoutParams(params);


        // set height of RecyclerView
        //holder.imageView.setLayoutParams(params);
        Upload uploadCurrent = mUploads.get(position);
        Log.d(TAG, "onBindViewHolder: "+mUploads.toString());
        holder.textViewName.setText(uploadCurrent.getmName());
        Picasso.get().load(uploadCurrent.getImageUrl()).into(holder.imageView);
        //Glide.with(mContext).load(uploadCurrent.getImageUrl()).into(holder.imageView);

        Log.d("DATA", "onBindViewHolder: " + Uri.parse(uploadCurrent.getImageUrl()));

    }

    //holder.imageView.setImageURI(Uri.parse(uploadCurrent.getImageUrl()));

    // holder.imageView.setImageURI(Uri.parse(uploadCurrent.getImageUrl()));


    @Override
    public int getItemCount() {
        return mUploads.size();
    }




    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.imag_view_upload);

        }
    }


}
