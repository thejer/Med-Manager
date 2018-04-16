package ng.codeinn.med_manager.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.medications.MedicationsActivity;
import ng.codeinn.med_manager.sync.MedManagerIntentService;
import ng.codeinn.med_manager.sync.MedManagerTasks;

/**
 * Created by Jer on 07/04/2018.
 */


public class NotificationUtils {

    private static final int MEDICATION_REMINDER_PENDING_INTENT_ID = 346;

    private static final String MEDICATION_MANAGER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";

    private static final int MEDICATION_MANAGER_NOTIFICATION_ID = 1138;

    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 33;

    private static final int ACTION_TAKE_MEDS_PENDING_INTENT_ID = 24;
    public static void remindUserForMeds(Context context, String medicationName){

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    MEDICATION_MANAGER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);

        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, MEDICATION_MANAGER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_local_hospital_small)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.medication_manager_notification_title))
                .setContentText(context.getString(R.string.medication_manager_notification_body, medicationName))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                context.getString(R.string.medication_manager_notification_body, medicationName)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(takeMedsAction(context))
                .addAction(ignoreMedReminderAction(context))
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(MEDICATION_MANAGER_NOTIFICATION_ID, notificationBuilder.build());



    }

    private static NotificationCompat.Action ignoreMedReminderAction(Context context){
        Intent ignoreReminderIntent = new Intent(context, MedManagerIntentService.class);
        ignoreReminderIntent.setAction(MedManagerTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action(R.drawable.ic_cancel_black_24dp,
                "I'll pass.",
                ignoreReminderPendingIntent);
    }

    private static NotificationCompat.Action takeMedsAction(Context context){
        Intent takeMedsIntent = new Intent(context, MedManagerIntentService.class);
        takeMedsIntent.setAction(MedManagerTasks.ACTION_INCREMENT_MEDICATION_TAKEN);

        PendingIntent takeMedsPendingIntent = PendingIntent.getService(context,
                ACTION_TAKE_MEDS_PENDING_INTENT_ID,
                takeMedsIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Action(R.drawable.ic_local_hospital_black_24dp,
                "I took the pill",
                takeMedsPendingIntent);
    }

    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static PendingIntent contentIntent(Context context){

        Intent startActivityIntent = new Intent(context, MedicationsActivity.class);

        return PendingIntent.getActivity(
                context,
                MEDICATION_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
                );
    }

    private static Bitmap largeIcon(Context context){
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, R.drawable.ic_local_hospital_black_24dp);

    }

}
