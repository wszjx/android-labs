package com.example.messagebrower;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.messagebrower.domain.Messages;
import com.loopj.android.image.SmartImageView;

import android.renderscript.ScriptGroup.Input;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony.Sms.Conversations;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	List<Messages> messagesList;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			ListView lv = (ListView) findViewById(R.id.lv);
			lv.setAdapter(new MyAdapter());
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getMessageInfo();
		
		//ListView lv = (ListView) findViewById(R.id.lv);
		//��ʱ��Ϣ��Ϣ��û�н�����ϣ�messagesList��û��new����
		//lv.setAdapter(new MyAdapter());
	}

	class MyAdapter extends BaseAdapter{

		//���ص�Ҫ��ʾ������
		@Override
		public int getCount() {
			return messagesList.size();
		}
		
		//����һ��View���󣬻���ΪListView��һ����Ŀ��ʾ�ڽ�����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Messages messages = messagesList.get(position);
			View v = null;
			if (convertView == null) {
				v = View.inflate(MainActivity.this, R.layout.item_listview, null);
			}
			else{
				v = convertView;
			}
			
			//View v = View.inflate(MainActivity.this, R.layout.item_listview, null);
			
			//����Ŀ�е�ÿ���������Ҫ��ʾ������
			TextView username = (TextView) v.findViewById(R.id.username);
			username.setText(messages.getToUserName());
			TextView createtime = (TextView) v.findViewById(R.id.createtime);
			createtime.setText(messages.getCreateTime());
			TextView content = (TextView) v.findViewById(R.id.content);
			content.setText(messages.getContent());
			
			SmartImageView siv = (SmartImageView) v.findViewById(R.id.siv);
			siv.setImageUrl(messages.getImageUrl());
			
			return v;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return messagesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
	}
	
	private void getMessageInfo() {

		Thread t = new Thread(){
			@Override
			public void run() {
				String path = "https://raw.githubusercontent.com/Pyanz/Test/master/drawable/messages.xml";
				//String path = "http://192.168.0.101:8080/messages.xml";
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(8000);
					conn.setReadTimeout(8000);

					if (conn.getResponseCode() == 200) {
						//���������Ϣ��һ��xml�ļ����ı���Ϣ����xml��������������Ҫ��Ϊ�ı�ȥ����
						InputStream is = conn.getInputStream();
						getMessagesFromStream(is);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		t.start();
	}
	
	private void getMessagesFromStream(InputStream is) {

		XmlPullParser xp = Xml.newPullParser();
		try {
			xp.setInput(is,"utf-8");
			
			//��ȡ�¼����ͣ�ͨ���¼������жϳ���ǰ��������ʲô�ڵ�
			int type = xp.getEventType();
			
			Messages messages = null;
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if ("messageslist".equals(xp.getName())) {
						messagesList = new ArrayList<Messages>();
					}
					else if ("message".equals(xp.getName())) {
						messages = new Messages();
					}
					else if ("ToUserName".equals(xp.getName())) {
						String ToUserName = xp.nextText();
						messages.setToUserName(ToUserName);
					}
					else if ("CreateTime".equals(xp.getName())) {
						String CreateTime = xp.nextText();
						messages.setCreateTime(CreateTime);
					}
					else if ("Content".equals(xp.getName())) {
						String Content = xp.nextText();
						messages.setContent(Content);
					}
					else if ("imageUrl".equals(xp.getName())) {
						String imageUrl = xp.nextText();
						messages.setImageUrl(imageUrl);
					}
					break;
					
				case XmlPullParser.END_TAG:
					if ("message".equals(xp.getName())) {
						messagesList.add(messages);
					}
					break;
				}
				//ָ���ƶ�����һ���ڵ㲢�����¼�����
				type = xp.next();
			}
			//������Ϣ�������߳�ˢ��ListView
			handler.sendEmptyMessage(1);
			
//			for (Messages n : messagesList) {
//				System.out.println(n.toString());
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}














