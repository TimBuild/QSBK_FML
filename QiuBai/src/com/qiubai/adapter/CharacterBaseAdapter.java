package com.qiubai.adapter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bt.qiubai.FirstEnterActivity;
import com.bt.qiubai.R;
import com.qiubai.entity.Character;
import com.qiubai.ui.CharacterListView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CharacterBaseAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
//	private List<Map<String, Object>> listItems;
	private List<Character> listCharacters;
	private AlertDialog mDialog;
	private CharacterListView listView;
	
	ViewHolder holder = null;
	
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	
//	private OnRefreshListener refreshListener;

	
	private String TAG = "CharacterBaseAdapter";
	
	

	/*public CharacterBaseAdapter(Context context,List<Map<String, Object>> listItems){
		this.mInflater = LayoutInflater.from(context);
		this.listItems = listItems;
	}*/

	public CharacterBaseAdapter(Context context, List<Character> listChars,CharacterListView listView) {
		this.mInflater = LayoutInflater.from(context);
		this.listCharacters = listChars;
		this.listView = listView;
	}
	
	public void changeValue(List<Character> listChars){
		this.listCharacters = listChars;
	}

	@Override
	public int getCount() {
		return listCharacters.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_character_detail, null);
			holder.fcd_context = (TextView) convertView.findViewById(R.id.fragment_character_detail_context);
			
			holder.fcd_support_text = (TextView) convertView.findViewById(R.id.fragment_character_detail_support_text);
			holder.fcd_support_img = (ImageView) convertView.findViewById(R.id.fragment_character_detail_support);
			
			holder.fcd_tread_text = (TextView) convertView.findViewById(R.id.fragment_character_detail_tread_text);
			holder.fcd_tread_img = (ImageView) convertView.findViewById(R.id.fragment_character_detail_tread);
			
			holder.fcd_share_text = (TextView) convertView.findViewById(R.id.fragment_character_detail_share_text);
			
			holder.fcd_follow_text = (TextView) convertView.findViewById(R.id.fragment_character_detail_follow_text);
			
			/*holder.share_view = mInflater.inflate(R.layout.share_dialog, null);
			holder.lin_share_qqfriends = (LinearLayout) holder.share_view.findViewById(R.id.share_sns_qqfriends);
			holder.lin_share_qzone = (LinearLayout) holder.share_view.findViewById(R.id.share_sns_qzone);
			holder.lin_share_sina = (LinearLayout) holder.share_view.findViewById(R.id.share_sns_sina);
			holder.lin_share_weixin = (LinearLayout) holder.share_view.findViewById(R.id.share_sns_weixin);
			*/
		
			convertView.setTag(holder);
			
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		/*holder.fcd_context.setText((String)listItems.get(position).get("context_text"));
		holder.fcd_support_text.setText((String)listItems.get(position).get("support_text"));
		holder.fcd_tread_text.setText((String)listItems.get(position).get("tread_text"));
		holder.fcd_follow_text.setText((String)listItems.get(position).get("follow_text"));*/
		holder.fcd_context.setText(listCharacters.get(position).getChar_context());
		holder.fcd_support_text.setText(listCharacters.get(position).getChar_support());
		holder.fcd_tread_text.setText(listCharacters.get(position).getChar_oppose());
		holder.fcd_follow_text.setText(listCharacters.get(position).getChar_comment());
		
		System.out.println("id: "+listCharacters.get(position).getId()+" 时间: "+listCharacters.get(position).getChar_time());
		final View share_view  = mInflater.inflate(R.layout.share_dialog, null);
		holder.lin_share_qqfriends = (LinearLayout) share_view.findViewById(R.id.share_sns_qqfriends);
		holder.lin_share_qzone = (LinearLayout) share_view.findViewById(R.id.share_sns_qzone);
		holder.lin_share_sina = (LinearLayout) share_view.findViewById(R.id.share_sns_sina);
		holder.lin_share_weixin = (LinearLayout) share_view.findViewById(R.id.share_sns_weixin);
		

		
		OnClickListener lin_share_click = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.share_sns_qqfriends:
					Log.d(TAG, "qq好友");
					break;
				case R.id.share_sns_qzone:
					Log.d(TAG, "qq空间");
					break;
				case R.id.share_sns_sina:
					Log.d(TAG, "新浪");
					break;
				case R.id.share_sns_weixin:
					Log.d(TAG, "微信");
					break;

				}
			}
		};
		holder.lin_share_qqfriends.setOnClickListener(lin_share_click);
		holder.lin_share_qzone.setOnClickListener(lin_share_click);
		holder.lin_share_sina.setOnClickListener(lin_share_click);
		holder.lin_share_weixin.setOnClickListener(lin_share_click);
		
		
		holder.fcd_share_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDialog == null) {
					AlertDialog.Builder builder = new Builder(mInflater
							.getContext());
					builder.setView(share_view);
					mDialog = builder.create();
				}
				mDialog.show();

			}
		});
		
		return convertView;
	}
	
	
	
	public final class ViewHolder{
		//正文
		private TextView fcd_context;
		//点赞的人数
		private TextView fcd_support_text;
		//点赞的按钮
		private ImageView fcd_support_img;
		//点反对的人数
		private TextView fcd_tread_text;
		//点反对的按钮
		private ImageView fcd_tread_img;
		//分享的按钮
		private TextView fcd_share_text;
		//评论的人数
		private TextView fcd_follow_text;
		//QQ好友分享
		private LinearLayout lin_share_qqfriends;
		//QQ空间分享
		private LinearLayout lin_share_qzone;
		//新浪微博分享
		private LinearLayout lin_share_sina;
		//微信分享
		private LinearLayout lin_share_weixin;
		
		//分享的dialog
		//View share_view;
		
	
		
		
	}

	

}
