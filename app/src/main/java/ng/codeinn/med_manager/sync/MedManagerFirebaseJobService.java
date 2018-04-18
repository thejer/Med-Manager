package ng.codeinn.med_manager.sync;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Jer on 06/04/2018.
 */

public class MedManagerFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;

    private static String medicationMame;

    public static void setMedicationMame(String medicationMame) {
        MedManagerFirebaseJobService.medicationMame = medicationMame;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {

        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = MedManagerFirebaseJobService.this;
                MedManagerTasks.executeTask(context, MedManagerTasks.ACTION_MEDICATION_REMINDER, medicationMame);
                Log.i(" ", "doInBackground: notified " + medicationMame );
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job ,false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mBackgroundTask != null)mBackgroundTask.cancel(true);
        return true;
    }
}