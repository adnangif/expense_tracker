package com.example.expenses;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value){
        return new Gson()
                .fromJson(value,new TypeToken<ArrayList<String>>(){}
                        .getType());
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list){
        return new Gson()
                .toJson(list);
    }
}
