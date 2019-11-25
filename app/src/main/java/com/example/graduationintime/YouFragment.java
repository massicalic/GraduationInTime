package com.example.graduationintime;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class YouFragment extends Fragment {

    private View view;
    private User user;
    private AppCompatActivity activity;

    public YouFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_you, container, false);

        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}
