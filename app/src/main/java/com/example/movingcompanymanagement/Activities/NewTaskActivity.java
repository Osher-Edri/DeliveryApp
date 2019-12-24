package com.example.movingcompanymanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class NewTaskActivity extends AppCompatActivity {

    private MaterialEditText mFullName, mArea, mDateOfTransport, mDestinationAddress, mOriginAddress, mPhoneNumber, mTransportDetails;

    private Button btn_submit;
    private DatabaseReference databaseReference;
    UserData mangerUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        Intent intent = getIntent();
        mangerUser = (UserData) intent.getSerializableExtra("current user");
        Log.i("noam", mangerUser.getFirstName());

         mFullName = (MaterialEditText)findViewById(R.id.transport_fullName);
        mDateOfTransport = (MaterialEditText)findViewById(R.id.transport_dateOfTransport);
        mDestinationAddress = (MaterialEditText)findViewById(R.id.transport_destinationAddress);
        mOriginAddress = (MaterialEditText)findViewById(R.id.transport_originAddress);
        mPhoneNumber = (MaterialEditText)findViewById(R.id.transport_phoneNumber);
       mTransportDetails = (MaterialEditText)findViewById(R.id.transport_transportDetails);
        mArea = (MaterialEditText)findViewById(R.id.transport_area);

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
                newTask.setSubmit_by_user(mangerUser.getFirstName());
                databaseReference = databaseReference.push();
                databaseReference.setValue(newTask);
                databaseReference.child("task_id").setValue(databaseReference.getKey());

                finish(); //close Activity
            }
        });
    }
}
