package com.example.administrator.a7testweekdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/12.
 */
public class TeaDatabaseHistoryHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="tea_history_database.db";
    public static final int DB_VERSION=1;
    public static final String TABLE_NAME="table_history_teadata";

    public TeaDatabaseHistoryHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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
        String sql="create table if not exists "+TABLE_NAME+"(_id integer primary key autoincrement,id,title,source,description,wap_thumb,create_time,nickname);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i<i1){
            Log.d("1606","你的版本更新了");
        }
    }
}
