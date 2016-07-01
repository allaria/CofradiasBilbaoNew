package com.cofradias.android.model;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.cofradias.android.R;

/**
 * Created by alaria on 28/06/2016.
 */
public class MyPreferences extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}