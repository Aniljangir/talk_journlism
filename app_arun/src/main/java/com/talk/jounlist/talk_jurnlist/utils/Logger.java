package com.talk.jounlist.talk_jurnlist.utils;


/**
 * Logs are used within applications to facilitate development by displaying messages thereby 
 * allowing user to track the flow. This app also uses Logs to display messages in almost all files. 
 * But using logs, and that too in such a large number to display large chunks of data, disturbs 
 * the application speed. So to prevent the 10-15% memory consumption by logging, it is advisable 
 * to turn logs off when releasing the application. To implement this, this custom Log class is used. 
 * It contains methods to display logs using the default android.util.Log class. Moreover, the logs are 
 * saved in Log.txt file in the app folder. The static boolean variable LOG decides whether the Logs should 
 * be displayed in the LogCat or not. When preparing Live build or when providing .apk for testing, the developer 
 * must make the variable LOG false.
**/

public class Logger {
	
	//TODO: 
	/** 
	 *  during development mode --> LOG = true
	 *  Releasing application or giving build for testing --> LOG = false  
	 **/
	static final boolean LOG = true;
	    
	public static void i(String tag, String msg){
		if(LOG) 
			android.util.Log.i(tag, msg);
	}
	
	public static void e(String tag, String msg){
		if(LOG) 
			android.util.Log.e(tag, msg);
	}
	
	public static void d(String tag, String msg){
		if(LOG) 
			android.util.Log.d(tag, msg);
	}
	
	public static void v(String tag, String msg){
		if(LOG) 
			android.util.Log.v(tag, msg);
	}
	
	public static void w(String tag, String msg){
		if(LOG) 
			android.util.Log.w(tag, msg);
	}

}
