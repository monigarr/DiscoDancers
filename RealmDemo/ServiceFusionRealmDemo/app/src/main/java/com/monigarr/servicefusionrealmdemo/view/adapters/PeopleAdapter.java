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

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {

    private RealmList<Person> people;

    public PeopleAdapter(RealmList<Person> people) {
        this.people = people;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.tvFirstName.setText(people.get(position).getFirstName());
        holder.tvLastName.setText(people.get(position).getLastName());
        holder.tvZipcode.setText(people.get(position).getZipcode());
        String dob = "Dob :" + " " + DateFormatter.convertDateToString(people.get(position).getDob());
        holder.tvDob.setText(dob);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFirstname;
        private TextView tvLastname;
        private TextView tvDob;
        private TextView tvZipcode;

        public PersonViewHolder(View itemView) {
            super(itemView);
            tvFirstname = (TextView) itemView.findViewById(R.id.tv_firstname);
            tvLastname = (TextView) itemView.findViewById(R.id.tv_lastname);
            tvDob = (TextView) itemView.findViewById(R.id.tv_dob);
            tvZipcode = (TextView) itemView.findViewById(R.id.tv_zipcode);

        }
    }
}
