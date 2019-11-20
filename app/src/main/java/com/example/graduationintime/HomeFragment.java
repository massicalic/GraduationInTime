package com.example.graduationintime;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.chaquo.python.*;
import com.chaquo.python.android.AndroidPlatform;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private AppCompatActivity activity;
    private View view;
    private TextView text;
    private Button button_prob;
    private Toolbar toolbar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.Toolbar);
        text = view.findViewById(R.id.TextView_timeGrad);
        button_prob = view.findViewById(R.id.Button_probability);
        activity.setSupportActionBar(toolbar);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(activity.getApplicationContext()));
        }
        Python py = Python.getInstance();
        PyObject test = py.getModule("test");
        String i = String.valueOf(test.callAttr("c_area", 2, 3).toFloat());
        text.setText(i);
        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}
