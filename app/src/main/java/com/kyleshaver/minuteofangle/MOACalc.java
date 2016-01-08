package com.kyleshaver.minuteofangle;

import java.text.DecimalFormat;

/**
 * Created by Kyle on 12/28/2015.
 */

public class MOACalc {

    private MOAPreferenceManager prefs;

    public MOACalc(MOAPreferenceManager preferences) {
        prefs = preferences;
    }

    public double minuteOfAngle(double distanceToTarget, double poiOffset) {
        return ((poiOffset * prefs.poiOffsetScalar())/ prefs.shootersMOAScalar()) / ((distanceToTarget * prefs.distanceToTargetScalar())/ 100.0);
    }

    public double minuteOfAngle(int distanceToTarget, int poiOffset) {
        return minuteOfAngle((double)distanceToTarget, (double)poiOffset);
    }

    public String roundOff(double numberToFormat, int numberOfDecimals) {
        String pattern = "###,###";
        pattern += (numberOfDecimals > 0 ? "." : "");
        for (int i=0; i<numberOfDecimals; i++) pattern += "#";
        return new DecimalFormat(pattern).format(numberToFormat);
    }
}
