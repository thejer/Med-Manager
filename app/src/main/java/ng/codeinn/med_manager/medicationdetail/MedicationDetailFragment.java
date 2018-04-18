package ng.codeinn.med_manager.medicationdetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.addeditmedication.AddEditMedicationActivity;
import ng.codeinn.med_manager.addeditmedication.AddEditMedicationFragment;
import ng.codeinn.med_manager.data.Medication;
import ng.codeinn.med_manager.util.ActivityUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationDetailFragment extends Fragment implements MedicationDetailContract.IView {

    @NonNull
    private static final String ARGUMENT_MEDICATION_ID = "MEDICATION_ID";

    @NonNull
    private static final int REQUEST_EDIT_MEDICATION = 1;

    private MedicationDetailContract.IPresenter mPresenter;

    private TextView mMedicationName,
            mDescription,
            mInterval,
            mStartDateTime,
            mEndDateTime;


    public static MedicationDetailFragment newInstance(@Nullable String medicationId){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_MEDICATION_ID, medicationId);
        MedicationDetailFragment fragment = new MedicationDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    public MedicationDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_medication_detail, container, false);

        setHasOptionsMenu(true);

        mMedicationName = rootView.findViewById(R.id.medication_name_detail);
        mDescription = rootView.findViewById(R.id.description_detail);
        mInterval = rootView.findViewById(R.id.interval_detail);
        mStartDateTime = rootView.findViewById(R.id.detail_start_date_tv);
        mEndDateTime = rootView.findViewById(R.id.detail_end_date_tv);

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab_edit_medication);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.editMedication();
            }
        });

        return rootView;
    }

    @Override
    public void setPresenter(MedicationDetailContract.IPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_delete:
                mPresenter.deleteMedication();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.medication_detail_fragment_menu, menu);
    }



    @Override
    public void setMedicationDetails(Medication medicationDetails) {
        String name = medicationDetails.getMMedicationName();
        String description = medicationDetails.getMDescription();
        Integer interval = medicationDetails.getMInterval();
        String intervalString = String.format(getString(R.string.hours_format), interval.toString());
        String start = medicationDetails.getMStartDate();
        String end = medicationDetails.getMEndDate();

        mMedicationName.setText(name);
        mDescription.setText(description);
        mInterval.setText(intervalString);
        mStartDateTime.setText(start);
        mEndDateTime.setText(end);
    }

    @Override
    public void showEditMedication(String medicationId) {
        Intent intent = new Intent(getContext(), AddEditMedicationActivity.class);
        intent.putExtra(AddEditMedicationFragment.EDIT_MEDICATION_ID, medicationId);
        startActivityForResult(intent, REQUEST_EDIT_MEDICATION);
    }

    @Override
    public void showMedicationDeleted() {
        getActivity().finish();
        Toast.makeText(getContext(), "Medication deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_MEDICATION){
            if (resultCode == Activity.RESULT_OK){
                getActivity().finish();
            }
        }
    }
}
