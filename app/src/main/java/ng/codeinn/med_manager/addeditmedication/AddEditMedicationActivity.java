package ng.codeinn.med_manager.addeditmedication;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.data.source.room.MedicationsDatabase;
import ng.codeinn.med_manager.data.source.room.MedicationsLocalDataSource;
import ng.codeinn.med_manager.util.ActivityUtils;
import ng.codeinn.med_manager.util.AppExecutors;

public class AddEditMedicationActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    public static final int REQUEST_ADD_MEDICATION = 1;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "should_load_data_from_repo_key";

    private AddEditMedicationPresenter mAddEditMedicationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_medication);

        Toolbar toolbar = findViewById(R.id.add_edit_medication_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        AddEditMedicationFragment addEditMedicationFragment = (AddEditMedicationFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.add_edit_medication_container);

        String medicationId = getIntent().getStringExtra(AddEditMedicationFragment.EDIT_MEDICATION_ID);

        setToolbarTitle(medicationId);

        if(addEditMedicationFragment == null){
            addEditMedicationFragment = new AddEditMedicationFragment();

            if (getIntent().hasExtra(AddEditMedicationFragment.EDIT_MEDICATION_ID)){
                Bundle bundle = new Bundle();
                bundle.putString(AddEditMedicationFragment.EDIT_MEDICATION_ID, medicationId);
                addEditMedicationFragment.setArguments(bundle);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditMedicationFragment, R.id.add_edit_medication_container);
        }

        boolean shouldLoadDataFromRepo = true;

        if (savedInstanceState != null){
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

        MedicationsDatabase database = MedicationsDatabase.getInstance(getApplicationContext());

        mAddEditMedicationPresenter = new AddEditMedicationPresenter(
                MedicationsLocalDataSource.getInstance(new AppExecutors(),database.medicationsDao()),
                addEditMedicationFragment,
                medicationId,
                shouldLoadDataFromRepo);


    }

    private void setToolbarTitle(@Nullable String taskId) {
        if(taskId == null) {
            mActionBar.setTitle("New Medication");
        } else {
            mActionBar.setTitle("Edit Medication");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, mAddEditMedicationPresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    @VisibleForTesting
//    public IdlingResource getCountingIdlingResource(){}

}
