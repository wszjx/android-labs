package edu.hzuapps.androidworks.homeworks.com1314080901109

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Com1314080901109Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void handleSample1(View v) {
		startActivity(new Intent(this, Sample1.class));
	}

	public void handleSample2(View v) {
		startActivity(new Intent(this, Sample2.class));
	}
	
}
