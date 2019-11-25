package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment home = new HomeFragment();
    private DiaryFragment diary = new DiaryFragment();
    private ProfileFragment profile = new ProfileFragment();
    private AppCompatActivity activity;
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

        home.setActivity(activity);
        addFragment(home);
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ancor, fragment);
        fragmentTransaction.commit();
    }
}
