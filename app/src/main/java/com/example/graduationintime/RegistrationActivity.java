package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getName();
    private Toolbar toolbar;
    private View first_step, second_step, third_step;
    private YouFragment you = new YouFragment(); //fragment N 1
    private DetailsFragment det = new DetailsFragment(); //fragment N 2
    private TimeStudyFragment time = new TimeStudyFragment(); //fragment N 3
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private User user;
    private int Nfragment = 1;
    private MenuItem next;
    private String userid;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    private Menu menus;
    private Intent intentservice;

    private static final String nameKEY = "name_key";
    private static final String surnameKEY = "surname_key";
    private static final String emailKEY = "email_key";
    private static final String useridKEY = "userid_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.Toolbar);
        first_step = findViewById(R.id.first_step);
        second_step = findViewById(R.id.second_step);
        third_step = findViewById(R.id.third_step);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = mFirebaseAuth.getCurrentUser();

        builder = new AlertDialog.Builder(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        addFragment(you);
        first_step.setBackgroundResource(R.color.green);

        intentservice = new Intent(this, ExitService.class);
        startService(intentservice);

        if (getIntent().getStringExtra(nameKEY)!=null) {
            you.setStringName(getIntent().getStringExtra(nameKEY));
            you.setStringSurname(getIntent().getStringExtra(surnameKEY));
            you.setStringEmail(getIntent().getStringExtra(emailKEY));
            userid = getIntent().getStringExtra(useridKEY);
        }
        user = new User();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.registration_menu, menu);
        menus = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id) {
            case android.R.id.home:
                switch (Nfragment){
                    case 1:
                        LoginManager.getInstance().logOut();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(RegistrationActivity.this, PreLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        addFragment(you);
                        Nfragment = 1;
                        toolbar.setTitle(R.string.title_reg_you);
                        second_step.setBackgroundResource(R.color.grey);
                        break;
                    case 3:
                        addFragment(det);
                        Nfragment = 2;
                        toolbar.setTitle(R.string.title_reg_det);
                        third_step.setBackgroundResource(R.color.grey);
                        toolbar.getMenu().clear();
                        getMenuInflater().inflate(R.menu.registration_menu, menus);
                        break;
                }
                return true;
            case R.id.next:
                switch (Nfragment){
                    case 1:
                        if (you.getName().getText().toString().trim().equals("")){
                            builder.setMessage(R.string.noName);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }else{
                            if (you.getSurname().getText().toString().trim().equals("")){
                                builder.setMessage(R.string.noSurname);
                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog = builder.create();
                                dialog.show();
                            }else {
                                if (you.getBirthdate().getText().toString().equals("")){
                                    builder.setMessage(R.string.noBirthdate);
                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog = builder.create();
                                    dialog.show();
                                }else {
                                    if (you.getEmail().getText().toString().trim().equals("")){
                                        builder.setMessage(R.string.noEmail);
                                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog = builder.create();
                                        dialog.show();
                                    }else {
                                        String v = you.getEmail().getText().toString();
                                        if(!(v.contains("@")&&v.contains("."))){
                                            builder.setMessage(R.string.incorrectEmail);
                                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog = builder.create();
                                            dialog.show();
                                        }else{
                                            if (userid==null) {
                                                if (you.getPsw().getText().toString().trim().equals("")) {
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
                                                    String a = you.getPsw().getText().toString().trim();
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
                                                    } else {
                                                        det.setActivity(this);
                                                        addFragment(det);
                                                        toolbar.setTitle(R.string.title_reg_det);
                                                        Nfragment = 2;
                                                        second_step.setBackgroundResource(R.color.green);
                                                        user.setName(you.getName().getText().toString());
                                                        user.setSurname(you.getSurname().getText().toString());
                                                        user.setBirthdate(you.getDate());
                                                        user.setEmail(you.getEmail().getText().toString());
                                                        user.setPsw(you.getPsw().getText().toString());
                                                    }
                                                }
                                            } else{
                                                det.setActivity(this);
                                                addFragment(det);
                                                toolbar.setTitle(R.string.title_reg_det);
                                                Nfragment = 2;
                                                second_step.setBackgroundResource(R.color.green);
                                                user.setName(you.getName().getText().toString());
                                                user.setSurname(you.getSurname().getText().toString());
                                                user.setBirthdate(you.getDate());
                                                user.setEmail(you.getEmail().getText().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                        if (det.isMoved()==null){
                            builder.setMessage(R.string.noMoved);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }else{
                            time.setActivity(this);
                            addFragment(time);
                            toolbar.setTitle(R.string.title_reg_time);
                            Nfragment = 3;
                            third_step.setBackgroundResource(R.color.green);
                            user.setYearEnroll((Integer) det.getSpinner().getSelectedItem());
                            user.setMoved(det.isMoved());
                            toolbar.getMenu().clear();
                            getMenuInflater().inflate(R.menu.finish_registration_menu, menus);
                        }
                        break;
                    case 3:
                        if (time.getTime()==0){
                            builder.setMessage(R.string.noTime);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        }else{
                            user.setStudyTime(time.getTime());
                            if (userid==null){
                                mFirebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPsw())
                                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    user.setPsw(null);
                                                    userid = mFirebaseAuth.getCurrentUser().getUid();
                                                    user.setDay(user.getBirthdate().get(Calendar.DAY_OF_MONTH));
                                                    user.setMonth(user.getBirthdate().get(Calendar.MONTH));
                                                    user.setYear(user.getBirthdate().get(Calendar.YEAR));
                                                    user.setBirthdate(null);
                                                    mDatabase.child("users").child(userid).setValue(user);
                                                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                                    builder.setMessage(task.getException().getMessage())
                                                            .setTitle(R.string.login_error_title)
                                                            .setPositiveButton(android.R.string.ok, null);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            }
                                        });
                            }else {
                                user.setPsw(null);
                                user.setDay(user.getBirthdate().get(Calendar.DAY_OF_MONTH));
                                user.setMonth(user.getBirthdate().get(Calendar.MONTH));
                                user.setYear(user.getBirthdate().get(Calendar.YEAR));
                                user.setBirthdate(null);
                                mDatabase.child("users").child(userid).setValue(user);
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                stopService(intentservice);
                                startActivity(intent);
                            }
                            finish();
                        }
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        switch (Nfragment){
            case 1:
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(RegistrationActivity.this, PreLoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                addFragment(you);
                toolbar.setTitle(R.string.title_reg_you);
                Nfragment = 1;
                second_step.setBackgroundResource(R.color.grey);
                break;
            case 3:
                addFragment(det);
                Nfragment = 2;
                toolbar.setTitle(R.string.title_reg_det);
                third_step.setBackgroundResource(R.color.grey);
                toolbar.getMenu().clear();
                getMenuInflater().inflate(R.menu.registration_menu, menus);
                break;
        }
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ancor, fragment);
        fragmentTransaction.commit();
    }
}
