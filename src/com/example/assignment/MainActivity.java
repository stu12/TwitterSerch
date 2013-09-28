package com.example.assignment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	String value;
	EditText ed;
	boolean dx;
	int k=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final NetworkCheck N=new NetworkCheck(getApplicationContext());
		 Button bt1=(Button)findViewById(R.id.bt1);
		 ed=(EditText)findViewById(R.id.ed1);
		
		 
		  bt1.setOnClickListener(new Button.OnClickListener() { 
		    	@Override
		    	public void onClick(View v) {
					// TODO Auto-generated method stub
		    		value=ed.getText().toString();
		    		k=value.length();
		    		dx=N.isConnectingToInternet();
		    		if((dx==true)&&(k!=0))
		    		{
					Intent intent=new Intent(MainActivity.this,SecondActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("query", value);
					startActivity(intent);
					finish();
		    		
		    		}
		    	else
		    	{
		    		 Toast.makeText(MainActivity.this,"Please enter a valid value or check your internet connection.", Toast.LENGTH_LONG).show(); 	
		    	}
		    	}
	     });
		
	}

}
