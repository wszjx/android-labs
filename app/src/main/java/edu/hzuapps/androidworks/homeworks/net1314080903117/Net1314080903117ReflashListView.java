package edu.hzuapps.androidworks.homeworks.net1314080903117;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({ "InflateParams", "ClickableViewAccessibility", "SimpleDateFormat" })
public class Net1314080903117ReflashListView extends ListView implements OnScrollListener {
	
	View header;//����һ��ȫ��View��Ϊ���������ļ���
	View footer;//����һ��ȫ�ֵ�View��Ϊ�ײ������ļ�
	
	int headerHeight;//���������ļ��ĸ߶�
	int firstVisibleItem;//��ǰ�����һ���ɼ���item��λ�ã�
	boolean isRemark;//��ǣ��ж��Ƿ���ListView����������ģ�
	int startY;//��ʼ����ʱ��Yֵ
	
	int state;//��ǰ��״̬
	final int NONE = 0;//����״̬
	final int PULL = 1; //����ʱ����ʾ������ˢ�µ�״̬
	final int RELESE = 2; //������һ���߶Ⱥ���ʾ�ɿ�����ˢ�µ�״̬
	final int REFLASHING = 3; // �ɿ��Ժ�����ˢ���е�״̬��
	
	int scrollState;//ListView��ǰ�Ĺ���״̬

	IReflashListener iReflashListener;
	
	
	int totalItemCount;
	int lastVisibleItem;
	boolean isLoading;
	private TextView loading = null;
	private ProgressBar pBarLoading = null;
	
	

	public Net1314080903117ReflashListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public Net1314080903117ReflashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public Net1314080903117ReflashListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	/*
	 * ��ʼ�������ļ�����Ӳ����ļ���ListView�У�����ÿ�����췽���е���
	 */
	private void initView(Context context){
		//�����������ļ�����,�ײ��ļ�����
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.net1314080903117header_layout, null);
		
		
		footer = inflater.inflate(R.layout.net1314080903117footer,null);
		//��Ҫ�Ƚ��ײ���������
		footer.setVisibility(View.GONE);


		/**
		 * �����������ֵĸ߶ȺͿ��
		 */
		measureView(header);
		
		//���������ֵĸ߶ȵĸ�ֵ�ȴ���topPadding����
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);//���븺ֵ������ʾ�����˶�����ˢ�²���
		
		//�������Ĳ����ļ���ӵ�ListView�У�ͨ��addHeaderView()�ķ���
		/**
		 * ��Ӷ����͵ײ�����
		 */
		this.addHeaderView(header);
		this.addFooterView(footer);
		
		//�趨ListView�Ĺ��������¼�
		this.setOnScrollListener(this);
		
		loading = (TextView) footer.findViewById(R.id.loading);
		pBarLoading = (ProgressBar) footer.findViewById(R.id.progressbar);
		
	}
	
	/**
	 * ֪ͨ������ListView�������ļ�����Ҫ�Ŀ�Ⱥ͸߶�
	 * @param view
	 */
	private void measureView(View view){
		
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		/*
		 * ����һ����ȣ���ȡ�Ӳ��ֵĿ��
		 * spec Ϊ��ǰheader�����ұ߾�
		 * padding Ϊ��ǰheader���ڱ߾�
		 * childDimension Ϊ�Ӳ��ֵĿ��
		 */
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if (tempHeight > 0) {
			/*
			 * mode ģʽΪMeasureSpec.EXACTLY
			 * �߶Ȳ�Ϊ�յ�ʱ����䲼��
			 */
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
			
		}else {
			//�߶�Ϊ0ʱ�����������
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
		
	}
	
	/**
	 * ����ķ���������һ���߶�
	 * �����ö������ֵ��ϱ߾�
	 * ��߾ࡢ�ұ߾ࡢ�ױ߾಻�䣬�ϱ߾���Ϊ����ĸ߶�
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		// TODO Auto-generated method stub
		header.setPadding(header.getPaddingLeft(), topPadding, 
				header.getPaddingRight(), header.getPaddingBottom());
		/**
		 * ���»��ƽ���
		 */
		header.invalidate();
		
		
	}

	/**
	 * ��д����״̬�ı�ķ���
	 * @param view
	 * @param scrollState
     */
    @Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.scrollState = scrollState;
		/**
		 * SCROLL_STATE_IDLE��ʾ��ǰ����״̬Ϊ������
		 */
		if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading = true;
				footer.setVisibility(View.VISIBLE);
				loading.setText("���ظ���");
				iReflashListener.onLoad();
			}
		}
		
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		this.totalItemCount = totalItemCount;
		this.lastVisibleItem = firstVisibleItem+visibleItemCount;
		
	}
	
	/**
	 * ���ü������ƴ����¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//�жϵ�ǰ�����Ƿ��ڽ������
			if (firstVisibleItem == 0) {
				isRemark = true;
				//��¼����ʱ��ʼ��Yֵ
				startY = (int) ev.getY();
				
			}
			
			break;

		case MotionEvent.ACTION_UP:
			//�жϵ�ǰ״̬
			if (state == RELESE) {
				state = REFLASHING;
				//������������
				reflashViewByState();
				//���ýӿڵ�ˢ�·���
				iReflashListener.onReflash();
			}else if(state == PULL){
				//����ˢ�µ�״̬�Ļ����Ͳ��������ݣ���״̬��Ϊ������״̬
				state =NONE;
				isRemark = false;
				reflashViewByState();
			}
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			onMove(ev);
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * �ж������ƶ������еĲ���
	 */
	private void onMove(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (!isRemark) {
			return ;
		}
		//��ȡ��ǰ������λ��
		int tempY =(int) ev.getY();
		//��ȡ������ʼ�ƶ��˵ľ���
		int space = tempY - startY;
		//�趨���ƶ����̶������ֵ�topPadding��ֵ������������ʾ��������
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			if (space > 0) {
				state =PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);
			//�����������һ���߶ȵ�ʱ�򣬲��ҹ���״̬Ϊ���ڹ�����
			if (space > headerHeight+30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELESE;
				reflashViewByState();
			}
			break;
		case RELESE:
			topPadding(topPadding);
			//���С��һ���ĸ߶ȣ�״̬�����������ˢ��״̬
			if (space < headerHeight+30) {
				state = PULL;
				reflashViewByState();
			}else if (space <=0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		
	}
	/**
	 * ���ݵ�ǰ��״̬��ˢ����ʾ����
	 */
	private void reflashViewByState(){
		
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progressBar = (ProgressBar) header.findViewById(R.id.progressbar);
		
		/**
		 * ����ͷ��Ӷ���Ч��
		 * һ��ʼ��0--180�ȱ仯
		 */
		RotateAnimation rotateAnimation = new RotateAnimation(0,180,
				RotateAnimation.RELATIVE_TO_SELF,0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5F);
		//�趨����֮���ʱ����
		rotateAnimation.setDuration(800);
		rotateAnimation.setFillAfter(true);
		
		//����תЧ��
		RotateAnimation rotateAnimation1 = new RotateAnimation(180,0,
				RotateAnimation.RELATIVE_TO_SELF,0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5F);
		//�趨����֮���ʱ����
		rotateAnimation1.setDuration(800);
		rotateAnimation1.setFillAfter(true);
		
		switch (state) {
		case NONE:
			//����״̬����ʾ������topPadding��ֵΪ��ֵ����
			topPadding(-headerHeight);
			arrow.clearAnimation();
			break;
		case PULL:
			//����״̬����ʾ��ͷΪ����״̬��������Ϊ����״̬
			arrow.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tip.setText("=���أ�=����ˢ����");
			//���������Ч���������ö���Ч������ͷ��180��0�ȱ仯
			arrow.clearAnimation();
			arrow.setAnimation(rotateAnimation1);
			break;
		case RELESE:
			//�ɿ�����ˢ�µ�״̬����ʾ��ʾ�ļ�ͷ��������״̬������������
			arrow.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tip.setText("�ҷſ����Ҿͱ�����");
			arrow.clearAnimation();
			arrow.setAnimation(rotateAnimation);
			break;

		case REFLASHING:
			//����ˢ�µ�״̬���ڹ̶��ĸ߶���ʾ��������ʧ����ʾ��ͷ���أ�������Ϊ��ʾ״̬
			topPadding(30);
			arrow.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			tip.setText("������...");
			arrow.clearAnimation();
			break;
		}
		
		
	}
	
	//����ˢ������Ժ�����������
	public void reflashComplete(){
		state = NONE;
		isRemark = false;
		//ˢ�µ�ǰ����
		reflashViewByState();
		//�趨�ϴθ���ʱ��
		TextView lastupdatatime = (TextView) header.findViewById(R.id.lastupdate_time);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = format.format(date);
		lastupdatatime.setText("�ϴθ��£�"+time);
	}
	
	public void allDataReflashComplete(){
		isRemark =false;
		TextView tip = (TextView) header.findViewById(R.id.tip);
		tip.setText("ľ�������ݿ����ȵȹ�");
		
	}
	
	public void GoneHeader() {
		topPadding(-headerHeight);
		header.setVisibility(View.GONE);
	}
	
	public void loadingComplete(){
		isLoading = false;
		footer.setVisibility(View.GONE);
		
	}
	public void addDataLoadingComplete(){
		isLoading =false;
		loading.setText("ȫ�������Ѽ������");
		pBarLoading.setVisibility(View.GONE);
	}
	
	
	
	
	
	//�趨һ�����������ӿ��������
	public void setReflashListener(IReflashListener iReflashListener){
		this.iReflashListener =iReflashListener;
	}
	
	
	/*
	 * ������һ��ˢ�����ݵĽӿ�
	 * ��Ҫ��MainActivity��ȥʵ������ӿ�
	 */
	public interface IReflashListener{
		/*
		 * �ӿ��ж�����������
		 * onReflash����������ˢ�µķ���
		 * onLoad�����������Զ����ظ���ķ���
		 */
		public void onReflash();
		public void onLoad();
		
	}

}
