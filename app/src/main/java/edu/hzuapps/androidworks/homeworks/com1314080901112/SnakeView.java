package com.xmobileapp.Snake;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SnakeView extends TileView {

	private static final String TAG = "SnakeView";

	//��Ϸ������״̬����ʼ״̬ΪԤ����ʼ��״̬
	int mMode = READY;
	public static final int PAUSE = 0;//��ͣ
	public static final int READY = 1;
	public static final int RUNNING = 2;
	public static final int LOSE = 3;

	//�����˶��ķ����ʶ
	int mDirection = SOUTH;
	int mNextDirection = SOUTH;
	static final int NORTH = 1;
	static final int SOUTH = 2;
	static final int EAST = 3;
	static final int WEST = 4;

	//��Ϸ�����ַ����Ӧ����ֵ
	static final int RED_STAR = 1;
	static final int YELLOW_STAR = 2;
	static final int GREEN_STAR = 3;

	private long mScore = 0;//��¼��õķ���
	//ÿ�ƶ�һ������ʱ����ʼʱ����Ϊ600ms���Ժ�ÿ��һ�����ӣ����9��  ����ɵĽ���������ٶ�Խ��Խ��
	private long mMoveDelay = 600; 
	
	//��¼�ϴ��ƶ���ȷ��ʱ�䡣 
    //ͬmMoveDelayһ�������û����첽������Эͬ���⡣ 
	private long mLastMove;

	//������ʾ��Ϸ״̬��TextView 
	private TextView mStatusText;

	/*
	 * ���������ֱ������洢 ���� �� ���ӵ����ꡣ 
     * ÿ��������˶�������������������µ�ƻ�������Ե�ƻ���������������¼�� 
	 */
	private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
	private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

	//��������������������������ƻ������addRandomApple()��ʹ�á�
	private static final Random RNG = new Random();
	
   //��Handler����ʵ�ֶ�ʱˢ�¡�
	private RefreshHandler mRedrawHandler = new RefreshHandler();

	class RefreshHandler extends Handler {

		//��ȡ��Ϣ������
		@Override
		public void handleMessage(Message msg) {
			SnakeView.this.update();
			SnakeView.this.invalidate();//ˢ��viewΪ����Ľ���
		}

		 //��ʱ������Ϣ��UI�̣߳��Դ˴ﵽ���µ�Ч����  
		public void sleep(long delayMillis) {
			this.removeMessages(0);//�����Ϣ���У�Handler���������Ϣ�ĵȴ�
			sendMessageDelayed(obtainMessage(0), delayMillis);//��ʱ��������Ϣ,����handler
		}
	};

	public SnakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSnakeView();//���캯���У������ˣ���ʼ����Ϸ�� 
	}

	public SnakeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSnakeView();
	}
	//��ʼ��SnakeView�࣬ע�⣬�����ʼ����Ϸ�ǲ�һ���ġ�  
	private void initSnakeView() {
		setFocusable(true);//���ý��㣬���ڴ��� ���ֽ��� �� ��Ϸ�������ת�����focus�ǲ��ɻ�ȱ�ġ�

		//ȡ����Դ�е�ͼƬ�����ص� ש���ֵ� �С�
		Resources r = this.getContext().getResources();

		resetTiles(4);
		loadTile(RED_STAR, r.getDrawable(R.drawable.redstar));
		loadTile(YELLOW_STAR, r.getDrawable(R.drawable.yellowstar));
		loadTile(GREEN_STAR, r.getDrawable(R.drawable.greenstar));

	}

	//������Ǵ���ͣ�лظ�������Ҫ ��ʼ����Ϸ�ˡ�
	void initNewGame() {
		//��ձ�������͹��ӵ����ݽṹ
		mSnakeTrail.clear();
		mAppleList.clear();

		 // �趨��ʼ״̬�������λ�á�Coordinate��ʵ�������忪ʼʱ���С���飬����������С����
		mSnakeTrail.add(new Coordinate(7, 7));
		mSnakeTrail.add(new Coordinate(6, 7));
		mSnakeTrail.add(new Coordinate(5, 7));
		mSnakeTrail.add(new Coordinate(4, 7));
		mSnakeTrail.add(new Coordinate(3, 7));
		mSnakeTrail.add(new Coordinate(2, 7));
		mNextDirection = SOUTH;//�趨���߳�ʼ�˶��ķ���ΪSOUTH
		
		 // Two apples to start with  
		addRandomApple();
		addRandomApple();

		mMoveDelay = 600;
		mScore = 0;
	}

	/*
	 * ����Ϸ��ͣʱ����Ҫͨ��Bundle��ʽ�������ݡ���saveState()�� 
     * Bundle֧�ּ򵥵����顣 
     * ������Ҫ�����ǵĲ������ݽṹ���������ƻ��λ�õ����飬ת���ɼ򵥵����л���int���顣 
	 */
	private int[] coordArrayListToArray(ArrayList<Coordinate> cvec) {
		int count = cvec.size();
		int[] rawArray = new int[count * 2];
		for (int index = 0; index < count; index++) {
			Coordinate c = cvec.get(index);
			rawArray[2 * index] = c.x;
			rawArray[2 * index + 1] = c.y;
		}
		return rawArray;
	}

	//����������£���ʱ�Ա�����Ϸ���ݣ����´δ���Ϸʱ�����Լ�����Ϸ�������绰�ˡ� 
	public Bundle saveState() {
		Bundle map = new Bundle();

		map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
		map.putInt("mDirection", Integer.valueOf(mDirection));
		map.putInt("mNextDirection", Integer.valueOf(mNextDirection));
		map.putLong("mMoveDelay", Long.valueOf(mMoveDelay));
		map.putLong("mScore", Long.valueOf(mScore));
		map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));

		return map;
	}
	
	/*
	 * ��coordArrayListToArray����������̣�������ȡ������Bundle�е����ݡ� 
     * @param rawArray : [x1,y1,x2,y2,...] 
     * @return a ArrayList of Coordinates 
	 */

	private ArrayList<Coordinate> coordArrayToArrayList(int[] rawArray) {
		ArrayList<Coordinate> coordArrayList = new ArrayList<Coordinate>();

		int coordCount = rawArray.length;
		for (int index = 0; index < coordCount; index += 2) {
			Coordinate c = new Coordinate(rawArray[index], rawArray[index + 1]);
			coordArrayList.add(c);
		}
		return coordArrayList;
	}

	/*
	 * �ָ���Ϸ���ݡ���saveState()������� 
     * @param icicle a Bundle containing the game state 
	 */
	public void restoreState(Bundle icicle) {
		setMode(PAUSE);

		mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
		mDirection = icicle.getInt("mDirection");
		mNextDirection = icicle.getInt("mNextDirection");
		mMoveDelay = icicle.getLong("mMoveDelay");
		mScore = icicle.getLong("mScore");
		mSnakeTrail = coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
	}

	/*
	 * �����ļ����� 
     * ���ڴ������android�ֻ���û�а����ˡ� 
     * ���Լ���ģ����ϲ���������ʹ�����С��Ϸ�� - -# 
     * @see android.view.View#onKeyDown(int, android.os.KeyEvent) 
	 * (non-Javadoc)
	 * @see android.view.View#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) {

		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			if (mMode == READY | mMode == LOSE) {
				initNewGame();
				setMode(RUNNING);
				update();//update()ʵ���˶���Ϸ���ݵĸ��£���������Ϸ���ƶ�����
				return (true);
			}

			/* 
             * If the game is merely paused, we should just continue where 
             * we left off. 
             */  
			if (mMode == PAUSE) {
				setMode(RUNNING);
				update();
				return (true);
			}

			if (mDirection != SOUTH) {//��������ķ��� ���߱�����˶�������ȫ�෴�����޷�ִ��  
				mNextDirection = NORTH;
			}
			return (true);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (mDirection != NORTH) {
				mNextDirection = SOUTH;
			}
			return (true);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (mDirection != EAST) {
				mNextDirection = WEST;
			}
			return (true);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (mDirection != WEST) {
				mNextDirection = EAST;
			}
			return (true);
		}

		return super.onKeyDown(keyCode, msg);
	}

	//Snake�����õ��������󶨵���Ӧ��textview.
	public void setTextView(TextView newView) {
		mStatusText = newView;
	}

	/*
	 * ����Ӧ�ó���ĵ�ǰģʽ(���л���ͣ�����Ƶ�) ����
	 * *�Լ�����textview�ܼ��ȵ�֪ͨ��
	 */
	public void setMode(int newMode) {
		int oldMode = mMode;
		mMode = newMode;

		if (newMode == RUNNING & oldMode != RUNNING) {
			mStatusText.setVisibility(View.INVISIBLE);//��Ϸ��ʼ�󣬽�TextView��������ʾ����Ϊ���ɼ�
			update();
			return;
		}

		Resources res = getContext().getResources();
		CharSequence str = "";
		if (newMode == PAUSE) {
			str = res.getText(R.string.mode_pause);
		}
		if (newMode == READY) {
			str = res.getText(R.string.mode_ready);
		}
		if (newMode == LOSE) {
			//str = res.getString(R.string.mode_lose_prefix) + mScore
					//+ res.getString(R.string.mode_lose_suffix);
			//str=mScore+"";
			str=res.getString(R.string.mode_lose_prefix)
					+res.getString(R.string.mode_lose_suffix)+mScore;
		}

		mStatusText.setText(str);
		mStatusText.setVisibility(View.VISIBLE);
	}

	/*
	 * �ڵ�ͼ����������ӹ���
	 * �²����Ĺ��ӵ���������ӵ�mApplist�������ϡ�
	 */
	private void addRandomApple() {
		Coordinate newCoord = null;
		boolean found = false;
		while (!found) {
			//ע�������ڱ߿��ϵĹ���
			int newX = 1 + RNG.nextInt(mXTileCount - 2);
			int newY = 1 + RNG.nextInt(mYTileCount + 5);
			//int newX=1;
			//int newY=1;
			newCoord = new Coordinate(newX, newY);

			// Make sure it's not already under the snake 
			boolean collision = false;
			int snakelength = mSnakeTrail.size();
			for (int index = 0; index < snakelength; index++) {
				if (mSnakeTrail.get(index).equals(newCoord)) {
					collision = true;
				}
			}
			found = !collision;
		}
		if (newCoord == null) {
			Log.e(TAG, "Somehow ended up with a null newCoord!");
		}
		mAppleList.add(newCoord);
	}

	//ˢ����Ϸ״̬��ÿ����Ϸ����ĸ��¡���Ϸ���ݵĸ��£������������update()����ɵġ� 
	public void update() {
		if (mMode == RUNNING) {
			long now = System.currentTimeMillis();

			if (now - mLastMove > mMoveDelay) {//�����Ƕ�������Ϸ�տ�ʼʱ�����������ƶ����ʵĿ��� 
				clearTiles();//��ս��滭��
				updateWalls();//���»���ǽ��
				updateSnake();//���ߵ� ��Ϸ�߼� �Ĵ��� �Լ�����  
				updateApples();//�Թ��ӵ� ��Ϸ�߼� �Ĵ��� �Լ����� 
				mLastMove = now;
			}
			mRedrawHandler.sleep(mMoveDelay);//����Handler���� ��ʱˢ�µĿ��ƣ�����mMoveDelay�����ߵ��ƶ��ٶȣ�
		}

	}

	//��setTile����ǽ�� 
	private void updateWalls() {
		for (int x = 0; x < mXTileCount; x++) {//������
			setTile(GREEN_STAR, x, 3);
			setTile(GREEN_STAR, x, mYTileCount - 1);
		}
		for (int y = 3; y < mYTileCount - 1; y++) {//������
			setTile(GREEN_STAR, 0, y);
			setTile(GREEN_STAR, mXTileCount - 1, y);
		}
	}
   //���ƹ���
	private void updateApples() {
		for (Coordinate c : mAppleList) {
			setTile(YELLOW_STAR, c.x, c.y);
		}
	}

	private void updateSnake() {
		boolean growSnake = false;//�Թ����ӵ��߻᳤�������������Ϊ���ı�ǡ�

		Coordinate head = mSnakeTrail.get(0);//ͷ������Ҫ��ֻ��ͷ��������������
		Coordinate newHead = new Coordinate(1, 1);//����һ��һ����ǰ�ƣ�Ҳ����newHead������ֻ���β�����ӡ�

		mDirection = mNextDirection;

		switch (mDirection) {
		case EAST: {
			newHead = new Coordinate(head.x + 1, head.y);
			break;
		}
		case WEST: {
			newHead = new Coordinate(head.x - 1, head.y);
			break;
		}
		case NORTH: {
			newHead = new Coordinate(head.x, head.y - 1);
			break;
		}
		case SOUTH: {
			newHead = new Coordinate(head.x, head.y + 1);
			break;
		}
		}
		//ײǽ���  
		if ((newHead.x < 1) || (newHead.y < 4) || (newHead.x > mXTileCount - 2)
				|| (newHead.y > mYTileCount - 2)) {
			setMode(LOSE);
			return;

		}
		//ײ�Լ����
		int snakelength = mSnakeTrail.size();
		for (int snakeindex = 0; snakeindex < snakelength; snakeindex++) {
			Coordinate c = mSnakeTrail.get(snakeindex);
			if (c.equals(newHead)) {
				setMode(LOSE);
				return;
			}
		}
		//�Թ��Ӽ��  
		int applecount = mAppleList.size();
		for (int appleindex = 0; appleindex < applecount; appleindex++) {
			Coordinate c = mAppleList.get(appleindex);
			if (c.equals(newHead)) {
				mAppleList.remove(c);
				addRandomApple();

				mScore++;
				mMoveDelay *= 0.9;

				growSnake = true;
			}
		}
		// push a new head onto the ArrayList and pull off the tail  
        //ǰ��  
		mSnakeTrail.add(0, newHead);
		if (!growSnake) {
			mSnakeTrail.remove(mSnakeTrail.size() - 1);
		}
		//�����µ�����  
		int index = 0;
		for (Coordinate c : mSnakeTrail) {
			if (index == 0) {
				setTile(YELLOW_STAR, c.x, c.y);
			} else {
				setTile(RED_STAR, c.x, c.y);
			}
			index++;
		}

	}

	//�����������ࡣ�ܼ򵥵Ĵ洢XY���ꡣ 
	private class Coordinate {
		public int x;
		public int y;

		public Coordinate(int newX, int newY) {
			x = newX;
			y = newY;
		}

		public boolean equals(Coordinate other) {
			if (x == other.x && y == other.y) {
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return "Coordinate: [" + x + "," + y + "]";
		}
	}

}
