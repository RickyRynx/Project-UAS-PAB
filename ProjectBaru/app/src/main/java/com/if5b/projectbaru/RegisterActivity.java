package com.if5b.projectbaru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.if5b.projectbaru.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        binding.btnLogin.setOnClickListener(view -> {
            finish();
        });

        binding.btnRegister.setOnClickListener(view -> {
            if (binding.etGmailRegister.getText().length()>0 && binding.etPassword.getText().length()>0 && binding.etKonfirmasipass.getText().length()>0 ){
                if (binding.etPassword.getText().toString().equals(binding.etKonfirmasipass.getText().toString())) {
                    register(binding.etGmailRegister.getText().toString(), binding.etPassword.getText().toString());
                }else {
                    Toast.makeText(this, "Silahkan masukan password yang sama!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    Toast.makeText(RegisterActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
                    if (firebaseUser!=null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(email)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this, "Register Gagal!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}