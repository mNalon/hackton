package br.com.tvglobo.proximity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tvglobo.proximity.Classes.Wifi;

public class MainActivity extends Activity {

    WifiManager mainWifi;
    WifiReceiver receiverWifi;

    ListView lv;
    Context context;

    ArrayList wifiList;

    StringBuilder sb = new StringBuilder();

    private final Handler handler = new Handler();
    ArrayList<Wifi> connections=new ArrayList<Wifi>();

    public long interval = 1;

    Thread thread = null;

    AsyncTaskSound asyncTaskSound;
    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Wifi list
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        if(mainWifi.isWifiEnabled()==false)
        {
            mainWifi.setWifiEnabled(true);
        }
        doInback();

        context=this;

        lv=(ListView) findViewById(R.id.listView);

        //Layout

    }

    public void doInback()
    {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                receiverWifi = new WifiReceiver();
                registerReceiver(receiverWifi, new IntentFilter(
                        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                mainWifi.startScan();
                doInback();
            }
        }, 500);

        ArrayList<Wifi> a = connections;
        for(Wifi wifi : connections)
        {
            if(wifi.SSID.startsWith("DEPED_TESTE"))
            {
                if(mp != null)
                    mp.stop();
                //thread.interrupt();
                if(wifi.level > -70 && wifi.level <= -60)
                    mp = MediaPlayer.create(context, R.raw.beep_greenlight);
                    //interval = 5000;
                    //playSound(5);
                    //thread.start(5);
                else if(wifi.level > -60 && wifi.level <= -50)
                    mp = MediaPlayer.create(context, R.raw.beep_green);
                    //interval = 4000;
                    //playSound(4);
                    //thread.start(4);
                else if(wifi.level > -50 && wifi.level <= -40)
                    mp = MediaPlayer.create(context, R.raw.beep_yellow);
                    //interval = 3000;
                    //playSound(3);
                    //thread.start(3);
                else if(wifi.level > -40 && wifi.level <= -30)
                    mp = MediaPlayer.create(context, R.raw.beep_orange);
                    //interval = 2000;
                    //playSound(2);
                    //thread.start(2);
                else if(wifi.level > -30)
                    mp = MediaPlayer.create(context, R.raw.beep_red);
                //interval = 1000;
                //playSound(1);
                //thread.start(1);
                mp.start();
//                        if(asyncTaskSound != null)
//                            asyncTaskSound.cancel(true);
//                        asyncTaskSound = new AsyncTaskSound();
//                        asyncTaskSound.execute(interval);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Refresh");
        return super.onCreateOptionsMenu(menu);}
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mainWifi.startScan();

        return super.onMenuItemSelected(featureId, item);}


    @Override
    protected void onPause()
    {
        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class WifiReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent)
        {
            connections=new ArrayList<Wifi>();
            ArrayList<Float> Signal_Strenth= new ArrayList<Float>();

            sb = new StringBuilder();
            List<ScanResult> wifiList;
            wifiList = mainWifi.getScanResults();
            for(int i = 0; i < wifiList.size(); i++)
            {
                if(wifiList.get(i).SSID.startsWith("DEPED_TESTE")) {
                    connections.add(new Wifi(wifiList.get(i).SSID, wifiList.get(i).level));
                    int level = wifiList.get(i).level;

                }
            }

            WifiAdapter wifiAdapter = new WifiAdapter(context, connections);
            wifiAdapter.notifyDataSetChanged();
            lv.setAdapter(wifiAdapter);

            /*ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);*/


            /*try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }
}