package com.qiubai.entity;

import com.bt.qiubai.R;

public enum WeatherPhenomena {
	Sunny("晴", "00", R.drawable.day00), 
	Cloudy("多云", "01", R.drawable.day01), 
	Overcast("阴", "02", R.drawable.day02),
	Shower("阵雨", "03", R.drawable.day03),
	Thundershower("雷阵雨", "04", R.drawable.day04),
	ThundershowerWithHail("雷阵雨伴有冰雹","05", R.drawable.day05),
	Sleet("雨夹雪", "06", R.drawable.day06),
	LightRain("小雨", "07", R.drawable.day07),
	ModerateRain("中雨", "08",R.drawable.day08),
	HeavyRain("大雨", "09", R.drawable.day09),
	Storm("暴雨", "10", R.drawable.day10),
	HeavyStorm("大暴雨", "11",R.drawable.day11),
	SevereStorm("特大暴雨", "12", R.drawable.day12),
	SnowFlurry("阵雨", "13", R.drawable.day13),
	LightSnow("小雪", "14",R.drawable.day14), 
	ModerateSnow("中雪", "15", R.drawable.day15),
	HeavySnow("大雪", "16", R.drawable.day16),
	SnowStorm("暴雪", "17",R.drawable.day17),
	Foggy("雾", "18", R.drawable.day18),
	IceRain("冻雨", "19", R.drawable.day19),
	DustStorm("沙尘暴", "20",R.drawable.day20),
	LightToModerateRain("小到中雨", "21",R.drawable.day21),
	ModerateToHeavyRain("中到大雨", "22",R.drawable.day22),
	HeavyRainToStorm("大到暴雨", "23", R.drawable.day23),
	StormToHeavyStorm("暴雨到大暴雨", "24", R.drawable.day24),
	HeavyToSevereStorm("大暴雨到特大暴雨","25", R.drawable.day25),
	LightToModerateSnow("小到中雪", "26",R.drawable.day26),
	ModerateToHeavySnow("中到大雪", "27",R.drawable.day27),
	HeavySnowToSnowStorm("大到暴雪", "28",R.drawable.day28),
	Dust("浮尘", "29", R.drawable.day29),
	Sand("扬沙","30", R.drawable.day30),
	SandStorm("强沙尘暴", "31", R.drawable.day31),
	Haze("霾", "53", R.drawable.day53),
	Unknown("无", "99", R.drawable.dayundefined);

	private String name;
	private String index;
	private int imageResource;

	private WeatherPhenomena(String name, String index, int imageResource) {
		this.name = name;
		this.index = index;
		this.imageResource = imageResource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public int getImageResource() {
		return imageResource;
	}

	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}

	/**
	 * 根据字符串返回相对应的天气现象
	 * 
	 * @param index
	 * @return
	 */
	public static String getPhenomenaName(String index) {

		for (WeatherPhenomena wp : WeatherPhenomena.values()) {
			if (wp.getIndex().equals(index)) {
				return wp.getName();
			}
		}
		return null;

	}
	
	/**
	 * 根据字符串返回相对应的天气现象图标
	 * @param index
	 * @return
	 */
	public static int getPhenomenaPicture(String index){
		for (WeatherPhenomena wp : WeatherPhenomena.values()) {
			if (wp.getIndex().equals(index)) {
				return wp.getImageResource();
			}
		}
		return 0;
	}

}
