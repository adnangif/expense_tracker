package com.example.expenses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Expense.class},exportSchema = false,version = 2)
@TypeConverters({Converters.class})
public abstract class ExpenseDatabase extends RoomDatabase {
    public static final String DB_NAME = "expense_db";
    public static ExpenseDatabase instance;
    public static ExpenseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,ExpenseDatabase.class,DB_NAME).build();
        }
        return instance;
    }
    public abstract ExpenseDao expenseDao();
}

