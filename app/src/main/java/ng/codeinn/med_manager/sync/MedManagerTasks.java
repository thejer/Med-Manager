package ng.codeinn.med_manager.sync;

import android.content.Context;
import android.support.annotation.Nullable;

import ng.codeinn.med_manager.utilities.NotificationUtils;
import ng.codeinn.med_manager.utilities.PreferenceUtils;

/**
 * Created by Jer on 07/04/2018.
 */

public class MedManagerTasks {

    public static final String ACTION_INCREMENT_MEDICATION_TAKEN = "increment_medication_taken",
            ACTION_DISMISS_NOTIFICATION = "dismiss_notification",
            ACTION_MEDICATION_REMINDER = "medication_reminder";

    public static void executeTask(Context context, String action, @Nullable String medicationName){
        if (ACTION_INCREMENT_MEDICATION_TAKEN.equals(action)){
           incrementMedicationTaken(context);
        }else if (ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }else if (ACTION_MEDICATION_REMINDER.equals(action)){
            issueMedicationReminder(context, medicationName);
        }
    }

    private static void issueMedicationReminder(Context context, String medicationName){
        NotificationUtils.remindUserForMeds(context, medicationName);
    }

    private static void incrementMedicationTaken(Context context){
        PreferenceUtils.incrementMedicationTaken(context);
        NotificationUtils.clearAllNotifications(context);
    }
}
