package com.example.graduationintime;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private AppCompatActivity activity;
    private TextView name, tipe, year;
    private View view;
    private Toolbar toolbar;
    private User user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.Toolbar);
        activity.setSupportActionBar(toolbar);

        name = view.findViewById(R.id.name);
        tipe = view.findViewById(R.id.tipe);
        year = view.findViewById(R.id.year);

        name.setText(user.getName());
        switch (user.getStudyTime()) {
            case 1:
                tipe.setText(R.string.student);
                break;
            case 2:
                tipe.setText(R.string.studwork);
                break;
            case 3:
                tipe.setText(R.string.workstud);
                break;
        }
        year.setText(""+user.getYearEnroll());

        setHasOptionsMenu(true); //for menu on toolbar
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.settings) {
            Intent intent = new Intent(activity, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
