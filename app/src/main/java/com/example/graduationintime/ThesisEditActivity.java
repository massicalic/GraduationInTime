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

public class ThesisEditActivity extends AppCompatActivity {

    private EditText argument, info;
    private Toolbar toolbar;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private DatabaseReference mDatabaseRef;
    private String userid;

    private static final String thesisKEY = "thesis_key";
    private static final String infoThesisKEY = "thesisInfo_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thesis_edit);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        argument = findViewById(R.id.EditText_argument);
        info = findViewById(R.id.EditText_info);
        toolbar = findViewById(R.id.Toolbar);

        setSupportActionBar(toolbar);

        builder = new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        if (!getIntent().getStringExtra(thesisKEY).equals(this.getResources().getString(R.string.argument_thesis))) {
            argument.setText(getIntent().getStringExtra(thesisKEY));
        }
        if (!getIntent().getStringExtra(infoThesisKEY).equals(this.getResources().getString(R.string.info_edit))) {
            info.setText(getIntent().getStringExtra(infoThesisKEY));
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
                String arg, infos;
                arg = argument.getText().toString().trim();
                infos = info.getText().toString().trim();

                if (arg.equals("")) {
                    builder.setMessage(R.string.noArgument);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }else {
                    Intent resultIntent = new Intent();

                    mDatabaseRef.child("users").child(userid).child("thesis").setValue(arg);
                    resultIntent.putExtra(thesisKEY, arg);

                    if (infos.equals("")) {
                        mDatabaseRef.child("users").child(userid).child("thesisInfo").setValue(null);
                        resultIntent.putExtra(thesisKEY, infos);
                    }else {
                        mDatabaseRef.child("users").child(userid).child("thesisInfo").setValue(infos);
                        resultIntent.putExtra(thesisKEY, infos);
                    }
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
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
