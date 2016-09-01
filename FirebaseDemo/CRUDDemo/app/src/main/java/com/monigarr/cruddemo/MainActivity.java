/*
 * Copyright (c) 2016.
 */

package com.monigarr.cruddemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Main Activity";
    private DatabaseReference mDatabase;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private ListView lvPerson;
    private static MainActivity mainActivity;
    private static ArrayList<Person> arrayListPerson = new ArrayList<>();
    private PersonDetailsAdapter personDetailsAdapter;
    private ArrayList<String> keysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Persons");
        mDatabase.addChildEventListener(childEventListener);
        mDatabase.keepSynced(true);
        mainActivity = this;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        lvPerson = (ListView) findViewById(R.id.PersonList);

        keysArray = new ArrayList<>();

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PersonDetailsActivity.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrUpdatePersonActivity.class);
                intent.putExtra("Position", -1);
                startActivity(intent);
            }
        });

        personDetailsAdapter = new PersonDetailsAdapter(MainActivity.this, arrayListPerson);
        lvPerson.setAdapter(personDetailsAdapter);

        new Wait().execute();
    }

    private class Wait extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            lvPerson.setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException ie) {
                Log.d(TAG,ie.toString());
            }
            return(arrayListPerson.size()==0);
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if(bool)
                updateView();
        }
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    public ArrayList<String> getKeysArray() {
        return keysArray;
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void addPerson(Person model) {
        Person person = new Person();
        person.setFirstname(model.getFirstname());
        person.setLastname(model.getLastname());
        person.setZipcode(model.getZipcode());
        person.setDob(model.getDob());

        //append data to the list with unique ID (auto generated)
        //each time Persons child is added
        //Avoids write conflicts when many clients simultaneously adding data
        String key = mDatabase.child("Persons").push().getKey();
        Map<String, Object> postValues = person.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        //add person object
        mDatabase.updateChildren(childUpdates);
    }

    public void deletePerson(int position) {
        String clickedKey = keysArray.get(position);
        mDatabase.child(clickedKey).removeValue();
    }

    public void updatePersonDetails(final Person model, int position) {
        String clickedKey = keysArray.get(position);
        Person p = new Person();
        p.setFirstname(model.getFirstname());
        p.setLastname(model.getLastname());
        p.setZipcode(model.getZipcode());
        p.setDob(model.getDob());
        mDatabase.child(clickedKey).setValue(p);
    }

    ChildEventListener childEventListener = new ChildEventListener() {

        @Override
        //get list or listen for additions
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            lvPerson.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG, dataSnapshot.getKey() + ":" + dataSnapshot.getValue().toString());
            Person person = dataSnapshot.getValue(Person.class);
            arrayListPerson.add(person);
            keysArray.add(dataSnapshot.getKey());
            updateView();
        }

        @Override
        //listen for changes
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String changedKey = dataSnapshot.getKey();
            int changedIndex = keysArray.indexOf(changedKey);
            Person person = dataSnapshot.getValue(Person.class);
            arrayListPerson.set(changedIndex,person);
            updateView();
        }

        @Override
        //listen for removals
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String deletedKey = dataSnapshot.getKey();
            int removedIndex = keysArray.indexOf(deletedKey);
            keysArray.remove(removedIndex);
            arrayListPerson.remove(removedIndex);
            updateView();
        }

        @Override
        //listen for order changes
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(),"Could not update.",Toast.LENGTH_SHORT).show();
            updateView();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        arrayListPerson.clear();
        mDatabase.removeEventListener(childEventListener);
    }

    public void updateView() {
        personDetailsAdapter.notifyDataSetChanged();
        lvPerson.invalidate();
        progressBar.setVisibility(View.GONE);
        lvPerson.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}