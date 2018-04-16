package ng.codeinn.med_manager.data.source.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ng.codeinn.med_manager.data.Medication;

/**
 * Created by Jer on 11/04/2018.
 */

@Dao
public interface MedicationsDao {

    @Query("SELECT * FROM Medications")
    List<Medication> getAll();

    @Query("SELECT * FROM Medications WHERE month = :month")
    List<Medication> getMonthMedications(String month);

    @Query("SELECT * FROM Medications WHERE id = :medicationId")
    Medication getMedicationById(String medicationId);


//    @Query("SELECT COUNT(*) from medications")
//    int countMedications;

//    @Insert
//    void insertAll(Medication... medications);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedication(Medication medications);

    @Update
    int updateMedication(Medication medications);


//    @Delete
//    void delete(Medication medications);



    @Query("DELETE FROM Medications WHERE id = :entryId")
    int deleteTaskById(String entryId);



}
