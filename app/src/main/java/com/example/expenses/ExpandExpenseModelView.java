package com.example.expenses;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExpandExpenseModelView extends ViewModel {

    public MutableLiveData<Integer> isChanged = new MutableLiveData<>();
}
