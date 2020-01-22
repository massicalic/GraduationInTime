package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private TextView text;
    private ArrayAdapter<String> adapter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private DatabaseReference mDatabaseRef;
    private String userid;

    private ArrayList<String> listExam;
    private ArrayList<String> listDates;
    private ArrayList<Integer> listPos;

    private static final String listExamKEY = "listExam_key";
    private static final String listDatesKEY = "listDates_key";
    private static final String listPosKEY = "listPos_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        toolbar = findViewById(R.id.Toolbar);
        listView = findViewById(R.id.ListView);
        text = findViewById(R.id.TextView);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        builder = new AlertDialog.Builder(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        listExam = getIntent().getStringArrayListExtra(listExamKEY);
        listDates = getIntent().getStringArrayListExtra(listDatesKEY);
        listPos = getIntent().getIntegerArrayListExtra(listPosKEY);

        if (listExam.size()==0) {
            listView.setVisibility(View.GONE);
        }else {
            text.setVisibility(View.GONE);
            adapter = new CustomAdapterNextExam(this, listExam, listDates);
            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    builder.setMessage(R.string.remove_notification);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabaseRef.child("users").child(userid).child("exams").child(String.valueOf(listPos.get(position))).child("notification").setValue(false);
                            listExam.remove(position);
                            listDates.remove(position);
                            listPos.remove(position);
                            adapter.notifyDataSetChanged();
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
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(listExamKEY, listExam);
                resultIntent.putExtra(listDatesKEY, listDates);
                resultIntent.putExtra(listPosKEY, listPos);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
