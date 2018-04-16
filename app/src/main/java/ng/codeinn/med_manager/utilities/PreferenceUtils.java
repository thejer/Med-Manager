package ng.codeinn.med_manager.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jer on 06/04/2018.
 */

public final class PreferenceUtils {

    public static final String KEY_MEDICATION_COUNT = "medication_count";

    public static final String KEY_MEDICATION_STATUS = "medication_status";

    public static final int DEFAULT_COUNT = 0;

    public static final int DEFAULT_STATUS = 0;

    synchronized private static void setMedicationCount(Context context, int medicationTaken){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_MEDICATION_COUNT, medicationTaken);
        editor.apply();
    }

    public static int getMedicationCount(Context context){
        return  PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_MEDICATION_COUNT, DEFAULT_COUNT);
    }

    synchronized public static void incrementMedicationTaken(Context context){
        int medicationCount = PreferenceUtils.getMedicationCount(context);
        PreferenceUtils.setMedicationCount(context, ++medicationCount);
    }

    synchronized public static void setMedicationStatus(Context context, int status){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_MEDICATION_STATUS, status);
        editor.apply();
    }

    public static int getMedicationStatus(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_MEDICATION_STATUS, DEFAULT_STATUS);
    }


}
