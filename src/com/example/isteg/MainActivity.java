package com.example.isteg;









import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {
	ImageButton button,button1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button = (ImageButton) findViewById(R.id.ImageButton1);
        button1 = (ImageButton) findViewById(R.id.ImageButton2);
        
		// Capture button clicks
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
 
				// Start NewActivity.class
				Intent myIntent = new Intent(MainActivity.this,
						Text.class);
				startActivity(myIntent);
			}
		});
        

		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
 
				// Start NewActivity.class
				Intent myIntent = new Intent(MainActivity.this,
						Audio.class);
				startActivity(myIntent);
			}
		});
        
        	
     
    }


    public void sendMessage(View view) {
    	Intent intent = new Intent(this, Text.class);
    	 startActivity(intent);
    }
}
