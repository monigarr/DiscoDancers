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
import com.monigarr.servicefusionrealmdemo.model.Disco;
import com.monigarr.servicefusionrealmdemo.presenters.IDiscoPresenter;
import com.monigarr.servicefusionrealmdemo.presenters.impl.DiscoPresenter;
import com.monigarr.servicefusionrealmdemo.realm.table.RealmTable;
import com.monigarr.servicefusionrealmdemo.view.adapters.DiscoAdapter;
import com.monigarr.servicefusionrealmdemo.view.dialogs.AddDiscoDialog;

import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class DiscoActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton fbAdd;
    private RecyclerView rvDiscos;
    private DiscoAdapter adapter;

    private IDiscoPresenter presenter;
    private RealmResults<Disco> discos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discos);

        presenter = new DiscoPresenter(this);

        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getAllDiscos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @Override
    protected void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.discos);
        setSupportActionBar(toolbar);
        fbAdd = (FloatingActionButton) findViewById(R.id.fab_add_disco);
        fbAdd.setOnClickListener(this);
        initRecyclerListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_disco: {
                showAddUniversityDialog();
                break;
            }
        }
    }

    private void initRecyclerListener() {
        rvDiscos = (RecyclerView) findViewById(R.id.rv_discos);
        rvDiscos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvDiscos.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.deleteDiscoById(discos.get(viewHolder.getAdapterPosition()).getId());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(rvDiscos);
    }

    public void showDiscos(RealmResults<Disco> discos) {
        this.discos = discos;
        adapter = new DiscoAdapter(discos);
        adapter.setOnItemClickListener(new DiscoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                Intent intent = new Intent(getApplicationContext(), PeopleActivity.class);
                intent.putExtra(RealmTable.ID, id);
                startActivity(intent);
            }
        });
        rvDiscos.setAdapter(adapter);
    }

    private void showAddUniversityDialog() {
        final AddDiscoDialog dialog = new AddDiscoDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new AddDiscoDialog.OnAddDiscoClickListener() {
            @Override
            public void onAddDiscoClickListener(String discoName) {
                dialog.dismiss();
                presenter.addDisco(discoName);
            }
        });
    }

}