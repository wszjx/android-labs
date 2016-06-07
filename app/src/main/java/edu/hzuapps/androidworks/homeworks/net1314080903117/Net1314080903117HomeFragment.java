package edu.hzuapps.androidworks.homeworks.net1314080903117;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.hzuapps.androidworks.homeworks.net1314080903117.Net1314080903117ReflashListView.IReflashListener;

/**
 * �ý���Ϊ��ҳ���棬ʵ�����Զ���ListView�е�����ˢ�µĽӿ�
 * @author Charlie
 *
 */
@SuppressLint("SimpleDateFormat")
public class Net1314080903117HomeFragment extends Fragment implements IReflashListener {

	private List<Net1314080903117Message> mList;//List����������΢����Ϣ���ݵ����
	private Net1314080903117MessageAdapter mAdapter;//ListView��Adapter������
	private Net1314080903117ReflashListView mReflashListView;//�Զ����ListView
	/**
	 * ���ڻ�ȡ��ǰʱ���ʱ���ʽ��ת��
	 */
	private Date date ;
	private SimpleDateFormat format;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/**
		 * ������ԴID �󶨵�ǰ����Ĳ����ļ�
		 */
		View view = inflater.inflate(R.layout.net1314080903117fragment_home, container,false);
		/*
		 * �Զ���ListView�ؼ��Ĳ���
		 */
		mReflashListView = (Net1314080903117ReflashListView) view.findViewById(R.id.id_reflash_Listview);
		/**
		 * ��ǰϵͳʱ��ĳ�ʼ��
		 * formatʱ��ת��Ϊ��yyyy-MM-dd HH:mm:ss��ʽ
		 */
		date = new Date();
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//���ڳ�ʼ��΢����Ϣ����
		initData();
		/**
		 * mAdapter ΪListView����������������г�ʼ�������뵱ǰ�����ģ���װ��΢����Ϣ���ݵ�List����
		 */
		mAdapter = new Net1314080903117MessageAdapter(getContext(), mList);
		//ΪListView��������������������ˢ�ºͼ��ظ���ļ���
		mReflashListView.setAdapter(mAdapter);
		mReflashListView.setReflashListener(this);
		return view;
	}

	/**
	 * ���ڳ�ʼ��΢����ҳ΢����Ϣ����ʾ����
	 */
	private void initData() {
		mList = new ArrayList<>();
		Net1314080903117Message msg = null ;
		for (int i = 0; i < 15; i++) {
			msg = new Net1314080903117Message();
			/**
			 * ΢����Ϣͷ�����getRandomIcon�����������һ��ͷ��
			 */
			msg.setMessageIcon(getRandomIcon());
			msg.setMessageUser("Charlie");
			msg.setMessageContent("����΢�������ݣ������"+i);
			msg.setMessageTime(format.format(date));
			mList.add(msg);//��΢����Ϣ��ӵ�List������
		}
		
		
	}
	/**
	 * ����0-6���������
	 * ���ݲ����������������һ��ͷ����ԴId
	 * @return
	 */
	private int getRandomIcon(){
		Random rand = new Random();
		int randNum = rand.nextInt(6);
		if (randNum==0) {
			return R.drawable.net1314080903117icon1;
		}
		if (randNum==1) {
			return R.drawable.net1314080903117icon2;
		}
		if (randNum==2) {
			return R.drawable.net1314080903117icon3;
		}
		if (randNum==3) {
			return R.drawable.net1314080903117icon4;
		}
		if (randNum==4) {
			return R.drawable.net1314080903117icon5;
		}
		if (randNum==5) {
			return R.drawable.net1314080903117icon6;
		}
		return R.drawable.net1314080903117icon3;
	}

	/**
	 * ʵ������ˢ�º�ķ���
	 */
	@Override
	public void onReflash() {
		/*
		 * ���Ȼ�ȡ��������
		 * �����ִ�и÷����������������
		 * Ȼ��֪ͨ������ʾ
		 * ���ListViewˢ���������
		 */
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//���ø÷������ˢ������
				mList = addNewData(mList);
				//ˢ�����
				mReflashListView.reflashComplete();
				//����������
				mAdapter.notifyDataSetChanged();
			}
		}, 2000);
	}

	/**
	 * ������������
	 */
	@Override
	public void onLoad() {
		/*
		 * ���Ȼ�ȡ��������
		 * �����ִ�и÷����������������
		 * Ȼ��֪ͨ������ʾ
		 * ���ListViewˢ���������
		 */
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//���ü������ݵķ���
				mList = addLoadData(mList);
				mReflashListView.reflashComplete();
				mAdapter.notifyDataSetChanged();
			}
		}, 2000);
	}
	
	/**
	 * ����������µ����ݵ�List������
	 * 
	 * @param list ԭ�ȵ�������������������ϼ������
	 * @return
	 */
	private List<Net1314080903117Message> addNewData(List<Net1314080903117Message> list){
		Net1314080903117Message msg= null;
		for (int i = 0; i < 3; i++) {
			msg = new Net1314080903117Message();
			msg.setMessageIcon(R.drawable.net1314080903117icon1);
			msg.setMessageUser("Charlieˢ��");
			msg.setMessageTime(format.format(date));
			msg.setMessageContent("��������ˢ�³�����������"+i);
			list.add(0,msg);//��0��ʼ��ӣ��Ϳ��Խ�������ӵ�������ʾ
		}
		return list;
		
	}
	/**
	 * ���ڼ������������ݣ���ӵ�ԭ��������
	 * @param list
	 * @return
	 */
	private List<Net1314080903117Message> addLoadData(List<Net1314080903117Message> list){
		Net1314080903117Message msg= null;
		for (int i = 0; i < 3; i++) {
			msg = new Net1314080903117Message();
			msg.setMessageIcon(R.drawable.net13140809031117iconload);
			msg.setMessageUser("��Charlie");
			msg.setMessageTime(format.format(date));
			msg.setMessageContent("���Ǽ��ص�������"+i);
			list.add(msg);
		}
		return list;
	}

}
