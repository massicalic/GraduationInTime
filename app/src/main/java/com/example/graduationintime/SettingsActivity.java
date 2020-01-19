package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = SettingsActivity.class.getName();
    private TextView edit, delete, change_psw, notifications, exit;
    private View view;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        edit = findViewById(R.id.Button_edit);
        delete = findViewById(R.id.Button_delete);
        change_psw = findViewById(R.id.Button_change_psw);
        notifications = findViewById(R.id.Button_notifications);
        exit = findViewById(R.id.Button_exit);
        view = findViewById(R.id.view3);

        if (getIntent().getBooleanExtra(providerKEY, false)) {
            change_psw.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        builder = new AlertDialog.Builder(this);

        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        change_psw.setOnClickListener(this);
        notifications.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_edit:
                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                intent.putExtra(providerKEY, getIntent().getBooleanExtra(providerKEY, false));
                intent.putExtra(nameKEY, getIntent().getStringExtra(nameKEY));
                intent.putExtra(surnameKEY, getIntent().getStringExtra(surnameKEY));
                intent.putExtra(emailKEY, getIntent().getStringExtra(emailKEY));
                intent.putExtra(dayKEY, getIntent().getIntExtra(dayKEY, 0));
                intent.putExtra(monthKEY, getIntent().getIntExtra(monthKEY, 0));
                intent.putExtra(yearKEY, getIntent().getIntExtra(yearKEY, 0));
                intent.putExtra(enrollKEY, getIntent().getIntExtra(enrollKEY, 0));
                intent.putExtra(movedKEY, getIntent().getBooleanExtra(movedKEY, false));
                intent.putExtra(studyKEY, getIntent().getIntExtra(studyKEY, 0));
                startActivity(intent);
                break;
            case R.id.Button_delete:
                builder.setMessage(R.string.DeleteAccount);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String id = user.getUid();
                        LoginManager.getInstance().logOut();
                        FirebaseAuth.getInstance().signOut();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User account deleted.");
                                        }
                                    }
                                });
                        Intent intent = new Intent(SettingsActivity.this, PreLoginActivity.class);
                        startActivity(intent);
                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                        databaseRef.child("users").child(id).removeValue();
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
            case R.id.Button_change_psw:
                Intent intent2 = new Intent(SettingsActivity.this, EditPswActivity.class);
                startActivity(intent2);
                break;
            case R.id.Button_notifications:
                break;
            case R.id.Button_exit:
                builder.setMessage(R.string.sure_exit);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getIntent().getBooleanExtra(providerKEY, false)) {
                            LoginManager.getInstance().logOut();
                            FirebaseAuth.getInstance().signOut();
                        }else{
                            FirebaseAuth.getInstance().signOut();
                        }
                        Intent intentpre = new Intent(SettingsActivity.this, PreLoginActivity.class);
                        startActivity(intentpre);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
