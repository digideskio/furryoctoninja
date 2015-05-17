package pl.rspective.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

import pl.rspective.data.local.model.StorageType;

public class McKinseyStorageSurvey implements SurveyLocalStorage<String>{

    public static final String EMPTY_JSON = "{}";

    private SharedPreferences preferences;

    @Inject
    public McKinseyStorageSurvey(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void save(StorageType type, String survey) {
        preferences.edit().putString(type.getTag(), survey).commit();
    }

    @Override
    public String load(StorageType type) {
        return preferences.getString(type.getTag(), EMPTY_JSON);
    }

    @Override
    public void clear(StorageType type) {
        preferences.edit().putString(type.getTag(), EMPTY_JSON).commit();
    }
}
