package com.example.expenses;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ExpandedItemAdapter extends RecyclerView.Adapter<ExpandedItemAdapter.ViewHolder> {

    private Expense e;
    private ExpandExpenseModelView model;
    public void setE(Expense e) {
        this.e = e;
    }

    public void setModel(ExpandExpenseModelView model) {
        this.model = model;
    }

    public Expense getE() {
        return e;
    }

    public ExpandedItemAdapter(Expense e) {
        this.e = e;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expanded_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cost.setText(e.mini_transactions.get(position));
        holder.details.setText(e.transaction_cause.get(position));
        holder.time_of_cost.setText(e.time_of_creation.get(position));


        holder.details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                e.transaction_cause.set(holder.getAdapterPosition(),holder.details.getText().toString().trim());
                model.isChanged.setValue(1);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        holder.cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                e.mini_transactions.set(holder.getAdapterPosition(),holder.cost.getText().toString().trim());
                model.isChanged.setValue(1);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
    public void remove(int position){
        e.transaction_cause.remove(position);
        e.mini_transactions.remove(position);
        e.time_of_creation.remove(position);

        notifyItemRemoved(position);
        model.isChanged.setValue(1);
    }


    @Override
    public int getItemCount() {
        return e.transaction_cause.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView time_of_cost;
        EditText details,cost;
        MaterialCardView card_mini;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_of_cost = itemView.findViewById(R.id.time_of_cost);
            details = itemView.findViewById(R.id.details);
            cost = itemView.findViewById(R.id.cost);
            card_mini = itemView.findViewById(R.id.card_mini);
        }

    }
}
