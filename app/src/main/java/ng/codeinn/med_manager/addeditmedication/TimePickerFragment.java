package ng.codeinn.med_manager.addeditmedication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Jer on 16/04/2018.
 */

public class TimePickerFragment extends android.support.v4.app.DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "";
    SharedPreferences timePrefs;
    private int mHour, mMinute;
    Handler timePickerHandler;

    public TimePickerFragment() {
    }

    @SuppressLint("ValidFragment")
    public TimePickerFragment(Handler h) {
        timePickerHandler = h;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, false);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.i(TAG, "onTimeSet: " + hourOfDay);
        Log.i(TAG, "onTimeSet: " + minute);

        mHour = hourOfDay;
        mMinute = minute;
        Bundle timeBundle = new Bundle();
        timeBundle.putInt("set_hour", mHour);
        timeBundle.putInt("set_minute", mMinute);
        Message timeMessage = new Message();
        timeMessage.setData(timeBundle);

        timePickerHandler.sendMessage(timeMessage);
    }


}