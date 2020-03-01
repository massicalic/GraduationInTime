package com.example.graduationintime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExamActivity extends AppCompatActivity {

    private static final String TAG = ExamActivity.class.getName();
    private Toolbar toolbar;
    private MenuItem notification;
    private TextView title, date, time, place, place2, prof, prof2, info, info2;
    private Exam exam = new Exam();
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private DatabaseReference mDatabaseRef;
    private String userid;
    private String s;
    private boolean openFromNoti;

    private static final String examKEY = "exam_key";
    private static final String posListKEY = "posList_key";
    private static final String openFromNotiKEY = "openFromNoti_key";

    public static final String NOTIFICATION_CHANNEL_ID = "11001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        s = String.valueOf(getIntent().getIntExtra(posListKEY, 0));
        openFromNoti = getIntent().getBooleanExtra(openFromNotiKEY, false);

        exam = (Exam) getIntent().getSerializableExtra(examKEY);

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
        String ss = (exam.getDay()<10 ? "0"+exam.getDay()+"/" : exam.getDay()+"/") +
                ((exam.getMonth()+1)<10?"0"+(exam.getMonth()+1)+"/" : (exam.getMonth()+1)+"/") + (exam.getYear());
        date.setText(ss);
        if (exam.getHour()!=0) {
            ss = (exam.getHour()<10 ? "0"+exam.getHour()+":" : exam.getHour()+":") +
                    (exam.getMinutes()<10 ? "0"+exam.getMinutes() : exam.getMinutes());
            time.setText(ss);
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
                if (openFromNoti) {
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.notfication:
                Calendar c = Calendar.getInstance();
                Calendar dateCalendar = new GregorianCalendar(exam.getYear(), exam.getMonth(), exam.getDay(), exam.getHour(), exam.getMinutes());
                long diffInMilli = dateCalendar.getTimeInMillis() - c.getTimeInMillis();

                if (exam.isNotification()) {
                    exam.setNotification(false);
                    mDatabaseRef.child("users").child(userid).child("exams").child(s).child("notification").setValue(false);
                    notification.setIcon(R.drawable.ic_notification_none);
                    if (diffInMilli>0) {
                        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
                        int notificationid = getIntent().getIntExtra(posListKEY, 0);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationid, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                        notificationid += 50;
                        pendingIntent = PendingIntent.getBroadcast(this, notificationid, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pendingIntent);
                    }
                }else {
                    exam.setNotification(true);
                    mDatabaseRef.child("users").child(userid).child("exams").child(s).child("notification").setValue(true);
                    notification.setIcon(R.drawable.ic_notification);
                    String tmp = exam.getName()+ "   " + (exam.getDay()<10 ? "0"+exam.getDay()+"/" : exam.getDay()+"/") +
                            ((exam.getMonth()+1)<10?"0"+(exam.getMonth()+1)+"/" : (exam.getMonth()+1)+"/") + (exam.getYear());
                    if (diffInMilli>0) {
                        int pos = getIntent().getIntExtra(posListKEY, 0);
                        long milli = diffInMilli - 518400000;
                        scheduleNotification(getNotification(tmp, this.getResources().getString(R.string.six_days), pos), milli, pos);
                        milli = diffInMilli - 86400000;
                        scheduleNotification(getNotification(tmp, this.getResources().getString(R.string.tomorrow), pos), milli, pos+50);
                    }
                }
                break;
            case R.id.edit:
                Intent intent = new Intent(ExamActivity.this, ExamEditActivity.class);
                intent.putExtra(examKEY, exam);
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
                        e.setNotification(false);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        if (openFromNoti) {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    exam = (Exam) data.getSerializableExtra(examKEY);

                    String s = (exam.getDay()<10 ? "0"+exam.getDay()+"/" : exam.getDay()+"/") +
                            ((exam.getMonth()+1)<10?"0"+(exam.getMonth()+1)+"/" : (exam.getMonth()+1)+"/") + (exam.getYear());
                    date.setText(s);
                    if (exam.getHour()!=0) {
                        time.setVisibility(View.VISIBLE);
                        s = (exam.getHour()<10 ? "0"+exam.getHour()+":" : exam.getHour()+":") +
                                (exam.getMinutes()<10 ? "0"+exam.getMinutes() : exam.getMinutes());
                        time.setText(s);
                    }else {
                        time.setVisibility(View.GONE);
                    }
                    if (exam.getPlace()!=null) {
                        place.setVisibility(View.VISIBLE);
                        place2.setText(exam.getPlace());
                    }else {
                        place.setVisibility(View.GONE);
                    }
                    if (exam.getProf()!=null) {
                        prof.setVisibility(View.VISIBLE);
                        prof2.setText(exam.getProf());
                    }else {
                        prof.setVisibility(View.GONE);
                    }
                    if (exam.getInfo()!=null) {
                        info.setVisibility(View.VISIBLE);
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
