package com.coolweather.app.db;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class CoolWeatherDB {
	public static final String DB_NAME = "cool_weahter";
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	
	public CoolWeatherDB(Context context) {
		// TODO Auto-generated constructor stub
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}

}
