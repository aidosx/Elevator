package com.ilya.busyElevator.desktop;

import com.ilya.busyElevator.ad.AdInterface;

public class DesktopAd implements AdInterface {

    @Override
    public void showOrLoadInterstital(boolean loadAd) {
        System.out.println("showOrLoadInterstital()");
    }

    @Override
    public void loadAd() {
        System.out.println("loadAd");
    }

    @Override
    public void showAd() {
        System.out.println("showAd");
    }
}
