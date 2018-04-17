package ng.codeinn.med_manager.medications;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.addeditmedication.AddEditMedicationActivity;
import ng.codeinn.med_manager.data.Medication;
import ng.codeinn.med_manager.medicationdetail.MedicationDetailActivity;
import ng.codeinn.med_manager.utilities.MedicationDateUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationsFragment extends Fragment implements MedicationsContract.IView, MedicationItemListener{

    private MedicationsContract.IPresenter mPresenter;

    private MedicationAdapter mMedicationAdapter;

    private View mNoMedicationsView;

    private ImageView mNoMedicationIcon;

    private TextView mNoMedicationAddView;

    private TextView mNoMedicationMainView;

    private RecyclerView mRecyclerView;

    public MedicationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMedicationAdapter = new MedicationAdapter(new ArrayList<Medication>(0), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_medications, container, false);
       mRecyclerView = (RecyclerView) rootView.findViewById(R.id.medications_recycler_view);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mMedicationAdapter);
        // Set up  no Medications view
        mNoMedicationsView = rootView.findViewById(R.id.noMedications);
        mNoMedicationIcon = (ImageView) rootView.findViewById(R.id.noMedicatonsIcon);
        mNoMedicationMainView = (TextView) rootView.findViewById(R.id.noMedicationsMain);
        mNoMedicationAddView = (TextView) rootView.findViewById(R.id.noMedicationsAdd);
        mNoMedicationAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMedication();
            }
        });


        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_medication);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewMedication();
            }
        });

        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        swipeRefreshLayout.setScrollUpChild(mRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadMedications();
            }
        });

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                mPresenter.loadMedications();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.medications_fragment_menu ,menu);
    }


    @Override
    public void setPresenter(MedicationsContract.IPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null){
            return;
        }
        final SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });

    }

    @Override
    public void showMedications(List<Medication> medications) {
        mMedicationAdapter.replaceData(medications);
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoMedicationsView.setVisibility(View.GONE);
    }

    @Override
    public void showAddMedication() {
        startActivityForResult(new Intent(getContext(), AddEditMedicationActivity.class),
                AddEditMedicationActivity.REQUEST_ADD_MEDICATION);


    }

    @Override
    public void showMedicationDetailsUi(String medicationId) {
        Intent intent = new Intent(getContext(), MedicationDetailActivity.class);
        intent.putExtra(MedicationDetailActivity.MEDICATION_EXTRA_ID, medicationId);
        startActivity(intent);
    }

    @Override
    public void showLoadingMedicationsError() {
        showMessage(getString(R.string.no_medications));
    }

    @Override
    public void showNoMedications() {
        showNoMedicationsViews(
                getString(R.string.no_medications),
                R.drawable.ic_check_circle_24dp
        );
    }

    private void showNoMedicationsViews(String mainText, int icon){
        mRecyclerView.setVisibility(View.GONE);
        mNoMedicationsView.setVisibility(View.VISIBLE);
        mNoMedicationMainView.setText(mainText);
        mNoMedicationIcon.setImageDrawable(getResources().getDrawable(icon));
        mNoMedicationAddView.setVisibility(View.VISIBLE);

    }



    @Override
    public void setJanFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "January"));
    }

    @Override
    public void setFebFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "February"));

    }

    @Override
    public void setMarFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "March"));

    }

    @Override
    public void setAprFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "April"));

    }

    @Override
    public void setMayFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "May"));

    }

    @Override
    public void setJunFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "June"));

    }

    @Override
    public void setJulFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "July"));

    }

    @Override
    public void setAugFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "August"));

    }

    @Override
    public void setSepFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "September"));

    }

    @Override
    public void setOctFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "October"));

    }

    @Override
    public void setNovFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "November"));

    }

    @Override
    public void setDecFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "December"));

    }

    @Override
    public void setAllFilterLabel() {
        ((MedicationsActivity)getActivity()).setActionBarTitle(getString(R.string.title_format, "All"));

    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.saved_message));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showFilteringPopUpMenu() {

        android.support.v7.widget.PopupMenu popup = new android.support.v7.widget.PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.medications_filter_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.jan:
                        mPresenter.setFiltering(MedicationsFilterType.JANUARY_MEDICATIONS);
                        break;
                    case R.id.feb:
                        mPresenter.setFiltering(MedicationsFilterType.FEBRUARY_MEDICATIONS);
                        break;
                    case R.id.mar:
                        mPresenter.setFiltering(MedicationsFilterType.MARCH_MEDICATIONS);
                        break;
                    case R.id.apr:
                        mPresenter.setFiltering(MedicationsFilterType.APRIL_MEDICATIONS);
                        break;
                    case R.id.may:
                        mPresenter.setFiltering(MedicationsFilterType.MAY_MEDICATIONS);
                        break;
                    case R.id.jun:
                        mPresenter.setFiltering(MedicationsFilterType.JUNE_MEDICATIONS);
                        break;
                    case R.id.jul:
                        mPresenter.setFiltering(MedicationsFilterType.JULY_MEDICATIONS);
                        break;
                    case R.id.aug:
                        mPresenter.setFiltering(MedicationsFilterType.AUGUST_MEDICATIONS);
                        break;
                    case R.id.sep:
                        mPresenter.setFiltering(MedicationsFilterType.SEPTEMBER_MEDICATIONS);
                        break;
                    case R.id.oct:
                        mPresenter.setFiltering(MedicationsFilterType.OCTOBER_MEDICATIONS);
                        break;
                    case R.id.nov:
                        mPresenter.setFiltering(MedicationsFilterType.NOVEMBER_MEDICATIONS);
                        break;
                    case R.id.dec:
                        mPresenter.setFiltering(MedicationsFilterType.DECEMBER_MEDICATIONS);
                        break;
                    default:
                        mPresenter.setFiltering(MedicationsFilterType.ALL_MEDICATIONS);
                        break;
                }
                mPresenter.loadMedications();
                return true;
            }
        });

        popup.show();
    }




    @Override
    public void onMedicationClick(Medication clickedMedication) {
        mPresenter.openMedicationDetails(clickedMedication);
    }


    public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationAdapterViewHolder> {

        private List<Medication> mMedications;

        private MedicationItemListener mItemListener;

        public MedicationAdapter(List<Medication> medications, MedicationItemListener itemListener) {
            setList(medications);
            mItemListener = itemListener;
        }

        private void setList(List<Medication> medications){
            mMedications = checkNotNull(medications);
        }

        private Medication getItem(int position){
            return mMedications.get(position);
        }

        public void replaceData(List<Medication> medications){
            setList(medications);
            notifyDataSetChanged();
        }

        @Override
        public MedicationAdapter.MedicationAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list_item, parent, false);
            view.setFocusable(true);


            return new MedicationAdapter.MedicationAdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MedicationAdapter.MedicationAdapterViewHolder holder, int position) {
            Medication medication = getItem(position);

            // TODO: 16/04/2018 Fix the date format

            holder.medicationNameView.setText(medication.getMMedicationName());
            long startDateInMillis = MedicationDateUtils.normalDateToMillis(medication.getMStartDate());

            long endDateInMillis = MedicationDateUtils.normalDateToMillis(medication.getMEndDate());

            float durationHours = TimeUnit.MILLISECONDS.toHours(endDateInMillis - startDateInMillis);

            float timeCompletedHours =  (durationHours - TimeUnit.MILLISECONDS.toHours(endDateInMillis - System.currentTimeMillis()));

            int completed = (int) ((int) (timeCompletedHours)/durationHours *100);

            int percentageCompleted;

            String status;

            int statusIcon;

            if (completed >= 100){
                percentageCompleted = 100;
                status = getString(R.string.status_completed);
                statusIcon = R.drawable.ic_status_completed;
            }else if( completed <= 0){
                percentageCompleted = 0;
                status = getString(R.string.status_not_started);
                statusIcon = R.drawable.ic_status_not_started;
            }else{
                percentageCompleted = completed;
                status = getString(R.string.in_progress);
                statusIcon = R.drawable.ic_status_in_progress;
            }

            holder.percentageView.setText(String.format(getString(R.string.percentage), percentageCompleted));
            holder.statusView.setText(status);
            holder.statusImageView.setImageResource(statusIcon);
        }

        @Override
        public int getItemCount() {
            return mMedications.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class MedicationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView medicationNameView,
                    statusView,
                    percentageView;
            ImageView statusImageView;

            public MedicationAdapterViewHolder(View itemView) {
                super(itemView);

                medicationNameView = (TextView) itemView.findViewById(R.id.medication_name);
                statusView = (TextView) itemView.findViewById(R.id.status);
                percentageView = (TextView) itemView.findViewById(R.id.percentage_completed);
                statusImageView = itemView.findViewById(R.id.status_indicator);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Medication clickedMedication = getItem(getAdapterPosition());
                mItemListener.onMedicationClick(clickedMedication);
                Toast.makeText(getContext(), "This item was clicked" + clickedMedication.getMMedicationName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
