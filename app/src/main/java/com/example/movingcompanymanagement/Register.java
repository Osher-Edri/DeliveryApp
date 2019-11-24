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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Register extends AppCompatActivity {
MaterialEditText register_firstName,register_lastName,register_email,register_password,register_phoneNumber;
Button register_btn;
ProgressBar register_progressBar;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
User user;


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

        user = new User();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
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
                String firstName = register_firstName.getText().toString().trim();
                String lastName = register_lastName.getText().toString().trim();
                int phoneNumber;
                try{
                    phoneNumber = Integer.parseInt(register_phoneNumber.getText().toString().trim());
                }
                catch (NumberFormatException e){ //happens when phone number field is left empty
                    register_phoneNumber.setError("Please Fill phoneNumber Fields");
                    return;
                }


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
                if(TextUtils.isEmpty(firstName)){
                    register_firstName.setError("Please Fill firstName Fields");
                    return;
                }
                if(TextUtils.isEmpty(lastName)){
                    register_lastName.setError("Please Fill lastName Fields");
                    return;
                }
//                if(TextUtils.isEmpty(Integer.toString(phoneNumber))){
//                    register_phoneNumber.setError("Please Fill phoneNumber Fields");
//                    return;
//                }

                register_progressBar.setVisibility(View.VISIBLE);

                //Insert data into Realtime Database (firebase)
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);
                user.setPhoneNumber(phoneNumber);
                databaseReference.push().setValue(user);

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
