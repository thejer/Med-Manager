package ng.codeinn.med_manager.medicationdetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.data.source.room.MedicationsDatabase;
import ng.codeinn.med_manager.data.source.room.MedicationsLocalDataSource;
import ng.codeinn.med_manager.util.ActivityUtils;
import ng.codeinn.med_manager.util.AppExecutors;

public class MedicationDetailActivity extends AppCompatActivity {

    public static final String MEDICATION_EXTRA_ID = "MEDICATION_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        // Get the requested task id
        String medicationId = getIntent().getStringExtra(MEDICATION_EXTRA_ID);

        MedicationDetailFragment medicationDetailFragment = (MedicationDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.medication_detail_container);

        if (medicationDetailFragment == null){
            medicationDetailFragment = MedicationDetailFragment.newInstance(medicationId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    medicationDetailFragment, R.id.medication_detail_container);
        }

        MedicationsDatabase database = MedicationsDatabase.getInstance(getApplicationContext());

        new MedicationDetailPresenter(medicationId,
                MedicationsLocalDataSource.getInstance(new AppExecutors(), database.medicationsDao()),
                medicationDetailFragment);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
