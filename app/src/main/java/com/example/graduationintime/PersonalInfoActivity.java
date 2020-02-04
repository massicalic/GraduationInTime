package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PersonalInfoActivity extends AppCompatActivity {

    private EditText name, surname, birthDate, bornOn, citizenship, cf, address, licens, email, cell, links;
    private RadioGroup radio;
    private RadioButton buttonYes, buttonNo;
    private Button citizenshipInfo, addressInfo, licensInfo;
    private Toolbar toolbar;
    private int mYear, mMonth, mDay;
    private GregorianCalendar date;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private User user;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Boolean withCar;

    private static final String userKEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        builder = new AlertDialog.Builder(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        user = (User) getIntent().getSerializableExtra(userKEY);
        date = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());

        name = findViewById(R.id.EditText_name);
        surname = findViewById(R.id.EditText_surname);
        birthDate = findViewById(R.id.EditText_birthdate);
        bornOn = findViewById(R.id.EditText_bornon);
        citizenship = findViewById(R.id.EditText_citizenship);
        cf = findViewById(R.id.EditText_cf);
        address = findViewById(R.id.EditText_address);
        licens = findViewById(R.id.EditText_driving_license);
        email = findViewById(R.id.EditText_email);
        cell = findViewById(R.id.EditText_phone);
        links = findViewById(R.id.EditText_links);
        radio = findViewById(R.id.RadioGroup);
        buttonYes = findViewById(R.id.radioButtonYes);
        buttonNo = findViewById(R.id.radioButtonNo);
        citizenshipInfo = findViewById(R.id.Button_citizenship_info);
        addressInfo = findViewById(R.id.Button_address_info);
        licensInfo = findViewById(R.id.Button_driving_info);

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s = (dayOfMonth<10 ? "0"+dayOfMonth+"/" : dayOfMonth+"/") +
                                        ((monthOfYear+1)<10?"0"+(monthOfYear+1)+"/" : (monthOfYear+1)+"/") + (year);
                                birthDate.setText(s);
                                date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonYes:
                        withCar = true;
                        break;
                    case  R.id.radioButtonNo:
                        withCar = false;
                        break;
                }
            }
        });

        name.setText(user.getName());
        surname.setText(user.getSurname());
        String s = (user.getDay()<10 ? "0"+user.getDay()+"/" : user.getDay()+"/") +
                ((user.getMonth()+1)<10?"0"+(user.getMonth()+1)+"/" : (user.getMonth()+1)+"/") + (user.getYear());
        birthDate.setText(s);
        if (user.getCurriculum().getPlace()!=null) {
            bornOn.setText(user.getCurriculum().getPlace());
        }
        if (user.getCurriculum().getCitizenship()!=null) {
            citizenship.setText(user.getCurriculum().getCitizenship());
        }
        if (user.getCurriculum().getCf()!=null) {
            cf.setText(user.getCurriculum().getCf());
        }
        if (user.getCurriculum().getAddress()!=null) {
            address.setText(user.getCurriculum().getAddress());
        }
        if (user.getCurriculum().getLicens()!=null) {
            licens.setText(user.getCurriculum().getLicens());
        }
        if (user.getCurriculum().isWithCar()!=null) {
            if (user.getCurriculum().isWithCar()) {
                buttonYes.setChecked(true);
                withCar = true;
            }else {
                buttonNo.setChecked(true);
                withCar = false;
            }
        }
        if (user.getCurriculum().getCellphone()!=null) {
            cell.setText(user.getCurriculum().getCellphone());
        }
        if (user.getCurriculum().getEmail()!=null) {
            email.setText(user.getCurriculum().getEmail());
        }
        if (user.getCurriculum().getLinks()!=null) {
            links.setText(user.getCurriculum().getLinks());
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
                if (!name.getText().toString().equals("")) {
                    user.setName(name.getText().toString());
                }
                if (!surname.getText().toString().equals("")) {
                    user.setSurname(surname.getText().toString());
                }
                if (!birthDate.getText().toString().equals("")) {
                    user.setDay(date.get(Calendar.DAY_OF_MONTH));
                    user.setMonth(date.get(Calendar.MONTH));
                    user.setYear(date.get(Calendar.YEAR));
                }
                if (!bornOn.getText().toString().equals("")) {
                    user.getCurriculum().setPlace(bornOn.getText().toString());
                }
                if (!citizenship.getText().toString().equals("")) {
                    user.getCurriculum().setCitizenship(citizenship.getText().toString());
                }
                if (!cf.getText().toString().equals("")) {
                    user.getCurriculum().setCf(cf.getText().toString());
                }
                if (!address.getText().toString().equals("")) {
                    user.getCurriculum().setAddress(address.getText().toString());
                }
                if (!licens.getText().toString().equals("")) {
                    user.getCurriculum().setLicens(licens.getText().toString());
                }
                if (withCar!=null) {
                    user.getCurriculum().setWithCar(withCar);
                }
                if (!cell.getText().toString().equals("")) {
                    user.getCurriculum().setCellphone(cell.getText().toString());
                }
                if (!email.getText().toString().equals("")) {
                    user.getCurriculum().setEmail(email.getText().toString());
                }
                if (!links.getText().toString().equals("")) {
                    user.getCurriculum().setLinks(links.getText().toString());
                }
                mDatabase.child("users").child(mUserId).setValue(user);
                /*Intent resultIntent = new Intent();
                resultIntent.putExtra(userKEY, user);
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
