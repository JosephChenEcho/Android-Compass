package com.example.compass;

import android.hardware.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class MainActivity extends Activity {
	
	float pitch = 0;
	float roll =0;
	float heading = 0;
	
	CompassView compassView;
	SensorManager sensorManager;

	private final SensorListener sensorListener = new SensorListener(){
		
		@Override
		public void onSensorChanged(int sensor, float[] values){
			updateOrientation(values[SensorManager.DATA_X],values[SensorManager.DATA_Y],values[SensorManager.DATA_Z]);
		}
		
		@Override
		public void onAccuracyChanged(int sensor, int accuracy){}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		compassView = (CompassView)this.findViewById(R.id.compassView);
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		updateOrientation(0,0,0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		sensorManager.registerListener(sensorListener, SensorManager.SENSOR_ORIENTATION,SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	@Override
	protected void onStop(){
		sensorManager.unregisterListener(sensorListener);
		super.onStop();
	}
	
	private void updateOrientation(float _roll,float _pitch, float _heading){
		heading = _heading;
		pitch = _pitch;
		roll = _roll;
		
		if (compassView != null){
			compassView.setBearing(heading);
			compassView.setPitch(pitch);
			compassView.setRoll(roll);
			compassView.invalidate();
		}
	}

}
