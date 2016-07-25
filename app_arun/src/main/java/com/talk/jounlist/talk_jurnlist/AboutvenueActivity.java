package com.talk.jounlist.talk_jurnlist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Arun on 7/14/2016.
 */
public class AboutvenueActivity extends Activity {
    ImageView img_back;
    TextView tv_main;
    Button button_map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutvenue);
        tv_main=(TextView)findViewById(R.id.tv_main);
        img_back=(ImageView) findViewById(R.id.img_back);
        button_map=(Button) findViewById(R.id.button_map);
        tv_main.setText("About Venue");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String url = "http://maps.google.com/maps?saddr=26.8277311/75.80613949999997;daddr=27.030594/75.890027;ll=26.8277311/75.80613949999997";
                String url =        "http://maps.google.com/maps?saddr=26.8277311,75.80613949999997&daddr=27.0307607,75.887665";
                startActivity( new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
            }
        });
    }


}
