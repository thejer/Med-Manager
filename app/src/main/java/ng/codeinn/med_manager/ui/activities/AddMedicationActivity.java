package ng.codeinn.med_manager.ui.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static ng.codeinn.med_manager.data.MedicationsDbContract.MedicationsEntry.*;
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
    boolean noIntervalSelected = true;

    int endYear;
    int endDay ;
    int endMonth;
    int startYear;
    int startDay;
    int startMonth;

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
                showStartDatePickerDialog(v);
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog(v);
            }
        });

        cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;
                medicationNameEditText.setError(null);
                medicationDescriptionEditText.setError(null);

                String medicationName = medicationNameEditText.getText().toString();
                String medicationDescription = medicationDescriptionEditText.getText().toString();

                if (TextUtils.isEmpty(medicationName) || medicationName.equals(" ")){
                    medicationNameEditText.setError("input the medication name");
                    focusView = medicationNameEditText;
                    cancel = true;
                }
                if (TextUtils.isEmpty(medicationDescription) || medicationDescription.equals(" ")){
                    medicationDescriptionEditText.setError("input a short description");
                    focusView = medicationDescriptionEditText;
                    cancel = true;
                }
                if (noIntervalSelected){
                    focusView = intervalSpinner;
                }

                if (cancel || noIntervalSelected){
                    focusView.requestFocus();
                }else {
                    addMedication();
                    finish();
                    startActivity(new Intent(AddMedicationActivity.this, MainActivity.class));
                }
            }
        });


    }

    private void setUpIntervalSpinner(){
        noIntervalSelected = false;
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
                noIntervalSelected = true;
            }
        });
    }


    public void addMedication(){
        String medicationName = medicationNameEditText.getText().toString();
        String medicationDescription = medicationDescriptionEditText.getText().toString();

        String startDateString = getResources().getString(R.string.date_formatter,
                startMonth,
                startDay,
                startYear,
                "12", "00", "AM");
        String endDateString = getResources().getString(R.string.date_formatter,
                endMonth,
                endDay,
                endYear,
                "11", "59", "PM");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault() );
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = simpleDateFormat.parse(startDateString);
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDateMillis = startDate.getTime();
        long endDateMillis = endDate.getTime();

        ContentValues medicationContentValues = new ContentValues();

        medicationContentValues.put(COLUMN_MEDICATION_NAME, medicationName);
        medicationContentValues.put(MEDICATION_DESCRIPTION, medicationDescription);
        medicationContentValues.put(MEDICATION_INTERVAL, timeInterval);
        medicationContentValues.put(START_DATE, startDateMillis);
        medicationContentValues.put(START_MONTH, startMonth);
        medicationContentValues.put(END_DATE, endDateMillis);

        Uri uri = getContentResolver().insert(CONTENT_URI, medicationContentValues);
        if (uri!=null){
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        Calendar calendar = Calendar.getInstance();
        Handler datePickerHandler;
        private int mYear, mMonth, mDay;


        public DatePickerFragment() {
        }

        @SuppressLint("ValidFragment")
        public DatePickerFragment(Handler h) {
            datePickerHandler = h;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            Bundle dateBundle = new Bundle();
            dateBundle.putInt("set_year", mYear);
            dateBundle.putInt("set_month", mMonth);
            dateBundle.putInt("set_day", mDay);

            Message dateMessage = new Message();
            dateMessage.setData(dateBundle);

            datePickerHandler.sendMessage(dateMessage);

        }
    }

    @SuppressLint("HandlerLeak")
    Handler mStartDateHandler = new  Handler() {
        public void handleMessage(Message m) {
            Bundle startDateBundle = m.getData();

            startYear = startDateBundle.getInt("set_year", 0);
            startDay = startDateBundle.getInt("set_day", 0);
            startMonth = startDateBundle.getInt("set_month", 0);
            updateDate(startYear, startMonth, startDay, startDateTextView);

        }
    };


    @SuppressLint("HandlerLeak")
    Handler mEndDateHandler = new Handler(){
        public void handleMessage(Message m){
           Bundle endDateBundle = m.getData();
           endYear = endDateBundle.getInt("set_year", 0);
           endDay = endDateBundle.getInt("set_day", 0);
           endMonth = endDateBundle.getInt("set_month", 0);
            updateDate(endYear, endMonth, endDay, endDateTextView);
        }
    };

    public void showStartDatePickerDialog(View v){
        DialogFragment dialogFragment = new DatePickerFragment(mStartDateHandler);
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v){
        DialogFragment dialogFragment = new DatePickerFragment(mEndDateHandler);
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }


    private void updateDate(int year, int month,  int day, TextView v){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        String format = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        v.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
