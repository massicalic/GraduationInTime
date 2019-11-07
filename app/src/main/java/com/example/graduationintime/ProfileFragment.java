package com.example.graduationintime;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private AppCompatActivity activity;
    private View view;
    private Toolbar toolbar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        activity.setSupportActionBar(toolbar);
        /*jdhfjehfefuhfuehehueuh*/
        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}
