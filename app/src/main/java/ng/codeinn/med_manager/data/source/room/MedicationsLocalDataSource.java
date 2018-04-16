package ng.codeinn.med_manager.data.source.room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ng.codeinn.med_manager.data.Medication;
import ng.codeinn.med_manager.data.source.MedicationsDataSource;
import ng.codeinn.med_manager.util.AppExecutors;

import static android.content.ContentValues.TAG;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jer on 12/04/2018.
 */

public class MedicationsLocalDataSource implements MedicationsDataSource {

    private static MedicationsLocalDataSource INSTANCE;

    private MedicationsDao mMedicationsDao;

    private AppExecutors mAppExecutors;

    private MedicationsLocalDataSource (@NonNull AppExecutors appExecutors, @NonNull MedicationsDao medicationsDao){
        mMedicationsDao = medicationsDao;
        mAppExecutors = appExecutors;
    }

    public static MedicationsLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull MedicationsDao medicationsDao){
        if (INSTANCE == null){
            synchronized (MedicationsLocalDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new MedicationsLocalDataSource(appExecutors ,medicationsDao);

                }
            }
        }
        return INSTANCE;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllMedications(@NonNull final LoadAllMedicationsCallBack callBack) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                List<Medication> medications = mMedicationsDao.getAll();
//                if(medications.isEmpty()){
//                    callBack.onDataNotAvailable();
//                }else {
//                    callBack.onMedicationsLoaded(medications);
//                }
//                return null;
//            }
//        }.execute();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Medication> medications = mMedicationsDao.getAll();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (medications.isEmpty()){
                            callBack.onDataNotAvailable();
                            Log.i(TAG, "run: empty");
                        }else {
                            callBack.onMedicationsLoaded(medications);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);


    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getMonthlyMedications(@NonNull final String month, @NonNull final LoadMonthlyMedicationsCallBack callBack) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                List<Medication> medications = mMedicationsDao.getMonthMedications(month);
//                if (medications.isEmpty()){
//                    callBack.onDataNotAvailable();
//                }else {
//                    callBack.onMonthlyMedicationsLoaded(medications);
//                }
//                return null;
//            }
//        }.execute();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Medication> medications = mMedicationsDao.getMonthMedications(month);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (medications.isEmpty()){
                            callBack.onDataNotAvailable();
                            Log.i(TAG, "run: empty");
                        }else {
                            callBack.onMonthlyMedicationsLoaded(medications);
                        }
                    }
                });

            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void getMedication(@NonNull final String medicationId, @NonNull final GetMedicationCallback callback) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                if(medication != null){
//                    callback.onMedicationLoaded(medication);
//                }else {
//                    callback.onDataNotAvailable();
//                }
//                return null;
//            }
//        }.execute();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Medication medication = mMedicationsDao.getMedicationById(medicationId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(medication != null){
                            callback.onMedicationLoaded(medication);
                        }else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);


    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveMedication(@NonNull final Medication medication) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mMedicationsDao.insertMedication(medication);
//                return null;
//            }
//        }.execute();

        checkNotNull(medication);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mMedicationsDao.insertMedication(medication);
                Log.i(TAG, "run: saved" + medication.getMMedicationName());
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);

    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void deleteMedication(@NonNull final String medicationId) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mMedicationsDao.deleteTaskById(medicationId);
//                return null;
//            }
//        }.execute();

        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mMedicationsDao.deleteTaskById(medicationId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);


    }
}
