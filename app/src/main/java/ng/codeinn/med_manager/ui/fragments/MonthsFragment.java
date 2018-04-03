package ng.codeinn.med_manager.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import ng.codeinn.med_manager.data.MonthsAssets;
import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.ui.adapters.MonthsAdapter;


/**
 * Created by Jer on 31/03/2018.
 */


// This fragment displays the 12 months in a grid list
public class MonthsFragment extends Fragment {

    // Mandatory empty constructor
    public MonthsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_months, container, false);


        GridView monthsGridView = (GridView) rootView.findViewById(R.id.months_grid_view);

        MonthsAdapter monthsAdapter = new MonthsAdapter(getActivity(), MonthsAssets.getMonths());

        monthsGridView.setAdapter(monthsAdapter);

        return rootView;
    }
}
