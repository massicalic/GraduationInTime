package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DistanceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;
    private RadioGroup radioTrips, radioMove;
    private RadioButton radioButtonHome, radioButtonFrequent, radioButtonLimited, radioButtonNo, radioButtonEurope,
            radioButtonNonEuropean, radioButtonNoMove;
    private String strProv;
    private ArrayList<Integer> move;
    private Curriculum curriculum;
    private DatabaseReference mDatabase;
    private String mUserId;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private static final String curriculumKEY = "curriculum_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        builder = new AlertDialog.Builder(this);

        curriculum = (Curriculum) getIntent().getSerializableExtra(curriculumKEY);
        strProv = curriculum.getProvince();
        move = curriculum.getMove();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        editText = findViewById(R.id.EditText);
        radioTrips = findViewById(R.id.RadioGroupTrips);
        radioMove = findViewById(R.id.RadioGroupMove);
        radioButtonHome = findViewById(R.id.radioButtonHome);
        radioButtonFrequent = findViewById(R.id.radioButtonFrequent);
        radioButtonLimited = findViewById(R.id.radioButtonLimited);
        radioButtonNo = findViewById(R.id.radioButtonNo);
        radioButtonEurope = findViewById(R.id.radioButtonEurope);
        radioButtonNonEuropean = findViewById(R.id.radioButtonNonEuropean);
        radioButtonNoMove = findViewById(R.id.radioButtonNoMove);

        if (strProv!=null) {
            editText.setText(strProv);
        }
        if (move.size()!=0) {
            switch (move.get(0)) {
                case 0:
                    radioButtonHome.setChecked(true);
                    break;
                case 1:
                    radioButtonFrequent.setChecked(true);
                    break;
                case 2:
                    radioButtonLimited.setChecked(true);
                    break;
                case 3:
                    radioButtonNo.setChecked(true);
                    break;
            }
            switch (move.get(1)) {
                case 0:
                    radioButtonEurope.setChecked(true);
                    break;
                case 1:
                    radioButtonNonEuropean.setChecked(true);
                    break;
                case 2:
                    radioButtonNoMove.setChecked(true);
                    break;
            }
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
                if (!editText.getText().toString().equals("")) {
                    strProv = editText.getText().toString();
                    curriculum.setProvince(strProv);
                    move.clear();
                    switch (radioTrips.getCheckedRadioButtonId()) {
                        case R.id.radioButtonHome:
                            move.add(0);
                            break;
                        case R.id.radioButtonFrequent:
                            move.add(1);
                            break;
                        case R.id.radioButtonLimited:
                            move.add(2);
                            break;
                        case R.id.radioButtonNo:
                            move.add(3);
                            break;
                    }
                    switch (radioMove.getCheckedRadioButtonId()) {
                        case R.id.radioButtonEurope:
                            move.add(0);
                            break;
                        case R.id.radioButtonNonEuropean:
                            move.add(1);
                            break;
                        case R.id.radioButtonNoMove:
                            move.add(2);
                            break;
                    }
                    curriculum.setMove(move);
                    mDatabase.child("users").child(mUserId).child("curriculum").setValue(curriculum);
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
