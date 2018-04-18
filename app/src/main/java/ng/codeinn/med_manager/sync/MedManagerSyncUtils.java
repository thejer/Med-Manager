package ng.codeinn.med_manager.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import ng.codeinn.med_manager.data.Medication;
import ng.codeinn.med_manager.utilities.MedicationDateUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Jer on 10/04/2018.
 */

public class MedManagerSyncUtils {



    private static boolean sInitialized;

    synchronized public static void scheduleMedicationReminderSync (@NonNull final Context context, int interval, String medicationTag){
        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Log.i(TAG, "scheduleMedicationReminderSync: started " + medicationTag);

        Log.i(TAG, "scheduleMedicationReminderSync: " + interval);

        int reminderIntervalSeconds = (int) (TimeUnit.HOURS.toSeconds(interval));
        int flexTimeSeconds = (int) (TimeUnit.MINUTES.toSeconds(15));

        MedManagerFirebaseJobService.setMedicationMame(medicationTag);

        Job medManagerJob = firebaseJobDispatcher.newJobBuilder()
                .setService(MedManagerFirebaseJobService.class)
                .setTag(medicationTag)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
//                .setTrigger(Trigger.NOW)
                .setTrigger(Trigger.executionWindow(
                        reminderIntervalSeconds,
                        reminderIntervalSeconds + flexTimeSeconds ))
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(medManagerJob);
        sInitialized = true;

    }

    synchronized public static void scheduleMedicationScheduler(@NonNull final Context context, String startDate,
                                                                String medicationName, int interval){
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        long setTimeMillis = 0;
        try {
            setTimeMillis = MedicationDateUtils.normalDateToMillis(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        MedicationSchedulerService.setInterval(interval);
        MedicationSchedulerService.setMedicationTag(medicationName);


        long timeToSchedule = Math.round(setTimeMillis - System.currentTimeMillis());

        int jobTime;

        if (timeToSchedule > 0 ){
            jobTime =  (int) TimeUnit.MILLISECONDS.toSeconds(timeToSchedule);

        }else{
            jobTime =  15;
        }


        Log.i(TAG, "scheduleMedicationScheduler: time used" + jobTime);




        Job medicationJobSchedulerJob = firebaseJobDispatcher.newJobBuilder()
                .setService(MedicationSchedulerService.class)
                .setTag(medicationName + "Schedule")
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(jobTime, jobTime + (jobTime / 3)))
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(medicationJobSchedulerJob);

    }
}
