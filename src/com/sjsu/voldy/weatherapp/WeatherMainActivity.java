package com.sjsu.voldy.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sjsu.voldy.beans.Weather;
import com.sjsu.voldy.beans.WeatherForecast;

public class WeatherMainActivity extends Activity implements LocationListener {
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	String lat;
	String provider;
	Button button;
	protected double latitude,longitude; 
	protected boolean gps_enabled,network_enabled;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_main);
		txtLat = (TextView) findViewById(R.id.textview1);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		addListenerOnButton(this);
		
	}
	
	

	public void addListenerOnButton(final WeatherMainActivity weatherMainActivity) {
		 
		button = (Button) findViewById(R.id.button1);
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				WeatherHttpConnection weatherHttpConnection = new WeatherHttpConnection();
				Weather weather =weatherHttpConnection.fetchCurrentWeather(latitude,longitude,weatherMainActivity);
				WeatherForecast weatherForecast =  weatherHttpConnection.fetchForecastWeather(latitude,latitude,weatherMainActivity);
				
			}
		});
 
	}
	
	
	@Override
	public void onLocationChanged(Location location) {
		txtLat = (TextView) findViewById(R.id.textview1);
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
//		WeatherHttpConnection weatherHttpConnection = new WeatherHttpConnection();
//		weatherHttpConnection.fetchCurrentWeather(location.getLatitude(),location.getLongitude());
//		weatherHttpConnection.fetchForecastWeather(location.getLatitude(),location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
	Log.d("Latitude","disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
	Log.d("Latitude","enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	Log.d("Latitude","status");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
