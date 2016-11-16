package com.example.administrator.a7testweekdemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome3Fragment extends Fragment implements View.OnClickListener {


    private ImageView imageView;

    public Welcome3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome3,container,false);
        imageView = ((ImageView) view.findViewById(R.id.fragment_welcome3_imageViewId));
        imageView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
