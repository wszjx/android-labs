package com.lws.camerpreview;

import java.io.IOException;

import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
/*����Ԥ���������ǽ���һ��surface��
��ȡsurface�ÿ�����surfaceholder��
����surface�Ĵ���previewdisplay��
���ʼԤ��startpreview��
��Ȼ���Ҫ��������ص�Ȩ�ޡ�*/
public class MainActivity extends Activity {
	private SurfaceView mView=null;		//�ǽ���һ��surface
	private SurfaceHolder mHolder=null;
	private Camera mCamera=null;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mView=(SurfaceView)this.findViewById(R.id.surfaceView1);
		mHolder=mView.getHolder();		//��ȡsurface�ÿ�����surfaceHolder
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		mHolder.setFixedSize(1, 700); // ����Surface�ֱ���
//		mHolder.setSizeFromLayout();
//		mHolder.setKeepScreenOn(true);// ��Ļ����
		mHolder.addCallback(new Callback(){

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				mCamera=Camera.open(1);					//��Ҫ������������ͷѡ����Ӧ������try,catch
				try {
					mCamera.setPreviewDisplay(mHolder);	//����surface�Ĵ���PreviewDisplay
					mCamera.setDisplayOrientation(90);	//��������ͷ�Ƕȣ��Ժ�������Ҫ�������������ֻ�������ʾ
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mCamera.startPreview();
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				mCamera.startPreview();
				mCamera.release();
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
