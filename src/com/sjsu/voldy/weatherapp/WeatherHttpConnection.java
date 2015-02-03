package com.sjsu.voldy.weatherapp;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sjsu.voldy.beans.DayForecast;
import com.sjsu.voldy.beans.Weather;
import com.sjsu.voldy.beans.WeatherForecast;

public class WeatherHttpConnection{
	Weather weather;
	WeatherForecast weatherForecast;
	Weather fetchCurrentWeather(double lat, double longi, final WeatherMainActivity weatherMainActivity){
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params=new RequestParams();
		//params.put("user_id","");
		String latString = Double.toString(lat);
		String longiString = Double.toString(longi);
		
		client.get("http://api.openweathermap.org/data/2.5/weather?lat="+latString+"&lon="+longiString+"&units=imperial",params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, Header[] header, JSONObject response) {
				// TODO Auto-generated method stub
				System.out.println("Success");
				try {
					 weather = JSONWeatherParser.getWeather(response);
					 displayWeatherDetails(weather,weatherMainActivity);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		return weather;
	}

	public WeatherForecast fetchForecastWeather(double lat, double longi, final WeatherMainActivity weatherMainActivity) {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params=new RequestParams();
		//params.put("user_id","");
		String latString = Double.toString(lat);
		String longiString = Double.toString(longi);
		
		client.get("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+latString+"&lon="+longiString+"&cnt=2&mode=json&units=imperial",params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int arg0, Header[] header, JSONObject response) {
				// TODO Auto-generated method stub
				System.out.println("Success");
				try {
					weatherForecast = JSONWeatherParser.getForecastWeather(response);
					displayWeatherForecastDetails(weatherForecast,weatherMainActivity);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		

		});
		
		return weatherForecast;
	}
	
	private void displayWeatherDetails(Weather weather, WeatherMainActivity weatherMainActivity) {
		
		// TODO Auto-generated method stub
		TextView condDescr = (TextView) weatherMainActivity.findViewById(R.id.condDescr);
		TextView temp = (TextView) weatherMainActivity.findViewById(R.id.temp);
		TextView press = (TextView) weatherMainActivity.findViewById(R.id.press);
		TextView hum = (TextView) weatherMainActivity.findViewById(R.id.hum);
		TextView windSpeed = (TextView) weatherMainActivity.findViewById(R.id.windSpeed);
		TextView windDeg = (TextView) weatherMainActivity.findViewById(R.id.windDeg);
		
		condDescr.setText(weather.currentCondition.getCondition()+" \n"+weather.currentCondition.getDescr());
		temp.setText("\t"+Float.toString(weather.temperature.getTemp()));
		press.setText("\t"+Float.toString(weather.currentCondition.getPressure()));
		hum.setText("\t"+Float.toString(weather.currentCondition.getHumidity()));
		windSpeed.setText("\t"+Float.toString(weather.wind.getSpeed()));
		windDeg.setText("\t"+Float.toString(weather.wind.getDeg()));
		
	}
	private void displayWeatherForecastDetails(WeatherForecast weatherForecast, WeatherMainActivity weatherMainActivity) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				
		
				TextView condDescr = (TextView) weatherMainActivity.findViewById(R.id.condDescrF);
				TextView temp = (TextView) weatherMainActivity.findViewById(R.id.tempF);
				TextView press = (TextView) weatherMainActivity.findViewById(R.id.pressF);
				TextView hum = (TextView) weatherMainActivity.findViewById(R.id.humF);
				TextView windSpeed = (TextView) weatherMainActivity.findViewById(R.id.windSpeedF);
				TextView windDeg = (TextView) weatherMainActivity.findViewById(R.id.windDegF);
				
				DayForecast forecast =weatherForecast.getForecast(1);
				
				
				condDescr.setText(forecast.weather.currentCondition.getCondition()+" \n"+forecast.weather.currentCondition.getDescr());
				//temp.setText("\t"+Float.toString(forecast.weather.temperature.getTemp()));
				press.setText("\t"+Float.toString(forecast.weather.currentCondition.getPressure()));
				hum.setText("\t"+Float.toString(forecast.weather.currentCondition.getHumidity()));
				windSpeed.setText("\t"+Float.toString(forecast.weather.wind.getSpeed()));
				windDeg.setText("\t"+Float.toString(forecast.weather.wind.getDeg()));
	}
}
