package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class SoftSkillActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup radioAutonomy, radioSelfConfidence, radioStress, radioOrganize, radioAccuracy, radioLearn, radioGoals,
            radioManageInfo, radioInitiative, radioComunicationSkills, radioProblemSolving, radioTeamWork, radioLeadership;
    private RadioButton buttonAutonomy1, buttonAutonomy2, buttonAutonomy3, buttonAutonomy4, buttonAutonomy5, buttonAutonomy6,
            buttonAutonomy7, buttonAutonomy8, buttonAutonomy9, buttonAutonomy10, buttonSelfConfidence1, buttonSelfConfidence2,
            buttonSelfConfidence3, buttonSelfConfidence4, buttonSelfConfidence5, buttonSelfConfidence6, buttonSelfConfidence7,
            buttonSelfConfidence8, buttonSelfConfidence9, buttonSelfConfidence10, buttonStress1, buttonStress2, buttonStress3,
            buttonStress4, buttonStress5, buttonStress6, buttonStress7, buttonStress8, buttonStress9, buttonStress10,
            buttonOrganize1, buttonOrganize2, buttonOrganize3, buttonOrganize4, buttonOrganize5, buttonOrganize6, buttonOrganize7,
            buttonOrganize8, buttonOrganize9, buttonOrganize10, buttonAccuracy1, buttonAccuracy2, buttonAccuracy3, buttonAccuracy4,
            buttonAccuracy5, buttonAccuracy6, buttonAccuracy7, buttonAccuracy8, buttonAccuracy9, buttonAccuracy10, buttonLearn1,
            buttonLearn2, buttonLearn3, buttonLearn4, buttonLearn5, buttonLearn6, buttonLearn7, buttonLearn8, buttonLearn9, buttonLearn10,
            buttonGoals1, buttonGoals2, buttonGoals3, buttonGoals4, buttonGoals5, buttonGoals6, buttonGoals7, buttonGoals8,
            buttonGoals9, buttonGoals10, buttonManageInfo1, buttonManageInfo2, buttonManageInfo3, buttonManageInfo4, buttonManageInfo5,
            buttonManageInfo6, buttonManageInfo7, buttonManageInfo8, buttonManageInfo9, buttonManageInfo10, buttonInitiative1,
            buttonInitiative2, buttonInitiative3, buttonInitiative4, buttonInitiative5, buttonInitiative6, buttonInitiative7,
            buttonInitiative8, buttonInitiative9, buttonInitiative10, buttonComunicationSkills1, buttonComunicationSkills2,
            buttonComunicationSkills3, buttonComunicationSkills4, buttonComunicationSkills5, buttonComunicationSkills6,
            buttonComunicationSkills7, buttonComunicationSkills8, buttonComunicationSkills9, buttonComunicationSkills10,
            buttonProblemSolving1, buttonProblemSolving2, buttonProblemSolving3, buttonProblemSolving4, buttonProblemSolving5,
            buttonProblemSolving6, buttonProblemSolving7, buttonProblemSolving8, buttonProblemSolving9, buttonProblemSolving10,
            buttonTeamWork1, buttonTeamWork2, buttonTeamWork3, buttonTeamWork4, buttonTeamWork5, buttonTeamWork6, buttonTeamWork7,
            buttonTeamWork8, buttonTeamWork9, buttonTeamWork10, buttonLeadership1, buttonLeadership2, buttonLeadership3,
            buttonLeadership4, buttonLeadership5, buttonLeadership6, buttonLeadership7, buttonLeadership8, buttonLeadership9,
            buttonLeadership10;
    private DatabaseReference mDatabase;
    private String mUserId;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private ArrayList<Integer> skills;

    private static final String skillKEY = "skill_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_skill);

        skills = getIntent().getIntegerArrayListExtra(skillKEY);

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        builder = new AlertDialog.Builder(this);

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        radioAccuracy = findViewById(R.id.radio_accuracy);
        radioAutonomy = findViewById(R.id.radio_autonomy);
        radioComunicationSkills = findViewById(R.id.radio_comunication_skills);
        radioGoals = findViewById(R.id.radio_goals);
        radioInitiative = findViewById(R.id.radio_initiative);
        radioLeadership = findViewById(R.id.radio_leadership);
        radioLearn = findViewById(R.id.radio_learn);
        radioManageInfo = findViewById(R.id.radio_manage_info);
        radioOrganize = findViewById(R.id.radio_organize);
        radioProblemSolving = findViewById(R.id.radio_problem_solving);
        radioSelfConfidence = findViewById(R.id.radio_self_confidence);
        radioStress = findViewById(R.id.radio_stress);
        radioTeamWork = findViewById(R.id.radio_team_work);

        buttonAccuracy1 = findViewById(R.id.radioButtonAccuracy1);
        buttonAccuracy2 = findViewById(R.id.radioButtonAccuracy2);
        buttonAccuracy3 = findViewById(R.id.radioButtonAccuracy3);
        buttonAccuracy4 = findViewById(R.id.radioButtonAccuracy4);
        buttonAccuracy5 = findViewById(R.id.radioButtonAccuracy5);
        buttonAccuracy6 = findViewById(R.id.radioButtonAccuracy6);
        buttonAccuracy7 = findViewById(R.id.radioButtonAccuracy7);
        buttonAccuracy8 = findViewById(R.id.radioButtonAccuracy8);
        buttonAccuracy9 = findViewById(R.id.radioButtonAccuracy9);
        buttonAccuracy10 = findViewById(R.id.radioButtonAccuracy10);
        buttonAutonomy1 = findViewById(R.id.radioButtonAutonomy1);
        buttonAutonomy2 = findViewById(R.id.radioButtonAutonomy2);
        buttonAutonomy3 = findViewById(R.id.radioButtonAutonomy3);
        buttonAutonomy4 = findViewById(R.id.radioButtonAutonomy4);
        buttonAutonomy5 = findViewById(R.id.radioButtonAutonomy5);
        buttonAutonomy6 = findViewById(R.id.radioButtonAutonomy6);
        buttonAutonomy7 = findViewById(R.id.radioButtonAutonomy7);
        buttonAutonomy8 = findViewById(R.id.radioButtonAutonomy8);
        buttonAutonomy9 = findViewById(R.id.radioButtonAutonomy9);
        buttonAutonomy10 = findViewById(R.id.radioButtonAutonomy10);
        buttonComunicationSkills1 = findViewById(R.id.radioButtonComunicationSkills1);
        buttonComunicationSkills2 = findViewById(R.id.radioButtonComunicationSkills2);
        buttonComunicationSkills3 = findViewById(R.id.radioButtonComunicationSkills3);
        buttonComunicationSkills4 = findViewById(R.id.radioButtonComunicationSkills4);
        buttonComunicationSkills5 = findViewById(R.id.radioButtonComunicationSkills5);
        buttonComunicationSkills6 = findViewById(R.id.radioButtonComunicationSkills6);
        buttonComunicationSkills7 = findViewById(R.id.radioButtonComunicationSkills7);
        buttonComunicationSkills8 = findViewById(R.id.radioButtonComunicationSkills8);
        buttonComunicationSkills9 = findViewById(R.id.radioButtonComunicationSkills9);
        buttonComunicationSkills10 = findViewById(R.id.radioButtonComunicationSkills10);
        buttonGoals1 = findViewById(R.id.radioButtonGoals1);
        buttonGoals2 = findViewById(R.id.radioButtonGoals2);
        buttonGoals3 = findViewById(R.id.radioButtonGoals3);
        buttonGoals4 = findViewById(R.id.radioButtonGoals4);
        buttonGoals5 = findViewById(R.id.radioButtonGoals5);
        buttonGoals6 = findViewById(R.id.radioButtonGoals6);
        buttonGoals7 = findViewById(R.id.radioButtonGoals7);
        buttonGoals8 = findViewById(R.id.radioButtonGoals8);
        buttonGoals9 = findViewById(R.id.radioButtonGoals9);
        buttonGoals10 = findViewById(R.id.radioButtonGoals10);
        buttonInitiative1 = findViewById(R.id.radioButtonInitiative1);
        buttonInitiative2 = findViewById(R.id.radioButtonInitiative2);
        buttonInitiative3 = findViewById(R.id.radioButtonInitiative3);
        buttonInitiative4 = findViewById(R.id.radioButtonInitiative4);
        buttonInitiative5 = findViewById(R.id.radioButtonInitiative5);
        buttonInitiative6 = findViewById(R.id.radioButtonInitiative6);
        buttonInitiative7 = findViewById(R.id.radioButtonInitiative7);
        buttonInitiative8 = findViewById(R.id.radioButtonInitiative8);
        buttonInitiative9 = findViewById(R.id.radioButtonInitiative9);
        buttonInitiative10 = findViewById(R.id.radioButtonInitiative10);
        buttonLeadership1 = findViewById(R.id.radioButtonLeadership1);
        buttonLeadership2 = findViewById(R.id.radioButtonLeadership2);
        buttonLeadership3 = findViewById(R.id.radioButtonLeadership3);
        buttonLeadership4 = findViewById(R.id.radioButtonLeadership4);
        buttonLeadership5 = findViewById(R.id.radioButtonLeadership5);
        buttonLeadership6 = findViewById(R.id.radioButtonLeadership6);
        buttonLeadership7 = findViewById(R.id.radioButtonLeadership7);
        buttonLeadership8 = findViewById(R.id.radioButtonLeadership8);
        buttonLeadership9 = findViewById(R.id.radioButtonLeadership9);
        buttonLeadership10 = findViewById(R.id.radioButtonLeadership10);
        buttonLearn1 = findViewById(R.id.radioButtonLearn1);
        buttonLearn2 = findViewById(R.id.radioButtonLearn2);
        buttonLearn3 = findViewById(R.id.radioButtonLearn3);
        buttonLearn4 = findViewById(R.id.radioButtonLearn4);
        buttonLearn5 = findViewById(R.id.radioButtonLearn5);
        buttonLearn6 = findViewById(R.id.radioButtonLearn6);
        buttonLearn7 = findViewById(R.id.radioButtonLearn7);
        buttonLearn8 = findViewById(R.id.radioButtonLearn8);
        buttonLearn9 = findViewById(R.id.radioButtonLearn9);
        buttonLearn10 = findViewById(R.id.radioButtonLearn10);
        buttonManageInfo1 = findViewById(R.id.radioButtonManageInfo1);
        buttonManageInfo2 = findViewById(R.id.radioButtonManageInfo2);
        buttonManageInfo3 = findViewById(R.id.radioButtonManageInfo3);
        buttonManageInfo4 = findViewById(R.id.radioButtonManageInfo4);
        buttonManageInfo5 = findViewById(R.id.radioButtonManageInfo5);
        buttonManageInfo6 = findViewById(R.id.radioButtonManageInfo6);
        buttonManageInfo7 = findViewById(R.id.radioButtonManageInfo7);
        buttonManageInfo8 = findViewById(R.id.radioButtonManageInfo8);
        buttonManageInfo9 = findViewById(R.id.radioButtonManageInfo9);
        buttonManageInfo10 = findViewById(R.id.radioButtonManageInfo10);
        buttonOrganize1 = findViewById(R.id.radioButtonOrganize1);
        buttonOrganize2 = findViewById(R.id.radioButtonOrganize2);
        buttonOrganize3 = findViewById(R.id.radioButtonOrganize3);
        buttonOrganize4 = findViewById(R.id.radioButtonOrganize4);
        buttonOrganize5 = findViewById(R.id.radioButtonOrganize5);
        buttonOrganize6 = findViewById(R.id.radioButtonOrganize6);
        buttonOrganize7 = findViewById(R.id.radioButtonOrganize7);
        buttonOrganize8 = findViewById(R.id.radioButtonOrganize8);
        buttonOrganize9 = findViewById(R.id.radioButtonOrganize9);
        buttonOrganize10 = findViewById(R.id.radioButtonOrganize10);
        buttonProblemSolving1 = findViewById(R.id.radioButtonProblemSolving1);
        buttonProblemSolving2 = findViewById(R.id.radioButtonProblemSolving2);
        buttonProblemSolving3 = findViewById(R.id.radioButtonProblemSolving3);
        buttonProblemSolving4 = findViewById(R.id.radioButtonProblemSolving4);
        buttonProblemSolving5 = findViewById(R.id.radioButtonProblemSolving5);
        buttonProblemSolving6 = findViewById(R.id.radioButtonProblemSolving6);
        buttonProblemSolving7 = findViewById(R.id.radioButtonProblemSolving7);
        buttonProblemSolving8 = findViewById(R.id.radioButtonProblemSolving8);
        buttonProblemSolving9 = findViewById(R.id.radioButtonProblemSolving9);
        buttonProblemSolving10 = findViewById(R.id.radioButtonProblemSolving10);
        buttonSelfConfidence1 = findViewById(R.id.radioButtonSelfConfidence1);
        buttonSelfConfidence2 = findViewById(R.id.radioButtonSelfConfidence2);
        buttonSelfConfidence3 = findViewById(R.id.radioButtonSelfConfidence3);
        buttonSelfConfidence4 = findViewById(R.id.radioButtonSelfConfidence4);
        buttonSelfConfidence5 = findViewById(R.id.radioButtonSelfConfidence5);
        buttonSelfConfidence6 = findViewById(R.id.radioButtonSelfConfidence6);
        buttonSelfConfidence7 = findViewById(R.id.radioButtonSelfConfidence7);
        buttonSelfConfidence8 = findViewById(R.id.radioButtonSelfConfidence8);
        buttonSelfConfidence9 = findViewById(R.id.radioButtonSelfConfidence9);
        buttonSelfConfidence10 = findViewById(R.id.radioButtonSelfConfidence10);
        buttonStress1 = findViewById(R.id.radioButtonStress1);
        buttonStress2 = findViewById(R.id.radioButtonStress2);
        buttonStress3 = findViewById(R.id.radioButtonStress3);
        buttonStress4 = findViewById(R.id.radioButtonStress4);
        buttonStress5 = findViewById(R.id.radioButtonStress5);
        buttonStress6 = findViewById(R.id.radioButtonStress6);
        buttonStress7 = findViewById(R.id.radioButtonStress7);
        buttonStress8 = findViewById(R.id.radioButtonStress8);
        buttonStress9 = findViewById(R.id.radioButtonStress9);
        buttonStress10 = findViewById(R.id.radioButtonStress10);
        buttonTeamWork1 = findViewById(R.id.radioButtonTeamWork1);
        buttonTeamWork2 = findViewById(R.id.radioButtonTeamWork2);
        buttonTeamWork3 = findViewById(R.id.radioButtonTeamWork3);
        buttonTeamWork4 = findViewById(R.id.radioButtonTeamWork4);
        buttonTeamWork5 = findViewById(R.id.radioButtonTeamWork5);
        buttonTeamWork6 = findViewById(R.id.radioButtonTeamWork6);
        buttonTeamWork7 = findViewById(R.id.radioButtonTeamWork7);
        buttonTeamWork8 = findViewById(R.id.radioButtonTeamWork8);
        buttonTeamWork9 = findViewById(R.id.radioButtonTeamWork9);
        buttonTeamWork10 = findViewById(R.id.radioButtonTeamWork10);

        if (skills.size()!=0) {
            for (int i=0; i<skills.size(); i++) {
                switch (i) {
                    case 0:
                        switch (skills.get(0)) {
                            case 1:
                                buttonAutonomy1.setChecked(true);
                                break;
                            case 2:
                                buttonAutonomy2.setChecked(true);
                                break;
                            case 3:
                                buttonAutonomy3.setChecked(true);
                                break;
                            case 4:
                                buttonAutonomy4.setChecked(true);
                                break;
                            case 5:
                                buttonAutonomy5.setChecked(true);
                                break;
                            case 6:
                                buttonAutonomy6.setChecked(true);
                                break;
                            case 7:
                                buttonAutonomy7.setChecked(true);
                                break;
                            case 8:
                                buttonAutonomy8.setChecked(true);
                                break;
                            case 9:
                                buttonAutonomy9.setChecked(true);
                                break;
                            case 10:
                                buttonAutonomy10.setChecked(true);
                                break;
                        }
                        break;
                    case 1:
                        switch (skills.get(1)) {
                            case 1:
                                buttonSelfConfidence1.setChecked(true);
                                break;
                            case 2:
                                buttonSelfConfidence2.setChecked(true);
                                break;
                            case 3:
                                buttonSelfConfidence3.setChecked(true);
                                break;
                            case 4:
                                buttonSelfConfidence4.setChecked(true);
                                break;
                            case 5:
                                buttonSelfConfidence5.setChecked(true);
                                break;
                            case 6:
                                buttonSelfConfidence6.setChecked(true);
                                break;
                            case 7:
                                buttonSelfConfidence7.setChecked(true);
                                break;
                            case 8:
                                buttonSelfConfidence8.setChecked(true);
                                break;
                            case 9:
                                buttonSelfConfidence9.setChecked(true);
                                break;
                            case 10:
                                buttonSelfConfidence10.setChecked(true);
                                break;
                        }
                        break;
                    case 2:
                        switch (skills.get(2)) {
                            case 1:
                                buttonStress1.setChecked(true);
                                break;
                            case 2:
                                buttonStress2.setChecked(true);
                                break;
                            case 3:
                                buttonStress3.setChecked(true);
                                break;
                            case 4:
                                buttonStress4.setChecked(true);
                                break;
                            case 5:
                                buttonStress5.setChecked(true);
                                break;
                            case 6:
                                buttonStress6.setChecked(true);
                                break;
                            case 7:
                                buttonStress7.setChecked(true);
                                break;
                            case 8:
                                buttonStress8.setChecked(true);
                                break;
                            case 9:
                                buttonStress9.setChecked(true);
                                break;
                            case 10:
                                buttonStress10.setChecked(true);
                                break;
                        }
                        break;
                    case 3:
                        switch (skills.get(3)) {
                            case 1:
                                buttonOrganize1.setChecked(true);
                                break;
                            case 2:
                                buttonOrganize2.setChecked(true);
                                break;
                            case 3:
                                buttonOrganize3.setChecked(true);
                                break;
                            case 4:
                                buttonOrganize4.setChecked(true);
                                break;
                            case 5:
                                buttonOrganize5.setChecked(true);
                                break;
                            case 6:
                                buttonOrganize6.setChecked(true);
                                break;
                            case 7:
                                buttonOrganize7.setChecked(true);
                                break;
                            case 8:
                                buttonOrganize8.setChecked(true);
                                break;
                            case 9:
                                buttonOrganize9.setChecked(true);
                                break;
                            case 10:
                                buttonOrganize10.setChecked(true);
                                break;
                        }
                        break;
                    case 4:
                        switch (skills.get(4)) {
                            case 1:
                                buttonAccuracy1.setChecked(true);
                                break;
                            case 2:
                                buttonAccuracy2.setChecked(true);
                                break;
                            case 3:
                                buttonAccuracy3.setChecked(true);
                                break;
                            case 4:
                                buttonAccuracy4.setChecked(true);
                                break;
                            case 5:
                                buttonAccuracy5.setChecked(true);
                                break;
                            case 6:
                                buttonAccuracy6.setChecked(true);
                                break;
                            case 7:
                                buttonAccuracy7.setChecked(true);
                                break;
                            case 8:
                                buttonAccuracy8.setChecked(true);
                                break;
                            case 9:
                                buttonAccuracy9.setChecked(true);
                                break;
                            case 10:
                                buttonAccuracy10.setChecked(true);
                                break;
                        }
                        break;
                    case 5:
                        switch (skills.get(5)) {
                            case 1:
                                buttonLearn1.setChecked(true);
                                break;
                            case 2:
                                buttonLearn2.setChecked(true);
                                break;
                            case 3:
                                buttonLearn3.setChecked(true);
                                break;
                            case 4:
                                buttonLearn4.setChecked(true);
                                break;
                            case 5:
                                buttonLearn5.setChecked(true);
                                break;
                            case 6:
                                buttonLearn6.setChecked(true);
                                break;
                            case 7:
                                buttonLearn7.setChecked(true);
                                break;
                            case 8:
                                buttonLearn8.setChecked(true);
                                break;
                            case 9:
                                buttonLearn9.setChecked(true);
                                break;
                            case 10:
                                buttonLearn10.setChecked(true);
                                break;
                        }
                        break;
                    case 6:
                        switch (skills.get(6)) {
                            case 1:
                                buttonGoals1.setChecked(true);
                                break;
                            case 2:
                                buttonGoals2.setChecked(true);
                                break;
                            case 3:
                                buttonGoals3.setChecked(true);
                                break;
                            case 4:
                                buttonGoals4.setChecked(true);
                                break;
                            case 5:
                                buttonGoals5.setChecked(true);
                                break;
                            case 6:
                                buttonGoals6.setChecked(true);
                                break;
                            case 7:
                                buttonGoals7.setChecked(true);
                                break;
                            case 8:
                                buttonGoals8.setChecked(true);
                                break;
                            case 9:
                                buttonGoals9.setChecked(true);
                                break;
                            case 10:
                                buttonGoals10.setChecked(true);
                                break;
                        }
                        break;
                    case 7:
                        switch (skills.get(7)) {
                            case 1:
                                buttonManageInfo1.setChecked(true);
                                break;
                            case 2:
                                buttonManageInfo2.setChecked(true);
                                break;
                            case 3:
                                buttonManageInfo3.setChecked(true);
                                break;
                            case 4:
                                buttonManageInfo4.setChecked(true);
                                break;
                            case 5:
                                buttonManageInfo5.setChecked(true);
                                break;
                            case 6:
                                buttonManageInfo6.setChecked(true);
                                break;
                            case 7:
                                buttonManageInfo7.setChecked(true);
                                break;
                            case 8:
                                buttonManageInfo8.setChecked(true);
                                break;
                            case 9:
                                buttonManageInfo9.setChecked(true);
                                break;
                            case 10:
                                buttonManageInfo10.setChecked(true);
                                break;
                        }
                        break;
                    case 8:
                        switch (skills.get(8)) {
                            case 1:
                                buttonInitiative1.setChecked(true);
                                break;
                            case 2:
                                buttonInitiative2.setChecked(true);
                                break;
                            case 3:
                                buttonInitiative3.setChecked(true);
                                break;
                            case 4:
                                buttonInitiative4.setChecked(true);
                                break;
                            case 5:
                                buttonInitiative5.setChecked(true);
                                break;
                            case 6:
                                buttonInitiative6.setChecked(true);
                                break;
                            case 7:
                                buttonInitiative7.setChecked(true);
                                break;
                            case 8:
                                buttonInitiative8.setChecked(true);
                                break;
                            case 9:
                                buttonInitiative9.setChecked(true);
                                break;
                            case 10:
                                buttonInitiative10.setChecked(true);
                                break;
                        }
                        break;
                    case 9:
                        switch (skills.get(9)) {
                            case 1:
                                buttonComunicationSkills1.setChecked(true);
                                break;
                            case 2:
                                buttonComunicationSkills2.setChecked(true);
                                break;
                            case 3:
                                buttonComunicationSkills3.setChecked(true);
                                break;
                            case 4:
                                buttonComunicationSkills4.setChecked(true);
                                break;
                            case 5:
                                buttonComunicationSkills5.setChecked(true);
                                break;
                            case 6:
                                buttonComunicationSkills6.setChecked(true);
                                break;
                            case 7:
                                buttonComunicationSkills7.setChecked(true);
                                break;
                            case 8:
                                buttonComunicationSkills8.setChecked(true);
                                break;
                            case 9:
                                buttonComunicationSkills9.setChecked(true);
                                break;
                            case 10:
                                buttonComunicationSkills10.setChecked(true);
                                break;
                        }
                        break;
                    case 10:
                        switch (skills.get(10)) {
                            case 1:
                                buttonProblemSolving1.setChecked(true);
                                break;
                            case 2:
                                buttonProblemSolving2.setChecked(true);
                                break;
                            case 3:
                                buttonProblemSolving3.setChecked(true);
                                break;
                            case 4:
                                buttonProblemSolving4.setChecked(true);
                                break;
                            case 5:
                                buttonProblemSolving5.setChecked(true);
                                break;
                            case 6:
                                buttonProblemSolving6.setChecked(true);
                                break;
                            case 7:
                                buttonProblemSolving7.setChecked(true);
                                break;
                            case 8:
                                buttonProblemSolving8.setChecked(true);
                                break;
                            case 9:
                                buttonProblemSolving9.setChecked(true);
                                break;
                            case 10:
                                buttonProblemSolving10.setChecked(true);
                                break;
                        }
                        break;
                    case 11:
                        switch (skills.get(11)) {
                            case 1:
                                buttonTeamWork1.setChecked(true);
                                break;
                            case 2:
                                buttonTeamWork2.setChecked(true);
                                break;
                            case 3:
                                buttonTeamWork3.setChecked(true);
                                break;
                            case 4:
                                buttonTeamWork4.setChecked(true);
                                break;
                            case 5:
                                buttonTeamWork5.setChecked(true);
                                break;
                            case 6:
                                buttonTeamWork6.setChecked(true);
                                break;
                            case 7:
                                buttonTeamWork7.setChecked(true);
                                break;
                            case 8:
                                buttonTeamWork8.setChecked(true);
                                break;
                            case 9:
                                buttonTeamWork9.setChecked(true);
                                break;
                            case 10:
                                buttonTeamWork10.setChecked(true);
                                break;
                        }
                        break;
                    case 12:
                        switch (skills.get(12)) {
                            case 1:
                                buttonLeadership1.setChecked(true);
                                break;
                            case 2:
                                buttonLeadership2.setChecked(true);
                                break;
                            case 3:
                                buttonLeadership3.setChecked(true);
                                break;
                            case 4:
                                buttonLeadership4.setChecked(true);
                                break;
                            case 5:
                                buttonLeadership5.setChecked(true);
                                break;
                            case 6:
                                buttonLeadership6.setChecked(true);
                                break;
                            case 7:
                                buttonLeadership7.setChecked(true);
                                break;
                            case 8:
                                buttonLeadership8.setChecked(true);
                                break;
                            case 9:
                                buttonLeadership9.setChecked(true);
                                break;
                            case 10:
                                buttonLeadership10.setChecked(true);
                                break;
                        }
                        break;
                }
            }
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
                if (radioAccuracy.getCheckedRadioButtonId() == -1 || radioTeamWork.getCheckedRadioButtonId() == -1 ||
                        radioStress.getCheckedRadioButtonId() == -1 || radioSelfConfidence.getCheckedRadioButtonId() == -1 ||
                        radioProblemSolving.getCheckedRadioButtonId() == -1 || radioOrganize.getCheckedRadioButtonId() == -1 ||
                        radioManageInfo.getCheckedRadioButtonId() == -1 || radioLearn.getCheckedRadioButtonId() == -1 ||
                        radioLeadership.getCheckedRadioButtonId() == -1 || radioInitiative.getCheckedRadioButtonId() == -1 ||
                        radioGoals.getCheckedRadioButtonId() == -1 || radioComunicationSkills.getCheckedRadioButtonId() == -1 ||
                        radioAutonomy.getCheckedRadioButtonId() == -1) {

                    builder.setMessage(R.string.notChecked);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }else {
                    skills.clear();
                    int checked = radioAutonomy.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonAutonomy1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonAutonomy2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonAutonomy3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonAutonomy4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonAutonomy5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonAutonomy6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonAutonomy7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonAutonomy8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonAutonomy9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonAutonomy10:
                            skills.add(10);
                            break;
                    }
                    checked = radioSelfConfidence.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonSelfConfidence1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonSelfConfidence2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonSelfConfidence3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonSelfConfidence4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonSelfConfidence5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonSelfConfidence6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonSelfConfidence7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonSelfConfidence8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonSelfConfidence9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonSelfConfidence10:
                            skills.add(10);
                            break;
                    }
                    checked = radioStress.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonStress1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonStress2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonStress3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonStress4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonStress5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonStress6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonStress7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonStress8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonStress9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonStress10:
                            skills.add(10);
                            break;
                    }
                    checked = radioOrganize.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonOrganize1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonOrganize2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonOrganize3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonOrganize4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonOrganize5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonOrganize6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonOrganize7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonOrganize8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonOrganize9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonOrganize10:
                            skills.add(10);
                            break;
                    }
                    checked = radioAccuracy.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonAccuracy1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonAccuracy2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonAccuracy3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonAccuracy4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonAccuracy5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonAccuracy6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonAccuracy7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonAccuracy8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonAccuracy9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonAccuracy10:
                            skills.add(10);
                            break;
                    }
                    checked = radioLearn.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonLearn1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonLearn2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonLearn3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonLearn4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonLearn5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonLearn6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonLearn7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonLearn8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonLearn9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonLearn10:
                            skills.add(10);
                            break;
                    }
                    checked = radioGoals.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonGoals1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonGoals2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonGoals3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonGoals4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonGoals5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonGoals6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonGoals7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonGoals8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonGoals9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonGoals10:
                            skills.add(10);
                            break;
                    }
                    checked = radioManageInfo.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonManageInfo1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonManageInfo2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonManageInfo3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonManageInfo4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonManageInfo5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonManageInfo6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonManageInfo7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonManageInfo8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonManageInfo9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonManageInfo10:
                            skills.add(10);
                            break;
                    }
                    checked = radioInitiative.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonInitiative1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonInitiative2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonInitiative3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonInitiative4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonInitiative5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonInitiative6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonInitiative7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonInitiative8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonInitiative9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonInitiative10:
                            skills.add(10);
                            break;
                    }
                    checked = radioComunicationSkills.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonComunicationSkills1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonComunicationSkills2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonComunicationSkills3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonComunicationSkills4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonComunicationSkills5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonComunicationSkills6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonComunicationSkills7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonComunicationSkills8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonComunicationSkills9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonComunicationSkills10:
                            skills.add(10);
                            break;
                    }
                    checked = radioProblemSolving.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonProblemSolving1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonProblemSolving2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonProblemSolving3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonProblemSolving4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonProblemSolving5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonProblemSolving6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonProblemSolving7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonProblemSolving8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonProblemSolving9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonProblemSolving10:
                            skills.add(10);
                            break;
                    }
                    checked = radioTeamWork.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonTeamWork1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonTeamWork2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonTeamWork3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonTeamWork4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonTeamWork5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonTeamWork6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonTeamWork7:
                            skills.add( 7);
                            break;
                        case R.id.radioButtonTeamWork8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonTeamWork9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonTeamWork10:
                            skills.add(10);
                            break;
                    }
                    checked = radioLeadership.getCheckedRadioButtonId();
                    switch (checked) {
                        case R.id.radioButtonLeadership1:
                            skills.add(1);
                            break;
                        case R.id.radioButtonLeadership2:
                            skills.add(2);
                            break;
                        case R.id.radioButtonLeadership3:
                            skills.add(3);
                            break;
                        case R.id.radioButtonLeadership4:
                            skills.add(4);
                            break;
                        case R.id.radioButtonLeadership5:
                            skills.add(5);
                            break;
                        case R.id.radioButtonLeadership6:
                            skills.add(6);
                            break;
                        case R.id.radioButtonLeadership7:
                            skills.add(7);
                            break;
                        case R.id.radioButtonLeadership8:
                            skills.add(8);
                            break;
                        case R.id.radioButtonLeadership9:
                            skills.add(9);
                            break;
                        case R.id.radioButtonLeadership10:
                            skills.add(10);
                            break;
                    }
                    mDatabase.child("users").child(mUserId).child("curriculum").child("skills").setValue(skills);
                    /*Intent resultIntent = new Intent();
                    resultIntent.putExtra(skillKEY, skills);
                    setResult(Activity.RESULT_OK, resultIntent);*/
                    finish();
                }
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
