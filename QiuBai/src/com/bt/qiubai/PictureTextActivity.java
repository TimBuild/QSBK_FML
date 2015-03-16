package com.bt.qiubai;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

public class PictureTextActivity extends Activity{
	
	private ViewPager viewpager;
	private List<View> list;  //表示装载滑动的布局
	private MyPagerAdpater myPagerAdpater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picturetext_activity);
		
		viewpager = (ViewPager) findViewById(R.id.pt_viewpager);
		View v1 = getLayoutInflater().inflate(R.layout.detail_title, null);
		View v2 = getLayoutInflater().inflate(R.layout.detail_title, null);
		View v3 = getLayoutInflater().inflate(R.layout.detail_title, null);
		View v4 = getLayoutInflater().inflate(R.layout.detail_title, null);
		
		list = new ArrayList<View>();
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		
		myPagerAdpater = new MyPagerAdpater();
		viewpager.setAdapter(myPagerAdpater);
		
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				System.out.println(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
	}
	
	private class MyPagerAdpater extends PagerAdapter{
		
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(list.get(position));
			return list.get(position);
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(list.get(position));
		}
	}

	
	
}
