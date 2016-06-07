package edu.hzuapps.androidworks.homeworks.net1314080903117;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * ����ΪReflashListview��������
 * �̳���BaseAdapter
 * ��дBaseAdapter���ĸ�����
 * @author Charlie
 *
 */
public class Net1314080903117MessageAdapter extends BaseAdapter {
	private List<Net1314080903117Message> mList;//΢����Ϣ��������
	private Context mContext;//��ȡ�����Ķ���
	
	
	/**
	 * �������������Ĺ��췽��
	 * @param context �����Ķ���
	 * @param list  ��Ϣ����
	 */
	public Net1314080903117MessageAdapter(Context context, List<Net1314080903117Message> list) {
		mContext = context;
		mList = list;
	}
	/**
	 * ��ȡItem �ĸ���
	 */
	@Override
	public int getCount() {   return mList.size();  }

	/**
	 * ��ʾ��ǰ�������ʾ������
	 */
	@Override
	public Object getItem(int position) {
		
		return mList.get(position);
	}

	/**
	 * ��ȡ��ǰλ��
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * ��ȡ��ǰ��View
	 * Ϊ�˷�ֹ�ڴ����Ĺ���ʹ��MessageViewHolder��ʵ��
	 * �����Դ������
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MessageViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MessageViewHolder();
			//��ȡListView��Item�����Ĳ���item_listview
			convertView = View.inflate(mContext, R.layout.net1314080903117item_listview, null);
			//ͨ��View �����Ҷ�Ӧ�Ŀؼ�
			viewHolder.messageIcon = (ImageView) convertView.findViewById(R.id.id_message_icon);
			viewHolder.messageUser = (TextView) convertView.findViewById(R.id.id_message_User);
			viewHolder.messageTime = (TextView) convertView.findViewById(R.id.id_message_Time);
			viewHolder.messageContent = (TextView) convertView.findViewById(R.id.id_message_content);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (MessageViewHolder) convertView.getTag();
		}
		/**
		 * Ϊ�����ؼ����и�ֵ������position����ȡ��ǰҪ��ʾ�����ݣ�����ʾ����
		 */
	    viewHolder.messageIcon.setImageResource(mList.get(position).getMessageIcon());
		viewHolder.messageUser.setText(mList.get(position).getMessageUser());
		viewHolder.messageTime.setText(mList.get(position).getMessageTime());
		viewHolder.messageContent.setText(mList.get(position).getMessageContent());
		return convertView;
	}
	
	/**
	 * ʹ��ViewHolder�����Բ���ÿ��������getView��ʱ�򶼴����µĶ���
	 * ��������������
	 * ���ҽ������ڴ�����
	 * @author Charlie
	 *
	 */
	class MessageViewHolder{
		/**
		 * ListView ��Item�еĿؼ�
		 */
		public ImageView messageIcon;
		public TextView messageUser;
		public TextView messageTime;
		public TextView messageContent;
		
	}

}
