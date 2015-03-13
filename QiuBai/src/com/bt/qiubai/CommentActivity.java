package com.bt.qiubai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CommentActivity extends Activity {
	
	private RelativeLayout comment_title_back;
	private ListView comment_listview;
	private CommentBaseAdapter commentBaseAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 下面三行代码顺序不能颠倒
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 声明使用自定义标题
		setContentView(R.layout.comment_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.comment_title);
		
		comment_title_back = (RelativeLayout) findViewById(R.id.comment_title_back);
		comment_title_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentActivity.this.finish();
				overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			}
		});
		
		comment_listview = (ListView) findViewById(R.id.comment_listview);
		commentBaseAdapter = new CommentBaseAdapter(this);
		comment_listview.setAdapter(commentBaseAdapter);
		comment_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		
	}
	
	@SuppressLint("ViewHolder")
	private class CommentBaseAdapter extends BaseAdapter {
		private LayoutInflater inflater;// 得到一个LayoutInfalter对象用来导入布局
		
		public CommentBaseAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return 20;
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
			convertView = inflater.inflate(R.layout.comment_listview_item, null);
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			return convertView;
		}
		
	}
}
