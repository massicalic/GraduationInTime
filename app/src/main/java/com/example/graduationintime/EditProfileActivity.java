package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = EditProfileActivity.class.getName();
    private TextView name, surname, photo, birthdate, email, yearEnrollment, moved, studyTime;
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
    private String userid;
    private View dialogView;
    private Uri mCropImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser user;
    private int mYear, mMonth, mDay;
    private GregorianCalendar date;
    private Boolean bMoved;
    private int study_time;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        u = new User();
        u.setName(getIntent().getStringExtra(nameKEY));
        u.setSurname(getIntent().getStringExtra(surnameKEY));
        u.setEmail(getIntent().getStringExtra(emailKEY));
        u.setDay(getIntent().getIntExtra(dayKEY, 0));
        u.setMonth(getIntent().getIntExtra(monthKEY, 0));
        u.setYear(getIntent().getIntExtra(yearKEY, 0));
        u.setMoved(getIntent().getBooleanExtra(movedKEY, false));
        u.setStudyTime(getIntent().getIntExtra(studyKEY, 0));

        name = findViewById(R.id.TextView_name);
        surname = findViewById(R.id.TextView_surname);
        photo = findViewById(R.id.TextView_photo);
        birthdate = findViewById(R.id.TextView_birthdate);
        email = findViewById(R.id.TextView_email);
        yearEnrollment = findViewById(R.id.TextView_year_enroll);
        moved = findViewById(R.id.TextView_moved);
        studyTime = findViewById(R.id.TextView_study_time);
        view = findViewById(R.id.view);

        if (getIntent().getBooleanExtra(providerKEY, false)) {
            email.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        builder = new AlertDialog.Builder(this);

        name.setOnClickListener(this);
        surname.setOnClickListener(this);
        photo.setOnClickListener(this);
        birthdate.setOnClickListener(this);
        email.setOnClickListener(this);
        yearEnrollment.setOnClickListener(this);
        moved.setOnClickListener(this);
        studyTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TextView_name:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_text, null);
                final EditText editText = dialogView.findViewById(R.id.EditText_settings);
                editText.setText(u.getName());
                builder.setTitle(R.string.name);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().trim().equals("")){
                            mDatabaseRef.child("users").child(userid).child("name").setValue(editText.getText().toString().trim());
                            u.setName(editText.getText().toString().trim());
                        }else {
                            dialog.dismiss();
                        }
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
            case R.id.TextView_surname:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_text, null);
                final EditText editText1 = dialogView.findViewById(R.id.EditText_settings);
                editText1.setText(u.getSurname());
                builder.setTitle(R.string.surname);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText1.getText().toString().trim().equals("")){
                            userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            mDatabaseRef.child("users").child(userid).child("surname").setValue(editText1.getText().toString().trim());
                            u.setSurname(editText1.getText().toString().trim());
                        }else {
                            dialog.dismiss();
                        }
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
            case R.id.TextView_photo:
                CropImage.startPickImageActivity(this);
                break;
            case R.id.TextView_birthdate:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_birthdate, null);
                final EditText editText3 = dialogView.findViewById(R.id.EditText_birthdate);
                editText3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = getIntent().getIntExtra(yearKEY,0);
                        mMonth = getIntent().getIntExtra(monthKEY,0);
                        mDay = getIntent().getIntExtra(dayKEY,0);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        String s = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                        if ((monthOfYear+1)<10){
                                            s = dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
                                        }
                                        editText3.setText(s);
                                        date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                String a = (u.getDay()<10?"0"+u.getDay(): u.getDay())+"/"+(u.getMonth()+1<10?"0"+ u.getMonth() : u.getMonth()) +
                        "/" + u.getYear();
                editText3.setText(a);
                builder.setTitle(R.string.birthdate);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date!=null){
                            mDatabaseRef.child("users").child(userid).child("day").setValue(date.get(Calendar.DAY_OF_MONTH));
                            mDatabaseRef.child("users").child(userid).child("month").setValue(date.get(Calendar.MONTH));
                            mDatabaseRef.child("users").child(userid).child("year").setValue(date.get(Calendar.YEAR));
                            u.setDay(date.get(Calendar.DAY_OF_MONTH));
                            u.setMonth(date.get(Calendar.MONTH));
                            u.setYear(date.get(Calendar.YEAR));
                        }else {
                            dialog.dismiss();
                        }
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
            case R.id.TextView_email:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_text, null);
                final EditText editText2 = dialogView.findViewById(R.id.EditText_settings);
                editText2.setText(u.getEmail());
                builder.setTitle(R.string.email);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText2.getText().toString().trim().equals("")){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(editText2.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                            }
                                        }
                                    });
                            mDatabaseRef.child("users").child(userid).child("email").setValue(editText2.getText().toString().trim());
                            u.setEmail(editText2.getText().toString().trim());
                        }else {
                            dialog.dismiss();
                        }
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
            case R.id.TextView_year_enroll:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_spinner, null);
                final Spinner spinner = dialogView.findViewById(R.id.spinner);
                int year = new GregorianCalendar().get(GregorianCalendar.YEAR);
                List<Integer> spinnerArray =  new ArrayList<>();
                for(int i = 0; i<30; i++){
                    spinnerArray.add(year);
                    year--;
                }
                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(EditProfileActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                builder.setTitle(R.string.uni_year);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseRef.child("users").child(userid).child("yearEnroll").setValue((Integer) spinner.getSelectedItem());
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
            case R.id.TextView_moved:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_radio, null);
                final RadioGroup radio = dialogView.findViewById(R.id.RadioGroup);
                RadioButton buttonYes = dialogView.findViewById(R.id.radioButtonYes);
                RadioButton buttonNo = dialogView.findViewById(R.id.radioButtonNo);
                bMoved = u.isMoved();
                if (bMoved) {
                    buttonYes.setChecked(true);
                }else {
                    buttonNo.setChecked(true);
                }
                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radioButtonYes:
                                bMoved = true;
                                break;
                            case  R.id.radioButtonNo:
                                bMoved = false;
                                break;
                        }
                    }
                });

                builder.setTitle(R.string.moved);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseRef.child("users").child(userid).child("moved").setValue(bMoved);
                        u.setMoved(bMoved);
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
            case R.id.TextView_study_time:
                dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile_radio_study, null);
                final RadioGroup radio_study = dialogView.findViewById(R.id.RadioGroup);
                RadioButton buttonStud = dialogView.findViewById(R.id.radioButtonStud);
                RadioButton buttonStudWork = dialogView.findViewById(R.id.radioButtonStudWork);
                RadioButton buttonWorkStud = dialogView.findViewById(R.id.radioButtonWorkStud);
                study_time = u.getStudyTime();
                switch (study_time) {
                    case 1:
                        buttonStud.setChecked(true);
                        break;
                    case 2:
                        buttonStudWork.setChecked(true);
                        break;
                    case 3:
                        buttonWorkStud.setChecked(true);
                        break;
                }
                radio_study.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radioButtonStud:
                                study_time = 1;
                                break;
                            case  R.id.radioButtonStudWork:
                                study_time = 2;
                                break;
                            case  R.id.radioButtonWorkStud:
                                study_time = 3;
                                break;
                        }
                    }
                });

                builder.setTitle(R.string.time);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseRef.child("users").child(userid).child("studyTime").setValue(study_time);
                        u.setStudyTime(study_time);
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
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},   CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            String fileExt = MimeTypeMap.getFileExtensionFromUrl(result.getUri().toString());
            final String id = user.getUid();
            final StorageReference fileRef = mStorageRef.child(id+".jpg");
            fileRef.putFile(result.getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String s = uri.toString();
                            mDatabaseRef.child("users").child(id).child("imageUrl").setValue(s);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Write in Storage failure", e);
                }
            });
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).start(this);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
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
