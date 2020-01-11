package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskDetailsActivityManger extends MangerBaseActivity {
    private TextView edtFullName, edtPhoneNumber, edtOriginAddress, edtDestinationAddress, edtTransportDay, edtTransportDescription, edtstatus;
    TaskData taskDetails;
    EditText taskNote;
    Spinner spinnerStatus;
    DatabaseReference getStatusReference;
    Button btnSubmitStatus;
    RadioGroup taskStatus;
    RadioButton checkedRadioButton, radioButtonOpen, radioButtonClose, radioButtonProblem;
    private static final int REQUEST_CALL = 1;
    ImageView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.task_order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        taskDetails = (TaskData) intent.getSerializableExtra("current data");

        btnSubmitStatus = findViewById(R.id.btn_submit_status);
        edtFullName = findViewById(R.id.edt_fullName);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        edtOriginAddress = findViewById(R.id.edt_origin_address);
        edtDestinationAddress = findViewById(R.id.edt_destination_address);
        edtTransportDay = findViewById(R.id.edt_transport_day);
        edtTransportDescription = findViewById(R.id.edt_transport_description);
        taskNote = findViewById(R.id.edt_note_task);
        taskStatus = findViewById(R.id.radio_group_status);
        radioButtonOpen = findViewById(R.id.radio_open);
        radioButtonClose = findViewById(R.id.radio_close);
        radioButtonProblem = findViewById(R.id.radio_problem);
        phone = findViewById(R.id.phone_image);

        edtFullName.setText(taskDetails.getContact_name());
        edtPhoneNumber.setText(taskDetails.getContact_phone());
        edtOriginAddress.setText(taskDetails.getOriginAddress());
        edtDestinationAddress.setText(taskDetails.getAddress());
        edtTransportDay.setText(taskDetails.getTask_date());
        edtTransportDescription.setText(taskDetails.getOrder_note());
        taskNote.setText(taskDetails.getDriver_note());
        setCurrentStatus();
        btnSubmitStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskDetails();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });




    }

    private void makePhoneCall() {
        String number = taskDetails.getContact_phone();

        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(TaskDetailsActivityManger.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(TaskDetailsActivityManger.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(TaskDetailsActivityManger.this, "NO PHONE NUMBER", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setCurrentStatus() {
        String status = taskDetails.getStatus();
        switch (status) {
            case "close":
                radioButtonClose.setChecked(true);
                break;
            case "problem":
                radioButtonProblem.setChecked(true);
                break;
            default:
                radioButtonOpen.setChecked(true);
        }
    }

    private void updateTaskDetails() {
        int radioId = taskStatus.getCheckedRadioButtonId();
        checkedRadioButton = findViewById(radioId);
        Log.i("radioID", checkedRadioButton.getText().toString());
        String note = taskNote.getText().toString();
        getStatusReference = FirebaseDatabase.getInstance().getReference("Tasks").child(taskDetails.getTask_id());
        taskDetails.setStatus(checkedRadioButton.getText().toString());
        taskDetails.setDriver_note(note);
        getStatusReference.setValue(taskDetails);
        Toast.makeText(TaskDetailsActivityManger.this, "Task details update", Toast.LENGTH_SHORT).show();
    }
}
