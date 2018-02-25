package com.example.yooho.zerostart.mvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yooho.zerostart.BR;
import com.example.yooho.zerostart.databinding.ActivityMvvmHostBinding;

/**
 * Created by haoou on 2018/2/22.
 */

public class DemoViewModel extends BaseObservable {
    private ActivityMvvmHostBinding binding;

    @Bindable
    public String firstName;
    public String lastName;
    private int age;

    public DemoViewModel(String firstName, String lastName, int age, ActivityMvvmHostBinding binding) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.binding = binding;
    }

    public String getFirstName() {
        return this.firstName;
    }


    @Bindable
    public String getLastName() {
        return this.lastName;
    }


    public String getLastName2() {
        return this.lastName + "  0";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }


}
