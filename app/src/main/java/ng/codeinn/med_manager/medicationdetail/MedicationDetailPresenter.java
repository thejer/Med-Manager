package ng.codeinn.med_manager.medicationdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import ng.codeinn.med_manager.data.Medication;
import ng.codeinn.med_manager.data.source.MedicationsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jer on 15/04/2018.
 */

public class MedicationDetailPresenter implements MedicationDetailContract.IPresenter{

    private final MedicationsDataSource mMedicationsDataSource;

    private final MedicationDetailContract.IView mMedicationDetailView;

    @Nullable
    private String mMedicationId;

    public MedicationDetailPresenter(@Nullable String medicationId,
                                     @NonNull MedicationsDataSource medicationsDataSource,
                                     @NonNull MedicationDetailContract.IView medicationDetailView){

        mMedicationId = medicationId;
        mMedicationsDataSource = checkNotNull(medicationsDataSource);
        mMedicationDetailView = checkNotNull(medicationDetailView);

        mMedicationDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openMedication();
    }

    private void openMedication(){
        if(Strings.isNullOrEmpty(mMedicationId)){
            return;
        }

        mMedicationsDataSource.getMedication(mMedicationId, new MedicationsDataSource.GetMedicationCallback() {
            @Override
            public void onMedicationLoaded(Medication medication) {
                if (!mMedicationDetailView.isActive()) {
                    return;
                }
                if (null == medication){
                }else {
                    mMedicationDetailView.setMedicationDetails(medication);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mMedicationDetailView.isActive()){
                    return;
                }
            }
        });
    }

    @Override
    public void editMedication() {
        if(Strings.isNullOrEmpty(mMedicationId)){
            return;
        }
        mMedicationDetailView.showEditMedication(mMedicationId);

    }

    @Override
    public void deleteMedication() {
        if(Strings.isNullOrEmpty(mMedicationId)){
            return;
        }
        mMedicationsDataSource.deleteMedication(mMedicationId);
        mMedicationDetailView.showMedicationDeleted();
    }

}
