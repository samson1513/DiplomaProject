package com.example.samson.diplomaproject.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private MainActivity mActivity;

    private TextView mToCamera, mToAlbum, mToExit;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,null);

        findUI(view);
        setListeners();

        return view;
    }

    private void findUI(View _view){
        mToAlbum = (TextView) _view.findViewById(R.id.tvAlbum);
        mToCamera = (TextView) _view.findViewById(R.id.tvCamera);
        mToExit = (TextView) _view.findViewById(R.id.tvExit);
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
                changefragment(new AlbumFragment());
                break;
            case R.id.tvCamera:
                changefragment(new CameraFragment());
                break;
            case R.id.tvExit:
                mActivity.closeApp();
                break;
        }
    }

    private void changefragment(Fragment _fragment){
        FragmentReplacer.replaceFragmentWithBackStack(mActivity, _fragment);
    }

}
