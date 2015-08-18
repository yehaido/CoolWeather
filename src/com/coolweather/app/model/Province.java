/**
 * 
 */
package com.coolweather.app.model;

/**
 * @author yehd
 *
 */
public class Province {
	private int id;
	private String provinceName;
	private String provinceCode;
	/**
	 * 
	 */
	public Province() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public String getProviceName(){
		return provinceName;
	}

	public void setProviceName(String provinceName){
		this.provinceName = provinceName;
	}	
	
	public String getProvinceCode(){
		return provinceCode;
	}

	public void setId(String provinceCode){
		this.provinceCode = provinceCode;
	}
}
