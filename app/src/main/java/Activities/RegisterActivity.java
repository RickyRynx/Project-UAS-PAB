package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.if5b.projectuaspab.R;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText myEmail, myPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    private String tieEmail, tiePassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myEmail = findViewById(R.id.tie_email);
        myPassword = findViewById(R.id.tie_passwordRegister);
        btnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekDataUser();
            }
        });
    }

    private void cekDataUser() {
        tieEmail = myEmail.getText().toString();
        tiePassword = myPassword.getText().toString();

        if(TextUtils.isEmpty(tieEmail) || TextUtils.isEmpty(tiePassword)) {
            Toast.makeText(this, "Email atau Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else {
            if(tiePassword.length() < 6 ) {
                Toast.makeText(this, "Password Terlalu Pendek, Minimal 6 Karakter!", Toast.LENGTH_SHORT).show();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                createUserAccount();
            }
        }
    }

    private void createUserAccount() {
        auth.createUserWithEmailAndPassword(tieEmail, tiePassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Sign Up Succesfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


}