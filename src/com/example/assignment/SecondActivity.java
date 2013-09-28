package com.example.assignment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends Activity{
	
	String URL = "https://api.twitter.com/1.1/search/tweets.json?q=#:";
	final String Consumer_Key = "Yj9A8g1CceptIDDIEQZKw";
	final String Consumer_Secret = "RbCEvmnBgxHm3rbYaAz4yesHcdGYWylu1CveA6miPg";
	Intent i1;
	String abc,bc,cd,de;
	InputStream inputStream = null;
	JSONArray con = null;
	JsonParser jParser;
	ProgressDialog mProgressDialog ;
	JSONArray c = null;
	static String bearer_token;
	ArrayList<String> foundtweets;
	 ListView lt1;
	 ArrayAdapter<String> arrayAdapter ;
	 
	 @Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.listview1);
		    lt1=(ListView)findViewById(R.id.list1);
			i1=getIntent();
			abc=i1.getStringExtra("query");
			bc=URL+abc;
			Log.i("abc",abc);
			foundtweets=new ArrayList<String>();
			arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,foundtweets);
			new GetToken().execute();
			jParser = new JsonParser();
		              	
		}
		
		
		protected class GetToken extends AsyncTask<Void, Void, String> 
		{
			  @Override
			    protected void onPreExecute() {
			        super.onPreExecute();
			        mProgressDialog = new ProgressDialog(SecondActivity.this);
			        mProgressDialog.setTitle("SEARCHING");
			        mProgressDialog.setMessage("Searching for tweets,Please wait...");
			        mProgressDialog.setIndeterminate(false);
			        mProgressDialog.show();
			    }

	      
			@Override
			protected String doInBackground(Void... params) {
				
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
					HttpPost httppost = new HttpPost("https://api.twitter.com/oauth2/token");
					
					String apiString = Consumer_Key + ":" + Consumer_Secret;
					String authorization = "Basic " + Base64.encodeToString(apiString.getBytes(), Base64.NO_WRAP);
			
					httppost.setHeader("Authorization", authorization);
					httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
					httppost.setEntity(new StringEntity("grant_type=client_credentials"));
					
			
					InputStream inputStream = null;
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
			
					inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();
					
			
					String line = null;
					while ((line = reader.readLine()) != null)
					{
					    sb.append(line + "\n");
					}
					
					cd=sb.toString();
					JSONObject root = new JSONObject(cd);
					bearer_token = root.getString("access_token");	
					Log.i("token",bearer_token);
					
					JsonParser jParser = new JsonParser();
					 
					JSONObject json = jParser.getJSONFromUrl(bc);
					 
					try 
					{
					c = json.getJSONArray("statuses"); 
					
				    for(int i = 0; i < c.length(); i++)
					{
					JSONObject cp = c.getJSONObject(i);
					String id1 = cp.getString("text");
					foundtweets.add(id1);
					Log.i("value",id1);
					}
					} 
					catch (JSONException e) 
					{
					    e.printStackTrace();
					}
					
					return de;
				    }
				    catch (Exception e)
				    {
					Log.e("GetToken", "Error:" + e.getMessage());
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(String jsonText)
			{
				mProgressDialog.dismiss();
				lt1.setAdapter(arrayAdapter);
				
			}
	    }

}
