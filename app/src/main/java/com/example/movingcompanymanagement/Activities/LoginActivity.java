package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movingcompanymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    MaterialEditText login_email,login_password;
    Button login_btn;
    ProgressBar login_progressBar;
    FirebaseAuth firebaseAuth;
    TextView login_txt_create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = (MaterialEditText) findViewById(R.id.login_email);
        login_password = (MaterialEditText) findViewById(R.id.login_password);
        login_btn =  findViewById(R.id.login_btn);
        login_progressBar =  findViewById(R.id.login_progressBar);
        login_txt_create_account = findViewById(R.id.login_txt_create_account);

        firebaseAuth = FirebaseAuth.getInstance();


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    login_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    login_password.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    login_password.setError("Password must be >= 6 Characters");
                    return;
                }

                login_progressBar.setVisibility(View.VISIBLE);


                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            login_progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        login_txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }
}
