package br.com.tvglobo.proximity;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class AsyncTaskSound extends AsyncTask<Long, Void, Void> {
    @Override
    protected Void doInBackground(Long... params) {
        //while(true)
        //{
            try {
                //MediaPlayer mp = MediaPlayer.create(this, R.raw.beep_red);
                //mp.start();
                //playSound();
                //Thread.sleep(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //}
        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    void playSound(){
        int duration = 1; // seconds
        int sampleRate = 8000;
        int numSamples = duration * sampleRate;
        double sample[] = new double[numSamples];
        double freqOfTone = 500; // hz

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