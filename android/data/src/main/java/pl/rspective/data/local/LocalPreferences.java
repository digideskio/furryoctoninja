package pl.rspective.data.local;

public interface LocalPreferences {

    void setFirstUserLogin(boolean firstLogin);

    boolean isUserFirstLogin();

    void setAppEventStatus(int status);

    int getAppEventStatus();
}
