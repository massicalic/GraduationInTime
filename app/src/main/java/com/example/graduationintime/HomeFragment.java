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
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = ".HomeFragment";
    private AppCompatActivity activity;
    private View view;
    private TextView textTime, textNext, addDate;
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
    private ArrayList<Exam> nameExams;
    private int year = 0;

    private static final String examKEY = "exam_key";
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
        listView = view.findViewById(R.id.ListView_exam);
        progressBar = view.findViewById(R.id.progress_bar);
        v = view.findViewById(R.id.view2);
        addDate = view.findViewById(R.id.TextView_add_data);

        textTime.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        textNext.setVisibility(View.GONE);
        v.setVisibility(View.GONE);
        addDate.setVisibility(View.GONE);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final String mUserId = mFirebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        repeat = true;

        if (user==null) {
            mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        String key = obj.getKey();
                        if(key.equals(mUserId)){
                            user = dataSnapshot.child(key).getValue(User.class);

                            int[] sign = new int[nameExams.size()];
                            for (int i = 0; i < nameExams.size(); i++) {
                                for (int j = 0; j<user.getExams().size(); j++) {
                                    if (nameExams.get(i).getName().toLowerCase().equals(user.getExams().get(j).getName().toLowerCase())) {
                                        if (user.getExams().get(j).getMark() != null) {
                                            if (user.getExams().get(j).getMark().equals("30L")) {
                                                sign[i] = 30;
                                            }if (user.getExams().get(j).getMark().equals("superato")) {
                                                sign[i] = 30;
                                            }else {
                                                sign[i] = Integer.parseInt(user.getExams().get(j).getMark());
                                            }
                                        } else {
                                            sign[i] = 0;
                                        }
                                        break;
                                    } else {
                                        if (i + 1 == nameExams.size()) {
                                            sign[i] = 0;
                                        }
                                    }
                                }
                            }

                            Python py = Python.getInstance();
                            PyObject test = py.getModule("recommender");
                            List<PyObject> list = test.callAttr("recommendation", sign, user.getMatriculation(), user.getYearEnroll()).asList();

                            int cfu = 0;
                            for (int i=0; i<user.getExams().size(); i++) {
                                if (user.getExams().get(i).isFundamental()) {
                                    cfu = cfu + user.getExams().get(i).getCfu();
                                }
                            }
                            if (cfu>=126) {
                                for (int i=0; i<list.size(); i++) {
                                    String[] tokens = list.get(i).toString().split("::");
                                    int yearTemp = Integer.parseInt(list.get(i).toString().substring(list.get(i).toString().length()-4));
                                    String monthStr = tokens[1].substring(0, tokens[1].length() - 6);
                                    int month = 0;
                                    switch (monthStr) {
                                        case "Gennaio":
                                            month = 0;
                                            break;
                                        case "Febbraio":
                                            month = 1;
                                            break;
                                        case "Giugno":
                                            month = 5;
                                            break;
                                        case "Luglio":
                                            month = 6;
                                            break;
                                        case "Settembre":
                                            month = 8;
                                            break;
                                        case "Dicembre":
                                            month = 11;
                                            break;
                                    }
                                    Calendar c = Calendar.getInstance();
                                    int mYear = c.get(Calendar.YEAR);
                                    int mMonth = c.get(Calendar.MONTH);

                                    while (mYear>yearTemp) {
                                        yearTemp++;
                                        if (month<mMonth) {
                                            yearTemp++;
                                        }
                                    }

                                    if (yearTemp>year) {
                                        year = yearTemp;
                                    }
                                }

                                String w = activity.getResources().getString(R.string.can_graduate_in) + " " + year;
                                textTime.setText(w);
                            }

                            textTime.setVisibility(View.VISIBLE);
                            textNext.setVisibility(View.VISIBLE);
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

        if (repeat&&user!=null) {
            int[] sign = new int[nameExams.size()];
            for (int i = 0; i < nameExams.size(); i++) {
                for (int j = 0; j<user.getExams().size(); j++) {
                    if (nameExams.get(i).getName().toLowerCase().equals(user.getExams().get(j).getName().toLowerCase())) {
                        if (user.getExams().get(j).getMark() != null) {
                            if (user.getExams().get(j).getMark().equals("30L")) {
                                sign[i] = 30;
                            }if (user.getExams().get(j).getMark().equals("superato")) {
                                sign[i] = 30;
                            }else {
                                sign[i] = Integer.parseInt(user.getExams().get(j).getMark());
                            }
                        } else {
                            sign[i] = 0;
                        }
                        break;
                    } else {
                        if (i + 1 == nameExams.size()) {
                            sign[i] = 0;
                        }
                    }
                }
            }

            Python py = Python.getInstance();
            PyObject test = py.getModule("recommender");
            List<PyObject> list = test.callAttr("recommendation", sign, user.getMatriculation(), user.getYearEnroll()).asList();

            int cfu = 0;
            for (int i=0; i<user.getExams().size(); i++) {
                if (user.getExams().get(i).isFundamental()) {
                    cfu = cfu + user.getExams().get(i).getCfu();
                }
            }
            if (cfu>=126) {
                for (int i=0; i<list.size(); i++) {
                    String[] tokens = list.get(i).toString().split("::");
                    int yearTemp = Integer.parseInt(list.get(i).toString().substring(list.get(i).toString().length()-4));
                    String monthStr = tokens[1].substring(0, tokens[1].length() - 6);
                    int month = 0;
                    switch (monthStr) {
                        case "Gennaio":
                            month = 0;
                            break;
                        case "Febbraio":
                            month = 1;
                            break;
                        case "Giugno":
                            month = 5;
                            break;
                        case "Luglio":
                            month = 6;
                            break;
                        case "Settembre":
                            month = 8;
                            break;
                        case "Dicembre":
                            month = 11;
                            break;
                    }
                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);

                    while (mYear>yearTemp) {
                        yearTemp++;
                        if (month<mMonth) {
                            yearTemp++;
                        }
                    }

                    if (yearTemp>year) {
                        year = yearTemp;
                    }
                }

                String w = activity.getResources().getString(R.string.can_graduate_in) + " " + year;
                textTime.setText(w);
            }

            textTime.setVisibility(View.VISIBLE);
            textNext.setVisibility(View.VISIBLE);
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
                intent.putExtra(examKEY, exam);
                intent.putExtra(posListKEY, posList);
                startActivity(intent);
            }
        });

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

    public void setNameExams(ArrayList<Exam> nameExams) {
        this.nameExams = nameExams;
    }
}
