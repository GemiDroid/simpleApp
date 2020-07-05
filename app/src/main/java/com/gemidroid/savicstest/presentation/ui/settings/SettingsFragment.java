package com.gemidroid.savicstest.presentation.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.gemidroid.savicstest.R;
import com.gemidroid.savicstest.presentation.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment {

    private static final String KEY_MAX = "max_patients";
    public static final String KEY_CURRENT = "current";
    EditText currentUser, currentAdded, maxPatient;
    Button save;
    StringBuilder config;

    SharedPreferences sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        currentUser=root.findViewById(R.id.current_patient);
        currentAdded=root.findViewById(R.id.current_added_patients);
        maxPatient=root.findViewById(R.id.max_patients);

        if(sharedPref.contains(HomeFragment.KEY_USERNAME)){
            currentUser.setText(sharedPref.getString(HomeFragment.KEY_USERNAME,""));
        }
        if(sharedPref.contains(KEY_CURRENT)){
            currentAdded.setText(sharedPref.getString(KEY_CURRENT,""));
        }
        if(sharedPref.contains(KEY_MAX)){
            maxPatient.setText(sharedPref.getString(KEY_MAX,""));
        }

        save=root.findViewById(R.id.save_patient);

        save.setOnClickListener(view->{

            // configuration..
            config=new StringBuilder();
            config.append("Current User: "+currentUser.getText().toString());
            config.append("\t Current Added: "+currentAdded.getText().toString());
            config.append("\t Max Patients: "+maxPatient.getText().toString());

            Snackbar.make(root, config.toString(), Snackbar.LENGTH_SHORT).show();

            sharedPref.edit().putString(KEY_MAX,maxPatient.getText().toString()).apply();

        });

        return root;
    }
}