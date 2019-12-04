package com.example.graduationintime;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class YouFragment extends Fragment {

    private static final String TAG = ".YouFragment";
    private View view;
    private EditText name, surname, email, psw, birthdate;
    private int mYear, mMonth, mDay;
    private GregorianCalendar date;

    public YouFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_you, container, false);
        name = view.findViewById(R.id.EditText_name);
        surname = view.findViewById(R.id.EditText_surname);
        birthdate= view.findViewById(R.id.EditText_birthdate);
        email = view.findViewById(R.id.EditText_email);
        psw = view.findViewById(R.id.EditText_psw);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                birthdate.setText(s);
                                date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                Log.d(TAG, "monthhhhhhhhhhhhhhhhh "+date.get(Calendar.MONTH));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        return view;
    }

    public EditText getName() {
        return name;
    }

    public EditText getSurname() {
        return surname;
    }

    public TextView getBirthdate() {
        return birthdate;
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getPsw() {
        return psw;
    }

    public GregorianCalendar getDate() {
        return date;
    }
}
