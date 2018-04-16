package ng.codeinn.med_manager.medicationdetail;

import ng.codeinn.med_manager.BasePresenter;
import ng.codeinn.med_manager.BaseView;
import ng.codeinn.med_manager.data.Medication;

/**
 * Created by Jer on 15/04/2018.
 */

public interface MedicationDetailContract {

    interface IPresenter extends BasePresenter {

        void editMedication();

        void deleteMedication();

//        void completeMedication();

//        void activateMedication();
    }

    interface IView extends BaseView<IPresenter> {

        void setMedicationDetails(Medication medicationDetails);

        void showEditMedication(String medicationId);

        void showMedicationDeleted();

//        void showMedicationCompleted();

//        void showMedicationMarkedActive();

        boolean isActive();
    }


}
