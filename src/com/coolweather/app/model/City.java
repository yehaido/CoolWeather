/**
 * 
 */
package com.coolweather.app.model;

/**
 * @author yehd
 *
 */
public class City {
	private int id;
	private String cityName;
	private String cityCode;
	private int provinceId;
	/**
	 * 
	 */
	public City() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public int getProvinceId(){
		return provinceId;
	}

	public void setProvinceIdId(int provinceId){
		this.provinceId = provinceId;
	}
	
	public String getCityName(){
		return cityName;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}	
	
	public String getCityCode(){
		return cityCode;
	}

	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}
}
