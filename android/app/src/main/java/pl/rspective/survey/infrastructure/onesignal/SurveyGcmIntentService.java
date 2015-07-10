package pl.rspective.survey.infrastructure.onesignal;

import android.app.IntentService;
import android.content.Intent;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SurveyGcmIntentService extends IntentService {

   public SurveyGcmIntentService() {
      super("McKinseyGcmIntentService");
   }

   @Override
   protected void onHandleIntent(Intent intent) {
      // Release the wake lock provided by the WakefulBroadcastReceiver.
       OneSignalReceiver.completeWakefulIntent(intent);
   }
}