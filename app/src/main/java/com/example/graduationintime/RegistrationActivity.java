package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View first_step, second_step, third_step;
    private YouFragment you = new YouFragment(); //fragment N 1
    private DetailsFragment det = new DetailsFragment(); //fragment N 2
    private TimeStudyFragment time = new TimeStudyFragment(); //fragment N 3
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private User user;
    private int Nfragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        toolbar = findViewById(R.id.Toolbar);
        first_step = findViewById(R.id.first_step);
        second_step = findViewById(R.id.second_step);
        third_step = findViewById(R.id.third_step);

        builder = new AlertDialog.Builder(this);
        user = new User();
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        addFragment(you);
        first_step.setBackgroundResource(R.color.green);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.registration_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id) {
            case android.R.id.home:
                switch (Nfragment){
                    case 1:
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
                                            if (you.getPsw().getText().toString().trim().equals("")){
                                                builder.setMessage(R.string.noPsw);
                                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                dialog = builder.create();
                                                dialog.show();
                                            }else {
                                                String a = you.getPsw().getText().toString().trim();
                                                if (!(a.length()>=8&&a.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$"))){
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
                toolbar.setNavigationIcon(R.drawable.ic_back);
                third_step.setBackgroundResource(R.color.grey);
                break;
        }
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ancor, fragment);
        fragmentTransaction.commit();
    }
}
