/**
 * 
 */
package com.coolweather.app.model;

/**
 * @author yehd
 *
 */
public class County {
	private int id;
	private String countyName;
	private String countyCode;
	private int cityId;
	/**
	 * 
	 */
	public County() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public int getCityId(){
		return cityId;
	}

	public void setCityId(int cityId){
		this.cityId = cityId;
	}
	
	public String getProviceName(){
		return countyName;
	}

	public void setProviceName(String countyName){
		this.countyName = countyName;
	}	
	
	public String getcountyCode(){
		return countyCode;
	}

	public void setId(String countyCode){
		this.countyCode = countyCode;
	}
}
