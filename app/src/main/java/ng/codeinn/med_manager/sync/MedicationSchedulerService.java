package ng.codeinn.med_manager.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Jer on 17/04/2018.
 */

public class MedicationSchedulerService extends JobService {

    private AsyncTask mBackgroundTask;

    private static int mInterval;
    private static String mMedicationTag;


    public static void setInterval(int interval) {
        mInterval = interval;
    }

    public static void setMedicationTag(String medicationTag) {
        mMedicationTag = medicationTag;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {

        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                Context context = MedicationSchedulerService.this;

                MedManagerSyncUtils.scheduleMedicationReminderSync(context, mInterval, mMedicationTag);

                return null;
            }


            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
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
