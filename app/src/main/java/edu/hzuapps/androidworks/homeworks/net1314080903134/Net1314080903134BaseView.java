package com.alonso.view;

import com.alonso.myplane.Net1314080903134MainActivity;
import com.alonso.sounds.GameSoundPool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class Net1314080903134BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	protected int currentFrame;
	protected float scalex;
	protected float scaley;
	protected float screen_width;
	protected float screen_height;
	protected boolean threadFlag;
	protected Paint paint;
	protected Canvas canvas;
	protected Thread thread;
	protected SurfaceHolder sfh;
	protected GameSoundPool sounds;
	protected MainActivity mainActivity;
	//���췽��
	public Net1314080903134BaseView(Context context,GameSoundPool sounds){
		super(context);
		this.sounds = sounds;
		this.mainActivity = (MainActivity) context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
	}
	// ��ͼ�ı�ķ���
		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub

		}
		// ��ͼ�����ķ���
		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			screen_width = this.getWidth();		//�����ͼ�Ŀ��
			screen_height = this.getHeight();	//�����ͼ�ĸ߶�
			threadFlag = true;
		}
		// ��ͼ���ٵķ���
		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			threadFlag = false;
		}
		// ��ʼ��ͼƬ��Դ����
		public void initBitmap() {}
		// �ͷ�ͼƬ��Դ�ķ���
		public void release() {}
		// ��ͼ����
		public void drawSelf() {}
		// �߳����еķ���
		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
		public void setThreadFlag(boolean threadFlag){
			this.threadFlag = threadFlag;
		}


}
