package com.talk.jounlist.talk_jurnlist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.talk.jounlist.talk_jurnlist.utils.Logger;
import com.talk.jounlist.talk_jurnlist.utils.StaticData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Arun on 6/24/2016.
 */
public class Splash_activity extends Activity
{
    private String LOG_TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);
        getKeyHash();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        startSplash();
    }

    private void getKeyHash()
    {
        // TODO Auto-generated method stub
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.talk.jounlist.talk_jurnlist", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Logger.e("key","key="+something);
                //String something = new String(Base64.encodeBytes(md.digest()));
            }
        }
        catch (PackageManager.NameNotFoundException e1)
        {

        }
        catch (NoSuchAlgorithmException e)
        {

        }
        catch (Exception e)
        {

        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void startSplash()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {
                   // Logger1.e("SplashActivity", e.getMessage());
                }
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //	static_data.startActivity(SplashActivity.this,new Intent(SplashActivity.this, TutorialScreen.class));
                        SharedPreferences sharedPreferences = StaticData.pref(getApplicationContext());
                        //	static_data.startActivity(SplashActivity.this,new Intent(SplashActivity.this, TutorialScreen.class));
                        //SharedPreferences sharedPreferences = static_data.pref(getApplicationContext());
                       // Logger.e("cjdkjdk", "userlogin"+static_data.Is_User_Login);
                        if(!sharedPreferences.getBoolean(StaticData.Is_User_Login, false))
                        {

                            StaticData.startActivity(Splash_activity.this,new Intent(Splash_activity.this, Login.class));
                            //startActivity(new Intent(SplashActivity.this, Reg_Login_Activity.class));
                            finish();
                        }
                        else
                        {
                            //static_data.startActivity(SplashActivity.this,new Intent(SplashActivity.this, SignUp.class));
                            StaticData.startActivity(Splash_activity.this,new Intent(Splash_activity.this, MainActivity.class));
                            //startActivity(new Intent(SplashActivity.this, Reg_Login_Activity.class));
                            finish();

                        }
                        }

                        //UtilityAdapter.FFmpegRun("", "ffmpeg -i /sdcard/in.mp3 /sdcard/audio.flac");
                        //UtilityAdapter.FFmpegRun("", "ffmpeg -i /sdcard/audio.flac -lavfi showwavespic=split_channels=1:s=300x100 /sdcard/waveform.png");
                        //static_data.startActivity(SplashActivity.this,new Intent(SplashActivity.this, Social.class));


                });
            }
        }).start();

}
}
