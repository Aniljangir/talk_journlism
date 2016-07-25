package com.talk.jounlist.talk_jurnlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JUNED on 14-Jul-16.
 */
public class AboutJaipur extends Activity
{
    View horizontalScrollView1;
    TextView tv_main;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_jaipur);

        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_main.setText("About Jaipur");

        img_back=(ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
