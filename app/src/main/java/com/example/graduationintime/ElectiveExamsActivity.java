package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class ElectiveExamsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;
    private TextView exam1, exam2, exam3, exam4, exam5, exam6;
    private Button edit1, delete1, edit2, delete2, edit3, edit4, edit5, edit6, delete3, delete4, delete5, delete6;
    private ArrayList<Exam> exams;
    private DatabaseReference mDatabase;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private String mUserId;
    private boolean isEdit = false;
    private int pos;

    private static final String userKEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective_exams);

        User u = (User) getIntent().getSerializableExtra(userKEY);
        exams = u.getExams();

        builder = new AlertDialog.Builder(this);

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        editText = findViewById(R.id.EditText);
        exam1 = findViewById(R.id.exam1);
        exam2 = findViewById(R.id.exam2);
        exam3 = findViewById(R.id.exam3);
        exam4 = findViewById(R.id.exam4);
        exam5 = findViewById(R.id.exam5);
        exam6 = findViewById(R.id.exam6);
        edit1 = findViewById(R.id.Button_edit1);
        edit2 = findViewById(R.id.Button_edit2);
        edit3 = findViewById(R.id.Button_edit3);
        edit4 = findViewById(R.id.Button_edit4);
        edit5 = findViewById(R.id.Button_edit5);
        edit6 = findViewById(R.id.Button_edit6);
        delete1 = findViewById(R.id.Button_delete1);
        delete2 = findViewById(R.id.Button_delete2);
        delete3 = findViewById(R.id.Button_delete3);
        delete4 = findViewById(R.id.Button_delete4);
        delete5 = findViewById(R.id.Button_delete5);
        delete6 = findViewById(R.id.Button_delete6);

        if (exams.size()>=17) {
            exam1.setVisibility(View.VISIBLE);
            edit1.setVisibility(View.VISIBLE);
            delete1.setVisibility(View.VISIBLE);
            exam1.setText(exams.get(16).getName());
            edit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(exam1.getText());
                    isEdit = true;
                    pos = 16;
                }
            });
            delete1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exam1.setVisibility(View.GONE);
                            edit1.setVisibility(View.GONE);
                            delete1.setVisibility(View.GONE);
                            exams.remove(16);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
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
        }
        if (exams.size()>=18) {
            exam2.setVisibility(View.VISIBLE);
            edit2.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);
            exam2.setText(exams.get(17).getName());
            edit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(exam2.getText());
                    isEdit = true;
                    pos = 17;
                }
            });
            delete2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exam2.setVisibility(View.GONE);
                            edit2.setVisibility(View.GONE);
                            delete2.setVisibility(View.GONE);
                            exams.remove(17);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
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
        }
        if (exams.size()>=19) {
            exam3.setVisibility(View.VISIBLE);
            edit3.setVisibility(View.VISIBLE);
            delete3.setVisibility(View.VISIBLE);
            exam3.setText(exams.get(18).getName());
            edit3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(exam3.getText());
                    isEdit = true;
                    pos = 18;
                }
            });
            delete3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exam3.setVisibility(View.GONE);
                            edit3.setVisibility(View.GONE);
                            delete3.setVisibility(View.GONE);
                            exams.remove(18);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
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
        }
        if (exams.size()>=20) {
            exam4.setVisibility(View.VISIBLE);
            edit4.setVisibility(View.VISIBLE);
            delete4.setVisibility(View.VISIBLE);
            exam4.setText(exams.get(19).getName());
            edit4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(exam4.getText());
                    isEdit = true;
                    pos = 19;
                }
            });
            delete4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exam4.setVisibility(View.GONE);
                            edit4.setVisibility(View.GONE);
                            delete4.setVisibility(View.GONE);
                            exams.remove(19);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
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
        }
        if (exams.size()>=21) {
            exam5.setVisibility(View.VISIBLE);
            edit5.setVisibility(View.VISIBLE);
            delete5.setVisibility(View.VISIBLE);
            exam5.setText(exams.get(20).getName());
            edit5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(exam5.getText());
                    isEdit = true;
                    pos = 20;
                }
            });
            delete5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exam5.setVisibility(View.GONE);
                            edit5.setVisibility(View.GONE);
                            delete5.setVisibility(View.GONE);
                            exams.remove(20);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
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
        }
        if (exams.size()>=22) {
            exam6.setVisibility(View.VISIBLE);
            edit6.setVisibility(View.VISIBLE);
            delete6.setVisibility(View.VISIBLE);
            exam6.setText(exams.get(21).getName());
            edit6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(exam6.getText());
                    isEdit = true;
                    pos = 21;
                }
            });
            delete6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exam6.setVisibility(View.GONE);
                            edit6.setVisibility(View.GONE);
                            delete6.setVisibility(View.GONE);
                            exams.remove(21);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
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
                if (exams.size()!=22) {
                    if (!editText.getText().toString().equals("")) {
                        if (isEdit) {
                            Exam e = new Exam();
                            e.setName(editText.getText().toString());
                            e.setCfu(6);
                            exams.set(pos, e);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
                        }else {
                            Exam e = new Exam();
                            e.setName(editText.getText().toString());
                            e.setCfu(6);
                            exams.add(e);
                            mDatabase.child("users").child(mUserId).child("exams").setValue(exams);
                        }
                    }
                    finish();
                }else {
                    builder.setMessage(R.string.chosen_all_exams);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
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

