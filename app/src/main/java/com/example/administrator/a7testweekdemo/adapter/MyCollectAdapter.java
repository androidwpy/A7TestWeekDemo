package com.example.administrator.a7testweekdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.bean.MyCollectEntrity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * Created by Administrator on 2016-11-12.
 */
public class MyCollectAdapter extends MBaseAdapter<MyCollectEntrity.DataBean> {
    private final BitmapUtils bitmapUtils;
    private Context context;

    public MyCollectAdapter(Context context) {
        super(context);
        this.context=context;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view=getInflater().inflate(R.layout.item_first_listview,viewGroup,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.titleTextView.setText(getItem(i).getTitle());
        viewHolder.sourceTextView.setText(getItem(i).getSource());
        viewHolder.descriptionTextView.setText(getItem(i).getDescription());
        viewHolder.nameTextView.setText(getItem(i).getNickname());
        viewHolder.dateTextView.setText(getItem(i).getCreate_time());

        String wap_thumb = getItem(i).getWap_thumb();
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
        displayConfig.setLoadingDrawable(context.getResources().getDrawable(R.drawable.defaultcovers));

        bitmapUtils.display(viewHolder.imageView,wap_thumb, displayConfig);

        return view;
    }

    class ViewHolder{
        TextView titleTextView;
        TextView sourceTextView;
        TextView descriptionTextView;
        TextView nameTextView;
        TextView dateTextView;
        ImageView imageView;

        public ViewHolder(View view) {
            titleTextView= ((TextView) view.findViewById(R.id.item_first_titleId));
            sourceTextView= ((TextView) view.findViewById(R.id.item_first_sourceId));
            descriptionTextView= ((TextView) view.findViewById(R.id.item_first_descriptionId));
            nameTextView= ((TextView) view.findViewById(R.id.item_first_nameId));
            dateTextView= ((TextView) view.findViewById(R.id.item_first_dateId));
            imageView= ((ImageView) view.findViewById(R.id.item_first_imageViewId));
        }
    }
}
