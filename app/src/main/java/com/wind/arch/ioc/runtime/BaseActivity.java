package com.wind.arch.ioc.runtime;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created By wind
 * on 2020-02-21
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injects.inject(this);
    }
}
