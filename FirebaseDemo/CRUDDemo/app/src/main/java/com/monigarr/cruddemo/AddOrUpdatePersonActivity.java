/*
 * Copyright (c) 2016.
 */

package com.monigarr.cruddemo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by monicapeters on 8/31/16.
 */


public class AddOrUpdatePersonActivity extends AppCompatActivity {

    Button bOK,bCancel;
    Person person;
    int position;
    EditText pFirstname,pLastname,pZipcode,pDob;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_update_person);

        position = getIntent().getIntExtra("Position", -1);

        cl = (CoordinatorLayout) findViewById(R.id.cdlayout);

        pFirstname = (EditText) findViewById(R.id.pFirstname);
        pLastname = (EditText) findViewById(R.id.pLastname);
        pZipcode = (EditText) findViewById(R.id.pZipcode);
        pDob = (EditText) findViewById(R.id.pDob);

        bOK = (Button) findViewById(R.id.bOk);
        bCancel = (Button) findViewById(R.id.bCancel);

        if(position != -1) {
            getSupportActionBar().setTitle("Edit");
            searchPerson(position);
            person = new Person();
        }
        else {
            getSupportActionBar().setTitle("Add");
            person = null;
        }

        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pFirstname.getText().toString().trim().equals("") ||
                        pLastname.getText().toString().trim().equals("") ||
                        pZipcode.getText().toString().trim().equals("") ||
                        pDob.getText().toString().trim().equals("")) {
                    final Snackbar snackBar = Snackbar.make(cl, "Enter all Data.", Snackbar.LENGTH_LONG);
                    snackBar.setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                }
                else {
                    Person p = new Person();
                    p.setFirstname(pFirstname.getText().toString());
                    p.setLastname(pLastname.getText().toString());
                    p.setZipcode(pZipcode.getText().toString());
                    p.setDob(Integer.parseInt(pDob.getText().toString()));
                    if (person == null)
                        MainActivity.getInstance().addPerson(p);
                    else
                        MainActivity.getInstance().updatePersonDetails(p, position);
                    finish();
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void searchPerson(int position) {
        String clickedKey = MainActivity.getInstance().getKeysArray().get(position);
        MainActivity.getInstance().getmDatabase().child(clickedKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Person person = dataSnapshot.getValue(Person.class);
                        pFirstname.setText(person.getFirstname());
                        pLastname.setText(person.getLastname());
                        pZipcode.setText(person.getZipcode());
                        pDob.setText(String.valueOf(person.getDob()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}