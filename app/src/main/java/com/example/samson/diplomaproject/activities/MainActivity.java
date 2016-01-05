package com.example.samson.diplomaproject.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.fragments.ModeFragment;
import com.example.samson.diplomaproject.utils.FragmentReplacer;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentReplacer.addFragment(this, new ModeFragment());
    }

    public void closeApp(){
        this.finish();
    }
}
