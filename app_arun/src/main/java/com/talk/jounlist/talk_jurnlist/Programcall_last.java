package com.talk.jounlist.talk_jurnlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Arun on 6/27/2016.
 */
public class Programcall_last extends Activity {
Button button2;
    ImageView img_back;
    TextView tv_main,textView3;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_1);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
           s="";
        } else {
         s= bundle.getString("venues");
        }



        button2=(Button) findViewById(R.id.button2);
        textView3=(TextView) findViewById(R.id.textView3);

        textView3.setText(""+s);

        img_back=(ImageView) findViewById(R.id.img_back);
        tv_main=(TextView)findViewById(R.id.tv_main);
        tv_main.setText("Venue detail");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(Programcall_last.this,Prgramcall.class);
                startActivity(as);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
