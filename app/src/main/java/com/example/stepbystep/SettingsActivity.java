package com.example.stepbystep;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsActivity extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        //add xml
        addPreferencesFromResource(R.xml.settings_view);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        onSharedPreferenceChanged(sharedPreferences, getString(R.string.dailyGoal_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.units_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.firstName_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.lastName_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.gender_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.date_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.height_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.weight_key));
    }

    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(sharedPreferences.getString(key, ""));

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
