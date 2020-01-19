package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExamActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem notification;
    private TextView title, date, time, place, place2, prof, prof2, info, info2;
    private Exam exam = new Exam();
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private DatabaseReference mDatabaseRef;
    private String userid;
    private String s;

    private static final String examNameKEY = "examName_key";
    private static final String hourKEY = "hour_key";
    private static final String minutesKEY = "minutes_key";
    private static final String dayKEY = "day_key";
    private static final String monthKEY = "month_key";
    private static final String yearKEY = "year_key";
    private static final String placeKEY = "place_key";
    private static final String profKEY = "prof_key";
    private static final String infoKEY = "info_key";
    private static final String notificationKEY = "notification_key";
    private static final String posListKEY = "posList_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        s = String.valueOf(getIntent().getIntExtra(posListKEY, 0));

        exam.setName(getIntent().getStringExtra(examNameKEY));
        exam.setHour(getIntent().getIntExtra(hourKEY, 0));
        exam.setMinutes(getIntent().getIntExtra(minutesKEY, 0));
        exam.setDay(getIntent().getIntExtra(dayKEY, 0));
        exam.setMonth(getIntent().getIntExtra(monthKEY, 0));
        exam.setYear(getIntent().getIntExtra(yearKEY, 0));
        exam.setPlace(getIntent().getStringExtra(placeKEY));
        exam.setProf(getIntent().getStringExtra(profKEY));
        exam.setInfo(getIntent().getStringExtra(infoKEY));
        exam.setNotification(getIntent().getBooleanExtra(notificationKEY, false));

        builder = new AlertDialog.Builder(this);
        toolbar = findViewById(R.id.Toolbar);
        title = findViewById(R.id.TextView_titleExam);
        date = findViewById(R.id.TextView_date);
        time = findViewById(R.id.TextView_time);
        place = findViewById(R.id.TextView_place);
        prof = findViewById(R.id.TextView_prof);
        info = findViewById(R.id.TextView_info);
        place2 = findViewById(R.id.TextView_place2);
        prof2 = findViewById(R.id.TextView_prof2);
        info2 = findViewById(R.id.TextView_info2);

        title.setText(exam.getName());
        String s = (exam.getDay()<10 ? "0"+exam.getDay()+"/" : exam.getDay()+"/") +
                ((exam.getMonth()+1)<10?"0"+(exam.getMonth()+1)+"/" : (exam.getMonth()+1)+"/") + (exam.getYear());
        date.setText(s);
        if (exam.getHour()!=0) {
            s = (exam.getHour()<10 ? "0"+exam.getHour()+":" : exam.getHour()+":") +
                    (exam.getMinutes()<10 ? "0"+exam.getMinutes() : exam.getMinutes());
            time.setText(s);
        }else {
            time.setVisibility(View.GONE);
        }
        if (exam.getPlace()!=null) {
            place2.setText(exam.getPlace());
        }else {
            place.setVisibility(View.GONE);
        }
        if (exam.getProf()!=null) {
            prof2.setText(exam.getProf());
        }else {
            prof.setVisibility(View.GONE);
        }
        if (exam.getInfo()!=null) {
            info2.setText(exam.getInfo());
        }else {
            info.setVisibility(View.GONE);
        }

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exam_menu, menu);
        notification = menu.findItem(R.id.notfication);
        if (exam.isNotification()) {
            notification.setIcon(R.drawable.ic_notification);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.notfication:
                if (exam.isNotification()) {
                    exam.setNotification(false);
                    mDatabaseRef.child("users").child(userid).child("exams").child(s).child("notification").setValue(false);
                    notification.setIcon(R.drawable.ic_notification_none);
                }else {
                    exam.setNotification(true);
                    mDatabaseRef.child("users").child(userid).child("exams").child(s).child("notification").setValue(true);
                    notification.setIcon(R.drawable.ic_notification);
                }
                break;
            case R.id.edit:
                Intent intent = new Intent(ExamActivity.this, ExamEditActivity.class);
                intent.putExtra(examNameKEY, exam.getName());
                intent.putExtra(dayKEY, exam.getDay());
                intent.putExtra(monthKEY, exam.getMonth());
                intent.putExtra(yearKEY, exam.getYear());
                intent.putExtra(hourKEY, exam.getHour());
                intent.putExtra(minutesKEY, exam.getMinutes());
                intent.putExtra(placeKEY, exam.getPlace());
                intent.putExtra(profKEY, exam.getProf());
                intent.putExtra(infoKEY, exam.getInfo());
                intent.putExtra(notificationKEY, exam.isNotification());
                intent.putExtra(posListKEY, getIntent().getIntExtra(posListKEY, 0));
                startActivityForResult(intent, 1);
                break;
            case R.id.delete:
                builder.setMessage(R.string.DeleteExam);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Exam e = new Exam();
                        e.setName(exam.getName());
                        e.setCfu(exam.getCfu());
                        mDatabaseRef.child("users").child(userid).child("exams").child(s).setValue(e);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    exam.setDay(data.getIntExtra(dayKEY, 0));
                    exam.setMonth(data.getIntExtra(monthKEY, 0));
                    exam.setYear(data.getIntExtra(yearKEY, 0));
                    exam.setHour(data.getIntExtra(hourKEY, 0));
                    exam.setMinutes(data.getIntExtra(minutesKEY, 0));
                    exam.setPlace(data.getStringExtra(placeKEY));
                    exam.setProf(data.getStringExtra(profKEY));
                    exam.setInfo(data.getStringExtra(infoKEY));

                    String s = (exam.getDay()<10 ? "0"+exam.getDay()+"/" : exam.getDay()+"/") +
                            ((exam.getMonth()+1)<10?"0"+(exam.getMonth()+1)+"/" : (exam.getMonth()+1)+"/") + (exam.getYear());
                    date.setText(s);
                    if (exam.getHour()!=0) {
                        s = (exam.getHour()<10 ? "0"+exam.getHour()+":" : exam.getHour()+":") +
                                (exam.getMinutes()<10 ? "0"+exam.getMinutes() : exam.getMinutes());
                        time.setText(s);
                    }else {
                        time.setVisibility(View.GONE);
                    }
                    if (exam.getPlace()!=null) {
                        place2.setText(exam.getPlace());
                    }else {
                        place.setVisibility(View.GONE);
                    }
                    if (exam.getProf()!=null) {
                        prof2.setText(exam.getProf());
                    }else {
                        prof.setVisibility(View.GONE);
                    }
                    if (exam.getInfo()!=null) {
                        info2.setText(exam.getInfo());
                    }else {
                        info.setVisibility(View.GONE);
                    }
                }
                break;
            }
        }
    }
}
