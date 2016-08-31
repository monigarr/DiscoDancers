/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.viewholders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.monigarr.MoniGarrChatDemo.R;
import com.monigarr.MoniGarrChatDemo.adapters.FriendDetailsAdapter;
import com.monigarr.MoniGarrChatDemo.models.Friend;
import com.monigarr.MoniGarrChatDemo.services.CauseCrash;
import com.monigarr.MoniGarrChatDemo.services.SignOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monicapeters on 8/29/16.
 */

public class AddFriendActivity extends AppCompatActivity {

    //define
    static final String TAG = "Friend Activity";
    private DatabaseReference mDatabase;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private ListView lvFriend;
    private static AddFriendActivity addFriendActivity;
    private static ArrayList<Friend> arrayListFriend = new ArrayList<>();
    private FriendDetailsAdapter friendDetailsAdapter;
    private ArrayList<String> keysArray;

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(childEventListener);
        addFriendActivity = this;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        lvFriend = (ListView) findViewById(R.id.FriendList);

        keysArray = new ArrayList<>();

        lvFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AddFriendActivity.this, FriendDetailsActivity.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFriendActivity.this, UpdateFriendActivity.class);
                intent.putExtra("Position", -1);
                startActivity(intent);
            }
        });

        friendDetailsAdapter = new FriendDetailsAdapter(AddFriendActivity.this, arrayListFriend);
        lvFriend.setAdapter(friendDetailsAdapter);
        new Wait().execute();
    }

    //Wait
    private class Wait extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            lvFriend.setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException ie) {
                Log.d(TAG, ie.toString());
            }
            return(arrayListFriend.size() == 0);
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if(bool)
                updateView();
        }
    }

    //Activity
    public static AddFriendActivity getInstance() {
        return addFriendActivity;
    }

    public ArrayList<String> getKeysArray() {
        return keysArray;
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    //Add Friend
    public void addFriend(Friend model) {
        Friend friend = new Friend();
        friend.setFirstname(model.getFirstname());
        friend.setLastname(model.getLastname());
        friend.setZipcode(model.getZipcode());
        friend.setDob(model.getDob());

        String key = mDatabase.child("Friends").push().getKey();
        Map<String, Object> postValues = friend.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    //Delete Friend
    public void deleteFriend(int position) {
        String clickedKey = keysArray.get(position);
        mDatabase.child(clickedKey).removeValue();
    }

    //Update Friend
    public void updateFriendDetails(final Friend model, int position) {
        String clickedKey = keysArray.get(position);
        Friend f = new Friend();
        f.setFirstname(model.getFirstname());
        f.setLastname(model.getLastname());
        f.setZipcode(model.getZipcode());
        f.setDob(model.getDob());
        mDatabase.child(clickedKey).setValue(f);
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            lvFriend.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG, dataSnapshot.getKey() + ":" + dataSnapshot.getValue().toString());
            Friend friend = dataSnapshot.getValue(Friend.class);
            arrayListFriend.add(friend);
            keysArray.add(dataSnapshot.getKey());
            updateView();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String changedKey = dataSnapshot.getKey();
            int changedIndex = keysArray.indexOf(changedKey);
            Friend friend = dataSnapshot.getValue(Friend.class);
            arrayListFriend.set(changedIndex,friend);
            updateView();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String deletedKey = dataSnapshot.getKey();
            int removedIndex = keysArray.indexOf(deletedKey);
            keysArray.remove(removedIndex);
            arrayListFriend.remove(removedIndex);
            updateView();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(),"Unable to Update.",Toast.LENGTH_SHORT).show();
            updateView();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arrayListFriend.clear();
        mDatabase.removeEventListener(childEventListener);
    }

    public void updateView() {
        friendDetailsAdapter.notifyDataSetChanged();
        lvFriend.invalidate();
        progressBar.setVisibility(View.GONE);
        lvFriend.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu; adds items to action bar present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crash_menu:
                CauseCrash crash = new CauseCrash();
                crash.crashTestMe();
                return true;
            case R.id.sign_out_menu:
                SignOut gsignout = new SignOut();
                gsignout.signMeOut();
                startActivity(new Intent(this, SigninActivity.class));
                return true;
            case R.id.add_friend:
                startActivity(new Intent(this, AddFriendActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}