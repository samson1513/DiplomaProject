package com.example.samson.diplomaproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.utils.FileManager;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FileManager.createProjectDirectory();
        FileManager.createWorkDirectory(FileManager.TypeImage.Photo);
        openMainMenu();
    }

    private void openMainMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
