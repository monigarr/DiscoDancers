/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.viewholders;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monigarr.MoniGarrChatDemo.R;
import com.monigarr.MoniGarrChatDemo.models.Friend;
import com.monigarr.MoniGarrChatDemo.services.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monicapeters on 8/29/16.
 */

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFirstname;
    private EditText etLastname;
    private EditText etZipcode;
    private EditText etDob;
    private TextView tvFriends;
    private Button btCreate;
    private OnAddFriendClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_friend, container);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        DatabaseReference.setAndroidContext(this);

        btCreate = (Button) findViewById(R.id.btCreate);
        etFirstname = (EditText) findViewById(R.id.etFirstname);
        etLastname = (EditText) findViewById(R.id.etLastname);
        etDob = (EditText) findViewById(R.id.etDob);
        etZipcode = (EditText) findViewById(R.id.etZipcode);
        etFirstname.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btCreate.setOnClickListener(this);
        etDob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btCreate: {
                if (isUserInfoValidate()) {
                    //create firebase object
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                    //get values to store
                    String firstname = etFirstname.getText().toString().trim();
                    String lastname = etLastname.getText().toString().trim();
                    String zipcode = etZipcode.getText().toString().trim();
                    Date dob = etDob.getDob().toString().trim();

                    //create Friend object
                    Friend friend = new Friend();

                    //add friend values
                    friend.setFirstname(firstname);
                    friend.setLastname(lastname);
                    friend.setZipcode(zipcode);
                    friend.setDob(dob);
                    listener.onAddFriendClickListener(friend);

                    //store friend values to firebase
                    ref.child("Friend").setValue(friend);
                    //realtime data update
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                //get data from snapshot
                                Friend friend = postSnapshot.getValue(Friend.class);

                                //add to string
                                String theFriend = "Firstname: " +
                                        friend.getFirstname() +
                                        "\nLastname: " +
                                        friend.getLastname() +
                                        "\nZipcode: " +
                                        friend.getZipcode() +
                                        "\nDob: " +
                                        friend.getDob() +
                                        "\n\n";

                                //show in textview
                                tvFriends.setText(theFriend);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Read Failure: " + DatabaseError.getMessage());
                        }
                    });
                }
                break;
            }
            case R.id.etDob: {
                Calendar now = Calendar.getInstance();
                final DatePickerDialog dateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                                Calendar checkedCalendar = Calendar.getInstance();
                                checkedCalendar.set(year, monthOfYear, dayOfMonth);
                                date = checkedCalendar.getTime();
                                etDob.setText(DateFormat.convertDateToString(date));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dateDialog.setMaxDate(now);
                dateDialog.show((getActivity()).getFragmentManager(), this.getClass().getName());
                break;
            }
        }
    }

    private boolean isUserInfoValidate() {
        return !etFirstname.getText().toString().isEmpty() &&
                !etLastname.getText().toString().isEmpty() &&
                !etZipcode.getText().toString().isEmpty() &&
                !etDob.getText().toString().isEmpty();
    }

    public void setListener(OnAddFriendClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddFriendClickListener {
        void onAddFriendClickListener(Friend friend);
    }

}