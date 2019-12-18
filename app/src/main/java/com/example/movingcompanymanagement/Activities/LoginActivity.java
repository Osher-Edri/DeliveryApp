package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movingcompanymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    MaterialEditText login_email,login_password;
    Button login_btn;
    ProgressBar login_progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    //used to query the user's role
    ValueEventListener roleEventListener;
    //the role of the current user
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = (MaterialEditText) findViewById(R.id.login_email);
        login_password = (MaterialEditText) findViewById(R.id.login_password);
        login_btn =  findViewById(R.id.login_btn);
        login_progressBar =  findViewById(R.id.login_progressBar);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        setLoginListener();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getUid() != null) {
            setRoleListener();
            databaseReference.addListenerForSingleValueEvent(roleEventListener);
        }
        else{
            role = "Driver";
        }



    }

    private void setRoleListener(){
         roleEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                role = dataSnapshot.child(firebaseAuth.getUid()).child("role").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
    }
    private void setLoginListener(){
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
                            if (role.equals("Manager")) {
                                startActivity(new Intent(getApplicationContext(), ManagerMainActivity.class));
                            }
                            if (role.equals("Driver")) {
                                startActivity(new Intent(getApplicationContext(), DriverMainActivity.class));
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            login_progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}
