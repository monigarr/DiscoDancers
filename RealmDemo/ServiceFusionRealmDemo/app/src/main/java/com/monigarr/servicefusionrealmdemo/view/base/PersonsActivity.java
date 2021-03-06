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
import com.monigarr.servicefusionrealmdemo.view.adapters.PersonsAdapter;
import com.monigarr.servicefusionrealmdemo.view.dialogs.AddPersonDialog;

import io.realm.RealmList;

/**
 * Created by monigarr on 8/22/16.
 */

public class PersonsActivity extends BaseActivity implements View.OnClickListener {

    private IPersonPresenter presenter;
    private FloatingActionButton fbAdd;
    //todo: add edit feature
    // private CardView fbEdit;
    private RecyclerView rvPeople;
    private PersonsAdapter adapter;
    private RealmList<Person> persons;
    private String discoId;
    //todo: add edit feature
    // private OnAddEditPersonClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        presenter = new PersonPresenter(this);
        discoId = getIntent().getStringExtra(RealmTable.ID);

        initComponents();
    }
    @Override
    protected void initComponents() {
        fbAdd = (FloatingActionButton) findViewById(R.id.fab_add_person);
        //todo: add edit feature
        //fbEdit = (CardView) findViewById(R.id.cv);

        fbAdd.setOnClickListener(this);
        //todo: add edit feature
        //fbEdit.setOnClickListener(this);

        initRecyclerListener();
    }

    public void updateToolbarTitle(String title) {
        getSupportActionBar().setTitle("People" + " - " + title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getDiscoById(discoId);
        presenter.getAllPersonsByDiscoId(discoId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_person: {
                showAddPersonDialog();
                break;
            }
            //todo: add edit feature
            /*case R.id.cv: {
                showEditPersonDialog();
                break;
            }*/
        }
    }

    private void initRecyclerListener() {
        rvPeople = (RecyclerView) findViewById(R.id.rv_persons);
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
                presenter.deletePersonById(persons.get(viewHolder.getAdapterPosition()).getId());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(rvPeople);
    }

    private void showAddPersonDialog() {
        final AddPersonDialog dialog = new AddPersonDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new AddPersonDialog.OnAddPersonClickListener() {
            @Override
            public void onAddPersonClickListener(Person person) {
                dialog.dismiss();
                presenter.addPersonByDiscoId(person, discoId);
                presenter.getAllPersonsByDiscoId(discoId);
            }
        });
    }

    /* todo add edit person feature
    private void showEditPersonDialog() {
        final EditPersonDialog dialog = new EditPersonDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new EditPersonDialog.OnEditPersonClickListener() {
            @Override
            public void onEditPersonClickListener(Person person) {
                if (isUserInfoValidate()) {
                    Person person = new Person();
                    person.setName(etFirstName.getText().toString());
                    person.setLastName(etLastName.getText().toString());
                    person.setZipcode(etZipcode.getText().toString());
                    person.setDob(date);
                    listener.onAddPersonClickListener(person);
                dialog.dismiss();
                presenter.getPersonById(discoId);
            }
        });
    }*/

    public void showPersons(RealmList<Person> persons) {
        this.persons = persons;
        adapter = new PersonsAdapter(persons);
        rvPeople.setAdapter(adapter);
    }

    //todo: add this for edit person feature
    /* public void setListener(OnAddEditPersonClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddEditPersonClickListener {
        void onAddPersonClickListener(Person person);
    }*/
}