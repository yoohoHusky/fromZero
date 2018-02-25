package com.example.yooho.zerostart.mvvm.viewmodel;

import android.view.View;
import android.widget.Toast;

import com.example.yooho.zerostart.databinding.ActivityMvvmHostBinding;

/**
 * Created by haoou on 2018/2/22.
 */

public class MVVMHandler {
    private ActivityMvvmHostBinding binding;

    public MVVMHandler(ActivityMvvmHostBinding binding) {
        this.binding = binding;
    }

    public void btnClick(View view) {
        Toast.makeText(view.getContext().getApplicationContext(), binding.ed2.getText(), Toast.LENGTH_SHORT).show();
    }

    public void editTextChanged(View view) {
        Toast.makeText(view.getContext().getApplicationContext(), binding.edMvvm.getText(), Toast.LENGTH_SHORT).show();
    }
}
