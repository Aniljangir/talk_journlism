package com.talk.jounlist.talk_jurnlist;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.talk.jounlist.talk_jurnlist.utils.Logger;
import com.talk.jounlist.talk_jurnlist.utils.ProgressHUD;
import com.talk.jounlist.talk_jurnlist.utils.StaticData;

/**
 * Created by Arun on 6/27/2016.
 */
public class Sessionframent extends Fragment {

    private WebView webView;
    private String url = null;
    private ProgressHUD progressBar;
    private int pageType = -1;
    private String LOG_TAG = "WebActivity";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.session, container, false);

        //tv_error=(TextView) rootView.findViewById(R.id.tv_error);

        webView = (WebView) rootView.findViewById(R.id.web);
       // progressBar = ProgressHUD.show(getActivity(),"Loading...", true,true,null);
        //	progressBar.setCancelable(false);


        url="http://www.talkjournalism.in/sessions1/";

        if(url!=null) {
            Logger.e("url", url);
        }

        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.requestFocusFromTouch();
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {

            try {
                webView.getSettings().setDisplayZoomControls(false);
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new FbWebViewClient());
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

       /* if(isNetworkConnected())
        {
            webView.loadUrl(url);
        }
        else
        {
            Toast.makeText(getActivity() , "You Are Not Connected With Internet" , Toast.LENGTH_SHORT).show();
        }*/
        CookieManager.getInstance().setAcceptCookie(true);
        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Fetch data or something...
            if(isNetworkConnected())
            {
                progressBar = ProgressHUD.show(getActivity(),"Loading...", true,true,null);
                webView.loadUrl(url);
            }
            else
            {
                Toast.makeText(getActivity() , "You Are Not Connected With Internet" , Toast.LENGTH_SHORT).show();
            }


        }
    }
    private class FbWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Logger.d("******", "shouldOverrideUrlLoading");

            //webView.loadUrl(url);

            if(url.contains("meraref=")) {
                String[] separated =url.split("=");

                if(!separated[1].equals(""))
                {
                    String url1="yepaisa.com/burl?refcode="+separated[1];
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "YePaisa");

                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Your friend invited you at yepaisa," +
                            " Download yepaisa by "+url1);

                    ///	shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Your friend invited you at yepaisa, your friend's referral code is "+separated[1]+"  Download YePaisa at :-https://goo.gl/jKWVgB");
                    //	shareIntent.putExtra(android.content.Intent.EXTRA_REFERRER, "https://play.google.com/store/apps/details?id=com.yepaisa.mob");

                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                }
            }
            else {


                String scheme= Uri.parse(url).getScheme();
                if (Uri.parse(url).getScheme().equals("market")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        Activity host = (Activity) view.getContext();
                        host.startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException e) {
                        Uri uri = Uri.parse(url);
                        view.loadUrl("http://play.google.com/store/apps/" + uri.getHost() + "?" + uri.getQuery());
                        return false;
                    }

                }
                else if (url.contains("https://play.google.com/store/apps/details?id=")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        Activity host = (Activity) view.getContext();
                        host.startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException e) {
                        // Google Play app is not installed, you may want to open the app store link
                        // webView.loadUrl(url);
                        Uri uri = Uri.parse(url);
                        view.loadUrl("http://play.google.com/store/apps/" + uri.getHost() + "?" + uri.getQuery());
                        return false;
                    }

                }
                else {
                    webView.loadUrl(url);
                }
            }
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            progressBar.dismiss();
            webView.loadUrl("file:///android_asset/error.html");
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            progressBar.dismiss();

        }

    }

    private boolean isNetworkConnected()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
