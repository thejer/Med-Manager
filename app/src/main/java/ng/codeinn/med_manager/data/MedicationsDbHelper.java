package ng.codeinn.med_manager.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ng.codeinn.med_manager.data.MedicationsDbContract.MedicationsEntry;

import static ng.codeinn.med_manager.data.MedicationsDbContract.MedicationsEntry.*;

/**
 * Created by Jer on 02/04/2018.
 */

public class MedicationsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medications.db";

    private static final int DATABASE_VERSION = 1;

    public MedicationsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MEDICATIONS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEDICATION_NAME + " TEXT NOT NULL, " +
                MEDICATION_DESCRIPTION + " TEXT NOT NULL, " +
                MEDICATION_INTERVAL + " REAL NOT NULL, " +
                START_DAY + " INTEGER NOT NULL, " +
                START_MONTH + " INTEGER NOT NULL, " +
                START_YEAR + " INTEGER NOT NULL, " +
                END_DAY + " INTEGER NOT NULL, " +
                END_MONTH + " INTEGER NOT NULL, " +
                END_YEAR + " INTEGER NOT NULL" +
                "); ";
        db.execSQL(SQL_CREATE_MEDICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
