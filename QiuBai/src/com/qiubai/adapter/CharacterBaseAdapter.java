package com.qiubai.adapter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.qiubai.FirstEnterActivity;
import com.bt.qiubai.R;
import com.qiubai.common.CommonUtil;
import com.qiubai.entity.Character;
import com.qiubai.fragment.CharacterFragment;
import com.qiubai.service.CharacterService;
import com.qiubai.ui.CharacterListView;
import com.qiubai.util.HttpUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterBaseAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
//	private List<Map<String, Object>> listItems;
	private List<Character> listCharacters;
	private AlertDialog mDialog;
	private CharacterListView listView;
	
	private CharacterService charService;
	
	ViewHolder holder = null;
	
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	
//	private OnRefreshListener refreshListener;

	
	private String TAG = "CharacterBaseAdapter";
	
	private View share_view;
	
	private Context context;
	
	private String addSupportTread = CommonUtil.getADD_CHARACTER_SUPPORT_OPPOSE();
	private Map<String, String> addMap;
	
	private Thread addThread; 

	/*public CharacterBaseAdapter(Context context,List<Map<String, Object>> listItems){
		this.mInflater = LayoutInflater.from(context);
		this.listItems = listItems;
	}*/
	
	public static final int SUPPORT = 3;
	public static final int TREAD = 1;
	
	public Map<Integer, String> isCanSupport = new HashMap<Integer, String>() ;//判断是否点击过
	
	public addSupOppose addSupOpp;
	/*
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUPPORT:
				String support_text = (String) msg.obj;
				System.out.println(TAG+"  "+support_text);
				
				holder.fcd_support_text.setText(support_text);
				
				int pos = (Integer) msg.obj;
//				System.out.println(TAG+">>>"+pos);
				View view = getView(pos, null, null);
				TextView textview = (TextView) view.findViewById(R.id.fragment_character_detail_support_text);
				String supportText = String.valueOf(Integer.parseInt(textview.getText().toString())+1);
				System.out.println("supportText:"+supportText);
				textview.setText("3");
				
				break;

			case TREAD:
				break;
			}
		};
	};*/
	
	/*public class threadSupport implements Runnable{
		
		int pos;
		public threadSupport(int position){
			this.pos = position;
		}

		@Override
		public void run() {
			int char_support = Integer.parseInt(listCharacters.get(pos).getChar_support());
			System.out.println(TAG+"  "+pos+"--->"+String.valueOf(char_support+1));
			Message msg = handler.obtainMessage(SUPPORT);
			//msg.obj = String.valueOf(char_support+1);
			msg.obj = pos;
			handler.sendMessage(msg);
		}
		
	}*/

	public CharacterBaseAdapter(Context context, List<Character> listChars,CharacterListView listView) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.listCharacters = listChars;
		this.listView = listView;
//		this.isCanSupport= new HashMap<Integer, String>();
		this.addMap = new HashMap<String, String>();
//		notifyDataSetChanged();
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
		return listCharacters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
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
			
			share_view  = mInflater.inflate(R.layout.share_dialog, null);
			holder.lin_share_qqfriends = (LinearLayout) share_view.findViewById(R.id.share_sns_qqfriends);
			holder.lin_share_qzone = (LinearLayout) share_view.findViewById(R.id.share_sns_qzone);
			holder.lin_share_sina = (LinearLayout) share_view.findViewById(R.id.share_sns_sina);
			holder.lin_share_weixin = (LinearLayout) share_view.findViewById(R.id.share_sns_weixin);
		//	isCanSupport.put(position, false);
			
			
			convertView.setTag(holder);
			
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		if (!isCanSupport.containsKey(position)) {
			isCanSupport.put(position, "false");
		}
		System.out.println("position---" + position + " isCansupport:"
				+ isCanSupport.get(position));
		
		/*if(isCanSupport.get(position).equals("support")){
			holder.fcd_support_img.setImageResource(R.drawable.character_list_other_segments_support_done);
		}else if(isCanSupport.get(position).equals("tread")){
			holder.fcd_tread_img.setImageResource(R.drawable.character_list_other_segments_tread_done);
		}else{
			holder.fcd_support_img.setImageResource(R.drawable.character_list_other_segments_support);
			holder.fcd_tread_img.setImageResource(R.drawable.character_list_other_segments_tread);
		}*/
			
			
		
		
		holder.fcd_support_img.setImageResource(R.drawable.character_list_other_segments_support);
		holder.fcd_tread_img.setImageResource(R.drawable.character_list_other_segments_tread);
		
		holder.fcd_context.setText(listCharacters.get(position).getChar_context());
		holder.fcd_support_text.setText(listCharacters.get(position).getChar_support());
		holder.fcd_tread_text.setText(listCharacters.get(position).getChar_oppose());
		holder.fcd_follow_text.setText(listCharacters.get(position).getChar_comment());
		
		OnClickListener lin_share_click = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.share_sns_qqfriends://qq好友
					Log.d(TAG, "qq好友");
					break;
				case R.id.share_sns_qzone://qq空间
					Log.d(TAG, "qq空间");
					break;
				case R.id.share_sns_sina://新浪
					Log.d(TAG, "新浪");
					break;
				case R.id.share_sns_weixin://微信
					Log.d(TAG, "微信");
					break;

				}
			}
		};
		holder.lin_share_qqfriends.setOnClickListener(lin_share_click);
		holder.lin_share_qzone.setOnClickListener(lin_share_click);
		holder.lin_share_sina.setOnClickListener(lin_share_click);
		holder.lin_share_weixin.setOnClickListener(lin_share_click);
		
		//点击分享按钮
		holder.fcd_share_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("id: "+listCharacters.get(position).getId()+" 时间: "+listCharacters.get(position).getChar_context());
				
				if (mDialog == null) {
					AlertDialog.Builder builder = new Builder(mInflater
							.getContext());
					builder.setView(share_view);
					mDialog = builder.create();
				}
				mDialog.show();

			}
		});		
		
		//点击'点赞'
		holder.fcd_support_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int char_support = Integer.parseInt(listCharacters.get(position).getChar_support())+1;
//				int char_tread = Integer.parseInt(listCharacters.get(position).getChar_oppose())-1;
				
				String char_support_text = String.valueOf(char_support);
				String char_tread_text = listCharacters.get(position).getChar_oppose();
				updateSupport(position,char_support_text,char_tread_text);
				
			}
		});
		//点击'吐槽'
		holder.fcd_tread_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int char_tread = Integer.parseInt(listCharacters.get(position).getChar_oppose())+1;
//				int char_support = Integer.parseInt(listCharacters.get(position).getChar_support())-1;
				
				String char_tread_text = String.valueOf(char_tread);
				String char_support_text = listCharacters.get(position).getChar_support();
				
				updateTread(position, char_tread_text,char_support_text);
			}
		});
		
		return convertView;
	}
	
	
	
	public class addSupOppose implements Runnable{
		
		private Map<String,String> addMap;
		private String addSupportTread;
		
		public addSupOppose(Map<String, String> addMap,String addSupportTread){
			this.addMap = addMap;
			this.addSupportTread = addSupportTread;
		}

		@Override
		public synchronized void run() {
			HttpUtil.doPost(addMap, addSupportTread);
		}
		
	}
	
	/**
	 * 局部刷新点赞的人数
	 * @param position
	 * @param char_support
	 */
	private void updateSupport(int position,String char_support_text,String char_tread_text){
		int firstPosition = listView.getFirstVisiblePosition();
		int lastPosition = listView.getLastVisiblePosition();
		
		Log.d(TAG, "position:"+position+" firstPosition:"+firstPosition+" lastposition:"+lastPosition);
		if(position>=firstPosition&&position<=lastPosition){
			View view = listView.getChildAt(position-firstPosition+1);
			ViewHolder vh = null;
			if(view.getTag() instanceof ViewHolder){
				vh = (ViewHolder) view.getTag();
				vh.fcd_support_text.setText(char_support_text);
				vh.fcd_support_img.setImageResource(R.drawable.character_list_other_segments_support_done);
				if(isCanSupport.get(position).equals("tread")){
					vh.fcd_tread_img.setImageResource(R.drawable.character_list_other_segments_tread);
					char_tread_text = String.valueOf(Integer.parseInt(char_tread_text)-1);
					vh.fcd_tread_text.setText(char_tread_text);
					addMap.put("oppose", char_tread_text);
				}
				else{
					addMap.put("oppose", char_tread_text);
				}
				addMap.put("id", String.valueOf(listCharacters.get(position).getId()));
				addMap.put("support", char_support_text);
				listCharacters.get(position).setChar_support(char_support_text);
				listCharacters.get(position).setChar_oppose(char_tread_text);
				
				vh.fcd_support_img.startAnimation(AnimationUtils.loadAnimation(context, R.anim.support_animation));
				
				addSupOpp = new addSupOppose(addMap,addSupportTread);
				addThread = new Thread(addSupOpp);
				addThread.start();
			}
			
			isCanSupport.put(position, "support");
		}
	}
	private void updateTread(int position,String char_tread_text,String char_support_text){
		int firstPosition = listView.getFirstVisiblePosition();
		int lastPosition = listView.getLastVisiblePosition();
		
		if(position>=firstPosition&&position<=lastPosition){
			View view = listView.getChildAt(position-firstPosition+1);
			ViewHolder vh = null;
			if(view.getTag() instanceof ViewHolder){
				vh = (ViewHolder) view.getTag();
				vh.fcd_tread_text.setText(char_tread_text);
				vh.fcd_tread_img.setImageResource(R.drawable.character_list_other_segments_tread_done);
				if(isCanSupport.get(position).equals("support")){
					char_support_text = String.valueOf(Integer.parseInt(char_support_text)-1);
					vh.fcd_support_text.setText(char_support_text);
					vh.fcd_support_img.setImageResource(R.drawable.character_list_other_segments_support);
					addMap.put("support", char_support_text);
				}
				else{
					addMap.put("support", char_support_text);
				}
				addMap.put("id", String.valueOf(listCharacters.get(position).getId()));
				addMap.put("oppose", char_tread_text);
				listCharacters.get(position).setChar_support(char_support_text);
				listCharacters.get(position).setChar_oppose(char_tread_text);
				/*new Thread(new Runnable() {
					
					@Override
					public void run() {
						HttpUtil.doPost(addMap, addSupportTread);
					}
				}).start();*/
				
				vh.fcd_tread_img.startAnimation(AnimationUtils.loadAnimation(context, R.anim.support_animation));
				
				addSupOpp = new addSupOppose(addMap,addSupportTread);
				addThread = new Thread(addSupOpp);
				addThread.start();
			}
		}
		isCanSupport.put(position, "tread");
	}
	
	
	public static final class ViewHolder{
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
		
	}
	
}
