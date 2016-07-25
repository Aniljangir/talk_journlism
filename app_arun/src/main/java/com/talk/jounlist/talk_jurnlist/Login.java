package com.talk.jounlist.talk_jurnlist;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.talk.jounlist.talk_jurnlist.utils.Logger;

import com.talk.jounlist.talk_jurnlist.utils.ProgressHUD;
import com.talk.jounlist.talk_jurnlist.utils.RestClient;
import com.talk.jounlist.talk_jurnlist.utils.StaticData;


import org.json.JSONException;
import org.json.JSONObject;


public class Login extends Activity
{
    Button login , sign_up;
    String name , password;
    EditText etName , etPassword;
    ProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

       // FacebookSdk.sdkInitialize(getApplicationContext());


        etName = (EditText)findViewById(R.id.etName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.login);
        sign_up = (Button)findViewById(R.id.sign_up);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etName.getText().toString().equals("") || etName.getText().toString().equals(null) )
                {
                    etName.requestFocus();
                    Toast.makeText(Login.this , "Please Enter Name" , Toast.LENGTH_SHORT).show();
                }
                else if(etPassword.getText().toString().equals("") || etPassword.getText().toString().equals(null) )
                {
                    etPassword.requestFocus();
                    Toast.makeText(Login.this , "Please Enter Password" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    name = etName.getText().toString();
                    password = etPassword.getText().toString();

                    if(isNetworkConnected())
                    {
                        new LoginUser().execute();
                    }
                    else
                    {
                        Toast.makeText(Login.this , "You Are Not Connected With Internet" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this ,SignUp.class ));
                finish();
            }
        });
    }

    private class LoginUser extends AsyncTask<Void , String , String>
    {
        ProgressDialog pd ;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(Login.this, "Connecting", true, true,
                    null);

        }

        @Override
        protected String doInBackground(Void... params)
        {
            StaticData.SERVER_URL = "http://www.yeorder.com/adyep/rest/users/login";
            RestClient client = null;
            client = new RestClient();
            client.AddParam("username" , name);
            client.AddParam("password" , password);
            String responce;
            try
            {
                client.Execute(1);
            responce = client.getResponse();
                Logger.e("responce" , responce);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "error";
            }

            return responce;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (null != mProgressHUD && mProgressHUD.isShowing()) {

                mProgressHUD.dismiss();

            }

            try
            {
                JSONObject obj = new JSONObject(s);
                //String message = obj.get("message").toString();

                //Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();

                if(obj.optString("status").equals("true"))
                {
                    showToast(Login.this, "Successfully Login");

                    SharedPreferences pref = StaticData.pref(Login.this);

                    SharedPreferences.Editor e = pref.edit();
                    e.putString(StaticData.PREFKEY_USER_ID, obj.optString("userid"));
                    e.putString(StaticData.PREFKEY_USER_NAME, obj.optString("username"));
                    e.putString(StaticData.PREFKEY_USER_DOB, obj.optString("dob"));
                    e.putString(StaticData.PREFKEY_USER_EMAIL_ID, obj.optString("email"));
                    e.putString(StaticData.PREFKEY_USER_PIC, obj.optString("profile_pic"));
                    e.putString(StaticData.firsttime,"true");

                    e.putBoolean(StaticData.Is_User_Login, true);
                    //
                    e.commit();

                    Intent i = new Intent(Login.this , MainActivity.class);


                    startActivity(i);
                    finish();
                }

                else
                {
                    showToast(Login.this, obj.optString("message"));
                 //   Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();
                    //Staticutils.showToast(Login.this,obj.get("message")+"");

                }

            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }

    public  void showToast(Context cntx,String message)
    {
        Toast toast = new Toast(cntx);
        View view = ((LayoutInflater)cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_toast, null);
        //View view = getLayoutInflater(context).inflate(R.layout.custom_toast, null);
        ((TextView)view.findViewById(R.id.toast_message)).setText(message);
        toast.setView(view);

        toast.show();
    }

    private boolean isNetworkConnected()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo)
        {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                {
                    haveConnectedWifi = true;
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
            {
                if (ni.isConnected())
                {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}