package com.example.movingcompanymanagement.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;

public class DriverMainActivity extends DriverBaseActivity {

    UserData driverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_driver_main);
        Intent intent = getIntent();
        driverUser = (UserData) intent.getSerializableExtra("current user");
    }

    public void AllTaskList(View view) {
        Intent intent = new Intent(getApplicationContext(), DriverTasksListActivity.class);
        intent.putExtra("current user", driverUser);
        intent.putExtra("filter", "all");
        startActivity(intent);
    }

    public void todayTasks(View view) {
        Intent intent = new Intent(getApplicationContext(), DriverTasksListActivity.class);
        intent.putExtra("current user", driverUser);
        intent.putExtra("filter", "today");
        startActivity(intent);
    }

}
