package com.bt.qiubai;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiubai.db.DBManager;
import com.qiubai.db.DbOpenHelper;
import com.qiubai.fragment.CharacterFragment;
import com.qiubai.fragment.HotFragment;
import com.qiubai.fragment.PictureFragment;
import com.qiubai.service.CityService;
import com.qiubai.service.WeatherService;
import com.qiubai.util.BitmapUtil;
import com.qiubai.util.DensityUtil;
import com.qiubai.util.HttpUtil;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout main_title_reL_menu, rel_main_right, main_title_rel_person, 
		main_viewpager_title_rel_hot, main_viewpager_title_rel_character, main_viewpager_title_rel_picture;
	private LinearLayout lin_weather, lin_setting;
	private ImageView main_viewpager_title_iv_hot, main_viewpager_title_iv_character;
	private TextView text_weather;
	private ViewPager main_viewpager;
	
	private Bitmap bitmap_underline;
	private Dialog rightDialog;
	private List<Fragment> list_fragments = new ArrayList<Fragment>();
	private MainFragmentPagerAdapter mainFragmentPagerAdpater;
	
	private WeatherService weatherService;
	private CityService cityService;
	
	private final static int WEATHER = 1; 
	private final static int EXIT = 2; 
	private static boolean isExit = false; // 定义变量，判断是否退出
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title);
		
		main_title_reL_menu = (RelativeLayout) findViewById(R.id.main_title_reL_menu);
		main_title_reL_menu.setOnClickListener(this);
		rel_main_right = (RelativeLayout) findViewById(R.id.rel_main_title_right);
		rel_main_right.setOnClickListener(this);
		main_title_rel_person = (RelativeLayout) findViewById(R.id.main_title_rel_person);
		main_title_rel_person.setOnClickListener(this);
		main_viewpager_title_rel_hot = (RelativeLayout) findViewById(R.id.main_viewpager_title_rel_hot);
		main_viewpager_title_rel_hot.setOnClickListener(this);
		main_viewpager_title_rel_character = (RelativeLayout) findViewById(R.id.main_viewpager_title_rel_character);
		main_viewpager_title_rel_character.setOnClickListener(this);
		main_viewpager_title_rel_picture = (RelativeLayout) findViewById(R.id.main_viewpager_title_rel_picture);
		main_viewpager_title_rel_picture.setOnClickListener(this);
		main_viewpager_title_iv_hot = (ImageView) findViewById(R.id.main_viewpager_title_iv_hot);
		main_viewpager_title_iv_character = (ImageView) findViewById(R.id.main_viewpager_title_iv_character);
		bitmap_underline = BitmapUtil.resizeBitmapFillBox(getWindowManager().getDefaultDisplay().getWidth() / 3, DensityUtil.dip2px(this, 2.5f), BitmapFactory.decodeResource(getResources(), R.drawable.main_viewpager_title_underline));
		main_viewpager_title_iv_hot.setImageBitmap(bitmap_underline);
		//main_viewpager_title_iv_character.setImageBitmap(bitmap_underline);
		
		initTitleDialog(); //加载titlebar的dialog控件
		
		main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
		initFragment();
		mainFragmentPagerAdpater = new MainFragmentPagerAdapter(getSupportFragmentManager());
		main_viewpager.setAdapter(mainFragmentPagerAdpater);
		main_viewpager.setCurrentItem(0);
		main_viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				//System.out.println("arg0: " + arg0);
				//System.out.println("arg1: " + arg1);
				System.out.println("arg2: " + arg2);
				moveViewPagerTitleUnderline((float)arg2, bitmap_underline, main_viewpager_title_iv_hot);
				moveViewPagerTitleUnderline((float)arg2, bitmap_underline, main_viewpager_title_iv_character);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}

	/**
	 * initialize title dialog
	 */
	private void initTitleDialog() {
		rightDialog = new Dialog(MainActivity.this, R.style.CommonActionDialog);
		rightDialog.setContentView(R.layout.main_menu_action_bar);
		rightDialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
		lin_weather = (LinearLayout) rightDialog.findViewById(R.id.main_menu_action_weather_lin);
		lin_setting = (LinearLayout) rightDialog.findViewById(R.id.main_menu_action_setting_lin);
		text_weather = (TextView) rightDialog.findViewById(R.id.main_menu_action_weather);
		lin_weather.setOnClickListener(this);
		lin_setting.setOnClickListener(this);
	}
	
	/**
	 * initialize fragment
	 */
	private void initFragment() {
		HotFragment hotFragment = new HotFragment();
		CharacterFragment characterFragment = new CharacterFragment();
		PictureFragment pictureFragment = new PictureFragment();
		list_fragments.add(hotFragment);
		list_fragments.add(characterFragment);
		list_fragments.add(pictureFragment);
	}

	private class MainFragmentPagerAdapter extends FragmentPagerAdapter{

		public MainFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return list_fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return list_fragments.size();
		}
	}
	
	private void moveViewPagerTitleUnderline(float distanceX, Bitmap bitmap, ImageView imageview){
		System.out.println("distanceX: " + distanceX);
		//Bitmap bitmap_underline_translate = BitmapUtil.translateBitmap(distanceX, 0.0f, bitmap_underline);
		Bitmap alterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
		Canvas canvas = new Canvas(alterBitmap);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		Matrix matrix = new Matrix();
		matrix.setTranslate(-distanceX/3, 0.0f);
		canvas.drawBitmap(bitmap, matrix, paint);
		imageview.setImageBitmap(alterBitmap);
		
	}
	
	private void initWeather(final String cityName) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				DbOpenHelper dbHelper = new DbOpenHelper(getApplicationContext());
				DBManager dbManager = new DBManager(getApplicationContext());
				dbManager.copyDatabase();
				weatherService = new WeatherService();
				String city = weatherService.getCityByName(cityName, getApplicationContext());
				String weatherUrl = "http://www.weather.com.cn/data/cityinfo/"+city+".html";
				/*
				 * {"weatherinfo":{"city":"常州","cityid":"101191101","temp1":"15℃"
				 * ,"temp2":"9℃","weather":"阵雨","img1":"d3.gif","img2":"n3.gif",
				 * "ptime":"08:00"}}
				 */
				String weatherJson = HttpUtil.doGet(weatherUrl);
//				System.out.println("天气："+result);
				try {
					JSONObject jsonObject = new JSONObject(weatherJson);
					JSONObject weatherObject = jsonObject.getJSONObject("weatherinfo");
					
					String temp1 = weatherObject.getString("temp1");
					String temp2 = weatherObject.getString("temp2");
					String temp = temp1+"/"+temp2;
					Message msg = new Message();
					msg.what = WEATHER;
					msg.obj = temp;
					mainHandler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitApplication();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	};

	private void exitApplication() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			// 利用Handler延迟2秒发送更改消息
			mainHandler.sendEmptyMessageDelayed(EXIT, 2000);
		} else {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_title_reL_menu:
			// 点击左边的按钮响应事件

			// 跳转到Login activity
			//Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
			//startActivity(intent_login);
			break;
		case R.id.rel_main_title_right:
			rightDialog.show();
			
			SharedPreferences share = getSharedPreferences(CityActivity.SHAREDPREFERENCES_FIRSTENTER, MODE_PRIVATE);
			String city = share.getString(CityActivity.CityActivity_CityTown, "常州");
			initWeather(city);
			// 点击右边的按钮响应事件
			// 跳转到detail activity
			//Intent intent = new Intent(MainActivity.this, CharacterDetailActivity.class);
			//startActivity(intent);
			break;

		case R.id.main_title_rel_person:
			break;
		case R.id.main_viewpager_title_rel_hot:
			break;
		case R.id.main_viewpager_title_rel_character:
			break;
		case R.id.main_viewpager_title_rel_picture:
			break;
		case R.id.main_menu_action_weather_lin:
			//点击天气
			rightDialog.dismiss();
			Intent intent_weather = new Intent(MainActivity.this, WeatherActivity.class);
			startActivity(intent_weather);
			//Toast.makeText(MainActivity.this, "今天天气晴朗", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_menu_action_setting_lin:
			//点击设置
			rightDialog.dismiss();
			Toast.makeText(MainActivity.this, "点击设置，准备跳转", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mainHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == EXIT) {
				isExit = false; // 退出程序
			} else if (msg.what == WEATHER) {
				String temp = (String) msg.obj;
				System.out.println("天气：" + temp);
				text_weather.setText(temp);
			}
		}
	};

}
