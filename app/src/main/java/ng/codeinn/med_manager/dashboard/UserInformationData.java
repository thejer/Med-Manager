package ng.codeinn.med_manager.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import ng.codeinn.med_manager.data.UserInformation;
import ng.codeinn.med_manager.data.UserInformationRepository;
import ng.codeinn.med_manager.util.AppExecutors;

/**
 * Created by Jer on 18/04/2018.
 */

public class UserInformationData implements UserInformationRepository{

    private static UserInformationData INSTANCE;

    private FirebaseUser mFirebaseUser;

    private Context mContext;

    private UserInformationData (FirebaseUser firebaseUser, Context context){
        mFirebaseUser = firebaseUser;
        mContext = context;
    }

    public static UserInformationData getInstance(FirebaseUser firebaseUser, Context context){
        if (INSTANCE == null){
            synchronized (UserInformationData.class){
                if(INSTANCE == null){
                    INSTANCE = new UserInformationData( firebaseUser, context);

                }
            }
        }
        return INSTANCE;
    }



    @Override
    public void getUserInformation(@NonNull GetUserInformationCallback callback) {
        UserInformation userInformation = new UserInformation(mFirebaseUser.getDisplayName(), mFirebaseUser.getEmail());
        callback.onUserInformationLoaded(userInformation);
    }

    @Override
    public void saveUserInformation(UserInformation userInformation) {
        mFirebaseUser.updateEmail(userInformation.getmEmail());

        UserProfileChangeRequest profileChangeRequest  = new UserProfileChangeRequest.Builder()
                .setDisplayName(userInformation.getmUsername())
                .build();
        mFirebaseUser.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext, "Profile updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
