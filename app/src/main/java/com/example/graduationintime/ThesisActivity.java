package com.example.graduationintime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThesisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView argument, info;
    private String thesis, thesisInfo;

    private static final String thesisKEY = "thesis_key";
    private static final String infoThesisKEY = "thesisInfo_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thesis);

        toolbar = findViewById(R.id.Toolbar);
        argument = findViewById(R.id.TextView_argument);
        info = findViewById(R.id.TextView_info);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        thesis = getIntent().getStringExtra(thesisKEY);
        thesisInfo = getIntent().getStringExtra(infoThesisKEY);

        if (thesis != null) {
            if (!thesis.equals("")) {
                argument.setText(thesis);
            }
        }
        if (thesisInfo != null) {
            if (!thesisInfo.equals("")) {
                info.setText(thesisInfo);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                Intent intent = new Intent(ThesisActivity.this, ThesisEditActivity.class);
                intent.putExtra(thesisKEY, thesis);
                intent.putExtra(infoThesisKEY, thesisInfo);
                startActivityForResult(intent, 2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2: {
                if (resultCode == Activity.RESULT_OK) {
                    thesis = data.getStringExtra(thesisKEY);
                    thesisInfo = data.getStringExtra(infoThesisKEY);

                    if (thesis != null) {
                        if (!thesis.equals("")) {
                            argument.setText(thesis);
                        }
                    }
                    if (thesisInfo != null) {
                        if (!thesisInfo.equals("")) {
                            info.setText(thesisInfo);
                        }
                    }
                }
                break;
            }
        }
    }
}
