package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class LanguagesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;
    private LinearLayout linearLanguages;
    private RadioGroup radioNative, radioListening, radioReading, radioComunication, radioWriting;
    private RadioButton radioButtonNative, radioButtonNotNative, radioButtonListeningA1, radioButtonListeningA2, radioButtonListeningB1,
            radioButtonListeningB2, radioButtonListeningC1, radioButtonListeningC2, radioButtonReadingA1, radioButtonReadingA2,
            radioButtonReadingB1, radioButtonReadingB2, radioButtonReadingC1, radioButtonReadingC2, radioButtonComunicationA1,
            radioButtonComunicationA2, radioButtonComunicationB1, radioButtonComunicationB2, radioButtonComunicationC1,
            radioButtonComunicationC2, radioButtonWritingA1, radioButtonWritingA2, radioButtonWritingB1, radioButtonWritingB2,
            radioButtonWritingC1, radioButtonWritingC2;
    private TextView knowledge, listening, reading, comunication, writing;
    private String langNative;
    private ArrayList<String> langKnow;
    private ArrayList<ArrayList<String>> know;
    private DatabaseReference mDatabase;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private String mUserId;
    private boolean isEdit = false;
    private int pos;
    private LinearLayout linearLayout;
    private Curriculum curriculum;

    private static final String curriculumKEY = "curriculum_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);

        langNative = ((Curriculum) getIntent().getSerializableExtra(curriculumKEY)).getLanguagesNative();
        langKnow = ((Curriculum) getIntent().getSerializableExtra(curriculumKEY)).getLanguagesKnow();
        know = ((Curriculum) getIntent().getSerializableExtra(curriculumKEY)).getKnowledge();
        builder = new AlertDialog.Builder(this);

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        editText = findViewById(R.id.EditText);
        linearLanguages = findViewById(R.id.linearLayout_languages);
        radioNative = findViewById(R.id.RadioGroupNative);
        radioListening = findViewById(R.id.RadioGroupListening);
        radioReading = findViewById(R.id.RadioGroupReading);
        radioComunication = findViewById(R.id.RadioGroupComunication);
        radioWriting = findViewById(R.id.RadioGroupWriting);
        radioButtonNative = findViewById(R.id.radioButtonNative);
        radioButtonNotNative = findViewById(R.id.radioButtonNotNative);
        radioButtonListeningA2 = findViewById(R.id.radioButtonListeningA2);
        radioButtonListeningA1 = findViewById(R.id.radioButtonListeningA1);
        radioButtonListeningB1 = findViewById(R.id.radioButtonListeningB1);
        radioButtonListeningB2 = findViewById(R.id.radioButtonListeningB2);
        radioButtonListeningC1 = findViewById(R.id.radioButtonListeningC1);
        radioButtonListeningC2 = findViewById(R.id.radioButtonListeningC2);
        radioButtonReadingA2 = findViewById(R.id.radioButtonReadingA2);
        radioButtonReadingA1 = findViewById(R.id.radioButtonReadingA1);
        radioButtonReadingB1 = findViewById(R.id.radioButtonReadingB1);
        radioButtonReadingB2 = findViewById(R.id.radioButtonReadingB2);
        radioButtonReadingC1 = findViewById(R.id.radioButtonReadingC1);
        radioButtonReadingC2 = findViewById(R.id.radioButtonReadingC2);
        radioButtonComunicationA2 = findViewById(R.id.radioButtonComunicationA2);
        radioButtonComunicationA1 = findViewById(R.id.radioButtonComunicationA1);
        radioButtonComunicationB1 = findViewById(R.id.radioButtonComunicationB1);
        radioButtonComunicationB2 = findViewById(R.id.radioButtonComunicationB2);
        radioButtonComunicationC1 = findViewById(R.id.radioButtonComunicationC1);
        radioButtonComunicationC2 = findViewById(R.id.radioButtonComunicationC2);
        radioButtonWritingA2 = findViewById(R.id.radioButtonWritingA2);
        radioButtonWritingA1 = findViewById(R.id.radioButtonWritingA1);
        radioButtonWritingB1 = findViewById(R.id.radioButtonWritingB1);
        radioButtonWritingB2 = findViewById(R.id.radioButtonWritingB2);
        radioButtonWritingC1 = findViewById(R.id.radioButtonWritingC1);
        radioButtonWritingC2 = findViewById(R.id.radioButtonWritingC2);
        knowledge = findViewById(R.id.TextView_knowledge);
        listening = findViewById(R.id.TextView_listening);
        reading = findViewById(R.id.TextView_reading);
        comunication = findViewById(R.id.TextView_comunication);
        writing = findViewById(R.id.TextView_writing);

        radioNative.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonNative:
                        radioListening.setVisibility(View.GONE);
                        radioReading.setVisibility(View.GONE);
                        radioComunication.setVisibility(View.GONE);
                        radioWriting.setVisibility(View.GONE);
                        knowledge.setVisibility(View.GONE);
                        listening.setVisibility(View.GONE);
                        reading.setVisibility(View.GONE);
                        comunication.setVisibility(View.GONE);
                        writing.setVisibility(View.GONE);
                        break;
                    case R.id.radioButtonNotNative:
                        radioListening.setVisibility(View.VISIBLE);
                        radioReading.setVisibility(View.VISIBLE);
                        radioComunication.setVisibility(View.VISIBLE);
                        radioWriting.setVisibility(View.VISIBLE);
                        knowledge.setVisibility(View.VISIBLE);
                        listening.setVisibility(View.VISIBLE);
                        reading.setVisibility(View.VISIBLE);
                        comunication.setVisibility(View.VISIBLE);
                        writing.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        setView();
    }

    private void setView() {
        if (langNative!=null) {
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
            String str = getResources().getString(R.string.native_language)+ ": ";
            Spannable word = new SpannableString(str);
            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setText(word);
            str = langNative;
            text.append(str);
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
                            langNative = null;
                            mDatabase.child("users").child(mUserId).child("curriculum").child("languagesNative").setValue(null);
                            linearLanguages.removeAllViews();
                            setView();
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
                    editText.setText(langNative);
                    isEdit = true;
                    pos = -1;
                    radioButtonNative.setChecked(true);
                    radioNative.setClickable(false);
                }
            });
            linearLayout.addView(buttonEdit);
            linearLanguages.addView(linearLayout);
        }

        if (langKnow.size()>0) {
            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            linearLayout.setLayoutParams(params);
            TextView text = new TextView(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 0, 0, 30);
            text.setLayoutParams(params);
            float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (250 * scale + 0.5f);
            text.setWidth(pixels);
            String str = getResources().getString(R.string.known_languages)+ ": ";
            Spannable word = new SpannableString(str);
            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setText(word);
        }
        for (int i=0; i<langKnow.size(); i++) {
            final int j = i;
            TextView text = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 0, 0, 30);
            text.setLayoutParams(params);
            String str = langKnow.get(i)+" ";
            text.append(str);
            str = getResources().getString(R.string.listening)+" "+know.get(i).get(0)+"\n";
            text.append(str);
            str = getResources().getString(R.string.reading)+" "+know.get(i).get(1)+"\n";
            text.append(str);
            str = getResources().getString(R.string.comunication)+" "+know.get(i).get(2)+"\n";
            text.append(str);
            str = getResources().getString(R.string.writing)+" "+know.get(i).get(3);
            text.append(str);
            linearLayout.addView(text);

            Button button = new Button(this);
            float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (38 * scale + 0.5f);
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
                            langKnow.remove(j);
                            know.remove(j);
                            curriculum.setLanguagesKnow(langKnow);
                            curriculum.setKnowledge(know);
                            mDatabase.child("users").child(mUserId).child("curriculum").setValue(curriculum);
                            linearLanguages.removeAllViews();
                            setView();
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
                    radioNative.setClickable(true);
                    editText.setText(langKnow.get(j));
                    isEdit = true;
                    pos = j;
                    radioButtonNotNative.setChecked(true);
                    switch (know.get(j).get(0)) {
                        case "A2":
                            radioButtonListeningA2.setChecked(true);
                            break;
                        case "A1":
                            radioButtonListeningA1.setChecked(true);
                            break;
                        case "B2":
                            radioButtonListeningB2.setChecked(true);
                            break;
                        case "B1":
                            radioButtonListeningB1.setChecked(true);
                            break;
                        case "C2":
                            radioButtonListeningC2.setChecked(true);
                            break;
                        case "C1":
                            radioButtonListeningC1.setChecked(true);
                            break;
                    }
                    switch (know.get(j).get(1)) {
                        case "A2":
                            radioButtonReadingA2.setChecked(true);
                            break;
                        case "A1":
                            radioButtonReadingA1.setChecked(true);
                            break;
                        case "B2":
                            radioButtonReadingB2.setChecked(true);
                            break;
                        case "B1":
                            radioButtonReadingB1.setChecked(true);
                            break;
                        case "C2":
                            radioButtonReadingC2.setChecked(true);
                            break;
                        case "C1":
                            radioButtonReadingC1.setChecked(true);
                            break;
                    }
                    switch (know.get(j).get(2)) {
                        case "A2":
                            radioButtonComunicationA2.setChecked(true);
                            break;
                        case "A1":
                            radioButtonComunicationA1.setChecked(true);
                            break;
                        case "B2":
                            radioButtonComunicationB2.setChecked(true);
                            break;
                        case "B1":
                            radioButtonComunicationB1.setChecked(true);
                            break;
                        case "C2":
                            radioButtonComunicationC2.setChecked(true);
                            break;
                        case "C1":
                            radioButtonComunicationC1.setChecked(true);
                            break;
                    }
                    switch (know.get(j).get(3)) {
                        case "A2":
                            radioButtonWritingA2.setChecked(true);
                            break;
                        case "A1":
                            radioButtonWritingA1.setChecked(true);
                            break;
                        case "B2":
                            radioButtonWritingB2.setChecked(true);
                            break;
                        case "B1":
                            radioButtonWritingB1.setChecked(true);
                            break;
                        case "C2":
                            radioButtonWritingC2.setChecked(true);
                            break;
                        case "C1":
                            radioButtonWritingC1.setChecked(true);
                            break;
                    }
                }
            });
            linearLayout.addView(buttonEdit);
            linearLanguages.addView(linearLayout);
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
                    if (isEdit) {
                        if (pos == -1){
                            langNative = editText.getText().toString();
                            mDatabase.child("users").child(mUserId).child("curriculum").child("languagesNative").setValue(langNative);
                        }else {
                            langKnow.set(pos, editText.getText().toString());
                            ArrayList<String> temp = new ArrayList<>();
                            switch (radioListening.getCheckedRadioButtonId()) {
                                case R.id.radioButtonListeningA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonListeningA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonListeningB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonListeningB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonListeningC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonListeningC2:
                                    temp.add("C2");
                                    break;
                            }
                            switch (radioReading.getCheckedRadioButtonId()) {
                                case R.id.radioButtonReadingA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonReadingA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonReadingB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonReadingB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonReadingC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonReadingC2:
                                    temp.add("C2");
                                    break;
                            }
                            switch (radioComunication.getCheckedRadioButtonId()) {
                                case R.id.radioButtonReadingA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonComunicationA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonComunicationB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonComunicationB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonComunicationC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonComunicationC2:
                                    temp.add("C2");
                                    break;
                            }
                            switch (radioWriting.getCheckedRadioButtonId()) {
                                case R.id.radioButtonWritingA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonWritingA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonWritingB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonWritingB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonWritingC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonWritingC2:
                                    temp.add("C2");
                                    break;
                            }
                            know.add(temp);
                            curriculum.setLanguagesKnow(langKnow);
                            curriculum.setKnowledge(know);
                            mDatabase.child("users").child(mUserId).child("curriculum").setValue(curriculum);
                        }
                    }else {
                        if (radioNative.getCheckedRadioButtonId()==R.id.radioButtonNative) {
                            if (langNative!=null) {
                                langNative = langNative+", "+editText.getText().toString();
                                mDatabase.child("users").child(mUserId).child("curriculum").child("languagesNative").setValue(langNative);
                            }else {
                                langNative = editText.getText().toString();
                                mDatabase.child("users").child(mUserId).child("curriculum").child("languagesNative").setValue(langNative);
                            }
                        }else {
                            langKnow.add(editText.getText().toString());
                            ArrayList<String> temp = new ArrayList<>();
                            switch (radioListening.getCheckedRadioButtonId()) {
                                case R.id.radioButtonListeningA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonListeningA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonListeningB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonListeningB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonListeningC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonListeningC2:
                                    temp.add("C2");
                                    break;
                            }
                            switch (radioReading.getCheckedRadioButtonId()) {
                                case R.id.radioButtonReadingA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonReadingA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonReadingB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonReadingB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonReadingC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonReadingC2:
                                    temp.add("C2");
                                    break;
                            }
                            switch (radioComunication.getCheckedRadioButtonId()) {
                                case R.id.radioButtonReadingA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonComunicationA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonComunicationB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonComunicationB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonComunicationC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonComunicationC2:
                                    temp.add("C2");
                                    break;
                            }
                            switch (radioWriting.getCheckedRadioButtonId()) {
                                case R.id.radioButtonWritingA1:
                                    temp.add("A1");
                                    break;
                                case R.id.radioButtonWritingA2:
                                    temp.add("A2");
                                    break;
                                case R.id.radioButtonWritingB1:
                                    temp.add("B1");
                                    break;
                                case R.id.radioButtonWritingB2:
                                    temp.add("B2");
                                    break;
                                case R.id.radioButtonWritingC1:
                                    temp.add("C1");
                                    break;
                                case R.id.radioButtonWritingC2:
                                    temp.add("C2");
                                    break;
                            }
                            know.add(temp);
                            curriculum.setLanguagesKnow(langKnow);
                            curriculum.setKnowledge(know);
                            mDatabase.child("users").child(mUserId).child("curriculum").setValue(curriculum);
                        }
                    }
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
