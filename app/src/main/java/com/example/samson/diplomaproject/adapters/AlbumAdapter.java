package com.example.samson.diplomaproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.holders.PhotoHolder;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mPaths;

    public AlbumAdapter(Context _context, ArrayList<String> _list){
        mContext = _context;
        mPaths = _list;
    }

    @Override
    public int getCount() {
        return mPaths.size();
    }

    @Override
    public String getItem(int position) {
        return mPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void deleteItem(int position){
        mPaths.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_album_photo, null, false);
            holder = new PhotoHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PhotoHolder) convertView.getTag();
        }

        holder.setPicture(getItem(position));
        return convertView;
    }
}
