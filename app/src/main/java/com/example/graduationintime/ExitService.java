package com.example.graduationintime;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class ExitService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
        //do something you want before app closes.
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        //stop service
        this.stopSelf();
    }
}
