package com.example.fifth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class List3Activity extends ListActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "List3Activity";

    private ArrayList<HashMap<String,String>> listItems;
    RateAdapter adapter1;
    SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initListView();
        //setContentView(R.layout.activity_list3);
        //ListView list3 = findViewById(R.id.mylist3);

        //data
        /*
        listItems = new ArrayList<HashMap<String,String>>();
        for(int i = 0;i<50; i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate: " + i);//标题文字
            map.put("ItemDetail","Datail " + i);//详情描述
            listItems.add(map);

         */

        //adapter
    /*
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,//listItems 数据源
                R.layout.list_item,//listItem 的XML布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );

     */
        //MyAdapter adapter = new MyAdapter(this,R.layout.list_item,listItems);
        //list3.setAdapter(listItemAdapter);
        //getListView().setAdapter(listItemAdapter);
        //setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);




        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==11){
                    ArrayList<Rate> reList = (ArrayList<Rate>) msg.obj;
                    adapter1 = new RateAdapter(List3Activity.this,R.layout.list_item,reList);
                    setListAdapter(adapter1);
                }
                super.handleMessage(msg);

            }
        };
        RateTask task = new RateTask();
        task.setHandler(handler);
        Thread t = new Thread(task);
        t.start();//task.run()
    }




    /*private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i = 0;i<50; i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("itemTitle","Rate:" + i);
            map.put("ItemDetail","detail:" +i);
            listItems.add(map);
        }
        listItemAdapter = new SimpleAdapter(this,listItems,
                R.layout.list_item,new String[]{"itemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );
    }


     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position=" + position);

        listItems.remove(position);

        listItemAdapter.notifyDataSetChanged();

        Rate rate = (Rate) getListView().getItemAtPosition(position);
        Log.i(TAG, "onItemClick: cname=" + rate.getCname());
        Log.i(TAG, "onItemClick: cval=" + rate.getCval());
        //删除操作
        //adapter1.remove(rate);

        Intent calIntent = new Intent(this,CalcActivity.class);
        calIntent.putExtra("cname",rate.getCname());
        calIntent.putExtra("cval",rate.getCval());
        startActivity(calIntent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按事件处理");
        //对话框提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("演示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框事件处理");
                //删除数据项
                adapter1.remove(getListView().getItemAtPosition(position));
                //listItems.remove(position);
                //更新适配器
                //listItemAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("否",null);
        builder.create().show();

        return true;
    }
}

