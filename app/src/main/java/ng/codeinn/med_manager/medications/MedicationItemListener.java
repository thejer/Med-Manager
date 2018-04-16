package ng.codeinn.med_manager.medications;

import ng.codeinn.med_manager.data.Medication;

/**
 * Created by Jer on 16/04/2018.
 */

public interface MedicationItemListener {

    void onMedicationClick(Medication clickedMedication);

}