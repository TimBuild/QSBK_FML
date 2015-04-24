package com.qiubai.entity;

public class Weather {
	private String dayWeatherPhenomena;// 白天天气现象
	private String nightWeatherPhenomena;// 晚上天气现象
	private String dayTemperature;// 白天温度
	private String nightTemperature;// 晚上温度
	private String dayWind;// 白天分向
	private String nightWind;// 晚上分向
	private String dayWindPower;// 白天分力
	private String nightWindPower;// 晚上分力

	private int phenIcon;// 空气图标

	public String getDayWeatherPhenomena() {
		return dayWeatherPhenomena;
	}

	public void setDayWeatherPhenomena(String dayWeatherPhenomena) {
		this.dayWeatherPhenomena = dayWeatherPhenomena;
	}

	public String getNightWeatherPhenomena() {
		return nightWeatherPhenomena;
	}

	public void setNightWeatherPhenomena(String nightWeatherPhenomena) {
		this.nightWeatherPhenomena = nightWeatherPhenomena;
	}

	public String getDayTemperature() {
		return dayTemperature;
	}

	public void setDayTemperature(String dayTemperature) {
		this.dayTemperature = dayTemperature;
	}

	public String getNightTemperature() {
		return nightTemperature;
	}

	public void setNightTemperature(String nightTemperature) {
		this.nightTemperature = nightTemperature;
	}

	public String getDayWind() {
		return dayWind;
	}

	public void setDayWind(String dayWind) {
		this.dayWind = dayWind;
	}

	public String getNightWind() {
		return nightWind;
	}

	public void setNightWind(String nightWind) {
		this.nightWind = nightWind;
	}

	public String getDayWindPower() {
		return dayWindPower;
	}

	public void setDayWindPower(String dayWindPower) {
		this.dayWindPower = dayWindPower;
	}

	public String getNightWindPower() {
		return nightWindPower;
	}

	public void setNightWindPower(String nightWindPower) {
		this.nightWindPower = nightWindPower;
	}

	public int getPhenIcon() {
		return phenIcon;
	}

	public void setPhenIcon(int phenIcon) {
		this.phenIcon = phenIcon;
	}

}
