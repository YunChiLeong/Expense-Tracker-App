package com.example.expensetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    Activity activity;
    private  ArrayList expense_id, expense_name, expense_category, expense_date, expense_amount, expense_note;

    CustomAdapter(Activity activity, Context context, ArrayList expense_id, ArrayList expense_name,
                  ArrayList expense_category, ArrayList expense_date, ArrayList expense_amount,
                  ArrayList expense_note){
        this.activity = activity;
        this.context = context;
        this.expense_id = expense_id;
        this.expense_name = expense_name;
        this.expense_category = expense_category;
        this.expense_date = expense_date;
        this.expense_amount = expense_amount;
        this.expense_note = expense_note;
    }

    // Card view for each expense entry
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Get strings from arrays and set textviews
        holder.idTV.setText(String.valueOf(expense_id.get(position)));
        holder.nameTV.setText(String.valueOf(expense_name.get(position)));
        holder.categoryTV.setText(String.valueOf(expense_category.get(position)));
        holder.dateTV.setText(String.valueOf(expense_date.get(position)));
        holder.amountTV.setText(String.valueOf(expense_amount.get(position)));
        holder.noteTV.setText(String.valueOf(expense_note.get(position)));
        // Set listener for all linearlayout rows
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                // transfer current data into Update activity
                intent.putExtra("id",String.valueOf(expense_id.get(position)));
                intent.putExtra("name",String.valueOf(expense_name.get(position)));
                intent.putExtra("category",String.valueOf(expense_category.get(position)));
                intent.putExtra("date",String.valueOf(expense_date.get(position)));
                intent.putExtra("amount",String.valueOf(expense_amount.get(position)));
                intent.putExtra("note",String.valueOf(expense_note.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expense_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTV, nameTV, categoryTV, dateTV, amountTV, noteTV;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.id_tv);
            nameTV = itemView.findViewById(R.id.name_tv);
            categoryTV = itemView.findViewById(R.id.category_tv);
            dateTV = itemView.findViewById(R.id.date_tv);
            amountTV = itemView.findViewById(R.id.amount_tv);
            noteTV = itemView.findViewById(R.id.note_tv);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
