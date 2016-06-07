package com.example.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.MyPackage;
import models.TradeClass;
import models.consumeClass;
import models.incomeClass;

public class Net1314080903221QueryByTodayActivity extends Activity
{
  private ListView listView;
  private TextView textView;
  private Adapter_TD myadapter;
  Map<typeClass, Boolean> localmap;
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.querybytoday);
    //ivinfo=(ListView)findViewById(R.id.listView);
    //setAdapter();
    GetTodayBill();
  }
  


  private void GetTodayBill() {
	// TODO Auto-generated method stub
	//ArrayAdapter<String>  adapter=null;
	listView =(ListView)findViewById(R.id.listView);
	textView =(TextView)findViewById(R.id.textView);
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	float todaymenoy=0;
	MyPackage pack=new MyPackage(this);
	List<TradeClass> List=pack.getAlltrade();
	//bill_array=new String[List.size()];
	int i=0;
    Calendar localCalendar = Calendar.getInstance();
    int year = localCalendar.get(Calendar.YEAR);
    int month = localCalendar.get(Calendar.MONTH)+1;
    int day = localCalendar.get(Calendar.DAY_OF_MONTH);
	String str1=new String(year+"-"+month+"-"+day);
	String str;
	for(TradeClass con:List){
			str=con.gettime();
			if(str1.equals(str)){
				//bill_array[i]=con.getId()+"|*****|"+con.getMoney()+"\n"+con.getPocketType()+"|****|"+con.gettime();
				todaymenoy+=con.getMoney();
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("_id", con.getId());
				map.put("money", ""+con.getMoney());
				if(con.getPocketType().equals("�ճ�����")){
					map.put("icon",R.drawable.richanggouwu);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("��������")){
					map.put("icon",R.drawable.jiaojisongli);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("��������")){
					map.put("icon",R.drawable.canyingkaixiao);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("��������")){
					map.put("icon",R.drawable.gouziyiwu);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("���ֿ���")){
					map.put("icon",R.drawable.yulekaixiao);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("ˮ��ú��")){
					map.put("icon",R.drawable.shuidianmeiqi);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("���ѻ���")){
					map.put("icon",R.drawable.wannluohuafei);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("��ͨ����")){
					map.put("icon",R.drawable.jiaotongchuxing);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("��������")){
					map.put("icon",R.drawable.qita);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("��������")){
					map.put("icon",R.drawable.gongzi);
					map.put("classtype",1);
				}else if(con.getPocketType().equals("��Ʊ����")){
					map.put("icon",R.drawable.gupiao);
					map.put("classtype",1);
				}else {
					map.put("icon",R.drawable.qita);
					map.put("classtype",1);
				}
				map.put("time", con.gettime());
				map.put("type", con.getPocketType());
				list.add(map);
				i++;
			}
	}
	localmap=new HashMap<typeClass, Boolean>();
	myadapter=new Adapter_TD(this, list, localmap);
	textView.setText("���չ����ѣ�"+(-todaymenoy)+"Ԫ");
	listView.setAdapter(myadapter);
	if(i==0){
		Toast.makeText(getApplicationContext(), "��������û������Ŷ��", Toast.LENGTH_SHORT);
	}
}

public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(0, 1, 1, "ɾ��").setIcon(R.drawable.delete);
    return super.onCreateOptionsMenu(paramMenu);
  }


  public boolean onOptionsItemSelected(MenuItem paramMenuItem){
	  	// = Adapter_TD.isSelected;
	    if (localmap.size() <= 0)
	    {
	      Toast.makeText(this, "����ѡ��Ҫɾ�������Ѽ�¼!", 0).show();
	      return true;
	    }
        consumeClass tradeconsume=new consumeClass(0, 0, "", "123", "", Net1314080903221QueryByTodayActivity.this);
        incomeClass tradeincome=new incomeClass(0, 0, "", "123", "", Net1314080903221QueryByTodayActivity.this);
	    Iterator it = localmap.entrySet().iterator();
	    while (it.hasNext()) {
		     Map.Entry entry = (Map.Entry) it.next();
		     Object key = entry.getKey();
		     Object value = entry.getValue();
		     if((Boolean)value){
		    	 if(((typeClass)key).type==0){
		    		 int success=tradeconsume.trade_delect(((typeClass)key)._id);
		    		 if(success==1)Toast.makeText(this, "ɾ�����Ѽ�¼�ɹ�!", 0).show();
		    	 }else{
		    		 int success=tradeincome.trade_delect(((typeClass)key)._id);
		    		 if(success==1)Toast.makeText(this, "ɾ�������¼�ɹ�!", 0).show();
		    	 }
		    }
		     //Log.i("nihao","key=" + key + " value=" + value);
	    }
	    GetTodayBill(); 
	    return true;  
  }
  
  protected void onResume()
  {
	GetTodayBill();  
    super.onResume();
  }
}