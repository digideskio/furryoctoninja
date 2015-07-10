package pl.rspective.survey.infrastructure.onesignal;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onesignal.GcmBroadcastReceiver;
import com.squareup.otto.Bus;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import pl.rspective.data.local.LocalPreferences;
import pl.rspective.data.local.SurveyLocalStorage;
import pl.rspective.survey.R;
import pl.rspective.survey.architecture.bus.events.SurveyChangedEvent;
import pl.rspective.survey.architecture.bus.events.SurveyRestartEvent;
import pl.rspective.survey.architecture.bus.events.SurveyResultsUpdateEvent;
import pl.rspective.survey.dagger.Injector;
import pl.rspective.survey.data.model.AppEventStatus;
import pl.rspective.survey.infrastructure.onesignal.model.SurveyPushMessage;

public class OneSignalReceiver extends GcmBroadcastReceiver {

    private static final String TAG = "McKinseyPushMessageReceiver";

    public static class NotificationIdGenerator {
        private static final AtomicInteger c = new AtomicInteger(0);

        public static int generateNotificationId() {
            return c.incrementAndGet();
        }
    }

    @Inject
    LocalPreferences localPreferences;

    @Inject
    SurveyLocalStorage<String> surveyLocalStorage;

    @Inject
    Bus bus;

    private Gson gson;

    public OneSignalReceiver() {
        Injector.inject(this);
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), SurveyGcmIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

        if (intent != null && intent.getExtras() != null && intent.getExtras().getString("custom") != null) {
            SurveyPushMessage message = extractOnesignalMessage(intent);

            if (message == null || message.getMetaData() == null || message.getMetaData().getPushType() == null) {
                Log.d(TAG, "There was a problem to extract onesignal message from push payload");
                return;
            }

            switch (message.getMetaData().getPushType()) {
                case SURVEY_RESTART:
                    generateNotification(context, context.getString(R.string.app_name), context.getString(R.string.push_message_new_survey_subtitle));
                    localPreferences.setAppEventStatus(AppEventStatus.SURVEY_RESTART_PUSH_MESSAGE.ordinal());
                    Log.d(TAG, "Survey was restarted");
                    bus.post(new SurveyRestartEvent());
                    break;
                case SURVEY_CHANGED:
                    localPreferences.setAppEventStatus(AppEventStatus.SURVEY_CHANGED_PUSH_MESSAGE.ordinal());
                    Log.d(TAG, "Survey was changed");
                    bus.post(new SurveyChangedEvent());
                    break;
                case SURVEY_RESULTS_UPDATED:
                    Log.d(TAG, "Refresh survey results");
                    bus.post(new SurveyResultsUpdateEvent());
                    break;
            }

        }

        abortBroadcast();

    }

    private SurveyPushMessage extractOnesignalMessage(Intent intent) {
        SurveyPushMessage notification = gson.fromJson(intent.getExtras().getString("custom"), SurveyPushMessage.class);

        Log.d(TAG, "Onesingal notification was received");
        if(notification == null) {
            return null;
        }
        return notification;
    }

    private void generateNotification(Context context, String title, String subtitle) {
        NotificationManager mNotifM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setLargeIcon(largeIcon)
                .setContentText(subtitle);

        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);

        mNotifM.notify(NotificationIdGenerator.generateNotificationId(), mBuilder.build());
    }
}