package com.example.jesus.sensores04;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Sensores04 extends AppCompatActivity implements SensorEventListener{
int contador;
boolean continuar = true;
double x =0;
double y=0;
double z=0;
double a=0;
double amax=0;
double campoTierraMax = SensorManager.MAGNETIC_FIELD_EARTH_MAX;
double campoTierraMin = SensorManager.MAGNETIC_FIELD_EARTH_MIN;
TextView camX;
TextView camY;
TextView camZ;
TextView campoMagneticoTotal;
TextView campoTerrestre;
TextView campoTierra;
TextView cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores04);

        camX = (TextView) findViewById(R.id.campoX);
        camY = (TextView) findViewById(R.id.campoY);
        camZ = (TextView) findViewById(R.id.campoZ);
        campoMagneticoTotal = (TextView) findViewById(R.id.campoMagneticoTotal);
        campoTerrestre = (TextView) findViewById(R.id.campoTerrestre);
        campoTierra = (TextView) findViewById(R.id.campoTierra);
        cont = (TextView) findViewById(R.id.contador);

        SensorManager SensorManager = (android.hardware.SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor campo = SensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        SensorManager.registerListener(this,campo,SensorManager.SENSOR_DELAY_NORMAL);

        new MiAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        continuar=true;
        new MiAsyncTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        continuar=false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        a = Math.sqrt(x*x+y*y+z*z);
        if (a > amax) amax = a;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    class MiAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                contador++;
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            camX.setText("" + x);
            camY.setText("" + y);
            camZ.setText("" + z);
            campoMagneticoTotal.setText("" + a);
            campoTerrestre.setText("" + amax);
            campoTierra.setText(""+campoTierraMin+"-"+campoTierraMax);
            //cont.setText(contador);
        }
    }
}
