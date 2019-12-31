package com.example.movingcompanymanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;

public class DriverMainActivity extends BaseActivity {

    UserData driverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        Intent intent = getIntent();
        driverUser = (UserData) intent.getSerializableExtra("current user");
        Log.i("Driver name", driverUser.getFirstName());
    }
    public void tasksList(View view){
        Intent intent = new Intent(getApplicationContext(),DriverTasksListActivity.class);
        intent.putExtra("current user", driverUser);
        startActivity(intent);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}
