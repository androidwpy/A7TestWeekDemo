package com.example.administrator.a7testweekdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.a7testweekdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome2Fragment extends Fragment {


    public Welcome2Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome2,container,false);
        return view;
    }

}
