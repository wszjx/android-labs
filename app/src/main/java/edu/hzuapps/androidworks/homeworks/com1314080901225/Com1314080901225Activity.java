
package com.example.drawingboard;

import java.io.File;
import java.io.FileOutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Com1314080901225Activity extends Activity {
	private ImageView iv;
	private int startX, startY, endX, endY;
	Bitmap bitSrc, bitCopy;
	Paint paint;
	Canvas canvas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901225);
		iv = (ImageView) findViewById(R.id.iv);
		Bitmap bitmap=copyBitmap();
		iv.setImageBitmap(bitmap);
		iv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				// ��ָ����
				case MotionEvent.ACTION_DOWN:
					// ��ȡ���µ�xy
					startX = (int) event.getX();
					startY = (int) event.getY();
					break;

				// ��ָ����
				case MotionEvent.ACTION_MOVE:
					endX = (int) event.getX();
					endY = (int) event.getY();
					canvas.drawLine(startX, startY, endX, endY, paint);
					iv.setImageBitmap(bitCopy);

					// �ı仭���ٴε����
					startX = endX;
					startY = endY;
					break;

				// ��ָ�뿪
				case MotionEvent.ACTION_UP:

					break;

				}
				return true;
			}
		});
	}

	// �����ɱ༭����
	public Bitmap copyBitmap() {
		bitSrc = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		bitCopy = Bitmap.createBitmap(bitSrc.getWidth(), bitSrc.getHeight(),
				bitSrc.getConfig());
		paint = new Paint();
		canvas = new Canvas(bitCopy);
		canvas.drawBitmap(bitSrc, new Matrix(), paint);
		return bitCopy;
	}

	// ��ɫ����
	public void red(View v) {
		paint.setColor(Color.RED);
		Toast.makeText(getApplicationContext(), "��ѡ���˺�ɫ", 0).show();
	}

	// ��ɫ����
	public void black(View v) {
		paint.setColor(Color.BLACK);
		Toast.makeText(getApplicationContext(), "��ѡ���˺�ɫ", 0).show();
	}

	// ���ʼӴ�
	public void brush(View v) {
		paint.setStrokeWidth(9);
		Toast.makeText(getApplicationContext(), "���ʱ����", 0).show();
	}
	
	//����ͼƬ��sd��
	public void save(View v){
    	File file=new File("sdcard/zuohua1.png");
    	FileOutputStream fos;
    	try {
			fos=new FileOutputStream(file);
			//ѹ����ʽ��ѹ������100λ����������
			bitCopy.compress(CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//����һ�������㲥
    	Intent intent=new Intent();
    	intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
    	intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
    	sendBroadcast(intent);
    	Toast.makeText(getApplicationContext(), "�Ѿ�������ͼƬ", 0).show();
    } 
}
