package com.example.graduationintime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RegistrationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private YouFragment you = new YouFragment(); //fragment N 1
    private DetailsFragment det = new DetailsFragment(); //fragment N 2
    private TimeStudyFragment time = new TimeStudyFragment(); //fragment N 3
    private int Nfragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        addFragment(you);
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
                        break;
                    case 3:
                        addFragment(det);
                        Nfragment = 2;
                        toolbar.setTitle(R.string.title_reg_det);
                        break;
                }
                return true;
            case R.id.next:
                switch (Nfragment){
                    case 1:
                        addFragment(det);
                        toolbar.setTitle(R.string.title_reg_det);
                        Nfragment = 2;
                        break;
                    case 2:
                        addFragment(time);
                        toolbar.setTitle(R.string.title_reg_time);
                        Nfragment = 3;
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
                break;
            case 3:
                addFragment(det);
                Nfragment = 2;
                toolbar.setTitle(R.string.title_reg_det);
                toolbar.setNavigationIcon(R.drawable.ic_back);
                break;
        }
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ancor, fragment);
        fragmentTransaction.commit();
    }
}
