package com.qiubai.entity;

public enum WeatherPhenomena {
	Sunny("晴", "00"), Cloudy("多云", "01"), Overcast("阴", "02"), Shower("阵雨",
			"03"), Thundershower("雷阵雨", "04"), ThundershowerWithHail("雷阵雨伴有冰雹",
			"05"), Sleet("雨夹雪", "06"), LightRain("小雨", "07"), ModerateRain(
			"中雨", "08"), HeavyRain("大雨", "09"), Storm("暴雨", "10"), HeavyStorm(
			"大暴雨", "11"), SevereStorm("特大暴雨", "12"), SnowFlurry("阵雨", "13"), LightSnow(
			"小雪", "14"), ModerateSnow("中雪", "15"), HeavySnow("大雪", "16"), SnowStorm(
			"暴雪", "17"), Foggy("雾", "18"), IceRain("冻雨", "19"), DustStorm(
			"沙尘暴", "20"), LightToModerateRain("小到中雨", "21"), ModerateToHeavyRain(
			"中到大雨", "22"), HeavyRainToStorm("大到暴雨", "23"), StormToHeavyStorm(
			"暴雨到大暴雨", "24"), HeavyToSevereStorm("大暴雨到特大暴雨", "25"), LightToModerateSnow(
			"小到中雪", "26"), ModerateToHeavySnow("中到大雪", "27"), HeavySnowToSnowStorm(
			"大到暴雪", "28"), Dust("浮尘", "29"), Sand("扬沙", "30"), SandStorm(
			"强沙尘暴", "31"), Haze("霾", "53"), Unknown("无", "99");

	private String name;
	private String index;

	private WeatherPhenomena(String name, String index) {
		this.name = name;
		this.index = index;
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

}
