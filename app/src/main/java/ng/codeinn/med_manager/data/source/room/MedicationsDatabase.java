package ng.codeinn.med_manager.data.source.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ng.codeinn.med_manager.data.Medication;

/**
 * Created by Jer on 11/04/2018.
 */

@Database(entities = {Medication.class}, version = 1, exportSchema = false)
public abstract class MedicationsDatabase extends RoomDatabase {

    private static MedicationsDatabase INSTANCE;

    public abstract MedicationsDao medicationsDao();


    public static MedicationsDatabase getInstance(Context context){
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MedicationsDatabase.class, "medications.db")
                        .build();
            }
            return INSTANCE;
    }

//    public static void destroyInstance(){
//        INSTANCE = null;
//    }
}
