package com.qiubai.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qiubai.dao.WeatherDao;
import com.qiubai.db.DbOpenHelper;

public class WeatherDaoImpl implements WeatherDao {
	private DbOpenHelper dbHelper = null;

	public WeatherDaoImpl(Context context) {
		dbHelper = new DbOpenHelper(context);
	}

	@Override
	public String getCityIdByCityName(String city) {

		String sql = "select * from city_table where CITY =" + "'" + city + "'";

		SQLiteDatabase database = null;

		database = dbHelper.getReadableDatabase();

		Cursor cursor = database.rawQuery(sql, null);

		String cityName = null;
		if (cursor != null) {
			cursor.moveToFirst();
			cityName = cursor.getString(cursor.getColumnIndex("WEATHER_ID"));

		}
		return cityName;
	}

}
