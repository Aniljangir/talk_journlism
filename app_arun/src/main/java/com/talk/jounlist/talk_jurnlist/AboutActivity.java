package com.talk.jounlist.talk_jurnlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Arun on 7/14/2016.
 */
public class AboutActivity extends Activity{

    ImageView img_back;
    TextView tv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_adyep);
        tv_main=(TextView)findViewById(R.id.tv_main);
        img_back=(ImageView) findViewById(R.id.img_back);
        tv_main.setText("Technology Partner Of TJ");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }
}
