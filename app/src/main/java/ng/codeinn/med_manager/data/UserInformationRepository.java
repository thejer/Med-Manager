package ng.codeinn.med_manager.data;

import android.support.annotation.NonNull;

/**
 * Created by Jer on 18/04/2018.
 */

public interface UserInformationRepository {

    interface GetUserInformationCallback{
        void onUserInformationLoaded(UserInformation userInformation);

        void onDataNotAvailable();
    }

    void getUserInformation(@NonNull GetUserInformationCallback callback);

    void saveUserInformation(UserInformation userInformation);

}
