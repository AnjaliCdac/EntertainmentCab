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

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPass;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextEmail = (EditText)findViewById(R.id.txtEmailRegistration);
        editTextPass = (EditText)findViewById(R.id.txtPasswordRegistration);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void btnRegistrationUser_Click(View v)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this,
                "please wait...","processing..",true);
        (firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),editTextPass.
                getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(RegistrationActivity.this,"Register successfull",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(i);
                }
                else {
                    Log.e("Error",task.getException().toString() );
                    Toast.makeText(RegistrationActivity.this,task.getException().getMessage()
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
