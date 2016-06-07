package edu.hzuapps.androidworks.homeworks.com1314080901138;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.*;

public class Com1314080901138Activity extends Activity {
    private MediaPlayer mediaPlayer;//MediaPlayer����
    private boolean isPause = false;//�Ƿ���ͣ
    private File file;//Ҫ���ŵ��ļ�
    private TextView hint;//������ʾ��Ϣ���ı���

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901138);
        //��ȡ�����ܰ�ť
        final Button button = (Button) findViewById(R.id.button);//����
        final Button button1 = (Button) findViewById(R.id.button2);//��ͣ
        final Button button2 = (Button) findViewById(R.id.button3);//ֹͣ
        hint = (TextView) findViewById(R.id.textView);
        if (!isFileExist()) {
            button.setEnabled(false);
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.my);
        //��MediaPlayer��������¼����������������ʱ���¿�ʼ���ֲ���
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });
        //�Բ��Ű�ť�����¼�����
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                if (isPause) {
                    button1.setText("��ͣ");
                    isPause = false;
                }
                button1.setEnabled(true);
                button2.setEnabled(true);
                button.setEnabled(false);
            }
        });
        //����ͣ��������ť����¼�������
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying() && !isPause) {
                    mediaPlayer.pause();
                    isPause = true;
                    ((Button) v).setText("����");
                    hint.setText("��ͣ������Ƶ....");
                    button.setEnabled(true);
                } else {
                    mediaPlayer.start();
                    ((Button) v).setText("��ͣ");
                    hint.setText("����������Ƶ....");
                    button.setEnabled(false);
                }
            }
        });
        //��ֹͣ��ť����¼�������
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                hint.setText("ֹͣ������Ƶ...");
                button1.setEnabled(false);
                button2.setEnabled(false);
                button.setEnabled(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    //�ж��ļ��Ƿ����
    private boolean isFileExist() {
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "my.mp3");//��ȡ�ⲿ�洢�е������ļ�
        if (file.exists()) {
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.my);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "file exist", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "file don't exist", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //�������ֵķ���
    private void play() {
        try {
            mediaPlayer.reset();//��������Ҫ���ŵ�����
            mediaPlayer = MediaPlayer.create(this, R.raw.my);
            mediaPlayer.start();//��������
            hint.setText("Music is starting");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("err", e.getMessage());
        }
        //return ;
    }
}
