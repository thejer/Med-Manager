package ng.codeinn.med_manager.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.ui.fragments.MedicationsFragment;

/**
 * Created by Jer on 31/03/2018.
 */

/**
 * {@link MedicationAdapter} exposes a list of medications per month
 */
public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationAdapterViewHolder> {

    Context mContext;

    private Cursor mCursor;
    public MedicationAdapter(Context context ) {

        mContext = context;
    }

    @Override
    public MedicationAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.medication_list_item, parent, false);
        view.setFocusable(true);

        return new MedicationAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String medicationName = mCursor.getString(MedicationsFragment.INDEX_MEDICATION_NAME);
        holder.medicationNameView.setText(medicationName);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MedicationAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView medicationNameView,
                statusView,
                percentageView;

        public MedicationAdapterViewHolder(View itemView) {
            super(itemView);

            medicationNameView = itemView.findViewById(R.id.medication_name);
            statusView = itemView.findViewById(R.id.status);
            percentageView = itemView.findViewById(R.id.percentage_completed);

        }
    }
}
