package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PassedExamListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private List<Exam> list;
    private CustomAdapterPassedExam adapter;
    private String [] arrayExams;
    private String [] arrayMarks;
    private int [] arrayDays;
    private int [] arrayMonths;
    private int [] arrayYears;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private DatabaseReference mDatabaseRef;
    private String userid;

    private static final String arrayExamKEY = "arrayExam_key";
    private static final String arrayMarkKEY = "arrayMark_key";
    private static final String arrayDayKEY = "arrayDay_key";
    private static final String arrayMonthKEY = "arrayMonth_key";
    private static final String arrayYearKEY = "arrayYear_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passed_exam_list);

        toolbar = findViewById(R.id.Toolbar);
        listView = findViewById(R.id.ListView);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        builder = new AlertDialog.Builder(this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        arrayExams = getIntent().getStringArrayExtra(arrayExamKEY);
        arrayMarks = getIntent().getStringArrayExtra(arrayMarkKEY);
        arrayDays = getIntent().getIntArrayExtra(arrayDayKEY);
        arrayMonths = getIntent().getIntArrayExtra(arrayMonthKEY);
        arrayYears = getIntent().getIntArrayExtra(arrayYearKEY);

        list = new ArrayList<>();
        final List<String> list2 = new ArrayList<>();
        for (int i=0; i<arrayExams.length; i++) {
            if (!arrayMarks[i].equals("0")) {
                Exam e = new Exam();
                e.setName(arrayExams[i]);
                e.setMark(arrayMarks[i]);
                e.setDay(arrayDays[i]);
                e.setMonth(arrayMonths[i]);
                e.setYear(arrayYears[i]);
                list.add(e);
                list2.add("");
            }
        }
        adapter = new CustomAdapterPassedExam(PassedExamListActivity.this, list, list2);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder.setMessage(R.string.sure_delete);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j = 0;
                        for (int i=0; i<arrayExams.length; i++) {
                            if (list.get(position).getName().equals(arrayExams[i])) {
                                j = i;
                                break;
                            }
                        }
                        mDatabaseRef.child("users").child(userid).child("exams").child(String.valueOf(j)).child("mark").setValue(null);
                        list.remove(position);
                        list2.remove(position);
                        adapter.notifyDataSetChanged();
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
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
