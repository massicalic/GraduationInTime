package com.example.graduationintime;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterPassedExam extends ArrayAdapter<String> {

    private final Activity context;
    private final List<Exam> items;
    private final List<String> items2;

    public CustomAdapterPassedExam(Activity context, List<Exam> items, List<String> items2) {
        super(context, R.layout.passed_exam_list_item, items2);
        this.context = context;
        this.items= items;
        this.items2= items2;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.passed_exam_list_item, null, true);

        TextView itemName = (TextView) rowView.findViewById(R.id.item_name);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView mark = (TextView) rowView.findViewById(R.id.mark);

        String s = (items.get(position).getDay()<10 ? "0"+items.get(position).getDay()+"/" : items.get(position).getDay()+"/") +
                ((items.get(position).getMonth()+1)<10?"0"+(items.get(position).getMonth()+1)+"/" : (items.get(position).getMonth()+1)+"/")
                + (items.get(position).getYear());
        itemName.setText(items.get(position).getName());
        date.setText(s);
        mark.setText(items.get(position).getMark());

        return rowView;
    }
}
