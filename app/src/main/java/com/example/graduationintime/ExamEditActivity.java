package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExamEditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText date, time, place, prof, info;
    private TextView title;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int selectedDay, selectedMonth, selectedYear, hourSelected, minuteSelected;
    private GregorianCalendar dateCalendar;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Exam exam;
    private DatabaseReference mDatabaseRef;

    public static final String NOTIFICATION_CHANNEL_ID = "11001" ;
    private final static String default_notification_channel_id = "default" ;

    private static final String examKEY = "exam_key";
    private static final String posListKEY = "posList_key";
    private static final String openFromNotiKEY = "openFromNoti_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_edit);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.Toolbar);
        date = findViewById(R.id.EditText_date);
        time = findViewById(R.id.EditText_time);
        place = findViewById(R.id.EditText_place);
        prof = findViewById(R.id.EditText_prof);
        info = findViewById(R.id.EditText_info);
        title = findViewById(R.id.TextView_titleExam);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        if (getIntent().getSerializableExtra(examKEY)!=null) {
            exam = (Exam) getIntent().getSerializableExtra(examKEY);

            selectedDay = exam.getDay();
            selectedMonth = exam.getMonth();
            selectedYear = exam.getYear();
            hourSelected = exam.getHour();
            minuteSelected = exam.getMinutes();
            dateCalendar = new GregorianCalendar(selectedYear, selectedMonth, selectedDay, hourSelected, minuteSelected);
        }

        title.setText(exam.getName());

        if (exam!=null) {
            String s = (selectedDay<10 ? "0"+selectedDay+"/" : selectedDay+"/") +
                    ((selectedMonth+1)<10?"0"+(selectedMonth+1)+"/" : (selectedMonth+1)+"/") + (selectedYear);
            if (!s.equals("00/01/0")) {
                date.setText(s);
            }
            s = (hourSelected<10 ? "0"+hourSelected+":" : hourSelected+":") +
                    (minuteSelected<10 ? "0"+minuteSelected : minuteSelected);
            if (s.equals("00:00")){
                s = "";
            }
            time.setText(s);
            place.setText(exam.getPlace());
            prof.setText(exam.getProf());
            info.setText(exam.getInfo());
        } else {
            exam = new Exam();
        }

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
                                String s = (dayOfMonth<10 ? "0"+dayOfMonth+"/" : dayOfMonth+"/") +
                                        ((monthOfYear+1)<10?"0"+(monthOfYear+1)+"/" : (monthOfYear+1)+"/") + (year);
                                date.setText(s);
                                selectedMonth = monthOfYear;
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
        getMenuInflater().inflate(R.menu.save_menu, menu);
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
                    }else {
                        exam.setDay(selectedDay);
                        exam.setMonth(selectedMonth);
                        exam.setYear(selectedYear);
                        exam.setHour(hourSelected);
                        exam.setMinutes(minuteSelected);
                        if (!place.getText().toString().trim().equals("")) {
                            exam.setPlace(place.getText().toString().trim());
                        }
                        if (!prof.getText().toString().trim().equals("")) {
                            exam.setProf(prof.getText().toString().trim());
                        }
                        if (!info.getText().toString().trim().equals("")) {
                            exam.setInfo(info.getText().toString().trim());
                        }

                        if (exam.isNotification()) {
                            Calendar c = Calendar.getInstance();
                            Calendar dateCalendar = new GregorianCalendar(exam.getYear(), exam.getMonth(), exam.getDay(), exam.getHour(), exam.getMinutes());
                            long diffInMilli = dateCalendar.getTimeInMillis() - c.getTimeInMillis();

                            if (diffInMilli > 0) {
                                Intent notificationIntent = new Intent(this, NotificationPublisher.class);
                                int notificationid = getIntent().getIntExtra(posListKEY, 0);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationid, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);
                                notificationid += 50;
                                pendingIntent = PendingIntent.getBroadcast(this, notificationid, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.cancel(pendingIntent);

                                String tmp = exam.getName()+ "   " + (exam.getDay()<10 ? "0"+exam.getDay()+"/" : exam.getDay()+"/") +
                                        ((exam.getMonth()+1)<10?"0"+(exam.getMonth()+1)+"/" : (exam.getMonth()+1)+"/") + (exam.getYear());
                                int pos = getIntent().getIntExtra(posListKEY, 0);
                                long milli = diffInMilli - 518400000;
                                scheduleNotification(getNotification(tmp, this.getResources().getString(R.string.six_days), pos), milli, pos);
                                milli = diffInMilli - 86400000;
                                scheduleNotification(getNotification(tmp, this.getResources().getString(R.string.tomorrow), pos), milli, pos+50);
                            }

                        }
                        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String s = String.valueOf(getIntent().getIntExtra(posListKEY, 0));
                        mDatabaseRef.child("users").child(userid).child("exams").child(s).setValue(exam);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(examKEY, exam);
                        setResult(Activity.RESULT_OK, resultIntent);
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

    private void scheduleNotification(Notification notification, long delay, int notificationid) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationid);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationid, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content, String period, int requestCode) {
        Intent intent = new Intent(this, ExamActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(examKEY, exam);
        intent.putExtra(posListKEY, getIntent().getIntExtra(posListKEY, 0));
        intent.putExtra(openFromNotiKEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle(this.getResources().getString(R.string.next_exame)+" "+period);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_exam);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }
}
