package edu.hzuapps.androidworks.homeworks.net134080903206;

import com.bengua.whiteblock.R;

import android.os.Bundle;
import android.widget.TextView;

public class Net1314080903206GameRuleActivity extends Net1314080903206BaseActivity{
	
	private TextView mGameRule1TextView,mGameRule2TextView,mGameRule3TextView,mGameRule4TextView;
	private String mGameRule1,mGameRule2,mGameRule3,mGameRule4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.net1314080903206_activity_game_rule);
		
		mGameRule1 = "��Ϸ���� ";
		mGameRule2 = "�ȵ��׿��1��";
		mGameRule3 = "�ȵ��ڿ��5��";
		mGameRule4 = "��Ϸʱ��Ϊ30s";
		
		mGameRule1TextView = (TextView) findViewById(R.id.game_rule_1_text_view);
		mGameRule1TextView.setText(mGameRule1);
		
		mGameRule2TextView = (TextView) findViewById(R.id.game_rule_2_text_view);
		mGameRule2TextView.setText(mGameRule2);
		
		mGameRule3TextView = (TextView) findViewById(R.id.game_rule_3_text_view);
		mGameRule3TextView.setText(mGameRule3);
		
		mGameRule4TextView = (TextView) findViewById(R.id.game_rule_4_text_view);
		mGameRule4TextView.setText(mGameRule4);
	}

	
	
}
