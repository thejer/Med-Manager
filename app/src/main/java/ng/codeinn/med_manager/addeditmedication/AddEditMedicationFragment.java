package ng.codeinn.med_manager.addeditmedication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.utilities.MedicationDateUtils;

import static android.content.ContentValues.TAG;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditMedicationFragment extends Fragment implements AddEditMedicationContract.IView{

    public static final String EDIT_MEDICATION_ID = "edit_medication_id";

    private EditText medicationNameEditText,
            medicationDescriptionEditText;
    private Spinner intervalSpinner;
    private TextView startDateTextView,
            endDateTextView;

    int timeInterval;
    int endHour;
    int endMinute;
    int endYear;
    int endDay ;
    int endMonth;
    int startHour;
    int startMinute;
    int startYear;
    int startDay;
    int startMonthId;


    private AddEditMedicationContract.IPresenter mPresenter;




    public AddEditMedicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                getActivity().findViewById(R.id.fab_edit_medication_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                String startDateString =  String.format(getString(R.string.date_format),
//                        startMonthId + 1, startDay, startYear, startHour, startMinute);
//                String endDateString = String.format(getString(R.string.date_format),
//                        endMonth + 1, endDay, endYear, endHour, endMinute);


                if (startDay == 0){
                    startDateTextView.setError("Set Date and Time");
                }

                Calendar startCalendar = Calendar.getInstance();
                startCalendar.set(Calendar.YEAR, startYear);
                startCalendar.set(Calendar.MONTH, startMonthId);
                startCalendar.set(Calendar.DAY_OF_MONTH, startDay);
                startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
                startCalendar.set(Calendar.MINUTE, startMinute);

//        String format = "MMM dd yyyy HH:mm";
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
               String startDate = dateFormat.format(startCalendar.getTime());

                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set(Calendar.YEAR, endYear);
                endCalendar.set(Calendar.MONTH, endMonth);
                endCalendar.set(Calendar.DAY_OF_MONTH, endDay);
                endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
                endCalendar.set(Calendar.MINUTE, endMinute);

                String endDate = dateFormat.format(endCalendar.getTime());

                boolean cancel = false;


                if (endYear == 0){
                    endDateTextView.setError("Set the end date");
                    endDateTextView.requestFocus();
                    cancel = true;
                }
                if(startYear == 0){
                    startDateTextView.setError("Set the start date");
                    startDateTextView.requestFocus();
                    cancel = true;
                }
                if (endCalendar.getTime().getTime() < startCalendar.getTime().getTime()){
                    startDateTextView.setError("The start date can not be later than the end date");
                    endDateTextView.setError("The start date can not be later than the end date");
                    startDateTextView.requestFocus();
                    endDateTextView.requestFocus();
                    cancel = true;
                }

                if (!cancel){
                    Log.i(TAG, "onClick: start date" + startDate.toString());

                    Toast.makeText(getContext(), startDate, Toast.LENGTH_SHORT).show();

                    String month =  MedicationDateUtils.getMonth(startMonthId + 1);

                    mPresenter.saveMedication(medicationNameEditText.getText().toString(),
                            medicationDescriptionEditText.getText().toString(),
                            timeInterval,
                            startDate, endDate, month, getContext());

                    mPresenter.scheduleMedicationScheduler(getContext(), startDate, medicationNameEditText.getText().toString(), timeInterval);
                    Log.i(TAG, "onClick: called a scheduler for" + medicationNameEditText.getText().toString());
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_edit_medication, container, false);
        medicationNameEditText = rootView.findViewById(R.id.medication_name_edit);
        medicationDescriptionEditText = rootView.findViewById(R.id.description_edit);
        intervalSpinner = rootView.findViewById(R.id.interval_spinner);
        startDateTextView = rootView.findViewById(R.id.start_date_tv);
        startDateTextView.setText(MedicationDateUtils.getCurrentTimeInMillis());
        endDateTextView = rootView.findViewById(R.id.end_date_tv);
        endDateTextView.setText(MedicationDateUtils.getCurrentTimeInMillis());
        setUpIntervalSpinner();
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog();
                showStartTimePickerDialog();

            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog();
                showEndTimePickerDialog();

            }
        });
        setHasOptionsMenu(true);

        return rootView;
    }

    private void setUpIntervalSpinner()
    {
        List<Integer> intervals = new ArrayList<>();
        for (int i = 0; i < 25; i++){
            intervals.add(i);
        }

        ArrayAdapter<Integer> intervalAdapter = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item, intervals);

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


    @SuppressLint("HandlerLeak")
    Handler mStartDateHandler = new  Handler() {
        public void handleMessage(Message m) {
            Bundle startDateBundle = m.getData();

            startYear = startDateBundle.getInt("set_year", 0);
            startDay = startDateBundle.getInt("set_day", 0);
            startMonthId = startDateBundle.getInt("set_month", 0);
            updateDateTime(startYear, startMonthId, startDay, startHour, startMinute, startDateTextView);

        }
    };


    @SuppressLint("HandlerLeak")
    Handler mEndDateHandler = new Handler(){
        public void handleMessage(Message m){
            Bundle endDateBundle = m.getData();
            endYear = endDateBundle.getInt("set_year", 0);
            endDay = endDateBundle.getInt("set_day", 0);
            endMonth = endDateBundle.getInt("set_month", 0);
            updateDateTime(endYear, endMonth, endDay, endHour, endMinute, endDateTextView);

        }
    };

    @SuppressLint("HandlerLeak")
    Handler mStartTimeHandler = new  Handler() {
        public void handleMessage(Message m) {
            Bundle startTimeBundle = m.getData();

            startHour = startTimeBundle.getInt("set_hour", 0);
            startMinute = startTimeBundle.getInt("set_minute", 0);
        }
    };

    @SuppressLint("HandlerLeak")
    Handler mEndTimeHandler = new  Handler() {
        public void handleMessage(Message m) {
           Bundle endTimeBundle = m.getData();

            endHour = endTimeBundle.getInt("set_hour", 0);
            endMinute = endTimeBundle.getInt("set_minute", 0);
        }
    };

    @Override
    public void setPresenter(AddEditMedicationContract.IPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showStartDatePickerDialog() {
        DialogFragment dialogFragment = new DatePickerFragment(mStartDateHandler);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }



    @Override
    public void showEndDatePickerDialog() {
        DialogFragment dialogFragment = new DatePickerFragment(mEndDateHandler);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void showStartTimePickerDialog() {
        DialogFragment dialogFragment = new TimePickerFragment(mStartTimeHandler);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void showEndTimePickerDialog() {
        DialogFragment dialogFragment = new TimePickerFragment(mEndTimeHandler);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void updateDateTime(int year, int month, int day, int hour, int minute, TextView v) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

//        String format = "MMM dd yyyy HH:mm";
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        v.setText(dateFormat.format(calendar.getTime()));
    }



    @Override
    public void setEmptyFieldError() {
        medicationNameEditText.setError("Medication name cannot be empty");
        medicationNameEditText.requestFocus();
        medicationDescriptionEditText.setError("Description cannot be empty");
        medicationDescriptionEditText.requestFocus();
    }

    @Override
    public void setName(String name) {
        medicationNameEditText.setText(name);
    }

    @Override
    public void setDescription(String description) {
        medicationDescriptionEditText.setText(description);
    }


    @Override
    public void setStartDate(String startDate) {
        startDateTextView.setText(startDate.toString());
    }


    @Override
    public void setEndDate(String endDate) {
        endDateTextView.setText(endDate.toString());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showMedicationsList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


}
