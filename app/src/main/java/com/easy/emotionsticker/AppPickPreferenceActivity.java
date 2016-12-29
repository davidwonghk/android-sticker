package com.easy.emotionsticker;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.view.View;

import com.easy.emotionsticker.helper.MyAlertDialog;
import com.easy.emotionsticker.helper.SettingsHelper;
import com.easy.emotionsticker.pick.AppPick;
import com.easy.emotionsticker.pick.AppRepository;

import java.util.List;

/**
 * Created by david on 31/08/2016.
 */
public class AppPickPreferenceActivity extends PreferenceActivity {

	public static final String BUNDLE_NAME = "PICK_APPLICATIONS";
	private MyPreferenceFragment fragment;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();

		this.fragment = new MyPreferenceFragment();
		fragment.setArguments(extras);
		getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
	}


	public void save(View view) {
		if (fragment.save()) {
			onBackPressed();
		}
	}



	public static class MyPreferenceFragment extends PreferenceFragment
	{
		private List<AppPick> applications;
		private PreferenceCategory targetCategory;
		private SharedPreferences settings;


		@Override
		public void onCreate(final Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);

			this.settings = SettingsHelper.getPickSharedPreferences(super.getActivity());
			this.applications = AppRepository.fromStringArray(getArguments().getStringArray(BUNDLE_NAME));

			// Get the Preference Category which we want to add the ListPreference to
			this.targetCategory = (PreferenceCategory) findPreference("TARGET_CATEGORY");


		}


		@Override
		public void onResume() {
			super.onResume();

			if (targetCategory.getPreferenceCount() > 0) {
				targetCategory.removeAll();
			}

			for(AppPick a : applications) {
				CheckBoxPreference c = new CheckBoxPreference(getActivity());
				c.setIcon(a.getIcon());
				c.setKey(a.getAppName());
				c.setTitle(a.getAppName());
				c.setPersistent(true);

				boolean active = settings.getBoolean(a.getAppName(), false);
				c.setChecked(active);

				targetCategory.addPreference(c);
			}
		}


		public boolean save() {
			final int numApplications = targetCategory.getPreferenceCount();

			//check if any application is checked
			int count = 0;
			for (int i=0; i<numApplications; ++i) {
				CheckBoxPreference p = (CheckBoxPreference) targetCategory.getPreference(i);
				if (p.isChecked()) count++;
			}

			if (count == 0) {
				new MyAlertDialog(getActivity(), R.string.alert_title, R.string.alert_setting).show();
				return false;
			}

			//save the checked applications to share preferences.
			for (int i=0; i<numApplications; ++i) {
				CheckBoxPreference p = (CheckBoxPreference)targetCategory.getPreference(i);
				settings.edit().putBoolean(p.getKey(), p.isChecked()).commit();
			}

			return true;
		}

	}
}
