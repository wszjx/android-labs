/**
 * 
 */
package edu.hzuapps.androidworks.homeworks.net1314080903127;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;
import edu.hzuapps.androidworks.net1314080903127.R;
public class Net1314080903127_score_activity extends Activity {
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903127_score);
		setTitle("�����ְ�O(��_��)O~");

	RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
	ratingBar.setOnRatingBarChangeListener(
			new RatingBar.OnRatingBarChangeListener() {  
	        @Override  
	        public void onRatingChanged(RatingBar ratingBar,  
	                float rating, boolean fromUser) {  
	            // TODO Auto-generated method stub  
	            ratingBar.setRating(rating);  
	            Toast.makeText(Net1314080903127_score_activity.this,  
	                    "лл���֣�", Toast.LENGTH_SHORT).show();
	            finish();
	        	}         
			}
			);  
	}
}
