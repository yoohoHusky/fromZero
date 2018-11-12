package com.example.yooho.zerostart.databing;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.databinding.ActivityDatabingBinding;

import io.reactivex.Observable;

public class DatabingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDatabingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_databing);
        DatabindUser user = new DatabindUser();
        user.setName("小王");
        user.setAge(15);

        ObservableList<String> hobby = new ObservableArrayList<>();
        hobby.add("football");
        hobby.add("swim");
        hobby.add("basketball");
        user.setHobby(hobby);
        binding.setUser(user);

    }
}
