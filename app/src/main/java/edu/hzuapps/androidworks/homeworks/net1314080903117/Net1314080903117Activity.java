package edu.hzuapps.androidworks.homeworks.net1314080903117;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �����ǳ����MainActivity
 * �̳���FragmentActivity
 * ʵ���˵�������¼�
 * @author Charlie
 *
 */
public class Net1314080903117Activity extends FragmentActivity implements OnClickListener {
	//���ײ����ּ���
	private LinearLayout mTabHome;
	private LinearLayout mTabMessage;
	private LinearLayout mTabSendMsg;
	private LinearLayout mTabSearch;
	private LinearLayout mTabMy;
	
	//�ײ�Tab��ť��ͼ�갴ť
	private ImageButton mImgHome;
	private ImageButton mImgMessage;
	private ImageButton mImgSendMsg;
	private ImageButton mImgSearch;
	private ImageButton mImgMy;

	//�ײ�Tab��ť�����ֿؼ�
	private TextView mTextHome;
	private TextView mTextMessage;
	private TextView mTextSearch;
	private TextView mTextMy;

	//���������Fragment
	private Fragment mFrgHome;
	private Fragment mFrgMessage;
	private Fragment mFrgSendMsg;
	private Fragment mFrgSearch;
	private Fragment mFrgMy;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_net1314080903117);
	
		//ʵ�����ؼ�
		initView();
		//���ڳ�ʼ���¼�
		initEvent();
		//�����������Ĭ�ϼ���Home��ҳ�Ľ���
		setSelect(0);
		
	}


	/**
	 * ���������Ҫ���ڿؼ��ĳ�ʼ�����������֣��͸�����ť
	 */
	private void initView() {
		/**
		 * Tab LinearLayout���ֲ���
		 */
		mTabHome = (LinearLayout) findViewById(R.id.tab_home);
		mTabMessage = (LinearLayout) findViewById(R.id.tab_message);
		mTabSendMsg = (LinearLayout) findViewById(R.id.tab_sendmessage);
		mTabSearch = (LinearLayout) findViewById(R.id.tab_search);
		mTabMy = (LinearLayout) findViewById(R.id.tab_myself);
		
		/**
		 * Tab ��ť�е�ͼ��Button����
		 */
		mImgHome = (ImageButton) findViewById(R.id.tab_home_img);
		mImgMessage = (ImageButton) findViewById(R.id.tab_message_img);
		mImgSendMsg = (ImageButton) findViewById(R.id.tab_sendmessage_img);
		mImgSearch = (ImageButton) findViewById(R.id.tab_search_img);
		mImgMy = (ImageButton) findViewById(R.id.tab_myself_img);

		/**
		 * Tab ��ť�еײ�������TextView����
		 */
		mTextHome = (TextView) findViewById(R.id.tab_home_tv);
		mTextMessage = (TextView) findViewById(R.id.tab_message_tv);
		mTextSearch = (TextView) findViewById(R.id.tab_search_tv);
		mTextMy = (TextView) findViewById(R.id.tab_myself_tv);


	}
	
	/**
	 * �ؼ������¼���ע��
	 */
	private void initEvent() {
		mTabHome.setOnClickListener(this);
		mTabMessage.setOnClickListener(this);
		mTabSendMsg.setOnClickListener(this);
		mTabSearch.setOnClickListener(this);
		mTabMy.setOnClickListener(this);
		
	}
	
	/**
	 * ѡ��ǰ����
	 * @param i
	 */
	private void setSelect(int i){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		/**
		 * ����ʵ�µĽ����ʱ��������֮ǰ�Ľ���
		 * ͨ��Switch�ж�
		 */
		hideFragment(transaction);
		
		switch (i) {
		case 0:
			/**
			 * ���жϵ�ǰ�����Ƿ���ڣ��ٽ��н�����ƣ���ֹ�ڴ��˷�
			 * 1.��ǰ��ͼ�����ڣ��ʹ���
			 * 2.��ͼ���ڣ���ֱ����ʾ
			 * ����case Ҳһ��
			 */
			if (mFrgHome == null) {
				mFrgHome = new Net1314080903117HomeFragment();

				
				transaction.add(R.id.id_content, mFrgHome);
			}else {
				transaction.show(mFrgHome);
			}
			//ѡ���˸ý��棬���Ӧ��ͼ�껻������ɫ��
			mImgHome.setImageResource(R.mipmap.tabbar_home_highlighted);
			//ѡ���˸ý��棬���Ӧ��������ɫҲ�ı�
			mTextHome.setTextColor(Color.parseColor("#FF8E29"));
			break;
		case 1:
			if (mFrgMessage == null) {
				mFrgMessage = new Net1314080903117MessageFragment();
				transaction.add(R.id.id_content, mFrgMessage);
				
			}else {
				transaction.show(mFrgMessage);
			}
			mImgMessage.setImageResource(R.mipmap.tabbar_message_center_highlighted);
			mTextMessage.setTextColor(Color.parseColor("#FF8E29"));
			break;
			
		case 2:
			if (mFrgSendMsg == null) {
				mFrgSendMsg = new Net1314080903117SendMsgFragment();
				transaction.add(R.id.id_content, mFrgSendMsg);
			}else {
				transaction.show(mFrgSendMsg);
			}
			mImgSendMsg.setImageResource(R.mipmap.navigationbar_subsribe_manage_highlighted);
			break;
			
		case 3:
			if (mFrgSearch == null) {
				mFrgSearch = new Net1314080903117SearchFragment();
				transaction.add(R.id.id_content, mFrgSearch);
				
			}else {
				transaction.show(mFrgSearch);
			}
			mImgSearch.setImageResource(R.mipmap.tabbar_discover_highlighted);
			mTextSearch.setTextColor(Color.parseColor("#FF8E29"));
			break;
		case 4:
			if (mFrgMy == null) {
				mFrgMy = new Net1314080903117MyFragment();
				transaction.add(R.id.id_content, mFrgMy);
			}else {
				transaction.show(mFrgMy);
			}
			mImgMy.setImageResource(R.mipmap.tabbar_profile_highlighted);
			mTextMy.setTextColor(Color.parseColor("#FF8E29"));
			break;
		

		default:
			break;
		}
		//�ύ����
		transaction.commit();
	}
	
	/**
	 * ӵ������Fragment����ֹ�����ص�
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction)
	{
		/**
		 * �жϵ�ǰ��ʾ�����Ƿ�Ϊ�գ���Ϊ�վ���Ҫ���أ��ձ�ʾ�ý��沢δ��ʾ���������ز���
		 */
		if (mFrgHome != null)
		{
			transaction.hide(mFrgHome);
		}
		if (mFrgMessage != null)
		{
			transaction.hide(mFrgMessage);
		}
		if (mFrgSendMsg != null)
		{
			transaction.hide(mFrgSendMsg);
		}
		if (mFrgSearch != null)
		{
			transaction.hide(mFrgSearch);
		}
		if (mFrgMy != null)
		{
			transaction.hide(mFrgMy);
		}
	}
	
	/**
	 * ÿ��ѡ����ǰ�����ʱ����Ҫ���ø÷��������ڽ���ײ�Tab����tab��ͼ���ʼ�����ָ�������ɫ״̬
	 */
	private void resetImgs(){
		mImgHome.setImageResource(R.mipmap.tabbar_home);
		mImgMessage.setImageResource(R.mipmap.tabbar_message_center);
		mImgSendMsg.setImageResource(R.mipmap.navigationbar_subsribe_manage);
		mImgSearch.setImageResource(R.mipmap.tabbar_discover);
		mImgMy.setImageResource(R.mipmap.tabbar_profile);

		/**
		 * �趨�ײ�����ɫΪ��ɫ
		 */
		mTextHome.setTextColor(Color.BLACK);
		mTextMessage.setTextColor(Color.BLACK);
		mTextSearch.setTextColor(Color.BLACK);
		mTextMy.setTextColor(Color.BLACK);
	}


/**
 * �����¼��Ĵ������ݵ���İ�ť����ѡ��Ҫ��ʾ�Ľ���
 */
	@Override
	public void onClick(View v) {
		resetImgs();//��ʼ������ͼ�����ɫ���Ƚ�����ͼ������Ϊ����ɫͼ��
		switch (v.getId()) {
		case R.id.tab_home:
			setSelect(0);
			break;
		case R.id.tab_message:
			setSelect(1);
			break;
		case R.id.tab_sendmessage:
			setSelect(2);
			break;
		case R.id.tab_search:
			setSelect(3);
			break;
		case R.id.tab_myself:
			setSelect(4);
			break;

		default:
			break;
		}
		
	}

	
}
