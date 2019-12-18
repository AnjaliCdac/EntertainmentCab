package com.example.entertainmentcab.adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.entertainmentcab.R;
import com.example.entertainmentcab.activities.EntertainmentTv;
import com.example.entertainmentcab.activities.VideoPlay;
import com.example.entertainmentcab.models.Upload;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{
    public static final String TAG = VideoAdapter.class.getSimpleName();
    private android.content.Context mContext;
    private List<Upload> mUploads;
     VideoPlay mVideoPlay;
    public VideoAdapter(Context context, List<Upload> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.entetainment_items, parent, false);
        return new VideoViewHolder(view);

    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull final VideoViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.height= (int) mContext.getResources().getDimension(R.dimen.itemview_height_video);
        params.width= (int) mContext.getResources().getDimension(R.dimen.itemview_width_video);
        params.leftMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        params.rightMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        holder.itemView.setLayoutParams(params);


        final Upload uploadCurrent = mUploads.get(position);

        //holder.textViewName.setText(uploadCurrent.getmName());
        final String uriStr = uploadCurrent.getImageUrl();
        final Uri uri =  Uri.parse(uriStr);

        holder.videoView.setVideoURI(uri);
        holder.videoView.seekTo(1);
        holder.videoView.requestFocus();
        holder.videoView.setLayoutParams(params);


        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Intent intent = new Intent(mContext,VideoPlay.class);
              // intent.putExtra("URI", uriStr);
               //mContext.startActivity(intent);

               ((EntertainmentTv)mContext).showVideo(uriStr);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public VideoView videoView;
        public Button buttonPlayVideo;
        public Button buttonPauseVideo;
        public VideoViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view_video);
             buttonPlayVideo = itemView.findViewById(R.id.button_play_video);
             buttonPauseVideo = itemView.findViewById(R.id.button_pause_video);
        }
    }
}
