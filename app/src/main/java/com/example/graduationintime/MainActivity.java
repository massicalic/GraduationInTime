package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private HomeFragment home = new HomeFragment();
    private DiaryFragment diary = new DiaryFragment();
    private ProfileFragment profile = new ProfileFragment();
    private AppCompatActivity activity;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private User user;
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
                        home.setUser(user);
                        addFragment(home);
                    }
                    return true;
                case R.id.navigation_diary:selectedItemId = bottomNavigationView.getSelectedItemId();
                    if (selectedItemId != R.id.navigation_diary) {
                        diary.setActivity(activity);
                        addFragment(diary);
                    }
                    return true;

                case R.id.navigation_profile:
                    selectedItemId = bottomNavigationView.getSelectedItemId();
                    if (selectedItemId != R.id.navigation_profile) {
                        profile.setActivity(activity);
                        profile.setUser(user);
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

        activity = this;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        final String mUserId = firebaseUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    String key = obj.getKey();
                    if(key.equals(mUserId)){
                        user = dataSnapshot.child(key).getValue(User.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Lettura fallita dal database users", databaseError.toException());
            }
        });

        home.setActivity(activity);
        home.setUser(user);
        addFragment(home);
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ancor, fragment);
        fragmentTransaction.commit();
    }
}
