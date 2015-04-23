package com.bt.qiubai;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.protocol.HTTP;

import com.qiubai.service.WeatherService;
import com.qiubai.util.HttpUtil;
import com.qiubai.util.ReadPropertiesUtil;
import com.qiubai.util.WeatherKeyUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

	private TextView weather_degree;

	private WeatherService weatherService;

	public final static int WeatherToCity = 0;
	public final static int CityBackWeather = 1;

	private final static String TAG = "WeatherActivity";

	private Map<String, String> weatherMap;
	private String cityCode, public_key, private_key, key, getUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_main);

		initWeatherBar();
		getCityFromSharePreference();

		initWeather();

	}

	// weather:{"c":{"c1":"101191101","c2":"changzhou","c3":"常州","c4":"changzhou","c5":"常州","c6":"jiangsu","c7":"江苏","c8":"china","c9":"中国","c10":"2","c11":"0519","c12":"213000","c13":119.948000,"c14":31.766000,"c15":"8","c16":"AZ9513","c17":"+8"},
	// "f":{"f1":[
	// {"fa":"00","fb":"01","fc":"27","fd":"14","fe":"3","ff":"4","fg":"0","fh":"0","fi":"05:23|18:34"},
	// {"fa":"01","fb":"01","fc":"26","fd":"14","fe":"3","ff":"3","fg":"0","fh":"0","fi":"05:21|18:34"},
	// {"fa":"00","fb":"01","fc":"27","fd":"14","fe":"4","ff":"4","fg":"0","fh":"0","fi":"05:20|18:35"}],"f0":"201504231100"}}
	/**
	 * 获取天气信息类的json
	 * 
	 * @param city_town
	 */
	private void getWeather(final String city_town) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				weatherService = new WeatherService();
				cityCode = weatherService.getCityByName(city_town,
						getApplicationContext());// cityCode = 101191101
				public_key = WeatherKeyUtil.JointPublicUrl(cityCode);
				private_key = ReadPropertiesUtil.read("weather", "PRIVATE");

				key = WeatherKeyUtil
						.standardURLEncoder(public_key, private_key);
				getUrl = WeatherKeyUtil.JointUrl(cityCode, key);
				Log.d(TAG, getUrl);
				// String url =
				// "http://open.weather.com.cn/data/?areaid=101191101&type=forecast_f&date=201504231000&appid=543f65&key=%2BvtTYBDDzAF%2FnJ8Vk6cP7tMVS%2BQ%3D";
				String weather = HttpUtil.doGet(getUrl);
				System.out.println("weather:" +weather);
			}
		}).start();
	}

	/**
	 * 加载天气组件栏的其他信息
	 */
	private void initWeather() {
		weather_degree = (TextView) findViewById(R.id.text_weather_degree);
	}

	/**
	 * 从本地的sharepreferences中读取城市列表名称
	 */
	private void getCityFromSharePreference() {
		SharedPreferences share = getSharedPreferences(
				CityActivity.SHAREDPREFERENCES_FIRSTENTER, MODE_PRIVATE);
		String city_town = share.getString(CityActivity.CityActivity_CityTown,
				"常州");
		getWeather(city_town);
		weather_city.setText(city_town);
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
			overridePendingTransition(R.anim.in_from_right,
					R.anim.stay_in_place);
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
