package com.example.graduationintime;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = ".HomeFragment";
    private AppCompatActivity activity;
    private View view;
    private TextView textTime, textNext, addDate;
    private Button button_prob;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private View v;
    private ListView listView;
    private List<String> listName;
    private List<String> listDate;
    private CustomAdapterNextExam adapter;
    private User user;
    private boolean repeat;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private int posList;

    private static final String examNameKEY = "examName_key";
    private static final String hourKEY = "hour_key";
    private static final String cfuKEY = "cfu_key";
    private static final String minutesKEY = "minutes_key";
    private static final String placeKEY = "place_key";
    private static final String dayKEY = "day_key";
    private static final String monthKEY = "month_key";
    private static final String yearKEY = "year_key";
    private static final String profKEY = "prof_key";
    private static final String infoKEY = "info_key";
    private static final String notificationKEY = "notification_key";
    private static final String posListKEY = "posList_key";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.Toolbar);
        textTime = view.findViewById(R.id.TextView_timeGrad);
        textNext = view.findViewById(R.id.TextView);
        button_prob = view.findViewById(R.id.Button_probability);
        listView = view.findViewById(R.id.ListView_exam);
        progressBar = view.findViewById(R.id.progress_bar);
        v = view.findViewById(R.id.view2);
        addDate = view.findViewById(R.id.TextView_add_data);

        textTime.setVisibility(View.GONE);
        button_prob.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        textNext.setVisibility(View.GONE);
        v.setVisibility(View.GONE);
        addDate.setVisibility(View.GONE);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final String mUserId = mFirebaseUser.getUid();
        repeat = true;

        if (user==null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        String key = obj.getKey();
                        if(key.equals(mUserId)){
                            user = dataSnapshot.child(key).getValue(User.class);

                            textTime.setVisibility(View.VISIBLE);
                            textNext.setVisibility(View.VISIBLE);
                            button_prob.setVisibility(View.VISIBLE);
                            v.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            listName = new ArrayList<>();
                            listDate = new ArrayList<>();
                            for (int i=0; i<user.getExams().size(); i++) {
                                if (user.getExams().get(i).getDay()!=0&&user.getExams().get(i).getMark()==null) {
                                    listName.add(user.getExams().get(i).getName());
                                    listDate.add((user.getExams().get(i).getDay()<10 ? "0"+user.getExams().get(i).getDay()+"/" : user.getExams().get(i).getDay()+"/") +
                                            ((user.getExams().get(i).getMonth()+1)<10?"0"+(user.getExams().get(i).getMonth()+1)+"/" : (user.getExams().get(i).getMonth()+1)+"/")
                                            + (user.getExams().get(i).getYear()));
                                }
                            }
                            adapter = new CustomAdapterNextExam(activity, listName, listDate);
                            listView.setAdapter(adapter);
                            repeat = false;
                            if (listName.size()==0) {
                                addDate.setVisibility(View.VISIBLE);
                            }else {
                                listView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Lettura fallita dal database users", databaseError.toException());
                }
            });
        }
        button_prob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (repeat&&user!=null) {
            textTime.setVisibility(View.VISIBLE);
            textNext.setVisibility(View.VISIBLE);
            v.setVisibility(View.VISIBLE);
            button_prob.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            listName = new ArrayList<>();
            listDate = new ArrayList<>();
            for (int i=0; i<user.getExams().size(); i++) {
                if (user.getExams().get(i).getDay()!=0&&user.getExams().get(i).getMark()==null) {
                    listName.add(user.getExams().get(i).getName());
                    listDate.add((user.getExams().get(i).getDay()<10 ? "0"+user.getExams().get(i).getDay()+"/" : user.getExams().get(i).getDay()+"/") +
                            ((user.getExams().get(i).getMonth()+1)<10?"0"+(user.getExams().get(i).getMonth()+1)+"/" : (user.getExams().get(i).getMonth()+1)+"/")
                            + (user.getExams().get(i).getYear()));
                }
            }
            adapter = new CustomAdapterNextExam(activity, listName, listDate);
            listView.setAdapter(adapter);
            if (listName.size()==0) {
                addDate.setVisibility(View.VISIBLE);
            }else {
                listView.setVisibility(View.VISIBLE);
            }
        }

        activity.setSupportActionBar(toolbar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameTemp = listName.get(position);
                Exam exam = new Exam();
                for (int i=0; i<user.getExams().size(); i++) {
                    if (user.getExams().get(i).getName().equals(nameTemp)) {
                        exam = user.getExams().get(i);
                        posList = i;
                    }
                }
                Intent intent = new Intent(activity, ExamActivity.class);
                intent.putExtra(examNameKEY, exam.getName());
                intent.putExtra(cfuKEY, exam.getCfu());
                intent.putExtra(dayKEY, exam.getDay());
                intent.putExtra(monthKEY, exam.getMonth());
                intent.putExtra(yearKEY, exam.getYear());
                intent.putExtra(hourKEY, exam.getHour());
                intent.putExtra(minutesKEY, exam.getMinutes());
                intent.putExtra(placeKEY, exam.getPlace());
                intent.putExtra(profKEY, exam.getProf());
                intent.putExtra(infoKEY, exam.getInfo());
                intent.putExtra(notificationKEY, exam.isNotification());
                intent.putExtra(posListKEY, posList);
                startActivity(intent);
            }
        });

        /*if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(activity.getApplicationContext()));
        }
        Python py = Python.getInstance();
        PyObject test = py.getModule("recommender");
        List<PyObject> list = test.callAttr("recommendation").asList();
        String s = "";
        for (int i=0; i<list.size(); i++) {
            s += list.get(i).toString() + "          ";
        }*/
        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
