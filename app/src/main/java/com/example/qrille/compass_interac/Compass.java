package com.example.qrille.compass_interac;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Compass extends Activity implements SensorEventListener {

    private RelativeLayout view;

    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;


    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_compass2);
        view = (RelativeLayout) findViewById(R.id.layout);

        image = (ImageView) findViewById(R.id.imageViewCompass);
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(50);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);




        int deg = ((int)degree);

        view.setBackgroundColor(Color.rgb(generateRed(deg), generateGreen(deg), 0));

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
        //

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    private int generateRed(int n){
        if(n <= 180){
            return (int) (n*(1.41666));
        }else{
            int diff = n - 180;
            return (int) ((180-diff)*(1.41666));
        }

    }

    private int generateGreen(int n){
        if (n <= 180){
            int diffG = 180 - n;
            return (int) (diffG*(1.41666));
        } else {
            int over180 = 360- n;
            int diffG2 = 180 - over180;
            return (int) (diffG2*(1.41666));
            //deee
        }
    }
}