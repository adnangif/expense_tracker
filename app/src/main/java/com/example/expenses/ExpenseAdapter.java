package com.example.expenses;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private ArrayList<Expense> expenseArrayList=new ArrayList<>();

    public void setExpenseArrayList(ArrayList<Expense> expenseArrayList) {
        this.expenseArrayList = expenseArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(expenseArrayList.get(position).title);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),ExpandExpenseList.class);
                i.putExtra("Expense",expenseArrayList.get(holder.getAdapterPosition()));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        MaterialCardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.expense_title);
            cardView = itemView.findViewById(R.id.expense_card);

        }
    }
}
