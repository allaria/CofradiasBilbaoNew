package com.cofradias.android;

import android.preference.PreferenceActivity;
import android.util.Log;

import com.cofradias.android.view.MyPreferencesAdminFragment;
import com.cofradias.android.view.MyPreferencesFragment;

import java.util.List;

/**
 * Created by alaria on 05/07/2016.
 */
public class MyPreferencesActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_preferences, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        Log.v("+++", fragmentName);
        if (fragmentName.equals("com.cofradias.android.view.MyPreferencesFragment")) {
            return MyPreferencesFragment.class.getName().equals(fragmentName);
        } else {
            return MyPreferencesAdminFragment.class.getName().equals(fragmentName);
        }
    }
}
