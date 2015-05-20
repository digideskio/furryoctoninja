package pl.rspective.data.local;

public interface LocalPreferences {

    void setSurveyLoaded(boolean firstLogin);

    boolean isSurveyLoaded();

    void setAppEventStatus(int status);

    int getAppEventStatus();
}
