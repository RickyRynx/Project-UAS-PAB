package com.if5b.projectbaru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.if5b.projectbaru.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

         if (firebaseUser.getDisplayName()!=null) {
            binding.tvHome.setText(firebaseUser.getDisplayName());
        }else {
            binding.tvHome.setText("Login Gagal!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}