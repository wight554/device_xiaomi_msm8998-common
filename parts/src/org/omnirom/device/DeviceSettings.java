/*
* Copyright (C) 2016 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.omnirom.device;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.TwoStatePreference;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.util.Log;

public class DeviceSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String KEY_CATEGORY_DISPLAY = "display";
    public static final String KEY_TAPTOWAKE_SWITCH = "taptowake";
    public static final String KEY_VIBSTRENGTH = "vib_strength";

    final String KEY_DEVICE_DOZE = "device_doze";
    final String KEY_DEVICE_DOZE_PACKAGE_NAME = "org.lineageos.settings.doze";

    private TwoStatePreference mTapToWakeSwitch;
    private VibratorStrengthPreference mVibratorStrength;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main, rootKey);

        mVibratorStrength = (VibratorStrengthPreference) findPreference(KEY_VIBSTRENGTH);
        if (mVibratorStrength != null) {
            mVibratorStrength.setEnabled(VibratorStrengthPreference.isSupported());
        }

        mTapToWakeSwitch = (TwoStatePreference) findPreference(KEY_TAPTOWAKE_SWITCH);
        mTapToWakeSwitch.setEnabled(TapToWakeSwitch.isSupported());
        mTapToWakeSwitch.setChecked(TapToWakeSwitch.isCurrentlyEnabled(this.getContext()));
        mTapToWakeSwitch.setOnPreferenceChangeListener(new TapToWakeSwitch());

        if (!isAppInstalled(KEY_DEVICE_DOZE_PACKAGE_NAME)) {
            PreferenceCategory displayCategory = (PreferenceCategory) findPreference(KEY_CATEGORY_DISPLAY);
            displayCategory.removePreference(findPreference(KEY_DEVICE_DOZE));
        }

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getContext().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
