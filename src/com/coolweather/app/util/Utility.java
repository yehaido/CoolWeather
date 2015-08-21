package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.City;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONObject;
import org.json.JSONException;


public class Utility {

	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,
			String response){
		if(response != null && !"".equals(response)){
			String[] allProvinces = response.split(",");
			for(String p:allProvinces){
				String[] array = p.split("\\|");
				Province province = new Province();
				province.setProvinceCode(array[0]);
				province.setProviceName(array[1]);
				coolWeatherDB.saveProvince(province);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
			String response,int provinceId){
		if(response != null && !"".equals(response)){
			String[] allCities = response.split(",");
			for(String p:allCities){
				String[] array = p.split("\\|");
				City city = new City();
				city.setCityCode(array[0]);
				city.setCityName(array[1]);
				city.setProvinceIdId(provinceId);
				coolWeatherDB.saveCity(city);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
			String response,int cityId){
		if(response != null && !"".equals(response)){
			String[] allCounties = response.split(",");
			for(String p:allCounties){
				String[] array = p.split("\\|");
				County county = new County();
				county.setCountycode(array[0]);
				county.setCountyName(array[1]);
				county.setCityId(cityId);
				coolWeatherDB.saveCounty(county);
			}
			return true;
		}
		return false;
	}
	
	public static void handleWeatherResponse(Context context,String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
			
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	public static void saveWeatherInfo(Context context,String cityName,String weatherCode,
			String temp1,String temp2,String weatherDesp,String publishTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.
				getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1",temp1);
		editor.putString("temp2",temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}
	

}
