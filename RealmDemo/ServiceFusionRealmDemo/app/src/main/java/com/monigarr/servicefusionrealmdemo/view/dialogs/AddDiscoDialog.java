package com.monigarr.servicefusionrealmdemo.view.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.monigarr.servicefusionrealmdemo.R;

/**
 * Created by monigarr on 8/22/16.
 */

public class AddDiscoDialog extends DialogFragment implements View.OnClickListener {

    private EditText etDiscoName;
    private Button btAddDisco;

    private OnAddDiscoClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_disco, container);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        etDiscoName = (EditText) view.findViewById(R.id.et_disco_name);
        etDiscoName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btAddDisco = (Button) view.findViewById(R.id.bt_add_disco);
        btAddDisco.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_disco: {
                if (isUniversityInfoValid())
                    listener.onAddUniversityClickListener(etDiscoName.getText().toString());
                break;
            }
        }
    }

    private boolean isDiscoInfoValid() {
        return !etDiscoName.getText().toString().isEmpty();
    }

    public void setListener(OnAddDiscoClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddDiscoClickListener {
        void onAddDiscoClickListener(String discoName);
    }
}
