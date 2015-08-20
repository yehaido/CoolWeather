/**
 * 
 */
package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author yehd
 *
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
	public static final String DROP_TABLE_PROVINCE = "drop table Province";
	public static final String DROP_TABLE_CITY = "drop table City";
	public static final String DROP_TABLE_COUNTY = "drop table County";
	
	public static final String CREAT_PROVINCE = "create table Province ("
			+ "id integer primary key autoincrement,"
			+ "province_name text,"
			+ "province_code text)";
	
	public static final String CREATE_CITY = "create table City ("
			+ "id integer primary key autoincrement,"
			+ "city_name text,"
			+ "city_code text,"
			+ "province_id integer)";
	
	public static final String CREATE_COUNTY = "create table County ("
			+ "id integer primary key autoincrement,"
			+ "county_name text,"
			+ "county_code text,"
			+ "city_id integer)";
	
	public CoolWeatherOpenHelper(Context context,String name,
			CursorFactory factory,int version){
		super(context,name,factory,version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREAT_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		db.execSQL(DROP_TABLE_COUNTY);
		db.execSQL(DROP_TABLE_CITY);
		db.execSQL(DROP_TABLE_PROVINCE);
		onCreate(db);
	}
}
