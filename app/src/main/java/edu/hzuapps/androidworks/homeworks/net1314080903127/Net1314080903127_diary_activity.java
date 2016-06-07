package edu.hzuapps.androidworks.homeworks.net1314080903127;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.hzuapps.androidworks.net1314080903127.R;
public class Net1314080903127_diary_activity extends ListActivity {
	
	//�ظ��Ĺؼ���
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	//�˵���ѡ��
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private Net1314080903127_DbAdapter mDbHelper;
	private Cursor mDiaryCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903127_diary_list);
		mDbHelper = new Net1314080903127_DbAdapter(this);
		updateListView();

	}

	//���µ�ǰ��listacvitity
	private void updateListView() {
		mDbHelper.open();
		mDiaryCursor = mDbHelper.getAllNotes();
		//����Activity�����α�
		startManagingCursor(mDiaryCursor);
		String[] from = new String[] { Net1314080903127_DbAdapter.KEY_TITLE,
				Net1314080903127_DbAdapter.KEY_CREATED };
		int[] to = new int[] { R.id.text1, R.id.created };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.net1314080903127_diary_row, mDiaryCursor, from, to);
		setListAdapter(notes);
		mDbHelper.closeclose();
	}

	//����һ���˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0,"�½�").setIcon(R.drawable.net1314080903127_new_course);
		menu.add(0, DELETE_ID, 0, "ɾ��").setIcon(R.drawable.net1314080903127_delete);
		return true;
	}

	//�˵�ѡ��
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createDiary();
			return true;
		case DELETE_ID:
			mDbHelper.open();
			mDbHelper.deleteDiary(getListView().getSelectedItemId());
			mDbHelper.closeclose();
			updateListView();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void createDiary() {
		Intent i = new Intent(this, Net1314080903127_DiaryEditActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override
	// ��Ҫ��position��id����һ���ܺõ�����
	// positionָ���ǵ�������ViewItem�ڵ�ǰListView�е�λ��
	// ÿһ����ViewItem�󶨵����ݣ��϶�����һ��id��ͨ�����id�����ҵ��������ݡ�
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		Intent i = new Intent(this, Net1314080903127_DiaryEditActivity.class);
		i.putExtra(Net1314080903127_DbAdapter.KEY_ROWID, id);
		i.putExtra(Net1314080903127_DbAdapter.KEY_TITLE, c.getString(c
				.getColumnIndexOrThrow(Net1314080903127_DbAdapter.KEY_TITLE)));
		i.putExtra(Net1314080903127_DbAdapter.KEY_BODY, c.getString(c
				.getColumnIndexOrThrow(Net1314080903127_DbAdapter.KEY_BODY)));
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		updateListView();
	}
}
