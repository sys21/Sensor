package com.sensor;

import java.util.List;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements SensorEventListener {
	private boolean mRegisteredSensor;
	private SensorManager mSensorManager;
	private TextView Text;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRegisteredSensor = false;
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Text = (TextView) findViewById(R.id.label_text);
		image = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			mRegisteredSensor = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
			mRegisteredSensor = true;
		}
	}

	@Override
	protected void onPause() {
		if (mRegisteredSensor) {
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			// values[0]:
			// Azimuth, angle between the magnetic north direction and the Y
			// axis,
			// around the Z axis (0 to 359). 0=North, 90=East, 180=South,
			// 270=West
			// values[1]:
			// Pitch, rotation around X axis (-180 to 180),
			// with positive values when the z-axis moves toward the y-axis.
			// values[2]:
			// Roll, rotation around Y axis (-90 to 90),
			// with positive values when the x-axis moves away from the z-axis.
			Text.setText(String.valueOf(event.values[0]) + ", " + String.valueOf(event.values[1]) + ", " + String.valueOf(event.values[2]));
			if (event.values[1] < 0) {
				if (event.values[1] * -1 < 30) {
					image.setImageResource(R.drawable.imgm);
				} else {
					image.setImageResource(R.drawable.img);
				}
			} else {
				if (event.values[1] < 30) {
					image.setImageResource(R.drawable.imgm);
				} else {
					image.setImageResource(R.drawable.img);
				}
			}
		}
	}
}
