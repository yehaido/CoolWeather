package com.coolweather.app.activity;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.Utility;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.View;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ChooseAreaActivity extends Activity {
	public static final int LEVEL_PROVINCE = 1;
	public static final int LEVEL_CITY = 2;
	public static final int LEVEL_COUNTY = 3;
	
	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();
	
	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countyList;
	
	private Province selectedProvince;
	private City selectedCity;
	private County selectedCounty;
	
	private int currentLevel;
	
	private boolean isFromWeatherActivity;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isFromWeatherActivity =prefs.getBoolean("from_weather_activity", true);
        if(prefs.getBoolean("city_selected", false) 
        		&& !isFromWeatherActivity){
        	Intent intent = new Intent(this,WeatherActivity.class);
        	startActivity(intent);
        	finish();
        	return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        
        titleText = (TextView) findViewById(R.id.title_text);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
        		dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new OnItemClickListener(){
        	@Override
        	public void onItemClick(AdapterView<?> arg0,View view,
        			int index,long arg3){
        		if(currentLevel == LEVEL_PROVINCE){
        			selectedProvince = provinceList.get(index);
        			queryCities();
        		}else if(currentLevel == LEVEL_CITY){
        			selectedCity = cityList.get(index);
        			queryCounties();
        		}else if(currentLevel == LEVEL_COUNTY){
        			String countyCode = countyList.get(index).getCountyCode();
        			Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
        			intent.putExtra("county_code", countyCode);
        			startActivity(intent);
        			finish();
        		}
        	}
        });
    	queryProvinces();

    }
    
    private void queryProvinces(){
    	provinceList = coolWeatherDB.loadProvinces();
    	if(provinceList.size() > 0){
    		dataList.clear();
    		for(Province p:provinceList){
    			dataList.add(p.getProviceName());
    		}
    		adapter.notifyDataSetChanged();;
    		listView.setSelection(0);
    		titleText.setText("�й�");
    		currentLevel = LEVEL_PROVINCE;
    	}else{
    		queryFromServer(null,"province");
    	}
    }
    private void queryCities(){
    	cityList = coolWeatherDB.loadCities(selectedProvince.getId());
    	if(cityList.size() > 0){
    		dataList.clear();
    		for(City p:cityList){
    			dataList.add(p.getCityName());
    		}
    		adapter.notifyDataSetChanged();
    		listView.setSelection(0);
    		titleText.setText(selectedProvince.getProviceName());
    		currentLevel = LEVEL_CITY;
    	}else{
    		queryFromServer(selectedProvince.getProvinceCode(),"city");
    	}
    }
    private void queryCounties(){
    	countyList = coolWeatherDB.loadCounties(selectedCity.getId());
    	if(countyList.size() > 0){
    		dataList.clear();
    		for(County p:countyList){
    			dataList.add(p.getCountyName());
    		}
    		adapter.notifyDataSetChanged();
    		listView.setSelection(0);
    		titleText.setText(selectedCity.getCityName());
    		currentLevel = LEVEL_COUNTY;
    	}else{
    		queryFromServer(selectedCity.getCityCode(),"county");
    	}
    }
    
    private void queryFromServer(final String code,final String type){
    	String address;
    	if(code != null && !"".equals(code)){
    		address = "http://www.weather.com.cn/data/list3/city"+code
    				+".xml";
    	}else{
    		address = "http://www.weather.com.cn/data/list3/city.xml";
    	}
    	showProgressDialog();
    	HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){
    		@Override
    		public void onFinish(String response){
    			boolean result = false;
    			if("province".equals(type)){
    				result = Utility.handleProvincesResponse(coolWeatherDB, response);
    			}else if("city".equals(type)){
    				result = Utility.handleCitiesResponse(coolWeatherDB, 
    						response, selectedProvince.getId());
    			}else if("county".equals(type)){
    				result = Utility.handleCountiesResponse(coolWeatherDB, 
    						response, selectedCity.getId());
    			}
    			if(result){
    				runOnUiThread(new Runnable(){
    					@Override
    					public void run(){
    						closeProgressDialog();
    						if("province".equals(type)){
    							queryProvinces();
    						}else if("city".equals(type)){
    							queryCities();
    						}else if("county".equals(type)){
    							queryCounties();
    						}
    					}
    				});
    			}
    		}
    		
    		@Override
    		public void onError(Exception e){
    			runOnUiThread(new Runnable(){
    				@Override 
    				public void run(){
    					closeProgressDialog();
    					Toast.makeText(ChooseAreaActivity.this,
    							"����ʧ��", Toast.LENGTH_SHORT).show();
    				}
    			});
    		}
    	});
    }
    private void showProgressDialog(){
    	if(progressDialog == null){
    		progressDialog = new ProgressDialog(this);
    		progressDialog.setMessage("���ڼ���");
    		progressDialog.setCanceledOnTouchOutside(false);
    		
    	}
    	progressDialog.show();
    }
    private void closeProgressDialog(){
    	if(progressDialog != null){
    		progressDialog.dismiss();
    	}
    }
    @Override
    public void onBackPressed(){
    	if(currentLevel == LEVEL_COUNTY){
    		queryCities();
    	}else if(currentLevel == LEVEL_CITY){
    		queryProvinces();
    	}else{
    		if(isFromWeatherActivity){
    			Intent intent = new Intent(this,WeatherActivity.class);
    			startActivity(intent);
    			finish();
    		}
    		finish();
    	}
    }
}
