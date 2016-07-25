package com.talk.jounlist.talk_jurnlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Arun on 6/27/2016.
 */
public class Prgramcall  extends Activity{

    Button bt_venue1,bt_venue2,bt_venue3;
    ImageView img_back;
    TextView   tv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue);
        bt_venue1=(Button) findViewById(R.id.bt_venue1);
        bt_venue2=(Button) findViewById(R.id.bt_venue2);
        bt_venue3=(Button) findViewById(R.id.bt_venue3);
        img_back=(ImageView) findViewById(R.id.img_back);
        tv_main=(TextView)findViewById(R.id.tv_main);
        tv_main.setText("Venue");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_venue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(Prgramcall.this,Programcall_last.class);
                as.putExtra("venues","Venue 1 : July 29 2016");
                startActivity(as);
                finish();
            }
        });
        bt_venue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(Prgramcall.this,Programcall_last.class);
                as.putExtra("venues","Venue 2 : July 30 2016");
                startActivity(as);
                finish();

            }
        });
        bt_venue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(Prgramcall.this,Programcall_last.class);
                as.putExtra("venues","Venue 3 : July 31 2016");
                startActivity(as);
                finish();
            }
        });
    }



}
