package com.example.graduationintime;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.List;

public class RunnableRecommendation implements Runnable {

    private User user;
    private AppCompatActivity activity;
    private ArrayList<String> raco;
    private ArrayList<String> weight;

    public RunnableRecommendation( AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(activity.getApplicationContext()));
        }

    }

    public ArrayList<String> getRaco() {
        return raco;
    }

    public ArrayList<String> getWeight() {
        return weight;
    }
}
