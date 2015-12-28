package com.example.samson.diplomaproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.global.Constants;
import com.example.samson.diplomaproject.utils.FileExplorer;

/**
 * Created by samson on 22.07.15.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FileExplorer.createDirectory();
        openMainMenu();
    }

    private void openMainMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
