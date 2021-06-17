package com.example.fifth;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateTask implements Runnable {
    private static final String TAG = "MyTask";
    private Handler handler;

    public void setHandler(Handler h) {
        this.handler = h;
    }

    @Override
    public void run() {
        Document document = null;
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
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
        List<Rate> ret = new ArrayList<Rate>();

        try {
            Thread.sleep(5000);

            document = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run: title=" + document.title());

            //获取时间
            //body > section > div > div > article > p

            Element publicTime = document.getElementsByClass("time").first();
            Log.i(TAG, "run: time = " + publicTime.html());

            Element table = document.getElementsByTag("table").get(1);
            Elements trs = table.getElementsByTag("tr");
            for(Element tr:trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str = tds.first().text();
                    Log.i(TAG, "run: tds=" + str);

                    String val = tds.get(5).text();
                    Log.i(TAG, "run:rate=" + val);
                    ret.add(new Rate(str,val));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            Elements table = document.getElementsByTag("table");
            Element table2 = table.get(1);
            Elements tds = table2.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 8) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: " + str1 + "==>" + val);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", str1);
                map.put("ItemDetail", val);
                retList.add(map);
            }



        //返回数据给主线程

        Message msg = handler.obtainMessage(11,ret);
        msg.obj = "reList";
        handler.sendMessage(msg);

    }
}
