package com.example.graduationintime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RacoListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private User user;
    private ArrayList<String> raco;
    private ArrayList<String> weight;
    private int pos;

    private static final String racoKEY = "raco_key";
    private static final String weightKEY = "weight_key";
    private static final String examKEY = "exam_key";
    private static final String posListKEY = "posList_key";
    private static final String userKEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raco_list);

        raco = getIntent().getStringArrayListExtra(racoKEY);
        weight = getIntent().getStringArrayListExtra(weightKEY);
        user = (User) getIntent().getSerializableExtra(userKEY);

        for (int i=0; i<weight.size(); i++) {
            int teachingYear = 0;
            for (int j=0; j<user.getExams().size(); j++) {
                if (raco.get(i).equals(user.getExams().get(j).getName())) {
                    teachingYear = user.getExams().get(j).getTeachingYear();
                    break;
                }
            }

            int year = Integer.parseInt(weight.get(i).substring(weight.get(i).length() - 4));
            String monthStr = weight.get(i).substring(0, weight.get(i).length() - 6);
            int month = 0;
            switch (monthStr) {
                case "Gennaio":
                    month = 0;
                    break;
                case "Febbraio":
                    month = 1;
                    break;
                case "Giugno":
                    month = 5;
                    break;
                case "Luglio":
                    month = 6;
                    break;
                case "Settembre":
                    month = 8;
                    break;
                case "Dicembre":
                    month = 11;
                    break;
            }
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);

            while (mYear>year) {
                year++;
                if (month<mMonth) {
                    year++;
                }
            }

            weight.set(i, monthStr + ", " + year);
        }

        toolbar = findViewById(R.id.Toolbar);
        listView = findViewById(R.id.ListView);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i=0; i<raco.size(); i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("title", raco.get(i));
            datum.put("subtitle", weight.get(i));
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                new String[] {"title", "subtitle"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int j=0; j<user.getExams().size(); j++) {
                    if (raco.get(position).equals(user.getExams().get(j).getName()) && user.getExams().get(j).getMark()==null) {
                        pos = j;
                    }
                }
                Intent intent = new Intent(RacoListActivity.this, ExamEditActivity.class);
                intent.putExtra(examKEY, user.getExams().get(pos));
                intent.putExtra(posListKEY, pos);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
