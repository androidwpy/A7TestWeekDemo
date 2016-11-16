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
public class Welcome1Fragment extends Fragment {


    public Welcome1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome1,container,false);
        return view;
    }

}
