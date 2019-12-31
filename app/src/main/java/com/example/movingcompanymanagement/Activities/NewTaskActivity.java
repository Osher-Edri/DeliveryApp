package com.example.movingcompanymanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

public class NewTaskActivity extends BaseActivity {

    private MaterialEditText mFullName, mArea, mDateOfTransport, mDestinationAddress, mOriginAddress, mPhoneNumber, mTransportDetails;

    private Button btn_submit;
    private DatabaseReference databaseReference;
    private UserData mangerUser;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, dayOfMonth;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_new_task);

        Intent intent = getIntent();
        mangerUser = (UserData) intent.getSerializableExtra("current user");
        Log.i("noam", mangerUser.getFirstName());
        firebaseAuth = FirebaseAuth.getInstance();
        mFullName = (MaterialEditText)findViewById(R.id.transport_fullName);
        mDateOfTransport = (MaterialEditText)findViewById(R.id.transport_dateOfTransport);
        mDestinationAddress = (MaterialEditText)findViewById(R.id.transport_destinationAddress);
        mOriginAddress = (MaterialEditText)findViewById(R.id.transport_originAddress);
        mPhoneNumber = (MaterialEditText)findViewById(R.id.transport_phoneNumber);
        mTransportDetails = (MaterialEditText)findViewById(R.id.transport_transportDetails);
        mArea = (MaterialEditText)findViewById(R.id.transport_area);

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
                newTask.setOrder_date(sDateOfTransport); 
                newTask.setArea(sArea);
                newTask.setDriver("------");
                newTask.setSubmit_by_user(firebaseAuth.getUid());
                databaseReference = databaseReference.push();
                databaseReference.setValue(newTask);
                databaseReference.child("task_id").setValue(databaseReference.getKey());

                finish(); //close Activity
            }
        });
    }
}
