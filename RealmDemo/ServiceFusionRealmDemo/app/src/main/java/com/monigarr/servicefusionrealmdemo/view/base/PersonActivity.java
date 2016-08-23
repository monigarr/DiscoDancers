package com.monigarr.servicefusionrealmdemo.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.monigarr.servicefusionrealmdemo.R;
import com.monigarr.servicefusionrealmdemo.presenters.IPersonPresenter;
import com.monigarr.servicefusionrealmdemo.presenters.impl.PersonPresenter;
import com.monigarr.servicefusionrealmdemo.realm.table.RealmTable;
import com.monigarr.servicefusionrealmdemo.view.adapters.PeopleAdapter;
import com.monigarr.servicefusionrealmdemo.view.dialogs.AddPersonDialog;

import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton fbAdd;
    private RecyclerView rvUniversities;
    private PeopleAdapter adapter;

    private IPersonPresenter presenter;

    private RealmResults<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        presenter = new PersonPresenter(this);

        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getAllPeople();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @Override
    protected void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.people);
        setSupportActionBar(toolbar);
        fbAdd = (FloatingActionButton) findViewById(R.id.fab_add_person);
        fbAdd.setOnClickListener(this);
        initRecyclerListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_person: {
                showAddUniversityDialog();
                break;
            }
        }
    }

    private void initRecyclerListener() {
        rvUniversities = (RecyclerView) findViewById(R.id.rv_people);
        rvUniversities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvUniversities.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.deletePersonById(people.get(viewHolder.getAdapterPosition()).getId());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(rvUniversities);
    }

    public void showUniversities(RealmResults<Person> universities) {
        this.people = universities;
        adapter = new PersonAdapter(universities);
        adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                Intent intent = new Intent(getApplicationContext(), PeopleActivity.class);
                intent.putExtra(RealmTable.ID, id);
                startActivity(intent);
            }
        });
        rvUniversities.setAdapter(adapter);
    }

    private void showAddUniversityDialog() {
        final AddPersonDialog dialog = new AddPersonDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new AddPersonDialog.OnAddUniversityClickListener() {
            @Override
            public void onAddUniversityClickListener(String universityName) {
                dialog.dismiss();
                presenter.addPerson(personName);
            }
        });
    }

}
