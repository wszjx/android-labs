package com.alonso.object;

import com.alonso.myplane.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
/*�ӵ���Ʒ��*/
public class BulletGoods extends GameGoods{
	public BulletGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
	}
	// ��ʼ��ͼƬ��Դ��
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}

