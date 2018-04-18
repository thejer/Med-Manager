package ng.codeinn.med_manager.dashboard;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import ng.codeinn.med_manager.data.UserInformation;
import ng.codeinn.med_manager.data.UserInformationRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jer on 18/04/2018.
 */

public class DashboardPresenter implements DashboardContract.IPresenter, UserInformationRepository.GetUserInformationCallback {


    private final UserInformationRepository mUserInformationRepository;

    private final DashboardContract.IView mDashboardView;

    public DashboardPresenter(DashboardContract.IView dashboardView,
                              UserInformationRepository userInformationRepository){
        mDashboardView = checkNotNull(dashboardView);
        mUserInformationRepository = checkNotNull(userInformationRepository);

        mDashboardView.setPresenter(this);
    }



    @Override
    public void start() {
        populateProfile();
    }

    @Override
    public void saveProfile(String username, String email) {
        UserInformation userInformation =  new UserInformation(username, email);
        mUserInformationRepository.saveUserInformation(userInformation);
        mDashboardView.showMedications();
    }

    @Override
    public void populateProfile() {
        mUserInformationRepository.getUserInformation(this);
    }

    @Override
    public void signOut() {

    }

    @Override
    public void onUserInformationLoaded(UserInformation userInformation) {
        if(mDashboardView.isActive()){
            mDashboardView.setUsername(userInformation.getmUsername());
            mDashboardView.setEmail(userInformation.getmEmail());
        }
    }

    @Override
    public void onDataNotAvailable() {

    }
}
