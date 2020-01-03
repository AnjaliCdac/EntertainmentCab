
package com.example.entertainmentcab.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entertainmentcab.R;
import com.example.entertainmentcab.adapters.ImageAdapter;
import com.example.entertainmentcab.models.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDbReference;
    ImageView mImageViewSpecialOffer;
    TextView mEntertainTv;
    RecyclerView mRecyclerView;
    ImageAdapter mImageAdapter;
    ImageView mImageViewQRCode;
    ProgressBar mProgressbar;
    List<Upload> mUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
                GridLayoutManager.HORIZONTAL, false);
        String email = getIntent().getExtras().getString("email");
        mDbReference = FirebaseDatabase.getInstance().getReference().child("uploads");
        mImageViewQRCode = (ImageView) findViewById(R.id.img_qr);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEntertainTv = (TextView) findViewById(R.id.entertain_button);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        mImageViewSpecialOffer=(ImageView) findViewById(R.id.special_offer_image) ;
        mUpload = new ArrayList<Upload>();
        mDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Upload upload = dataSnapshot1.getValue(Upload.class);
                    /*if(upload.getmName().equalsIgnoreCase("qr")){
                        setQRCode(upload.getImageUrl());
                    }*/
                    //else {
                        mUpload.add(upload);
                   // }
                  mProgressbar.setVisibility(View.VISIBLE);
                }
                mImageAdapter = new ImageAdapter(MainActivity.this, mUpload);
                mImageAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mImageAdapter);
                mProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Oops....Somthing is wrong",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mImageViewSpecialOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).
                        show();
            }
        });
        mImageViewQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        mEntertainTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EntertainmentTv.class);
                startActivity(intent);

               // Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        if(!email.equals(""))
        {
            new ImageDownloaderTask(mImageViewQRCode).execute("https://api.qrserver.com/v1/create-qr-code/?size=1000x1000&data="+email);
        }
        else {
            Toast.makeText(MainActivity.this,"enter something!",Toast.LENGTH_LONG).show();
        }
    }

    private void setQRCode(String imageUrl) {
        Picasso.get().load(imageUrl).into(mImageViewQRCode);
    }

}

