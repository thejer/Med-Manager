package ng.codeinn.med_manager.data;

/**
 * Created by Jer on 18/04/2018.
 */

public class UserInformation {

    private String mUsername;

    private String mEmail;


    public UserInformation(String mUsername, String mEmail) {
        this.mUsername = mUsername;
        this.mEmail = mEmail;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }


}
