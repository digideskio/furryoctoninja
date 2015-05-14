package pl.rspective.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class McKinseySurveyStorage implements SurveyStorage<String> {

    private static final String SURVEY_LOCAL_KEY = "mckinsey_survey";

    private SharedPreferences preferences;

    @Inject
    public McKinseySurveyStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void save(String survey) {
        preferences.edit().putString(SURVEY_LOCAL_KEY, survey).commit();
    }

    @Override
    public String load() {
        return preferences.getString(SURVEY_LOCAL_KEY, "{}");
    }

    @Override
    public void clearSurvey() {
        preferences.edit().putString(SURVEY_LOCAL_KEY, "{}").commit();
    }
}
