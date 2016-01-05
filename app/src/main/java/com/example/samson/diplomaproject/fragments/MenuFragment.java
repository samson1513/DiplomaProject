package com.example.samson.diplomaproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

public class MenuFragment extends BaseFragment<MainActivity> implements View.OnClickListener {

    private TextView mToCamera, mToAlbum, mToExit;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findUI();
        setListeners();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    private void findUI(){
        mToAlbum = $(R.id.tvAlbum);
        mToCamera = $(R.id.tvCamera);
        mToExit = $(R.id.tvExit);
    }

    private void setListeners(){
        mToAlbum.setOnClickListener(this);
        mToCamera.setOnClickListener(this);
        mToExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAlbum:
                openFragment(new AlbumFragment());
                break;
            case R.id.tvCamera:
                openFragment(new CameraFragment());
                break;
            case R.id.tvExit:
                getHostActivity().closeApp();
                break;
        }
    }

    private void openFragment(Fragment fragment){
        FragmentReplacer.replaceFragmentWithBackStack(getHostActivity(), fragment);
    }

}
