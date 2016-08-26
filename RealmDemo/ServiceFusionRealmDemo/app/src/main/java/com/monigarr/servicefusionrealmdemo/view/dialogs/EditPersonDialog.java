package com.monigarr.servicefusionrealmdemo.view.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.monigarr.servicefusionrealmdemo.R;
import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.tools.DateFormat;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by monigarr on 8/25/16.
 */

public class EditPersonDialog extends DialogFragment implements View.OnClickListener {

    private EditText etFirstName, etLastName, etDob, etZipcode;
    private Date date;
    private Button btAdd;
    private EditPersonDialog.OnEditPersonClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_person, container);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btAdd = (Button) view.findViewById(R.id.btAdd);
        etFirstName = (EditText) view.findViewById(R.id.etFirstname);
        etLastName = (EditText) view.findViewById(R.id.etLastname);
        etDob = (EditText) view.findViewById(R.id.etDob);
        etZipcode = (EditText) view.findViewById(R.id.etZipcode);
        etFirstName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btAdd.setOnClickListener(this);
        etDob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAdd: {
                if (isUserInfoValidate()) {
                    Person person = new Person();
                    btAdd.setText("Edit");
                    person.setName(etFirstName.getText().toString());
                    person.setLastName(etLastName.getText().toString());
                    person.setZipcode(etZipcode.getText().toString());
                    person.setDob(date);
                    listener.onEditPersonClickListener(person);
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
        return !etFirstName.getText().toString().isEmpty() &&
                !etLastName.getText().toString().isEmpty() &&
                !etZipcode.getText().toString().isEmpty() &&
                !etDob.getText().toString().isEmpty();
    }

    public void setListener(EditPersonDialog.OnEditPersonClickListener listener) {
        this.listener = listener;
    }

    public interface OnEditPersonClickListener {
        void onEditPersonClickListener(Person person);
    }
}
