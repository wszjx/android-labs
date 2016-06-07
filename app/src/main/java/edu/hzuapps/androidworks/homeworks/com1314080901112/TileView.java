package com.xmobileapp.Snake;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class TileView extends View {

	protected static int mTileSize;   //ÿ��tile�ı߳����������� 

	protected static int mXTileCount;  //��Ļ�������ɵ� X�����Ϸ����������  
	protected static int mYTileCount;

	private static int mXOffset;      //ԭ�����꣬��pixel�ơ�X Y��ƫ����  
	private static int mYOffset;

	//�洢�Ų�ͬ�����bitmapͼ��ͨ��resetTiles��loadTile������Ϸ�еķ�����ص�������顣
	private Bitmap[] mTileArray; 
	
	/*�洢����������ÿ��tileλ��Ӧ�û��Ƶ�tile�� 
    * �ɿ���������ֱ�Ӳ����Ļ����� 
    * ͨ��setTile��clearTile ����ͼ����ʾ���޸Ĳ�����
    */
	private int[][] mTileGrid;  //���ͼƬ������
	
	//���ʣ�canvas��ͼ�λ��ƣ���Ҫ����Paintʵ�֡�
	private final Paint mPaint = new Paint();

	public TileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//ʹ��TypedArray����ȡ��attrs.xml��ΪTileView�����������tileSize
		//tileSize���Զ���Ϊ������
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TileView);

		mTileSize = a.getInt(R.styleable.TileView_tileSize, 12);
		//������ǰʹ�ù��ķ�����Լ���ʹ��TypedArray��һ��Ҫʹ�����
		a.recycle();
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TileView);

		mTileSize = a.getInt(R.styleable.TileView_tileSize, 12);

		a.recycle();
	}
	 //��������mTileArray������Ϸ��ʼ��ʱ��ʹ�á�
	public void resetTiles(int tilecount) {
		mTileArray = new Bitmap[tilecount];
	}

	//���ı���Ļ��С�ߴ�ʱ��ͬʱ�޸�tile����ؼ���ָ�ꡣ
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mXTileCount = (int) Math.floor(w / mTileSize);//���X���ܷŶ��ٸ���
		mYTileCount = (int) Math.floor(h / mTileSize);

		mXOffset = ((w - (mTileSize * mXTileCount)) / 2);//���Xƫ����
		mYOffset = ((h - (mTileSize * mYTileCount)) / 2);
		
		//����������Ż滭ͼ���X��Y���λ�õ����� ������������������
		mTileGrid = new int[mXTileCount][mYTileCount];
		clearTiles();//����
	}

	/*���ؾ����ש��ͼƬ �� ש���ֵ䡣 
    *������Ӧ��ש���ͼƬ ��Ӧ�ļ��ص� mTileArray������ 
    */
	public void loadTile(int key, Drawable tile) {
		//��������һ�� Drawable �� bitmap ��ת���������ⲿ����ʹ�õ�ʱ����ֱ�Ӷ�ȡ��Դ�ļ��е�ͼƬ��  
        //��drawable��ʽ�������ǵ�������bitmap��ʽ���������յĻ��ơ����ԣ���Ҫ����һ�ε� bitmap��ת����  
		Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);//��������
		tile.setBounds(0, 0, mTileSize, mTileSize);//��ͼ
		tile.draw(canvas);

		mTileArray[key] = bitmap;
	}
    /*
     * ���ͼ����ʾ�� 
     * ���Ը��»��档 
     * �����˻�ͼ��setTile()�� 
     */
	public void clearTiles() {
		for (int x = 0; x < mXTileCount; x++) {
			for (int y = 0; y < mYTileCount; y++) {
				setTile(0, x, y);
			}
		}
	}
	//����Ӧ������λ�û�����Ӧ��ש�� 
    //mTileGrid��ʵ��������ֱ�Ӳ����Ļ�����
	public void setTile(int tileindex, int x, int y) {
		mTileGrid[x][y] = tileindex;
	}

	//������ֱ�Ӳ����Ļ������Ƶ��ֻ������ϣ�
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int x = 0; x < mXTileCount; x += 1) {
			for (int y = 0; y < mYTileCount; y += 1) {
				if (mTileGrid[x][y] > 0) {
					canvas.drawBitmap(mTileArray[mTileGrid[x][y]], mXOffset + x
							* mTileSize, mYOffset + y * mTileSize, mPaint);
				}
			}
		}

	}

}
