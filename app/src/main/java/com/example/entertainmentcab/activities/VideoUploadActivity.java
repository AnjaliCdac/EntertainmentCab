package com.example.entertainmentcab.activities;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.entertainmentcab.R;
import com.example.entertainmentcab.models.Upload;
import com.example.entertainmentcab.models.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class VideoUploadActivity extends AppCompatActivity {


   private Uri videouri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoref;
    private DatabaseReference mDatabaseRef;
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_upload);

        mEditTextName = (EditText)findViewById(R.id.editText_file_name_video);
        videoref = FirebaseStorage.getInstance().getReference("videos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos");
    }

    public void upload(View view) {
        if (videouri != null ) {

            final StorageReference fileStorage=videoref.child(System.currentTimeMillis()+
                    "."+getFileExtention(videouri));

            StorageTask uploadTask = fileStorage.putFile(videouri);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(VideoUploadActivity.this,
                            "Upload failed: " + e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(VideoUploadActivity.this, "Upload complete",
                                    Toast.LENGTH_LONG).show();
                            fileStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                   final String  downloadUrl =
                                            uri.toString();
                                   Upload upload=new Upload(mEditTextName.getText().toString().trim(),downloadUrl);
                                    String uploadId=mDatabaseRef.push().getKey();

                                    mDatabaseRef.child(uploadId).setValue(upload);
                                }
                            });
                        }
                    }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            updateProgress(taskSnapshot);

                        }
                    });



        } else {
            Toast.makeText(VideoUploadActivity.this, "Nothing to upload",
                    Toast.LENGTH_LONG).show();
        }


    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getMimeTypeFromExtension(cR.getType(uri));
    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {

        @SuppressWarnings("VisibleForTests") long fileSize =
                taskSnapshot.getTotalByteCount();

        @SuppressWarnings("VisibleForTests")
        long uploadBytes = taskSnapshot.getBytesTransferred();

        long progress = (100 * uploadBytes) / fileSize;

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar_video);
        progressBar.setProgress((int) progress);
    }

    public void record(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);
    }




    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        videouri = data.getData();

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "Video saved to:\n" +
                        videouri, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
