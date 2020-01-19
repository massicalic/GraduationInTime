package com.example.graduationintime;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeStudyFragment extends Fragment {

    private View view;
    private AppCompatActivity activity;
    private RadioGroup radio;
    private int time = 0; //0 null   1 stud   2 studWork   3 workStud

    public TimeStudyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_study, container, false);

        radio = view.findViewById(R.id.RadioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonStud:
                        time = 1;
                        break;
                    case  R.id.radioButtonStudWork:
                        time = 2;
                        break;
                    case  R.id.radioButtonWorkStud:
                        time = 3;
                        break;
                }
            }
        });
        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public int getTime() {
        return time;
    }
}
