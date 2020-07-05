package com.gemidroid.savicstest.presentation.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gemidroid.savicstest.R;
import com.gemidroid.savicstest.data.entities.Patient;
import com.gemidroid.savicstest.presentation.ui.settings.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {


    EditText fullName, age, email;
    TextView list_patient;
    RadioGroup genderGroup;
    char gender = 'M';
    String name;
    int currentAdded=1;
    Patient mPatient;
    SharedPreferences sharedPref;
    public static final String KEY_USERNAME="username";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

         sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

         if(sharedPref.contains(KEY_USERNAME)){
             name = sharedPref.getString(KEY_USERNAME,"");
             Toast.makeText(getActivity(),
                     String.format(getString(R.string.hello),name), Toast.LENGTH_SHORT ).show();
         }
        fullName = root.findViewById(R.id.patient_name);
        age = root.findViewById(R.id.patient_age);
        email = root.findViewById(R.id.patient_email);
        list_patient=root.findViewById(R.id.list_patient);

        genderGroup = root.findViewById(R.id.gender_rg);

        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rd_male:
                    gender = 'M';
                    break;
                case R.id.rd_female:
                    gender = 'F';
                    break;
                case R.id.rd_other:
                    gender = 'O';
                    break;
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_add_patient);

        fab.setOnClickListener(view -> {

            if (!fieldAreValid()) {
                mPatient = new Patient(fullName.getText().toString(), email.getText().toString(),
                        Integer.parseInt(age.getText().toString()), gender);

                list_patient.append(mPatient.toString()+"\n\n");

                sharedPref.edit().putString(KEY_USERNAME,fullName.getText().toString()).apply();
                sharedPref.edit().putString(SettingsFragment.KEY_CURRENT,""+currentAdded).apply();
                currentAdded++;
            } else {
                Snackbar.make(root, getString(R.string.add_missing), Snackbar.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private boolean fieldAreValid() {
        return TextUtils.isEmpty(fullName.getText().toString()) ||
                TextUtils.isEmpty(age.getText().toString()) ||
                TextUtils.isEmpty(email.getText().toString());
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_reset) {
            resetData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetData() {

    fullName.setText("");
    age.setText("");
    email.setText("");
    list_patient.setText("");
    }
}
