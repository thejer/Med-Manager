package ng.codeinn.med_manager.dashboard;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ng.codeinn.med_manager.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardContract.IView {

    private DashboardContract.IPresenter mPresenter;

    private EditText userNameView, emailView, medicationCountView;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab_save_profile);
        floatingActionButton.setImageResource(R.drawable.ic_done);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveProfile(userNameView.getText().toString(), emailView.getText().toString());
            }
        });
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        userNameView = rootView.findViewById(R.id.user_name);
        emailView = rootView.findViewById(R.id.email);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void setPresenter(DashboardContract.IPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setUsername(String username) {
        userNameView.setText(username);
    }

    @Override
    public void setEmail(String email) {
        emailView.setText(email);
    }

    @Override
    public void setMedicationCount(int count) {

    }

    @Override
    public void showMedications() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void openLogin() {

    }

    @Override
    public boolean isActive() {
       return isAdded();
    }
}
