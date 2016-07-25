package com.talk.jounlist.talk_jurnlist.utils;

import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RestClient 
{

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private int responseCode;
	private String message;
	private String response;

	public String getResponse()
	{
		return response;
	}

	public String getErrorMessage() 
	{

		return message;
	}

	public int getResponseCode()
	{
		return responseCode;
	}

	public RestClient() 
	{
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void AddParam(String name, String value)
	{

		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) 
	{

		headers.add(new BasicNameValuePair(name, value));
	}

	public void Execute(int method) throws Exception 
	{

		// int method 0 for get, 1 for POST
		switch (method) 
		{
			case 0: 
			{
				// add parameters
				String combinedParams = "";
				if(!params.isEmpty()) 
				{
					combinedParams += "?";
					for (NameValuePair p : params) 
					{
						String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
						if(combinedParams.length() > 1) 
						{
							combinedParams += "&" + paramString;
						}
						else 
						{
							combinedParams += paramString;
						}
						
					}
					Logger.e("params", combinedParams);
				}
				HttpGet request = new HttpGet(StaticData.SERVER_URL + combinedParams);
					Logger.e("Final URI --> ", StaticData.SERVER_URL + combinedParams);
	
				request.addHeader("Authorization", "Basic " +
						Base64.encodeToString((StaticData.userName+":"+StaticData.passWord).getBytes(), Base64.NO_WRAP));
				// add headers
				for (NameValuePair h : headers) 
				{
	
					request.addHeader(h.getName(), h.getValue());
				}
				executeRequest(request, StaticData.SERVER_URL);
				break;
			}
			case 1: 
			{
				HttpPost request = new HttpPost(StaticData.SERVER_URL);
				//request.addHeader("Authorization", "Basic " +
					//	Base64.encodeToString((StaticData.Username+":"+static_data.password).getBytes(), Base64.NO_WRAP));
				// add headers
				for (NameValuePair h : headers) 
				{
					request.addHeader(h.getName(), h.getValue());
				}
				if(!params.isEmpty()) 
				{	
					Logger.e("params", params.toString());
					request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				}
	
				executeRequest(request, StaticData.SERVER_URL);
				break;
			}
			case 2:
			{
				HttpPost request = new HttpPost("http://yepaisa.com/api/index.php");
				//				request.addHeader("Authorization", "Basic " +
				//					    Base64.encodeToString((static_data.Username+":"+static_data.password).getBytes(), Base64.NO_WRAP));
				// add headers
				for (NameValuePair h : headers) 
				{
					request.addHeader(h.getName(), h.getValue());
				}
				if(!params.isEmpty()) 
				{
					request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				}
	
				executeRequest(request, StaticData.SERVER_URL);
				break;
			}
		}
	}

	private void executeRequest(HttpUriRequest request, String url) 
	{

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 40000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 40000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpClient client = new DefaultHttpClient(httpParameters);

		HttpResponse httpResponse;

		Logger.e("request --> ", request.toString());
		Logger.e("url --> ", url);
		try 
		{
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();
			if(entity != null) 
			{
				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);
				//	Log.e("res", response);
				// Closing the input stream will trigger connection release
				instream.close();
			}
		}
		catch (ConnectTimeoutException e) 
		{
			Log.e("Timeout Exception: ", e.toString());
			//response=StaticData.Timeout_Exception;

		}
		catch (SocketTimeoutException ste) 
		{
			//Log.e("Socket Timeout Exception: ", ste.toString());
			//response=StaticData.Socket_timeOut_Exception;
		}
		catch (ClientProtocolException e)
		{
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
		//	Logger.i("response --> ", response);
	}

	private static String convertStreamToString(InputStream is) 
	{

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				is.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
