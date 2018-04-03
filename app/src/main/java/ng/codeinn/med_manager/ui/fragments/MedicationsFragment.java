package ng.codeinn.med_manager.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.codeinn.med_manager.R;

import static ng.codeinn.med_manager.data.MedicationsDbContract.MedicationsEntry.*;

/**
 * Created by Jer on 01/04/2018.
 */

public class MedicationsFragment extends Fragment {


    public MedicationsFragment() {
    }


    public static final String[] MAIN_MEDICATION_PROJECTION = {
            COLUMN_MEDICATION_NAME,
            START_DAY,
            START_MONTH,
            START_YEAR,
            END_DAY,
            END_MONTH,
            END_YEAR,
    };

    public static final int INDEX_MEDICATION_NAME = 0,
            INDEX_START_DAY = 1,
            INDEX_START_MONTH = 2,
            INDEX_START_YEAR = 3,
            INDEX_END_DAY = 4,
            INDEX_END_MONTH = 5,
            INDEX_END_YEAR = 6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_medication, container, false);
        return rootView;

    }
}
