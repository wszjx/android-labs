package com.example.test;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;


public class Com1314080901239Activity extends Activity {
	private Uri fileUri;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901239);
    }
    // ����¼�
 	public void takePhoto(View v) {
 		// create Intent to take a picture and return control to the calling
 		// application
 		// ����һ����ͼ��������ǵ���google�������ṩ�ģ��ֻ��Դ�������Ӧ��
 		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 		// ���ô洢·��
 		intent.putExtra(MediaStore.EXTRA_OUTPUT,
 				Uri.fromFile(new File("sdcard/nuna.jpg"))); // set the image
 															// file name

 		// start the image capture Intent
 		startActivityForResult(intent, 0);

 	}

 	@Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		// TODO Auto-generated method stub
 		super.onActivityResult(requestCode, resultCode, data);
 		if (requestCode == 0) {
 			Toast.makeText(getApplicationContext(), "���ճɹ�", 0).show();
 		}
 	}


}
