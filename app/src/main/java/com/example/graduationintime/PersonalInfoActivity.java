package com.example.graduationintime;

import androidx.annotation.NonNull;
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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = PersonalInfoActivity.class.getName();
    private EditText name, surname, birthDate, birthPlace, citizenship, cf, address, licens, email, cell, links;
    private RadioGroup radio;
    private RadioButton buttonYes, buttonNo;
    private Button citizenshipInfo, addressInfo, licensInfo;
    private ImageView image;
    private Toolbar toolbar;
    private int mYear, mMonth, mDay;
    private GregorianCalendar date;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private User user;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Boolean withCar;
    private Uri mCropImageUri;
    private StorageReference mStorage;

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
        mStorage = FirebaseStorage.getInstance().getReference();

        user = (User) getIntent().getSerializableExtra(userKEY);
        date = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());

        name = findViewById(R.id.EditText_name);
        surname = findViewById(R.id.EditText_surname);
        birthDate = findViewById(R.id.EditText_birthdate);
        birthPlace = findViewById(R.id.EditText_birth_place);
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
        image = findViewById(R.id.imageView);

        citizenshipInfo.setOnClickListener(this);
        addressInfo.setOnClickListener(this);
        licensInfo.setOnClickListener(this);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(PersonalInfoActivity.this);
            }
        });
        Glide.with(PersonalInfoActivity.this).load(user.getImageUrl()).fitCenter().centerCrop().into(image);

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
            birthPlace.setText(user.getCurriculum().getPlace());
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
                if (!birthPlace.getText().toString().equals("")) {
                    user.getCurriculum().setPlace(birthPlace.getText().toString());
                }else {
                    user.getCurriculum().setPlace(null);
                }
                if (!citizenship.getText().toString().equals("")) {
                    user.getCurriculum().setCitizenship(citizenship.getText().toString());
                }else {
                    user.getCurriculum().setCitizenship(null);
                }
                if (!cf.getText().toString().equals("")) {
                    user.getCurriculum().setCf(cf.getText().toString());
                }else {
                    user.getCurriculum().setCf(null);
                }
                if (!address.getText().toString().equals("")) {
                    user.getCurriculum().setAddress(address.getText().toString());
                }else {
                    user.getCurriculum().setAddress(null);
                }
                if (!licens.getText().toString().equals("")) {
                    user.getCurriculum().setLicens(licens.getText().toString());
                }else {
                    user.getCurriculum().setLicens(null);
                }
                if (withCar!=null) {
                    user.getCurriculum().setWithCar(withCar);
                }
                if (!cell.getText().toString().equals("")) {
                    user.getCurriculum().setCellphone(cell.getText().toString());
                }else {
                    user.getCurriculum().setCellphone(null);
                }
                if (!email.getText().toString().equals("")) {
                    user.getCurriculum().setEmail(email.getText().toString());
                }else {
                    user.getCurriculum().setEmail(email.getText().toString());
                }
                if (!links.getText().toString().equals("")) {
                    user.getCurriculum().setLinks(links.getText().toString());
                }else {
                    user.getCurriculum().setLinks(null);
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

            image.setImageURI(result.getUri());
            final String mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final StorageReference fileRef = mStorage.child(mUserId+".jpg");
            fileRef.putFile(result.getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String s = uri.toString();
                            mDatabase.child("users").child(mUserId).child("imageUrl").setValue(s);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_citizenship_info:
                builder.setMessage(R.string.citizenship_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_address_info:
                builder.setMessage(R.string.address_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_driving_info:
                builder.setMessage(R.string.driving_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
}