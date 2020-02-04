package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ProfessionalGoalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText goal;
    private String strGoal;
    private DatabaseReference mDatabase;
    private String mUserId;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private static final String goalKEY = "goal_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_goal);

        builder = new AlertDialog.Builder(this);

        strGoal = getIntent().getStringExtra(goalKEY);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        goal = findViewById(R.id.EditText_goal);

        if (strGoal!=null) {
            goal.setText(strGoal);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.save:
                if (!goal.getText().toString().equals("")) {
                    strGoal = goal.getText().toString();
                    mDatabase.child("users").child(mUserId).child("curriculum").child("goal").setValue(strGoal);
                }
                /*Intent resultIntent = new Intent();
                resultIntent.putExtra(goalKEY, strGoal);
                setResult(Activity.RESULT_OK, resultIntent);*/
                finish();
                break;
            case android.R.id.home:
                builder.setMessage(R.string.areYouSure);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        builder.setMessage(R.string.areYouSure);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
    }
}
