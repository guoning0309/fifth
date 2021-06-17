package com.example.fifth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class List2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ListView listView = findViewById(R.id.mylist2);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==9){
                    ArrayList<String> list2 = (ArrayList<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(List2Activity.this, android.R.layout.simple_list_item_1,list2);
                    listView.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                }
                super.handleMessage(msg);
            }
        };

        MyTask task = new MyTask();
        task.setHandler(handler);
        Thread t = new Thread(task);
        t.start();//task.run();
    }
}