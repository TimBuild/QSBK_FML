package com.qiubai.adapter;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Text;

import com.bt.qiubai.R;
import com.qiubai.entity.PictureList;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureBaseAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Map<String, Object>> listItems;
	/*private ImageView fpd_image_text;
	private TextView fpd_textTitle_text;
	private TextView fpd_comment;*/

	/*private ImageView[] fpd3_image_text = new ImageView[3];
	private TextView fpd3_textTitle_text;
	private TextView fpd3_comment;*/
	
	private String TAG = "PictureBaseAdapter";
	private Context context;
	private List<PictureList> pictureLists;
	
	private ViewHolder holder;

	public PictureBaseAdapter(Context context,List<PictureList> pictureLists) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.pictureLists = pictureLists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictureLists.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setData(List<PictureList> lists){
		this.pictureLists = lists;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println(position);
		if (convertView == null) {
//			if(position%2 == 0){
				holder = new ViewHolder();
	
				convertView = mInflater.inflate(
						R.layout.fragment_picture_detail, null);
				holder.fpd_image_text = (ImageView) convertView
						.findViewById(R.id.fragment_picture_detail_img);
				holder.fpd_textTitle_text = (TextView) convertView
						.findViewById(R.id.fragment_picture_detail_textTitle);
				holder.fpd_comment = (TextView) convertView
						.findViewById(R.id.fragment_picture_detail_comment);
				
		/*	}
			else{
				holder = new ViewHolder();
				
				convertView = mInflater.inflate(
						R.layout.fragment_picture_detail_three, null);
				holder.fpd3_image_text_0 = (ImageView) convertView
						.findViewById(R.id.fragment_picture_detail_three_img1);
				holder.fpd3_image_text_1 = (ImageView) convertView
						.findViewById(R.id.fragment_picture_detail_three_img2);
				holder.fpd3_image_text_2 = (ImageView) convertView
						.findViewById(R.id.fragment_picture_detail_three_img3);
				holder.fpd3_textTitle_text = (TextView) convertView
						.findViewById(R.id.fragment_picture_detail_three_textTitle);
				holder.fpd3_comment = (TextView) convertView
						.findViewById(R.id.fragment_picture_detail_three_comment);
				
			}*/
			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(pictureLists.get(position).getPictures()!=null){
			String title = pictureLists.get(position).getPictures().get(0).getPic_title();
			String url = pictureLists.get(position).getPictures().get(0).getPic_address();
			Log.d(TAG, "pictureLists--->"+pictureLists.toString());
			Log.d(TAG, "url--->"+url);
			holder.fpd_textTitle_text.setText(title);
			Picasso.with(context).load(url).into(holder.fpd_image_text);
		}
//		String url = pictureLists.get(0).getPictures().get(position).getPic_describe();
		/*if(position%2 == 0){
			if(pictureLists.get(position).getPictures()!=null){
				String title = pictureLists.get(position).getPictures().get(0).getPic_title();
				String url = pictureLists.get(position).getPictures().get(0).getPic_address();
				Log.d(TAG, "pictureLists--->"+pictureLists.toString());
				Log.d(TAG, "url--->"+url);
				holder.fpd_textTitle_text.setText(title);
				Picasso.with(context).load(url).into(holder.fpd_image_text);
			}
		}else{
			if(pictureLists.get(position).getPictures()!=null){
				String title = pictureLists.get(position).getPictures().get(0).getPic_title();
				String url = pictureLists.get(position).getPictures().get(0).getPic_address();
				Log.d(TAG, "pictureLists--->"+pictureLists.toString());
				Log.d(TAG, "url--->"+url);
				holder.fpd3_textTitle_text.setText(title);
				Picasso.with(context).load(url).into(holder.fpd3_image_text_0);
				Picasso.with(context).load(url).into(holder.fpd3_image_text_1);
				Picasso.with(context).load(url).into(holder.fpd3_image_text_2);
			}
		}*/
	
		return convertView;

	}
	
	public static class ViewHolder{
		
		ImageView fpd_image_text;
		TextView fpd_textTitle_text;
		TextView fpd_comment;
		
		ImageView fpd3_image_text_0;
		ImageView fpd3_image_text_1;
		ImageView fpd3_image_text_2;
		TextView fpd3_textTitle_text;
		TextView fpd3_comment;
	}

}
