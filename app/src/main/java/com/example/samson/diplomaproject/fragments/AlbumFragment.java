package com.example.samson.diplomaproject.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.adapters.AlbumAdapter;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.utils.FileManager;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

import java.io.File;
import java.util.ArrayList;

public class AlbumFragment extends BaseFragment<MainActivity> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private GridView mAlbum;
    private AlbumAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAlbum();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_album;
    }

    private void initAlbum(){
        mAlbum = $(R.id.gvAlbum);
        mAlbum.setOnItemClickListener(this);
        mAlbum.setOnItemLongClickListener(this);
        mAlbum.setNumColumns(3);
        setAdapter();
    }

    private void setAdapter(){

        String targetPath = FileManager.getPathDirectory(FileManager.TypeImage.Photo);

        File targetDirectory = new File(targetPath);

        File[] files = targetDirectory.listFiles();
        ArrayList<String> paths = new ArrayList<>();
        for (File file : files){
            paths.add(file.getAbsolutePath());
        }

        mAdapter = new AlbumAdapter(getHostActivity(), paths);
        mAlbum.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditor(mAdapter.getItem(position));
    }

    private void openEditor(String _path){
        FragmentReplacer.replaceFragmentWithBackStack(getHostActivity(), EditorFragment.newInstance(_path));
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
