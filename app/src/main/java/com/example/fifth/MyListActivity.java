package com.example.fifth;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity implements Runnable {
    Handler handler;
    private static final String TAG = "MyListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list);

        List<String> list1 = new ArrayList<String>();
        for(int i = 1; i<100; i++){
            list1.add("item" + i);
        }

        //String[] list_data = {"one", "two", "three", "four"};
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        setListAdapter(adapter);


        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
                    ArrayList<String> list =  (ArrayList<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list);
                    setListAdapter(adapter);
                    Toast.makeText(MyListActivity.this, "ret size=" + list.size(), Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);

            }
        };

        //MyTask task = new MyTask();
        //task.setHandler(handler);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Log.i(TAG, "run: ........");

        for (int i=1;i<3;i++){
            Log.i(TAG,"run: i=" + i);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //线程中完成任务
        URL url = null;
        List<String> ret = new ArrayList<>();
        try{

            Document doc = Jsoup.connect(" https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run: title=" + doc.title());

            //获取时间
            //body > section > div > div > article > p

            //Element publicTime = doc.getElementsByClass("time").first();
            //Log.i(TAG, "run:= " + publicTime.html());

            Element table = doc.getElementsByTag("table").get(1);
            Elements trs = table.getElementsByTag("tr");
            for(Element tr:trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str = tds.first().text();
                    Log.i(TAG, "run: tds=" + str);

                    String val = tds.get(5).text();
                    Log.i(TAG, "run:rate=" + val);
                    ret.add(str + "-->" + val);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //返回数据给主线程

       Message msg = handler.obtainMessage(9,ret);
        msg.obj = "from message";
        handler.sendMessage(msg);
    }
}