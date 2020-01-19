package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddPassedExamActivity extends AppCompatActivity {

    private static final String TAG = AddPassedExamActivity.class.getName();
    private AutoCompleteTextView exam;
    private Spinner spinner;
    private EditText dateExam;
    private Toolbar toolbar;
    private int mYear, mMonth, mDay;
    private int yearTemp, dayTemp, monthTemp;
    private GregorianCalendar date;
    private String [] arrayExams;
    private String [] arrayMarks;
    private int [] arrayDays;
    private int [] arrayMonths;
    private int [] arrayYears;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private DatabaseReference mDatabaseRef;

    private static final String arrayExamKEY = "arrayExam_key";
    private static final String arrayMarkKEY = "arrayMark_key";
    private static final String arrayDayKEY = "arrayDay_key";
    private static final String arrayMonthKEY = "arrayMonth_key";
    private static final String arrayYearKEY = "arrayYear_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passed_exam);

        toolbar = findViewById(R.id.Toolbar);
        exam = findViewById(R.id.EditText_exam);
        spinner = findViewById(R.id.Spinner);
        dateExam = findViewById(R.id.EditText_date);

        builder = new AlertDialog.Builder(this);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        arrayExams = getIntent().getStringArrayExtra(arrayExamKEY);
        arrayMarks = getIntent().getStringArrayExtra(arrayMarkKEY);
        arrayDays = getIntent().getIntArrayExtra(arrayDayKEY);
        arrayMonths = getIntent().getIntArrayExtra(arrayMonthKEY);
        arrayYears = getIntent().getIntArrayExtra(arrayYearKEY);

        ArrayList<String> marks = new ArrayList<>();
        marks.add("18");
        marks.add("19");
        marks.add("20");
        marks.add("21");
        marks.add("22");
        marks.add("23");
        marks.add("24");
        marks.add("25");
        marks.add("26");
        marks.add("27");
        marks.add("28");
        marks.add("29");
        marks.add("30");
        marks.add("30L");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, marks);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayExams);
        exam.setAdapter(adapter);
        exam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                String mark = "";
                if (item instanceof String){
                    String name = (String) item;
                    for (int i=0; i<arrayExams.length; i++) {
                        if (name.equals(arrayExams[i])){
                            mark = arrayMarks[i];
                            String d = "";
                            dayTemp = 0;
                            monthTemp = 0;
                            yearTemp = 0;
                            if (arrayDays[i]!=0) {
                                d = (arrayDays[i]<10 ? "0"+arrayDays[i]+"/" : arrayDays[i]+"/") +
                                        ((arrayMonths[i]+1)<10?"0"+(arrayMonths[i]+1)+"/" : (arrayMonths[i]+1)+"/") + (arrayYears[i]);
                                dayTemp = arrayDays[i];
                                monthTemp = arrayMonths[i];
                                yearTemp = arrayYears[i];
                            }
                            dateExam.setText(d);
                            break;
                        }
                    }
                    if (!mark.equals("0")) {
                        switch (mark) {
                            case "18":
                                spinner.setSelection(0);
                                break;
                            case "19":
                                spinner.setSelection(1);
                                break;
                            case "20":
                                spinner.setSelection(2);
                                break;
                            case "21":
                                spinner.setSelection(3);
                                break;
                            case "22":
                                spinner.setSelection(4);
                                break;
                            case "23":
                                spinner.setSelection(5);
                                break;
                            case "24":
                                spinner.setSelection(6);
                                break;
                            case "25":
                                spinner.setSelection(7);
                                break;
                            case "26":
                                spinner.setSelection(8);
                                break;
                            case "27":
                                spinner.setSelection(9);
                                break;
                            case "28":
                                spinner.setSelection(10);
                                break;
                            case "29":
                                spinner.setSelection(11);
                                break;
                            case "30":
                                spinner.setSelection(12);
                                break;
                            case "30L":
                                spinner.setSelection(13);
                                break;
                        }
                    }
                }
            }
        });

        dateExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPassedExamActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s = (dayOfMonth<10 ? "0"+dayOfMonth+"/" : dayOfMonth+"/") +
                                        ((monthOfYear+1)<10?"0"+(monthOfYear+1)+"/" : (monthOfYear+1)+"/") + (year);
                                dateExam.setText(s);
                                date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                dayTemp = date.get(Calendar.DAY_OF_MONTH);
                                monthTemp = date.get(Calendar.MONTH);
                                yearTemp = date.get(Calendar.YEAR);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                for (int i=0; i<arrayExams.length; i++) {
                    if (exam.getText().toString().equals(arrayExams[i])) {
                        if (!dateExam.getText().equals("")) {
                            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            mDatabaseRef.child("users").child(userid).child("exams").child(String.valueOf(i)).child("mark").setValue(spinner.getSelectedItem().toString());
                            mDatabaseRef.child("users").child(userid).child("exams").child(String.valueOf(i)).child("day").setValue(dayTemp);
                            mDatabaseRef.child("users").child(userid).child("exams").child(String.valueOf(i)).child("month").setValue(monthTemp);
                            mDatabaseRef.child("users").child(userid).child("exams").child(String.valueOf(i)).child("year").setValue(yearTemp);
                            finish();
                        }else {
                            builder.setMessage(R.string.incorrectDate);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }
                        break;
                    }else {
                        if (i==(arrayExams.length-1)) {
                            builder.setMessage(R.string.incorrectExam);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
