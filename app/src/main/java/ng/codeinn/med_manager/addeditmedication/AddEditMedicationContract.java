package ng.codeinn.med_manager.addeditmedication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Strings;

import java.util.Date;

import ng.codeinn.med_manager.BasePresenter;
import ng.codeinn.med_manager.BaseView;
import ng.codeinn.med_manager.data.Medication;


/**
 * Created by Jer on 12/04/2018.
 */

public interface AddEditMedicationContract {

    interface IPresenter extends BasePresenter{

        void saveMedication(String name, String description, int interval, String start, String end, String month, Context context);

        void populateMedication();

        boolean isDataMissing();

        void scheduleMedicationScheduler(Context context, String startDate, String medicationName, int interval);

    }

    interface IView extends BaseView<IPresenter>{

        void showStartDatePickerDialog();

        void showEndDatePickerDialog();

        void showStartTimePickerDialog();

        void showEndTimePickerDialog();

        void updateDateTime(int year, int month,  int day, int hour, int minute, TextView v);

        void setEmptyFieldError();

        void setName(String name);

        void setDescription(String description);

        void setStartDate(String startDate);

        void setEndDate(String  endDate);

        boolean isActive();

        void showMedicationsList();



    }
}
