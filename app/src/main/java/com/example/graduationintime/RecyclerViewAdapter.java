package com.example.graduationintime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int [] tipes = {1,2,3,4,5,6};
    private User user;
    private Context context;
    private ArrayList<String> raco;
    private ArrayList<String> weight;
    private ArrayList<Exam> examsList;

    private static final String nameExamsKEY = "nameExams_key";
    private static final String cfuExamsKEY = "cfuExams_key";
    private static final String teachingYearExamsKEY = "teachingYearExams_key";
    private static final String arrayExamKEY = "arrayExam_key";
    private static final String userKEY = "user_key";
    private static final String arrayMarkKEY = "arrayMark_key";
    private static final String racoKEY = "raco_key";
    private static final String weightKEY = "weight_key";
    private static final String arrayDayKEY = "arrayDay_key";
    private static final String arrayMonthKEY = "arrayMonth_key";
    private static final String arrayYearKEY = "arrayYear_key";
    private static final String thesisKEY = "thesis_key";
    private static final String infoThesisKEY = "thesisInfo_key";

    public RecyclerViewAdapter (User user, Context context, ArrayList<String> raco, ArrayList<String> weight, ArrayList<Exam> examsList){
        this.user = user;
        this.context = context;
        this.raco = raco;
        this.weight = weight;
        this.examsList = examsList;
    }

    @Override
    public int getItemCount() {
        return tipes.length;
    }

    @Override
    public int getItemViewType(int position) {
        if (position%6 == 0) {
            return 1;    //pos 1
        }else if (position%5 == 0) {
            return 5;   //pos 6
        }else if (position%4 == 0) {
            return 4;    // pos 5
        }else if (position%3 == 0) {
            return 6;     //pos 4
        } else if (position%2 == 0) {
            return 3;    //pos 3
        } else {
            return 2;    //pos 2
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_progress, parent, false);
                ProgressViewHolder rowONE = new ProgressViewHolder(viewONE);
                return rowONE;
            case 2:
                View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_exam, parent, false);
                ExamViewHolder rowTWO = new ExamViewHolder(viewTWO);
                return rowTWO;
            case 3:
                View viewTHREE = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_average, parent, false);
                AverageViewHolder rowTHREE = new AverageViewHolder(viewTHREE);
                return rowTHREE;
            case 4:
                View viewFOUR = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_elective_exams, parent, false);
                ElectiveExamsViewHolder rowFOUR = new ElectiveExamsViewHolder(viewFOUR);
                return rowFOUR;
            case 5:
                View viewFIVE = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_thesis, parent, false);
                ThesisViewHolder rowFIVE = new ThesisViewHolder(viewFIVE);
                return rowFIVE;
            case 6:
                View viewSIX = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_fundamental, parent, false);
                FundamentalExamsViewHolder rowSIX = new FundamentalExamsViewHolder(viewSIX);
                return rowSIX;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final String [] exams = new String[user.getExams().size()];
        final String [] marks = new String[user.getExams().size()];
        final int [] days = new int[user.getExams().size()];
        final int [] months = new int[user.getExams().size()];
        final int [] years = new int[user.getExams().size()];
        for (int i = 0;i<user.getExams().size();i++) {
            exams[i] = user.getExams().get(i).getName();
            if (user.getExams().get(i).getMark()!=null) {
                marks[i] = user.getExams().get(i).getMark();
            }else {
                marks[i] = "0";
            }
            if (user.getExams().get(i).getDay()!=0) {
                days[i] = user.getExams().get(i).getDay();
                months[i] = user.getExams().get(i).getMonth();
                years[i] = user.getExams().get(i).getYear();
            }else {
                days[i] = 0;
                months[i] = 0;
                years[i] = 0;
            }
        }

        int viewType = holder.getItemViewType();
        switch (viewType) {
            case 1:
                ((ProgressViewHolder)holder).addExam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AddPassedExamActivity.class);
                        intent.putExtra(arrayExamKEY, exams);
                        intent.putExtra(arrayMarkKEY, marks);
                        intent.putExtra(arrayDayKEY, days);
                        intent.putExtra(arrayMonthKEY, months);
                        intent.putExtra(arrayYearKEY, years);
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                if (raco.size()!=0) {
                    ((ExamViewHolder)holder).exam.setText(raco.get(0));
                }else {
                    ((ExamViewHolder)holder).exam.setText(context.getResources().getString(R.string.add_exams));
                }
                ((ExamViewHolder)holder).showList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, RacoListActivity.class);
                        intent.putExtra(userKEY, user);
                        intent.putExtra(racoKEY, raco);
                        intent.putExtra(weightKEY, weight);
                        context.startActivity(intent);
                    }
                });
                break;
            case 3:
                String ar = context.getResources().getString(R.string.arithmetic);
                double n = 0;
                double sum = 0;
                for (int i = 0; i<user.getExams().size(); i++) {
                    if (user.getExams().get(i).getMark()!=null) {
                        n++;
                        int a = 0;
                        if (!user.getExams().get(i).getMark().equals("30L") && !user.getExams().get(i).getMark().equals("superato")) {
                           a = Integer.parseInt( user.getExams().get(i).getMark());
                            sum = sum + a;
                        }
                        if (user.getExams().get(i).getMark().equals("30L")) {
                            a = 33;
                            sum = sum + a;
                        }
                        if (user.getExams().get(i).getMark().equals("superato")) {
                            a = 0;
                            sum = sum + a;
                        }
                    }
                }
                double averageAr = (double)Math.round(sum/n * 1000d) / 1000d;
                ar = ar + " " + (averageAr>30?"30L":averageAr);
                ((AverageViewHolder)holder).arithmetic.setText(ar);

                String we = context.getResources().getString(R.string.weighted);
                sum = 0;
                double cfu = 0;
                for (int i = 0; i<user.getExams().size(); i++) {
                    if (user.getExams().get(i).getMark()!=null) {
                        int a = 0;
                        if (!user.getExams().get(i).getMark().equals("30L") && !user.getExams().get(i).getMark().equals("superato")) {
                            a = Integer.parseInt( user.getExams().get(i).getMark());
                            sum = sum + a * user.getExams().get(i).getCfu();
                            cfu = cfu + user.getExams().get(i).getCfu();
                        }
                        if (user.getExams().get(i).getMark().equals("30L")) {
                            a = 33;
                            sum = sum + a * user.getExams().get(i).getCfu();
                            cfu = cfu + user.getExams().get(i).getCfu();
                        }
                    }
                }
                double averageWe = (double)Math.round(sum/cfu * 1000d) / 1000d;
                we = we + " " + (averageWe>30?"30L":averageWe);
                ((AverageViewHolder)holder).weighted.setText(we);

                ((AverageViewHolder)holder).passedExams.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PassedExamListActivity.class);
                        intent.putExtra(arrayExamKEY, exams);
                        intent.putExtra(arrayMarkKEY, marks);
                        intent.putExtra(arrayDayKEY, days);
                        intent.putExtra(arrayMonthKEY, months);
                        intent.putExtra(arrayYearKEY, years);
                        context.startActivity(intent);
                    }
                });
                break;
            case 4:
                ((ElectiveExamsViewHolder)holder).choose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ElectiveExamsActivity.class);
                        intent.putExtra(userKEY, user);
                        ArrayList<String> nameExams = new ArrayList<>();
                        for (int i=0; i<examsList.size(); i++) {
                            if (!examsList.get(i).isFundamental() && examsList.get(i).getCfu()==6){
                                nameExams.add(examsList.get(i).getName());
                            }
                        }
                        intent.putExtra(nameExamsKEY, nameExams);
                        context.startActivity(intent);
                    }
                });
                break;
            case 5:
                if (user.getThesis()!=null) {
                    if (!user.getThesis().equals("")) {
                        ((ThesisViewHolder)holder).argument.setText(user.getThesis());
                    }
                }
                ((ThesisViewHolder)holder).info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ThesisActivity.class);
                        if (user.getThesis()!=null) {
                            if (!user.getThesis().equals("")) {
                                intent.putExtra(thesisKEY, user.getThesis());
                                if (user.getThesisInfo()!=null) {
                                    if (!user.getThesisInfo().equals("")) {
                                        intent.putExtra(infoThesisKEY, user.getThesisInfo());
                                    }
                                }else {
                                    intent.putExtra(infoThesisKEY, context.getResources().getString(R.string.info_edit));
                                }
                            }
                        }else {
                            intent.putExtra(thesisKEY, context.getResources().getString(R.string.argument_thesis));
                            intent.putExtra(infoThesisKEY, context.getResources().getString(R.string.info_edit));
                        }
                        context.startActivity(intent);
                    }
                });
                break;
            case 6:
                ((FundamentalExamsViewHolder)holder).fundamental.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, FundamentalExamsActivity.class);
                        intent.putExtra(userKEY, user);
                        ArrayList<String> nameExams = new ArrayList<>();
                        for (int i=0; i<examsList.size(); i++) {
                            if (examsList.get(i).isFundamental()){
                                nameExams.add(examsList.get(i).getName());
                            }
                        }
                        intent.putExtra(nameExamsKEY, nameExams);
                        ArrayList<Integer> cfuExams = new ArrayList<>();
                        for (int i=0; i<examsList.size(); i++) {
                            if (examsList.get(i).isFundamental()){
                                cfuExams.add(examsList.get(i).getCfu());
                            }
                        }
                        intent.putExtra(cfuExamsKEY, cfuExams);
                        ArrayList<Integer> teachingYearExams = new ArrayList<>();
                        for (int i=0; i<examsList.size(); i++) {
                            if (examsList.get(i).isFundamental()){
                                teachingYearExams.add(examsList.get(i).getTeachingYear());
                            }
                        }
                        intent.putExtra(teachingYearExamsKEY, teachingYearExams);
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        private TextView perc, addExam;

        public ProgressViewHolder (@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progress_bar);
            perc = itemView.findViewById(R.id.TextView_perc);
            addExam = itemView.findViewById(R.id.add_exam);

            progressBar.setMax(156);
            int passedExams = 0;
            for (int i = 0;i<user.getExams().size();i++) {
                if (user.getExams().get(i).getMark()!=null&&!user.getExams().get(i).getMark().equals("")) {
                    passedExams+=user.getExams().get(i).getCfu();
                }
            }
            progressBar.setProgress(passedExams);
            int cent = passedExams*100/156;

            String text = cent+"%";
            perc.setText(text);
        }
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder{

        private TextView exam, showList;

        public ExamViewHolder (@NonNull View itemView) {
            super(itemView);

            exam = itemView.findViewById(R.id.exam);
            showList = itemView.findViewById(R.id.show_list);
        }
    }

    public class AverageViewHolder extends RecyclerView.ViewHolder{

        private TextView weighted, arithmetic, passedExams;

        public AverageViewHolder (@NonNull View itemView) {
            super(itemView);

            weighted = itemView.findViewById(R.id.weighted);
            arithmetic = itemView.findViewById(R.id.arithmetic);
            passedExams = itemView.findViewById(R.id.passed_exams);
        }
    }

    public class ElectiveExamsViewHolder extends RecyclerView.ViewHolder{

        private TextView choose;

        public ElectiveExamsViewHolder (@NonNull View itemView) {
            super(itemView);

            choose = itemView.findViewById(R.id.choose);
        }
    }

    public class FundamentalExamsViewHolder extends RecyclerView.ViewHolder{

        private TextView fundamental;

        public FundamentalExamsViewHolder (@NonNull View itemView) {
            super(itemView);

            fundamental = itemView.findViewById(R.id.fundamental);
        }
    }

    public class ThesisViewHolder extends RecyclerView.ViewHolder{

        private TextView argument, info;

        public ThesisViewHolder (@NonNull View itemView) {
            super(itemView);

            argument = itemView.findViewById(R.id.argument);
            info = itemView.findViewById(R.id.info);
        }
    }
}
