package com.talk.jounlist.talk_jurnlist;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.talk.jounlist.talk_jurnlist.utils.Logger;
import com.talk.jounlist.talk_jurnlist.utils.ProgressHUD;
import com.talk.jounlist.talk_jurnlist.utils.RestClient;
import com.talk.jounlist.talk_jurnlist.utils.StaticData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by Arun on 25-Jun-16.
 */
public class SignUp extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private final int REG_REQ_CODE = 702;
    private RegBean regBean;
    ;

    boolean isFetching = false;
    private static final List<String> PERMISSIONS = Arrays.asList("email","user_friends","public_profile");
    private boolean pendingPublishReauthorization = false;
    private static final int FACEBOOK_REQ_CODE = 3;


    Session mCurrentSession=null;
    EditText etName , etMail,etPassword;

    TextView etDob , dobSet;
    ProgressHUD mProgressHUD;


    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;


    Button sign_up;
    String name , mail, password , dob;
    ImageView image_fb,image_gplus;


    String[] permission=new String[]{
            Manifest.permission.GET_ACCOUNTS,
    };
    com.facebook.login.LoginManager fbLoginManager;
    CallbackManager callbackManager;
    @Override
    protected void onStop() {

        super.onStop();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        getKeyHash();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        etName = (EditText)findViewById(R.id.etName);
        etMail = (EditText)findViewById(R.id.etMail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etDob = (TextView) findViewById(R.id.etDob);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        image_fb=(ImageView) findViewById(R.id.image_fb);
        image_gplus=(ImageView) findViewById(R.id.image_gplus);
        image_gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
               /* Account[] accounts = AccountManager.get(SignUp.this).getAccountsByType("com.google");
                Log.e("aa", "in google");
                if(accounts.length == 0)
                    popup();
                else
                    signInWithGplus();*/
            }
        });
        image_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   signInWithFacebook();
                fbLoginManager.logInWithReadPermissions(SignUp.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
            }
        });

        sign_up = (Button)findViewById(R.id.button1);

        sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etName.getText().toString().equals("") || etName.getText().toString().equals(null) )
                {
                    etName.requestFocus();
                    Toast.makeText(SignUp.this , "Please Enter Name" , Toast.LENGTH_SHORT).show();
                }
                else if(etMail.getText().toString().equals("") || etMail.getText().toString().equals(null) )
                {
                    etMail.requestFocus();
                    Toast.makeText(SignUp.this , "Please Enter Mail id" , Toast.LENGTH_SHORT).show();
                }
                else if(etPassword.getText().toString().equals("") || etPassword.getText().toString().equals(null) )
                {
                    etPassword.requestFocus();
                    Toast.makeText(SignUp.this , "Please Enter Password" , Toast.LENGTH_SHORT).show();
                }
                else if(etDob.getText().toString().equals("") || etDob.getText().toString().equals(null) )
                {
                    Toast.makeText(SignUp.this , "Please Enter Date of Birth" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    name = etName.getText().toString();
                    mail = etMail.getText().toString();
                    dob = etDob.getText().toString();
                    password = etPassword.getText().toString();

                    if(isNetworkConnected())
                    {
                        new SignUpUser().execute();
                    }
                    else
                    {
                        Toast.makeText(SignUp.this , "You Are Not Connected With Internet" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.GET_ACCOUNTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        permission,
                        333);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        callbackManager = CallbackManager.Factory.create();
        fbLoginManager = com.facebook.login.LoginManager.getInstance();
        fbLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("aa1", "onSuccess: ");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("aa", response.toString());
                                getFacebookData(object);
                                try {

                                    //          {"id":"1487362661358755","gender":"male","email":"aniljangir51@gmail.com","name":"Anil Jangir"}
                                    //          {"last_name":"Jangir","id":"1487362661358755","gender":"male","first_name":"Anil","email":"aniljangir51@gmail.com"}
                                    // Application code
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.e("aa1", "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("aa1", "onError: " + error.toString());
            }
        });
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("aa1", "onSuccess: ");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("aa", response.toString());
                                getFacebookData(object);
                                try {

                                    //          {"id":"1487362661358755","gender":"male","email":"aniljangir51@gmail.com","name":"Anil Jangir"}
                                    //          {"last_name":"Jangir","id":"1487362661358755","gender":"male","first_name":"Anil","email":"aniljangir51@gmail.com"}
                                    // Application code
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.e("aa1", "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("aa1", "onError: " + error.toString());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private Bundle getFacebookData(JSONObject object) {

        try {


            //   String imgurl = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=normal";
            URL profile_pic = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=200&height=150");
            regBean = new RegBean();
            regBean.setFbId(object.getString("id"));


            if (object.has("first_name"))
                regBean.setName(object.getString("first_name"));

            if (object.has("email"))
                regBean.setEmailId(object.getString("email") + "");

            if (object.has("gender"))
                regBean.setGender(object.getString("gender") + "");
            if (object.has("birthday"))
                regBean.setDob(object.getString("birthday"));
            if (object.has("location"))
                regBean.setAddress(object.getJSONObject("location").getString("name") + "");




            regBean.setProfilePicUrl(profile_pic.toString());
            networkWorkRegister  mRegisterTask = new networkWorkRegister("facebook");
            mRegisterTask.execute();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.e("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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



    private class SignUpUser extends AsyncTask<Void , String , String>
    {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(SignUp.this, " ", true, true,
                    null);
        }

        @Override
        protected String doInBackground(Void... params)
        {
            StaticData.SERVER_URL = "http://www.yeorder.com/adyep/rest/users/signup";
            RestClient client = null;
            client = new RestClient();
            client.AddParam("username" , name);
            client.AddParam("password" , password);
            client.AddParam("dob" , dob);
            client.AddParam("email" , mail);

            try
            {
                client.Execute(1);
                String responce = client.getResponse();
                Logger.e("responce" , responce);
                return responce;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "error";
            }


        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (null != mProgressHUD && mProgressHUD.isShowing()) {

                mProgressHUD.dismiss();

            }

            try {
                JSONObject obj = new JSONObject(s);
                //String message = obj.get("message").toString();

                //Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();

                if (obj.optString("status").equals("true")) {
                    showToast(SignUp.this, obj.optString("message"));

                    SharedPreferences pref = StaticData.pref(SignUp.this);

                    SharedPreferences.Editor e = pref.edit();
                    e.putString(StaticData.PREFKEY_USER_ID, obj.optString("userid"));

                    e.putString(StaticData.PREFKEY_USER_NAME, obj.optString("username"));
                    e.putString(StaticData.PREFKEY_USER_EMAIL_ID, obj.optString("email"));
                    e.putString(StaticData.PREFKEY_USER_DOB, obj.optString("dob"));
                    //e.putString(static_data.STORE_CAT_ID, value)
                    e.putBoolean(StaticData.Is_User_Login, true);
                    e.putString(StaticData.firsttime,"true");
                    //
                    e.commit();

                    Intent i = new Intent(SignUp.this, MainActivity.class);


                    startActivity(i);
                    finish();
                } else {
                    showToast(SignUp.this, obj.optString("message"));
                    //   Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();
                    //Staticutils.showToast(Login.this,obj.get("message")+"");

                }


            }catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public  void showToast(Context cntx, String message)
    {

        Toast toast = new Toast(cntx);
        View view = ((LayoutInflater)cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_toast, null);
//	   			View view = getLayoutInflater(context).inflate(R.layout.custom_toast, null);
        ((TextView)view.findViewById(R.id.toast_message)).setText(message);
        toast.setView(view);

        toast.show();

    }


    //


    private boolean isSubsetOf(Collection<String> subset,
                               Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
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



    //
    private class networkWorkRegister extends AsyncTask<Void , String , String>
    {
        ProgressDialog pd ;
        String Account_type="";
        public networkWorkRegister(String Account_type)
        {
            this.Account_type=Account_type;
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params)
        {
            StaticData.SERVER_URL = "http://www.yeorder.com/adyep/rest/users/socialLogin";
            RestClient client = null;
            client = new RestClient();

            client.AddParam("social_id" , regBean.getFbId());
            client.AddParam("username" , regBean.getName()+"");

            client.AddParam("profile_pic" , regBean.getProfilePicUrl()+"");
            client.AddParam("social_type" ,Account_type);
            client.AddParam("dob" ,regBean.getDob()+"");
            client.AddParam("email" ,regBean.getEmailId() + "");
            String responce;
            try
            {
                client.Execute(1);
                responce = client.getResponse();
                Logger.e("responce" , responce);
                return responce;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "error";
            }


        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (null != mProgressHUD && mProgressHUD.isShowing()) {

                mProgressHUD.dismiss();

            }

            try {
                JSONObject obj = new JSONObject(s);
                //String message = obj.get("message").toString();

                //Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();

                if (obj.optString("status").equals("true")) {
                    showToast(SignUp.this, obj.optString("message"));

                    SharedPreferences pref = StaticData.pref(SignUp.this);

                    SharedPreferences.Editor e = pref.edit();
                    e.putString(StaticData.PREFKEY_USER_ID, obj.optString("userid"));

                    //

                    e.putString(StaticData.PREFKEY_USER_PIC, obj.optString("profile_pic"));

                    e.putString(StaticData.PREFKEY_USER_NAME, obj.optString("username"));
                    e.putString(StaticData.PREFKEY_USER_EMAIL_ID, obj.optString("email"));
                    e.putString(StaticData.PREFKEY_USER_DOB, obj.optString("dob"));
                    //e.putString(static_data.STORE_CAT_ID, value)
                    e.putBoolean(StaticData.Is_User_Login, true);

                    e.putString(StaticData.firsttime,"true");
                    //
                    e.commit();

                    Intent i = new Intent(SignUp.this, MainActivity.class);


                    startActivity(i);
                    finish();
                } else {
                    showToast(SignUp.this, obj.optString("message"));
                    //   Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();
                    //Staticutils.showToast(Login.this,obj.get("message")+"");

                }


            }catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    private void popup() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Add Gmail account");
        helpBuilder.setMessage("These options rely on a Gmail account, but you don't seem to have one configured. Would you like to configure one now?");

        helpBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                AccountManager acm = AccountManager.get(getApplicationContext());
                acm.addAccount("com.google", null, null, null, SignUp.this, null, null);
            }
        });

        helpBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // close the dialog, return to activity
            }
        });

        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


    //



   /* @Override
    public void onConnected(Bundle arg0) {

        mSignInClicked = false;
        mProgressHUD = ProgressHUD.show(SignUp.this, "", true, true,
                null);
        getProfileInformation();
    }

    */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signOut();
    }

    /**
     * Fetching user's information name, email, profile pic
     * *//*
    private void getProfileInformation() {

        try {
            if(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                regBean = new RegBean();
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

                regBean.setFbId(currentPerson.getId());
                regBean.setEmailId(Plus.AccountApi.getAccountName(mGoogleApiClient));
                regBean.setName(currentPerson.getDisplayName());
                regBean.setAddress(currentPerson.getCurrentLocation());
                regBean.setGender(currentPerson.getGender() + "");
                regBean.setDob(currentPerson.getBirthday());
                regBean.setProfilePicUrl(currentPerson.getImage().getUrl());
                Logger.e("email11", regBean.getEmailId() + ","+regBean.getName());
                networkWorkRegister   mRegisterTask = new networkWorkRegister("google");
                mRegisterTask.execute();
            }
            else {
                Toast.makeText(getApplicationContext(), "Person information is null", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    //
    private void showDate(int year, int month, int day)
    {
        dateView.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
        dateView.setVisibility(View.VISIBLE);
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999)
        {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view)
    {
        showDialog(999);
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

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
           // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);

            regBean = new RegBean();
          //  Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            regBean.setFbId(acct.getId());
            regBean.setEmailId(acct.getEmail());
            regBean.setName(acct.getDisplayName());
            regBean.setAddress("");
            regBean.setGender( "Male");
            regBean.setDob("");
            Uri uri=acct.getPhotoUrl();
            if (uri!=null)
            {
                regBean.setProfilePicUrl(acct.getPhotoUrl().toString());
            }

          //  Logger.e("email11", regBean.getEmailId() + ","+regBean.getName());
            networkWorkRegister   mRegisterTask = new networkWorkRegister("google");
            mRegisterTask.execute();

            if (mGoogleApiClient.isConnected())
            {
                mGoogleApiClient.disconnect();
            }
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
       /* Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });*/
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
       /* if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }*/
    }
}