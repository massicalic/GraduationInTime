package com.example.graduationintime;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private View view;
    private AppCompatActivity activity;
    private Spinner spinner;
    private RadioGroup radio;
    private Boolean moved;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        spinner = view.findViewById(R.id.Spinner);
        radio = view.findViewById(R.id.RadioGroup);

        int year = new GregorianCalendar().get(GregorianCalendar.YEAR);
        List<Integer> spinnerArray =  new ArrayList<>();
        for(int i = 0; i<30; i++){
            spinnerArray.add(year);
            year--;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonYes:
                        moved = true;
                        break;
                    case  R.id.radioButtonNo:
                        moved = false;
                        break;
                }
            }
        });

        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Boolean isMoved() {
        return moved;
    }

    public Spinner getSpinner() {
        return spinner;
    }
}
