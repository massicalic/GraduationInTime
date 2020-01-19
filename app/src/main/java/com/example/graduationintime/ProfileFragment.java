package com.example.graduationintime;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ".ProfileFragment";
    private AppCompatActivity activity;
    private TextView name, tipe, year;
    private ProgressBar progressBar;
    private AppBarLayout appBarLayout;
    private CircleImageView image;
    private View view;
    private Toolbar toolbar;
    private User user;
    private boolean isFace = false;
    private RecyclerView recyclerView;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private boolean repeat;

    private static final String providerKEY = "provider_key";
    private static final String nameKEY = "name_key";
    private static final String surnameKEY = "surname_key";
    private static final String emailKEY = "email_key";
    private static final String dayKEY = "day_key";
    private static final String monthKEY = "month_key";
    private static final String yearKEY = "year_key";
    private static final String enrollKEY = "enroll_key";
    private static final String movedKEY = "moved_key";
    private static final String studyKEY = "study_key";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.Toolbar);
        activity.setSupportActionBar(toolbar);

        name = view.findViewById(R.id.name);
        tipe = view.findViewById(R.id.tipe);
        year = view.findViewById(R.id.year);
        image = view.findViewById(R.id.profile_image);
        recyclerView = view.findViewById(R.id.RecyclerView);
        progressBar = view.findViewById(R.id.progress_bar);
        appBarLayout = view.findViewById(R.id.appBar);

        repeat = true;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        // Use above layout manager for RecyclerView..
        recyclerView.setLayoutManager(linearLayoutManager);

        appBarLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        if (user==null) {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            final String mUserId = mFirebaseUser.getUid();

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        String key = obj.getKey();
                        if(key.equals(mUserId)){
                            user = dataSnapshot.child(key).getValue(User.class);

                            appBarLayout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            // Create recycler view raw adapter with item list.
                            RecyclerViewAdapter dataAdapter = new RecyclerViewAdapter(user, activity);
                            // Set RecyclerView raw adapter.
                            recyclerView.setAdapter(dataAdapter);

                            name.setText(user.getName());
                            switch (user.getStudyTime()) {
                                case 1:
                                    tipe.setText(R.string.student);
                                    break;
                                case 2:
                                    tipe.setText(R.string.studwork);
                                    break;
                                case 3:
                                    tipe.setText(R.string.workstud);
                                    break;
                            }
                            String s = ""+user.getYearEnroll();
                            year.setText(s);
                            if (user.getImageUrl()!=null) {
                                //Picasso.get().load(user.getImageUrl()).fit().centerCrop().into(image);
                                Glide.with(activity).load(user.getImageUrl()).fitCenter().centerCrop().circleCrop().into(image);
                            }
                            repeat = false;
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
            appBarLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            // Create recycler view raw adapter with item list.
            RecyclerViewAdapter dataAdapter = new RecyclerViewAdapter(user, activity);
            // Set RecyclerView raw adapter.
            recyclerView.setAdapter(dataAdapter);

            name.setText(user.getName());
            switch (user.getStudyTime()) {
                case 1:
                    tipe.setText(R.string.student);
                    break;
                case 2:
                    tipe.setText(R.string.studwork);
                    break;
                case 3:
                    tipe.setText(R.string.workstud);
                    break;
            }
            String s = ""+user.getYearEnroll();
            year.setText(s);
            if (user.getImageUrl()!=null) {
                //Picasso.get().load(user.getImageUrl()).fit().centerCrop().into(image);
                Glide.with(activity).load(user.getImageUrl()).fitCenter().centerCrop().circleCrop().into(image);
            }

            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (getActivity().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
            {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            }
            recyclerView.setPadding(0,0,0, actionBarHeight);
        }

        setHasOptionsMenu(true); //for menu on toolbar
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.settings) {
            FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            for (UserInfo userInfo : mFirebaseUser.getProviderData()) {
                if (userInfo.getProviderId().equals("facebook.com")) {
                    isFace = true;
                }
            }
            Intent intent = new Intent(activity, SettingsActivity.class);
            intent.putExtra(providerKEY, isFace);
            intent.putExtra(nameKEY, user.getName());
            intent.putExtra(surnameKEY, user.getSurname());
            intent.putExtra(emailKEY, user.getEmail());
            intent.putExtra(dayKEY, user.getDay());
            intent.putExtra(monthKEY, user.getMonth());
            intent.putExtra(yearKEY, user.getYear());
            intent.putExtra(enrollKEY, user.getYearEnroll());
            intent.putExtra(movedKEY, user.isMoved());
            intent.putExtra(studyKEY, user.getStudyTime());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
