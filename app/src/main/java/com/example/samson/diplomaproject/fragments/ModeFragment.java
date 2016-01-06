package com.example.samson.diplomaproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

/**
 * Created by samson on 05.01.16.
 */
public class ModeFragment extends BaseFragment<MainActivity> implements View.OnClickListener {

    private Button btnTest, btnLearn;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mode;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI();
        setClickListeners(this, btnLearn, btnTest);
    }

    private void findUI() {
        btnLearn = $(R.id.btnLearn);
        btnTest = $(R.id.btnTest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLearn:
                openFragment(new LearningFragment());
                break;
            case R.id.btnTest:
                openFragment(new MenuFragment());
                break;
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentReplacer.replaceFragment(getHostActivity(), fragment);
    }
}
