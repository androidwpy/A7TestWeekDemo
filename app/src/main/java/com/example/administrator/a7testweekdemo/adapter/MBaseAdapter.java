package com.example.administrator.a7testweekdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-12.
 */
public abstract class MBaseAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private LayoutInflater inflater;

    public MBaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        datas=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void addAll(List<T> dd){
        datas.addAll(dd);
        notifyDataSetChanged();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }
}
