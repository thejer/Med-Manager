package ng.codeinn.med_manager.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jer on 10/04/2018.
 */

public class MedManagerSyncUtils {
    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String MED_MANAGER_SYNC_TAG = "med_manager_sync";

    private static boolean sInitialized;

    synchronized public static void scheduleMedicationReminderSync (@NonNull final Context context){
        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job medManagerJob = firebaseJobDispatcher.newJobBuilder()
                .setService(MedManagerFirebaseJobService.class)
                .setTag(MED_MANAGER_SYNC_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(medManagerJob);
        sInitialized = true;

    }
}
