package com.example.administrator.a7testweekdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.a7testweekdemo.R;

public class InformationActivity extends AppCompatActivity{

    private TextView inforTextView;
    private TextView controlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initView();

        inforTextView.setText("       茶百科，字如其名，这里只谈茶！结识茶人，学茶、品茶、悟道，继承传播中国茶文化。" +
                "教你学会识茶选茶、贮存茶。更能品茶论水，赏茶雅玩，爱品茗。");
    }

    private void initView() {
        inforTextView = ((TextView) findViewById(R.id.activity_information_infoId));
        controlTextView = ((TextView) findViewById(R.id.activity_information_showControlId));
    }
    private boolean flag=true;
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_information_imageViewBackId:
                finish();
                break;
            case R.id.activity_information_showControlId:
                if (flag){
                    flag=false;
                    inforTextView.setEllipsize(null);
                    inforTextView.setSingleLine(false);
                    controlTextView.setText("收起");

                }else{
                    flag=true;
                    inforTextView.setEllipsize(TextUtils.TruncateAt.END);
                    inforTextView.setSingleLine(false);
                    inforTextView.setLines(2);
                    controlTextView.setText("更多");
                }
                break;
        }
    }
}
