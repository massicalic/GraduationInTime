package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class ElectiveExamsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AutoCompleteTextView autoText;
    private RadioGroup radio;
    private RadioButton radioButton2, radioButton3;
    private LinearLayout linearElective;
    private User user;
    private ArrayList<String> nameExams;
    private DatabaseReference mDatabase;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private  ArrayAdapter<String> adapter;
    private String mUserId;
    private boolean isEdit = false;
    private int pos;

    private static final String userKEY = "user_key";
    private static final String nameExamsKEY = "nameExams_key";
    private static final String cfuExamsKEY = "cfuExams_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective_exams);

        user = (User) getIntent().getSerializableExtra(userKEY);
        nameExams = getIntent().getStringArrayListExtra(nameExamsKEY);
        builder = new AlertDialog.Builder(this);

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        autoText = findViewById(R.id.AutoTextView);
        linearElective = findViewById(R.id.linearLayout_elective);
        radio = findViewById(R.id.RadioGroup);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);

        for (int i=0; i<user.getExams().size(); i++) {
            nameExams.remove(user.getExams().get(i).getName());
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nameExams);
        autoText.setAdapter(adapter);

        setView(this);
    }

    private void setView(final Context context) {
        for (int i=user.getExams().size()-1; !user.getExams().get(i).isFundamental(); i--) {
            final int j = i;
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            linearLayout.setLayoutParams(params);
            TextView text = new TextView(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 0, 0, 30);
            text.setLayoutParams(params);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (250 * scale + 0.5f);
            text.setMaxWidth(pixels);
            String s = user.getExams().get(i).getName();
            text.setText(s);
            linearLayout.addView(text);

            Button button = new Button(this);
            pixels = (int) (38 * scale + 0.5f);
            int pix = (int) (47 * scale + 0.5f);
            params = new LinearLayout.LayoutParams(pix, pixels);
            params.setMargins(5, 0, 0, 0);
            button.setLayoutParams(params);
            Drawable d = getResources().getDrawable(R.drawable.ic_delete_black);
            button.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            pixels = (int) (1 * scale + 0.5f);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nameExams.add(user.getExams().get(j).getName());
                            user.getExams().remove(j);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(user.getExams());

                            adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nameExams);
                            autoText.setAdapter(adapter);
                            linearElective.removeAllViews();
                            setView(context);
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
            });
            linearLayout.addView(button);
            Button buttonEdit = new Button(this);
            pixels = (int) (38 * scale + 0.5f);
            pix = (int) (47 * scale + 0.5f);
            params = new LinearLayout.LayoutParams(pix, pixels);
            params.setMargins(5, 0, 0, 0);
            buttonEdit.setLayoutParams(params);
            d = getResources().getDrawable(R.drawable.ic_edit_black);
            buttonEdit.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            pixels = (int) (1 * scale + 0.5f);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = user.getExams().get(j).getName();
                    autoText.setText(s);
                    switch (user.getExams().get(j).getTeachingYear()) {
                        case 2:
                            radioButton2.setChecked(true);
                            break;
                        case 3:
                            radioButton3.setChecked(true);
                            break;
                    }
                    isEdit = true;
                    pos = j;
                }
            });
            linearLayout.addView(buttonEdit);
            linearElective.addView(linearLayout);
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
                int sumcfu = 0;
                for (int m=0; m<nameExams.size(); m++) {
                    if (autoText.getText().toString().equals(nameExams.get(m))) {
                        for (int i=user.getExams().size()-1; !user.getExams().get(i).isFundamental(); i--) {
                            sumcfu += user.getExams().get(i).getCfu();
                        }
                        if (sumcfu==36) {
                            builder.setMessage(R.string.max_exams);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                            return false;
                        }

                        if (!autoText.getText().toString().trim().equals("") && radio.getCheckedRadioButtonId()!= -1) {

                            if (isEdit) {
                                Exam e = user.getExams().get(pos);
                                e.setName(autoText.getText().toString());
                                for (int v=0; v<nameExams.size(); v++) {
                                    if (nameExams.get(v).equals(autoText.getText().toString())) {
                                        e.setCfu(6);
                                        break;
                                    }
                                }
                                e.setFundamental(false);
                                sumcfu += 6;
                                if (sumcfu>36) {
                                    builder.setMessage(R.string.too_exams);
                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog = builder.create();
                                    dialog.show();
                                    return false;
                                }
                                int teaching = 0;
                                switch (radio.getCheckedRadioButtonId()) {
                                    case R.id.radioButton2:
                                        teaching = 2;
                                        break;
                                    case R.id.radioButton3:
                                        teaching = 3;
                                        break;
                                }
                                e.setTeachingYear(teaching);
                                user.getExams().set(pos, e);
                                mDatabase.child("users").child(mUserId).child("exams").setValue(user.getExams());
                                nameExams.remove(e.getName());
                                adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nameExams);
                                autoText.setAdapter(adapter);
                            }else {
                                Exam e = new Exam();
                                e.setName(autoText.getText().toString());
                                for (int v=0; v<nameExams.size(); v++) {
                                    if (nameExams.get(v).equals(autoText.getText().toString())) {
                                        e.setCfu(6);
                                        break;
                                    }
                                }
                                e.setFundamental(false);
                                sumcfu += 6;
                                if (sumcfu>126) {
                                    builder.setMessage(R.string.too_exams);
                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog = builder.create();
                                    dialog.show();
                                    return false;
                                }
                                int teaching = 0;
                                switch (radio.getCheckedRadioButtonId()) {
                                    case R.id.radioButton2:
                                        teaching = 2;
                                        break;
                                    case R.id.radioButton3:
                                        teaching = 3;
                                        break;
                                }
                                e.setTeachingYear(teaching);
                                user.getExams().add( e);
                                mDatabase.child("users").child(mUserId).child("exams").setValue(user.getExams());
                                nameExams.remove(e.getName());
                                adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nameExams);
                                autoText.setAdapter(adapter);
                            }
                            autoText.setText("");
                            radioButton2.setChecked(false);
                            radioButton3.setChecked(false);
                            isEdit = false;
                            linearElective.removeAllViews();
                            setView(this);
                            return false;
                        }else {
                            builder.setMessage(R.string.missing_data);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }
                    }else {
                        if (m==(nameExams.size()-1)) {
                            builder.setMessage(R.string.incorrectExam);
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
