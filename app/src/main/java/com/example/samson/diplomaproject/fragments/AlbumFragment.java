package com.example.samson.diplomaproject.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.adapters.AlbumAdapter;
import com.example.samson.diplomaproject.utils.FileExplorer;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

import java.io.File;
import java.util.ArrayList;

public class AlbumFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private MainActivity mActivity;

    private GridView mAlbum;
    private AlbumAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,null);
        initAlbum(view);
        return view;
    }

    private void initAlbum(View _view){
        mAlbum = (GridView) _view.findViewById(R.id.gvAlbum);
        mAlbum.setOnItemClickListener(this);
        mAlbum.setOnItemLongClickListener(this);
        mAlbum.setNumColumns(3);
        setAdapter();
    }

    private void setAdapter(){

        String targetPath = FileExplorer.getPathDirectory();

        File targetDirectory = new File(targetPath);

        File[] files = targetDirectory.listFiles();
        ArrayList<String> paths = new ArrayList<>();
        for (File file : files){
            paths.add(file.getAbsolutePath());
        }

        mAdapter = new AlbumAdapter(mActivity, paths);
        mAlbum.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditor(mAdapter.getItem(position));
    }

    private void openEditor(String _path){
        FragmentReplacer.replaceFragmentWithBackStack(mActivity, EditorFragment.newInstance(_path));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        File image = new File(mAdapter.getItem(position));
        if(image.delete()) {
            mAdapter.deleteItem(position);
            return true;
        } else {
            return false;
        }

    }
}
