package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.google.android.material.textfield.TextInputEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.if5b.projectuaspab.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegister, btnLogin;
    private TextInputEditText myEmail, myPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private String getEmail, getPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myEmail = findViewById(R.id.tie_email);
        myPassword = findViewById(R.id.tie_password);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }

        };
    }

    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener != null) {
            auth.removeAuthStateListener(listener);
        }
    }

    private void loginUserAccount() {
        auth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Cannot Login, Please Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                getEmail = myEmail.getText().toString();
                getPassword = myPassword.getText().toString();

                if(TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
                    Toast.makeText(this, "Email Atau Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    loginUserAccount();
                    progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}