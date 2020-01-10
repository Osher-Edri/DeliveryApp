package com.example.movingcompanymanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends MangerBaseActivity {

    private MaterialEditText mFullName, mArea, mDateOfTransport, mDestinationAddress, mOriginAddress, mPhoneNumber, mTransportDetails;

    private Button btn_submit;
    private DatabaseReference databaseReference;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, dayOfMonth;
    private FirebaseAuth firebaseAuth;
    private int taskDay, taskMonth, taskYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manager_new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mangerUser = (UserData) intent.getSerializableExtra("current user");
        Log.i("noam", mangerUser.getFirstName());
        firebaseAuth = FirebaseAuth.getInstance();
        mFullName = findViewById(R.id.transport_fullName);
        mDateOfTransport = findViewById(R.id.transport_dateOfTransport);
        mDestinationAddress = findViewById(R.id.transport_destinationAddress);
        mOriginAddress = findViewById(R.id.transport_originAddress);
        mPhoneNumber = findViewById(R.id.transport_phoneNumber);
        mTransportDetails =findViewById(R.id.transport_transportDetails);
        mArea = findViewById(R.id.transport_area);

        //remov title bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mDateOfTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NewTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mDateOfTransport.setText(day + "/" + (month + 1) + "/" + year);
                                taskDay = datePicker.getDayOfMonth();
                                taskMonth = datePicker.getMonth() + 1;
                                taskYear =datePicker.getYear();
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        btn_submit = findViewById(R.id.btn_transport_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sFullName = mFullName.getText().toString();
                String sDateOfTransport = mDateOfTransport.getText().toString();
                String sDestinationAddress = mDestinationAddress.getText().toString();
                String sOriginAddress = mOriginAddress.getText().toString();
                String sPhoneNumber = mPhoneNumber.getText().toString();
                String sTransportDetails = mTransportDetails.getText().toString();
                String sArea = mArea.getText().toString();
                TaskData newTask = new TaskData();
                newTask.setContact_name(sFullName);
                newTask.setAddress(sDestinationAddress);
                newTask.setOriginAddress(sOriginAddress);
                newTask.setContact_phone(sPhoneNumber);
                newTask.setOrder_note(sTransportDetails);
                newTask.setTask_date(sDateOfTransport);
                newTask.setArea(sArea);
                newTask.setDriver("------");
                newTask.setStatus("open");
                newTask.setTaskYear(taskYear);
                newTask.setTaskDay(taskDay);
                newTask.setTaskMonth(taskMonth);
                newTask.setSubmit_by_user(firebaseAuth.getUid());
                databaseReference = databaseReference.push();
                databaseReference.setValue(newTask);
                databaseReference.child("task_id").setValue(databaseReference.getKey());

                finish(); //close Activity
            }
        });
    }
}
