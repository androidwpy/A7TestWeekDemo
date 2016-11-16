package com.example.administrator.a7testweekdemo.ui;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.adapter.HistoryAdapter;
import com.example.administrator.a7testweekdemo.database.TeaDatabaseHistoryHelper;
import com.example.administrator.a7testweekdemo.uri.AppInterface;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private ImageView back;
    private ListView listView;
    private TeaDatabaseHistoryHelper helper;
    private static SQLiteDatabase db;
    private static Cursor cursor;
    private final int LOADER_ID = 1;
    private LoaderManager loaderManager;
    private String id;
    private HistoryAdapter adapter;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        helper=new TeaDatabaseHistoryHelper(this);
        db=helper.getReadableDatabase();
        cursor=db.query(TeaDatabaseHistoryHelper.TABLE_NAME,null,null,null,null,null,null);
        //实例化适配器
        adapter = new HistoryAdapter(this, cursor);
        //设置适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        registerForContextMenu(listView);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    private void initView() {
        back = ((ImageView) findViewById(R.id.activity_history_imageViewBackId));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listView = ((ListView) findViewById(R.id.activity_history_listViewId));
        empty = ((TextView) findViewById(R.id.activity_history_emptyId));
        listView.setEmptyView(empty);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cursor.moveToPosition(i);
        String id = cursor.getString(cursor.getColumnIndex("id"));
        final String title = cursor.getString(cursor.getColumnIndex("title"));
        final String source = cursor.getString(cursor.getColumnIndex("source"));
        final String create_time = cursor.getString(cursor.getColumnIndex("create_time"));

        String detailUrl=String.format(AppInterface.CONTENT_URL,id);
        OkHttpUtils.get().url(detailUrl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }
            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data=jsonObject.optJSONObject("data");
                    String wap_content=data.optString("wap_content");
                    Intent intent=new Intent(HistoryActivity.this, DrawerWebViewActivity.class);
                    intent.putExtra("wap_content",wap_content);
                    intent.putExtra("source",source);
                    intent.putExtra("create_time",create_time);
                    intent.putExtra("title",title);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        MyLoader loader = new MyLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    // 异步任务加载器
    private static class MyLoader extends AsyncTaskLoader<Cursor> {

        public MyLoader(Context context) {
            super(context);
        }

        // 开始加载数据时，执行该方法
        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            // 强制开始加载数据
            forceLoad();
        }
        // 开启子线程，做加载数据的操作
        @Override
        public Cursor loadInBackground() {
            cursor = db.query(TeaDatabaseHistoryHelper.TABLE_NAME, null, null, null, null, null, null);
            return cursor;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.data,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        cursor.moveToPosition(position);
        id = cursor.getString(cursor.getColumnIndex("id"));

        switch (item.getItemId()) {
            case R.id.delete_item://删除数据
                showDeteleDialog(id);
                break;
            case R.id.cancle_item://取消

                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeteleDialog(final String id) {
        // 对话框构建者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置对话框标题
        builder.setTitle("提示");
        // 设置提示信息
        builder.setMessage("确定要删除吗？");
        // 设置图标
        builder.setIcon(R.drawable.ic_logo);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id);
            }
        });
        // 创建真正的对话框对象
        AlertDialog dialog = builder.create();
        // 展示对话框
        dialog.show();
    }

    private void deleteData(String id) {
        int count = db.delete(TeaDatabaseHistoryHelper.TABLE_NAME, "id = ?", new String[]{id});
        if (count > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
        // 重新加载数据
        loaderManager.restartLoader(LOADER_ID, null, this);
    }
}
