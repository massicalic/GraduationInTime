package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExamEditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText date, time, place, prof, info;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int selectedDay, selectedMonth, selectedYear, hourSelected, minuteSelected;
    private GregorianCalendar dateCalendar;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Exam exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_edit);

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        exam = new Exam();
        /*inserire i dati anche negli int per la data, quando si crea l'exam vuoto se lo si sta creando o con
        i vari dati se lo si sta modificando

        if (exam.getDate_time()==null){
            exam.setDate_time(dateCalendar);
            if ()
        }else {
            dateCalendar = exam.getDate_time();
            selectedDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
            selectedMonth = dateCalendar.get(Calendar.MONTH);
            selectedYear = dateCalendar.get(Calendar.YEAR);
            hourSelected = dateCalendar.get(Calendar.HOUR_OF_DAY);
            minuteSelected = dateCalendar.get(Calendar.MINUTE);
        }*/

        date = findViewById(R.id.EditText_date);
        time = findViewById(R.id.EditText_time);
        place = findViewById(R.id.EditText_place);
        prof = findViewById(R.id.EditText_prof);
        info = findViewById(R.id.EditText_info);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ExamEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                if ((monthOfYear+1)<10){
                                    s = dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
                                }
                                date.setText(s);
                                selectedMonth = dayOfMonth;
                                selectedYear = year;
                                selectedDay = dayOfMonth;
                                if (dateCalendar!=null){
                                    dateCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth, hourSelected, minuteSelected);
                                }else{
                                    dateCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker = new TimePickerDialog(ExamEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hourSelected = selectedHour;
                        minuteSelected = selectedMinute;
                        String s = selectedHour + ":" + selectedMinute;
                        if (selectedHour<10){
                            s = "0" + selectedHour + ":" + selectedMinute;
                            if (selectedMinute<10){
                                s = "0" + selectedHour + ":" + "0" + selectedMinute;
                            }
                        }
                        if (selectedMinute<10){
                            s = selectedHour + ":" + "0" + selectedMinute;
                            if (selectedHour<10){
                                s = "0" + selectedHour + ":" + "0" + selectedMinute;
                            }
                        }
                        time.setText(s);
                        if (dateCalendar!=null){
                            dateCalendar = new GregorianCalendar(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);
                        }else{
                            dateCalendar = new GregorianCalendar();
                        }
                    }
                }, mHour, mMinute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_exam_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.save:
                if (date.getText().toString().trim().equals("")){
                    builder.setMessage(R.string.noDate);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }else{
                    if (time.getText().toString().trim().equals("")){
                        builder.setMessage(R.string.noTimes);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog = builder.create();
                        dialog.show();
                    }else{
                        exam.setDate_time(dateCalendar);
                        if (!place.getText().toString().trim().equals("")){
                            exam.setPlace(place.getText().toString().trim());
                        }
                        if (!prof.getText().toString().trim().equals("")){
                            exam.setProf(prof.getText().toString().trim());
                        }
                        if (!info.getText().toString().trim().equals("")){
                            exam.setInfo(info.getText().toString().trim());
                        }




                        finish();
                    }
                }
                break;
            case android.R.id.home:
                builder.setMessage(R.string.areYouSure);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        builder.setMessage(R.string.areYouSure);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
