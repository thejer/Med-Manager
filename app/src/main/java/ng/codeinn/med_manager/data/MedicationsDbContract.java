package ng.codeinn.med_manager.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jer on 02/04/2018.
 */

public class MedicationsDbContract {


    public static final String CONTENT_AUTHORITY = "ng.codeinn.med_manager";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MEDICATIONS = "medications";



    public static final class MedicationsEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_MEDICATIONS)
                .build();


        public static final String 
                TABLE_NAME = "medications",
                COLUMN_MEDICATION_NAME = "medication_name",
                MEDICATION_DESCRIPTION = "medication_description",
                MEDICATION_INTERVAL = "medication_interval",
                START_DATE = "start_date",
                START_MONTH = "start_month",
                END_DATE = "end_date";

        public static Uri buildUriWithMonth(String month){
            return CONTENT_URI.buildUpon().
                    appendPath(month).
                    build();
        }

        public static Uri builUriWithId(int id){
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();

        }


        public static String getSqlSelectForTheMonth(String month){
            return MedicationsDbContract.MedicationsEntry.START_MONTH + " == " + month;
        }

    }

}
