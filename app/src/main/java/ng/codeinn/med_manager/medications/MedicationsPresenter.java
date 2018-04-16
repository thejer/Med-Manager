package ng.codeinn.med_manager.medications;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ng.codeinn.med_manager.addeditmedication.AddEditMedicationActivity;
import ng.codeinn.med_manager.data.Medication;
import ng.codeinn.med_manager.data.source.MedicationsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jer on 14/04/2018.
 */

public class MedicationsPresenter implements MedicationsContract.IPresenter {

    private final MedicationsDataSource mMedicationsDataSource;

    private final MedicationsContract.IView mMedicationsView;

    private MedicationsFilterType mCurrentFiltering = MedicationsFilterType.ALL_MEDICATIONS;

    public MedicationsPresenter(@NonNull MedicationsDataSource medicationsDataSource,
                                MedicationsContract.IView medicationsView){
        mMedicationsDataSource = checkNotNull(medicationsDataSource, "medicationsDataSource cannot be null");
        mMedicationsView = checkNotNull(medicationsView, "medicationsView cannot be null");

        mMedicationsView.setPresenter(this);
    }



    @Override
    public void start() {
        loadMedications();
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a medication was successfully added, show snackbar

        if (AddEditMedicationActivity.REQUEST_ADD_MEDICATION == requestCode && Activity.RESULT_OK == resultCode) {
            mMedicationsView.showSuccessfullySavedMessage();
        }
    }


    @Override
    public void loadMedications() {

        loadMedications(true);
    }

    private void loadMedications(final boolean showLoadingUi){
        if (showLoadingUi) {
            mMedicationsView.setLoadingIndicator(true);
        }
        @Nullable String month;
        setFilterLabel();

        switch (mCurrentFiltering){
            case JANUARY_MEDICATIONS:
                month = "Jan";
                break;
            case FEBRUARY_MEDICATIONS:
                month = "Feb";
                break;
            case MARCH_MEDICATIONS:
                month = "Mar";
                break;
            case APRIL_MEDICATIONS:
                month = "Apr";
                break;
            case MAY_MEDICATIONS:
                month = "May";
                break;
            case JUNE_MEDICATIONS:
                month = "Jun";
                break;
            case JULY_MEDICATIONS:
                month = "Jul";
                break;
            case AUGUST_MEDICATIONS:
                month = "Aug";
                break;
            case SEPTEMBER_MEDICATIONS:
                month = "Sep";
                break;
            case OCTOBER_MEDICATIONS:
                month = "Oct";
                break;
            case NOVEMBER_MEDICATIONS:
                month = "Nov";
                break;
            case DECEMBER_MEDICATIONS:
                month = "Dec";
                break;
            default:
                month = "";
                break;
        }

        if(!month.equals("")) {
            mMedicationsDataSource.getMonthlyMedications(month, new MedicationsDataSource.LoadMonthlyMedicationsCallBack() {
                @Override
                public void onMonthlyMedicationsLoaded(List<Medication> medications) {
                    if(!mMedicationsView.isActive()){
                        return;
                    }
                    if (showLoadingUi){
                        mMedicationsView.setLoadingIndicator(false);
                    }
                    processMedications(medications);
                }

                @Override
                public void onDataNotAvailable() {

                    // TODO: 15/04/2018 fix the empty medications view
                    if(!mMedicationsView.isActive()){
                        return;
                    }
                    if (showLoadingUi){
                        mMedicationsView.setLoadingIndicator(false);
                    }
                    processEmptyMedications();
                    mMedicationsView.showLoadingMedicationsError();
                }
            });
        }else if(month.equals("")){
            mMedicationsDataSource.getAllMedications(new MedicationsDataSource.LoadAllMedicationsCallBack() {
                @Override
                public void onMedicationsLoaded(List<Medication> medications) {
                    if(!mMedicationsView.isActive()){
                        return;
                    }
                    if (showLoadingUi){
                        mMedicationsView.setLoadingIndicator(false);
                    }
                    processMedications(medications);
                }

                @Override
                public void onDataNotAvailable() {

                    if(!mMedicationsView.isActive()){
                        return;
                    }
                    if (showLoadingUi){
                        mMedicationsView.setLoadingIndicator(false);
                    }
                    processEmptyMedications();
                    mMedicationsView.showLoadingMedicationsError();
                }
            });
        }
    }

    private void setFilterLabel() {
        switch (mCurrentFiltering) {
            case JANUARY_MEDICATIONS:
                mMedicationsView.setJanFilterLabel();
                break;
            case FEBRUARY_MEDICATIONS:
                mMedicationsView.setFebFilterLabel();
                break;
            case MARCH_MEDICATIONS:
                mMedicationsView.setMarFilterLabel();
                break;
            case APRIL_MEDICATIONS:
                mMedicationsView.setAprFilterLabel();
                break;
            case MAY_MEDICATIONS:
                mMedicationsView.setMayFilterLabel();
                break;
            case JUNE_MEDICATIONS:
                mMedicationsView.setJunFilterLabel();
                break;
            case JULY_MEDICATIONS:
                mMedicationsView.setJulFilterLabel();
                break;
            case AUGUST_MEDICATIONS:
                mMedicationsView.setAugFilterLabel();
                break;
            case SEPTEMBER_MEDICATIONS:
                mMedicationsView.setSepFilterLabel();
                break;
            case OCTOBER_MEDICATIONS:
                mMedicationsView.setOctFilterLabel();
                break;
            case NOVEMBER_MEDICATIONS:
                mMedicationsView.setNovFilterLabel();
                break;
            case DECEMBER_MEDICATIONS:
                mMedicationsView.setDecFilterLabel();
                break;
            default:
                mMedicationsView.setAllFilterLabel();
                break;
        }
    }

    private void processMedications(List<Medication> medications){
        if(medications.isEmpty()){
            processEmptyMedications();
        }else {
            mMedicationsView.showMedications(medications);
        }
    }


    private void processEmptyMedications(){
        mMedicationsView.showNoMedications();
    }


    @Override
    public void addNewMedication() {
        mMedicationsView.showAddMedication();
    }

    @Override
    public void openMedicationDetails(@NonNull Medication requestedMedication) {
        checkNotNull(requestedMedication, "requestedMedication cannot be null");
        mMedicationsView.showMedicationDetailsUi(requestedMedication.getMId());
    }

    @Override
    public void setFiltering(MedicationsFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public MedicationsFilterType getFiltering() {
        return mCurrentFiltering;
    }



}
