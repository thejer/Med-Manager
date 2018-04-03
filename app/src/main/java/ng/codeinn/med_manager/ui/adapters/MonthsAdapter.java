package ng.codeinn.med_manager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ng.codeinn.med_manager.R;

/**
 * Created by Jer on 31/03/2018.
 */

// Custom adapter that displays the months in a grid view
public class MonthsAdapter extends BaseAdapter {

    // Keeps track of the context and the months list
    private Context mContext;
    private List<String> mMonthsList;


    /**
     * Constructor method
     * @param context the context of the activity
     * @param monthsList the list of months
     */
    public MonthsAdapter( Context context, List<String> monthsList) {
        mContext = context;
        mMonthsList = monthsList;
    }


    @Override
    public int getCount() {
        return mMonthsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.each_month_grid_item, parent, false);
        }

        TextView monthTextView = convertView.findViewById(R.id.month_id);
        monthTextView.setText(mMonthsList.get(position));
    
        return convertView;
    }
}
