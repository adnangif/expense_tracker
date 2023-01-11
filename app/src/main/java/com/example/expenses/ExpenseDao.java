package com.example.expenses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("select * from expensesTable")
    LiveData<List<Expense>> getAll();

    @Update
    void updateOne(Expense e);

    @Insert
    void insertOne(Expense e);

    @Delete
    void deleteOne(Expense e);

}
