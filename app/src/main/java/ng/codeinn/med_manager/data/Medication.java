package ng.codeinn.med_manager.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Jer on 11/04/2018.
 */

@Entity(tableName = "Medications")
public final class Medication {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey
    private final String mId;


    @Nullable
    @ColumnInfo(name = "name")
    private final String mMedicationName;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "interval")
    private final int mInterval;

    @Nullable
    @ColumnInfo(name = "start")
    private final String  mStartDate;

    @Nullable
    @ColumnInfo(name = "month")
    private final String mStartMonth;

    @Nullable
    @ColumnInfo(name = "end")
    private final String  mEndDate;

//    @NonNull
//    @ColumnInfo(name = "completed")
//    private final boolean mCompleted;


    /**
     * Use this constructor to create an active medication
     * @param mMedicationName medication name
     * @param mDescription description
     * @param mInterval interval of medication
     * @param mStartDate start date
     * @param mStartMonth month of medication
     * @param mEndDate end date
     */
    @Ignore
    public Medication(@Nullable String mMedicationName,
                      @Nullable String mDescription,
                      @Nullable int mInterval,
                      @Nullable String mStartDate,
                      @Nullable String mStartMonth,
                      @Nullable String mEndDate){
        this(mMedicationName, mDescription, mInterval, mStartDate, mStartMonth, mEndDate, UUID.randomUUID().toString());
    }

    /**
     * Use this constructor to create an active medication if it has an id (Update)
     * @param mMedicationName medication name
     * @param mDescription description
     * @param mInterval interval of medication
     * @param mStartDate start date
     * @param mStartMonth month of medication
     * @param mEndDate end date
     * @param mId medication id
     */
    public Medication(@Nullable String mMedicationName,
                      @Nullable String mDescription,
                      @Nullable int mInterval,
                      @Nullable String mStartDate,
                      @Nullable String mStartMonth,
                      @Nullable String mEndDate,
                      @NonNull String mId){
        this.mId = mId;
        this.mMedicationName = mMedicationName;
        this.mDescription = mDescription;
        this.mInterval = mInterval;
        this.mStartDate = mStartDate;
        this.mStartMonth = mStartMonth;
        this.mEndDate = mEndDate;
    }

    @NonNull
    public String getMId() {
        return mId;
    }

    @Nullable
    public String getMMedicationName() {
        return mMedicationName;
    }

    @Nullable
    public String getMDescription() {
        return mDescription;
    }

    @Nullable
    public int getMInterval() {
        return mInterval;
    }

    @Nullable
    public String getMStartDate() {
        return mStartDate;
    }

    @Nullable
    public String getMStartMonth() {
        return mStartMonth;
    }

    @Nullable
    public String getMEndDate() {
        return mEndDate;
    }

    public boolean isEmpty(){
        return Strings.isNullOrEmpty(mMedicationName)&&
                Strings.isNullOrEmpty(mDescription);
    }



    //    @NonNull
//    public boolean ismCompleted() {
//        return mCompleted;
//    }
}
