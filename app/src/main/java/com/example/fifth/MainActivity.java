package com.example.fifth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ConfigActivity";
    EditText dollarEditor, euroEditor, wonEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent conf = getIntent();
        float dollar = conf.getFloatExtra("dollar_rate_key", 0.0f);
        float euro = conf.getFloatExtra("euro_rate_key", 0.0f);
        float won = conf.getFloatExtra("won_rate_key", 0.0f);

        Log.i(TAG, "onCreate: dollar=" + dollar);
        Log.i(TAG, "onCreate: euro=" + euro);
        Log.i(TAG, "onCreate: won=" + won);

        dollarEditor = findViewById(R.id.edit_dollar);
        euroEditor = findViewById(R.id.edit_euro);
        wonEditor = findViewById(R.id.edit_won);

        dollarEditor.setText(String.valueOf(dollar));
        euroEditor.setText(String.valueOf(euro));
        wonEditor.setText(String.valueOf(won));
    }

    public void save(View btn) {
        float newDollar = Float.parseFloat(dollarEditor.getText().toString());
        float newEuro = Float.parseFloat(euroEditor.getText().toString());
        float newWon = Float.parseFloat(wonEditor.getText().toString());

        Intent intent = getIntent();


        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar", newDollar);
        bdl.putFloat("key_euro", newEuro);
        bdl.putFloat("key_won", newWon);

        intent.putExtras(bdl);
        setResult(2, intent);
        finish();
    }

    public void onClick(View btn) {
        Log.i(TAG, "myClick:click ccccccc ");
       /* //写入数据到数据库
        RateItem item = new RateItem();
        item.setCurname("币种");
        item.setCurrate("12.345");

        */
        RateManager manager = new RateManager(this);
        //manager.add(item);
        //Log.i(TAG, "onClick: item saved");




        //查询结果
        List<RateItem> list = manager.listAll();
        for(RateItem item : list){
            Log.i(TAG, "onClick: item=" + item);
        }
    }



    public void open(View btn) {
        Log.i(TAG, "open: ......");
        Intent rateIntent = new Intent(this, RateActivity.class);
        startActivity(rateIntent);
    }
}
