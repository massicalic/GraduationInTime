package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EditPswActivity extends AppCompatActivity {

    private static final String TAG = EditPswActivity.class.getName();
    private EditText newPsw, retypePsw;
    private Button edit;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_psw);

        newPsw = findViewById(R.id.EditText_new_psw);
        retypePsw = findViewById(R.id.EditText_retype);
        edit = findViewById(R.id.Button_edit);
        toolbar = findViewById(R.id.Toolbar);

        builder = new AlertDialog.Builder(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPsw.getText().toString().equals("")) {
                    builder.setMessage(R.string.noPsw);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                } else {
                    String a = newPsw.getText().toString();
                    if (!(a.length() >= 8 && a.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$"))) {
                        builder.setMessage(R.string.incorrectPsw);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog = builder.create();
                        dialog.show();
                    }else {
                        if (newPsw.getText().toString().equals(retypePsw.getText().toString())) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.updatePassword(newPsw.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User password updated.");
                                            }
                                        }
                                    });
                            finish();
                        }else {
                            builder.setMessage(R.string.differentPsw);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
