package com.example.account;

import android.os.Bundle;
//import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class Net1314080903221MainActivity extends TabActivity {
	private TabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		tabHost=getTabHost();
		Resources localResources = getResources();
		
		Intent localIntent2 = new Intent();
	    localIntent2.setClass(this, QueryBill.class);
        tabHost.addTab(tabHost.newTabSpec("�˵���ѯ")
                .setIndicator("�˵���ѯ",localResources.getDrawable(R.drawable.zhangdanchaxun))
                .setContent(localIntent2));  
		
		Intent localIntent1 = new Intent();
	    localIntent1.setClass(this, AddEvent.class);

		TabHost.TabSpec localTabSpec1=tabHost.newTabSpec("���֧��");
		localTabSpec1.setIndicator("���֧��",localResources.getDrawable(R.drawable.tianjiazhangdan));
        //localTabSpec1.setContent(R.id.RelativeLayout01);
		localTabSpec1.setContent(localIntent1);
		tabHost.addTab(localTabSpec1);
        /*
		LayoutInflater.from(this).inflate(R.layout.baobiao, tabHost.getTabContentView(), true);*/
        Intent localIntent4 = new Intent();
	    localIntent4.setClass(this, shouru.class);
        tabHost.addTab(tabHost.newTabSpec("�������")
                .setIndicator("�������",localResources.getDrawable(R.drawable.tongjibaobiao))
                .setContent(localIntent4));
		//LayoutInflater.from(this).inflate(R.layout.activity_main, tabHost.getTabContentView(), true);
        Intent localIntent3 = new Intent();
	    localIntent3.setClass(this, shezhi.class);
        tabHost.addTab(tabHost.newTabSpec("�������")
                .setIndicator("�������",localResources.getDrawable(R.drawable.fenleichaxun))
                .setContent(localIntent3));
        


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
