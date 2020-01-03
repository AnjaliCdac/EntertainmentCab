package com.example.entertainmentcab.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entertainmentcab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextPass = (EditText) findViewById(R.id.txtPasswordLogin);
        editTextEmail = (EditText) findViewById(R.id.txtEmailLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void btnUserLogin_Click(View v)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,
                "please wait..","processing....",true);
        Log.d("value", "btnUserLogin_Click:email "+editTextEmail.getText().toString());
        (firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),editTextPass.
                getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"login successfully",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    i.putExtra("email",firebaseAuth.getCurrentUser().getEmail());
                    Log.d("value", "onComplete: "+firebaseAuth.getCurrentUser().getEmail());
                    startActivity(i);
                }
                else {
                    Log.e("Error",task.getException().toString() );
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
