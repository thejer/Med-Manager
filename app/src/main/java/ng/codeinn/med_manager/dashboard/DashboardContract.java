package ng.codeinn.med_manager.dashboard;

import ng.codeinn.med_manager.BasePresenter;
import ng.codeinn.med_manager.BaseView;

/**
 * Created by Jer on 18/04/2018.
 */

public interface DashboardContract {

    interface IPresenter extends BasePresenter{

        void saveProfile(String username, String email);

        void populateProfile();

        void signOut();

//        void startChangePassword();

    }

    interface IView extends BaseView<IPresenter>{

        void setUsername(String username);

        void setEmail(String email);

        void setMedicationCount(int count);

        void showMedications();

        void openLogin();

        boolean isActive();

    }

}
