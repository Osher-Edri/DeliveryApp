package com.example.movingcompanymanagement;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Register extends AppCompatActivity {
MaterialEditText register_firstName,register_lastName,register_email,register_password,register_phoneNumber;
Button register_btn;
ProgressBar register_progressBar;
FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_firstName = (MaterialEditText) findViewById(R.id.register_firstName);
        register_lastName = (MaterialEditText) findViewById(R.id.register_lastName);
        register_email = (MaterialEditText) findViewById(R.id.register_email);
        register_password = (MaterialEditText) findViewById(R.id.register_password);
        register_phoneNumber = (MaterialEditText) findViewById(R.id.register_phoneNumber);

        register_btn = findViewById(R.id.register_btn);
        register_progressBar = findViewById(R.id.register_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        //If user already logged in
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
//                String firstName = register_firstName.getText().toString().trim();
//                String lastName = register_lastName.getText().toString().trim();
//                String phoneNumber = register_phoneNumber.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    register_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    register_password.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    register_password.setError("Password must be >= 6 Characters");
                    return;
                }
//                if(TextUtils.isEmpty(firstName)){
//                    register_firstName.setError("Please Fill In All Required Fields");
//                    return;
//                }
//                if(TextUtils.isEmpty(lastName)){
//                    register_lastName.setError("Please Fill In All Required Fields");
//                    return;
//                }
//                if(TextUtils.isEmpty(phoneNumber)){
//                    register_phoneNumber.setError("Please Fill In All Required Fields");
//                    return;
//                }

                register_progressBar.setVisibility(View.VISIBLE);

                //Register in Firebase
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            register_progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });



    }
}
