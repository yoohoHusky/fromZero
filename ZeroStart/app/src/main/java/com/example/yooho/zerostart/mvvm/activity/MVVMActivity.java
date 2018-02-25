package com.example.yooho.zerostart.mvvm.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.databinding.ActivityMvvmHostBinding;
import com.example.yooho.zerostart.mvvm.viewmodel.DemoViewModel;
import com.example.yooho.zerostart.mvvm.viewmodel.MVVMHandler;
import com.example.yooho.zerostart.mvvm.viewmodel.ModelEvent;

/**
 * Created by haoou on 2018/2/22.
 */

public class MVVMActivity extends AppCompatActivity {

    private DemoViewModel demoViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmHostBinding binDing = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_host);
        demoViewModel = new DemoViewModel("aa", "bb", 4, binDing);
        MVVMHandler handler = new MVVMHandler(binDing);

        binDing.setDemoVM(demoViewModel);
        binDing.setBtnClk(handler);
        binDing.setEvent(new ModelEvent());
    }

    public void changeName(View view) {
        demoViewModel.firstName = "changed_first_name";
        demoViewModel.lastName = "changed_last_name";
    }

    public void changeName2(View view) {
        demoViewModel.setFirstName("set_first_2");
        demoViewModel.setLastName("set_last_2");
    }
}

