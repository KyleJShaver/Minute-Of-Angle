package com.kyleshaver.minuteofangle;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

/**
 * Created by Kyle on 12/28/2015.
 */
public class MOAPreferenceManager {

    private static final String PREFS_NAME = "PrefsFile";
    private SharedPreferences settingsFile;
    private String[] distanceToTargetPossibleUnits = new String[]{"Feet", "Yards", "Meters"};
    private String[] poiOffsetPossibleUnits = new String[]{"Inches", "Centimeters", "Milimeters", "Feet", "Yards", "Meters"};

    public MOAPreferenceManager(SharedPreferences preferences) {
        settingsFile = preferences;
    }

    public String distanceToTargetUnit() {
        return settingsFile.getString("distanceToTargetUnit", "Yards");
    }

    public String distanceToTargetUnit(String newValue) {
        return putString("distanceToTargetUnit", newValue);
    }

    public double distanceToTargetScalar() {
        switch(distanceToTargetUnit()) {
            case "Feet":
                return 1.0/3.0;
            case "Meters":
                return 1.0936133;
            default:
                return 1.0;
        }
    }

    public String poiOffsetUnit() {
        return settingsFile.getString("poiOffsetUnit", "Inches");
    }

    public String poiOffsetUnit(String newValue) {
        return putString("poiOffsetUnit", newValue);
    }

    public double poiOffsetScalar() {
        switch(poiOffsetUnit()) {
            case "Centimeters":
                return 0.393701;
            case "Milimeters":
                return 0.0393701;
            case "Feet":
                return 12.0;
            case "Yards":
                return 36.0;
            case "Meters":
                return 36.0*1.0936133;
            default:
                return 1.0;
        }
    }

    public boolean shootersMOAActive() {
        return settingsFile.getBoolean("shootersMOAActive", false);
    }

    public boolean shootersMOAActive(boolean newValue) {
        return putBoolean("shootersMOAActive", newValue);
    }

    public double shootersMOAScalar() {
        return (shootersMOAActive() ? 1.0 : 1.047);
    }

    private String putString(String key, String value) {
        SharedPreferences.Editor editor = settingsFile.edit();
        editor.putString(key, value);
        editor.commit();
        return value;
    }
    private boolean putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = settingsFile.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return value;
    }

}
