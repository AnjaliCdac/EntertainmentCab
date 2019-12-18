package com.example.entertainmentcab.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.entertainmentcab.R;
import com.example.entertainmentcab.activities.EntertainmentTv;
import com.example.entertainmentcab.activities.VideoPlay;
import com.example.entertainmentcab.models.Upload;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private android.content.Context mContext;
    private List<Upload> mUploads;


    public MusicAdapter(Context context, List<Upload> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }
    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.entertainment_music_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        params.height= (int) mContext.getResources().getDimension(R.dimen.itemview_height_music);
        params.width= (int) mContext.getResources().getDimension(R.dimen.itemview_width_music);
        params.leftMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        params.rightMargin=(int) mContext.getResources().getDimension(R.dimen.itemview_topmargin);
        holder.itemView.setLayoutParams(params);

        Upload uploadCurrent = mUploads.get(position);
       // holder.textViewName.setText(uploadCurrent.getmName());
         final String uristr = uploadCurrent.getImageUrl();
         Uri uri =  Uri.parse(uristr);
         holder.videoView.setVideoURI(uri);
         holder.videoView.seekTo(1);
         holder.videoView.requestFocus();
         holder.videoView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                /* Intent intent = new Intent(mContext.getApplicationContext(), VideoPlay.class);
                 intent.putExtra("URI", uristr);
                 mContext.startActivity(intent);*/
                 ((EntertainmentTv)mContext).showVideo(uristr);
             }
         });
         holder.playButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 holder.videoView.start();
             }
         });
         holder.pauseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 holder.videoView.pause();
             }
         });
        //Glide.with(mContext).load(uploadCurrent.getImageUrl()).into(holder.videoView);
        //Picasso.get().load(Uri.parse(uploadCurrent.getImageUrl())).into(holder.videoView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        //public TextView textViewName;
        public VideoView videoView;
        public Button playButton;
        public Button pauseButton;
        public MusicViewHolder(View itemView) {
            super(itemView);
            //textViewName = itemView.findViewById(R.id.text_view_music);
            videoView = itemView.findViewById(R.id.video_view_music);
            playButton = itemView.findViewById(R.id.button_play_music);
            pauseButton = itemView.findViewById(R.id.button_pause_music);
        }
    }
}
