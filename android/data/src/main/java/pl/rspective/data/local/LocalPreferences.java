package pl.rspective.data.local;

public interface LocalPreferences {

    void setFirstUserLogin();

    boolean isUserFirstLogin();

    void setAppEventStatus(int status);

    int getAppEventStatus();
}
