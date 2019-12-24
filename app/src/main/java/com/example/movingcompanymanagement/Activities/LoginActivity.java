package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.Semaphore;

public class LoginActivity extends AppCompatActivity {
    MaterialEditText login_email, login_password;
    Button login_btn;
    ProgressBar login_progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    //used to query the user's role
    ValueEventListener roleEventListener;
    //the data of the current user
    UserData currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = (MaterialEditText) findViewById(R.id.login_email);
        login_password = (MaterialEditText) findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        login_progressBar = findViewById(R.id.login_progressBar);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();
        setLoginListener();
    }

    private void setLoginListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    login_email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    login_password.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    login_password.setError("Password must be >= 6 Characters");
                    return;
                }

                login_progressBar.setVisibility(View.VISIBLE);


                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            readUserDetailsFromFireBase();


                        } else {
                            Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            login_progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }

    private void readUserDetailsFromFireBase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.child(firebaseAuth.getUid()).getValue(UserData.class);
                Toast.makeText(LoginActivity.this, currentUser.getFirstName() + " Logged in SuccessFully", Toast.LENGTH_LONG).show();

                if (currentUser.getRole().equals("Manager")) {
                    Log.i("noam", "manger");
                    Intent i = new Intent(getApplicationContext(), ManagerMainActivity.class);
                    i.putExtra("current user", currentUser);
                    startActivity(i);
                }
                if (currentUser.getRole().equals("Driver")) {
                    Log.i("noam", "driver");
                    Intent i = new Intent(getApplicationContext(), DriverMainActivity.class);
                    i.putExtra("current user", currentUser);
                    startActivity(i);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
