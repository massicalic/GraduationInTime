package com.example.graduationintime;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurriculumFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = CurriculumFragment.class.getName();
    private AppCompatActivity activity;
    private Button personal, personalInfo, goal, goalInfo, skill, skillInfo, exp, expInfo, edu, eduInfo,
            language, languageInfo, it, itInfo, distance, distanceInfo;
    private TextView name, birthDate, age, place, place1, citizenship, citizenship1, cf, cf1, address, address1,
            licens, licens1, email, email1, cell, cell1, car, links;
    private LinearLayout  linearGoal, linearSkill, linearExperiences, linearEducation, linearLanguage,
            linearIt, linearDistance;
    private ImageView image;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private View view;
    private Toolbar toolbar;
    private User user;
    private boolean repeat;
    private DatabaseReference mDatabase;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private boolean running;

    private Uri mCropImageUri;
    private FirebaseUser u;
    private StorageReference mStorage;

    private static final String userKEY = "user_key";
    private static final String goalKEY = "goal_key";
    private static final String skillKEY = "skill_key";
    private static final String experiencesKEY = "experiences_key";
    private static final String educationKEY = "education_key";
    private static final String tipeEducationKEY = "tipeEducation_key";
    private static final String curriculumKEY = "curriculum_key";
    private static final String itKEY = "it_key";

    public CurriculumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_curriculum, container, false);
        toolbar = view.findViewById(R.id.Toolbar);
        activity.setSupportActionBar(toolbar);

        builder = new AlertDialog.Builder(activity);
        u = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

        progressBar = view.findViewById(R.id.progress_bar);
        scroll = view.findViewById(R.id.scroll);
        image = view.findViewById(R.id.imageView);
        personal = view.findViewById(R.id.Button_personal);
        personalInfo = view.findViewById(R.id.Button_personal_info);
        goal = view.findViewById(R.id.Button_goal);
        goalInfo = view.findViewById(R.id.Button_goal_info);
        skill = view.findViewById(R.id.Button_skill);
        skillInfo = view.findViewById(R.id.Button_skill_info);
        exp = view.findViewById(R.id.Button_experiences);
        expInfo = view.findViewById(R.id.Button_experiences_info);
        edu = view.findViewById(R.id.Button_education);
        eduInfo = view.findViewById(R.id.Button_education_info);
        language = view.findViewById(R.id.Button_language);
        languageInfo = view.findViewById(R.id.Button_language_info);
        it = view.findViewById(R.id.Button_it);
        itInfo = view.findViewById(R.id.Button_it_info);
        distance = view.findViewById(R.id.Button_distance);
        distanceInfo = view.findViewById(R.id.Button_distance_info);
        name = view.findViewById(R.id.TextView_name);
        birthDate = view.findViewById(R.id.TextView_birthdate);
        age  = view.findViewById(R.id.TextView_age);
        place = view.findViewById(R.id.TextView_birth_place);
        place1 = view.findViewById(R.id.TextView_birth_place1);
        citizenship = view.findViewById(R.id.TextView_citizenship);
        citizenship1 = view.findViewById(R.id.TextView_citizenship1);
        cf = view.findViewById(R.id.TextView_cf);
        cf1 = view.findViewById(R.id.TextView_cf1);
        address = view.findViewById(R.id.TextView_address);
        address1 = view.findViewById(R.id.TextView_address1);
        licens = view.findViewById(R.id.TextView_driving);
        licens1 = view.findViewById(R.id.TextView_driving1);
        email = view.findViewById(R.id.TextView_email);
        email1 = view.findViewById(R.id.TextView_email1);
        cell = view.findViewById(R.id.TextView_cell);
        cell1 = view.findViewById(R.id.TextView_cell1);
        car = view.findViewById(R.id.TextView_car);
        links = view.findViewById(R.id.TextView_links);

        linearGoal = view.findViewById(R.id.linearLayout_goal);
        linearSkill = view.findViewById(R.id.linearLayout_skill);
        linearExperiences = view.findViewById(R.id.linearLayout_experiences);
        linearEducation = view.findViewById(R.id.linearLayout_education);
        linearLanguage = view.findViewById(R.id.linearLayout_language);
        linearIt = view.findViewById(R.id.linearLayout_it);
        linearDistance = view.findViewById(R.id.linearLayout_distance);

        personal.setOnClickListener(this);
        personalInfo.setOnClickListener(this);
        goal.setOnClickListener(this);
        goalInfo.setOnClickListener(this);
        skill.setOnClickListener(this);
        skillInfo.setOnClickListener(this);
        exp.setOnClickListener(this);
        expInfo.setOnClickListener(this);
        edu.setOnClickListener(this);
        eduInfo.setOnClickListener(this);
        language.setOnClickListener(this);
        languageInfo.setOnClickListener(this);
        it.setOnClickListener(this);
        itInfo.setOnClickListener(this);
        distance.setOnClickListener(this);
        distanceInfo.setOnClickListener(this);

        scroll.setVisibility(View.GONE);

        final String mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        repeat = true;
        running = true;

        if (user==null) {
            mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        String key = obj.getKey();
                        if(key.equals(mUserId)){
                            user = dataSnapshot.child(key).getValue(User.class);

                            scroll.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            linearGoal.removeAllViews();
                            linearSkill.removeAllViews();
                            linearExperiences.removeAllViews();
                            linearEducation.removeAllViews();
                            linearLanguage.removeAllViews();
                            linearIt.removeAllViews();
                            linearDistance.removeAllViews();

                            repeat = false;
                            if (running) {
                                Glide.with(activity).load(user.getImageUrl()).fitCenter().centerCrop().into(image);
                            }
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CropImage.startPickImageActivity(activity);
                                }
                            });
                            String s = user.getName().toUpperCase()+ " " +user.getSurname().toUpperCase();
                            name.setText(s);
                            s = (user.getDay()<10 ? "0"+user.getDay()+"/" : user.getDay()+"/") +
                                    ((user.getMonth()+1)<10?"0"+(user.getMonth()+1)+"/" : (user.getMonth()+1)+"/") + (user.getYear());
                            birthDate.setText(s);
                            Calendar c = Calendar.getInstance();
                            Calendar dateCalendar = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());
                            long diffInMilli = c.getTimeInMillis() - dateCalendar.getTimeInMillis();
                            diffInMilli = diffInMilli/1000/60/60/24/365;
                            age.setText(String.valueOf(diffInMilli));
                            if (user.getCurriculum()!=null) {
                                //PERSONAL INFORMATION
                                if (user.getCurriculum().getPlace() != null) {
                                    place.setVisibility(View.VISIBLE);
                                    place1.setVisibility(View.VISIBLE);
                                    place.setText(user.getCurriculum().getPlace());
                                } else {
                                    place.setVisibility(View.GONE);
                                    place1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getCitizenship() != null) {
                                    citizenship.setVisibility(View.VISIBLE);
                                    citizenship1.setVisibility(View.VISIBLE);
                                    citizenship.setText(user.getCurriculum().getCitizenship());
                                } else {
                                    citizenship.setVisibility(View.GONE);
                                    citizenship1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getCf() != null) {
                                    cf.setVisibility(View.VISIBLE);
                                    cf1.setVisibility(View.VISIBLE);
                                    cf.setText(user.getCurriculum().getCf());
                                } else {
                                    cf.setVisibility(View.GONE);
                                    cf1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getAddress() != null) {
                                    address.setVisibility(View.VISIBLE);
                                    address1.setVisibility(View.VISIBLE);
                                    address.setText(user.getCurriculum().getAddress());
                                } else {
                                    address.setVisibility(View.GONE);
                                    address1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getLicens() != null) {
                                    licens.setVisibility(View.VISIBLE);
                                    licens1.setVisibility(View.VISIBLE);
                                    licens.setText(user.getCurriculum().getLicens());
                                } else {
                                    licens.setVisibility(View.GONE);
                                    licens1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().isWithCar() != null) {
                                    car.setVisibility(View.VISIBLE);
                                    if (user.getCurriculum().isWithCar()) {
                                        car.setText(activity.getResources().getString(R.string.with_car));
                                    } else {
                                        car.setText(activity.getResources().getString(R.string.without_car));
                                    }
                                } else {
                                    car.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getEmail() != null) {
                                    email.setVisibility(View.VISIBLE);
                                    email1.setVisibility(View.VISIBLE);
                                    email.setText(user.getCurriculum().getEmail());
                                } else {
                                    email.setVisibility(View.GONE);
                                    email1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getCellphone() != null) {
                                    cell.setVisibility(View.VISIBLE);
                                    cell1.setVisibility(View.VISIBLE);
                                    cell.setText(user.getCurriculum().getCellphone());
                                } else {
                                    cell.setVisibility(View.GONE);
                                    cell1.setVisibility(View.GONE);
                                }
                                if (user.getCurriculum().getLinks()!=null) {
                                    links.setVisibility(View.VISIBLE);
                                    links.setText(user.getCurriculum().getLinks());
                                }else {
                                    links.setVisibility(View.GONE);
                                }

                                //PROFESSIONAL GOAL
                                if (user.getCurriculum().getGoal()!=null) {
                                    TextView goalText = new TextView(activity);
                                    goalText.setText(user.getCurriculum().getGoal());
                                    linearGoal.addView(goalText);
                                }
                                //SOFT SKILL
                                if (user.getCurriculum().getSkills().size()!=0) {
                                    ArrayList<Integer> sk = user.getCurriculum().getSkills();
                                    TextView skillsText = new TextView(activity);
                                    String str = getResources().getString(R.string.autonomy)+" ";
                                    skillsText.setText(str);
                                    str = sk.get(0) + "/10\n";
                                    Spannable word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.self_confidence)+" ";
                                    skillsText.append(str);
                                    str = sk.get(1) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.stress)+" ";
                                    skillsText.append(str);
                                    str = sk.get(2) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.organize)+" ";
                                    skillsText.append(str);
                                    str = sk.get(3) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.accuracy)+" ";
                                    skillsText.append(str);
                                    str = sk.get(4) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.learn)+" ";
                                    skillsText.append(str);
                                    str = sk.get(5) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.goals)+" ";
                                    skillsText.append(str);
                                    str = sk.get(6) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.manage_info)+" ";
                                    skillsText.append(str);
                                    str = sk.get(7) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.initiative)+" ";
                                    skillsText.append(str);
                                    str = sk.get(8) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.comunication_skills)+" ";
                                    skillsText.append(str);
                                    str = sk.get(9) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.problem_solving)+" ";
                                    skillsText.append(str);
                                    str = sk.get(10) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.team_work)+" ";
                                    skillsText.append(str);
                                    str = sk.get(11) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    str =  getResources().getString(R.string.leadership)+" ";
                                    skillsText.append(str);
                                    str = sk.get(12) +"/10\n";
                                    word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    skillsText.append(word);
                                    linearSkill.addView(skillsText);
                                }

                                //WORK EXPERIENCES
                                if (user.getCurriculum().getExperiences().size()!=0) {
                                    ArrayList<String> exp = user.getCurriculum().getExperiences();
                                    for (int i=0; i<exp.size(); i++) {
                                        TextView expText = new TextView(activity);
                                        expText.setText(exp.get(i));
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(0, 0, 30, 10);
                                        expText.setLayoutParams(params);
                                        linearExperiences.addView(expText);
                                    }
                                }

                                //EDUCATION
                                if (user.getCurriculum().getEducation().size()!=0) {
                                    ArrayList<String> edu = user.getCurriculum().getEducation();
                                    ArrayList<Integer> tipeEdu = user.getCurriculum().getTipeEducation();
                                    for (int i=0; i<edu.size(); i++) {
                                        TextView eduText = new TextView(activity);
                                        if (tipeEdu.get(i)==0) {
                                            String str = getResources().getString(R.string.school)+": ";
                                            Spannable word = new SpannableString(str);
                                            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            eduText.setText(word);
                                        }else {
                                            String str = getResources().getString(R.string.university)+": ";
                                            Spannable word = new SpannableString(str);
                                            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            eduText.setText(word);
                                        }
                                        eduText.append(edu.get(i));
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(0, 0, 30, 10);
                                        eduText.setLayoutParams(params);
                                        linearEducation.addView(eduText);
                                    }
                                }

                                //LANGUAGES SKILLS
                                if (user.getCurriculum().getLanguagesNative()!=null) {
                                    String langNative = user.getCurriculum().getLanguagesNative();
                                    TextView langText = new TextView(activity);
                                    String str = getResources().getString(R.string.native_language)+ ": ";
                                    Spannable word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    langText.setText(word);
                                    str = langNative;
                                    langText.append(str);
                                    linearLanguage.addView(langText);
                                }
                                if (user.getCurriculum().getLanguagesKnow().size()!=0) {
                                    ArrayList<String> langKnow = user.getCurriculum().getLanguagesKnow();
                                    ArrayList<ArrayList<String>> know = user.getCurriculum().getKnowledge();

                                    for (int i=0; i<langKnow.size(); i++) {
                                        if (i==0) {
                                            TextView languText = new TextView(activity);
                                            String str = getResources().getString(R.string.known_languages);
                                            Spannable word = new SpannableString(str);
                                            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            languText.setText(word);
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                                            params.setMargins(10, 5, 0, 10);
                                            languText.setLayoutParams(params);
                                            linearLanguage.addView(languText);
                                        }
                                        TextView langText = new TextView(activity);
                                        String str = langKnow.get(i)+":\n"+getResources().getString(R.string.listening)+" "+ know.get(i).get(0)+"  "+
                                                getResources().getString(R.string.reading)+" "+ know.get(i).get(1)+"\n"+
                                                getResources().getString(R.string.comunication)+ " "+know.get(i).get(2)+"  "+
                                                getResources().getString(R.string.writing)+ " "+know.get(i).get(3);
                                        langText.setText(str);
                                        float scale = getResources().getDisplayMetrics().density;
                                        int pixels = (int) (400 * scale + 0.5f);
                                        langText.setMaxWidth(pixels);
                                        linearLanguage.addView(langText);
                                    }
                                }

                                //IT SKILLS
                                if (user.getCurriculum().getIt().size()!=0) {
                                    ArrayList<String> it = user.getCurriculum().getIt();
                                    for (int i=0; i<it.size(); i++) {
                                        TextView itText = new TextView(activity);
                                        itText.setText(it.get(i));
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(0, 0, 30, 10);
                                        itText.setLayoutParams(params);
                                        linearIt.addView(itText);
                                    }
                                }

                                //DISTANCE FROM WORKPLACE
                                if (user.getCurriculum().getProvince()!=null) {
                                    TextView provText = new TextView(activity);
                                    String str = getResources().getString(R.string.favorite_provinces)+ ": ";
                                    Spannable word = new SpannableString(str);
                                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    provText.setText(str);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 2, 0, 2);
                                    provText.setLayoutParams(params);
                                    provText.append(user.getCurriculum().getProvince());
                                    linearDistance.addView(provText);
                                }
                                if (user.getCurriculum().getMove().size()!=0) {
                                    ArrayList<Integer> mov = user.getCurriculum().getMove();
                                    TextView movText = new TextView(activity);
                                    String str = getResources().getString(R.string.willingness_buisness_trip);
                                    switch (mov.get(0)) {
                                        case 0:
                                            str = str + ": "+ getResources().getString(R.string.home_transfers);
                                            break;
                                        case 1:
                                            str = str + ": "+ getResources().getString(R.string.frequent_transfers);
                                            break;
                                        case 2:
                                            str = str + ": "+ getResources().getString(R.string.limited_transfers);
                                            break;
                                        case 3:
                                            str = str + ": "+ getResources().getString(R.string.no);
                                            break;
                                    }
                                    movText.setText(str);
                                    linearDistance.addView(movText);

                                    movText = new TextView(activity);
                                    str = getResources().getString(R.string.willingness_abroad_transfers);
                                    switch (mov.get(1)) {
                                        case 0:
                                            str = str + ": "+ getResources().getString(R.string.only_europe);
                                            break;
                                        case 1:
                                            str = str + ": "+ getResources().getString(R.string.non_european);
                                            break;
                                        case 2:
                                            str = str + ": "+ getResources().getString(R.string.no);
                                            break;
                                    }
                                    movText.setText(str);
                                    linearDistance.addView(movText);
                                }
                            }else {
                                place.setVisibility(View.GONE);
                                place1.setVisibility(View.GONE);
                                citizenship.setVisibility(View.GONE);
                                citizenship1.setVisibility(View.GONE);
                                cf.setVisibility(View.GONE);
                                cf1.setVisibility(View.GONE);
                                address.setVisibility(View.GONE);
                                address1.setVisibility(View.GONE);
                                licens.setVisibility(View.GONE);
                                licens1.setVisibility(View.GONE);
                                email.setVisibility(View.GONE);
                                email1.setVisibility(View.GONE);
                                cell.setVisibility(View.GONE);
                                cell1.setVisibility(View.GONE);
                                car.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Lettura fallita dal database users", databaseError.toException());
                }
            });
        }

        if (repeat&&user!=null) {
            scroll.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            linearGoal.removeAllViews();
            linearSkill.removeAllViews();
            linearExperiences.removeAllViews();
            linearEducation.removeAllViews();
            linearLanguage.removeAllViews();
            linearIt.removeAllViews();
            linearDistance.removeAllViews();

            if (running) {
                Glide.with(activity).load(user.getImageUrl()).fitCenter().centerCrop().into(image);
            }
            String s = user.getName().toUpperCase()+ " " +user.getSurname().toUpperCase();
            name.setText(s);
            s = (user.getDay()<10 ? "0"+user.getDay()+"/" : user.getDay()+"/") +
                    ((user.getMonth()+1)<10?"0"+(user.getMonth()+1)+"/" : (user.getMonth()+1)+"/") + (user.getYear());
            birthDate.setText(s);
            Calendar c = Calendar.getInstance();
            Calendar dateCalendar = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());
            long diffInMilli = c.getTimeInMillis() - dateCalendar.getTimeInMillis();
            diffInMilli = diffInMilli/1000/60/60/24/365;
            age.setText(String.valueOf(diffInMilli));
            if (user.getCurriculum()!=null) {
                //PERSONAL INFORMATION
                if (user.getCurriculum().getPlace()!=null) {
                    place.setText(user.getCurriculum().getPlace());
                }else {
                    place.setVisibility(View.GONE);
                    place1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getCitizenship()!=null) {
                    citizenship.setText(user.getCurriculum().getCitizenship());
                }else {
                    citizenship.setVisibility(View.GONE);
                    citizenship1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getCf()!=null) {
                    cf.setText(user.getCurriculum().getCf());
                }else {
                    cf.setVisibility(View.GONE);
                    cf1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getAddress()!=null) {
                    address.setText(user.getCurriculum().getAddress());
                }else {
                    address.setVisibility(View.GONE);
                    address1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getLicens()!=null) {
                    licens.setText(user.getCurriculum().getLicens());
                }else {
                    licens.setVisibility(View.GONE);
                    licens1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().isWithCar()!=null) {
                    if (user.getCurriculum().isWithCar()) {
                        car.setText(getResources().getString(R.string.with_car));
                    }else {
                        car.setText(getResources().getString(R.string.without_car));
                    }
                }else {
                    car.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getEmail()!=null) {
                    email.setText(user.getCurriculum().getEmail());
                }else {
                    email.setVisibility(View.GONE);
                    email1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getCellphone()!=null) {
                    email.setText(user.getCurriculum().getCellphone());
                }else {
                    cell.setVisibility(View.GONE);
                    cell1.setVisibility(View.GONE);
                }
                if (user.getCurriculum().getLinks()!=null) {
                    links.setText(user.getCurriculum().getLinks());
                }else {
                    links.setVisibility(View.GONE);
                }

                //PROFESSIONAL GOAL
                if (user.getCurriculum().getGoal()!=null) {
                    TextView goalText = new TextView(activity);
                    goalText.setText(user.getCurriculum().getGoal());
                    linearGoal.addView(goalText);
                }
                //SOFT SKILL
                if (user.getCurriculum().getSkills().size()!=0) {
                    ArrayList<Integer> sk = user.getCurriculum().getSkills();
                    TextView skillsText = new TextView(activity);
                    String str = getResources().getString(R.string.autonomy)+" ";
                    skillsText.setText(str);
                    str = sk.get(0) + "/10\n";
                    Spannable word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.self_confidence)+" ";
                    skillsText.append(str);
                    str = sk.get(1) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.stress)+" ";
                    skillsText.append(str);
                    str = sk.get(2) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.organize)+" ";
                    skillsText.append(str);
                    str = sk.get(3) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.accuracy)+" ";
                    skillsText.append(str);
                    str = sk.get(4) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.learn)+" ";
                    skillsText.append(str);
                    str = sk.get(5) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.goals)+" ";
                    skillsText.append(str);
                    str = sk.get(6) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.manage_info)+" ";
                    skillsText.append(str);
                    str = sk.get(7) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.initiative)+" ";
                    skillsText.append(str);
                    str = sk.get(8) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.comunication_skills)+" ";
                    skillsText.append(str);
                    str = sk.get(9) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.problem_solving)+" ";
                    skillsText.append(str);
                    str = sk.get(10) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.team_work)+" ";
                    skillsText.append(str);
                    str = sk.get(11) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    str =  getResources().getString(R.string.leadership)+" ";
                    skillsText.append(str);
                    str = sk.get(12) +"/10\n";
                    word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skillsText.append(word);
                    linearSkill.addView(skillsText);
                }

                //WORK EXPERIENCES
                if (user.getCurriculum().getExperiences().size()!=0) {
                    ArrayList<String> exp = user.getCurriculum().getExperiences();
                    for (int i=0; i<exp.size(); i++) {
                        TextView expText = new TextView(activity);
                        expText.setText(exp.get(i));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 30, 10);
                        expText.setLayoutParams(params);
                        linearExperiences.addView(expText);
                    }
                }

                //EDUCATION
                if (user.getCurriculum().getEducation().size()!=0) {
                    ArrayList<String> edu = user.getCurriculum().getEducation();
                    ArrayList<Integer> tipeEdu = user.getCurriculum().getTipeEducation();
                    for (int i=0; i<edu.size(); i++) {
                        TextView eduText = new TextView(activity);
                        if (tipeEdu.get(i)==0) {
                            String str = getResources().getString(R.string.school)+": ";
                            Spannable word = new SpannableString(str);
                            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            eduText.setText(word);
                        }else {
                            String str = getResources().getString(R.string.university)+": ";
                            Spannable word = new SpannableString(str);
                            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            eduText.setText(word);
                        }
                        eduText.append(edu.get(i));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 30, 10);
                        eduText.setLayoutParams(params);
                        linearEducation.addView(eduText);
                    }
                }

                //LANGUAGES SKILLS
                if (user.getCurriculum().getLanguagesNative()!=null) {
                    String langNative = user.getCurriculum().getLanguagesNative();
                    TextView langText = new TextView(activity);
                    String str = getResources().getString(R.string.native_language)+ ": ";
                    Spannable word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    langText.setText(word);
                    str = langNative;
                    langText.append(str);
                    linearLanguage.addView(langText);
                }
                if (user.getCurriculum().getLanguagesKnow().size()!=0) {
                    ArrayList<String> langKnow = user.getCurriculum().getLanguagesKnow();
                    ArrayList<ArrayList<String>> know = user.getCurriculum().getKnowledge();

                    for (int i=0; i<langKnow.size(); i++) {
                        if (i==0) {
                            TextView languText = new TextView(activity);
                            String str = getResources().getString(R.string.known_languages);
                            Spannable word = new SpannableString(str);
                            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            languText.setText(word);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(10, 5, 0, 10);
                            languText.setLayoutParams(params);
                            linearLanguage.addView(languText);
                        }
                        TextView langText = new TextView(activity);
                        String str = langKnow.get(i)+":\n"+getResources().getString(R.string.listening)+" "+ know.get(i).get(0)+"  "+
                                getResources().getString(R.string.reading)+" "+ know.get(i).get(1)+"\n"+
                                getResources().getString(R.string.comunication)+ " "+know.get(i).get(2)+"  "+
                                getResources().getString(R.string.writing)+ " "+know.get(i).get(3);
                        langText.setText(str);
                        float scale = getResources().getDisplayMetrics().density;
                        int pixels = (int) (400 * scale + 0.5f);
                        langText.setMaxWidth(pixels);
                        linearLanguage.addView(langText);
                    }
                }

                //IT SKILLS
                if (user.getCurriculum().getIt().size()!=0) {
                    ArrayList<String> it = user.getCurriculum().getIt();
                    for (int i=0; i<it.size(); i++) {
                        TextView itText = new TextView(activity);
                        itText.setText(it.get(i));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 30, 10);
                        itText.setLayoutParams(params);
                        linearIt.addView(itText);
                    }
                }

                //DISTANCE FROM WORKPLACE
                if (user.getCurriculum().getProvince()!=null) {
                    TextView provText = new TextView(activity);
                    String str = getResources().getString(R.string.favorite_provinces)+": ";
                    Spannable word = new SpannableString(str);
                    word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    provText.setText(str);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 2, 0, 2);
                    provText.setLayoutParams(params);
                    provText.append(user.getCurriculum().getProvince());
                    linearDistance.addView(provText);
                }
                if (user.getCurriculum().getMove().size()!=0) {
                    ArrayList<Integer> mov = user.getCurriculum().getMove();
                    TextView movText = new TextView(activity);
                    String str = getResources().getString(R.string.willingness_buisness_trip);
                    switch (mov.get(0)) {
                        case 0:
                            str = str + ": "+ getResources().getString(R.string.home_transfers);
                            break;
                        case 1:
                            str = str + ": "+ getResources().getString(R.string.frequent_transfers);
                            break;
                        case 2:
                            str = str + ": "+ getResources().getString(R.string.limited_transfers);
                            break;
                        case 3:
                            str = str + ": "+ getResources().getString(R.string.no);
                            break;
                    }
                    movText.setText(str);
                    linearDistance.addView(movText);

                    movText = new TextView(activity);
                    str = getResources().getString(R.string.willingness_abroad_transfers);
                    switch (mov.get(1)) {
                        case 0:
                            str = str + ": "+ getResources().getString(R.string.only_europe);
                            break;
                        case 1:
                            str = str + ": "+ getResources().getString(R.string.non_european);
                            break;
                        case 2:
                            str = str + ": "+ getResources().getString(R.string.no);
                            break;
                    }
                    movText.setText(str);
                    linearDistance.addView(movText);
                }
            }else {
                place.setVisibility(View.GONE);
                place1.setVisibility(View.GONE);
                citizenship.setVisibility(View.GONE);
                citizenship1.setVisibility(View.GONE);
                cf.setVisibility(View.GONE);
                cf1.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                address1.setVisibility(View.GONE);
                licens.setVisibility(View.GONE);
                licens1.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                email1.setVisibility(View.GONE);
                cell.setVisibility(View.GONE);
                cell1.setVisibility(View.GONE);
                car.setVisibility(View.GONE);
            }
        }
        return view;
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.Button_personal:
                Intent intent = new Intent(activity, PersonalInfoActivity.class);
                intent.putExtra(userKEY, user);
                startActivity(intent);
                break;
            case R.id.Button_personal_info:
                builder.setMessage(R.string.personal_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_goal:
                intent = new Intent(activity, ProfessionalGoalActivity.class);
                intent.putExtra(goalKEY, user.getCurriculum().getGoal());
                startActivity(intent);
                break;
            case R.id.Button_goal_info:
                builder.setMessage(R.string.goal_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_skill:
                intent = new Intent(activity, SoftSkillActivity.class);
                intent.putExtra(skillKEY, user.getCurriculum().getSkills());
                startActivity(intent);
                break;
            case R.id.Button_skill_info:
                builder.setMessage(R.string.skill_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_experiences:
                intent = new Intent(activity, ExperiencesActivity.class);
                intent.putExtra(experiencesKEY, user.getCurriculum().getExperiences());
                startActivity(intent);
                break;
            case R.id.Button_experiences_info:
                builder.setMessage(R.string.experiences_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_education:
                intent = new Intent(activity, EducationActivity.class);
                intent.putExtra(educationKEY, user.getCurriculum().getEducation());
                intent.putExtra(tipeEducationKEY, user.getCurriculum().getTipeEducation());
                startActivity(intent);
                break;
            case R.id.Button_education_info:
                builder.setMessage(R.string.education_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_language:
                intent = new Intent(activity, LanguagesActivity.class);
                intent.putExtra(curriculumKEY, user.getCurriculum());
                startActivity(intent);
                break;
            case R.id.Button_language_info:
                builder.setMessage(R.string.language_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_it:
                intent = new Intent(activity, ItActivity.class);
                intent.putExtra(itKEY, user.getCurriculum().getIt());
                startActivity(intent);
                break;
            case R.id.Button_it_info:
                builder.setMessage(R.string.it_infos);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.Button_distance:
                intent = new Intent(activity, DistanceActivity.class);
                intent.putExtra(curriculumKEY, user.getCurriculum());
                startActivity(intent);
                break;
            case R.id.Button_distance_info:
                builder.setMessage(R.string.distance_infos);
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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10: {
                if (resultCode == Activity.RESULT_OK) {
                    user = (User) data.getSerializableExtra(userKEY);

                    String s = user.getName().toUpperCase()+ " " +user.getSurname().toUpperCase();
                    name.setText(s);
                    s = (user.getDay()<10 ? "0"+user.getDay()+"/" : user.getDay()+"/") +
                            ((user.getMonth()+1)<10?"0"+(user.getMonth()+1)+"/" : (user.getMonth()+1)+"/") + (user.getYear());
                    birthDate.setText(s);
                    Calendar c = Calendar.getInstance();
                    Calendar dateCalendar = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());
                    long diffInMilli = c.getTimeInMillis() - dateCalendar.getTimeInMillis();
                    diffInMilli = diffInMilli/1000/60/60/24/365;
                    age.setText(String.valueOf(diffInMilli));
                    if (user.getCurriculum()!=null) {
                        if (user.getCurriculum().getPlace() != null) {
                            place.setVisibility(View.VISIBLE);
                            place1.setVisibility(View.VISIBLE);
                            place.setText(user.getCurriculum().getPlace());
                        } else {
                            place.setVisibility(View.GONE);
                            place1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getCitizenship() != null) {
                            citizenship.setVisibility(View.VISIBLE);
                            citizenship1.setVisibility(View.VISIBLE);
                            citizenship.setText(user.getCurriculum().getCitizenship());
                        } else {
                            citizenship.setVisibility(View.GONE);
                            citizenship1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getCf() != null) {
                            cf.setVisibility(View.VISIBLE);
                            cf1.setVisibility(View.VISIBLE);
                            cf.setText(user.getCurriculum().getCf());
                        } else {
                            cf.setVisibility(View.GONE);
                            cf1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getAddress() != null) {
                            address.setVisibility(View.VISIBLE);
                            address1.setVisibility(View.VISIBLE);
                            address.setText(user.getCurriculum().getAddress());
                        } else {
                            address.setVisibility(View.GONE);
                            address1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getLicens() != null) {
                            licens.setVisibility(View.VISIBLE);
                            licens1.setVisibility(View.VISIBLE);
                            licens.setText(user.getCurriculum().getLicens());
                        } else {
                            licens.setVisibility(View.GONE);
                            licens1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().isWithCar() != null) {
                            car.setVisibility(View.VISIBLE);
                            if (user.getCurriculum().isWithCar()) {
                                car.setText(activity.getResources().getString(R.string.with_car));
                            } else {
                                car.setText(activity.getResources().getString(R.string.without_car));
                            }
                        } else {
                            car.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getEmail() != null) {
                            email.setVisibility(View.VISIBLE);
                            email1.setVisibility(View.VISIBLE);
                            email.setText(user.getCurriculum().getEmail());
                        } else {
                            email.setVisibility(View.GONE);
                            email1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getCellphone() != null) {
                            cell.setVisibility(View.VISIBLE);
                            cell1.setVisibility(View.VISIBLE);
                            cell.setText(user.getCurriculum().getCellphone());
                        } else {
                            cell.setVisibility(View.GONE);
                            cell1.setVisibility(View.GONE);
                        }
                        if (user.getCurriculum().getLinks()!=null) {
                            links.setVisibility(View.VISIBLE);
                            links.setText(user.getCurriculum().getLinks());
                        }else {
                            links.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            }
            case 11:
                if (resultCode == Activity.RESULT_OK) {
                    String strGoal = data.getStringExtra(goalKEY);
                    if (strGoal != null) {
                        linearGoal.removeAllViews();
                        TextView goalText = new TextView(activity);
                        goalText.setText(strGoal);
                        linearGoal.addView(goalText);
                    }
                }
                break;
        }
    }*/

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(activity, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(activity, imageUri)) {
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
            final String id = u.getUid();
            final StorageReference fileRef = mStorage.child(id+".jpg");
            fileRef.putFile(result.getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String s = uri.toString();
                            mDatabase.child("users").child(id).child("imageUrl").setValue(s);
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
        CropImage.activity(imageUri).start(activity);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(activity, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}
