package com.bt.qiubai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import com.qiubai.entity.Picture;
import com.qiubai.service.PictureService;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PictureTextActivity extends Activity implements OnClickListener{
	
	private RelativeLayout pt_title_back,pt_title_menu;
	private LinearLayout action_share,action_comment;
	
	private TextView picture_describe;
	private TextView picture_detail_size;
	private TextView picture_detail_current;
	private TextView picture_title;
	private ViewPager viewpager;
//	private List<View> list;  //表示装载滑动的布局
	private MyPagerAdpater myPagerAdpater;
	
	private static String TAG = "PictureTextActivity";
	
	private Dialog actionDialog;
	private Map<String, String> map;
	private PictureService pictureService;
	private List<Picture> pictures;
	
	private ImageView[] mImageViews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pt_activity);
		
		pt_title_back = (RelativeLayout) findViewById(R.id.pt_title_back);
		pt_title_menu = (RelativeLayout) findViewById(R.id.pt_title_action_bar);
		pt_title_back.setOnClickListener(this);
		pt_title_menu.setOnClickListener(this);
		
		actionDialog = new Dialog(PictureTextActivity.this, R.style.CommonActionDialog);
		actionDialog.setContentView(R.layout.common_action_bar);
		actionDialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
		
		action_share = (LinearLayout) actionDialog.findViewById(R.id.common_action_share);
		action_comment = (LinearLayout) actionDialog.findViewById(R.id.common_action_comment);
		action_share.setOnClickListener(this);
		action_comment.setOnClickListener(this);
		
		picture_describe = (TextView) findViewById(R.id.picture_describe);
		picture_detail_current = (TextView) findViewById(R.id.picture_current);
		picture_detail_size = (TextView) findViewById(R.id.picture_size);
		picture_title = (TextView) findViewById(R.id.picture_title);
		
		Intent intent = getIntent();
		int id = intent.getExtras().getInt("pic_id");
		String title = intent.getExtras().getString("pic_title");
		picture_title.setText(title);
		viewpager = (ViewPager) findViewById(R.id.pt_viewpager);
		
		new LoadPicture().execute(id);
		
		
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setTitleBackground(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}
	
	/**
	 * 设置图片的描述
	 * @param selectItems
	 */
	private void setTitleBackground(int selectItems){
		Log.d(TAG, "selectItems:"+selectItems);
		picture_describe.setText(pictures.get(selectItems).getPic_describe());
		picture_detail_current.setText(String.valueOf(selectItems+1));
	}
	
 	
	/**
	 * 将图片装载到数组中
	 * @param lists
	 */
	private void setImageResource(List<Picture> lists){
		mImageViews = new ImageView[lists.size()];
		for(int i=0;i<mImageViews.length;i++){
			ImageView imageView = new ImageView(this);
			Picasso.with(this).load(lists.get(i).getPic_address()).into(imageView);
			mImageViews[i] = imageView;
		}
		
	}
	
	/**
	 * 返回Picture的list集合
	 * @param result
	 * @return List<Picture>
	 */
	private List<Picture> getDetails(String result){
		List<Picture> lists = new ArrayList<Picture>();
		if(result!=null || !result.equals("error")){
			JSONObject jsonObjects;
			try {
				jsonObjects = new JSONObject(result);
				JSONArray jsonArray = jsonObjects.getJSONArray("pictureDetail");
				
				for(int i=0;i<jsonArray.length()-1;i++){
					Picture picture = new Picture();
					JSONObject jsObject = jsonArray.getJSONObject(i);
					String pic_address = jsObject.getString("pic_address");
					String pic_describe = jsObject.getString("pic_describe");
					picture.setPic_address(pic_address);
					picture.setPic_describe(pic_describe);
					lists.add(picture);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	private class LoadPicture extends AsyncTask<Integer, Void, Object>{

		@Override
		protected Object doInBackground(Integer... params) {
			pictureService = new PictureService();
			map = new HashMap<String, String>();
			map.put("id", String.valueOf(params[0]));
			String result = pictureService.getPictureDetailAll(map);
//			Log.d(TAG, "result:-->"+result);
			pictures = getDetails(result);
			/*for(Picture picture:pictures){
				Log.d(TAG, "picture.getPic_address():"+picture.getPic_address());
				Log.d(TAG, "picture.getPic_describe():"+picture.getPic_describe());
			}*/
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			
			setImageResource(pictures);
			myPagerAdpater = new MyPagerAdpater();
			viewpager.setAdapter(myPagerAdpater);
			
			setTitleBackground(0);
			picture_detail_size.setText(String.valueOf(pictures.size()));
			
		}
		
	}
	

	
	private class MyPagerAdpater extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position
					% mImageViews.length]);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pt_title_back:
			PictureTextActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.pt_title_action_bar:
			actionDialog.show();
			break;
			
		case R.id.common_action_share:
			actionDialog.dismiss();
			break;
			
		case R.id.common_action_comment:
			actionDialog.dismiss();
			Intent intent = new Intent(PictureTextActivity.this, CommentActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			break;
		}
	}

	
	
}
