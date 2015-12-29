package br.com.tvglobo.proximity.Classes;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import br.com.tvglobo.proximity.R;

/**
 * Created by nalon on 12/29/15.
 */
public class SoundPoolPlayer {
    private SoundPool mShortPlayer = null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);


        mSounds.put(R.raw.sound1, this.mShortPlayer.load(pContext, R.raw.sound1, 1));
        mSounds.put(R.raw.sound2, this.mShortPlayer.load(pContext, R.raw.sound2, 1));
        mSounds.put(R.raw.sound3, this.mShortPlayer.load(pContext, R.raw.sound3, 1));
        mSounds.put(R.raw.sound4, this.mShortPlayer.load(pContext, R.raw.sound4, 1));
        mSounds.put(R.raw.sound5, this.mShortPlayer.load(pContext, R.raw.sound5, 1));
    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}