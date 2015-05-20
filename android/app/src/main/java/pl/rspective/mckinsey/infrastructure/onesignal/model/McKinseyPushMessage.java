package pl.rspective.mckinsey.infrastructure.onesignal.model;

import com.google.gson.annotations.SerializedName;

public class McKinseyPushMessage {

    @SerializedName("a")
    private PushMessageMetaData metaData;

    @SerializedName("i")
    private String messageId;

    public PushMessageMetaData getMetaData() {
        return metaData;
    }

    public static class PushMessageMetaData {
        @SerializedName("eventType")
        private PushType pushType;

        public PushType getPushType() {
            return pushType;
        }
    }


}
