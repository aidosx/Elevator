package com.ilya.busyElevator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ilya.busyElevator.ad.AdInterface;
import com.ilya.busyElevator.util.Constants;

public class AndroidLauncher extends AndroidApplication implements AdInterface {
    private static final String TAG = "Android Launcher";

    private final int SHOW_AD = 0;
    private final int LOAD_AD = 1;

    private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-5776177024730924/8771574690";
    private InterstitialAd interstitialAd;
    private AdRequest interstitialRequest;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_AD:
                    showAd();
                    break;
                case LOAD_AD:
                    loadAd();
                    break;
            }
        }
    };
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new ElevatorMain(this), config);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Constants.adReady = true;
            }
            @Override
            public void onAdClosed() {
                Constants.adReady = false;
            }
        });
        interstitialRequest = new AdRequest.Builder().build();
	}

    @Override
     public void showOrLoadInterstital(boolean loadAd) {
           handler.sendEmptyMessage(loadAd ? LOAD_AD : SHOW_AD);
    }

    @Override
    public void showAd(){
            interstitialAd.show();

    }

    @Override
    public void loadAd(){
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    interstitialAd.loadAd(interstitialRequest);
                    }
                }
            );
        } catch (Exception e) {
        }
    }

}
