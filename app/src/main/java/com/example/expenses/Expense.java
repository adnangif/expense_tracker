package com.example.expenses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

@Entity(tableName = "expensesTable")
public class Expense implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name="title")
    String title;

    @ColumnInfo(name="mini_transactions")
    ArrayList<String> mini_transactions= new ArrayList<>();

    @ColumnInfo(name="transaction_cause")
    ArrayList<String> transaction_cause = new ArrayList<>();

    @ColumnInfo(name="time_of_creation")
    ArrayList<String> time_of_creation = new ArrayList<>();

    public Expense(int id, String title, ArrayList<String> mini_transactions, ArrayList<String> transaction_cause) {
        this.id = id;
        this.title = title;
        this.mini_transactions = mini_transactions;
        this.transaction_cause = transaction_cause;
    }

    @Ignore
    public Expense(String title) {
        this.title = title;
    }
}
