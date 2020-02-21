package com.wind.arch.ioc.runtime;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wind.arch.ioc.R;

@Layout(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injects.inject(this);
        tv_1.setText("TextView1 注入成功");
        tv_2.setText("TextView2 注入成功");


    }

    @OnClick({R.id.tv_1,R.id.tv_2})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.tv_1:
                Toast.makeText(this,"这是textview1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_2:
                Toast.makeText(this,"这是textview2",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @OnLongClick({R.id.tv_1,R.id.tv_2})
    public boolean onViewLongnClick(View view){
        switch (view.getId()){
            case R.id.tv_1:
                Toast.makeText(this,"这是textview1 long click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_2:
                Toast.makeText(this,"这是textview2 long click",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    @OnTouch(R.id.tv_1)
    public boolean onViewTouch(View view,MotionEvent event){

        System.out.println("event.getAction()"+event.getAction());
        return false;
    }



}
