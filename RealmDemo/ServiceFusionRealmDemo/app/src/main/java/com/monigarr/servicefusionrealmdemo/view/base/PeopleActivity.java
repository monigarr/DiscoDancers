package com.monigarr.servicefusionrealmdemo.view.base;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.monigarr.servicefusionrealmdemo.R;
import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.presenters.IPersonPresenter;
import com.monigarr.servicefusionrealmdemo.presenters.impl.PersonPresenter;
import com.monigarr.servicefusionrealmdemo.realm.table.RealmTable;
import com.monigarr.servicefusionrealmdemo.view.adapters.PeopleAdapter;
import com.monigarr.servicefusionrealmdemo.view.base.BaseActivity;
import com.monigarr.servicefusionrealmdemo.view.dialogs.AddPersonDialog;

import io.realm.RealmList;

import static android.R.attr.id;

/**
 * Created by monigarr on 8/22/16.
 */

public class PeopleActivity extends BaseActivity implements View.OnClickListener {

    private IPersonPresenter presenter;
    private FloatingActionButton fbAdd;
    private RecyclerView rvPeople;
    private PeopleAdapter adapter;
    private RealmList<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        presenter = new PersonPresenter(this);
        id = getIntent().getStringExtra(RealmTable.ID);

        initComponents();
    }
    @Override
    protected void initComponents() {
        fbAdd = (FloatingActionButton) findViewById(R.id.fab_add_person);
        fbAdd.setOnClickListener(this);
        initRecyclerListener();
    }

    public void updateToolbarTittle(String title) {
        getSupportActionBar().setTitle(getString(R.string.people) + " - " + title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getAllPeopleById(personId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_addPerson: {
                showAddStudentDialog();
                break;
            }
        }
    }

    private void initRecyclerListener() {
        rvPeople = (RecyclerView) findViewById(R.id.rv_people);
        rvPeople.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvPeople.setItemAnimator(new DefaultItemAnimator());

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
        swipeToDismissTouchHelper.attachToRecyclerView(rvPeople);
    }

    private void showAddPersonDialog() {
        final AddPersonDialog dialog = new AddPersonDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getFirstName());
        dialog.setListener(new AddPersonDialog.OnAddStudentClickListener() {
            @Override
            public void onAddPersonClickListener(Person person) {
                dialog.dismiss();
                presenter.addPersonById(person, id);
                presenter.getAllPeopleById(id);
            }
        });
    }

    public void showPeople(RealmList<Person> people) {
        this.people = people;
        adapter = new PeopleAdapter(people);
        rvPeople.setAdapter(adapter);
    }
}
