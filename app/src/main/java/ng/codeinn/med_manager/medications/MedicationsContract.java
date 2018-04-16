package ng.codeinn.med_manager.medications;

import android.support.annotation.NonNull;

import java.util.List;

import ng.codeinn.med_manager.BasePresenter;
import ng.codeinn.med_manager.BaseView;
import ng.codeinn.med_manager.data.Medication;

/**
 * Created by Jer on 14/04/2018.
 */

public interface MedicationsContract {
    
    interface IPresenter extends BasePresenter{
        void result(int requestCode, int resultCode);

        void loadMedications();

        void addNewMedication();

        void openMedicationDetails(@NonNull Medication requestedMedication);

        void setFiltering(MedicationsFilterType requestType);

        MedicationsFilterType getFiltering();
    }

    interface IView extends BaseView<IPresenter>{

        void setLoadingIndicator(boolean active);

        void showMedications(List<Medication> medications);

        void showAddMedication();

        void showMedicationDetailsUi(String medicationId);

        void showLoadingMedicationsError();

        void showNoMedications();

        void setJanFilterLabel();

        void setFebFilterLabel();

        void setMarFilterLabel();

        void setAprFilterLabel();

        void setMayFilterLabel();

        void setJunFilterLabel();

        void setJulFilterLabel();

        void setAugFilterLabel();

        void setSepFilterLabel();

        void setOctFilterLabel();

        void setNovFilterLabel();

        void setDecFilterLabel();

        void setAllFilterLabel();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }
}
