package com.example.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ExpenseDatabase expenseDatabase;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab =findViewById(R.id.add_new);
        expenseDatabase = ExpenseDatabase.getInstance(MainActivity.this);
        recyclerView = findViewById(R.id.recycler_view);
        ExpenseAdapter expenseAdapter = new ExpenseAdapter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        expenseDatabase.expenseDao().insertOne(new Expense("New Expense"));
                    }
                });
            }
        });

        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

        expenseDatabase.expenseDao().getAll().observe(MainActivity.this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseAdapter.setExpenseArrayList((ArrayList<Expense>) expenses);
            }
        });
    }
}