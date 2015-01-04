package marcus.application.test.testapplication1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import java.lang.String;



public class MainActivity extends ActionBarActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this,senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = 0;

        final TextView tv_View = (TextView)findViewById(R.id.tv_View);
        final EditText et_Text = (EditText)findViewById(R.id.et_Text);

        adjustViewSize();

        et_Text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(keyCode == KeyEvent.KEYCODE_ENTER)
                    {
                        tv_View.setText(tv_View.getText() + ", " + et_Text.getText());
                        et_Text.setText("");
                        adjustViewSize();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currTime = event.timestamp;

            if(currTime - lastUpdate > 100) {
                String sensorText = "x: " + String.valueOf(x) + "y: " + String.valueOf(y) + ",z: " + String.valueOf(z);
                final TextView tv_View = (TextView)findViewById(R.id.tv_View);
                tv_View.setText(sensorText);
                lastUpdate = currTime;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void adjustViewSize()
    {
        final TextView tv_View = (TextView)findViewById(R.id.tv_View);
        final EditText et_Text = (EditText)findViewById(R.id.et_Text);

        //float tv_X = tv_View.getX();
        float tv_Y = tv_View.getY();

        float tv_Height = tv_View.getHeight();
        //float tv_Width = tv_View.getWidth();

        //float new_X_Pos = tv_X + tv_Width;
        float new_Y_Pos = tv_Y + tv_Height;

        //et_Text.setX(new_X_Pos);
        et_Text.setY(new_Y_Pos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        senSensorManager.registerListener(this,senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
