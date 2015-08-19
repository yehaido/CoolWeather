package com.coolweather.app.util;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.City;
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

}
