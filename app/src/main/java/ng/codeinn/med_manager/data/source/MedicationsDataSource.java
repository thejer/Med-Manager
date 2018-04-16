package ng.codeinn.med_manager.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ng.codeinn.med_manager.data.Medication;

/**
 * Created by Jer on 12/04/2018.
 */

public interface MedicationsDataSource {

    interface LoadMonthlyMedicationsCallBack {
        void onMonthlyMedicationsLoaded(List<Medication> medications);

        void onDataNotAvailable();

    }

    interface LoadAllMedicationsCallBack{
        void onMedicationsLoaded(List<Medication> medications);

        void onDataNotAvailable();
    }

    interface GetMedicationCallback {
        void onMedicationLoaded(Medication medication);

        void onDataNotAvailable();

    }

    void getAllMedications(@NonNull LoadAllMedicationsCallBack callBack);

    void getMonthlyMedications(@NonNull String month, @NonNull LoadMonthlyMedicationsCallBack callBack);

    void getMedication(@NonNull String medicationId, @NonNull GetMedicationCallback callback);

    void saveMedication(@NonNull Medication medication);

    void deleteMedication(@NonNull String medicationId);


}
