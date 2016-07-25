/**
 * @author Anil
 * Class contains Static variables used in this app
 */

package com.talk.jounlist.talk_jurnlist.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.talk.jounlist.talk_jurnlist.R;


public class StaticData
{
	public static String userName = "yepaisa";
    public static final String PREFKEY_USER_EMAIL_ID = "EmailId";
    public static final String PREFKEY_USER_NAME = "Name";
    public static final String PREFKEY_USER_DOB = "Dob";
    public static final String PREFKEY_USER_PIC = "ProfilePic";
	public static String passWord = "b2lZRlcrQnVvazVwejZ4SG9tblRpQT09";
	public static String Is_User_Login="islogin";
    public static final String TEMP_PHOTO_FILE_NAME = "temp.jpg";
	public static final String PREFKEY_USER_ID = "UserId";
	public static final String PREFKEY_USER_PHONE = "phone";
	public static final String firsttime = "false";

	private static String LOG_TAG = "StaticData";
	public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "YePaisa";
	public static Context appContext;
	public static Resources res;
	public static float density;
	public static int screenWidth;
	public static int screenHeight;
	public static String imei;
	private static Display display;
	private static DisplayMetrics metrics;
	
	public static final String APP_PACKAGE_NAME = "com.yepaisa.mob";
	public static final String SIGNUP_ACTION="Signup";
	public static final String APP_MAINFOLDER = "YePaisa";
	public static final String APP_IMAGES = "Images";
	//public static final String APP_img_dir = MainAppClass.APP_directory + File.separator + APP_IMAGES;
	
	//public static final String SERVER_URL = "http://192.168.0.36/yepaisa/api/index.php";
	
	//YE_TEST/newapi
	//http://yepaisa.com/YE_API/newapi/users
	public static String SERVER_URL = "http://www.yeorder.com/adyep";
	//public static final String SERVER_URL = "http://yepaisa.com/testapi/index.php";
	public static final String SERVER_URL_HALF = "http://yepaisa.com/YE_API/newapi";

	public static final String PREFKEY_ISLOGIN = "IsLogin";
	public static final String PREFKEY_BANNER_URL = "Banner_Url";
	

	
	public static int FETCH_PAGESIZE = 20;
	
	/**
	 * Called in launcher activity to initialize required resources
	 **/
	public static void set_No_Network(View main_lay,View network_lay)
	{
		
		main_lay.setVisibility(View.GONE);
		network_lay.setVisibility(View.VISIBLE);
		main_lay.invalidate();
		network_lay.invalidate();
	}


	public static void startActivity(Activity context,Intent intent)
	{
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		//		 context.overridePendingTransition(R.drawable.activity_open_translate,R.drawable.activity_close_scale);
	}
	
	//


	public static SharedPreferences pref(Context ctx) {

		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	/*public static void backPress(Activity con)
	{
		con.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}*/


	
	/*public static void startActivity(Activity context,Intent intent)
	{
		context.startActivity(intent);
		context.overridePendingTransition(R.drawable.fadein,R.drawable.fade_out);
		//		 context.overridePendingTransition(R.drawable.activity_open_translate,R.drawable.activity_close_scale);
	}
	
	
	public static  void onBackPressed(Activity context) {
		// TODO Auto-generated method stub
		context.overridePendingTransition(R.drawable.activity_open_scale,R.drawable.activity_close_translate);
		
	}*/
	
	/*public static void Set_Level_Img(int i,ImageView img)
	{
		if(img!=null) {
			
			
			switch (i) {
				case 1:
					img.setImageResource(R.drawable.l1);
					break;
				case 2:
					img.setImageResource(R.drawable.l2);
					break;
				case 3:
					img.setImageResource(R.drawable.l3);
					break;
				case 4:
					img.setImageResource(R.drawable.l4);
					break;
				case 5:
					img.setImageResource(R.drawable.l5);
					break;
				case 6:
					img.setImageResource(R.drawable.l6);
					break;
				case 7:
					img.setImageResource(R.drawable.l7);
					break;
					
					
				default:
					//				/img.setImageResource(null);
					break;
			}
		}
	}*/
	
	public void Set_Current_date(int month1 , int Day)
	{
		Calendar c = Calendar.getInstance();

		int day=c.get(Calendar.DAY_OF_MONTH);
		int month=c.get(Calendar.MONTH)+1;
		int year=c.get(Calendar.YEAR);


		if (month>=month1) {

			if (day>=Day) {
				Log.e("", null);
			}
		}
	}
}
