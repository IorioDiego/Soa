package com.example.covid;

import android.app.IntentService;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.Nullable;

public class Timer extends IntentService {


    public Timer(){
        super("TIMER");
    }

    Intent i = new Intent( "com.example.intentservice.intent.action.TIMER_ACT");

    CountDownTimer timerConection = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Servicio", "Comienza el timer...");
        timerConection = new CountDownTimer(15000, 1000) {// 840000 pra 14 minutos

            @Override
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                //se envia el tiempo finalizado
                i.putExtra("Fin", "Tiempo terminado!");
                sendBroadcast(i);
            }
        }.start();



    }

    @Override
    public void onDestroy() {
       // timerConection.cancel();
        Log.i("Servicio", "Timer cancelado");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }



    public Timer(String name) {
        super(name);
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        return;
    }
}
