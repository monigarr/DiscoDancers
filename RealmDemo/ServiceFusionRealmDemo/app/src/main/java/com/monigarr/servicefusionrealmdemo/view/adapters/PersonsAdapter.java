package com.monigarr.servicefusionrealmdemo.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monigarr.servicefusionrealmdemo.R;
import com.monigarr.servicefusionrealmdemo.app.RealmDemoApp;
import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.tools.DateFormat;

import io.realm.RealmList;

/**
 * Created by monigarr on 8/22/16.
 */

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.PersonViewHolder> {

    private RealmList<Person> persons;

    public PersonsAdapter(RealmList<Person> persons) {
        this.persons = persons;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.tvFirstname.setText(persons.get(position).getName());
        holder.tvLastname.setText(persons.get(position).getLastName());
        holder.tvZipcode.setText(persons.get(position).getZipcode());
        String dob = RealmDemoApp.getInstance().getString(R.string.dob) + " " + DateFormat.convertDateToString(persons.get(position).getDob());
        holder.tvDob.setText(dob);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFirstname;
        private TextView tvLastname;
        private TextView tvZipcode;
        private TextView tvDob;

        public PersonViewHolder(View itemView) {
            super(itemView);

            tvFirstname = (TextView) itemView.findViewById(R.id.tvFirstname);
            tvLastname = (TextView) itemView.findViewById(R.id.tvLastname);
            tvZipcode = (TextView) itemView.findViewById(R.id.tvZipcode);
            tvDob = (TextView) itemView.findViewById(R.id.tvDob);
        }
    }
}