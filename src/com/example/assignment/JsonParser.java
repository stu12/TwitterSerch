package com.example.assignment;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class JsonParser {

	 InputStream is = null;
	   JSONObject jObj = null;
	   String json = "";


public JSONObject getJSONFromUrl(String url) 
{
	try
	{
		
	    DefaultHttpClient httpclient1 = new DefaultHttpClient(new BasicHttpParams());
		HttpGet httpget1 = new HttpGet(url);
		httpget1.setHeader("Authorization", "Bearer " + SecondActivity.bearer_token );
		httpget1.setHeader("Content-type", "application/json");

		
		HttpResponse response1 = httpclient1.execute(httpget1);
		HttpEntity entity1 = response1.getEntity();
		is = entity1.getContent();
		
	}
	
	catch(Exception e)
	{
   Log.e("serch problem", "Error " + e.toString());	
	}
	
   try 
   {
       BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
       StringBuilder sb = new StringBuilder();
       String line = null;
       while ((line = reader.readLine()) != null) 
       {
           sb.append(line + "\n");
       }
       is.close();
       json = sb.toString();
   } 
   catch (Exception e) 
   {
       Log.e("Buffer Error", "Error converting result " + e.toString());
   }

   try 
   {
       jObj = new JSONObject(json);
   }
   catch (JSONException e) 
   {
   Log.e("JSON Parser", "Error parsing data " + e.toString());
   }

   return jObj;
   }


}
