package br.com.tvglobo.proximity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
        }, 1000);

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
                    if(thread == null) {
                        thread = new Thread(new Runnable() {
                            public void run() {
                                handler.post(new Runnable() {

                                    public void run() {
                                        while (true) {
                                            playSound();
                                            try
                                            {
                                                Thread.sleep(interval);
                                            }
                                            catch (InterruptedException e)
                                            {
                                                Thread.currentThread().interrupt(); // restore interrupted status
                                            }
                                        }

                                    }
                                });
                            }
                        });
                        thread.start();
                    }
                    connections.add(new Wifi(wifiList.get(i).SSID, wifiList.get(i).level));
                    int level = wifiList.get(i).level;
                    if(wifiList.get(i).SSID.startsWith("DEPED_TESTE"))
                    {
                        //thread.interrupt();
                        if(level > -80 && level <= -70)
                            interval = 5000;
                            //playSound(5);
                            //thread.start(5);
                        else if(level > -70 && level <= -60)
                            interval = 4000;
                            //playSound(4);
                            //thread.start(4);
                        else if(level > -60 && level <= -50)
                            interval = 3000;
                            //playSound(3);
                            //thread.start(3);
                        else if(level > -50 && level <= -40)
                            interval = 2000;
                            //playSound(2);
                            //thread.start(2);
                        else if(level > -40)
                            interval = 1000;
                            //playSound(1);
                            //thread.start(1);
                    }

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


    void playSound(){
        int duration = 1; // seconds
        int sampleRate = 1000;
        int numSamples = duration * sampleRate;
        double sample[] = new double[numSamples];
        double freqOfTone = 440; // hz

        byte generatedSnd[] = new byte[2 * numSamples];
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
                sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

}
