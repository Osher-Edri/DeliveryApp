package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddWorkerActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    MaterialEditText register_firstName,register_lastName,register_email,register_password,register_phoneNumber;
    ProgressBar register_progressBar;
    Spinner register_permission;
    Button register_btn;
    FirebaseAuth firebaseAuth;
    //FirebaseAuth second = FirebaseAuth.
    DatabaseReference databaseReference;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        register_firstName =  findViewById(R.id.addWorker_firstName);
        register_lastName = findViewById(R.id.addWorker_lastName);
        register_email = findViewById(R.id.addWorker_email);
        register_password = findViewById(R.id.addWorker_password);
        register_phoneNumber = findViewById(R.id.addWorker_phoneNumber);

        //define dropdown menu to select worker permission
        register_permission = findViewById(R.id.addWorker_permission);
        String[] items = new String[]{"Manager", "Driver"};
        //create an adapter to describe how the items are displayed.
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        register_permission.setAdapter(adapter);
        register_permission.setOnItemSelectedListener(this);
        register_btn = findViewById(R.id.addWorker_btn);
        register_progressBar = findViewById(R.id.addWorker_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        userData = new UserData();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");


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

                register_progressBar.setVisibility(View.VISIBLE);

                userData.setEmail(email);
                userData.setFirstName(firstName);
                userData.setLastName(lastName);
                userData.setPassword(password);
                userData.setPhoneNumber(phoneNumber);
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userData.setId(firebaseAuth.getUid());
                            databaseReference.child(firebaseAuth.getUid()).setValue(userData);
                            Toast.makeText(AddWorkerActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ManagerMainActivity.class));
                        }
                        else{
                            Toast.makeText(AddWorkerActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            register_progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                finish();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.userData.setRole((String)parent.getAdapter().getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
