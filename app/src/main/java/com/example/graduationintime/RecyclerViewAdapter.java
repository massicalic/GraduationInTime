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

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int [] tipes = {1,2,3,4};
    private User user;
    private Context context;

    private static final String arrayExamKEY = "arrayExam_key";
    private static final String arrayMarkKEY = "arrayMark_key";
    private static final String arrayDayKEY = "arrayDay_key";
    private static final String arrayMonthKEY = "arrayMonth_key";
    private static final String arrayYearKEY = "arrayYear_key";
    private static final String thesisKEY = "thesis_key";
    private static final String infoThesisKEY = "thesisInfo_key";

    public RecyclerViewAdapter ( User user, Context context){
        this.user = user;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tipes.length;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {
            return 1;
        }else if (position % 3 == 0) {
            return 4;
        } else if (position % 2 == 0) {
            return 3;
        } else {
            return 2;
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
                View viewFOUR = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_thesis, parent, false);
                ThesisViewHolder rowFOUR = new ThesisViewHolder(viewFOUR);
                return rowFOUR;
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
                ((ExamViewHolder)holder).exam.setText("   ANALISI");
                ((ExamViewHolder)holder).showList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, RacoListActivity.class);
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
                        sum = sum + (user.getExams().get(i).getMark().equals("30L") ? 33 : Integer.parseInt( user.getExams().get(i).getMark()));
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
                        sum = sum + ((user.getExams().get(i).getMark().equals("30L") ? 33 : Integer.parseInt( user.getExams().get(i).getMark())) *
                        user.getExams().get(i).getCfu());
                        cfu = cfu + user.getExams().get(i).getCfu();
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

            progressBar.setMax(180);
            int passedExams = 0;
            for (int i = 0;i<user.getExams().size();i++) {
                if (user.getExams().get(i).getMark()!=null&&!user.getExams().get(i).getMark().equals("")) {
                    passedExams+=user.getExams().get(i).getCfu();
                }
            }
            progressBar.setProgress(passedExams);
            int cent = passedExams*100/180;

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

    public class ThesisViewHolder extends RecyclerView.ViewHolder{

        private TextView argument, info;

        public ThesisViewHolder (@NonNull View itemView) {
            super(itemView);

            argument = itemView.findViewById(R.id.argument);
            info = itemView.findViewById(R.id.info);
        }
    }
}
