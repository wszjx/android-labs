package edu.hzuapps.androidworks.homeworks.net1314080903141;


import edu.hzuapps.androidworks.homeworks.net131240809031413.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Net1314080903141Activity extends Activity {

	// ��˷翪��ͼƬButton
	private ImageButton state;
	Net1314080903141RunActivity run_Metned = new Net1314080903141RunActivity();
	Thread thread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net1314080903141);

		// �󶨿ؼ�
		this.state = (ImageButton) findViewById(R.id.state);
		this.state.setOnClickListener(new ocl());
	}

	// ��¼��˷�״̬�Ĳ���ֵ
	private boolean is = false;

	// ������
	class ocl implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.state && is == true) {
				// �л�ͼƬ��ť�ı���ͼ���������������������ء�
				state.setImageDrawable(getResources().getDrawable(
						R.drawable.net1314080903141close));
				run_Metned.no();

				// �ı�״̬
				is = false;
			} else if (v.getId() == R.id.state && is == false) {
				// �л�ͼƬ��ť�ı���ͼ���ء�������������������
				state.setImageDrawable(getResources().getDrawable(
						R.drawable.net1314080903141open));
				// �ı�״̬
				try {
					Thread thread = new Thread(run_Metned);

					thread.start();
				} catch (Exception e) {

				}
				is = true;
			}
		}
	}

	int i = 0;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			i++;
			if (i == 3) {
				finish();
			}

		}
		return true;
	}
}
