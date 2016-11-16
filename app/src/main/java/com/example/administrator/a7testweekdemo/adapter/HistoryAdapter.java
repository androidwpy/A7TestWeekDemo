package com.example.administrator.a7testweekdemo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a7testweekdemo.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/10/12.
 */
public class HistoryAdapter extends CursorAdapter{
    private final BitmapUtils bitmapUtils;
    private int titleIndex;
    private int sourceIndex;
    private int nameIndex;
    private int dateIndex;
    private int pictureIndex;
    private int descriptionIndex;

    public HistoryAdapter(Context context, Cursor c) {
        super(context, c, HistoryAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        bitmapUtils = new BitmapUtils(context);

/**
 *  "id": "8207",
 "title": "“日照绿茶”品牌价值8.85亿 居山东茶类之首",
 "source": "转载",
 "description": "",
 "wap_thumb": "http://s1.sns.maimaicha.com/images/2016/01/04/20160104104521_47289_suolue3.jpg",
 "create_time": "01月04日10:47",
 "nickname": "bubu123"
 *
 */
        titleIndex = c.getColumnIndex("title");
        sourceIndex = c.getColumnIndex("source");
        nameIndex = c.getColumnIndex("nickname");
        dateIndex = c.getColumnIndex("create_time");
        pictureIndex = c.getColumnIndex("wap_thumb");
        descriptionIndex=c.getColumnIndex("description");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_first_listview,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        String title = cursor.getString(titleIndex);
        String source = cursor.getString(sourceIndex);
        String nickname = cursor.getString(nameIndex);
        String create_time = cursor.getString(dateIndex);
        String description=cursor.getString(descriptionIndex);

        viewHolder.titleTextView.setText(title);
        viewHolder.sourceTextView.setText(source);
        viewHolder.nameTextView.setText(nickname);
        viewHolder.dateTextView.setText(create_time);
        viewHolder.descriptionTextView.setText(description);

        String wap_thumb = cursor.getString(pictureIndex);
        bitmapUtils.display(viewHolder.imageView,wap_thumb);
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
