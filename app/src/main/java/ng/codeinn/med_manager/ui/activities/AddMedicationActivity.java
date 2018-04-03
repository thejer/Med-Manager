package ng.codeinn.med_manager.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ng.codeinn.med_manager.R;

public class AddMedicationActivity extends AppCompatActivity {

    private static final String TAG = "AddMedicationsActivity";
    private EditText medicationNameEditText,
            medicationDescriptionEditText;
    private Spinner intervalSpinner;
    private TextView startDateTextView,
            endDateTextView;
    private LinearLayout cancelLayout,
            addLayout;
    int timeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        medicationNameEditText = findViewById(R.id.medication_name_edit);
        medicationDescriptionEditText = findViewById(R.id.description_edit);

        startDateTextView = findViewById(R.id.start_date_tv);
        endDateTextView = findViewById(R.id.end_date_tv);

        cancelLayout = findViewById(R.id.cancel_layout);
        addLayout = findViewById(R.id.add_layout);

        setUpIntervalSpinner();

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void setUpIntervalSpinner(){
        List<Integer> intervals = new ArrayList<>();
        for (int i = 0; i < 25; i++){
            intervals.add(i);
        }

        ArrayAdapter<Integer> intervalAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, intervals);

        intervalSpinner = findViewById(R.id.interval_spinner);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    timeInterval = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }catch (NumberFormatException exception){
                    Log.e(TAG, "onItemSelected: Could not parse", exception );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void addMedication(){
        String medicationName = medicationNameEditText.getText().toString();
        String medicationDescription = medicationDescriptionEditText.getText().toString();

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        }
    }

    public void showDatePickerDialog(View v){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
