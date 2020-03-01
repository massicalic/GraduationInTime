package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private HomeFragment home = new HomeFragment();
    private CurriculumFragment curriculum = new CurriculumFragment();
    private ProfileFragment profile = new ProfileFragment();
    private AppCompatActivity activity;
    private DatabaseReference mDatabase;
    private User user;
    private ArrayList<String> raco;
    private ArrayList<String> weight;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int selectedItemId;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedItemId = bottomNavigationView.getSelectedItemId();
                    if (selectedItemId != R.id.navigation_home) {
                        home.setActivity(activity);
                        addFragment(home);
                    }
                    return true;
                case R.id.navigation_curriculum:selectedItemId = bottomNavigationView.getSelectedItemId();
                    if (selectedItemId != R.id.navigation_curriculum) {
                        curriculum.setActivity(activity);
                        addFragment(curriculum);
                    }
                    return true;

                case R.id.navigation_profile:
                    selectedItemId = bottomNavigationView.getSelectedItemId();
                    if (selectedItemId != R.id.navigation_profile) {
                        profile.setActivity(activity);
                        addFragment(profile);
                    }
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("examsname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Exam> exams = new ArrayList<>();
                for(DataSnapshot obj : dataSnapshot.getChildren()) {
                    String key = obj.getKey();
                    exams.add(dataSnapshot.child(key).getValue(Exam.class));
                }
                profile.setNameExams(exams);
                home.setNameExams(exams);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Lettura fallita dal database users", databaseError.toException());
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        home.setActivity(activity);
        addFragment(home);
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ancor, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
