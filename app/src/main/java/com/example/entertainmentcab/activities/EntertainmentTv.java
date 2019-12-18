package com.example.entertainmentcab.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entertainmentcab.R;
import com.example.entertainmentcab.adapters.MusicAdapter;
import com.example.entertainmentcab.adapters.VideoAdapter;
import com.example.entertainmentcab.models.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class EntertainmentTv extends AppCompatActivity {

    DatabaseReference mDbReference;
    VideoView mVideoviewVideo;
    VideoView mVideoViewMusic;
    RecyclerView mRecyclerViewVideo;
    RecyclerView mRecyclerViewMusic;
    VideoAdapter mVideoAdapter;
    MusicAdapter mMusicAdapter;
    ProgressBar mProgressbar;
    List<Upload> mUploadVideo;
    List<Upload> mUploadMusic;
    Context mContext;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entertainment_videos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContext = EntertainmentTv.this;
        GridLayoutManager gridLayoutManagerVideo = new GridLayoutManager(this, 1,
                GridLayoutManager.HORIZONTAL, false);

        GridLayoutManager gridLayoutManagerMusic = new GridLayoutManager(this, 1,
                GridLayoutManager.HORIZONTAL, false);

        mDbReference = FirebaseDatabase.getInstance().getReference().child("videos");
        mRecyclerViewVideo = (RecyclerView) findViewById(R.id.recycler_view_video);
        mRecyclerViewMusic = (RecyclerView) findViewById(R.id.recycler_view_music);
        mRecyclerViewVideo.setHasFixedSize(true);
        mRecyclerViewVideo.setLayoutManager(gridLayoutManagerVideo);
        mRecyclerViewMusic.setHasFixedSize(true);
        mRecyclerViewMusic.setLayoutManager(gridLayoutManagerMusic);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        mVideoviewVideo = (VideoView) findViewById(R.id.video_view_video);
        mVideoViewMusic = (VideoView) findViewById(R.id.video_view_music);
        mUploadVideo = new ArrayList<Upload>();
        mUploadMusic = new ArrayList<Upload>();

        mDbReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Upload upload = dataSnapshot1.getValue(Upload.class);
                    if (upload.getmName().equalsIgnoreCase("video")) {
                        mUploadVideo.add(upload);
                    }
                    if (upload.getmName().equalsIgnoreCase("music")) {
                        mUploadMusic.add(upload);
                    }
                    mProgressbar.setVisibility(View.VISIBLE);
                }
                mVideoAdapter = new VideoAdapter(mContext, mUploadVideo);
                mVideoAdapter.notifyDataSetChanged();
                mRecyclerViewVideo.setAdapter(mVideoAdapter);
                mMusicAdapter = new MusicAdapter(mContext, mUploadMusic);
                mMusicAdapter.notifyDataSetChanged();
                mRecyclerViewMusic.setAdapter(mMusicAdapter);
                mProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EntertainmentTv.this, "Oops....Somthing is wrong",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showVideo(String strUri) {
        final Dialog dialog = new Dialog(this,android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.videodialog);




        final VideoView video = (VideoView) dialog.findViewById(R.id.video_view);

        final MediaController mMediaController = new MediaController(this);

        Uri uri = Uri.parse(strUri);

        video.setVideoURI(uri);
        video.requestFocus();

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(
                        new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer player, int width, int height) {

                                video.setMediaController(mMediaController);
                                mMediaController.setAnchorView(video);
                               mMediaController.show();
                            }
                        }
                );

                video.start();

            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });
        WindowManager.LayoutParams a = dialog.getWindow().getAttributes();
        a.dimAmount = 0;
        dialog.getWindow().setAttributes(a);
        dialog.show();
    }
}
