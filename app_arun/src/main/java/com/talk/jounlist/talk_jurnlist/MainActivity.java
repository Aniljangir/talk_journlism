package com.talk.jounlist.talk_jurnlist;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.talk.jounlist.talk_jurnlist.utils.StaticData;

/**
 * Created by Arun on 6/25/2016.
 */
public class MainActivity extends FragmentActivity implements TabListener
{
    ViewPager viewPager;
    TextView tvProgram , tvGallery , tvHome , tvSession , tvWorkshop , tvSpeaker;
    Tabpageadpter mAdapter;
    ImageView img_back;
    DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    LinearLayout ll_program,ll_gallery,ll_home,ll_session,ll_workspace,ll_speaker;
    RelativeLayout profile,rl_home,rl_about,rl_jaipur,rl_venus,rl_Ar;
    // Tab titles
    private String[] tabs = {"PROGRAM", "GALLERY", "HOME", "SESSIONS", "WORKSHOP", "SPEAKER"};
    TextView tv_main;
    HorizontalScrollView horizontalScrollView1;
ImageView power_img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        intial();


        mAdapter = new Tabpageadpter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        /**
         * on swiping the viewpager make respective tab selected
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                // TODO Auto-generated method stub
                Home_tab_color(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
                // TODO Auto-generated method stub
            }
        });

        viewPager.setCurrentItem(1);
        //Home_tab_color(0);


       /* ll_program.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Home_tab_color(0);
                viewPager.setCurrentItem(0);
            }
        });*/
        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(0);
                viewPager.setCurrentItem(0);
            }
        });
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(1);
                viewPager.setCurrentItem(1);
            }
        });
        ll_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(2);
                viewPager.setCurrentItem(2);
            }
        });
        ll_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(3);
                viewPager.setCurrentItem(3);

            }
        });
//        ll_workspace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Home_tab_color(4);
//                viewPager.setCurrentItem(4);
//
//            }
//        });

        /*ll_program.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Home_tab_color(0);
                viewPager.setCurrentItem(0);
            }
        });
        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(1);
                viewPager.setCurrentItem(1);
            }
        });
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(2);
                viewPager.setCurrentItem(2);
            }
        });
        ll_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(3);
                viewPager.setCurrentItem(3);
            }
        });
        ll_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(5);
                viewPager.setCurrentItem(5);

            }
        });
        ll_workspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_tab_color(4);
                viewPager.setCurrentItem(4);

            }
        });*/
       /*
         MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
       if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        }*/
        // Here, thisActivity is the current activity

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        333);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 333: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("aa","Granted");

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Log.e("aa","Not Granted");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed()
    {


        if(viewPager.getCurrentItem() == 1)
        {
            super.onBackPressed();
        }
        else
        {
            viewPager.setCurrentItem(1);
        }
    }

    private void intial()
    {
        tvProgram = (TextView)findViewById(R.id.tvProgram);
        tvGallery = (TextView)findViewById(R.id.tvGallery);
        tvHome = (TextView)findViewById(R.id.tvHome);
        tvSession = (TextView)findViewById(R.id.tvSession);
        tvWorkshop = (TextView)findViewById(R.id.tvWorkshop);
        tvSpeaker = (TextView)findViewById(R.id.tvSpeaker);
        power_img = (ImageView) findViewById(R.id.power_img);

        horizontalScrollView1 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
        viewPager = (ViewPager) findViewById(R.id.pager);
        ll_program=(LinearLayout) findViewById(R.id.ll_program);
        ll_gallery=(LinearLayout) findViewById(R.id.ll_gallery);
        ll_home=(LinearLayout) findViewById(R.id.ll_home);
        ll_session=(LinearLayout) findViewById(R.id.ll_session);
        ll_workspace=(LinearLayout) findViewById(R.id.ll_workspace);
        ll_speaker=(LinearLayout) findViewById(R.id.ll_speaker);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        tv_main = (TextView)findViewById(R.id.tv_main);
        tv_main.setText(StaticData.pref(MainActivity.this).getString(StaticData.PREFKEY_USER_NAME, "hell"));

        profile=(RelativeLayout) findViewById(R.id.profile);
        rl_home=(RelativeLayout) findViewById(R.id.rl_home);

        rl_about=(RelativeLayout)findViewById(R.id.rl_about);
        rl_jaipur=(RelativeLayout) findViewById(R.id.rl_jaipur);
        rl_venus=(RelativeLayout) findViewById(R.id.rl_venus);
        rl_Ar=(RelativeLayout) findViewById(R.id.rl_Ar);

        rl_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent as = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(as);

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent as = new Intent(MainActivity.this,Profile.class);
                startActivity(as);

            }
        });
        rl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Home_tab_color(1);
                viewPager.setCurrentItem(1);
            }
        });
        rl_venus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent as = new Intent(MainActivity.this,AboutvenueActivity.class);
                startActivity(as);
            }
        });
        rl_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent as = new Intent(MainActivity.this,ImageTargets.class);
                startActivity(as);
            }
        });
        rl_jaipur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent as = new Intent(MainActivity.this,AboutJaipur.class);
                startActivity(as);
            }
        });
        power_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent as = new Intent(MainActivity.this,ImageTargets.class);
                startActivity(as);
            }
        });
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                {
                    mDrawerLayout.closeDrawers();
                }
                else
                {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

    }

    public void Home_tab_color(int pos)
    {
        switch (pos)
        {
            /*case 0:
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // TODO Auto-generated method stub
                        horizontalScrollView1.scrollTo(0, 0);
                    }
                });
                ll_program.setBackground(getResources().getDrawable(R.mipmap.mouse_hover_bg));
                tvProgram.setTextSize(19);
                tvProgram.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                tvGallery.setTextSize(17);
                tvGallery.setTypeface(Typeface.DEFAULT);
                tvHome.setTextSize(17);
                tvHome.setTypeface(Typeface.DEFAULT);
                tvSession.setTextSize(17);
                tvSession.setTypeface(Typeface.DEFAULT);
                tvWorkshop.setTextSize(17);
                tvWorkshop.setTypeface(Typeface.DEFAULT);
                tvSpeaker.setTextSize(17);
                tvSpeaker.setTypeface(Typeface.DEFAULT);



                ll_program.requestFocus();
                //ll_earnyeps.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                ll_gallery.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_home.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_session.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_workspace.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_speaker.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;*/
            case 0:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //	horizontalScrollView1.scrollTo(starttime_arun, 0);
                     //   horizontalScrollView1.scrollTo(70*2, 0);
                        horizontalScrollView1.scrollTo(0, 0);
                    }
                });
                ll_program.setBackgroundColor(getResources().getColor(R.color.transparent));
                //ll_earnyeps.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                ll_gallery.setBackground(getResources().getDrawable(R.mipmap.mouse_hover_bg));

                tvProgram.setTextSize(17);
                tvProgram.setTypeface(Typeface.DEFAULT);

                tvGallery.setTextSize(19);
                tvGallery.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                tvHome.setTextSize(17);
                tvHome.setTypeface(Typeface.DEFAULT);
                tvSession.setTextSize(17);
                tvSession.setTypeface(Typeface.DEFAULT);
                tvWorkshop.setTextSize(17);
                tvWorkshop.setTypeface(Typeface.DEFAULT);
                tvSpeaker.setTextSize(17);
                tvSpeaker.setTypeface(Typeface.DEFAULT);

                ll_gallery.requestFocus();
                ll_home.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_session.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_workspace.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_speaker.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 1:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //	horizontalScrollView1.scrollTo(starttime_arun, 0);
                        horizontalScrollView1.scrollTo(70*2, 0);
                    }
                });
                ll_program.setBackgroundColor(getResources().getColor(R.color.transparent));
                //ll_earnyeps.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                ll_gallery.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_home.setBackground(getResources().getDrawable(R.mipmap.mouse_hover_bg));

                tvProgram.setTextSize(17);
                tvProgram.setTypeface(Typeface.DEFAULT);
                tvGallery.setTextSize(17);
                tvGallery.setTypeface(Typeface.DEFAULT);

                tvHome.setTextSize(19);
                tvHome.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                tvSession.setTextSize(17);
                tvSession.setTypeface(Typeface.DEFAULT);
                tvWorkshop.setTextSize(17);
                tvWorkshop.setTypeface(Typeface.DEFAULT);
                tvSpeaker.setTextSize(17);
                tvSpeaker.setTypeface(Typeface.DEFAULT);

                ll_home.requestFocus();
                ll_session.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_workspace.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_speaker.setBackgroundColor(getResources().getColor(R.color.transparent));
                //
                break;
            case 2:


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //	horizontalScrollView1.scrollTo(starttime_arun, 0);
                        horizontalScrollView1.scrollTo(70*3, 0);
                    }
                });

                ll_program.setBackgroundColor(getResources().getColor(R.color.transparent));
                //ll_earnyeps.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                ll_gallery.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_home.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_session.setBackground(getResources().getDrawable(R.mipmap.mouse_hover_bg));

                tvProgram.setTextSize(17);
                tvProgram.setTypeface(Typeface.DEFAULT);
                tvGallery.setTextSize(17);
                tvGallery.setTypeface(Typeface.DEFAULT);
                tvHome.setTextSize(17);
                tvHome.setTypeface(Typeface.DEFAULT);

                tvSession.setTextSize(19);
                tvSession.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                tvWorkshop.setTextSize(17);
                tvWorkshop.setTypeface(Typeface.DEFAULT);
                tvSpeaker.setTextSize(17);
                tvSpeaker.setTypeface(Typeface.DEFAULT);

                ll_session.requestFocus();
                ll_workspace.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_speaker.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;

          /*  case 4:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //	horizontalScrollView1.scrollTo(starttime_arun, 0);
                        horizontalScrollView1.scrollTo(70*5, 0);
                    }
                });


                ll_program.setBackgroundColor(getResources().getColor(R.color.transparent));
                //ll_earnyeps.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                ll_gallery.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_home.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_session.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_workspace.setBackground(getResources().getDrawable(R.mipmap.mouse_hover_bg));

                tvProgram.setTextSize(17);
                tvProgram.setTypeface(Typeface.DEFAULT);
                tvGallery.setTextSize(17);
                tvGallery.setTypeface(Typeface.DEFAULT);
                tvHome.setTextSize(17);
                tvHome.setTypeface(Typeface.DEFAULT);
                tvSession.setTextSize(17);
                tvSession.setTypeface(Typeface.DEFAULT);

                tvWorkshop.setTextSize(19);
                tvWorkshop.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                tvSpeaker.setTextSize(17);
                tvSpeaker.setTypeface(Typeface.DEFAULT);

                ll_workspace.requestFocus();
                ll_speaker.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;*/
            case 3:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //	horizontalScrollView1.scrollTo(starttime_arun, 0);
                       // horizontalScrollView1.scro

                        horizontalScrollView1.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });

                ll_program.setBackgroundColor(getResources().getColor(R.color.transparent));
                //ll_earnyeps.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                ll_gallery.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_home.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_session.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_workspace.setBackgroundColor(getResources().getColor(R.color.transparent));
                ll_speaker.setBackground(getResources().getDrawable(R.mipmap.mouse_hover_bg));

                tvProgram.setTextSize(17);
                tvProgram.setTypeface(Typeface.DEFAULT);
                tvGallery.setTextSize(17);
                tvGallery.setTypeface(Typeface.DEFAULT);
                tvHome.setTextSize(17);
                tvHome.setTypeface(Typeface.DEFAULT);
                tvSession.setTextSize(17);
                tvSession.setTypeface(Typeface.DEFAULT);
                tvWorkshop.setTextSize(17);
                tvWorkshop.setTypeface(Typeface.DEFAULT);

                tvSpeaker.setTextSize(19);
                tvSpeaker.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                ll_speaker.requestFocus();
                break;

            default:
                break;
        }

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }
}