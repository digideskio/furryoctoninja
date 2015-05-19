package pl.rspective.mckinsey.infrastructure.onesignal.model;

import com.google.gson.annotations.SerializedName;

/*
{"a":{"eventType":"SURVEY-CHANGED"},"i":"da173b2c-fd95-11e4-95e7-4ffb87386879"}
 */
public class McKinseyPushMessage {

    @SerializedName("a")
    private PushMessageMetaData metaData;

    @SerializedName("i")
    private String messageId;

    public PushMessageMetaData getMetaData() {
        return metaData;
    }

    private class PushMessageMetaData {
        private String eventType;

        public String getEventType() {
            return eventType;
        }
    }


}