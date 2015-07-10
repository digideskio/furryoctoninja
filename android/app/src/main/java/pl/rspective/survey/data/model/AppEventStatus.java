package pl.rspective.survey.data.model;

public enum AppEventStatus {

    NO_EVENTS,
    SURVEY_CHANGED_PUSH_MESSAGE,
    SURVEY_RESTART_PUSH_MESSAGE;

    public static AppEventStatus valueOf(int index) {
        for(AppEventStatus status : values()) {
            if(status.ordinal() == index) {
                return status;
            }
        }

        throw new IllegalArgumentException("Not recognised app event status");
    }

}
