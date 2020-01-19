package com.example.graduationintime;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterNextExam extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemsName;
    private final List<String> itemsDate;

    public CustomAdapterNextExam(Activity context, List<String> itemsName, List<String> itemsDate) {
        super(context, R.layout.passed_exam_list_item, itemsDate);
        this.context = context;
        this.itemsName= itemsName;
        this.itemsDate= itemsDate;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.next_exam_list_item, null, true);

        TextView itemName = (TextView) rowView.findViewById(R.id.item_name);
        TextView date = (TextView) rowView.findViewById(R.id.date);

        itemName.setText(itemsName.get(position));
        date.setText(itemsDate.get(position));

        return rowView;
    }
}
