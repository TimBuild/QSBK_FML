package com.bt.qiubai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity implements OnClickListener {

	/**
	 * 返回按钮
	 */
	private RelativeLayout weather_back = null;
	/**
	 * 选择某个城市按钮
	 */
	private RelativeLayout weather_location_city = null;
	/**
	 * 分享天气按钮
	 */
	private RelativeLayout weather_share = null;
	
	/**
	 * 城市文本框
	 */
	private TextView weather_city;

	public final static int WeatherToCity = 0;
	public final static int CityBackWeather = 1;
	
	private final static String TAG = "WeatherActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_main);

		initWeatherBar();
	}

	/**
	 * 加载天气组件栏的titlebar
	 */
	private void initWeatherBar() {
		weather_back = (RelativeLayout) findViewById(R.id.rel_weather_title_left);
		weather_location_city = (RelativeLayout) findViewById(R.id.rel_weather_title_location);
		weather_share = (RelativeLayout) findViewById(R.id.rel_weather_title_share);
		weather_city = (TextView) findViewById(R.id.title_city_text);
		
		weather_back.setOnClickListener(this);
		weather_location_city.setOnClickListener(this);
		weather_share.setOnClickListener(this);

	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rel_weather_title_left:
			// 返回
			WeatherActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.rel_weather_title_location:
			Intent intent = new Intent(WeatherActivity.this, CityActivity.class);
			startActivityForResult(intent, WeatherToCity);
			// 选择城市
			break;
		case R.id.rel_weather_title_share:
			// 分享
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == WeatherToCity && resultCode == CityBackWeather) {
			Bundle data = intent.getExtras();
			String city_town = data.getString("city_town");
			weather_city.setText(city_town);
		}
	}
}
