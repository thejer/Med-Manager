package ng.codeinn.med_manager.addeditmedication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Jer on 13/04/2018.
 */

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
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
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

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
