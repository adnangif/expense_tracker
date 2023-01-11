package com.example.expenses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class ExpandExpenseList extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton btn_add,btn_save;
    MaterialButton delete_icon;
    EditText title_of_expense;
    ExpenseDatabase expenseDatabase;
    Expense expense;
    ExpandedItemAdapter expandedItemAdapter;
    ImageView done_icon,pending_save_icon;

    public ExpandExpenseModelView model;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_expense_list);

        delete_icon = findViewById(R.id.delete);
        expense = (Expense) getIntent().getSerializableExtra("Expense");
        recyclerView = findViewById(R.id.recycler_view);
        btn_add =findViewById(R.id.btn_floating);
        title_of_expense = findViewById(R.id.title_of_expense);
        expenseDatabase = ExpenseDatabase.getInstance(getApplicationContext());
        btn_save = findViewById(R.id.save);
        done_icon = findViewById(R.id.done_icon);
        pending_save_icon=findViewById(R.id.pending_save_icon);
        model = new ViewModelProvider(ExpandExpenseList.this).get(ExpandExpenseModelView.class);
        model.isChanged.setValue(0);





        title_of_expense.setText(expense.title);
        expandedItemAdapter = new ExpandedItemAdapter(expense);
        expandedItemAdapter.setModel(model);
        recyclerView.setAdapter(expandedItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expandedItemAdapter.remove(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expense = expandedItemAdapter.getE();

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        expenseDatabase.expenseDao().updateOne(expense);
                    }
                });
                model.isChanged.setValue(0);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                expense.transaction_cause.add("");
                expense.mini_transactions.add("");
                expense.time_of_creation.add(date);

                expandedItemAdapter.setE(expense);
                expandedItemAdapter.notifyItemInserted(expense.mini_transactions.size());
                model.isChanged.setValue(1);
            }
        });

        title_of_expense.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                expense.title = title_of_expense.getText().toString().trim();
                Executors.newFixedThreadPool(10).execute(new Runnable() {
                    @Override
                    public void run() {
                        expenseDatabase.expenseDao().updateOne(expense);
                    }
                });
                expandedItemAdapter.setE(expense);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        expenseDatabase.expenseDao().getAll().observe(ExpandExpenseList.this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                for (Expense e:
                        expenses) {
                    if(e.id == expense.id){
                        expandedItemAdapter.setE(e);
                    }
                }
            }
        });

        model.isChanged.observe(ExpandExpenseList.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 0){
                    pending_save_icon.setVisibility(View.INVISIBLE);
                    done_icon.setVisibility(View.VISIBLE);
                }else{
                    pending_save_icon.setVisibility(View.VISIBLE);
                    done_icon.setVisibility(View.INVISIBLE);
                }
            }
        });


        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialAlertDialogBuilder(ExpandExpenseList.this)
                                .setMessage("You will lose all your data. Do you want to delete this record?")
                                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        })
                                                .setNegativeButton("Sure", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                expenseDatabase.expenseDao().deleteOne(expense);
                                                            }
                                                        });
                                                        onBackPressed();

                                                    }
                                                })
                        .create().show();




            }
        });


    }
}