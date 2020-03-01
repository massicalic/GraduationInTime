package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        new Thread(new RunnableRecommendation(this)).start();

        if (firebaseUser!=null) {
            final String stringUserId = firebaseUser.getUid();
            mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot obj : dataSnapshot.getChildren()) {
                        String key = obj.getKey();
                        if (key.equals(stringUserId)) {
                            user = dataSnapshot.child(key).getValue(User.class);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (user != null) {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Lettura fallita dal database users", databaseError.toException());
                }
            });
        }else {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(SplashActivity.this, PreLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}
